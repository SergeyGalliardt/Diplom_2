import ApiClass.User;
import Data.UserData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

public class ChangeUserDataTests {
    private UserData userData;
    private Response response;
    private final User user = new User();
    private String token;
    private String email;
    private String password;
    private String name;

    @Before
    public void setup() {
        userData = UserData.createNewUser();
        response = user.createUser(userData);
        token = response.then().extract().body().path("accessToken");
    }

    @Test
    @DisplayName("Изменение имени с авторизацией")
    public void changeNameTest() {
        name = userData.getName();
        userData.setName(name + "Username");
        response = user.updateUser(userData, token);
        userData.setName(name);
        response.then().assertThat().body("success", equalTo(true))
                .and().statusCode(200);
    }


    @Test
    @DisplayName("Изменение пароля с авторизацией")
    public void changePasswordTest() {
        password = userData.getPassword();
        userData.setPassword(password + "password");
        response = user.updateUser(userData, token);
        userData.setPassword(password);
        response.then().assertThat().body("success", equalTo(true))
                .and().statusCode(200);
    }

    @Test
    @DisplayName("Изменение email с авторизацией")
    public void changeEmailTest() {
        email = userData.getEmail();
        userData.setEmail(email + "test-data@yandex.ru");
        response = user.updateUser(userData, token);
        userData.setEmail(email);
        response.then().assertThat().body("success", equalTo(true))
                .and().statusCode(200);
    }

    @Test
    @DisplayName("Изменение имени без авторизации")
    public void changeNameWithoutAuthorization() {
        name = userData.getName();
        userData.setName(name + "Username");
        response = user.updateUser(userData, "notaccesstoken");
        userData.setName(name);
        response.then().assertThat().body("success", equalTo(false))
                .and().statusCode(401)
                .body("message", is("You should be authorised"));;
    }

    @Test
    @DisplayName("Изменение пароля без авторизации")
        public void changePasswordWithoutAuthorization() {
        password = userData.getPassword();
        userData.setPassword(password + "password");
        response = user.updateUser(userData, "notaccesstoken");
        userData.setPassword(password);
        response.then().assertThat().body("success", equalTo(false))
                .and().statusCode(401)
                .body("message", is("You should be authorised"));;
    }

    @Test
    @DisplayName("Изменение email без авторизации")
    public void changeEmailWithoutAuthorization() {
        email = userData.getEmail();
        userData.setEmail(email + "test-data@yandex.ru");
        response = user.updateUser(userData, "notaccesstoken");
        userData.setEmail(email);
        response.then().assertThat().body("success", equalTo(false))
                .and().statusCode(401)
                .body("message", is("You should be authorised"));;
    }

    @After
    public void teardown() {
        user.removeUser(token);
    }
}
