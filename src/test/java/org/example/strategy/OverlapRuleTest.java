package org.example.strategy;

import org.example.domain.Appointment;
import org.example.domain.Appointment.Status;
import org.example.domain.ServiceType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OverlapRuleTest {

    @Test
    void isValidShouldReturnTrueWhenNoOverlap() {
        OverlapRule rule = new OverlapRule();

        Appointment existing = new Appointment(
                1,
                "2026-05-10",
                "10:00",
                60,
                4,
                0,
                ServiceType.GROUP,
                Status.AVAILABLE,
                "None"
        );

        Appointment newAppointment = new Appointment(
                2,
                "2026-05-10",
                "12:00",
                60,
                4,
                0,
                ServiceType.GROUP,
                Status.AVAILABLE,
                "None"
        );

        List<Appointment> appointments = new ArrayList<>();
        appointments.add(existing);

        boolean result = rule.isValid(newAppointment, appointments);

        assertTrue(result);
    }

    @Test
    void isValidShouldReturnFalseWhenOverlapExistsForSameServiceType() {
        OverlapRule rule = new OverlapRule();

        Appointment existing = new Appointment(
                1,
                "2026-05-10",
                "10:00",
                60,
                4,
                0,
                ServiceType.GROUP,
                Status.AVAILABLE,
                "None"
        );

        Appointment newAppointment = new Appointment(
                2,
                "2026-05-10",
                "10:30",
                60,
                4,
                0,
                ServiceType.GROUP,
                Status.AVAILABLE,
                "None"
        );

        List<Appointment> appointments = new ArrayList<>();
        appointments.add(existing);

        boolean result = rule.isValid(newAppointment, appointments);

        assertFalse(result);
    }

    @Test
    void isValidShouldReturnTrueWhenSameTimeButDifferentServiceType() {
        OverlapRule rule = new OverlapRule();

        Appointment existing = new Appointment(
                1,
                "2026-05-10",
                "10:00",
                60,
                4,
                0,
                ServiceType.GROUP,
                Status.AVAILABLE,
                "None"
        );

        Appointment newAppointment = new Appointment(
                2,
                "2026-05-10",
                "10:30",
                60,
                4,
                0,
                ServiceType.URGENT,
                Status.AVAILABLE,
                "None"
        );

        List<Appointment> appointments = new ArrayList<>();
        appointments.add(existing);

        boolean result = rule.isValid(newAppointment, appointments);

        assertTrue(result);
    }

    @Test
    void isValidShouldReturnTrueWhenSameIdIsIgnored() {
        OverlapRule rule = new OverlapRule();

        Appointment existing = new Appointment(
                1,
                "2026-05-10",
                "10:00",
                60,
                4,
                0,
                ServiceType.GROUP,
                Status.AVAILABLE,
                "None"
        );

        Appointment newAppointment = new Appointment(
                1,
                "2026-05-10",
                "10:30",
                60,
                4,
                0,
                ServiceType.GROUP,
                Status.AVAILABLE,
                "None"
        );

        List<Appointment> appointments = new ArrayList<>();
        appointments.add(existing);

        boolean result = rule.isValid(newAppointment, appointments);

        assertTrue(result);
    }
}
