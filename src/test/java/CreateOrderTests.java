import ApiClass.Order;
import ApiClass.User;
import Data.OrderData;
import Data.UserData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;

public class CreateOrderTests {
    private final Order order = new Order();
    private final User user = new User();
    private OrderData orderData;
    private String token;
    private Response response;

    @Test
    @DisplayName("Создание заказа с авторизацией")
    public void CreateOrderWithAuth() {
        UserData userData = UserData.createNewUser();
        response = user.createUser(userData);
        token = response.then().extract().body().path("accessToken");
        response = order.createOrder(OrderData.getOrderCorrect(), token);
        user.removeUser(token);
                response.then().assertThat().body("success", equalTo(true))
                .and().statusCode(200);
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void CreateOrderWithoutAuth() {
        response = order.createOrder(OrderData.getOrderCorrect(), "token");
        response.then().assertThat().body("success", equalTo(false))
                .and().statusCode(401);
        //Тест возвращает 200 и true, очевидно баг
    }

    @Test
    @DisplayName("Создание заказа с ингредиентами")
    public void CreateOrderWithIngridients() {
        orderData = OrderData.getOrderCorrect();
        response = order.createOrder(orderData, "token");
        response.then().assertThat().body("success", equalTo(true))
                .and().statusCode(200);
    }

    @Test
    @DisplayName("Создание заказа без ингредиентов")
     public void СreateOrderWithoutIngridients() {
        response = order.createOrder(OrderData.getOrderWithoutIngredients(), "token");
        response.then().assertThat().body("message", equalTo("Ingredient ids must be provided"))
                .and().statusCode(400);
    }

    @Test
    @DisplayName("Создание заказа с неверным хешем ингредиентов")
    public void createOrderWithWrongHash() {
        orderData = OrderData.getOrderWithWrongHash();
        response = order.createOrder(orderData, "token");
        response.then().assertThat().statusCode(500);
    }

}
