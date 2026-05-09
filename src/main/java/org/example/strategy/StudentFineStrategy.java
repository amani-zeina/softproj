package org.example.strategy;

public class StudentFineStrategy  implements FineCalculationStrategy{
	public double calculateFine(int overdueDays) {
        return overdueDays * 1.0;  // 1 per day
    }
}
