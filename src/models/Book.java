package models;

public class Book implements Editable {
    private String title;
    private String author;
    private String genre;
    private String id;
    public Boolean isAvailable = true;

    public Book(String title, String author, String genre, String id, boolean isAvailable) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.id = id;
        this.isAvailable = isAvailable;
    }

    // Get functions
    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public String getGenre() {
        return genre;
    }

    public String getID() {
        return id;
    }

    public Boolean getIsAvailable() {
        return isAvailable;
    }


    // Set functions
    public void setTitle(String title) {
        this.title = title;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public void setId(String id) {
        this.id = id;
    }

 
    // Used to check out books
    public void checkOut() {
        this.isAvailable = false;
    }

    public void checkIn() {
        this.isAvailable = true;
    }

    // Used to write to file
    @Override
    public String toString() {
        return title + "," 
            + author + "," 
            + genre + "," 
            + id + "," 
            + isAvailable;
    }

    // Displays books to the console
    @Override
    public void display() {
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Genre: " + genre);
        System.out.println("ID: " + id);
        System.out.println("Available: " + isAvailable);
        System.out.println("---------------");
    }

    // Used to edit book information
    @Override
    public void edit(int field, String value) {
        switch (field) {
            case 1:
                this.title = value;
                break;
            case 2:
                this.author = value;
                break;
            case 3:
                this.genre = value;
                break;
            case 4:
                this.id = value;
                break;
            default:
                System.out.println("");
        }
    }

    // Returns the field names for the book
    @Override
    public String[] getFieldNames() {
        return new String[] {
            "Title",
            "Author",
            "Genre",
            "ID"
        };
    }
}
