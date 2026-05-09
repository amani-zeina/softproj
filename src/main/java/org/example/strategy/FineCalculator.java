package org.example.strategy;

public class FineCalculator {
	 private FineCalculationStrategy strategy;

	    
	    // aggregation
	    public void setStrategy(FineCalculationStrategy strategy) {
	        this.strategy = strategy;
	    }

	    public double calculateFine(int overdueDays) {
	        if (strategy == null) {
	            throw new IllegalStateException("Fine strategy not set!");
	        }
	        return strategy.calculateFine(overdueDays);
	    }
}
