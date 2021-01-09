package com.robertovecchio.controller;

import com.robertovecchio.model.user.GenderType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Classe che gestisce la view della registrazione del cliente
 * @author robertovecchio
 * @version 1.0
 * @since 08/01/2021
 * */
public class CustomerRegistrationController {
    //==================================================
    //               Variabili FXML
    //==================================================

    @FXML
    Button backButton;
    @FXML
    ImageView userImage;
    @FXML
    TextField fiscalCodeField;
    @FXML
    TextField surnameField;
    @FXML
    ComboBox<GenderType> genreField;
    @FXML
    TextField usernameField;
    @FXML
    TextField nameField;
    @FXML
    DatePicker dateOfBirthField;
    @FXML
    TextField emailField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button registerButton;
    @FXML
    TextField phoneField;
    @FXML
    CheckBox informationField;

    //==================================================
    //               Variabili Statiche
    //==================================================

    private final static String loginController = "src/com/robertovecchio/view/fxml/customerLogin.fxml";
    private final static String userLogo = "Assets/user.png";

    //==================================================
    //               Inizializzazione
    //==================================================

    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize(){
        try {
            // Inizializziamo il logo utente
            Image logo = new Image(new FileInputStream(userLogo));
            userImage.setImage(logo); // impostiamo l'immagine all'imageview
            userImage.setFitHeight(70); // impostiamo altezza
            userImage.setPreserveRatio(true); // impostiamo la conservazione delle proporzioni originali
        }catch (FileNotFoundException e){
            // catchiamo l'errore
            System.out.println("File Non trovato");
        }
    }

    //==================================================
    //                Metodi FXML
    //==================================================

    /**
     * Questo metodo gestisce il caso in cui si voglia tornare indietro, verso main view
     * */
    @FXML
    private void handleBackButton(){
        UtilityController.navigateTo(loginController, "Taxi Finder",
                "Errore di caricamento file interfaccia main", backButton);
    }

    /**
     * Metodo atto alla registrazione di un utente di tipo cliente
     */
    @FXML
    private void handleRegistration(){

    }

}