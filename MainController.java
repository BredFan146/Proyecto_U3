import java.util.Scanner;

public class MainController { Scanner leer = new Scanner(System.in);
    ////////////////////////////
    AuthorController authorController = new AuthorController();
    BookController bookController = new BookController();
    ClientController clientController = new ClientController();
    TransactionController transactionController = new TransactionController();

    public int MainMenu() {
        int option;
        System.out.print("  ---- Menu ----\n");
        System.out.print("""
                Select an option
                1) Books
                2) Authors
                3) Clients
                4) Transactions
                9) Log out
                10) Exit
                Option:\s""");
        option = Auxiliar.ReadIntData(leer);
        return option;
    }


    public int clientMainMenu() {
        int option;
        System.out.print("  ---- Menu -----\n");
        System.out.print("""
                Select an option
                1) Books
                2) Transactions
                9) Log out
                10) Exit
                Option:\s""");
        option = Auxiliar.ReadIntData(leer);
        return option;
    }


    public void clientMainSwitch(int option) {
        switch (option) {
            case 1 -> {//Books
                String bookOption = bookController.clientBookMenu();
                bookController.bookSwitch(bookOption);
                System.out.printf("%nReturning main menu%n");
            }
            case 2 -> {//Transactions
                int transactionOption = transactionController.clientTransactionMenu();
                transactionController.transactionSwitch(transactionOption);
                System.out.printf("%nReturning main menu%n");
            }
        }
    }


    public void MainSwitch(int option, Administrator administrator) {
        switch (option) {
            case 1 -> {//Books
                String bookOption = bookController.bookMenu(administrator).toLowerCase();
                bookController.bookSwitch(bookOption);
                System.out.printf("%nReturning main menu%n");
            }
            case 2 -> {//Authors
                String authorOption = authorController.AuthorMenu(administrator).toLowerCase();
                authorController.authorSwitch(authorOption);
                System.out.printf("%nReturning main menu%n");
            }
            case 3 -> {//Clients
                String clientOption = clientController.clientMenu(administrator).toLowerCase();
                clientController.clientSwitch(clientOption);
                System.out.printf("%nReturning main menu%n");
            }
            case 4 -> {//Transactions
                int transactionOption = transactionController.transactionMenu();
                transactionController.transactionSwitch(transactionOption);
                System.out.printf("%nReturning main menu%n");
            }
        }
    }
}
