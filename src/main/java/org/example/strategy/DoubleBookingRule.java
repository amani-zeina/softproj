package org.example.strategy;

import org.example.domain.Appointment;
import java.util.List;

public class DoubleBookingRule implements BookingRuleStrategy {

    @Override
    public boolean isValid(Appointment appointment,String username, int participantsCount, List<Appointment> allAppointments) {

        for (Appointment other : allAppointments) {

            if (username.equals(other.getBookedBy()) &&
                other.getDate().equals(appointment.getDate()) &&
                other.getTime().equals(appointment.getTime())) {

                System.out.println("You already have another appointment at this time!");
                return false;
            }
        }

        return true;
    }
}