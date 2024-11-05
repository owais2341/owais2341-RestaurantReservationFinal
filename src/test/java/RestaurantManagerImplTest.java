import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import org.java.RestaurantManagerImpl;
import org.java.Restaurant;

class RestaurantManagerImplTest {
    private RestaurantManagerImpl restaurantManager;

    @BeforeEach
    void setUp() {
        restaurantManager = new RestaurantManagerImpl();
    }

    @Test
    void addRestaurantWithValidDetails() {
        LocalDateTime openingTime = LocalDateTime.of(2023, 10, 1, 9, 0);
        LocalDateTime closingTime = LocalDateTime.of(2023, 10, 1, 18, 0);
        List<LocalDateTime> closures = Collections.singletonList(LocalDateTime.now().plusDays(30));

        String restaurantId = restaurantManager.addRestaurant("Test Restaurant", "123 Test St", 50, openingTime, closingTime, closures);

        assertNotNull(restaurantId);
        assertNotNull(restaurantManager.getRestaurantById(restaurantId));
    }

    @Test
    void addRestaurantWithNullNameThrowsException() {
        LocalDateTime openingTime = LocalDateTime.of(2023, 10, 1, 9, 0);
        LocalDateTime closingTime = LocalDateTime.of(2023, 10, 1, 18, 0);
        List<LocalDateTime> closures = Collections.singletonList(LocalDateTime.now().plusDays(30));

        assertThrows(IllegalArgumentException.class, () -> {
            restaurantManager.addRestaurant(null, "123 Test St", 50, openingTime, closingTime, closures);
        });
    }

    @Test
    void addRestaurantWithNegativeCapacityThrowsException() {
        LocalDateTime openingTime = LocalDateTime.of(2023, 10, 1, 9, 0);
        LocalDateTime closingTime = LocalDateTime.of(2023, 10, 1, 18, 0);
        List<LocalDateTime> closures = Collections.singletonList(LocalDateTime.now().plusDays(30));

        assertThrows(IllegalArgumentException.class, () -> {
            restaurantManager.addRestaurant("Test Restaurant", "123 Test St", -10, openingTime, closingTime, closures);
        });
    }

    @Test
    void addRestaurantWithClosingTimeBeforeOpeningTimeThrowsException() {
        LocalDateTime openingTime = LocalDateTime.of(2023, 10, 1, 18, 0);
        LocalDateTime closingTime = LocalDateTime.of(2023, 10, 1, 9, 0);
        List<LocalDateTime> closures = Collections.singletonList(LocalDateTime.now().plusDays(30));

        assertThrows(IllegalArgumentException.class, () -> {
            restaurantManager.addRestaurant("Test Restaurant", "123 Test St", 50, openingTime, closingTime, closures);
        });
    }

    @Test
    void addRestaurantWithClosureDateInPastThrowsException() {
        LocalDateTime openingTime = LocalDateTime.of(2023, 10, 1, 9, 0);
        LocalDateTime closingTime = LocalDateTime.of(2023, 10, 1, 18, 0);
        List<LocalDateTime> closures = Collections.singletonList(LocalDateTime.now().minusDays(1));

        assertThrows(IllegalArgumentException.class, () -> {
            restaurantManager.addRestaurant("Test Restaurant", "123 Test St", 50, openingTime, closingTime, closures);
        });
    }

    @Test
    void modifyRestaurantWithValidDetails() {
        LocalDateTime openingTime = LocalDateTime.of(2023, 10, 1, 9, 0);
        LocalDateTime closingTime = LocalDateTime.of(2023, 10, 1, 18, 0);
        List<LocalDateTime> closures = Collections.singletonList(LocalDateTime.now().plusDays(30));

        String restaurantId = restaurantManager.addRestaurant("Test Restaurant", "123 Test St", 50, openingTime, closingTime, closures);

        LocalDateTime newOpeningTime = LocalDateTime.of(2023, 10, 1, 10, 0);
        LocalDateTime newClosingTime = LocalDateTime.of(2023, 10, 1, 20, 0);
        List<LocalDateTime> newClosures = Collections.singletonList(LocalDateTime.now().plusDays(60));

        restaurantManager.modifyRestaurant(restaurantId, "Updated Restaurant", "456 Updated St", 100, newOpeningTime, newClosingTime, newClosures);

        Restaurant updatedRestaurant = restaurantManager.getRestaurantById(restaurantId);
        assertEquals("Updated Restaurant", updatedRestaurant.getName());
        assertEquals("456 Updated St", updatedRestaurant.getAddress());
        assertEquals(100, updatedRestaurant.getCapacity());
        assertEquals(newOpeningTime, updatedRestaurant.getOpeningTime());
        assertEquals(newClosingTime, updatedRestaurant.getClosingTime());
        assertEquals(newClosures, updatedRestaurant.getClosureDates());
    }

    @Test
    void removeRestaurantWithValidId() {
        LocalDateTime openingTime = LocalDateTime.of(2023, 10, 1, 9, 0);
        LocalDateTime closingTime = LocalDateTime.of(2023, 10, 1, 18, 0);
        List<LocalDateTime> closures = Collections.singletonList(LocalDateTime.now().plusDays(30));

        String restaurantId = restaurantManager.addRestaurant("Test Restaurant", "123 Test St", 50, openingTime, closingTime, closures);

        String removedRestaurantId = restaurantManager.removeRestaurant(restaurantId);

        assertEquals(restaurantId, removedRestaurantId);
        assertThrows(RuntimeException.class, () -> {
            restaurantManager.getRestaurantById(restaurantId);
        });
    }

    @Test
    void getRestaurantByIdWithInvalidIdThrowsException() {
        assertThrows(RuntimeException.class, () -> {
            restaurantManager.getRestaurantById("invalid-id");
        });
    }

    // Test that modifying a non-existent restaurant throws a RuntimeException.
    @Test
    void modifyNonExistentRestaurantThrowsException() {
        assertThrows(RuntimeException.class, () -> {
            restaurantManager.modifyRestaurant("non-existent-id", "Updated Restaurant", "456 Updated St", 100, LocalDateTime.now(), LocalDateTime.now(), Collections.emptyList());
        });
    }

    // Test that adding a duplicate restaurant (same name, address, and timings) does not create a duplicate entry in the system.
    @Test
    void addDuplicateRestaurant() {
        LocalDateTime openingTime = LocalDateTime.of(2023, 10, 1, 9, 0);
        LocalDateTime closingTime = LocalDateTime.of(2023, 10, 1, 18, 0);
        List<LocalDateTime> closures = Collections.singletonList(LocalDateTime.now().plusDays(30));

        String restaurantId1 = restaurantManager.addRestaurant("Test Restaurant", "123 Test St", 50, openingTime, closingTime, closures);
        String restaurantId2 = restaurantManager.addRestaurant("Test Restaurant", "123 Test St", 50, openingTime, closingTime, closures);

        assertEquals(restaurantId1, restaurantId2);
    }
}