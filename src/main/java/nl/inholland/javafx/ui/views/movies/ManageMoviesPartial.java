package nl.inholland.javafx.ui.views.movies;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nl.inholland.javafx.data.Database;
import nl.inholland.javafx.models.Movie;
import nl.inholland.javafx.models.Showing;
import nl.inholland.javafx.models.User;
import nl.inholland.javafx.ui.windows.MainWindow;

import java.util.Optional;

public class ManageMoviesPartial extends GridPane {

    public ManageMoviesPartial(Database db, User user, Stage stage) {
        this.setVgap(10); // Vertical spacing between grid items
        this.setHgap(8); // Horizontal spacing between grid items

        // Movie Title
        Label movieLabel = new Label("Movie Title");
        GridPane.setConstraints(movieLabel, 0,0);
        TextField movieTitleInput = new TextField();
        GridPane.setConstraints(movieTitleInput, 1,0);

        // Runtime field
        Label runTimeLabel = new Label("Runtime");
        GridPane.setConstraints(runTimeLabel, 0, 1);
        TextField runTimeInput = new TextField();
        runTimeInput.setText("200");
        GridPane.setConstraints(runTimeInput, 1, 1);

        // Price field
        Label priceLabel = new Label("Price");
        GridPane.setConstraints(priceLabel, 0, 2);
        TextField priceInput = new TextField();
        priceInput.setText("12.50");
        GridPane.setConstraints(priceInput, 1, 2);

        // Purchase
        Button addMovieButton = new Button("Add movie");
        GridPane.setConstraints(addMovieButton, 2,2);

        EventHandler<ActionEvent> addMovieClick = e -> {
            try {
                Integer runTime = Integer.parseInt(runTimeInput.getText());
                Double price = Double.parseDouble(priceInput.getText());
                if(movieTitleInput.getText() != null && movieTitleInput.getText().length() > 0) {
                    Movie movie = new Movie(movieTitleInput.getText(), price, runTime);
                    db.addMovie(movie);
                    MainWindow mainWindow = new MainWindow(db, user);
                    mainWindow.getStage().show();
                    stage.close();
                }
            } catch (Exception ex) {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText(String.format("One or more of the fields incorrect. Error message: %s", ex));
                a.show();
            }
        };
        addMovieButton.setOnAction(addMovieClick);

        this.getChildren().addAll(movieLabel, movieTitleInput, runTimeLabel, runTimeInput, priceLabel, priceInput, addMovieButton);
    }
}
