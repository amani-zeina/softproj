package org.example.strategy;

import org.example.domain.Appointment;
import org.example.domain.Appointment.Status;
import org.example.domain.ServiceType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class ParticipantRangeRuleTest {

    @Test
    void isValidShouldReturnFalseWhenParticipantsBelowMinimum() {
        ParticipantRangeRule rule = new ParticipantRangeRule();

        Appointment appointment = new Appointment(
                1,
                "2026-05-10",
                "10:00",
                60,
                4,
                0,
                ServiceType.Upgrades,
                Status.AVAILABLE,
                "None"
        );

        boolean result = rule.isValid(appointment, "user", 0, new ArrayList<>());

        assertFalse(result);
    }

    @Test
    void isValidShouldReturnFalseWhenParticipantsAboveMaximum() {
        ParticipantRangeRule rule = new ParticipantRangeRule();

        Appointment appointment = new Appointment(
                2,
                "2026-05-10",
                "10:00",
                60,
                4,
                0,
                ServiceType.Upgrades,
                Status.AVAILABLE,
                "None"
        );

        boolean result = rule.isValid(appointment, "user", 5, new ArrayList<>());

        assertFalse(result);
    }

    @Test
    void isValidShouldReturnTrueWhenParticipantsWithinRange() {
        ParticipantRangeRule rule = new ParticipantRangeRule();

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

        boolean result = rule.isValid(appointment, "user", 2, new ArrayList<>());

        assertTrue(result);
    }
}