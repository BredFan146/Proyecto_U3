import java.util.ArrayList;

public class Author {
    private Profile profile;
    private ArrayList<Book> books = new ArrayList<>();


    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public void setBooks(Book book) {
        books.add(book);
    }

    public ArrayList<Book> getBooks() {
        return books;
    }
}
