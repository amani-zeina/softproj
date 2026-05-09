package org.example.notification;

public class ConsoleNotificationObserver implements Observer {

    @Override
    public void notifyUser(String username, String message) {
        System.out.println("Notification for " + username + ": " + message);
    }
}