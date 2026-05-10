package org.example.service;

import org.example.domain.Appointment;
import org.example.domain.ServiceType;
import org.example.util.FileUtil;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentServiceSuccessFlowTest {

	private int createFutureAppointment(AppointmentService service, String time, ServiceType type, int maxParticipants) {
    
    String date = LocalDate.now().plusYears(5).toString();

    boolean added = service.addAppointment(date, time, 60, maxParticipants, type);
    assertTrue(added, "Appointment should be added successfully");

    Appointment created = service.loadAppointments().stream()
            .filter(a -> a.getDate().equals(date) && a.getTime().equals(time) && a.getServiceType() == type)
            .max(Comparator.comparingInt(Appointment::getId))
            .orElse(null);
    if (created == null) {
        fail("Expected appointment was not found in the system.");
        return -1; 
    }

    assertNotNull(created);
    return created.getId();
}

    @Test
    void addBookAndCancelByAdminFlowShouldWork() {
    	FileUtil.write("appointments.txt", List.of());
        AppointmentService service = new AppointmentService();

        int id = createFutureAppointment(service, "09:00", ServiceType.GROUP, 4);

        assertTrue(service.bookAppointment(id, "user_admin_cancel", 2));
        assertTrue(service.cancelAppointmentByAdmin(id));
    }

    @Test
    void addEditAndDeleteFlowShouldWork() {
        AppointmentService service = new AppointmentService();

        int id = createFutureAppointment(service, "11:00", ServiceType.ASSESSMENT, 3);

        assertTrue(service.canEditAppointment(id));
        assertTrue(service.editAppointment(id, LocalDate.now().plusYears(5).plusDays(400).toString(), "12:00", 90));
        assertTrue(service.deleteAppointmentByAdmin(id));
    }

    @Test
    void addBookEditParticipantsAndCancelByUserFlowShouldWork() {
        AppointmentService service = new AppointmentService();

        int id = createFutureAppointment(service, "13:00", ServiceType.GROUP, 4);

        assertTrue(service.bookAppointment(id, "user_cancel_flow", 2));
        assertTrue(service.editParticipants(id, "user_cancel_flow", 3));
        assertTrue(service.cancelAppointment(id, "user_cancel_flow"));
    }

    @Test
    void validateBookedAppointmentShouldReturnFalse() {
        AppointmentService service = new AppointmentService();

        int id = createFutureAppointment(service, "15:00", ServiceType.GROUP, 4);

        assertTrue(service.bookAppointment(id, "user_validate", 2));

        Appointment booked = service.getAppointmentById(id);
        assertNotNull(booked);

        assertFalse(service.validateAppointment(booked, "another_user"));
    }
}
