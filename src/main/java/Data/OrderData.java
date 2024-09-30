package Data;

import java.util.ArrayList;
import java.util.List;

public class OrderData {

    private List<String> ingredients = new ArrayList<>();

    public OrderData(List<String> ingredients) {
        this.ingredients = ingredients;
    }

        public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

        public List<String> getIngredients() {
            return ingredients;
        }

    public static OrderData getOrderCorrect() {
        return new OrderData(List.of("61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa71", "61c0c5a71d1f82001bdaaa73"));
    }

        public static OrderData getOrderWithoutIngredients() {
            return new OrderData(List.of());
        }

        public static OrderData getOrderWithWrongHash() {
            return new OrderData(List.of("1", "2"));
        }

}
