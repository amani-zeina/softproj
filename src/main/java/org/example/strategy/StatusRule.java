package org.example.strategy;

import org.example.domain.Appointment;
import org.example.domain.Appointment.Status;
import java.util.List;

public class StatusRule implements BookingRuleStrategy {

    @Override
    public boolean isValid(Appointment appointment, String username, int participantsCount, List<Appointment> allAppointments) {

        if (appointment == null) {
            System.out.println("Appointment not found!");
            return false;
        }

        if (appointment.getStatus() == Status.LATE) {
            System.out.println("Cannot book expired appointment!");
            return false;
        }

        if (appointment.getStatus() != Status.AVAILABLE) {
            System.out.println("This appointment is no longer available!");
            return false;
        }

        return true;
    }
}