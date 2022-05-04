package ServiceProvider.controllers;

import Entities.User;
import Services.ServiceUser;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class UpdateFreelancer {
    public Button uploadImage;
    public TextField firstNameField;
    public TextField lastNameField;
    public TextArea bioField;
    public TextField phoneField;
    public TextField specialisationField;
    public TextField salaryField;

    public User user;
    public ImageView imageView;
    public String pathPhoto;



    void setTextField(User u) {

        this.user = u;

        firstNameField.setText(user.getPrenom());
        lastNameField.setText(user.getNom());
        bioField.setText(user.getBio());
        phoneField.setText(String.valueOf(user.getPhone()));
        specialisationField.setText(user.getSpecialisation());
        salaryField.setText(String.valueOf(user.getMontantHoraire()));


    }

    public void uploadImage(MouseEvent mouseEvent) throws IOException {
        FileChooser fc = new FileChooser();
        FileChooser.ExtensionFilter ext1 = new FileChooser.ExtensionFilter("JPG files(*.jpg)","*.JPG");
        FileChooser.ExtensionFilter ext2 = new FileChooser.ExtensionFilter("PNG files(*.png)","*.PNG");
        fc.getExtensionFilters().addAll(ext1,ext2);
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        File file = fc.showOpenDialog(stage);
        this.pathPhoto = file.getPath();
        BufferedImage bf;
        bf = ImageIO.read(file);
        Image image = SwingFXUtils.toFXImage(bf, null);
        imageView.setImage(image);
        System.out.println(file);
    }

    public void save(MouseEvent mouseEvent) {
        ServiceUser serviceUser = new ServiceUser();

        String nom = lastNameField.getText();
        String prenom = firstNameField.getText();
        int phone = 55648235;
        String bio = bioField.getText();
        String specialisation = specialisationField.getText();
        int salary = 25;

        user.setNom(nom);
        user.setPrenom(prenom);
        user.setSpecialisation(specialisation);
        user.setPhone(phone);
        user.setBio(bio);
        user.setMontantHoraire(salary);
        user.setPhoto(pathPhoto);
        System.out.println(user);
        serviceUser.updateUser(user,user.getId());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText("User"+user.getUsername()+" update successful");
        alert.showAndWait();
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.close();
    }
}
