package ServiceProvider.controllers;

import Entities.User;
import Services.ServiceUser;
import Utils.SendMail;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.commons.validator.routines.EmailValidator;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;

public class AddFreelancer implements Initializable {
    public TextField firstNameField;
    public TextField lastNameField;
    public TextArea bioField;
    public TextField phoneField;
    public TextField specialisationField;
    public TextField salaryField;
    public TextField usernameField;
    public TextField emailField;
    public PasswordField passwordField;
    public PasswordField confirmPasswordField;
    public Button registration;
    public Button clear;
    public ImageView imageView;
    private User user;
    private boolean update;
    public String pathPhoto;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void registrationFreelancer(MouseEvent mouseEvent) throws SQLException {
        ServiceUser serviceUser = new ServiceUser();

        String username = usernameField.getText();
        String nom = lastNameField.getText();
        String prenom = firstNameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        int phone = Integer.parseInt(phoneField.getText());
        String bio = bioField.getText();
        String specialisation = specialisationField.getText();
        int salary = Integer.parseInt(salaryField.getText());
        String role = "prestataire";

        if (nom.isEmpty() || prenom.isEmpty() || email.isEmpty() || bio.isEmpty() || specialisation.isEmpty())
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Please Fill All DATA");
            alert.showAndWait();
        }else if (!isValidEmail(email)) {

        }else if (!password.equals(confirmPassword)){

        }else if (!serviceUser.checkEmail(email)){

        }else if (!serviceUser.checkUsername(username)){

        }else if (update){


        }else {

            user = new User(nom,prenom,username,email,password,phone,role);
            user.setBio(bio);
            user.setSpecialisation(specialisation);
            user.setMontantHoraire(salary);
            user.setPhoto(pathPhoto);
            System.out.println(user);
            serviceUser.addFreelancer(user);

            String token = serviceUser.generateNewToken();
            String msg= "div class=\"app font-sans min-w-screen min-h-screen bg-grey-lighter py-8 px-4\">\n" +
                    "\n" +
                    "    <div class=\"mail__wrapper max-w-md mx-auto\">\n" +
                    "\n" +
                    "        <div class=\"mail__content bg-white p-8 shadow-md\">\n" +
                    "\n" +
                    "            <div class=\"content__header text-center tracking-wide border-b\">\n" +
                    "                <div class=\"text-green text-sm font-bold\">Service provider</div>\n" +
                    "                <h1 class=\"text-3xl h-48 flex items-center justify-center\">E-mail Confirmation</h1>\n" +
                    "            </div>\n" +
                    "\n" +
                    "            <div class=\"content__body py-8 border-b\">\n" +
                    "                <p>\n" +
                    "                    Hey, <br><br>It looks like you just signed up for The App, that’s awesome! Can we ask you for email confirmation? Just click the button bellow.\n" +
                    "                </p>\n" +
                    "                <a class=\"text-white text-sm tracking-wide bg-green rounded w-full my-8 p-4 \" href=\"http://127.0.0.1:8000/confirmer-mon-compte/"+token+"\">CONFIRM EMAIL ADRESS</a>\n" +
                    "\n" +
                    "                <p class=\"text-sm\">\n" +
                    "                    Keep Rockin'!<br> Your The App team\n" +
                    "                </p>\n" +
                    "            </div>\n" +
                    "\n" +
                    "            <div class=\"content__footer mt-8 text-center text-grey-darker\">\n" +
                    "                <h3 class=\"text-base sm:text-lg mb-4\">Thanks for using The App!</h3>\n" +
                    "                <p>www.ServiceProvider.io</p>\n" +
                    "            </div>\n" +
                    "\n" +
                    "        </div>\n" +
                    "\n" +
                    "        <div class=\"mail__meta text-center text-sm text-grey-darker mt-8\">\n" +
                    "\n" +
                    "            <div class=\"meta__social flex justify-center my-4\">\n" +
                    "                <a href=\"#\" class=\"flex items-center justify-center mr-4 bg-black text-white rounded-full w-8 h-8 no-underline\"><i class=\"fab fa-facebook-f\"></i></a>\n" +
                    "                <a href=\"#\" class=\"flex items-center justify-center mr-4 bg-black text-white rounded-full w-8 h-8 no-underline\"><i class=\"fab fa-instagram\"></i></a>\n" +
                    "                <a href=\"#\" class=\"flex items-center justify-center bg-black text-white rounded-full w-8 h-8 no-underline\"><i class=\"fab fa-twitter\"></i></a>\n" +
                    "            </div>\n" +
                    "\n" +
                    "            <div class=\"meta__help\">\n" +
                    "                <p class=\"leading-loose\">\n" +
                    "                    Questions or concerns? <a href=\"#\" class=\"text-grey-darkest\">help@theapp.io</a>\n" +
                    "\n" +
                    "                    <br> Want to quit getting updates? <a href=\"#\" class=\"text-grey-darkest\">Unsubscribe</a>\n" +
                    "                </p>\n" +
                    "            </div>\n" +
                    "\n" +
                    "        </div>\n" +
                    "\n" +
                    "    </div>\n" +
                    "\n" +
                    "</div>\n" +
                    "\n" +
                    "<a href=\"http://127.0.0.1:8000/confirmer-mon-compte/"+token+"\">Cliquer ici pour confirmer votre compte</a>"
                    ;

            SendMail sm= new SendMail();
            try {
                sm.sendMail(user.getEmail(), "Thanks for signing up!", msg);
            } catch (MessagingException ex) {
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText("you have been added successfully please check your email for confirmation ");
            alert.showAndWait();
            Parent root = null;
            try {
                root = FXMLLoader.load(getClass().getResource("/ServiceProvider/view/login.fxml"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();

        }


    }

    public void clear(MouseEvent mouseEvent) {

    }

    public boolean isValidEmail(String email){
        EmailValidator validator = EmailValidator.getInstance();

        return validator.isValid(email);
    }

    public boolean isValidPhone(String phone){
        return phone.matches("\\d{10}") && phone.length() == 8;
    }

    void setUpdate(boolean b) {
        this.update = b;
        usernameField.setVisible(false);
        passwordField.setVisible(false);
        confirmPasswordField.setVisible(false);
    }


    public void openCam(MouseEvent mouseEvent) throws IOException {
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
}
