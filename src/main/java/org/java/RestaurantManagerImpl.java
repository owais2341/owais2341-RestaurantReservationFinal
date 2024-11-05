package org.java;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RestaurantManagerImpl implements RestaurantManager{
    private final Map<String, Restaurant> restaurantMap = new HashMap<>();

    @Override
    public String addRestaurant(String name, String address, int capacity, LocalDateTime openingTime, LocalDateTime closingTime, List<LocalDateTime> closures) {
        validateRestaurantDetails(name, address, capacity, openingTime, closingTime, closures);

        // Check for existing restaurant with the same details
        for (Restaurant restaurant : restaurantMap.values()) {
            if (restaurant.getName().equals(name) && restaurant.getAddress().equals(address) &&
                    restaurant.getOpeningTime().equals(openingTime) && restaurant.getClosingTime().equals(closingTime)) {
                return restaurant.getId();
            }
        }

        String restaurantId = UUID.randomUUID().toString();
        Restaurant restaurant = new Restaurant(restaurantId, name, address, capacity, openingTime, closingTime, closures);
        restaurantMap.put(restaurantId, restaurant);
        return restaurantId;
    }
    @Override
    public String modifyRestaurant(String restaurantId, String name, String address, int capacity, LocalDateTime openingTime, LocalDateTime closingTime, List<LocalDateTime> closures) {
        Restaurant restaurant = getRestaurantById(restaurantId); // Retrieve the existing restaurant by ID

        // Update the restaurant properties
        restaurant.setName(name);
        restaurant.setAddress(address);
        restaurant.setCapacity(capacity);
        restaurant.setOpeningTime(openingTime);
        restaurant.setClosingTime(closingTime);
        restaurant.setClosureDates(closures);

        return restaurantId; // Return the original ID
    }

    @Override
    public String removeRestaurant(String restaurantId) {
        if (restaurantMap.remove(restaurantId) == null) {
            throw new RuntimeException("Restaurant with ID " + restaurantId + " does not exist.");
        }
        return restaurantId;
    }

    @Override
    public Restaurant getRestaurantById(String restaurantId) {
        Restaurant restaurant = restaurantMap.get(restaurantId);
        if (restaurant == null) {
            throw new RuntimeException("Restaurant with ID " + restaurantId + " does not exist.");
        }
        return restaurant;
    }

    private void validateRestaurantDetails(String name, String address, int capacity, LocalDateTime openingTime, LocalDateTime closingTime, List<LocalDateTime> closures) {
        if (name == null || address == null || openingTime == null || closingTime == null || closures == null) {
            throw new IllegalArgumentException("None of the input parameters can be null.");
        }
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity must be a positive number.");
        }
        if (!openingTime.isBefore(closingTime)) {
            throw new IllegalArgumentException("Opening time must be before closing time.");
        }
        if (closingTime.isBefore(openingTime.plusHours(8))) {
            throw new IllegalArgumentException("The restaurant must be open for at least 8 hours.");
        }
        for (LocalDateTime closure : closures) {
            if (closure.isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("Closure dates must be in the future.");
            }
        }
    }
}
