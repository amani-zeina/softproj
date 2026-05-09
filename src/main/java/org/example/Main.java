package org.example;


import org.example.service.*;
import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.example.service.*;
import org.example.domain.ServiceType;
import org.example.domain.User;
import org.example.repository.UserRepository;

import java.time.*;
import java.time.format.*;
import org.example.domain.Appointment;

public class Main {

    private static final Scanner sc = new Scanner(System.in);
    private static final AuthenticationService authService = new AuthenticationService();
    private static final UserService userService = new UserService();
    private static final AppointmentService appointmentService = new AppointmentService();

 public static void main(String[] args) {

    	ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    	scheduler.scheduleAtFixedRate(() -> appointmentService.sendReminders(),
    			0, 24, TimeUnit.HOURS);
        while (true) {
            System.out.println("\n===== Appointment Scheduling System =====");
            System.out.println("1. Administrator");
            System.out.println("2. User");
            System.out.println("3. Exit");
            System.out.print("Choose: ");

            int choice = readIntInRange(1, 3);

            switch (choice) {
                case 1 -> adminLoginMenu();
                case 2 -> userMenu();
                case 3 -> {
                    System.out.println("Goodbye!");
                    return;
                }
            }
          }
    }

    
    private static int readIntInRange(int min, int max) {
        while (true) {
            try {
                int value = Integer.parseInt(sc.nextLine());
                if (value >= min && value <= max)
                    return value;
            } catch (Exception ignored) {}

            System.out.print("Enter a number between " + min + " and " + max + ": ");
        }
    }

    private static void adminLoginMenu() {
        System.out.print("Username: ");
        String u = sc.nextLine();

        System.out.print("Password: ");
        String p = sc.nextLine();

        if (authService.loginAdmin(u, p)) {
            System.out.println("Login successful!");
            adminDashboard();
        } else {
            System.out.println("Invalid credentials!");
        }
    }

    private static void adminDashboard() {

        while (authService.isAdminLoggedIn()) {

            System.out.println("\n--- Admin Dashboard ---");
            System.out.println("1. View appointments");
            System.out.println("2. Add appointment");
            System.out.println("3. View Statistics");
            System.out.println("4. Edit Appointment");
            System.out.println("5. Delete Appointment");
            System.out.println("6. Cancel Confirmed Appointment");
            System.out.println("7. Logout");
            System.out.print("Choose: ");

            int c = readIntInRange(1, 7);

            switch (c) {
                case 1 -> appointmentService.viewAllAppointmentsForAdmin();
                case 2 -> addAppointmentMenu();
                case 3 -> appointmentService.viewStatistics();
                case 4 -> editAppointmentMenu();
                case 5 -> deleteAppointmentMenu();
                case 6 -> cancelByAdminMenu();
                case 7 -> {
                    authService.logoutAdmin();
                    System.out.println("Logged out successfully!");
                }
            }
        }
    }
    
    private static void cancelByAdminMenu() {

    	appointmentService.viewCancelableAppointments();

        System.out.print("Enter ID to cancel: ");
        int id = readIntInRange(1, 10000);

        appointmentService.cancelAppointmentByAdmin(id);
    }
    
    private static void deleteAppointmentMenu() {

    	appointmentService.viewDeletableAppointments();

        System.out.print("Enter ID to delete: ");
        int id = readIntInRange(1, 10000);

        appointmentService.deleteAppointmentByAdmin(id);
    }

    private static void editAppointmentMenu() {

        appointmentService.viewAllAppointmentsForAdmin();

        System.out.print("Enter ID to edit: ");
        int id = readIntInRange(1, 10000);
        
        if (!appointmentService.canEditAppointment(id)) {
        	
        }else {
        Appointment selected = appointmentService.getAppointmentById(id);

        if (selected == null) {
            System.out.println("Appointment not found!");
            return;
        }
        
        ServiceType type = selected.getServiceType();
        System.out.println("Editing appointment for service: " + type.getLabel());
        System.out.println("Allowed duration: " + type.getMinDuration() + " - " + type.getMaxDuration() + " minutes");

        String date = readValidDate();
        String time = readValidTime();
        int duration = readDuration(selected.getServiceType(), time);

        appointmentService.editAppointment(id, date, time, duration);
        }
    }

    private static void userMenu() {

        while (true) {

            System.out.println("\n--- User Menu ---");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Back");
            System.out.print("Choose: ");

            int c = readIntInRange(1, 3);

            switch (c) {
                case 1 -> registerUser();
                case 2 -> loginUser();
                case 3 -> { return; }
            }
        }
    }

    private static void registerUser() {

        System.out.print("Username: ");
        String u = sc.nextLine();

        System.out.print("Password: ");
        String p = sc.nextLine();
        
        System.out.print("Email: ");
        String e = sc.nextLine();

        if (userService.register(u, p,e)) {
            System.out.println("Registration successful!");
            System.out.println("Please login to continue.\n");
            loginUser();
        } else {
            System.out.println("Username already exists!");
        }
    }

    private static void loginUser() {

        System.out.print("Username: ");
        String u = sc.nextLine();

        System.out.print("Password: ");
        String p = sc.nextLine();

        if (userService.login(u, p)) {
            System.out.println("Login successful!");
            userDashboard();
        } else {
            System.out.println("Invalid credentials!");
        }
    }

    private static void userDashboard() {

        while (userService.isLoggedIn()) {

            System.out.println("\n--- User Dashboard ---");
            System.out.println("1. View available slots");
            System.out.println("2. Book appointment");
            System.out.println("3. My Appointments");
            System.out.println("4. Cancel Appointment");
            System.out.println("5. Edit Participants");
            System.out.println("6. Logout");
            System.out.println("7. Back");
            System.out.print("Choose: ");

            int c = readIntInRange(1, 7);

            switch (c) {
                case 1 -> appointmentService.viewAvailableSlots();
                case 2 -> bookMenu();
                case 3 -> appointmentService.viewUserAppointments(
                        userService.getCurrentUser().getUsername());
                case 4 -> cancelMenu();
                case 5 -> editParticipantsMenu();
                case 6 -> {userService.logout(); System.out.println("Logged out!");}
                case 7 -> { return; }
                default -> System.out.println("Invalid choice");
            }
        }
    }
    
    private static void bookMenu() {

        appointmentService.viewAvailableSlots();

        System.out.print("Enter Appointment ID: ");
        int id = readIntInRange(1, 10000);
        Appointment selectedAppointment = appointmentService.getAppointmentById(id);
        if (!appointmentService.validateAppointment(selectedAppointment, userService.getCurrentUser().getUsername())) {
            return;
        }
        ServiceType type = selectedAppointment.getServiceType();
        while (true) {

            int participants;

            while (true) {
                System.out.print("How many people will come with you (" + type.getMinParticipants() + "-" + type.getMaxParticipants() + ")? ");
                try {
                    participants = Integer.parseInt(sc.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Please enter a valid number!");
                }
            }

            if (appointmentService.bookAppointment(id, userService.getCurrentUser().getUsername(), participants)) {

                System.out.println("Appointment booked successfully!");
                EmailService emailService = new EmailService("s12324672@stu.najah.edu", "oapr atjl gdsf ijkh");
                String userEmail = userService.getCurrentUser().getEmail();
                String subject = "Appointment Confirmation";
                String body = "Your appointment is booked on " + selectedAppointment.getDate() +
                              " at " + selectedAppointment.getTime();
                emailService.sendEmail(userEmail, subject, body);
                break; 
            }
        }
    }

    private static void cancelMenu() {

        appointmentService.viewUserAppointments(
                userService.getCurrentUser().getUsername());

        System.out.print("Enter ID to cancel: ");
        int id = readIntInRange(1, 10000);

        appointmentService.cancelAppointment(id, userService.getCurrentUser().getUsername());
    }

    private static void editParticipantsMenu() {

        appointmentService.viewUserAppointments(userService.getCurrentUser().getUsername());

        System.out.print("Enter appointment ID to edit participants: ");
        int id = readIntInRange(1, 10000);

        while (true) {

            int participants;

            while (true) {
                System.out.print("New number of participants: ");
                try {
                    participants = Integer.parseInt(sc.nextLine());
                    break;
                } catch (Exception e) {
                    System.out.println("Please enter a valid number!");
                }
            }

            if (appointmentService.editParticipants(id, userService.getCurrentUser().getUsername(), participants)) {

                System.out.println("Participants updated successfully!");
                break;
            }
        }
      
    }
    
    private static void addAppointmentMenu() {

    ServiceType.printOptions();
    System.out.print("Choose service type: ");
    int c = readIntInRange(1, ServiceType.values().length);

    ServiceType type = ServiceType.fromChoice(c);

    String date = readValidDate();
    String time = readValidTime();
    int duration = readDuration(type, time);
    int max=type.getMaxParticipants();
    
    if (appointmentService.addAppointment(date, time, duration, max, type))
        System.out.println("Appointment added successfully!");

    }

    private static String readValidDate() {

        DateTimeFormatter formatter =
                DateTimeFormatter.ofPattern("yyyy-M-d");

        while (true) {
            try {
                System.out.print("Date (YYYY-MM-DD): ");
                LocalDate date =
                        LocalDate.parse(sc.nextLine(), formatter);

                if (date.isBefore(LocalDate.now())) {
                    System.out.println("Date cannot be in the past!");
                    continue;
                }

                return date.format(DateTimeFormatter.ISO_LOCAL_DATE);

            } catch (Exception e) {
                System.out.println("Invalid date format!");
            }
        }
    }

    private static String readValidTime() {

      while (true) {
      System.out.print("Time HH:MM: ");
      String input = sc.nextLine().trim();

      try {

          LocalTime time;
          
          if (input.matches("\\d{1,2}")) {

              int hour = Integer.parseInt(input);

              if (hour < 0 || hour > 23) {
                  System.out.println("Hour must be between 0 and 23");
                  continue;
              }

              time = LocalTime.of(hour, 0);
          }
          else if (input.matches("\\d{1,2}:\\d{1,2}")) {

              String[] parts = input.split(":");
              int hour = Integer.parseInt(parts[0]);
              int minute = Integer.parseInt(parts[1]);

              if (hour < 0 || hour > 23 ||
                  minute < 0 || minute > 59) {

                  System.out.println("Invalid hour or minute!");
                  continue;
              }

              time = LocalTime.of(hour, minute);
          }

          else {
              System.out.println("Invalid format! Example: 8 or 08 or 8:00");
              continue;
          }
          return time.format(DateTimeFormatter.ofPattern("HH:mm"));

      } catch (Exception e) {
          System.out.println("Invalid time input!");
      }
  }
    }

    private static int readDuration(ServiceType type, String time) {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime start = LocalTime.parse(time, timeFormatter);
        LocalTime closingTime = LocalTime.of(16, 0);

        while (true) {
            System.out.print("Duration (" + type.getMinDuration() + "-" +
                    type.getMaxDuration() + " min): ");

            int duration = readIntInRange(type.getMinDuration(), type.getMaxDuration());

            
            LocalTime end = start.plusMinutes(duration);

            if (end.isAfter(closingTime)) {
                System.out.println(
                    "Invalid duration! This appointment will end at " + 
                    end + " which is after 16:00."
                );
                continue;
            }

            return duration;
        }
    }
}
