import ApiClass.User;
import Data.UserData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class CreateUserTest {
    private UserData userData;
    private final User user = new User();
    private Response response;

    @Test
    @DisplayName("Создание уникального пользователя")
    public void successCreateUserTest() {
        userData = UserData.createNewUser();
        response = user.createUser(userData);
        String token = response.then().extract().body().path("accessToken");
        response.then().assertThat().body("accessToken", notNullValue())
                .and().statusCode(200);
    }
    @Test
    @DisplayName("Cоздание пользователя, который уже зарегистрирован")
        public void doubleCreateUserTest() {
        userData = UserData.createExistUser();
        response = user.createUser(userData);
        response.then().assertThat().body("message", equalTo("User already exists"))
                .and().statusCode(403);
    }

    @Test
    @DisplayName("Cоздание пользователя без имени")
        public void createUserWithoutNameTest() {
        userData = UserData.createUserWithoutName();
        response = user.createUser(userData);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and().statusCode(403);
    }

    @Test
    @DisplayName("Cоздание пользователя без email")
        public void createUserWithoutEmailTest() {
        userData = UserData.createUserWithoutEmail();
        response = user.createUser(userData);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and().statusCode(403);
    }

    @Test
    @DisplayName("Cоздание пользователя без пароля")
       public void createUserWithoutPasswordTest() {
        userData = UserData.createUserWithoutPassword();
        response = user.createUser(userData);
        response.then().assertThat().body("message", equalTo("Email, password and name are required fields"))
                .and().statusCode(403);
    }

}

