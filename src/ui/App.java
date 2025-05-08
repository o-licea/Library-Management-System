package ui;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import models.Book;
import models.Borrower;
import services.LibraryService;

public class App {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Borrower> borrowers = new ArrayList<>();

        // Read books from the file
        try (Scanner fileScanner = new Scanner(new File("books.txt"))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                    String title = parts[0];
                    String author = parts[1];
                    String genre = parts[2];
                    String id = parts[3];
                    boolean isAvailable = Boolean.parseBoolean(parts[4]);
                    books.add(new Book(title, author, genre, id, isAvailable));
            }
        } 
        catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        // Read borrowers from the file
        try (Scanner fileScanner = new Scanner(new File("borrowers.txt"))) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");
                    String id = parts[0];
                    String name = parts[1];
                    String email = parts[2];
                    String phoneNumber = parts[3];
                    Borrower borrower = new Borrower(id, name, email, phoneNumber);

                    // Add borrowed book list to array
                    if (parts.length > 4) {
                        String[] borrowedBooks = parts[4].split(";");
                        for (String bookID : borrowedBooks) {
                            borrower.addBorrowedBook(bookID);
                        }
                    }
                    borrowers.add(borrower);
            }
        } 
        catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
        }


        LibraryService libraryService = new LibraryService(books, borrowers);

        Boolean cont = true;

        while (cont) {
            System.out.println("Library Management System");
            System.out.println("""
                                1. Book Menu
                                2. Borrower Menu
                                3. Borrow & Return Menu
                                4. Exit Program""");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume leftover newline character

            switch (choice) {
                case 1 -> {
                    System.out.println("------------------------------");
                    bookMenu(libraryService, scanner);
                    System.out.println("------------------------------");
                    break;
                }
                case 2 -> {
                    System.out.println("------------------------------");
                    borrowerMenu(libraryService, scanner);
                    System.out.println("------------------------------");
                    break;
                }
                case 3 -> {
                    System.out.println("------------------------------");
                    borrowReturnMenu(libraryService, scanner);
                    System.out.println("------------------------------");
                    break;
                }
                case 4 -> {
                    cont = false;
                    System.out.println("Exiting program...");
                    break;
                } 
                default -> {
                    System.out.println("Invalid choice. Please try again.");
                    System.out.println("------------------------------");
                    break;
                }
            }
        }
        // Close scanner 
        scanner.close();
    }

    public static void bookMenu(LibraryService libraryService, Scanner scanner) {
        Boolean cont = true;
        while (cont) {
            System.out.println("Book Menu:");
            System.out.println("""
                                1. Add Book
                                2. Delete Book
                                3. Update Book
                                4. List all Books
                                5. Search for Book
                                6. Return to Main Menu""");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume leftover newline character

            switch (choice) {
                // Adds book to the list
                case 1 -> {
                    System.out.println("Enter book title: ");
                    String title = scanner.nextLine();

                    System.out.println("Enter book author: ");
                    String author = scanner.nextLine();

                    System.out.println("Enter book genre: ");
                    String genre = scanner.nextLine();

                    System.out.println("Enter book ID: ");
                    String id = scanner.nextLine();

                    // Add book to the list
                    libraryService.addBook(new Book(title, author, genre, id, true));
                    System.out.println("------------------------------");
                    break;
                }
                // Deletes book from the list
                case 2 -> {
                    libraryService.displayBooks();
                    System.out.println("Enter the index of the book that you want to delete: ");
                    int idx = scanner.nextInt();

                    libraryService.removeBook(idx);
                    System.out.println("------------------------------");
                    break;
                }
                // Edits a book on the list
                case 3 -> {
                    libraryService.displayBooks();
                    System.out.println("Enter the index of the book that you want to edit: ");
                    int idx = scanner.nextInt();
                    scanner.nextLine(); // Consume leftover newline character

                    libraryService.editBook(idx);
                    System.out.println("Book updated successfully.");
                    System.out.println("------------------------------");
                    break;
                }
                // Displays books on the list
                case 4 -> {
                    libraryService.displayBooks();
                    System.out.println("------------------------------");
                    break;
                }
                // Searches for books using search menu
                case 5 -> {
                    System.out.println("------------------------------");
                    searchMenu(libraryService, scanner);
                    System.out.println("------------------------------");
                    break;
                }
                // Backs out of list
                case 6 -> {
                    cont = false;
                    break;
                }

                // If choice is invalid, prompt user to try again
                default -> {
                    System.out.println("Invalid choice. Please try again.");
                    System.out.println("------------------------------");
                    break;
            }

            }
        }
    }

    public static void borrowerMenu(LibraryService libraryService, Scanner scanner) {
        Boolean cont = true;
        while (cont) {
            System.out.println("Borrower Menu:");
            System.out.println("""
                               1. Register Borrower
                               2. Delete Borrower
                               3. Update Borrower
                               4. List All Borrowers
                               5. Return to Main Menu"""); 
            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                // Adds borrower from the list
                case 1 -> {
                    System.out.println("Enter the Borrower's ID: ");
                    String id = scanner.nextLine();

                    System.out.println("Enter the Borrower's name: ");
                    String name = scanner.nextLine();

                    System.out.println("Enter the borrowers's email: ");
                    String email = scanner.nextLine();

                    System.out.println("Enter the borrowers's phone number: ");
                    String phoneNumber = scanner.nextLine();

                    libraryService.addBorrower(new Borrower(id, name, email, phoneNumber));
                    System.out.println("------------------------------");
                    break;
                }
                // Deletes borrowers fron the list
                case 2 -> {
                    libraryService.displayBorrowers();
                    System.out.println("Enter the index of the borrower that you want to delete");
                    int idx = scanner.nextInt();

                    libraryService.removeBorrower(idx);
                    System.out.println("------------------------------");
                    break;
                }
                // Edits borrowers on the list
                case 3 -> {
                    libraryService.displayBorrowers();
                    System.out.println("Enter the index of the borrower that you want to edit");
                    int idx = scanner.nextInt();
                    scanner.nextLine(); // Consume leftover newline character

                    libraryService.editBorrower(idx);
                    System.out.println("Borrower updated successfully");
                    System.out.println("------------------------------");
                    break;
                }
                // Displays borrowers on the list
                case 4 -> {
                    libraryService.displayBorrowers();
                    System.out.println("------------------------------");
                    break;
                }
                // Breaks out of menu
                case 5 -> {
                    cont = false;
                    break;
                }
                // If choice is invalid, prompt user to try again
                default -> {
                    System.out.println("Invalid choice. Please try again.");
                    System.out.println("------------------------------");
                    break;
                }
            }
        }
    }

    public static void borrowReturnMenu(LibraryService libraryService, Scanner scanner) {
        Boolean cont = true;
        while (cont) {
            System.out.println("Borrow & Return Menu:");
            System.out.println("""
                                1. Borrow Book
                                2. Return Book
                                3. Check Full Book Availability
                                4. Return to Main Menu""");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume leftover newline character

            switch (choice) {
                // Borrows book from the list
                case 1 -> {
                    libraryService.displayBooks();
                    System.out.println("Enter the index of the book that you want to borrow: ");
                    int bookIdx = scanner.nextInt();

                    libraryService.displayBorrowers();
                    System.out.println("Enter the index of the person who is borrowing the book: ");
                    int borrowerIdx = scanner.nextInt();

                    libraryService.borrowBook(bookIdx, borrowerIdx);
                    System.out.println("------------------------------");
                    break;
                }
                // Returns book from the list
                case 2 -> {
                    libraryService.displayBorrowers();
                    System.out.println("Enter the index of the borrower that is returning the book: ");
                    int borrowerIdx = scanner.nextInt();

                    libraryService.displayBorrowedBooks(borrowerIdx);
                    System.out.println("Enter the index of the book that you want to return: ");
                    int bookIdx = scanner.nextInt();

                    libraryService.returnBook(bookIdx, borrowerIdx);
                    System.out.println("------------------------------");
                    break;
                }
                // Displays borrowed books on the list
                case 3 -> {
                    System.out.println("Available Books:");
                    libraryService.displayAvailableBooks();

                    System.out.println("Unavailable Books:");
                    libraryService.displayUnavailableBooks();

                    System.out.println("------------------------------");
                    break;
                }
                // Breaks out of menu
                case 4 -> {
                    cont = false;
                    break;
                }
                // If choice is invalid, prompt user to try again
                default -> {
                    System.out.println("Invalid choice. Please try again.");
                    System.out.println("------------------------------");
                    break;
                }
            }
        }
    }

    public static void searchMenu(LibraryService libraryService, Scanner scanner) {
        Boolean cont = true;
        while (cont) {
            System.out.println("Search Menu:");
            System.out.println("""
                                1. Search by Title
                                2. Search by Author
                                3. Search by Genre
                                4. Return to Book Menu""");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume leftover newline character

            switch (choice) {
                // Searches for book by title
                case 1 -> {
                    System.out.println("Enter the title of the book you want to search for: ");
                    String title = scanner.nextLine();

                    libraryService.searchBookTitle(title);
                    System.out.println("------------------------------");
                    break;
                }
                // Searches for book by author
                case 2 -> {
         
                    System.out.println("Enter the author of the book you want to search for: ");
                    String author = scanner.nextLine();

                    libraryService.searchBookAuthor(author);
                    System.out.println("------------------------------");
                    break;
                }
                // Searches for book by genre
                case 3 -> {
                    System.out.println("Enter the genre of the book you want to search for: ");
                    String genre = scanner.nextLine();

                    libraryService.searchBookGenre(genre);
                    System.out.println("------------------------------");
                    break;
                }
                // Breaks out of menu
                case 4 -> {
                    cont = false;
                    break;
                }
                // If choice is invalid, prompt user to try again
                default -> {
                    System.out.println("Invalid choice. Please try again.");
                    System.out.println("------------------------------");
                    break;
                }
            }
        }
    }
}