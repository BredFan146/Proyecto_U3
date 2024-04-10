import java.util.ArrayList;

public class Client extends User{
    private ArrayList<Book> borrowedBooks = new ArrayList<>();
/*
    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }*/

    public Client(Profile profile, String username, String password) {
        super(profile, username, password);
    }

    public ArrayList<Book> getBorrowedBooks() {
        return borrowedBooks;
    }

    public void setBorrowedBooks(Book borrowedBooks) {
        this.borrowedBooks.add(borrowedBooks);
    }
}
