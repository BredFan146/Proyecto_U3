import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Auxiliar auxiliar = new Auxiliar();
        AdministratorController administratorController = new AdministratorController();

        Seeder seeder = new Seeder();
        seeder.initialize();

        int option = 0;
        do {
            System.out.print("Username: ");
            String userName = Auxiliar.ReadStringData(scanner);
            if (auxiliar.checkUserLog(userName)) {
                System.out.print("Password: ");
                String password = Auxiliar.ReadStringData(scanner);
                if (User.checkPassword(password, auxiliar.getUSer(userName))) {
                    option = administratorController.mainMenu(auxiliar.isAdmin(userName), userName);
                } else {
                    System.out.print("Invalid password\n");
                }
            } else {
                System.out.print("Invalid user\n");
            }
        } while (option != 10);
    }
}

