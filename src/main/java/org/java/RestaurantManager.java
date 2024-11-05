package org.java;

import java.time.LocalDateTime;
import java.util.List;

public interface RestaurantManager {

    String addRestaurant(String name, String address, int capacity, LocalDateTime open, LocalDateTime close, List<LocalDateTime> closures);
    String modifyRestaurant(String restaurantId, String name, String address, int capacity, LocalDateTime open, LocalDateTime close, List<LocalDateTime> closures);
    String removeRestaurant(String restaurantId);
    Restaurant getRestaurantById(String restaurantId) ;
}
