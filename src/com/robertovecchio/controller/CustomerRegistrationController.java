package com.robertovecchio.controller;

import com.robertovecchio.model.user.GenderType;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

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
    ImageView userImageField;
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

    //==================================================
    //               Variabili Statiche
    //==================================================

    private final static String loginController = "src/com/robertovecchio/view/fxml/customerLogin.fxml";

    //==================================================
    //               Inizializzazione
    //==================================================

    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize(){

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