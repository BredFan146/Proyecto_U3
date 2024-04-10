import java.util.Scanner;

public class BookController {
    Scanner leer = new Scanner(System.in);


    public String bookMenu(Administrator administrator) {
        String bookMenuOption;
        System.out.printf("%n >>>> Books menu <<<<%n");
        Auxiliar.createMenu(administrator);

        System.out.print("Option: ");
        bookMenuOption = Auxiliar.ReadStringData(leer);
        for (Permission permission : administrator.getPermissions()) {
            if (!bookMenuOption.equalsIgnoreCase(String.valueOf(permission))) {
                System.out.printf("Invalid option\n");
                bookMenuOption = "a";
            }
        }
        return bookMenuOption;
    }


    public String clientBookMenu() {
        String bookMenuOption;
        System.out.printf("%n >>>> Books menu <<<<%n");
        System.out.printf("Select an option%n" +
                "1) See books%n" +
                "Option: ");
        bookMenuOption = Auxiliar.ReadStringData(leer);
        if (Integer.parseInt(bookMenuOption) == 1) {
            bookMenuOption = "read";
        } else {
            System.out.print("Invalid option\n");
            bookMenuOption = "a";
        }
        return bookMenuOption;
    }


    public void bookSwitch(String boookOption) {
        switch (boookOption) {
            case "write" ->//Create
                    this.addBook();
            case "read" -> {//See
                this.seeBooks();
            }
            case "edit" -> {//Edit
                this.editBookInfo();
            }
            case "delete" -> {//Delete
                this.deleteBook();
            }
        }
    }

    private void addBook() {
        if (AuthorRepository.authors.isEmpty()) {
            System.out.print("There are not authors, create an author first\n");
        } else {
            System.out.print(" >>> Add a new book <<<\n");
            System.out.print("Title: ");
            String title = Auxiliar.ReadStringData(leer);
            System.out.print("Isbn: ");
            String isbn = Auxiliar.ReadStringData(leer);
            System.out.print("Book publish date \n");
            Date date = new Date();
            date.publishDate();
            AuthorController.showAuthorArrayList();

            System.out.print("Choose an author of the list\n ");
            System.out.print("Author first name : ");
            String bookAuthor = Auxiliar.ReadStringData(leer);
            System.out.print("Author last name: ");
            String bookAuthorLastName = Auxiliar.ReadStringData(leer);

            int authorSub = AuthorController.getAuthor(bookAuthor, bookAuthorLastName);
            Book book = new Book(title, isbn, AuthorRepository.authors.get(authorSub), date, true);
            BookRepository.books.add(book);
            AuthorRepository.authors.get(authorSub).setBooks(book);
            seeAllBooks();
            System.out.print("The book was created ...\n");
        }
    }


    public void seeBooks() {
        System.out.print("""
                --- See books ---
                1) All books
                2) Available books to borrow
                3) Books borrowed
                Option:\s""");
        int seeBookOption = Auxiliar.ReadIntData(leer);
        switch (seeBookOption) {
            case 1 -> seeAllBooks();
            case 2 -> seeAvailableBooks();
            case 3 -> seeNoAvailableBooks();
        }
    }


    public static void seeAllBooks() {
        String available = "";
        if (BookRepository.books.isEmpty()) {
            System.out.print("There are not books\n");
        } else {
            System.out.printf("%n----  Books  ----%n");
            System.out.printf("------------------------------------------------------------%n");
            System.out.printf("|%-15s|%-10s|%-15s|%-15s|%-10s|%n", "Title", "ISBN", "Author", "Publish date", "Available");
            System.out.printf("------------------------------------------------------------%n");
            for (Book books : BookRepository.books) {
                if (books.isAvaible()) {
                    available = "Available";
                } else {
                    available = "Not available";
                }
                System.out.printf("|%-15s|%-10s|%-15s|%-15s|%-10s|%n", books.getTitle(), books.getIsbn(), books.getAuthor().getProfile().getName(), books.getPublishDate().getStringPublishDate(), available);
                System.out.printf("------------------------------------------------------------%n");
            }
        }
    }


    public static void seeAvailableBooks() {
        if (BookRepository.books.isEmpty()) {
            System.out.print("There are not books\n");
        } else {
            System.out.printf("%n----  Books  ----%n");
            System.out.printf("------------------------------------------------------------%n");
            System.out.printf("|%-15s|%-10s|%-15s|%-15s|%-10s|%n", "Title", "ISBN", "Author", "Publish date", "Available");
            System.out.printf("------------------------------------------------------------%n");
            for (Book books : BookRepository.books) {
                if (books.isAvaible()) {
                    System.out.printf("|%-15s|%-10s|%-15s|%-15s|%-10s|%n", books.getTitle(), books.getIsbn(), books.getAuthor().getProfile().getName(), books.getPublishDate().getStringPublishDate(), "Available");
                    System.out.printf("------------------------------------------------------------%n");
                }
            }
        }
    }


    public static void seeNoAvailableBooks() {
        if (BookRepository.books.isEmpty()) {
            System.out.print("No books available\n");
        } else {
            System.out.printf("%n----  Books  ----%n");
            System.out.printf("------------------------------------------------------------%n");
            System.out.printf("|%-15s|%-10s|%-15s|%-15s|%-10s|%n", "Title", "ISBN", "Author", "Publish date", "Available");
            System.out.printf("------------------------------------------------------------%n");
            for (Book books : BookRepository.books) {
                if (!books.isAvaible()) {
                    System.out.printf("|%-15s|%-10s|%-15s|%-15s|%-10s|%n", books.getTitle(), books.getIsbn(), books.getAuthor().getProfile().getName(), books.getPublishDate().getStringPublishDate(), "Borrowed");
                    System.out.printf("------------------------------------------------------------%n");
                }
            }
        }
    }


    private void editBookInfo() {
        if (BookRepository.books.isEmpty()) {
            System.out.print("There are not books \n");
        } else {
            seeAllBooks();
            System.out.print(" ISBN: ");
            String isbnEdit = Auxiliar.ReadStringData(leer);
            if (getBook(isbnEdit) != -1) {
                System.out.printf("What would you like to edit of the book: %-15s\n", BookRepository.books.get(getBook(isbnEdit)).getTitle());
                System.out.print("""
                        1) Title
                        2) ISBN
                        3) Author
                        4) Publish date
                        Option:\s""");
                int editBookOption = Auxiliar.ReadIntData(leer);
                editBook(editBookOption, getBook(isbnEdit));
            } else {
                System.out.print("Book did not found\n");
            }
        }
    }


    private void editBook(int optionEdit, int position) {
        switch (optionEdit) {
            case 1 -> {
                System.out.print("New title: ");
                String newTitle = Auxiliar.ReadStringData(leer);
                BookRepository.books.get(position).setTitle(newTitle);
                System.out.printf("New title: %s", BookRepository.books.get(position).getTitle());
            }
            case 2 -> {
                System.out.print("New ISBN: ");
                String newIsbn = Auxiliar.ReadStringData(leer);
                BookRepository.books.get(position).setIsbn(newIsbn);
                System.out.printf("New isbn: %s", BookRepository.books.get(position).getIsbn());
            }
            case 3 -> {
                AuthorController.showAuthorArrayList();
                System.out.print("New author ");
                System.out.print("Name: ");
                String newAuthor = Auxiliar.ReadStringData(leer);
                System.out.print("Last name: ");
                String newLastName = Auxiliar.ReadStringData(leer);
                int authorPosition = AuthorController.getAuthor(newAuthor, newLastName);
                if (authorPosition != -1) {
                    int oldAuthor = AuthorController.getAuthor(BookRepository.books.get(position).getAuthor().getProfile().getName(), BookRepository.books.get(position).getAuthor().getProfile().getLastName());
                    String bookIsbn = BookRepository.books.get(position).getIsbn();
                    for (int i = 0; i <= AuthorRepository.authors.get(oldAuthor).getBooks().size(); i++) {
                        if (AuthorRepository.authors.get(oldAuthor).getBooks().get(i).getIsbn().equalsIgnoreCase(bookIsbn)) {
                            AuthorRepository.authors.get(oldAuthor).getBooks().remove(i);
                        }
                    }
                    AuthorRepository.authors.get(authorPosition).setBooks(BookRepository.books.get(position));
                } else {
                    System.out.print("Author not found\n");
                }
                System.out.printf("New author: %s", BookRepository.books.get(position).getAuthor().getProfile().getName());
            }
            case 4 -> {
                Date date = new Date();
                date.setPublishDate(date.publishDate());
                BookRepository.books.get(position).setPublishDate(date);
                System.out.printf("New date: %s", BookRepository.books.get(position).getPublishDate().getStringPublishDate());
            }
        }
    }


    private void deleteBook() {
        if (BookRepository.books.isEmpty()) {
            System.out.print("There are not books \n");
        } else {
            seeAllBooks();
            System.out.print("ISBN : ");
            String bookDelete = Auxiliar.ReadStringData(leer);
            if (getBook(bookDelete) != -1) {
                if (!BookRepository.books.get(getBook(bookDelete)).isAvaible()) {
                    System.out.print("Borrowed book\n");
                } else {
                    String author = BookRepository.books.get(getBook(bookDelete)).getAuthor().getProfile().getName();
                    String authorLast = BookRepository.books.get(getBook(bookDelete)).getAuthor().getProfile().getLastName();
                    for (int i = 0; i < AuthorRepository.authors.get(AuthorController.getAuthor(author, authorLast)).getBooks().size(); i++) {
                        if (AuthorRepository.authors.get(AuthorController.getAuthor(author, authorLast)).getBooks().get(i).getIsbn().equalsIgnoreCase(bookDelete)) {
                            AuthorRepository.authors.get(AuthorController.getAuthor(author, authorLast)).getBooks().remove(i);
                        }
                    }
                    BookRepository.books.remove(getBook(bookDelete));
                }
            } else {
                System.out.print("Bok not created\n");
            }
        }
    }


    public static int getBook(String isbn) {
        int bookPosition = -1;
        for (int i = 0; i < BookRepository.books.size(); i++) {
            if (BookRepository.books.get(i).getIsbn().equalsIgnoreCase(isbn)) {
                bookPosition = i;
            }
        }
        return bookPosition;
    }
}
