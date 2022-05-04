package ServiceProvider.controllers;

import Entities.User;
import Services.ServiceUser;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DashboardController implements Initializable {

    public Button btnReturn;
    public ScrollPane scroll;
    public GridPane grid;

    private List<User> users = new ArrayList<>();


    private List<User> getData() {
        List<User> users = new ArrayList<>();
        ServiceUser se = new ServiceUser();
        try {
            users.addAll(se.displayFreelancer());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return users;
    }
    public void goBack(MouseEvent mouseEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ServiceProvider/view/frontDashboard.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        affichage();

    }

    private void affichage()
    {
        int ligne =0;
        int colone = 0;
        users.addAll(getData());
        for(int i=0;i<users.size();i++){
            try {


                FXMLLoader fxmlLoader= new FXMLLoader();

                fxmlLoader.setLocation(getClass().getResource("/ServiceProvider/view/profileCard.fxml"));//recuperer le fichier fxml
                AnchorPane    col;

                col = fxmlLoader.load(); //recuperer le block du produit


                ProfileCard CardController = fxmlLoader.getController();//recuperer le controller du ficher fxml
                CardController.setData(users.get(i));//faire le block pour chaque produit de la liste
                
                grid.add(col, 0, ligne++);//ajouter le block dans le grid
                GridPane.setMargin(col, new Insets(100));
            } catch (IOException ex) {
                Logger.getLogger(DashboardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    }
