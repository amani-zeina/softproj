package org.example.notification;

import java.util.ArrayList;
import java.util.List;
/**
 * Service responsible for managing appointments.
 * Provides operations for booking, cancelling,
 * validating rules, and listing appointments.
 *
 * This class interacts with appointment storage
 * and applies scheduling rules before confirming bookings.
 *
 * @author Team
 * @version 1.0
 */
public class NotificationService {

    private final List<Observer> Observers = new ArrayList<>();

    public void addObserver(Observer obs) {
        Observers.add(obs);
    }

    public void notifyAllUsers(String username, String message) {
        for (Observer obs : Observers) {
            obs.notifyUser(username, message);
        }
    }
}
