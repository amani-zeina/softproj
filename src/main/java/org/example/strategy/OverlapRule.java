package org.example.strategy;

import org.example.domain.Appointment;
import org.example.domain.ServiceType;
import java.time.LocalTime;
import java.util.List;

public class OverlapRule implements AppointmentRuleStrategy {

    @Override
    public boolean isValid(Appointment appointment, List<Appointment> allAppointments) {

        LocalTime newStart = LocalTime.parse(appointment.getTime());
        LocalTime newEnd = newStart.plusMinutes(appointment.getDuration());
        ServiceType type = appointment.getServiceType();

        for (Appointment a : allAppointments) {

            if (!a.getDate().equals(appointment.getDate()))
                continue;

            if (a.getServiceType() != type)
                continue;

            if (a.getId() == appointment.getId())
                continue;

            LocalTime existStart = LocalTime.parse(a.getTime());
            LocalTime existEnd = existStart.plusMinutes(a.getDuration());

            if (newStart.isBefore(existEnd) && newEnd.isAfter(existStart)) {
                System.out.println("Time conflict for same service type!");
                return false;
            }
        }

        return true;
    }
}
