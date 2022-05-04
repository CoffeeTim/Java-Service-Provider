package ServiceProvider.controllers;

import Entities.User;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProfileController implements Initializable {
    public Button btnReturn;
    public ImageView profilePhoto;
    public Text username;
    public Text email;
    public Text name;
    public Text aboutMe;
    public Text specialisation;
    public Text salary;
    public Text phone;
    User user;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void goBack(MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(
                getClass().getResource(
                        "/ServiceProvider/view/frontDashboard.fxml"
                )
        );

        Scene scene = new Scene(loader.load());
        FrontDashboard controller = loader.getController();
        controller.setUser(user);

        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }


    public void setUser(User u) throws IOException {
        this.user = u;
        username.setText(user.getUsername());
        email.setText(user.getEmail());
        String flname= user.getPrenom()+" "+user.getNom();
        name.setText(flname);
        aboutMe.setText(user.getBio());
        specialisation.setText(user.getSpecialisation());
        if(!user.getPhoto().isEmpty()){
            String path=user.getPhoto();
            File file= new File(path);
            BufferedImage bf;
            bf = ImageIO.read(file);
            Image image = SwingFXUtils.toFXImage(bf, null);
            profilePhoto.setImage(image);
        }
    }

}
