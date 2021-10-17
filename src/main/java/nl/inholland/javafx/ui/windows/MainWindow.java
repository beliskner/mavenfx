package nl.inholland.javafx.ui.windows;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import nl.inholland.javafx.data.Database;
import nl.inholland.javafx.models.User;
import nl.inholland.javafx.models.enums.UserRole;
import nl.inholland.javafx.ui.views.movies.ManageMovies;
import nl.inholland.javafx.ui.views.purchase.PurchaseView;
import nl.inholland.javafx.ui.views.showings.ManageShowings;

import java.util.concurrent.atomic.AtomicReference;

public class MainWindow {
    private Stage stage;
    private Database db;

    public Stage getStage() {return stage; }

    public MainWindow(Database db, User user) {
        this.db = db;
        stage = new Stage();
        VBox layout = new VBox();
        ManageShowings manageShowingsView = new ManageShowings(db, user, stage);
        ManageMovies manageMoviesView = new ManageMovies(db, user, stage);

        MenuBar menuBar = new MenuBar();
        // Admin
        if (user.getRole() == UserRole.ADMIN) {
            Menu adminMenu = new Menu("Admin");
            MenuItem manageShowings = new MenuItem("Manage showings");
            MenuItem manageMovies = new MenuItem("Manage movies");
            adminMenu.getItems().add(manageShowings);
            adminMenu.getItems().add(manageMovies);
            menuBar.getMenus().add(adminMenu);

            EventHandler<ActionEvent> manageShowingsClick = e -> {
                layout.getChildren().set(1, manageShowingsView);
            };
            manageShowings.setOnAction(manageShowingsClick);

            EventHandler<ActionEvent> manageMoviesClick = e -> {
                layout.getChildren().set(1, manageMoviesView);
            };
            manageMovies.setOnAction(manageMoviesClick);
        }
        // About
        Menu helpMenu = new Menu("Help");
        MenuItem about = new MenuItem("About (not implemented)");
        helpMenu.getItems().add(about);
        menuBar.getMenus().add(helpMenu);

        // Logout
        Menu logoutMenu = new Menu("Logout");
        MenuItem logout = new MenuItem("Logout");
        logoutMenu.getItems().add(logout);
        menuBar.getMenus().add(logoutMenu);

        EventHandler<ActionEvent> logoutEvent = e -> {
            LoginWindow login = new LoginWindow(db);
            login.getStage().show();
            stage.close();
        };
        logout.setOnAction(logoutEvent);
        VBox menuBox = new VBox(menuBar);

        PurchaseView purchaseView  = new PurchaseView(db, user, stage);
        layout.getChildren().addAll(menuBox, purchaseView);

        Scene scene = new Scene(layout);

        stage.setTitle(String.format("Welcome to the fabulous cinema, %s.", user.getUserName()));
        stage.setScene(scene);
    }
}
