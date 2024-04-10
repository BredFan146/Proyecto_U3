public class User {
    private Profile profile;
    private String username;
    private String password;

    public User(Profile profile, String username, String password) {
        this.profile = profile;
        this.username = username;
        this.password = Auxiliar.hashString(password);
    }

    public User() {
    }

    public Profile getProfile() {
        return profile;
    }

    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username.toLowerCase();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = Auxiliar.hashString(password);
    }

    public static boolean checkPassword(String password, User user) {
        boolean ret = false;
        if (Auxiliar.hashString(password).equals(user.getPassword())) {
            ret = true;
        }
        return ret;
    }

}
