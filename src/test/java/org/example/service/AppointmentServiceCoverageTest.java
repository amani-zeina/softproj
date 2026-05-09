package org.example.service;

import org.example.domain.ServiceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentServiceCoverageTest {

    @Test
    void addAppointmentShouldNotCrash() {

        AppointmentService service = new AppointmentService();

        assertDoesNotThrow(() ->
                service.addAppointment(
                        "2030-07-01",
                        "10:30",
                        60,
                        4,
                        ServiceType.Upgrades
                )
        );
    }

    @Test
    void viewMethodsShouldNotCrash() {

        AppointmentService service = new AppointmentService();

        assertDoesNotThrow(service::viewAvailableSlots);
        assertDoesNotThrow(service::viewAllAppointments);
        assertDoesNotThrow(service::viewStatistics);
        assertDoesNotThrow(service::viewDeletableAppointments);
        assertDoesNotThrow(service::viewCancelableAppointments);
    }

    @Test
    void invalidOperationsShouldReturnFalse() {

        AppointmentService service = new AppointmentService();

        assertFalse(service.cancelAppointmentByAdmin(9999));
        assertFalse(service.deleteAppointmentByAdmin(9999));
        assertFalse(service.canEditAppointment(9999));
    }
}