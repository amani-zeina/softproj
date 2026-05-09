package org.example.strategy;

import org.example.domain.Appointment;
import org.example.domain.Appointment.Status;
import org.example.domain.ServiceType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class StatusRuleTest {

    @Test
    void isValidShouldReturnFalseWhenAppointmentIsNull() {
        StatusRule rule = new StatusRule();

        boolean result = rule.isValid(null, "user", 1, new ArrayList<>());

        assertFalse(result);
    }

    @Test
    void isValidShouldReturnFalseWhenAppointmentIsLate() {
        StatusRule rule = new StatusRule();

        Appointment appointment = new Appointment(
                1,
                "2026-05-10",
                "10:00",
                60,
                4,
                0,
                ServiceType.Upgrades,
                Status.LATE,
                "None"
        );

        boolean result = rule.isValid(appointment, "user", 1, new ArrayList<>());

        assertFalse(result);
    }

    @Test
    void isValidShouldReturnFalseWhenAppointmentIsNotAvailable() {
        StatusRule rule = new StatusRule();

        Appointment appointment = new Appointment(
                2,
                "2026-05-10",
                "10:00",
                60,
                4,
                0,
                ServiceType.Upgrades,
                Status.CONFIRMED,
                "None"
        );

        boolean result = rule.isValid(appointment, "user", 1, new ArrayList<>());

        assertFalse(result);
    }

    @Test
    void isValidShouldReturnTrueWhenAppointmentIsAvailable() {
        StatusRule rule = new StatusRule();

        Appointment appointment = new Appointment(
                3,
                "2026-05-10",
                "10:00",
                60,
                4,
                0,
                ServiceType.Upgrades,
                Status.AVAILABLE,
                "None"
        );

        boolean result = rule.isValid(appointment, "user", 1, new ArrayList<>());

        assertTrue(result);
    }
}