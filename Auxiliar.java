import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;
import java.util.Scanner;

public class Auxiliar {
    Scanner scanner = new Scanner(System.in);
    Random random = new Random();

    /**
     * Read int data
     *
     * @param scanner is the scanner that the method reads
     * @return data converted in Int type
     */
    public static int ReadIntData(Scanner scanner) {
        String dataInt = scanner.nextLine();
        return Integer.parseInt(dataInt);
    }

    /**
     * Read String data
     *
     * @param scanner is the scanner that the method reads
     * @return returns the string after read it
     */
    public static String ReadStringData(Scanner scanner) {
        return scanner.nextLine();
    }

    /**
     * hashString for passwords
     *
     * @param input is the password of the user
     * @return the codification password
     */
    public static String hashString(String input) {
        try {
            // Create MessageDigest instance for SHA-256
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            // Calculate the SHA-256 hash value
            byte[] hash = md.digest(input.getBytes());

            // Convert byte array to hexadecimal string
            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            System.err.println("SHA-256 algorithm not found.");
            return null;
        }
    }

    /**
     * This method is using to check if the username is registered
     *
     * @param userName is the username of the user
     * @return if the username is registered
     */
    public boolean checkUserLog(String userName) {
        boolean registered = false;

        for (Administrator admin : AdministratorRepository.administrators) {
            if (admin.getUsername().equals(userName)) {
                registered = true;
            }
        }
        for (Client client : ClientRepository.clients) {
            if (client.getUsername().equals(userName)) {
                registered = true;
            }
        }
        return registered;
    }

    /**
     * This method is to get a user
     *
     * @param userName is the username
     * @return the user, it can be an admin or a client
     */
    public User getUSer(String userName) {
        User user = new User();
        for (Administrator admin : AdministratorRepository.administrators) {
            if (admin.getUsername().equals(userName)) {
                user = admin;
            }
        }
        for (Client client : ClientRepository.clients) {
            if (client.getUsername().equals(userName)) {
                user = client;
            }
        }
        return user;
    }

    /**
     * this method is using to know if the user is an admin
     *
     * @param userName is the userName
     * @return if is an admin or not
     */
    public boolean isAdmin(String userName) {
        boolean is = false;
        for (Administrator admin : AdministratorRepository.administrators) {
            if (admin.getUsername().equals(userName)) {
                is = true;
            }
        }
        return is;
    }

    /**
     * This method creates a username
     *
     * @param profile is the profile of the user
     * @return the username
     */
    public String createUserName(Profile profile) {
        String id = "";
        for (int i = 0; i < 3; i++) {
            int ran = random.nextInt(10);
            String ids = String.valueOf(ran);
            id += ids;
        }
        id = profile.getName() + id;
        return id.toLowerCase();
    }

    /**
     * This method is using to create the menu of admins using their permissions
     *
     * @param administrator is the administrator to read the permissions that it has
     */
    public static void createMenu(Administrator administrator) {
        System.out.printf("Permissions of %s %n", administrator.getProfile().getName());
        for (Permission per : administrator.getPermissions()) {
            System.out.print(String.valueOf(per) + "\n");
        }
    }
}
