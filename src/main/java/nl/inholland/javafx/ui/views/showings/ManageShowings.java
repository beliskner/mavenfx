package nl.inholland.javafx.ui.views.showings;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nl.inholland.javafx.data.Database;
import nl.inholland.javafx.models.Room;
import nl.inholland.javafx.models.Showing;
import nl.inholland.javafx.models.User;
import nl.inholland.javafx.ui.views.purchase.PurchasePartialView;

import java.util.List;
import java.util.stream.Collectors;

public class ManageShowings extends VBox {
    private Database db;
    private List<Showing> showings;
    private User user;
    private Stage stage;

    public ManageShowings(Database db, User user, Stage stage) {
        this.db = db;
        this.user = user;
        this.stage = stage;
        this.showings = db.getShowings();

        this.setPadding(new Insets(20));
        this.setSpacing(20);

        HBox roomsBox = new HBox();
        for(Room room : db.getRooms()) {
            VBox vbox = new VBox();
            Label label = new Label(room.getTheaterRoom().toString());

            TableView<Showing> showingsTableView = new TableView<>();
            ObservableList<Showing> showingsForRoom = FXCollections.observableArrayList(showings.stream().filter(x -> x.getRoom().getTheaterRoom().equals(room.getTheaterRoom())).collect(Collectors.toList()));

            TableColumn startCol = new TableColumn("Start");
            startCol.setMinWidth(130);
            startCol.setCellValueFactory(new PropertyValueFactory<Showing, String>("startTime"));

            TableColumn endCol = new TableColumn("End");
            endCol.setMinWidth(130);
            endCol.setCellValueFactory(new PropertyValueFactory<Showing, String>("endTime"));

            TableColumn titleCol = new TableColumn("Title");
            titleCol.setMinWidth(270);
            titleCol.setCellValueFactory(new PropertyValueFactory<Showing, String>("movieTitle"));

            TableColumn seatsCol = new TableColumn("Seats");
            seatsCol.setMinWidth(40);
            seatsCol.setCellValueFactory(new PropertyValueFactory<Showing, String>("seatsLeft"));

            TableColumn priceCol = new TableColumn("Price");
            priceCol.setMinWidth(40);
            priceCol.setCellValueFactory(new PropertyValueFactory<Showing, String>("moviePrice"));

            showingsTableView.setPlaceholder(new Label("No movies to display"));

            showingsTableView.getColumns().addAll(startCol, endCol, titleCol, seatsCol, priceCol);
            showingsTableView.setItems(showingsForRoom);
            vbox.getChildren().addAll(label, showingsTableView);
            roomsBox.getChildren().add(vbox);
        }
        this.getChildren().add(roomsBox);

        ManageShowingsPartial partialView = new ManageShowingsPartial(db, user, stage);
        this.getChildren().add(partialView);
    }
}
