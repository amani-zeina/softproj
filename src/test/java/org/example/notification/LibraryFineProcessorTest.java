package org.example.notification;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import org.example.service.EmailService;
import org.example.strategy.LibraryFineProcessor;
import org.example.strategy.FineCalculator;
import org.example.strategy.StudentFineStrategy;


public class LibraryFineProcessorTest {

    @Test
    void testStudentFineEmailIsSent() {
        // Arrange
        EmailService mockEmailService = mock(EmailService.class);
        
        FineCalculator calculator = new FineCalculator();

        LibraryFineProcessor processor = new LibraryFineProcessor(mockEmailService, calculator);

        StudentFineStrategy studentStrategy = new StudentFineStrategy();

        // Act
        processor.processFine("s12324672@stu.najah.edu", studentStrategy, 5);

        // Assert
        verify(mockEmailService, times(1))
                .sendEmail(eq("s12324672@stu.najah.edu"), eq("Library Fine Notice"), contains("Your fine is"));
    }

   
}
