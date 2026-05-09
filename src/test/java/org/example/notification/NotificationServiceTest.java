package org.example.notification;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class NotificationServiceTest {

    @Mock
    private Observer observer;

    @Test
    void shouldNotifyUser() {

        NotificationService service = new NotificationService();

        service.addObserver(observer);

        service.notifyAllUsers("testUser", "Appointment reminder");

        verify(observer).notifyUser("testUser", "Appointment reminder");
    }
}