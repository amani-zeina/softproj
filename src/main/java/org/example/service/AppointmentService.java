package org.example.service;

import org.example.notification.ConsoleNotificationObserver;
import org.example.notification.NotificationService;
import org.example.repository.UserRepository;
import org.example.domain.Appointment;
import org.example.domain.Appointment.Status;
import org.example.domain.ServiceType;
import org.example.domain.User;
import org.example.strategy.*;
import org.example.util.FileUtil;

import java.time.*;
import java.util.*;

/**
 * Service responsible for managing appointments.
 * Provides operations for booking, cancelling,
 * validating rules, and listing appointments.
 *
 * This class interacts with appointment storage
 * and applies scheduling rules before confirming bookings.
 *
 * @author Team
 * @version 1.0
 */
public class AppointmentService {

    private final NotificationService notificationService = new NotificationService();
    private static final String FILE = "appointments.txt";

    private final List<AppointmentRuleStrategy> appointmentRules;
    private final List<BookingRuleStrategy> bookingRules;

    public AppointmentService() {
        notificationService.addObserver(new ConsoleNotificationObserver());

        appointmentRules = List.of(
                new WorkingHoursRule(),
                new OverlapRule()
        );

        bookingRules = List.of(
                new StatusRule(),
                new ParticipantRangeRule(),
                new DoubleBookingRule()
        );
    }

    public Appointment getAppointmentById(int id) {
        List<Appointment> list = loadAppointments();
        for (Appointment a : list) {
            if (a.getId() == id) {
                return a;
            }
        }
        return null;
    }

    public boolean addAppointment(String date, String time, int duration, int maxParticipants, ServiceType type) {
        List<Appointment> list = loadAppointments();
        int id = list.stream().mapToInt(Appointment::getId).max().orElse(0) + 1;

        Appointment ap = new Appointment(
                id,
                date,
                time,
                duration,
                maxParticipants,
                0,
                type,
                Status.AVAILABLE,
                "None"
        );

        for (AppointmentRuleStrategy rule : appointmentRules) {
            if (!rule.isValid(ap, list)) {
                return false;
            }
        }

        list.add(ap);
        saveAppointments(list);
        return true;
    }

    public boolean bookAppointment(int id, String username, int participantsCount) {
        List<Appointment> list = loadAppointments();

        for (Appointment a : list) {
            if (a.getId() == id) {

                for (BookingRuleStrategy rule : bookingRules) {
                    if (!rule.isValid(a, username, participantsCount, list)) {
                        return false;
                    }
                }

                a.setCurrentParticipants(participantsCount);
                a.setBookedBy(username);
                a.setStatus(Status.CONFIRMED);

                notificationService.notifyAllUsers(username, "Your appointment has been booked successfully.");

                saveAppointments(list);
                return true;
            }
        }

        System.out.println("Appointment ID not found!");
        return false;
    }

    public boolean validateAppointment(Appointment appointment, String username) {
        if (appointment == null) {
            System.out.println("Appointment not found!");
            return false;
        }

        if (appointment.getStatus() == Status.LATE) {
            System.out.println("Cannot book expired appointment!");
            return false;
        }

        if (appointment.getStatus() != Status.AVAILABLE) {
            System.out.println("This appointment is no longer available!");
            return false;
        }

        List<Appointment> list = loadAppointments();
        for (Appointment other : list) {
            if (username.equals(other.getBookedBy())
                    && other.getDate().equals(appointment.getDate())
                    && other.getTime().equals(appointment.getTime())) {
                System.out.println("You already have another appointment at this time!");
                return false;
            }
        }

        return true;
    }

    public void viewAvailableSlots() {
        List<Appointment> list = loadAppointments();

        System.out.println("\n--- Available Slots ---");
        boolean found = false;

        for (Appointment a : list) {
            if (a.getStatus() == Status.AVAILABLE) {
                System.out.println(a.getId() + " | "
                        + a.getDate() + " | "
                        + a.getTime() + " | "
                        + a.getServiceType().getLabel() + " | "
                        + a.getDuration() + " min | "
                        + a.getCurrentParticipants() + "/"
                        + a.getMaxParticipants() + " | "
                        + a.getStatus());
                found = true;
            }
        }

        if (!found) {
            System.out.println("No available slots.");
        }
    }

    public void viewAllAppointmentsForAdmin() {
        List<Appointment> list = loadAppointments();

        System.out.println("\nID | Date | Time | Service | Duration | P | Status | Booked By");
        System.out.println("---------------------------------------------------------------");

        for (Appointment a : list) {
            System.out.println(a);
        }

        if (list.isEmpty()) {
            System.out.println("No appointments found.");
        }
    }

    public void viewAllAppointments() {
        viewAllAppointmentsForAdmin();
    }

    public void viewUserAppointments(String username) {
        List<Appointment> list = loadAppointments();
        boolean found = false;

        System.out.println("\n--- Your Appointments ---");

        for (Appointment a : list) {
            if (username.equals(a.getBookedBy())) {
                System.out.println(a);
                found = true;
            }
        }

        if (!found) {
            System.out.println("You have no appointments.");
        }
    }

    public boolean editAppointment(int id, String newDate, String newTime, int newDuration) {
        List<Appointment> list = loadAppointments();

        for (Appointment a : list) {
            if (a.getId() == id) {

                Appointment updated = new Appointment(
                        a.getId(),
                        newDate,
                        newTime,
                        newDuration,
                        a.getMaxParticipants(),
                        a.getCurrentParticipants(),
                        a.getServiceType(),
                        a.getStatus(),
                        a.getBookedBy()
                );

                for (AppointmentRuleStrategy rule : appointmentRules) {
                    if (!rule.isValid(updated, list)) {
                        return false;
                    }
                }

                a.setDate(newDate);
                a.setTime(newTime);
                a.setDuration(newDuration);

                saveAppointments(list);

                System.out.println("Appointment updated successfully!");
                return true;
            }
        }

        System.out.println("Appointment not found!");
        return false;
    }

    public boolean cancelAppointment(int id, String username) {
        List<Appointment> list = loadAppointments();

        for (Appointment a : list) {
            if (a.getId() == id && username.equals(a.getBookedBy())) {

                LocalDateTime appointmentDateTime = LocalDateTime.of(
                        LocalDate.parse(a.getDate()),
                        LocalTime.parse(a.getTime())
                );

                Duration diff = Duration.between(LocalDateTime.now(), appointmentDateTime);

                if (a.getStatus() == Status.LATE) {
                    System.out.println("Cannot cancel expired appointment!");
                    return false;
                }

                if (diff.toHours() < 24) {
                    System.out.println("Cannot cancel less than 24 hours before appointment!");
                    return false;
                }

                a.setCurrentParticipants(0);
                a.setBookedBy("None");
                a.setStatus(Status.AVAILABLE);

                saveAppointments(list);

                System.out.println("Appointment cancelled successfully!");
                return true;
            }
        }

        System.out.println("Appointment not found!");
        return false;
    }

    public boolean editParticipants(int id, String username, int newCount) {
        List<Appointment> list = loadAppointments();

        for (Appointment a : list) {
            if (a.getId() == id) {

                if (!username.equals(a.getBookedBy())) {
                    System.out.println("You can only edit your own appointments!");
                    return false;
                }

                LocalDateTime appointmentDateTime = LocalDateTime.of(
                        LocalDate.parse(a.getDate()),
                        LocalTime.parse(a.getTime())
                );

                if (appointmentDateTime.isBefore(LocalDateTime.now().plusHours(24))) {
                    System.out.println("Cannot edit less than 24 hours before appointment!");
                    return false;
                }

                ServiceType type = a.getServiceType();

                if (newCount < type.getMinParticipants() || newCount > type.getMaxParticipants()) {
                    System.out.println("Invalid number! Allowed: "
                            + type.getMinParticipants() + "-"
                            + type.getMaxParticipants());
                    return false;
                }

                a.setCurrentParticipants(newCount);
                saveAppointments(list);

                System.out.println("Participants updated successfully!");
                return true;
            }
        }

        System.out.println("Appointment ID not found!");
        return false;
    }

    private void updateLateAppointments(List<Appointment> list) {
        LocalDateTime now = LocalDateTime.now();

        for (Appointment a : list) {
            LocalDateTime appointmentTime = LocalDateTime.of(
                    LocalDate.parse(a.getDate()),
                    LocalTime.parse(a.getTime())
            );

            if (appointmentTime.isBefore(now) && a.getStatus() != Status.LATE) {
                a.setStatus(Status.LATE);
            }
        }
    }

    public void viewStatistics() {
        List<Appointment> list = loadAppointments();

        int totalAppointments = list.size();
        int totalBooked = 0;

        Map<ServiceType, Integer> serviceCount = new HashMap<>();

        for (Appointment a : list) {
            if (a.getStatus() == Status.CONFIRMED) {
                totalBooked++;
                serviceCount.put(
                        a.getServiceType(),
                        serviceCount.getOrDefault(a.getServiceType(), 0) + 1
                );
            }
        }

        ServiceType mostPopular = null;
        int max = 0;

        for (var entry : serviceCount.entrySet()) {
            if (entry.getValue() > max) {
                max = entry.getValue();
                mostPopular = entry.getKey();
            }
        }

        System.out.println("\n--- Statistics ---");
        System.out.println("Total Appointments: " + totalAppointments);
        System.out.println("Total Booked: " + totalBooked);

        if (mostPopular != null) {
            System.out.println("Most Popular Service: " + mostPopular.getLabel());
        } else {
            System.out.println("Most Popular Service: None");
        }
    }

    public boolean canEditAppointment(int id) {
        List<Appointment> list = loadAppointments();

        for (Appointment a : list) {
            if (a.getId() == id) {
                if (a.getCurrentParticipants() > 0) {
                    System.out.println("Cannot edit booked appointment!");
                    return false;
                }

                if (a.getStatus() == Status.LATE) {
                    System.out.println("Cannot edit expired appointment!");
                    return false;
                }

                return true;
            }
        }

        System.out.println("Appointment not found!");
        return false;
    }

    public boolean deleteAppointmentByAdmin(int id) {
        List<Appointment> list = loadAppointments();

        for (Appointment a : list) {
            if (a.getId() == id) {
                if (a.getStatus() == Appointment.Status.AVAILABLE
                        || a.getStatus() == Appointment.Status.LATE) {
                    list.remove(a);
                    saveAppointments(list);
                    System.out.println("Appointment deleted successfully!");
                    return true;
                }

                System.out.println("Only AVAILABLE or LATE appointments can be deleted!");
                return false;
            }
        }

        System.out.println("Appointment not found!");
        return false;
    }

    public boolean cancelAppointmentByAdmin(int id) {
        List<Appointment> list = loadAppointments();

        for (Appointment a : list) {
            if (a.getId() == id) {

                if (a.getStatus() != Appointment.Status.CONFIRMED) {
                    System.out.println("Only CONFIRMED appointments can be cancelled!");
                    return false;
                }

                LocalDateTime appointmentTime = LocalDateTime.of(
                        LocalDate.parse(a.getDate()),
                        LocalTime.parse(a.getTime())
                );

                if (Duration.between(LocalDateTime.now(), appointmentTime).toHours() < 24) {
                    System.out.println("Cannot cancel less than 24 hours before appointment!");
                    return false;
                }
                EmailService emailService = new EmailService("s12324672@stu.najah.edu", "oapr atjl gdsf ijkh");
                String username = getAppointmentById(id).getBookedBy();
                User bookedUser = UserRepository.findByUsername(username);

                if (bookedUser != null) {
                    String userEmail = bookedUser.getEmail();
                    String subject = "Appointment Cancelled";
                    String body = "We apologize, your appointment on " +
                                  getAppointmentById(id).getDate() + " at " +
                                  getAppointmentById(id).getTime() + " has been cancelled.";
                    emailService.sendEmail(userEmail, subject, body);
                    }
                a.setCurrentParticipants(0);
                a.setBookedBy("None");
                a.setStatus(Status.AVAILABLE);

                saveAppointments(list);

                System.out.println("Appointment cancelled successfully!");
                return true;
            }
        }

        System.out.println("Appointment not found!");
        return false;
    }

    public void viewDeletableAppointments() {
        List<Appointment> list = loadAppointments();

        System.out.println("\n--- Deletable Appointments ---");
        System.out.println("ID | Date | Time | Service | Duration | P | Status | Booked By");
        System.out.println("---------------------------------------------------------------");

        for (Appointment a : list) {
            if (a.getStatus() == Appointment.Status.AVAILABLE
                    || a.getStatus() == Appointment.Status.LATE) {
                System.out.println(a);
            }
        }
    }

    public void viewCancelableAppointments() {
        List<Appointment> list = loadAppointments();

        System.out.println("\n--- Confirmed Appointments ---");
        System.out.println("ID | Date | Time | Service | Duration | P | Status | Booked By");
        System.out.println("---------------------------------------------------------------");

        for (Appointment a : list) {
            if (a.getStatus() == Appointment.Status.CONFIRMED) {
                System.out.println(a);
            }
        }
    }

    private void removeOldLateAppointments(List<Appointment> list) {
        LocalDateTime now = LocalDateTime.now();
        Iterator<Appointment> iterator = list.iterator();

        while (iterator.hasNext()) {
            Appointment a = iterator.next();

            if (a.getStatus() == Appointment.Status.LATE) {
                LocalDateTime appointmentTime = LocalDateTime.of(
                        LocalDate.parse(a.getDate()),
                        LocalTime.parse(a.getTime())
                );

                long daysPassed = Duration.between(appointmentTime, now).toDays();

                if (daysPassed >= 15) {
                    iterator.remove();
                }
            }
        }
    }

    public List<Appointment> loadAppointments() {
        List<String> lines = FileUtil.read(FILE);
        List<Appointment> list = new ArrayList<>();

        for (String l : lines) {
            list.add(Appointment.fromFileString(l));
        }

        updateLateAppointments(list);
        removeOldLateAppointments(list);

        list.sort(Comparator
                .comparing(Appointment::getDate)
                .thenComparing(Appointment::getTime));

        return list;
    }

    private void saveAppointments(List<Appointment> list) {
        List<String> lines = new ArrayList<>();

        for (Appointment a : list) {
            lines.add(a.toFileString());
        }

        FileUtil.write(FILE, lines);
    }
    public void sendReminders() {
        List<Appointment> list = loadAppointments();
        EmailService emailService = new EmailService();
        LocalDateTime now = LocalDateTime.now();

        for (Appointment a : list) {
            if (a.getStatus() == Appointment.Status.CONFIRMED) {
                LocalDateTime appointmentDateTime = LocalDateTime.of(
                        LocalDate.parse(a.getDate()),
                        LocalTime.parse(a.getTime())
                );

                Duration diff = Duration.between(now, appointmentDateTime);

                if (diff.toHours() <= 24 && diff.toHours() > 0) {
                    String username = a.getBookedBy();
                    User bookedUser = UserRepository.findByUsername(username);

                    if (bookedUser != null) {
                        String userEmail = bookedUser.getEmail();
                        String subject = "Appointment Reminder";
                        String body = "Dear " + username + ",\n\n"
                                + "This is a reminder for your appointment on "
                                + a.getDate() + " at " + a.getTime() + ".\n\n"
                                + "Best regards,\nAppointment System";

                        emailService.sendEmail(userEmail, subject, body);
                    }
                }
            }
        }
    }

}
