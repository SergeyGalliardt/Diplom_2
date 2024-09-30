package Data;

import ApiClass.User;
import org.apache.commons.lang3.RandomStringUtils;

public class UserData {

    private String email;
        private String password;
        private String name;

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setName(String name) {
        this.name = name;
    }

        public UserData(String email, String password, String name) {
            this.email = email;
            this.password = password;
            this.name = name;
        }

    public static UserData createNewUser() {
        return new UserData(RandomStringUtils.randomAlphanumeric(12) + "@yandex.ru", "password", "Username");
    }

    public static UserData createExistUser() {
        return new UserData("test-data@yandex.ru", "password", "Username");
        }

    public static UserData createUserWithoutEmail() {
        return new UserData(null, "password", "Username");
    }

    public static UserData createUserWithoutPassword() {
        return new UserData("test-data@yandex.ru", null, "Username");
    }

    public static UserData createUserWithoutName() {
        return new UserData("test-data@yandex.ru", "password", null);
    }

}
