import ApiClass.User;
import Data.UserData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


import static org.hamcrest.Matchers.equalTo;

public class LoginUserTests {
    private UserData userData;
    private Response response;
    private final User user = new User();
    private String token;
    private String email;
    private String password;

    @Before
    public void setup() {
        userData = UserData.createNewUser();
        response = user.createUser(userData);
        token = response.then().extract().body().path("accessToken");
    }

    @Test
    @DisplayName("Логин под существующим пользователем")
    public void SuccessLogin() {
        response = user.loginUser(userData = UserData.createExistUser());
        response.then().statusCode(200).assertThat().body("success", equalTo(true));
    }

    @Test
    @DisplayName("Логин с неверным логином и паролем")
    public void loginWithUncorrectLoginAndPassword() {
        email = userData.getEmail();
        userData.setEmail("FredDurst@mail.com");
        password = userData.getPassword();
        userData.setPassword("LimpBizkitIsBetterThenEveryone");
        response = user.loginUser(userData, token);
        userData.setEmail(email);
        userData.setPassword(password);
        response.then().assertThat().body("success", equalTo(false))
                .and().statusCode(401);
    }
    @After
    public void teardown() {
        user.removeUser(token);
    }
}
