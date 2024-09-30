import ApiClass.Order;
import ApiClass.User;
import Data.OrderData;
import Data.UserData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class GetOrderTests {
    private OrderData orderData;
    private Response response;
    private final Order order = new Order();
    private final User user = new User();
    private String token;

    @Test
    @DisplayName("Получить заказ от авторизованного пользователя")
        public void GetOrderWithAuth() {
        UserData userData = UserData.createNewUser();
        response = user.createUser(userData);
        token = response.then().extract().body().path("accessToken");
        orderData = OrderData.getOrderCorrect();
        response = order.createOrder(orderData, token);
        response = order.getUserOrders(token);
        user.removeUser(token);
        response.then().assertThat().body("orders", notNullValue())
                .and().statusCode(200);
    }

    @Test
    @DisplayName("Получить заказ от неавторизованного пользователя")
    public void getOrdersWithoutAuth() {
        orderData = OrderData.getOrderCorrect();
        response = order.createOrder(orderData, "token");
        response = order.getUserOrders("token");
        response.then().assertThat().body("message", equalTo("You should be authorised"))
                .and().statusCode(401);
    }
}
