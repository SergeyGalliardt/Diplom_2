package ApiClass;

import Data.UserData;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import static Constants.Constants.*;
import static io.restassured.RestAssured.given;

public class User {
    @Step("Создание пользователя")
    public Response createUser(UserData user) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(URL)
                .body(user).post(CreateUser);
    }

    @Step("Авторизация пользователя")
    public Response loginUser(UserData userData) {
        return given()
                .contentType(ContentType.JSON)
                .baseUri(URL)
                .body(userData).post(LoginUser);
    }

    @Step("Авторизация пользователя")
    public Response loginUser(UserData userData, String token) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .baseUri(URL)
                .body(userData).post(LoginUser);
    }

    @Step("Изменение данных пользователя")
    public Response updateUser(UserData newUser, String token) {
        return given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .baseUri(URL)
                .body(newUser).patch(ChangeUserData);
    }

    @Step("Удаление пользователя")
    public void removeUser(String token) {
        given()
                .contentType(ContentType.JSON)
                .header("Authorization", token)
                .baseUri(URL).delete(ChangeUserData);
    }
}
