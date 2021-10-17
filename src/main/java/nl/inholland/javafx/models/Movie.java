package nl.inholland.javafx.models;

import nl.inholland.javafx.models.enums.TheaterRoom;

import java.time.LocalDateTime;

public class Movie {
    private String title;
    private Double price;
    private Integer runTime;

    public Movie(String title, Double price, Integer runTime) {
        this.title = title;
        this.price = price;
        this.runTime = runTime;
    }

    // Movie Title
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    // Movie Price
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }

    // Run Time
    public Integer getRunTime() {
        return runTime;
    }
    public void setRunTime(Integer runTime) {
        this.runTime = runTime;
    }
}
