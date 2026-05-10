package org.example.strategy;

import org.example.domain.Appointment;
import java.util.List;

public interface BookingRuleStrategy {
    boolean isValid(Appointment appointment,String username,int participantsCount,List<Appointment> allAppointments);
}
