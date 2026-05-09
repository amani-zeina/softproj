package org.example.service;

import org.example.domain.ServiceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentServiceCoverageJumpTest {

    @Test
    void multipleAppointmentsFlow() {

        AppointmentService service = new AppointmentService();

        assertDoesNotThrow(() ->
                service.addAppointment("2031-02-01","09:00",60,2,ServiceType.Upgrades)
        );

        assertDoesNotThrow(() ->
                service.addAppointment("2031-02-01","11:00",60,2,ServiceType.Upgrades)
        );

        assertDoesNotThrow(() ->
                service.addAppointment("2031-02-02","10:30",60,3,ServiceType.Full_Inspection)
        );

        assertDoesNotThrow(service::viewAllAppointments);
        assertDoesNotThrow(service::viewAvailableSlots);
        assertDoesNotThrow(service::viewStatistics);
    }

    @Test
    void adminEdgeCases() {

        AppointmentService service = new AppointmentService();

        assertFalse(service.deleteAppointmentByAdmin(-1));
        assertFalse(service.cancelAppointmentByAdmin(-1));

        assertDoesNotThrow(service::viewCancelableAppointments);
        assertDoesNotThrow(service::viewDeletableAppointments);
    }

    @Test
    void userEdgeCases() {

        AppointmentService service = new AppointmentService();

        assertFalse(service.cancelAppointment(999,"user"));
        assertFalse(service.editParticipants(999,"user",1));
        assertFalse(service.canEditAppointment(999));
    }
}