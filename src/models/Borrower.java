package models;

import java.util.ArrayList;
public class Borrower implements Editable{
    private String id;
    private String name;
    private String email;
    private String phoneNumber;
    private ArrayList<String> borrowedBooks;

    public Borrower(String id, String name, String email, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.borrowedBooks = new ArrayList<>();
    }      

    // Get functions
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }   

    public String getEmail() {
        return email;
    }  

    public String getPhoneNumber() {
        return phoneNumber;
    } 
    
    // Set functions
    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }   

    public void setEmail(String email) {
        this.email = email;
    }   

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // Used to add books to the list of borrowed books
    public void addBorrowedBook(String bookId) {
        borrowedBooks.add(bookId);
    }

    public void removeBorrowedBook(String bookId) {
        borrowedBooks.remove(bookId);
    }

    // Returns list of borrowed books
    public ArrayList<String> getBorrowedBooks() {
        return borrowedBooks;
    }

    // Used to write to file
    @Override
    public String toString() {
        return id + "," 
            + name + "," 
            + email + "," 
            + phoneNumber + ","
            + String.join(";", borrowedBooks);
    }

    // Used to display contents to user
    @Override
    public void display() {
        System.out.println("User ID: " + id);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone #: "  + phoneNumber);
        System.out.println("Borrowed Books: " + String.join(", ", borrowedBooks));
        System.out.println("---------------");
    }

    // Used to edit borrower information
    @Override
    public void edit(int field, String value) {
        switch (field) {
            case 1:
                setId(id);
                break;
            case 2:
                setName(value);
                break;
            case 3:
                setEmail(value);
                break;
            case 4:
                setPhoneNumber(value);
                break;
            default:
                System.out.println("");
        }
    }

    // Returns the field names for the borrower
    @Override
    public String[] getFieldNames() {
        return new String[] {
            "ID",
            "Name",
            "Email",
            "Phone Number"
        };
    }
}
