package nl.inholland.javafx.ui.views.purchase;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nl.inholland.javafx.data.Database;
import nl.inholland.javafx.models.Showing;
import nl.inholland.javafx.models.User;
import nl.inholland.javafx.ui.windows.MainWindow;

import java.util.Optional;

public class PurchasePartialView extends GridPane {

    public PurchasePartialView(Database db, Showing showing, User user, Stage stage) {
        this.setVgap(10); // Vertical spacing between grid items
        this.setHgap(8); // Horizontal spacing between grid items

        if (showing != null) {
            // Room
            Label genericRoomLabel = new Label("Room");
            GridPane.setConstraints(genericRoomLabel, 0,0);
            Label roomLabel = new Label(showing.getRoom().getTheaterRoom().toString());
            GridPane.setConstraints(roomLabel, 1,0);

            // Movie
            Label genericMovieLabel = new Label("Movie Title");
            GridPane.setConstraints(genericMovieLabel, 2,0);
            Label movieLabel = new Label(showing.getMovieTitle());
            GridPane.setConstraints(movieLabel, 3,0);

            // Start
            Label genericStartLabel = new Label("Start");
            GridPane.setConstraints(genericStartLabel, 0,1);
            Label startLabel = new Label(showing.getStartTime().toString());
            GridPane.setConstraints(startLabel, 1,1);

            // Seats
            Label genericSeatsLabel = new Label("Seats");
            GridPane.setConstraints(genericSeatsLabel, 2,1);
            ObservableList<Integer> seatsOptions =
                    FXCollections.observableArrayList(
                            0,1,2,3,4,5,6,7,8,9,10
                    );
            ComboBox seatDropdown = new ComboBox(seatsOptions);
            GridPane.setConstraints(seatDropdown, 3,1);

            // Purchase
            Button purchaseButton = new Button("Purchase");
            GridPane.setConstraints(purchaseButton, 4,1);

            // End
            Label genericEndLabel = new Label("End");
            GridPane.setConstraints(genericEndLabel, 0,2);
            Label endLabel = new Label(showing.getStartTime().toString());
            GridPane.setConstraints(endLabel, 1,2);

            // Name field
            Label nameLabel = new Label("Name");
            GridPane.setConstraints(nameLabel, 2, 2);
            TextField nameInput = new TextField();
            nameInput.setPromptText("Enter name");
            GridPane.setConstraints(nameInput, 3, 2);

            // Clear
            Button clearButton = new Button("Clear");
            GridPane.setConstraints(clearButton, 4,2);

            // Clear action
            EventHandler<ActionEvent> clearForm = e -> {
                this.getChildren().clear();
            };
            clearButton.setOnAction(clearForm);

            EventHandler<ActionEvent> purchaseTickets = e -> {
                if (!nameInput.getText().isEmpty()) {
                    if (seatDropdown.getSelectionModel().getSelectedIndex() > 0) {
                        if (showing.getSeatsLeft() >= seatDropdown.getSelectionModel().getSelectedIndex()) {
                            Alert a = new Alert(Alert.AlertType.CONFIRMATION);
                            String confirm = String.format("You are about to purchase %d tickets ", seatDropdown.getSelectionModel().getSelectedIndex()); // Luckily index and value are always the same
                            confirm += String.format("for a total of %.2f moneys, do you confirm this purchase?", seatDropdown.getSelectionModel().getSelectedIndex() * showing.getMoviePrice());
                            a.setContentText(confirm);
                            Optional<ButtonType> result = a.showAndWait();
                            if (result.get() == ButtonType.OK){
                                showing.setSeatsLeft(showing.getSeatsLeft() - seatDropdown.getSelectionModel().getSelectedIndex());
                                db.updateShowing(showing);
                                MainWindow mainWindow = new MainWindow(db, user);
                                mainWindow.getStage().show();
                                stage.close();
                            }
                        } else {
                            Alert a = new Alert(Alert.AlertType.ERROR);
                            a.setContentText("Not enough seats available for your order");
                            a.show();
                        }
                    } else {
                        Alert a = new Alert(Alert.AlertType.ERROR);
                        a.setContentText("Please select number of seats");
                        a.show();
                    }
                } else {
                    Alert a = new Alert(Alert.AlertType.ERROR);
                    a.setContentText("Please fill in your name");
                    a.show();
                }
            };
            purchaseButton.setOnAction(purchaseTickets);

            this.getChildren().addAll(genericRoomLabel, roomLabel, genericMovieLabel, movieLabel,
                    genericStartLabel, startLabel, genericSeatsLabel, seatDropdown, purchaseButton,
                    genericEndLabel, endLabel, nameLabel, nameInput, clearButton);
        }
    }
}
