package nl.inholland.javafx.models;

import nl.inholland.javafx.models.enums.TheaterRoom;

import java.time.LocalDateTime;
import java.util.concurrent.atomic.AtomicInteger;

public class Showing {
    private static final AtomicInteger count = new AtomicInteger(0);
    private Integer id;
    private Movie movie;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private Room room;
    private Integer seatsLeft;

    // Add redundant fields to satisfy Java FX inability to deal with nested properties for TableView
    private String movieTitle;
    private Double moviePrice;

    public Showing(Movie movie, Room room, LocalDateTime startTime) {
        this.id = count.incrementAndGet();
        this.movie = movie;
        this.room = room;
        this.seatsLeft = room.getSeats();
        this.startTime = startTime;
        this.endTime = startTime.plusMinutes(movie.getRunTime());
        this.moviePrice = movie.getPrice();
        this.movieTitle = movie.getTitle();
    }

    // Id
    public Integer getId() { return id; }

    // Seats Left
    public Integer getSeatsLeft() {
        return seatsLeft;
    }
    public void setSeatsLeft(Integer seatsLeft) {
        this.seatsLeft = seatsLeft;
    }

    // Theater Room
    public Room getRoom() {
        return room;
    }
    public void setTheaterRoom(Room room) {
        this.room = room;
    }

    // Start Time
    public LocalDateTime getStartTime() {
        return startTime;
    }
    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    // End Time
    public LocalDateTime getEndTime() {
        return endTime;
    }
    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public String getMovieTitle() { return movieTitle; }
    public Double getMoviePrice() { return moviePrice; }
}
