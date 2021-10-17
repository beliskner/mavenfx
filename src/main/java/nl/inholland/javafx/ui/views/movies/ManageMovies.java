package nl.inholland.javafx.ui.views.movies;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nl.inholland.javafx.data.Database;
import nl.inholland.javafx.models.Movie;
import nl.inholland.javafx.models.Room;
import nl.inholland.javafx.models.Showing;
import nl.inholland.javafx.models.User;
import nl.inholland.javafx.ui.views.showings.ManageShowingsPartial;

import java.util.List;
import java.util.stream.Collectors;

public class ManageMovies extends VBox{
    private Database db;
    private User user;
    private Stage stage;

    public ManageMovies(Database db, User user, Stage stage) {
        this.db = db;
        this.user = user;
        this.stage = stage;

        List<Movie> moviesInDb = db.getMovies();

        this.setPadding(new Insets(20));
        this.setSpacing(20);
        VBox vbox = new VBox();
        Label label = new Label("Movies");

        TableView<Movie> moviesTableView = new TableView<>();
        ObservableList<Movie> movies = FXCollections.observableArrayList(moviesInDb);

        TableColumn titleCol = new TableColumn("Title");
        titleCol.setMinWidth(270);
        titleCol.setCellValueFactory(new PropertyValueFactory<Movie, String>("title"));

        TableColumn runTimeCol = new TableColumn("Runtime");
        runTimeCol.setMinWidth(40);
        runTimeCol.setCellValueFactory(new PropertyValueFactory<Movie, Integer>("runTime"));

        TableColumn priceCol = new TableColumn("Price");
        priceCol.setMinWidth(40);
        priceCol.setCellValueFactory(new PropertyValueFactory<Movie, String>("price"));

        moviesTableView.setPlaceholder(new Label("No movies to display"));

        moviesTableView.getColumns().addAll(titleCol, runTimeCol, priceCol);
        moviesTableView.setItems(movies);
        vbox.getChildren().addAll(label, moviesTableView);

        this.getChildren().add(vbox);

        ManageMoviesPartial partialView = new ManageMoviesPartial(db, user, stage);
        this.getChildren().add(partialView);
    }
}
