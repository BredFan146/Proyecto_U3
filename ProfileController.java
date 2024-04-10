import java.util.Scanner;

public class ProfileController {
    private Scanner scanner = new Scanner(System.in);

    public Profile createProfile() {
        System.out.print("Name: ");
        String name = readStringData();
        System.out.print("Last name: ");
        String lastName = readStringData();
        System.out.printf("Author birthdate %n");
        Date date = new Date();
        String birthDate = date.birthDate();
        date.setBirthDate(birthDate);
        Profile profile1 = new Profile(name, lastName, date);
        return profile1;
    }

    private String readStringData() {
        return scanner.nextLine();
    }

    public void editAuthorProfile(int option, int position) {
        switch (option) {
            case 1 -> {
                System.out.print("New name: ");
                String newName = readStringData();
                AuthorRepository.authors.get(position).getProfile().setName(newName);
            }
            case 2 -> {
                System.out.print("New last name: ");
                String newLastName = readStringData();
                AuthorRepository.authors.get(position).getProfile().setLastName(newLastName);
            }
            case 3 -> {
                System.out.printf("New birth date%n");
                Date newDate = new Date();
                String date = newDate.birthDate();
                newDate.setBirthDate(date);
                AuthorRepository.authors.get(position).getProfile().setBirthDate(newDate);
            }
        }
    }

    public void editClientProfile(int option, int position) {
        switch (option) {
            case 1 -> {
                System.out.print("New name: ");
                String newName = readStringData();
                ClientRepository.clients.get(position).getProfile().setName(newName);
            }
            case 2 -> {
                System.out.print("New last name: ");
                String newLastName = readStringData();
                ClientRepository.clients.get(position).getProfile().setLastName(newLastName);
            }
            case 3 -> {
                System.out.printf("New birth date%n");
                Date newDate = new Date();
                String date = newDate.birthDate();
                newDate.setBirthDate(date);
                ClientRepository.clients.get(position).getProfile().setBirthDate(newDate);
            }
        }
    }

    public void editAdministratorProfile(int option, int position) {
        switch (option) {
            case 1 -> {
                System.out.print("New name: ");
                String newName = readStringData();
                AdministratorRepository.administrators.get(position).getProfile().setName(newName);
            }
            case 2 -> {
                System.out.print("New last name: ");
                String newLastName = readStringData();
                AdministratorRepository.administrators.get(position).getProfile().setLastName(newLastName);
            }
            case 3 -> {
                System.out.printf("New birth date%n");
                Date newDate = new Date();
                String date = newDate.birthDate();
                newDate.setBirthDate(date);
                AdministratorRepository.administrators.get(position).getProfile().setBirthDate(newDate);
            }
        }
    }
}
