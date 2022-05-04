package ServiceProvider.controllers;

import Entities.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class ProfileCard {
    public ImageView profilePhoto;
    public Text username;
    public Text specialisation;
    public Button btnshow;
    private User user;
    public void showProfile(MouseEvent mouseEvent) throws IOException {
        Node node = (Node) mouseEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/ServiceProvider/view/myProfile.fxml"
                )
        );
        stage.setScene(
                new Scene(loader.load())
        );
        ProfileController myProfile = loader.getController();
        myProfile.setUser(this.user);

        stage.show();
    }

    public void setData(User user) {
        this.user = user;

        username.setText(user.getUsername());
        specialisation.setText(user.getSpecialisation());

    }
}
