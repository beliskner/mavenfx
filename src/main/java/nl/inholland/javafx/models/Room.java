package nl.inholland.javafx.models;

import nl.inholland.javafx.models.enums.TheaterRoom;

public class Room {
    private TheaterRoom room;
    private Integer seats;

    public Room(TheaterRoom room, Integer seats) {
        this.room = room;
        this.seats = seats;
    }

    // Seats Left
    public Integer getSeats() {
        return seats;
    }
    public void setSeats(Integer seats) {
        this.seats = seats;
    }

    // Theater Room
    public TheaterRoom getTheaterRoom() {
        return room;
    }
    public void setTheaterRoom(TheaterRoom room) {
        this.room = room;
    }
}
