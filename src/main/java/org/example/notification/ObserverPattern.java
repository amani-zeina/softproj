package org.example.notification;

import java.util.Observable;// class    (publisher)
import java.util.Observer;// interface   (Sub)

import io.github.cdimascio.dotenv.Dotenv;

public class ObserverPattern {

}


@SuppressWarnings("deprecation")

class BookInventory extends Observable {

    @SuppressWarnings("deprecation")
    
	public void bookReturned(String bookTitle) {
        System.out.println(" Book '" + bookTitle + "' returned to inventory.");

        // Mark that the state has changed
        setChanged();

        // Notify all observers (users)
        notifyObservers(bookTitle);
    }
}

 class LibraryUser implements Observer {
    private String name;
    private String email;

    public LibraryUser(String name, String email) {
        this.name = name;
        this.email = email;
    }

    // Called automatically when notifyObservers() is triggered
    public void update(@SuppressWarnings("deprecation") Observable o, Object arg) {
        if (arg instanceof String) {
        	
            String bookTitle = (String) arg;
            
            System.out.printf(name + " (" + email + ") notified: '"
                    + bookTitle + "' is now available!");
           
//        	
//        	String subject = "Book Due Reminder";
//            String body = "Dear user, Your book is due soon. Best regards, An Najah Library System";
//        
            
        
        }
    }
}
 
 class LibrarySystem {
	    @SuppressWarnings("deprecation")
		public static void main(String[] args) {
	    	
	        // Create observable (subject)
	        BookInventory inventory = new BookInventory();

	        // Create observers (users)
	        LibraryUser ali = new LibraryUser("Ali", "ali@gmail.com");
	        LibraryUser sami = new LibraryUser("Sami", "sami@gmail.com");

	        // Subscribe users to the inventory
	        inventory.addObserver(ali);
	        inventory.addObserver(sami);

	        // Simulate book return event
	        inventory.bookReturned("Clean Code");  //  Ali(ali@gmail.com) notified: 'Clean Code' is now available!
	                                               // Sami (sami@gmail.com) notified: 'Clean Code' is now available!
	    }
	}


