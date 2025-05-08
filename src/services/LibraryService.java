package services;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import models.Book;
import models.Borrower;
import models.Editable;

public class LibraryService {
    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Borrower> borrowers = new ArrayList<>();
    Scanner scanner = new Scanner(System.in);
    
    public LibraryService(ArrayList<Book> books, ArrayList<Borrower> borrowers) {
        this.books = books;
        this.borrowers = borrowers;
    }

    // ------------------Generic methods used for both classes-------------------

    public <T extends Editable> void displayItems(List<T> items) {
        // Check if the list is empty
        if (items.isEmpty()) {
            System.out.println("The list is empty");
            return;
        }

        System.out.println("--------------");

        for (int x = 0; x < items.size(); x++) {
            System.out.print(x + ". ");
            items.get(x).display();
        }
    }

    public <T> T getItem(int idx, List<T> items) {
        // Check if the index is valid
        if (idx < 0 || idx >= items.size()) {
            System.out.println("Invalid index. Please enter a valid index");
            return null;
        }

        return items.get(idx);
    }

    public <T> void addItem(T item, List<T> items, String fileName) {
        // Input validation for the item        

        // Add item to the list
        items.add(item);

        try {
            List<String> lines = items.stream()
                    .map(T::toString)
                    .toList();

            Files.write(Paths.get(fileName), lines);
        } 
        catch (IOException e) {
            System.out.println("The file could not be written to");
        }

        System.out.println("Item added successfully");
    }

    public <T> void removeItem(int idx, List<T> items, String fileName) {
        // Input validation for the index
        if (idx < 0 || idx >= items.size()) {
            System.out.println("Invalid index. Please enter a valid index");
            return;
        } 
        
        // Removes item in index
        items.remove(idx);
        
        // Erases the item from the file
        try {
            List<String> lines = items.stream()
                    .map(T::toString)
                    .toList();

            Files.write(Paths.get(fileName), lines);
        } 
        catch (IOException e) {
            System.out.println("The file could not be written to");
        }

        System.out.println("Item removed successfully");  
    }

    public <T extends Editable> void editItem(int idx, List<T> items, String fileName) {
        if (idx < 0 || idx >= items.size()) {
            System.out.println("Invalid index. Please enter a valid index");
            return;
        }

        String[] fieldNames = items.get(idx).getFieldNames();


        System.out.println("Which information do you want to edit?");
        for (int i = 0; i < fieldNames.length; i++) {
            System.out.println((i + 1) + ". " + fieldNames[i]);
        }
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume leftover newline character

        if (choice < 1 || choice > fieldNames.length) {
            System.out.println("Invalid choice.");
            return;
        }

        System.out.println("Enter the new value for " + fieldNames[choice - 1] + ": ");
        String newValue = scanner.nextLine();
        items.get(idx).edit(choice, newValue);

        // Troubleshooting
        items.get(idx).display();

        try {
            List<String> lines = items.stream()
                    .map(T::toString)
                    .toList();

            Files.write(Paths.get(fileName), lines);
        } 
        catch (IOException e) {
            System.out.println("The file could not be written to");
        }

    }

    // -------------------------Book methods-----------------------------

    public void addBook(Book book) {
        addItem(book, books, "books.txt");
    }

    public void removeBook(int idx) {
        removeItem(idx, books, "books.txt");
    }

    public void displayBooks() {
        displayItems(books);
    }

    public void editBook(int idx) {
        editItem(idx, books, "books.txt");
    }

    public void borrowBook(int bookIdx, int borrowerIdx) {
        // Input validation for indexes
        if (bookIdx < 0 || bookIdx >= books.size()) {
            System.out.println("Invalid index. Please enter a valid index");
            return;
        }
        if (borrowerIdx < 0 || borrowerIdx >= borrowers.size()) {
            System.out.println("Invalid index. Please enter a valid index");
            return;
        }

        // Gets book and borrower from lists
        Book book = books.get(bookIdx);
        Borrower borrower = borrowers.get(borrowerIdx);

        // Checks availability of book
        if (book.getIsAvailable()) {
            // Checks out book
            book.checkOut();
            borrower.addBorrowedBook(book.getID());

            // Updates files with new changes
            try {
                List<String> bookLines = books.stream()
                    .map(Book::toString)
                    .toList();

                Files.write(Paths.get("books.txt"), bookLines);

                List<String> borrowerLines = borrowers.stream() 
                    .map(Borrower::toString)
                    .toList();
                Files.write(Paths.get("borrowers.txt"), borrowerLines);
            } 
            catch (IOException e) {
                System.out.println("The file(s) could not be written to");
            }

            System.out.println("Book borrowed successfully");
        } 
        else {
            System.out.println("Book is not available for borrowing");
        }

    }

    public void returnBook(int bookIdx, int borrowerIdx) {
        // Input validation for indexes
        if (bookIdx < 0 || bookIdx >= books.size()) {
            System.out.println("Invalid index. Please enter a valid index");
            return;
        }
        if (borrowerIdx < 0 || borrowerIdx >= borrowers.size()) {
            System.out.println("Invalid index. Please enter a valid index");
            return;
        }

        // Gets book and borrower from lists
        Book book = books.get(bookIdx);
        Borrower borrower = borrowers.get(borrowerIdx);

        // Checks if the book is borrowed by the borrower
        if (borrower.getBorrowedBooks().contains(book.getID())) {
            // Checks in the book
            book.checkIn();
            borrower.removeBorrowedBook(book.getID());

            // Updates files with new changes
            try {
                List<String> bookLines = books.stream()
                    .map(Book::toString)
                    .toList();

                Files.write(Paths.get("books.txt"), bookLines);

                List<String> borrowerLines = borrowers.stream() 
                    .map(Borrower::toString)
                    .toList();
                Files.write(Paths.get("borrowers.txt"), borrowerLines);
            } 
            catch (IOException e) {
                System.out.println("The file(s) could not be written to");
            }

            System.out.println("Book returned successfully");
        } 
        else {
            System.out.println("This book was not borrowed by this user");
        }
    }

    public void displayBorrowedBooks(int borrowerIdx) {
        // Input validation for index
        if (borrowerIdx < 0 || borrowerIdx >= borrowers.size()) {
            System.out.println("Invalid index. Please enter a valid index");
            return;
        }

        Borrower borrower = borrowers.get(borrowerIdx);
        ArrayList<String> borrowedBooks = borrower.getBorrowedBooks();

        // Check if the list is empty
        if (borrowedBooks.isEmpty()) {
            System.out.println("The list is empty");
            return;
        }

        System.out.println("--------------");

        System.out.println(borrower.getName() + "'s borrowed books: ");

        for (int x = 0; x < borrowedBooks.size(); x++) {
            System.out.print(x + ". ");
            System.out.println(borrowedBooks.get(x));
        }
    }

    public void displayAvailableBooks() {
        // Check if the list is empty
        if (books.isEmpty()) {
            System.out.println("The list is empty");
            return;
        }

        System.out.println("--------------");

        // Display all available books
        for (int x = 0; x < books.size(); x++) {
            if (books.get(x).getIsAvailable()) {
                System.out.print(x + ". ");
                books.get(x).display();
            }
        }
    }
    
    public void displayUnavailableBooks() {
        // Check if the list is empty
        if (books.isEmpty()) {
            System.out.println("The list is empty");
            return;
        }

        System.out.println("--------------");

        // Display all unavailable books
        for (int x = 0; x < books.size(); x++) {
            if (!books.get(x).getIsAvailable()) {
                System.out.print(x + ". ");
                books.get(x).display();
            }
        }
    }

    public void searchBookTitle(String title){
        // Check if the list is empty
        if (books.isEmpty()) {
            System.out.println("The list is empty");
            return;
        }

        System.out.println("--------------");

        // Display all books with same title
        for (int x = 0; x < books.size(); x++) {
            if (books.get(x).getTitle().equalsIgnoreCase(title)) {
                System.out.print(x + ". ");
                books.get(x).display();
            }
        }
    }

    public void searchBookAuthor(String author){
        // Check if the list is empty
        if (books.isEmpty()) {
            System.out.println("The list is empty");
            return;
        }

        System.out.println("--------------");

        // Display all books with same author
        for (int x = 0; x < books.size(); x++) {
            if (books.get(x).getAuthor().equalsIgnoreCase(author)) {
                System.out.print(x + ". ");
                books.get(x).display();
            }
        }
    }

    public void searchBookGenre(String genre){
        // Check if the list is empty
        if (books.isEmpty()) {
            System.out.println("The list is empty");
            return;
        }

        System.out.println("--------------");

        // Display all books with same genre
        for (int x = 0; x < books.size(); x++) {
            if (books.get(x).getGenre().equalsIgnoreCase(genre)) {
                System.out.print(x + ". ");
                books.get(x).display();
            }
        } 
    }

    // ------------------Borrower methods-------------------

    public void addBorrower(Borrower borrower) {
        addItem(borrower, borrowers, "borrowers.txt");
    }

    public void removeBorrower(int idx) {
        removeItem(idx, borrowers, "borrowers.txt");
    }

    public void displayBorrowers() {
        displayItems(borrowers);
    }

    public void editBorrower(int idx) {
        editItem(idx, borrowers, "borrowers.txt");

    }
}
