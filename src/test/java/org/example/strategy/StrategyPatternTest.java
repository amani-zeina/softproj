package org.example.strategy;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StrategyPatternTest {

    @Test
    void facultyFineStrategyShouldCalculateCorrectly() {
        FineCalculationStrategy strategy = new FacultyFineStrategy();
        double fine = strategy.calculateFine(5);
        assertEquals(2.5, fine);
    }

    @Test
    void guestFineStrategyShouldCalculateCorrectly() {
        FineCalculationStrategy strategy = new GuestFineStrategy();
        double fine = strategy.calculateFine(5);
        assertEquals(10.0, fine);
    }

    @Test
    void fineCalculatorShouldSwitchStrategies() {
        FineCalculator calculator = new FineCalculator();

        calculator.setStrategy(new FacultyFineStrategy());
        assertEquals(2.5, calculator.calculateFine(5));

        calculator.setStrategy(new GuestFineStrategy());
        assertEquals(10.0, calculator.calculateFine(5));
    }
}
