package org.java;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Pattern;

public class ReservationSystemImpl implements ReservationSystem {
    private final Map<String, Reservation> reservationMap = new HashMap<>();
    private final Map<String, TreeMap<LocalDateTime, Reservation>> restaurantReservations = new HashMap<>();
    private final RestaurantManager restaurantManager;

    public ReservationSystemImpl(RestaurantManager restaurantManager) {
        this.restaurantManager = restaurantManager;
    }

    @Override
    public String addReservation(String restaurantId, String guestName, String guestContact, LocalDateTime dateTime, int numPeople) {
        validateReservationDetails(restaurantId, guestName, guestContact, dateTime, numPeople);

        String reservationId = UUID.randomUUID().toString();
        Reservation reservation = new Reservation(reservationId, restaurantId, guestName, guestContact, dateTime, numPeople);
        reservationMap.put(reservationId, reservation);

        restaurantReservations.computeIfAbsent(restaurantId, k -> new TreeMap<>()).put(dateTime, reservation);
        return reservationId;
    }

    @Override
    public String modifyReservation(String reservationId, String restaurantId, String guestName, String guestContact, LocalDateTime dateTime, int numPeople) {
        removeReservation(reservationId);
        return addReservation(restaurantId, guestName, guestContact, dateTime, numPeople);
    }

    @Override
    public String removeReservation(String reservationId) {
        Reservation reservation = reservationMap.remove(reservationId);
        if (reservation == null) {
            throw new RuntimeException(String.format("Reservation with ID %s does not exist.", reservationId));
        }
        restaurantReservations.get(reservation.getRestaurantId()).remove(reservation.getDateTime());
        return reservationId;
    }

    private void validateReservationDetails(String restaurantId, String guestName, String guestContact, LocalDateTime dateTime, int numPeople) {
        if (restaurantId == null || guestName == null || guestContact == null || dateTime == null) {
            throw new IllegalArgumentException("None of the input parameters can be null.");
        }
        if (!Pattern.matches("\\+?[0-9. ()-]{7,25}", guestContact)) {
            throw new IllegalArgumentException(String.format("Invalid phone number: %s", guestContact));
        }
        Restaurant restaurant = restaurantManager.getRestaurantById(restaurantId);
        if (restaurant == null) {
            throw new IllegalArgumentException(String.format("Restaurant with ID %s does not exist.", restaurantId));
        }
        if (!restaurant.IsReservationDateTimeValid(dateTime, 2)) {
            throw new IllegalArgumentException("Reservation time is not valid.");
        }
        int totalPeople = numPeople;
        TreeMap<LocalDateTime, Reservation> reservations = restaurantReservations.get(restaurantId);
        if (reservations != null) {
            for (Reservation reservation : reservations.values()) {
                if (reservationsOverlap(reservation, dateTime, 2)) {
                    totalPeople += reservation.getNumPeople();
                }
            }
        }
        if (totalPeople > restaurant.getCapacity()) {
            throw new IllegalArgumentException("Total number of people exceeds restaurant capacity.");
        }
    }

    private boolean reservationsOverlap(Reservation reservation, LocalDateTime newDateTime, int durationInHours) {
        LocalDateTime existingStart = reservation.getDateTime();
        LocalDateTime existingEnd = existingStart.plusHours(2);
        LocalDateTime newEnd = newDateTime.plusHours(durationInHours);
        return newDateTime.isBefore(existingEnd) && newEnd.isAfter(existingStart);
    }
}