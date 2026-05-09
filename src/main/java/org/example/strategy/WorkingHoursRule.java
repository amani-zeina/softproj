package org.example.strategy;

import org.example.domain.Appointment;
import java.time.LocalTime;
import java.util.List;

public class WorkingHoursRule implements AppointmentRuleStrategy {

    @Override
    public boolean isValid(Appointment appointment, List<Appointment> allAppointments) {

        LocalTime start = LocalTime.parse(appointment.getTime());
        LocalTime end = start.plusMinutes(appointment.getDuration());

        if (start.isBefore(LocalTime.of(8, 0)) ||
            end.isAfter(LocalTime.of(16, 0))) {

            System.out.println("Appointment must be between 08:00 and 16:00");
            return false;
        }

        return true;
    }
}
