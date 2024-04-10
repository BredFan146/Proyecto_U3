import java.util.Scanner;

public class AdministratorController {
    Scanner leer = new Scanner(System.in);
    Auxiliar auxiliar = new Auxiliar();
    ProfileController profileController = new ProfileController();
    MainController mainController = new MainController();


    public int mainMenu(boolean isAdmin, String userName) {
        int mainOption = 0;
        System.out.print("Welcome\n");
        do {
            if (isAdmin) {
                if (getAdministrator(userName).isSuperAdmin()) {
                    int superAdmin = this.superMenu();
                    this.superSwitch(superAdmin, getAdministrator(userName));
                } else {
                    mainOption = mainController.MainMenu();
                    mainController.MainSwitch(mainOption, getAdministrator(userName));
                }
            } else {
                int clientOption = mainController.clientMainMenu();
                mainController.clientMainSwitch(clientOption);
            }
        } while (mainOption != 9);
        System.out.print("Login out\n");
        return mainOption;
    }


    private int superMenu() {
        int superOption = 0;
        System.out.print("  ---- Menu ----\n");
        System.out.print("""
                Select an option
                1) Books
                2) Authors
                3) Clients
                4) Transactions
                5) Administrators
                9) Log out
                10) Exit
                Option:\s""");
        superOption = Auxiliar.ReadIntData(leer);
        return superOption;
    }


    private void superSwitch(int option, Administrator administrator) {
        if (option == 5) {
            System.out.printf("%n ---- Administrator menu ----%n");
            System.out.printf("Select an option%n" +
                    "1) Add an administrator%n" +
                    "2) See administrators%n" +
                    "3) Edit administrator info%n" +
                    "4) Edit administrator permissions%n" +
                    "5) Delete administrator%n" +
                    "Option: ");
            String switchOption = Auxiliar.ReadStringData(leer);
            switch (switchOption) {
                case "add" -> this.addAdministrator();
                case "see" -> this.showAdministratorArrayList();
                case "edit info" -> this.editAdministratorInfo();
                case "edit permissions" -> this.setPermissions(administrator);
                case "delete" -> this.deleteAdmin();
            }
        } else {
            mainController.MainSwitch(option, administrator);
        }
    }


    private void addAdministrator() {
        Profile profile;
        System.out.printf(" ---- Add a new administrator ----%n");
        System.out.printf("Administrator info %n");
        profile = profileController.createProfile();
        System.out.print("Profile created\n");

        String userName = auxiliar.createUserName(profile);
        System.out.printf("Administrator user name %s", userName);

        System.out.print("Set password: ");
        String password = Auxiliar.ReadStringData(leer);

        Administrator administrator = new Administrator(profile, userName, password, false);
        this.setPermissions(administrator);
        AdministratorRepository.administrators.add(administrator);


        System.out.print("Actual client list\n");
        showAdministratorArrayList();
    }


    private void showAdministratorArrayList() {
        if (AdministratorRepository.administrators.isEmpty()) {
            System.out.printf("No registered clients%n");
        } else {
            System.out.print("\n\n---- Administrator ----");
            System.out.printf("-----------------------%n");
            System.out.printf("|%-15s|%-20s|%-15s|%-20s|%-20s|%n", "Administrator name", "Administrator last name", "Username", "Administrator birth", "Is super admin");
            System.out.printf("-----------------------%n");
            for (Administrator admin : AdministratorRepository.administrators) {
                System.out.printf("|%-15s|%-20s|%-15s|%-20s|%-20s|%n", admin.getProfile().getName(), admin.getProfile().getLastName(), admin.getUsername(), admin.getProfile().getBirthDate().getStringBirthDate(), admin.isSuperAdmin());
                System.out.printf("-----------------------%n");
            }
        }
    }


    private void editAdministratorInfo() {
        if (AdministratorRepository.administrators.isEmpty()) {
            System.out.print("There are not clients registered\n");
        } else {
            this.showAdministratorArrayList();
            System.out.printf(" >>> Edit administrator info <<<%n");
            System.out.print("Administrator name: ");
            String nameEdit = Auxiliar.ReadStringData(leer);
            System.out.print("Administrator last name: ");
            String lastNameEdit = Auxiliar.ReadStringData(leer);
            int position = getAdministratorByName(nameEdit, lastNameEdit);
            if (position != -1) {
                System.out.printf("What would you like to edit of %s%n", AdministratorRepository.administrators.get(position).getProfile().getName());
                System.out.printf("1) Name%n" +
                        "2) Last name%n" +
                        "3) Birth date%n" +
                        "Option: ");
                int editOption = Auxiliar.ReadIntData(leer);
                if (editOption > 0 && editOption <= 3) {
                    profileController.editAdministratorProfile(editOption, position);
                } else {
                    System.out.print("Invalid option\n");
                }
            } else {
                System.out.print("Author did not find\n");
            }
        }
    }

    private void deleteAdmin() {
        if (!AdministratorRepository.administrators.isEmpty()) {
            this.showAdministratorArrayList();
            System.out.print("Name of administrator to eliminate: ");
            String nameDelete = Auxiliar.ReadStringData(leer);
            System.out.print("Last name of administrator to eliminate: ");
            String lastNameDelete = Auxiliar.ReadStringData(leer);
            int adminDelete = getAdministratorByName(nameDelete, lastNameDelete);
            if (AdministratorRepository.administrators.get(adminDelete).isSuperAdmin() && AdministratorRepository.administrators.size() > 1) {
                this.showAdministratorArrayList();
                System.out.print("Choose another superAdmin: ");
                System.out.print("Name: ");
                String newSuper = Auxiliar.ReadStringData(leer);
                System.out.print("Last name");
                String newSuperLast = Auxiliar.ReadStringData(leer);
                int newSuperInd = getAdministratorByName(newSuper, newSuperLast);
                if (newSuperInd != -1) {
                    AdministratorRepository.administrators.get(newSuperInd).setSuperAdmin(true);
                    AdministratorRepository.administrators.remove(adminDelete);
                } else {
                    System.out.printf("Admin did not found could not delete super admin\n");
                }
            } else {
                System.out.print("Can not eliminate the admin is the only super admin\n");
            }
        }
        System.out.print("There are not admins to eliminate\n");
    }



    public static Administrator getAdministrator(String userName) {
        Administrator admin = null;
        for (Administrator admins : AdministratorRepository.administrators) {
            if (admins.getUsername().equals(userName)) {
                return admin = admins;
            }
        }
        return admin;
    }


    private void setPermissions(Administrator administrator) {
        if (administrator.getPermissions().isEmpty()) {
            System.out.printf("The administrator does not have permissions\n");
        } else {
            for (int i = 0; i < administrator.getPermissions().size(); i++) {
                administrator.getPermissions().remove(i);
            }
            System.out.print("The administrator can read data of authors, books and clients: " +
                    "1) Yes\n" +
                    "2) No\n" +
                    "Option: ");
            int read = Auxiliar.ReadIntData(leer);
            if (read == 1) {
                administrator.setRead();
            } else if (read != 2) {
                System.out.print("Invalid option\n");
            }

            System.out.print("The administrator can delete of authors, books and clients: " +
                    "1) Yes\n" +
                    "2) No\n" +
                    "Option: ");
            int delete = Auxiliar.ReadIntData(leer);
            if (delete == 1) {
                administrator.setDelete();
            } else if (delete != 2) {
                System.out.print("Invalid option\n");
            }

            System.out.print("The administrator can write data of authors, books and clients: " +
                    "1) Yes\n" +
                    "2) No\n" +
                    "Option: ");
            int edit = Auxiliar.ReadIntData(leer);
            if (edit == 1) {
                administrator.setWrite();
            } else if (edit != 2) {
                System.out.print("Invalid option\n");
            }
        }
    }


    public static int getAdministratorByName(String name, String lastName) {
        int ind = -1;
        for (int i = 0; i < AdministratorRepository.administrators.size(); i++) {
            if (AdministratorRepository.administrators.get(i).getProfile().getName().equalsIgnoreCase(name) && AdministratorRepository.administrators.get(i).getProfile().getLastName().equalsIgnoreCase(lastName)) {
                ind = i;
            }
        }
        return ind;
    }
}

