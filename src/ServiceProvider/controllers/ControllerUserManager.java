package ServiceProvider.controllers;

import Entities.User;
import Services.ServiceUser;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ControllerUserManager implements Initializable {


    public TextField search;
    @FXML
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User, String> idCol;
    @FXML
    private TableColumn<User, String> usernameCol;
    @FXML
    private TableColumn<User, String> nomCol;
    @FXML
    private TableColumn<User, String> prenomCol;
    @FXML
    private TableColumn<User, String> emailCol;
    @FXML
    private TableColumn<User, String> roleCol;
    @FXML
    private TableColumn<User, String> phoneCol;
    @FXML
    private TableColumn<User, String> enabledCol;
    @FXML
    private TableColumn<User, User> editCol;

    User user =null;

    ObservableList<User> userList = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            loadData();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void getAddView(MouseEvent mouseEvent) {
        try {
            URL url = new File("src/ServiceProvider/view/addUser.fxml").toURI().toURL();
            Parent parent = FXMLLoader.load(url);
            Scene scene = new Scene(parent);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("User Details");
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        }catch (IOException ex) {
            Logger.getLogger(ControllerUserManager.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void refreshTable() throws SQLException {
        ServiceUser serviceUser = new ServiceUser();
        userList.clear();
        userList= serviceUser.displayUsers();
        usersTable.setItems(userList);
    }

    public void print(MouseEvent mouseEvent) {
    }

    private void loadData() throws SQLException {
        ServiceUser serviceUser = new ServiceUser();
        userList.clear();
        userList= serviceUser.displayUsers();


        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nomCol.setCellValueFactory(new PropertyValueFactory<>("nom"));
        prenomCol.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        usernameCol.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        roleCol.setCellValueFactory(new PropertyValueFactory<>("role"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));
        enabledCol.setCellValueFactory(new PropertyValueFactory<>("enabled"));
        editCol.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        editCol.setCellFactory(param -> new TableCell<User, User>(){


        /*
        //add cell of button edit
        Callback<TableColumn<User, String>, TableCell<User, String>> cellFactory = (TableColumn<User, String> param) -> {
            // make cell containing buttons
            final TableCell<User, String> cell = new TableCell<User, String>(){*/


                public void updateItem(User item, boolean empty) {
                    super.updateItem(item, empty);
                    //that cell created only on non-empty rows
                    if (empty) {
                        setGraphic(null);

                    } else {

                        Button deleteIcon = new Button();
                        Button editIcon = new Button();
                        deleteIcon.setText("Delete");
                        editIcon.setText("Edit");

                        deleteIcon.setOnMouseClicked((MouseEvent event) -> {
                            Alert alert= new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setHeaderText(null);
                            alert.setTitle("Confirmation");
                            alert.setContentText("Are you sure you wanna delete ?");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() ==ButtonType.OK){
                                user = usersTable.getSelectionModel().getSelectedItem();
                                serviceUser.deleteUser(user);
                                try {
                                    refreshTable();
                                } catch (SQLException throwables) {
                                    throwables.printStackTrace();
                                }
                            }else {
                                System.out.println("Erreur delete");
                            }

                        });
                        editIcon.setOnMouseClicked((MouseEvent event) -> {

                            user = usersTable.getSelectionModel().getSelectedItem();
                            System.out.println(user);
                            FXMLLoader loader = new FXMLLoader ();

                            loader.setLocation(getClass().getResource("/ServiceProvider/view/addUser.fxml"));
                            try {

                                loader.load();
                            } catch (IOException ex) {
                                Logger.getLogger(ControllerUserManager.class.getName()).log(Level.SEVERE, null, ex);
                            }

                            ControllerAddUser addUserController = loader.getController();
                            addUserController.setUpdate(true);
                            addUserController.setTextField(user.getId(),user.getNom(),
                                    user.getPrenom(),user.getEmail(),user.getPhone(),user.getRole());
                            Parent parent = loader.getRoot();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(parent));
                            stage.initStyle(StageStyle.UTILITY);
                            stage.show();
                            try {
                                refreshTable();
                            } catch (SQLException throwables) {
                                throwables.printStackTrace();
                            }
                        });

                        HBox managebtn = new HBox(editIcon, deleteIcon);
                        managebtn.setStyle("-fx-alignment:center");
                        HBox.setMargin(deleteIcon, new Insets(2, 2, 0, 3));
                        HBox.setMargin(editIcon, new Insets(2, 3, 0, 2));

                        setGraphic(managebtn);
                        setText(null);
                    }


                }

            });

           /* return cell;
        };
        editCol.setCellFactory(cellFactory);*/
        usersTable.setItems(userList);
        FilteredList<User> filteredData = new FilteredList<>(userList, b-> true);

        search.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(User -> {
                // If filter text is empty, display all persons.

                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                if (user.getNom().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
                    return true; // Filter matches first name.
                } else if (user.getRole().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                }else if (user.getPrenom().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                }
                else if (user.getEmail().toLowerCase().indexOf(lowerCaseFilter) != -1) {
                    return true; // Filter matches last name.
                }
                //tf_datedeb.setValue(o.getDatedeb().toLocalDate());

                else if (String.valueOf(user.getCreatedAt()).indexOf(lowerCaseFilter)!=-1) {
                    return true; // Filter matches last name.
                }
                else if (String.valueOf(user.getId()).indexOf(lowerCaseFilter)!=-1)
                    return true;
                else
                    return false; // Does not match.
            });
        });
        // 3. Wrap the FilteredList in a SortedList.
        SortedList<User> sortedData = new SortedList<>(filteredData);

        // 4. Bind the SortedList comparator to the TableView comparator.
        // 	  Otherwise, sorting the TableView would have no effect.
        sortedData.comparatorProperty().bind(usersTable.comparatorProperty());

        // 5. Add sorted (and filtered) data to the table.

        usersTable.setItems(sortedData);
    }

    public void goBack(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/ServiceProvider/view/main.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
