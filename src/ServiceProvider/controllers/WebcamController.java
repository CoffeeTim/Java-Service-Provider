package ServiceProvider.controllers;

import com.github.sarxos.webcam.Webcam;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WebcamController implements Initializable {

    public ImageView image;
    public Button btnCapture;
    public Button btnSave;

    private BufferedImage img = null;
    private String fileName = "blabla";

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    public void setFileName(String file) {
        this.fileName = file;
    }

    public void btnCaptureClicked(MouseEvent mouseEvent) {
        Webcam webcam = Webcam.getDefault();
        if (webcam != null) {
            System.out.println("Webcam: " + webcam.getName()); webcam.open();

            img = webcam.getImage();

            Image image = SwingFXUtils.toFXImage(img, null);
            this.image.setImage(image);
        } else {
            System.out.println("No webcam detected");
        }


    }

    public void btnSaveClicked(MouseEvent mouseEvent) throws IOException {
        if(img != null) {
            ImageIO.write(img, "PNG", new File(fileName+".png"));
        }
        else {
            Notifications n = Notifications.create()
                    .title("Take a picture")
                    .text("  nothing to save ! ")
                    .position(Pos.TOP_CENTER)
                    .hideAfter(Duration.seconds(1));
            n.darkStyle();
            n.show();
        }
    }
}
