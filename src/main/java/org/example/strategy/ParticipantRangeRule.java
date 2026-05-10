package org.example.strategy;

import org.example.domain.Appointment;
import org.example.domain.ServiceType;
import java.util.List;

public class ParticipantRangeRule implements BookingRuleStrategy {

    @Override
    public boolean isValid(Appointment appointment, String username, int participantsCount, List<Appointment> allAppointments) {

        ServiceType type = appointment.getServiceType();

        if (participantsCount < type.getMinParticipants() ||
            participantsCount > type.getMaxParticipants()) {

            System.out.println("Invalid number of participants! Allowed: "
                    + type.getMinParticipants() + "-"
                    + type.getMaxParticipants());
            return false;
        }

        return true;
    }
}
