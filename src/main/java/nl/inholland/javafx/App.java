package nl.inholland.javafx;

import javafx.application.Application;
import javafx.stage.Stage;
import nl.inholland.javafx.data.Database;
import nl.inholland.javafx.ui.windows.LoginWindow;

public class App extends Application {
    @Override
    public void start(Stage window) throws Exception {
        window.setTitle("Login");
        Database db = new Database();

        LoginWindow login = new LoginWindow(db);
        login.getStage().show();
    }
}
