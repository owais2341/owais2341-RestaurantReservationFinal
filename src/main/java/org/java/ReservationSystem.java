package org.java;

import java.time.LocalDateTime;
public interface ReservationSystem {
    String addReservation(String restaurantId, String guestName, String guestContact, LocalDateTime dateTime, int numPeople);
    String modifyReservation(String reservationId, String restaurantId, String guestName, String guestContact, LocalDateTime dateTime, int numPeople);
    String removeReservation(String reservationId);
}