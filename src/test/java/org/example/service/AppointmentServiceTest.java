package org.example.service;

import org.example.domain.Appointment;
import org.example.domain.ServiceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class AppointmentServiceTest {
	

    @Test
    void serviceShouldBeCreated() {
        AppointmentService service = new AppointmentService();
        assertNotNull(service);
    }

    @Test
    void addAppointmentShouldRunWithoutException() {
        AppointmentService service = new AppointmentService();

        assertDoesNotThrow(() ->
                service.addAppointment(
                        "2030-05-10",
                        "10:00",
                        60,
                        4,
                        ServiceType.GROUP
                )
        );
    }

    @Test
    void addAppointmentOutsideWorkingHoursShouldRunWithoutException() {
        AppointmentService service = new AppointmentService();

        assertDoesNotThrow(() ->
                service.addAppointment(
                        "2030-05-10",
                        "07:00",
                        60,
                        4,
                        ServiceType.GROUP
                )
        );
    }

    @Test
    void getAppointmentByIdShouldReturnNullForInvalidId() {
        AppointmentService service = new AppointmentService();
        Appointment result = service.getAppointmentById(9999);
        assertNull(result);
    }

    @Test
    void bookAppointmentShouldReturnFalseForInvalidId() {
        AppointmentService service = new AppointmentService();
        boolean result = service.bookAppointment(9999, "user", 1);
        assertFalse(result);
    }

    @Test
    void validateAppointmentShouldReturnFalseForNullAppointment() {
        AppointmentService service = new AppointmentService();
        boolean result = service.validateAppointment(null, "user");
        assertFalse(result);
    }

    @Test
    void cancelAppointmentShouldReturnFalseForInvalidId() {
        AppointmentService service = new AppointmentService();
        boolean result = service.cancelAppointment(9999, "user");
        assertFalse(result);
    }

    @Test
    void editParticipantsShouldReturnFalseForInvalidId() {
        AppointmentService service = new AppointmentService();
        boolean result = service.editParticipants(9999, "user", 2);
        assertFalse(result);
    }

    @Test
    void editAppointmentShouldReturnFalseForInvalidId() {
        AppointmentService service = new AppointmentService();
        boolean result = service.editAppointment(9999, "2030-05-10", "11:00", 60);
        assertFalse(result);
    }

    @Test
    void canEditAppointmentShouldReturnFalseForInvalidId() {
        AppointmentService service = new AppointmentService();
        boolean result = service.canEditAppointment(9999);
        assertFalse(result);
    }

    @Test
    void deleteAppointmentByAdminShouldReturnFalseForInvalidId() {
        AppointmentService service = new AppointmentService();
        boolean result = service.deleteAppointmentByAdmin(9999);
        assertFalse(result);
    }

    @Test
    void cancelAppointmentByAdminShouldReturnFalseForInvalidId() {
        AppointmentService service = new AppointmentService();
        boolean result = service.cancelAppointmentByAdmin(9999);
        assertFalse(result);
    }

    @Test
    void viewMethodsShouldNotThrowExceptions() {
        AppointmentService service = new AppointmentService();

        assertDoesNotThrow(service::viewAvailableSlots);
        assertDoesNotThrow(service::viewAllAppointments);
        assertDoesNotThrow(service::viewStatistics);
        assertDoesNotThrow(service::viewDeletableAppointments);
        assertDoesNotThrow(service::viewCancelableAppointments);
        assertDoesNotThrow(() -> service.viewUserAppointments("user"));
    }

    @Test
    void loadAppointmentsShouldReturnList() {
        AppointmentService service = new AppointmentService();
        assertNotNull(service.loadAppointments());
    }
    @Test
    void updateLateAppointmentsShouldMarkOldAppointmentAsLate() {
        AppointmentService service = new AppointmentService();

        String pastDate = LocalDate.now().minusDays(10).toString(); // قديم بس مش منمسوح
        service.addAppointment(pastDate, "09:00", 60, 2, ServiceType.GROUP);

        List<Appointment> appointments = service.loadAppointments();

        Appointment oldAppointment = appointments.stream()
                .filter(a -> a.getDate().equals(pastDate))
                .findFirst()
                .orElse(null);

        assertNotNull(oldAppointment, "Appointment should exist in the list");
        assertEquals(Appointment.Status.LATE, oldAppointment.getStatus());
    }

    @Test
    void removeOldLateAppointmentsShouldDeleteExpiredAppointments() {
        AppointmentService service = new AppointmentService();

        String veryOldDate = LocalDate.now().minusDays(20).toString();
        service.addAppointment(veryOldDate, "10:00", 60, 2, ServiceType.ASSESSMENT);

        List<Appointment> appointments = service.loadAppointments();

        boolean exists = appointments.stream()
                .anyMatch(a -> a.getDate().equals(veryOldDate));

        assertFalse(exists);
    }

    @Test
    void sendRemindersShouldRunWithoutException() {
        AppointmentService service = new AppointmentService();

        String upcomingDate = LocalDate.now().toString();
        String upcomingTime = LocalTime.now().plusHours(12).format(DateTimeFormatter.ofPattern("HH:mm"));
        service.addAppointment(upcomingDate, upcomingTime, 60, 2, ServiceType.GROUP);

        int id = service.loadAppointments().get(0).getId();
        service.bookAppointment(id, "user_reminder", 1);

        assertDoesNotThrow(service::sendReminders);
    }


}
