package Services;

import Entities.User;
import Interfaces.InterfaceUser;
import Utils.dbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.springframework.security.crypto.bcrypt.BCrypt;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.security.SecureRandom;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Properties;


public class ServiceUser implements InterfaceUser {

    private static final SecureRandom secureRandom = new SecureRandom(); //threadsafe
    private static final Base64.Encoder base64Encoder = Base64.getUrlEncoder(); //threadsafe

    Connection cnx;

    public ServiceUser(){
        cnx = dbConnection.getInstance().getConnection();
    }

    @Override
    public void addFreelancer(User user) {
        try {

            Date date = new Date(System.currentTimeMillis());
            String requete = "INSERT INTO `user`"
                    + "(`username`, `nom`,"
                    + " `prenom`, `email`, `phone`,"
                    + " `password`,`bio`,`specialisation`,`montant_horaire`,`photo`, "
                    + " `role`,`created_at`, `enabled`, `token`,`is_verified`)"
                    + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getNom());
            pst.setString(3, user.getPrenom());
            pst.setString(4, user.getEmail());
            pst.setInt(5, user.getPhone());
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt(13));
            pst.setString(6, hashedPassword);
            pst.setString(7, user.getBio());
            pst.setString(8, user.getSpecialisation());
            pst.setInt(9,user.getMontantHoraire());
            pst.setString(10, user.getPhoto());
            pst.setString(11, user.getRole());
            pst.setDate(12, date);
            pst.setBoolean(13,false);
            pst.setString(14,generateNewToken());
            pst.setBoolean(15,false);

            pst.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public ObservableList<User> displayUsers() throws SQLException{
        ObservableList<User> users = FXCollections.observableArrayList();;

            Statement stm = cnx.createStatement();
            String query = "SELECT * FROM `user` ";
            ResultSet rst = stm.executeQuery(query);


            while (rst.next()){
                User user= new User();
                user.setId(rst.getInt("id"));
                user.setNom(rst.getString("nom"));
                user.setPrenom(rst.getString("prenom"));
                user.setEmail(rst.getString("email"));
                user.setRole(rst.getString("role"));
                user.setPhone(rst.getInt("phone"));
                user.setEnable(rst.getBoolean("enabled"));
                users.add(user);

            }


        return users;
    }

    @Override
    public void updateUser(User user,int id) {
        try {

            String requete = "UPDATE user SET "
                    + " nom = ?, prenom = ?, email = ?,"
                    + " phone = ?, bio = ?, specialisation = ?, montant_horaire = ?, photo = ?"
                    + " WHERE id = ?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, user.getNom());
            pst.setString(2, user.getPrenom());
            pst.setString(3, user.getEmail());
            pst.setInt(4, user.getPhone());
            pst.setString(5, user.getBio());
            pst.setString(6, user.getSpecialisation());
            pst.setInt(7, user.getMontantHoraire());
            pst.setInt(8, id);
            pst.setString(9, user.getPhoto());
            System.out.println(requete);
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public void deleteUser(User user) {
        try {
            String requete = "DELETE FROM user WHERE id=?";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setInt(1,user.getId());
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }

    @Override
    public ObservableList<User> displayUserById(int id) throws SQLException {
        ObservableList<User> users = FXCollections.observableArrayList();;
        String query = "SELECT * FROM `user` WHERE id=?";
        PreparedStatement pst = cnx.prepareStatement(query);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();


        while (rs.next()){
            User user= new User();
            user.setId(rs.getInt("id"));
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setEmail(rs.getString("email"));
            user.setRole(rs.getString("role"));
            user.setPhone(rs.getInt("phone"));
            user.setEnable(rs.getBoolean("enabled"));
            users.add(user);

        }
        return users;
    }

    @Override
    public User UserById(int id) throws SQLException {
        User user= new User();
        String query = "SELECT * FROM `user` WHERE id=?";
        PreparedStatement pst = cnx.prepareStatement(query);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();


        while (rs.next()){

            user.setId(rs.getInt("id"));
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setEmail(rs.getString("email"));
            user.setRole(rs.getString("role"));
            user.setPhone(rs.getInt("phone"));
            user.setMontantHoraire(rs.getInt("montant_horaire"));
            user.setBio(rs.getString("bio"));
            user.setSpecialisation(rs.getString("specialisation"));
            user.setUsername(rs.getString("username"));
            user.setEnable(rs.getBoolean("enabled"));

        }
        return user;
    }

    @Override
    public ObservableList<User> searchUser(String input) throws SQLException {
        ObservableList <User> users = FXCollections.observableArrayList();

        try {
            String requete = "SELECT id, nom,"
                    + " prenom, email"
                    + " , phone, username, role "
                    + "FROM user "
                    + "WHERE `nom` like ? or `prenom` like ? or "
                    + " `role` like ? or `email` like ? or `phone` like ? or "
                    + " `role` like ? ";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, "%"+input+"%");
            pst.setString(2, "%"+input+"%");
            pst.setString(3, "%"+input+"%");
            pst.setString(4, "%"+input+"%");
            pst.setString(5, "%"+input+"%");
            pst.setString(6, "%"+input+"%");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                User user= new User();
                user.setId(rs.getInt("id"));
                user.setNom(rs.getString("nom"));
                user.setPrenom(rs.getString("prenom"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                user.setPhone(rs.getInt("phone"));
                user.setEnable(rs.getBoolean("enabled"));
                users.add(user);
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
        return users;

    }

    @Override
    public Boolean checkEmail(String email) throws SQLException {
        String requete = "SELECT email "
                + "FROM user ";
        PreparedStatement pst = cnx.prepareStatement(requete);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            if(rs.getString("email").equals(email)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public Boolean checkUsername(String username) throws SQLException {
        String requete = "SELECT username "
                + "FROM user ";
        PreparedStatement pst = cnx.prepareStatement(requete);
        ResultSet rs = pst.executeQuery();
        while (rs.next()) {
            if(rs.getString("username").equals(username)) {
                return false;
            }
        }
        return true;
    }

    public String generateNewToken() {
        byte[] randomBytes = new byte[24];
        secureRandom.nextBytes(randomBytes);
        return base64Encoder.encodeToString(randomBytes);
    }

    @Override
    public void changePassword(String email, String password) {
        try {

            String requete = "UPDATE user SET password = ?"
                    + " WHERE email = ?";
            PreparedStatement pst = cnx.prepareStatement(requete);

            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(13));
            pst.setString(1, hashedPassword);
            pst.setString(2, email);
            pst.executeUpdate();

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }
    }


    @Override
    public User login(String inputUsername, String inputPassword) {
        User user = new User();
        user.setId(-1);

        String hashedPassword = "";
        boolean isVerified = false;

        try {
            String requete = "SELECT password,is_verified FROM user where email like ? OR username like ? ";
            PreparedStatement pst = cnx.prepareStatement(requete);
            pst.setString(1, inputUsername);
            pst.setString(2, inputUsername);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                hashedPassword = rs.getString("password");
                isVerified = rs.getBoolean("is_verified");
            }

            if(BCrypt.checkpw(inputPassword, hashedPassword)) {
                System.out.println("It matches");
                requete = "SELECT * FROM user where email like ? OR username like ?";
                pst = cnx.prepareStatement(requete);
                pst.setString(1, inputUsername);
                pst.setString(2, inputUsername);
                rs = pst.executeQuery();
                while (rs.next()) {
                    user.setId(rs.getInt("id"));
                    user.setNom(rs.getString("nom"));
                    user.setPrenom(rs.getString("prenom"));
                    user.setEmail(rs.getString("email"));
                    user.setRole(rs.getString("role"));
                    user.setPhone(rs.getInt("phone"));
                    user.setMontantHoraire(rs.getInt("montant_horaire"));
                    user.setBio(rs.getString("bio"));
                    user.setSpecialisation(rs.getString("specialisation"));
                    user.setEnable(rs.getBoolean("enabled"));
                    System.out.println("  user : "+user);
                }
            }
            else {
                System.out.println("user id :"+user.getId());
            }

        } catch (SQLException ex) {
            System.err.println(ex.getMessage());
        }


        return user;
    }

    @Override
    public List<User> displayFreelancer() throws SQLException {
        List<User> users = new ArrayList<>();
        String query = "SELECT * FROM `user` WHERE role=?";
        PreparedStatement pst = cnx.prepareStatement(query);
        pst.setString(1, "prestataire");
        ResultSet rs = pst.executeQuery();


        while (rs.next()){
            User user= new User();
            user.setId(rs.getInt("id"));
            user.setNom(rs.getString("nom"));
            user.setPrenom(rs.getString("prenom"));
            user.setEmail(rs.getString("email"));
            user.setRole(rs.getString("role"));
            user.setPhone(rs.getInt("phone"));
            user.setMontantHoraire(rs.getInt("montant_horaire"));
            user.setBio(rs.getString("bio"));
            user.setSpecialisation(rs.getString("specialisation"));
            user.setEnable(rs.getBoolean("enabled"));
            users.add(user);

        }
        return users;
    }
}
