package com.robertovecchio.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;

/**
 * Classe che gestisce la view del login cliente
 * @author robertovecchio
 * @version 1.0
 * @since 08/01/2021
 * */
public class CustomerLoginController {
    //==================================================
    //               Variabili FXML
    //==================================================

    @FXML
    Button backButton;

    //==================================================
    //               Variabili Statiche
    //==================================================

    private final static String controllerFile = "src/com/robertovecchio/view/fxml/main.fxml";

    //==================================================
    //                Metodi FXML
    //==================================================

    @FXML
    public void handleBackButton(){
        UtilityController.navigateTo(controllerFile, "Taxi Finder",
                "Errore di caricamento file interfaccia main", backButton);
    }
}
