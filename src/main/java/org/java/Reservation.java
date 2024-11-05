package org.java;

import java.time.LocalDateTime;

public class Reservation {
    private final String id;
    private final String restaurantId;
    private final String guestName;
    private final String guestContact;
    private final LocalDateTime dateTime;
    private final int numPeople;

    public Reservation(String id, String restaurantId, String guestName, String guestContact, LocalDateTime dateTime, int numPeople) {
        this.id = id;
        this.restaurantId = restaurantId;
        this.guestName = guestName;
        this.guestContact = guestContact;
        this.dateTime = dateTime;
        this.numPeople = numPeople;
    }

    public String getId() {
        return id;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getGuestContact() {
        return guestContact;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public int getNumPeople() {
        return numPeople;
    }
}