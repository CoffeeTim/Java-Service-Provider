package ServiceProvider.controllers;

import Entities.User;
import Services.ServiceUser;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;

import java.util.ResourceBundle;

public class ControllerAddUser implements Initializable {
    public TextField usernameField;
    public TextField nomField;
    public TextField prenomField;
    public TextField emailField;
    public TextField passwordField;
    public TextField phoneField;
    public ComboBox roleComboBox;
    private User user;
    private boolean update;
    int userId;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> options = FXCollections.observableArrayList(
                "client",
                "prestataire",
                "entreprise"
        );
        roleComboBox.setItems(options);

    }

    public void saveUser(MouseEvent mouseEvent) {
        String username = usernameField.getText();
        String nom = nomField.getText();
        String prenom = prenomField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        int phone = Integer.parseInt(phoneField.getText());
        String role = (String) roleComboBox.getValue();

        if (username.isEmpty() || nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || password.isEmpty() || role.isEmpty() ){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();

        }else if (update){

            ServiceUser serviceUser = new ServiceUser();
            user = new User(nom,prenom,username,email,password,phone,role);
            serviceUser.updateUser(user,userId);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("User"+username+" update successful");
            alert.showAndWait();
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.close();

        } else {
            ServiceUser serviceUser = new ServiceUser();
            user = new User(nom,prenom,username,email,password,phone,role);
            serviceUser.addFreelancer(user);
            usernameField.setText(null);
            nomField.setText(null);
            prenomField.setText(null);
            emailField.setText(null);
            passwordField.setText(null);
            phoneField.setText(null);
            roleComboBox.setValue(null);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("User"+username+" added successful");
            alert.showAndWait();
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.close();
        }
    }

    public void cleanUser(MouseEvent mouseEvent) {
        usernameField.setText(null);
        nomField.setText(null);
        prenomField.setText(null);
        emailField.setText(null);
        passwordField.setText(null);
        phoneField.setText(null);
        roleComboBox.setValue(null);
    }

    void setTextField(int id, String nom, String prenom, String email, int phone, String role) {

        userId = id;

        nomField.setText(nom);
        prenomField.setText(prenom);
        emailField.setText(email);

        phoneField.setText(String.valueOf(phone));
        roleComboBox.setValue(role);

    }

    void setUpdate(boolean b) {
        this.update = b;

    }

}
