package ApiClass;

import Data.OrderData;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static Constants.Constants.*;

import static io.restassured.RestAssured.given;

public class Order {
    @Step("Создание заказа")
    public Response createOrder(OrderData orderData, String token) {

        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .baseUri(URL)
                .body(orderData)
                .post(Orders);
    }

    @Step("Получение заказаов пользователя")
    public Response getUserOrders(String token) {

        return given()
                .header("Content-Type", "application/json")
                .header("Authorization", token)
                .baseUri(URL)
                .get(Orders);
    }
}
