import java.util.ArrayList;

    public class Administrator extends User {
        private ArrayList<Permission> permissions1 = new ArrayList<>();
        private boolean isSuperAdmin;

        public Administrator(Profile profile, String username, String password, boolean isSuperAdmin) {
            super(profile, username, password);
            this.isSuperAdmin = isSuperAdmin;
        }

        public ArrayList<Permission> getPermissions() {
            return permissions1;
        }

        public void setDelete() {
            this.permissions1.add(Permission.DELETE);
        }

        public void setWrite() {
            this.permissions1.add(Permission.WRITE);
        }

        public void setRead() {
            this.permissions1.add(Permission.READ);
        }

        public boolean isSuperAdmin() {
            return isSuperAdmin;
        }

        public void setSuperAdmin(boolean superAdmin) {
            isSuperAdmin = superAdmin;
        }
    }

