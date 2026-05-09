package org.example.strategy;

//1. Define the Strategy Interface
interface FineCalculationStrategy {
    double calculateFine(int overdueDays);
}
class FacultyFineStrategy implements FineCalculationStrategy {
    public double calculateFine(int overdueDays) {
        return overdueDays * 0.5;  // 50 per day
    }
}

class GuestFineStrategy implements FineCalculationStrategy {
    public double calculateFine(int overdueDays) {
        return overdueDays * 2.0;  // 2 per day
    }
    
}

//4. client
 public class StrategyPattern {
	 
	    public static void main(String[] args) {
	    	
	    
	        FineCalculator calculator = new FineCalculator();

	        // Student returns late
	        calculator.setStrategy(new StudentFineStrategy());
	        System.out.println("Student fine: $" + calculator.calculateFine(5));
	        
	  

	        // Faculty returns late
	        calculator.setStrategy(new FacultyFineStrategy());
	        System.out.println("Faculty fine: $" + calculator.calculateFine(5));

	        // Guest returns late
	        calculator.setStrategy(new GuestFineStrategy());
	        System.out.println("Guest fine: $" + calculator.calculateFine(5));
	    }
	    
	    
	}
 
// Benefits
//
// Removes if-else complexity
// Makes system extensible � new strategies can be added easily
// Promotes Open/Closed Principle (open for extension, closed for modification)
// Enables dynamic behavior changes at runtime

 //Use Case	Strategy Interface	Example Strategies
 
//Fine calculation	FineCalculationStrategy	Student, Faculty, Guest
//Search algorithms	SearchStrategy	ByTitle, ByAuthor, ByISBN
//Sorting books	    SortStrategy	ByTitle, ByAuthor, ByDate
