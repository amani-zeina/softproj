package org.example.strategy;

import org.example.domain.Appointment;
import org.example.domain.Appointment.Status;
import org.example.domain.ServiceType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class WorkingHoursRuleTest {

    @Test
    void isValidShouldReturnTrueWhenInsideWorkingHours() {

        WorkingHoursRule rule = new WorkingHoursRule();

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

        boolean result = rule.isValid(appointment, new ArrayList<>());

        assertTrue(result);
    }

    @Test
    void isValidShouldReturnFalseWhenBeforeWorkingHours() {

        WorkingHoursRule rule = new WorkingHoursRule();

        Appointment appointment = new Appointment(
                2,
                "2026-05-10",
                "07:00",
                60,
                4,
                0,
                ServiceType.Upgrades,
                Status.AVAILABLE,
                "None"
        );

        boolean result = rule.isValid(appointment, new ArrayList<>());

        assertFalse(result);
    }

    @Test
    void isValidShouldReturnFalseWhenAfterWorkingHours() {

        WorkingHoursRule rule = new WorkingHoursRule();

        Appointment appointment = new Appointment(
                3,
                "2026-05-10",
                "15:30",
                60,
                4,
                0,
                ServiceType.Upgrades,
                Status.AVAILABLE,
                "None"
        );

        boolean result = rule.isValid(appointment, new ArrayList<>());

        assertFalse(result);
    }
}