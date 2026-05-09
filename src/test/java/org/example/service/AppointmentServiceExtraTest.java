package org.example.service;

import org.example.domain.ServiceType;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentServiceExtraTest {

    @Test
    void addAppointmentShouldWork() {

        AppointmentService service = new AppointmentService();

        boolean result = service.addAppointment(
                "2030-06-10",
                "11:00",
                60,
                4,
                ServiceType.GROUP
        );

        assertNotNull(result);
    }

    @Test
    void viewAvailableSlotsShouldNotThrow() {

        AppointmentService service = new AppointmentService();

        assertDoesNotThrow(service::viewAvailableSlots);
    }

    @Test
    void viewStatisticsShouldNotThrow() {

        AppointmentService service = new AppointmentService();

        assertDoesNotThrow(service::viewStatistics);
    }
}
