package org.example.notification;

import org.junit.jupiter.api.Test;
import java.util.Observable;
import java.util.Observer;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

class ObserverPatternTest {

    @Test
    void observerShouldBeNotifiedWhenBookReturned() {
        // Arrange
        BookInventory inventory = new BookInventory();
        Observer mockUser = mock(Observer.class);

        inventory.addObserver(mockUser);

        // Act
        inventory.bookReturned("Clean Code");

        // Assert
        verify(mockUser, times(1))
                .update(any(Observable.class), eq("Clean Code"));
    }

    @Test
    void multipleObserversShouldAllBeNotified() {
        BookInventory inventory = new BookInventory();
        Observer user1 = mock(Observer.class);
        Observer user2 = mock(Observer.class);

        inventory.addObserver(user1);
        inventory.addObserver(user2);

        inventory.bookReturned("Design Patterns");

        verify(user1).update(any(Observable.class), eq("Design Patterns"));
        verify(user2).update(any(Observable.class), eq("Design Patterns"));
    }
    @Test
    void noObserversShouldNotCauseErrors() {
        BookInventory inventory = new BookInventory();
        assertDoesNotThrow(() -> inventory.bookReturned("Refactoring"));
    }
    @Test
    void singleObserverShouldBeNotified() {
        BookInventory inventory = new BookInventory();
        Observer mockUser = mock(Observer.class);
        inventory.addObserver(mockUser);

        inventory.bookReturned("Effective Java");

        verify(mockUser, times(1)).update(any(Observable.class), eq("Effective Java"));
    }
    @Test
    void multipleObserversReceiveSameNotification() {
        BookInventory inventory = new BookInventory();
        Observer user1 = mock(Observer.class);
        Observer user2 = mock(Observer.class);

        inventory.addObserver(user1);
        inventory.addObserver(user2);

        inventory.bookReturned("Clean Architecture");

        verify(user1).update(any(Observable.class), eq("Clean Architecture"));
        verify(user2).update(any(Observable.class), eq("Clean Architecture"));
    }
    @Test
    void removedObserverShouldNotBeNotified() {
        BookInventory inventory = new BookInventory();
        Observer mockUser = mock(Observer.class);
        inventory.addObserver(mockUser);
        inventory.deleteObserver(mockUser);

        inventory.bookReturned("Domain-Driven Design");

        verify(mockUser, never()).update(any(Observable.class), any());
    }


}
