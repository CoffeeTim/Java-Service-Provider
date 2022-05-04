package ServiceProvider.controllers;

import Entities.User;
import Services.ServiceUser;
import Utils.dbConnection;

import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    public TextField txtUsername;
    public PasswordField txtPassword;
    public Label lblErrors;
    public Pane Proot;

    Connection cnx = null;
    User user = null ;

    public LoginController() {
        cnx = dbConnection.getInstance().getConnection();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (cnx == null) {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Server Error : Check");
        } else {
            lblErrors.setTextFill(Color.GREEN);
           // showSnackBar("Server is up : Good to go");
            lblErrors.setText("Server is up : Good to go");
        }
    }

    public void signInClicked(MouseEvent mouseEvent) throws IOException {
        if (logIn().equals("Success")) {
            Node node = (Node) mouseEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            //stage.setMaximized(true);
            stage.close();
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(
                            "/ServiceProvider/view/frontDashboard.fxml"
                    )
            );


            stage.setScene(
                    new Scene(loader.load())
            );
            System.out.println(user);
            FrontDashboard controller = loader.getController();
            controller.setUser(user);

            stage.show();

        }
    }

    public void signUpClicked(MouseEvent mouseEvent) {
        try {
            //add you loading or delays - ;-)
            Node node = (Node) mouseEvent.getSource();
            Stage stage = (Stage) node.getScene().getWindow();
            //stage.setMaximized(true);
            stage.close();
            Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/ServiceProvider/view/addFreelancer.fxml")));
            stage.setScene(scene);
            stage.show();

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    private String logIn() {
        ServiceUser serviceUser = new ServiceUser();
        String status = "Success";
        String email = txtUsername.getText();
        String password = txtPassword.getText();
        if(email.isEmpty() || password.isEmpty()) {
            setLblError(Color.TOMATO, "Empty credentials");
            status = "Error";
        } else {
            user = serviceUser.login(email,password);

                if (user.getId() == -1) {
                    setLblError(Color.TOMATO, "Enter Correct Email/Password");
                    status = "Error";
                } else {
                    setLblError(Color.GREEN, "Login Successful..Redirecting..");

                }
            }

        return status;
    }

    private void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
        System.out.println(text);
    }


}
