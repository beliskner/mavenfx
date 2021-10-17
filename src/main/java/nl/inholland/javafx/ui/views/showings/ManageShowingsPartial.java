package nl.inholland.javafx.ui.views.showings;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nl.inholland.javafx.data.Database;
import nl.inholland.javafx.models.Movie;
import nl.inholland.javafx.models.Room;
import nl.inholland.javafx.models.Showing;
import nl.inholland.javafx.models.User;
import nl.inholland.javafx.ui.windows.LoginWindow;
import nl.inholland.javafx.ui.windows.MainWindow;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ManageShowingsPartial extends GridPane {

    public ManageShowingsPartial(Database db, User user, Stage stage) {
        this.setVgap(10); // Vertical spacing between grid items
        this.setHgap(8); // Horizontal spacing between grid items

        List<String> movieTitles = db.getMovies().stream().map(Movie::getTitle).collect(Collectors.toList());
        List<Room> rooms = db.getRooms();
        List<Movie> movies = db.getMovies();

        // Movie
        Label genericMovieLabel = new Label("Movie Title");
        GridPane.setConstraints(genericMovieLabel, 0,0);
        ObservableList<String> movieOptions =
                FXCollections.observableArrayList(movieTitles);
        ComboBox movieDropdown = new ComboBox(movieOptions);
        GridPane.setConstraints(movieDropdown, 1,0);

        // Start
        Label genericStartLabel = new Label("Start");
        GridPane.setConstraints(genericStartLabel, 2,0);
        DatePicker datePicker = new DatePicker();
        GridPane.setConstraints(datePicker, 3,0);
        TextField time = new TextField();
        time.setText("20:00");
        GridPane.setConstraints(time, 4,0);

        // Room
        Label genericRoomLabel = new Label("Room");
        GridPane.setConstraints(genericRoomLabel, 0,1);
        ObservableList<String> roomOptions =
                FXCollections.observableArrayList("Room 1", "Room 2");
        ComboBox roomDropdown = new ComboBox(roomOptions);
        GridPane.setConstraints(roomDropdown, 1,1);

        // Seats
        Label genericSeatsLabel = new Label("Seats");
        GridPane.setConstraints(genericSeatsLabel, 0,2);
        Label seatsLabel = new Label("");
        GridPane.setConstraints(seatsLabel, 1,2);

        // Add showing
        Button addShowingButton = new Button("Add Showing");
        GridPane.setConstraints(addShowingButton, 4,2);

        // End
        Label genericEndLabel = new Label("End");
        GridPane.setConstraints(genericEndLabel, 2,1);
        Label endLabel = new Label("");
        GridPane.setConstraints(endLabel, 3,1);

        // Name field
        Label genericPriceLabel = new Label("Price");
        GridPane.setConstraints(genericPriceLabel, 2, 2);
        Label priceLabel = new Label("");
        GridPane.setConstraints(priceLabel, 3, 2);

        roomDropdown.valueProperty().addListener((ChangeListener<String>) (ov, t, t1) -> {
            Optional<Room> room = rooms.stream().filter(x -> x.getTheaterRoom().toString() == t1).findFirst();
            room.ifPresent(value -> seatsLabel.setText(value.getSeats().toString()));
        });

        time.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                LocalTime t = LocalTime.parse(newValue + ":00", DateTimeFormatter.ofPattern("H:mm:ss"));
                if(t != null && datePicker.getValue() != null) {
                    LocalDateTime endDate = datePicker.getValue().atTime(t);
                    Optional<Movie> movie = movies.stream().filter(x -> x.getTitle() == movieDropdown.getValue()).findFirst();
                    if (movie.isPresent()) {
                        endDate = endDate.plusMinutes(movie.get().getRunTime());
                        endLabel.setText(endDate.toString());
                    }
                }
            } catch(Exception e) {
                System.out.println(e);
            }
        });

        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            LocalTime t = LocalTime.parse(time.getText() + ":00", DateTimeFormatter.ofPattern("H:mm:ss"));
            if(t != null && datePicker.getValue() != null) {
                LocalDateTime endDate = datePicker.getValue().atTime(t);
                Optional<Movie> movie = movies.stream().filter(x -> x.getTitle() == movieDropdown.getValue()).findFirst();
                if (movie.isPresent()) {
                    endDate = endDate.plusMinutes(movie.get().getRunTime());
                    endLabel.setText(endDate.toString());
                }
            }
        });

        movieDropdown.valueProperty().addListener((ChangeListener<String>) (ov, t, t1) -> {
            Optional<Movie> movie = movies.stream().filter(x -> x.getTitle() == t1).findFirst();
            if (movie.isPresent()) {
                priceLabel.setText(movie.get().getPrice().toString());
                if(time.getText() != null && datePicker.getValue() != null) {
                    try {
                        LocalTime parsedtime = LocalTime.parse(time.getText() + ":00", DateTimeFormatter.ofPattern("H:mm:ss"));
                        LocalDateTime endDate = datePicker.getValue().atTime(parsedtime);
                        endDate = endDate.plusMinutes(movie.get().getRunTime());
                        endLabel.setText(endDate.toString());
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                }
            }
        });

        EventHandler<ActionEvent> event = e -> {
            try {
                Optional<Movie> movie = movies.stream().filter(x -> x.getTitle() == movieDropdown.getValue()).findFirst();
                Optional<Room> room = rooms.stream().filter(x -> x.getTheaterRoom().toString() == roomDropdown.getValue()).findFirst();
                LocalTime parsedtime = LocalTime.parse(time.getText() + ":00", DateTimeFormatter.ofPattern("H:mm:ss"));
                LocalDateTime startDate = datePicker.getValue().atTime(parsedtime);
                if(movie.isPresent() && room.isPresent()) {
                    Boolean showingAdded = db.addShowing(new Showing(movie.get(), room.get(), startDate));
                    if(showingAdded) {
                        Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                        a.setContentText("Showing succesfully added to the list.");
                        Optional<ButtonType> result = a.showAndWait();
                        if (result.get() == ButtonType.OK){
                            MainWindow mainWindow = new MainWindow(db, user);
                            mainWindow.getStage().show();
                            stage.close();
                        }
                    } else {
                        Alert b = new Alert(Alert.AlertType.ERROR);
                        b.setContentText("A showing is already booked for this timeslot. Try a different timeslot.");
                        b.show();
                    }
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Could not book a showing for this information. Did you input the correct time?");
                    a.show();
                }
            } catch (Exception ex) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Could not book a showing for this information. Did you input the correct time?");
                a.show();
            }

        };
        addShowingButton.setOnAction(event);

        this.getChildren().addAll(genericRoomLabel, roomDropdown, genericMovieLabel,
                genericStartLabel, datePicker, genericSeatsLabel, seatsLabel, movieDropdown, addShowingButton,
                genericEndLabel, genericPriceLabel, priceLabel, time, endLabel);
    }
}
