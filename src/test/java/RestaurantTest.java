import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RestaurantTest {
    @Spy
    Restaurant restaurant = new Restaurant("Amelie's cafe", "Chennai",
            LocalTime.parse("10:30:00"), LocalTime.parse("22:00:00"));
    LocalTime currentTime;

    @BeforeEach
    public void setUp() {
        restaurant.addToMenu("Sweet corn soup", 119);
        restaurant.addToMenu("Vegetable lasagne", 269);
        currentTime = LocalTime.now();
    }


    @Test
    public void is_restaurant_open_should_return_true_if_time_is_between_opening_and_closing_time() {
        when(restaurant.getCurrentTime()).thenReturn(LocalTime.parse("11:30:00"));
        assertTrue(restaurant.isRestaurantOpen());
    }

    @Test
    public void is_restaurant_open_should_return_false_if_time_is_outside_opening_and_closing_time() {
        when(restaurant.getCurrentTime()).thenReturn(LocalTime.parse("08:30:00"));
        assertFalse(restaurant.isRestaurantOpen());
    }

    @Test
    public void adding_item_to_menu_should_increase_menu_size_by_1() {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.addToMenu("Sizzling brownie", 319);
        assertEquals(initialMenuSize + 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_from_menu_should_decrease_menu_size_by_1() throws itemNotFoundException {
        int initialMenuSize = restaurant.getMenu().size();
        restaurant.removeFromMenu("Vegetable lasagne");
        assertEquals(initialMenuSize - 1, restaurant.getMenu().size());
    }

    @Test
    public void removing_item_that_does_not_exist_should_throw_exception() {
        assertThrows(itemNotFoundException.class,
                () -> restaurant.removeFromMenu("French fries"));
    }

    @Test void given_list_of_items_total_should_be_returned() {
        final int price1 = 100;
        final int price2 = 50;
        restaurant.addToMenu("Fried rice",price1);
        restaurant.addToMenu("Ice cream", price2);
        List<String> itemsToPass = List.of("Fried rice", "Ice cream");
        assertEquals(price1 + price2, restaurant.getTotal(itemsToPass));
    }
}