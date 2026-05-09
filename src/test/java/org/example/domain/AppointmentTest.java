package org.example.domain;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AppointmentTest {

    @Test
    void appointmentShouldBeCreated() {

        Appointment a = new Appointment(
                1,
                "2030-05-10",
                "10:00",
                60,
                4,
                0,
                ServiceType.GROUP,
                Appointment.Status.AVAILABLE,
                "None"
        );

        assertNotNull(a);
    }

    @Test
    void gettersShouldWork() {

        Appointment a = new Appointment(
                1,
                "2030-05-10",
                "10:00",
                60,
                4,
                0,
                ServiceType.GROUP,
                Appointment.Status.AVAILABLE,
                "None"
        );

        assertEquals(1, a.getId());
        assertEquals("2030-05-10", a.getDate());
        assertEquals("10:00", a.getTime());
        assertEquals(60, a.getDuration());
        assertEquals(4, a.getMaxParticipants());
        assertEquals(0, a.getCurrentParticipants());
        assertEquals(ServiceType.GROUP, a.getServiceType());
        assertEquals(Appointment.Status.AVAILABLE, a.getStatus());
        assertEquals("None", a.getBookedBy());
    }
}
