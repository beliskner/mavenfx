package nl.inholland.javafx.ui.windows;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import nl.inholland.javafx.data.Database;
import nl.inholland.javafx.models.Movie;
import nl.inholland.javafx.models.User;
import nl.inholland.javafx.services.user.IUserService;
import nl.inholland.javafx.services.user.UserService;

import java.util.List;

public class LoginWindow {

    private Stage stage;

    public Stage getStage(){
        return stage;
    }

    public LoginWindow(Database db) {
        stage = new Stage();
        List<Movie> movies = db.getMovies();
        List<User> users = db.getUsers();
        IUserService userService = new UserService();

        GridPane gridPane = new GridPane();
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setVgap(10); // Vertical spacing between grid items
        gridPane.setHgap(8); // Horizontal spacing between grid items

        Label userLabel = new Label("Username:");
        GridPane.setConstraints(userLabel, 0, 0);
        TextField userInput = new TextField();
        userInput.setPromptText("Enter username");
        GridPane.setConstraints(userInput, 1, 0);


        Label passwordLabel = new Label("Password:");
        GridPane.setConstraints(passwordLabel, 0, 1);
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter password");
        GridPane.setConstraints(passwordField, 1, 1);

        Button loginButton = new Button("Log in");
        GridPane.setConstraints(loginButton, 1, 2);
        gridPane.getChildren().addAll(userLabel,
                                      userInput, passwordLabel, passwordField, loginButton);

        EventHandler<ActionEvent> event = e -> {
            User user = userService.GetUserByCredentials(users, userInput.getText(), passwordField.getText());
            if (user != null) {
                MainWindow mainWindow = new MainWindow(db, user);
                mainWindow.getStage().show();
                stage.close();
            } else {
                Alert a = new Alert(Alert.AlertType.ERROR);
                a.setContentText("Username or password incorrect");
                a.show();
            }
        };

        loginButton.setOnAction(event);

        Scene loginScene = new Scene(gridPane);

        stage.setTitle("Login");
        stage.setScene(loginScene);
    }
}
