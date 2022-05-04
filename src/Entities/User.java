package Entities;

import java.time.LocalDateTime;
import java.util.Date;

public class User {
    private int id;
    private String nom;
    private String prenom;
    private String username;
    private String email;
    private String password;
    private int phone;
    private String photo;
    private String bio;
    private String nomEntreprise,adresse,secteur,type,specialisation,siteWeb,presentation,taille;
    private int montantHoraire;
    private String role;
    private Date createdAt;
    private boolean enable;
    private String token;
    private boolean isVerified;

    public User() {
    }

    public User(String nom, String prenom, String username, String email, String password, int phone, String role) {
        this.nom = nom;
        this.prenom = prenom;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;

    }

    public String getNom() {
        return nom;
    }

    //getter
    public int getId() {
        return id;
    }

    public String getPrenom() {
        return prenom;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public int getPhone() {
        return phone;
    }

    public String getPhoto() {
        return photo;
    }

    public String getBio() {
        return bio;
    }

    public String getNomEntreprise() {
        return nomEntreprise;
    }

    public String getAdresse() {
        return adresse;
    }

    public String getSecteur() {
        return secteur;
    }

    public String getType() {
        return type;
    }

    public String getSpecialisation() {
        return specialisation;
    }

    public String getSiteWeb() {
        return siteWeb;
    }

    public String getPresentation() {
        return presentation;
    }

    public String getTaille() {
        return taille;
    }

    public int getMontantHoraire() {
        return montantHoraire;
    }

    public String getRole() {
        return role;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public boolean isEnable() {
        return enable;
    }

    public String getToken() {
        return token;
    }

    public boolean isVerified() {
        return isVerified;
    }

    //setter

    public void setId(int id) {
        this.id = id;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public void setNomEntreprise(String nomEntreprise) {
        this.nomEntreprise = nomEntreprise;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void setSecteur(String secteur) {
        this.secteur = secteur;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    public void setSiteWeb(String siteWeb) {
        this.siteWeb = siteWeb;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public void setTaille(String taille) {
        this.taille = taille;
    }

    public void setMontantHoraire(int montantHoraire) {
        this.montantHoraire = montantHoraire;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone=" + phone +
                ", photo='" + photo + '\'' +
                ", bio='" + bio + '\'' +
                ", nomEntreprise='" + nomEntreprise + '\'' +
                ", adresse='" + adresse + '\'' +
                ", secteur='" + secteur + '\'' +
                ", type='" + type + '\'' +
                ", specialisation='" + specialisation + '\'' +
                ", siteWeb='" + siteWeb + '\'' +
                ", presentation='" + presentation + '\'' +
                ", taille='" + taille + '\'' +
                ", montantHoraire=" + montantHoraire +
                ", role='" + role + '\'' +
                ", createdAt=" + createdAt +
                ", enable=" + enable +
                ", token='" + token + '\'' +
                ", isVerified=" + isVerified +
                '}';
    }
}
