/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Entities.Service;
import Utils.dbConnection;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Nour
 */
public class ServiceUserShowController implements Initializable {

   @FXML
    private TableView<Service> tableau;
    @FXML
    private TableColumn<Service, Integer> idcol;
    @FXML
    private TableColumn<Service, String> titlecol;
    @FXML
    private TableColumn<Service, String> decrcol;
    @FXML
    private TableColumn<Service, Integer> pricecol;
    @FXML
    private TableColumn<Service, java.sql.Date> datecol;
    @FXML
    private TextField tfrecher;
    @FXML
    private Button brecherche;
    @FXML
    private Button btnback;
    @FXML
    private Button btnorder;
    private dbConnection dc;
    private ObservableList<Service>data;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        dc = new dbConnection();
        afficherServicetable();
    }    

    @FXML
    private void rechercheserviceruser(ActionEvent event) {
    }

    @FXML
    private void backuser(ActionEvent event) {
        try {
            FXMLLoader loader
                    = new FXMLLoader(getClass().getResource("/servicepovidermain/FXMLDocument.fxml"));
            Parent root = loader.load();
            //Controller irc = loader.getController();
            btnback.getScene().setRoot(root);
        } catch (IOException ex) {
            Logger.getLogger(ServicePostController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void oerderService(ActionEvent event) {
        try {
            FXMLLoader loader
                    = new FXMLLoader(getClass().getResource("/servicepovidermain/ServiceOrder.fxml"));
            Parent root = loader.load();
            ServiceOrderController irc = loader.getController();
            Service Selected= tableau.getSelectionModel().getSelectedItem();
           if (Selected == null) {
            JOptionPane.showMessageDialog(null, "There is nothing selected !");
        } else { 
            irc.setIdOrder(String.valueOf(Selected.getId()));
            irc.setTitre(Selected.getTitle());
            btnorder.getScene().setRoot(root);
            }
        } catch (IOException ex) {
            Logger.getLogger(ServiceShowController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    private void afficherServicetable(){
      try {
            Connection conn =dc.getConnection();
            data=FXCollections.observableArrayList();
            ResultSet rs=conn.createStatement().executeQuery("Select * From service");
            
            while (rs.next()){
            data.add(new Service (rs.getInt(1), rs.getString(3), rs.getString(4), rs.getInt(5),rs.getDate(6)));
            } 
            
        } catch (SQLException ex) {
          System.err.println("errwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwwweur"+ex);
        
           }
        
        idcol.setCellValueFactory(new PropertyValueFactory<>("id"));
        
        titlecol.setCellValueFactory(new PropertyValueFactory<>("title"));
        decrcol.setCellValueFactory(new PropertyValueFactory<>("description"));
        pricecol.setCellValueFactory(new PropertyValueFactory<>("price"));
        datecol.setCellValueFactory(new PropertyValueFactory<>("creat_at"));
        
        
        
       
        tableau.setItems(null);
         
        tableau.setItems(data);
        
        
        
        
        
        FilteredList<Service> filteredData = new FilteredList<>(data, b -> true);
		
		
		tfrecher.textProperty().addListener((observable, oldValue, newValue) -> {
			filteredData.setPredicate(Service -> {
				
								
				if (newValue == null || newValue.isEmpty()) {
					return true;
				}
				String lowerCaseFilter = newValue.toLowerCase();
				
				if (Service.getTitle().toLowerCase().indexOf(lowerCaseFilter) != -1 ) {
					return true; 
                                } 
                                else if (Service.getDescription().toLowerCase().indexOf(lowerCaseFilter) != -1) {
					return true; 
				}
                                else if (String.valueOf(Service.getCreat_at()).indexOf(lowerCaseFilter)!=-1)
				     return true;
				     else  
				    	 return false; 
			});
		});
		
		// 3. Wrap the FilteredList in a SortedList. 
		SortedList<Service> sortedData = new SortedList<>(filteredData);
		
		// 4. Bind the SortedList comparator to the TableView comparator.
		// 	  Otherwise, sorting the TableView would have no effect.
		sortedData.comparatorProperty().bind(tableau.comparatorProperty());
		
		// 5. Add sorted (and filtered) data to the table.
		tableau.setItems(sortedData);
    }
    
}