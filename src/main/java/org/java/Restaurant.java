package org.java;

import java.time.LocalDateTime;
import java.util.List;

public class Restaurant {
    private String id;
    private String name;
    private String address;
    private int capacity;
    private LocalDateTime openingTime;
    private LocalDateTime closingTime;
    private List<LocalDateTime> closureDates;

    public Restaurant(String id, String name, String address, int capacity, LocalDateTime openingTime, LocalDateTime closingTime, List<LocalDateTime> closureDates) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.closureDates = closureDates;
    }
    public boolean IsReservationDateTimeValid(LocalDateTime dateTime, int durationInHours) {
        if(dateTime == null) {
            return false;
        }
        if (dateTime.toLocalDate().isBefore(LocalDateTime.now().toLocalDate())) {
            return false;
        }
        if (closureDates.contains(dateTime.toLocalDate())) {
            return false;
        }
        LocalDateTime reservationTime = dateTime;
        LocalDateTime reservationEndTime = reservationTime.plusHours(durationInHours);
        if (reservationTime.isBefore(openingTime) || reservationEndTime.isAfter(closingTime)) {
            return false;
        }
        return true;
    }
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getCapacity() {
        return capacity;
    }

    public LocalDateTime getOpeningTime() {
        return openingTime;
    }

    public LocalDateTime getClosingTime() {
        return closingTime;
    }

    public List<LocalDateTime> getClosureDates() {
        return closureDates;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setOpeningTime(LocalDateTime openingTime) {
        this.openingTime = openingTime;
    }

    public void setClosingTime(LocalDateTime closingTime) {
        this.closingTime = closingTime;
    }

    public void setClosureDates(List<LocalDateTime> closureDates) {
        this.closureDates = closureDates;
    }
}