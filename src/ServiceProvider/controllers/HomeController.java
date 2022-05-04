package ServiceProvider.controllers;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class HomeController implements Initializable {

    public BorderPane borderPane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void showProfiles(MouseEvent mouseEvent) throws IOException {
        Parent profiles = FXMLLoader.load(getClass().getResource("/ServiceProvider/view/userManager.fxml"));

        borderPane.setCenter(profiles);
    }
}
