package com.robertovecchio.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

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

    //==================================================
    //               Variabili Statiche
    //==================================================

    private final static String loginController = "src/com/robertovecchio/view/fxml/customerLogin.fxml";

    //==================================================
    //                Metodi FXML
    //==================================================

    /**
     * Questo metodo gestisce il caso in cui si voglia tornare indietro, verso main view
     * */
    @FXML
    public void handleBackButton(){
        UtilityController.navigateTo(loginController, "Taxi Finder",
                "Errore di caricamento file interfaccia main", backButton);
    }
}
