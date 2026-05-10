package org.example.strategy;

import org.example.domain.Appointment;
import org.example.domain.Appointment.Status;
import org.example.domain.ServiceType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DoubleBookingRuleTest {

    @Test
    void isValidShouldReturnFalseWhenUserAlreadyBookedSameDateAndTime() {
        DoubleBookingRule rule = new DoubleBookingRule();

        Appointment existing = new Appointment(
                1,
                "2026-05-10",
                "10:00",
                60,
                4,
                1,
                ServiceType.GROUP,
                Status.CONFIRMED,
                "zeina"
        );

        Appointment appointment = new Appointment(
                2,
                "2026-05-10",
                "10:00",
                60,
                4,
                0,
                ServiceType.URGENT,
                Status.AVAILABLE,
                "None"
        );

        List<Appointment> allAppointments = new ArrayList<>();
        allAppointments.add(existing);

        boolean result = rule.isValid(appointment, "zeina", 1, allAppointments);

        assertFalse(result);
    }

    @Test
    void isValidShouldReturnTrueWhenNoDoubleBookingExists() {
        DoubleBookingRule rule = new DoubleBookingRule();

        Appointment existing = new Appointment(
                1,
                "2026-05-10",
                "10:00",
                60,
                4,
                1,
                ServiceType.GROUP,
                Status.CONFIRMED,
                "ahmad"
        );

        Appointment appointment = new Appointment(
                2,
                "2026-05-10",
                "11:00",
                60,
                4,
                0,
                ServiceType.URGENT,
                Status.AVAILABLE,
                "None"
        );

        List<Appointment> allAppointments = new ArrayList<>();
        allAppointments.add(existing);

        boolean result = rule.isValid(appointment, "zeina", 1, allAppointments);

        assertTrue(result);
    }
}
