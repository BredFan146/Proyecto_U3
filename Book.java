public class Book {
    private String title;
    private String isbn;
    private Author author;
    private Date publishDate;
    private boolean isAvaible;

    public Book(String title, String isbn, Author author, Date publishDate, boolean isAvaible) {
        this.title = title;
        this.isbn = isbn;
        this.author = author;
        this.publishDate = publishDate;
        this.isAvaible = isAvaible;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String title) {

        this.title = title;
    }

    public String getIsbn() {

        return isbn;
    }

    public void setIsbn(String isbn) {

        this.isbn = isbn;
    }

    public Author getAuthor() {

        return author;
    }

    public void setAuthor(Author author) {

        this.author = author;
    }

    public Date getPublishDate() {

        return publishDate;
    }

    public void setPublishDate(Date publishDate) {

        this.publishDate = publishDate;
    }

    public boolean isAvaible() {

        return isAvaible;
    }

    public void setAvaible(boolean avaible) {

        isAvaible = avaible;
    }
}
