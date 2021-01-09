package com.robertovecchio.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Classe che gestisce la view dell'area aziendale
 * @author robertovecchio
 * @version 1.0
 * @since 08/01/2021
 * */
public class CompanyAreaController {
    //==================================================
    //               Variabili FXML
    //==================================================

    @FXML
    Button backButton;
    @FXML
    ImageView adminImage;
    @FXML
    ImageView driverImage;
    @FXML
    Label header;
    @FXML
    Label advice;

    //==================================================
    //               Variabili Statiche
    //==================================================

    private final static String controllerFile = "src/com/robertovecchio/view/fxml/main.fxml";
    private final static String adminLogo = "Assets/admin.png";
    private final static String driverLogo = "Assets/driver.png";
    private final static String fontFamily = "Helvetica";
    private final static double fontSize = 35D;

    //==================================================
    //               Inizializzazione
    //==================================================

    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize(){
        try{
            // Creiamo una nuova immagine dal path userLogo
            Image logo = new Image(new FileInputStream(adminLogo));
            Image secondLogo = new Image(new FileInputStream(driverLogo));


            adminImage.setImage(logo); //Impostiamo l'immagine
            adminImage.setFitHeight(80); // impostiamo altezza
            adminImage.setPreserveRatio(true); // impostiamo la conservazione delle proporzioni originali

            driverImage.setImage(secondLogo); //Impostiamo l'immagine
            driverImage.setFitHeight(80); // impostiamo altezza
            driverImage.setPreserveRatio(true); // impostiamo la conservazione delle proporzioni originali

        }catch (FileNotFoundException e){
            System.out.println("Errore di caricamento Immagine");
        }

        // Impostiamo le modalità di rappresentazione del testo "header"
        header.setFont(Font.font(fontFamily, FontWeight.BOLD, 30D));
        header.setTextFill(Color.web("#494949"));

        advice.setFont(Font.font(fontFamily, FontWeight.BOLD, 30D));
        advice.setTextFill(Color.web("#F3D833"));
    }

    //==================================================
    //                Metodi FXML
    //==================================================

    /**
     * Questo metodo gestisce il caso in cui si voglia tornare indietro, verso main view
     * */
    @FXML
    public void handleBackButton(){
        UtilityController.navigateTo(controllerFile, "Taxi Finder",
                               "Errore di caricamento file interfaccia main", backButton);
    }
}
