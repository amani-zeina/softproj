package org.example.service;

import org.example.domain.ServiceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentServiceBranchTest {

    @Test
    void fullServiceFlow() {

        AppointmentService service = new AppointmentService();

        assertDoesNotThrow(() ->
                service.addAppointment(
                        "2031-01-10",
                        "09:00",
                        60,
                        3,
                        ServiceType.Upgrades
                )
        );

        assertDoesNotThrow(service::viewAvailableSlots);
        assertDoesNotThrow(service::viewAllAppointments);
        assertDoesNotThrow(service::viewStatistics);
    }

    @Test
    void adminEdgeCases() {

        AppointmentService service = new AppointmentService();

        assertFalse(service.cancelAppointmentByAdmin(-1));
        assertFalse(service.deleteAppointmentByAdmin(-1));

        assertDoesNotThrow(service::viewCancelableAppointments);
        assertDoesNotThrow(service::viewDeletableAppointments);
    }

    @Test
    void userEdgeCases() {

        AppointmentService service = new AppointmentService();

        assertFalse(service.cancelAppointment(999, "test"));
        assertFalse(service.editParticipants(999, "test", 2));
        assertFalse(service.canEditAppointment(999));
    }
}