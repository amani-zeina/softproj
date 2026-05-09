package org.example.service;

import org.example.domain.ServiceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentServiceFinalTest {

    @Test
    void addAndViewAppointmentsFlow() {

        AppointmentService service = new AppointmentService();

        assertDoesNotThrow(() ->
                service.addAppointment(
                        "2030-08-10",
                        "10:00",
                        60,
                        4,
                        ServiceType.GROUP
                )
        );

        assertDoesNotThrow(service::viewAvailableSlots);
        assertDoesNotThrow(service::viewAllAppointments);
    }

    @Test
    void adminOperationsShouldNotCrash() {

        AppointmentService service = new AppointmentService();

        assertFalse(service.deleteAppointmentByAdmin(9999));
        assertFalse(service.cancelAppointmentByAdmin(9999));

        assertDoesNotThrow(service::viewStatistics);
        assertDoesNotThrow(service::viewDeletableAppointments);
        assertDoesNotThrow(service::viewCancelableAppointments);
    }

    @Test
    void invalidOperationsShouldReturnFalse() {

        AppointmentService service = new AppointmentService();

        assertFalse(service.canEditAppointment(9999));
        assertFalse(service.cancelAppointment(9999, "user"));
        assertFalse(service.editParticipants(9999, "user", 2));
    }
}
