import java.util.Random;
import java.util.Scanner;

public class TransactionController {
    Scanner leer = new Scanner(System.in);
    Random random = new Random();


    public int transactionMenu() {
        int transactionMenuOption;
        System.out.printf("%n ---- Transaction menu ----%n");
        System.out.printf("Select an option%n" +
                "1) Borrow a book%n" +
                "2) Return a book%n" +
                "3) See report%n" +
                "Option: ");
        transactionMenuOption = Auxiliar.ReadIntData(leer);
        return transactionMenuOption;
    }

    public int clientTransactionMenu() {
        int transactionMenuOption;
        System.out.printf("%n ---- Transaction menu ----%n");
        System.out.printf("Select an option%n" +
                "1) See report%n" +
                "Option: ");
        transactionMenuOption = Auxiliar.ReadIntData(leer);
        if (transactionMenuOption == 1) {
            this.clientUserReport();
            transactionMenuOption = 0;
        } else {
            System.out.print("Invalid option\n");
            transactionMenuOption = 0;
        }
        return transactionMenuOption;
    }


    public void transactionSwitch(int transactionOption) {
        switch (transactionOption) {
            case 1 -> this.borrowBook();
            case 2 -> this.returnBook();
            case 3 -> this.report();
        }
    }


    private void borrowBook() {
        if (ClientRepository.clients.isEmpty()) {
            System.out.print("There are not clients to borrow books\n");
        } else if (BookRepository.books.isEmpty()) {
            System.out.print("There are not books to borrow\n");
        } else {
            System.out.print("Select a book to borrow\n");
            BookController.seeAvailableBooks();
            System.out.print(" isbn: ");
            String borrowBook = Auxiliar.ReadStringData(leer);
            int borrowBookPosition = BookController.getBook(borrowBook);
            if (borrowBookPosition != -1) {
                System.out.print("Choose the client\n");
                ClientController.clientsToBorrowBooks();
                System.out.print("Client name: ");
                String clientBorrowBook = Auxiliar.ReadStringData(leer);
                System.out.print("Client last name: ");
                String clientBorrowBookLastName = Auxiliar.ReadStringData(leer);
                int clientBorrowBookPosition = ClientController.getClient(clientBorrowBook, clientBorrowBookLastName);
                if (clientBorrowBookPosition != -1) {

                    BookRepository.books.get(borrowBookPosition).setAvaible(false);

                    ClientRepository.clients.get(clientBorrowBookPosition).setBorrowedBooks(BookRepository.books.get(borrowBookPosition));

                    Date date = new Date();
                    date.setBorrowDate();
                    Transaction transaction = new Transaction();
                    transaction.setBook(BookRepository.books.get(borrowBookPosition));
                    transaction.setClient(ClientRepository.clients.get(clientBorrowBookPosition));
                    String id = this.generateId();
                    transaction.setId(id);
                    transaction.setDate(date);
                    transaction.setType("Borrow");

                    TransactionRepository.Transactions.add(transaction);

                    System.out.printf("Client %s has the book %s \n", ClientRepository.clients.get(clientBorrowBookPosition).getProfile().getName(), BookRepository.books.get(borrowBookPosition).getTitle());
                } else {
                    System.out.print("Client not found\n");
                }
            } else {
                System.out.print("Book not found\n");
            }
        }
    }

    private void returnBook() {
        System.out.print("Book to return\n");
        System.out.print("   ---- Books borrowed ----\n");
        System.out.printf("------------------------------------------------------------%n");
        System.out.printf("|%-15s|%-30s|%-7s|%-15s|%n", "Client", "Borrow date", "Transaction ID", "Book(s)");
        System.out.printf("------------------------------------------------------------%n");
        for (Transaction borrowedBooks : TransactionRepository.Transactions) {
            String books = getStringClientBorrowedBooks(borrowedBooks.getClient());
            System.out.printf("|%-15s|%-30s|%-7s|%-15s|%n", borrowedBooks.getClient().getProfile().getName(), borrowedBooks.getDate().getBorrowDate(), borrowedBooks.getId(), books);
            System.out.printf("------------------------------------------------------------%n");
        }
        System.out.print("Select transaction id: ");
        String transactionId = Auxiliar.ReadStringData(leer);

        int transactionPosition = getTransaction(transactionId);
        if (transactionPosition != -1) {
            int bookPosition = BookController.getBook(TransactionRepository.Transactions.get(transactionPosition).getBook().getIsbn());
            int clientPosition = ClientController.getClient(TransactionRepository.Transactions.get(transactionPosition).getClient().getProfile().getName(), TransactionRepository.Transactions.get(transactionPosition).getClient().getProfile().getLastName());
            int bookClientPosition = bookClientPosition(BookRepository.books.get(bookPosition).getIsbn(), clientPosition);

            BookRepository.books.get(bookPosition).setAvaible(true);
            ClientRepository.clients.get(clientPosition).getBorrowedBooks().remove(bookClientPosition);

            Date date = new Date();
            date.setBorrowDate();

            Transaction transaction = new Transaction();
            transaction.setId(transactionId + " Ret");
            transaction.setBook(BookRepository.books.get(bookPosition));
            transaction.setDate(date);
            transaction.setType("Return");
            transaction.setClient(ClientRepository.clients.get(clientPosition));
            TransactionRepository.Transactions.add(transaction);
            System.out.print("Book was returned\n");
        } else {
            System.out.print("Transaction did not found\n");
        }
    }


    private String generateId() {
        String id = "";
        boolean rep = false;
        while (!rep) {
            for (int i = 0; i < 6; i++) {
                int ran = random.nextInt(10);
                String ids = String.valueOf(ran);
                id += ids;
            }
            if (!TransactionRepository.Transactions.isEmpty()) {
                for (Transaction transactionId : TransactionRepository.Transactions) {
                    if (transactionId.getId().equalsIgnoreCase(id)) {
                        id = generateId();
                        rep = true;
                    } else {
                        rep = false;
                    }
                }
            } else {
                rep = true;
            }
        }
        return id;
    }


    private int getTransaction(String id) {
        int transactionPosition = -1;
        for (int i = 0; i < TransactionRepository.Transactions.size(); i++) {
            if (TransactionRepository.Transactions.get(i).getId().equalsIgnoreCase(id)) {
                transactionPosition = i;
            }
        }
        return transactionPosition;
    }


    private static String getStringClientBorrowedBooks(Client clientsArray) {
        String books = "";
        if (clientsArray.getBorrowedBooks().isEmpty()) {
            books = "Not books borrowed";
        } else {
            for (Book clientBooks : clientsArray.getBorrowedBooks()) {
                books += clientBooks.getTitle() + "\n///////////////////////////////////////////////////|";
            }
        }
        return books;
    }


    private static int bookClientPosition(String isbn, int clientPosition) {
        int bookPosition = -1;
        for (int i = 0; i < ClientRepository.clients.get(clientPosition).getBorrowedBooks().size(); i++) {
            if (ClientRepository.clients.get(clientPosition).getBorrowedBooks().get(i).getIsbn().equalsIgnoreCase(isbn)) {
                bookPosition = i;
            }
        }
        return bookPosition;
    }


    private void report() {
        System.out.print("Select an option\n" +
                "1) All movements\n" +
                "2) Book movements\n" +
                "3) Client movements\n" +
                "Option: ");
        int reportOption = Auxiliar.ReadIntData(leer);
        switch (reportOption) {
            case 1 -> this.generalReport();
            case 2 -> this.bookReport();
            case 3 -> this.clientReport();
        }
    }


    private void generalReport() {
        if (!TransactionRepository.Transactions.isEmpty()) {
            System.out.print("  ---- Report ----\n");
            System.out.printf("------------------------------------------------------------%n");
            System.out.printf("|%-7s|%-15s|%-15s|%-12s|%-30s|%n", "Movement", "Book", "Client", "Movement ID", "Date");
            System.out.printf("------------------------------------------------------------%n");
            for (Transaction transactions : TransactionRepository.Transactions) {
                System.out.printf("|%-7s|%-15s|%-15s|%-12s|%-30s|%n", transactions.getType(), transactions.getBook().getTitle(), transactions.getClient().getProfile().getName(), transactions.getId(), transactions.getDate().getBorrowDate());
                System.out.printf("------------------------------------------------------------%n");
            }
        } else {
            System.out.print("There are not any movement registered yet\n");
        }
    }


    private void bookReport() {
        int moveCont = 0;
        BookController.seeAllBooks();
        System.out.print("Select book ISBN: ");
        String bookReport = Auxiliar.ReadStringData(leer);
        int position = BookController.getBook(bookReport);
        if (position != -1) {
            System.out.printf("  ---- %s report ----\n", BookRepository.books.get(position).getTitle());
            System.out.printf("------------------------------------------------------------%n");
            System.out.printf("|%-7s|%-15s|%-15s|%-12s|%-30s|%n", "Movement", "Book", "Client", "Movement ID", "Date");
            System.out.printf("------------------------------------------------------------%n");
            for (Transaction transactions : TransactionRepository.Transactions) {
                if (transactions.getBook().getIsbn().equalsIgnoreCase(bookReport)) {
                    System.out.printf("|%-7s|%-15s|%-15s|%-12s|%-30s|%n", transactions.getType(), transactions.getBook().getTitle(), transactions.getClient().getProfile().getName(), transactions.getId(), transactions.getDate().getBorrowDate());
                    System.out.printf("------------------------------------------------------------%n");
                    moveCont++;
                }
            }
            if (moveCont == 0) {
                System.out.print("No movements registered to this book yet\n");
            }
        } else {
            System.out.print("Book did not found\n");
        }
    }

    private void clientReport() {
        ClientController.showClientArrayList();
        int moveCont = 0;
        System.out.print("Client name: ");
        String clientName = Auxiliar.ReadStringData(leer);
        System.out.print("Client last name: ");
        String clientLast = Auxiliar.ReadStringData(leer);
        int clientPosition = ClientController.getClient(clientName, clientLast);
        if (clientPosition != -1) {
            System.out.printf("  ---- %s report ----\n", ClientRepository.clients.get(clientPosition).getProfile().getName());
            System.out.printf("------------------------------------------------------------%n");
            System.out.printf("|%-7s|%-15s|%-15s|%-12s|%-30s|%n", "Movement", "Book", "Client", "Movement ID", "Date");
            System.out.printf("------------------------------------------------------------%n");
            for (Transaction transactions : TransactionRepository.Transactions) {
                if (transactions.getClient().getProfile().getName().equalsIgnoreCase(clientName) && transactions.getClient().getProfile().getLastName().equalsIgnoreCase(clientLast)) {
                    System.out.printf("|%-7s|%-15s|%-15s|%-12s|%-30s|%n", transactions.getType(), transactions.getBook().getTitle(), transactions.getClient().getProfile().getName(), transactions.getId(), transactions.getDate().getBorrowDate());
                    System.out.printf("------------------------------------------------------------%n");
                    moveCont++;
                }
            }
            if (moveCont == 0) {
                System.out.print("No movements registered to this book yet\n");
            }
        } else {
            System.out.print("Client did not found\n");
        }
    }

    private void clientUserReport() {
        System.out.print("Type your user name: ");
        String userName = Auxiliar.ReadStringData(leer);
        for (Client client : ClientRepository.clients) {
            if (client.getUsername().equals(userName)) {
                if (client.getBorrowedBooks().isEmpty()) {
                    System.out.printf("Not transactions");
                } else {
                    System.out.printf("  ---- %s report ----\n", client.getProfile().getName());
                    System.out.printf("------------------------------------------------------------%n");
                    System.out.printf("|%-7s|%-15s|%-15s|%-12s|%-30s|%n", "Movement", "Book", "Client", "Movement ID", "Date");
                    System.out.printf("------------------------------------------------------------%n");
                    for (Transaction transactions : TransactionRepository.Transactions) {
                        System.out.printf("|%-7s|%-15s|%-15s|%-12s|%-30s|%n", transactions.getType(), transactions.getBook().getTitle(), transactions.getClient().getProfile().getName(), transactions.getId(), transactions.getDate().getBorrowDate());
                        System.out.printf("------------------------------------------------------------%n");
                    }
                }
            }
        }
    }
}
