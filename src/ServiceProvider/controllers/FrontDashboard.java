package ServiceProvider.controllers;

import Entities.User;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FrontDashboard implements Initializable {

    public Button btnMyProfile;

    User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void profilesList(MouseEvent mouseEvent) throws IOException {
        System.out.println(user);
        Parent root = FXMLLoader.load(getClass().getResource("/ServiceProvider/view/dashboard.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void setUser(User userlogin) {
        this.user=userlogin;
    }

    public void showProfile(MouseEvent mouseEvent) throws IOException {
        Node node = (Node) mouseEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/ServiceProvider/view/userProfile.fxml"
                )
        );
        stage.setScene(
                new Scene(loader.load())
        );
        UserProfile myProfile = loader.getController();
        myProfile.setUser(this.user);

        stage.show();

    }

    public void logout(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ServiceProvider/view/login.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
