package org.example.strategy;

import org.example.domain.Appointment;
import java.util.List;

public interface AppointmentRuleStrategy {
    boolean isValid(Appointment appointment, List<Appointment> allAppointments);
}
