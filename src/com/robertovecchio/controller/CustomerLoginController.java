package com.robertovecchio.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

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
    @FXML
    ImageView userImage;
    @FXML
    VBox vboxUser;

    //==================================================
    //               Variabili Statiche
    //==================================================

    private final static String controllerFile = "src/com/robertovecchio/view/fxml/main.fxml";
    private final static String registrationFile = "src/com/robertovecchio/view/fxml/customerRegistration.fxml";
    private final static String userLogo = "Assets/user.png";
    private final static String fontFamily = "Helvetica";
    private final static double fontSize = 15D;

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
            Image logo = new Image(new FileInputStream(userLogo));

            //Impostiamo l'immagine
            userImage.setImage(logo);
            userImage.setFitHeight(80); // impostiamo altezza
            userImage.setPreserveRatio(true); // impostiamo la conservazione delle proporzioni originali
        }catch (FileNotFoundException e){
            System.out.println("Errore di caricamento Immagine");
        }

        // inizializziamo un textFlow
        TextFlow textFlow = new TextFlow();

        // Creiamo la prima parte della registrazione
        Text registrationText = new Text("Non sei ancora registrato? ");
        registrationText.setFont(Font.font(fontFamily, FontWeight.THIN, fontSize));

        // Creiamo la seconda parte della registrazione
        Text registrationTextSecondPart = new Text("Clicca qui!");
        registrationTextSecondPart.setFont(Font.font(fontFamily + " bold", FontWeight.BOLD, 17));
        registrationTextSecondPart.setFill(Color.web("#1134FD"));
        registrationTextSecondPart.setId("registration");

        // aggiungiamo varie parti del testo di registrazione al textFlow
        textFlow.getChildren().addAll(registrationText, registrationTextSecondPart);

        // allineiamo il texflow al centro
        textFlow.setTextAlignment(TextAlignment.CENTER);

        // impostiamo un piccolo distanziamento dai campi di login
        textFlow.setPadding(new Insets(20));

        // aggiungiamo al vbox il textflow
        vboxUser.getChildren().add(textFlow);

        registrationTextSecondPart.setOnMouseClicked(mouseEvent -> {
            handleRegistrationButton();
        });

        registrationTextSecondPart.setOnMouseEntered(mouseEvent -> {
            System.out.println("Entrato");
        });
    }

    //==================================================
    //                  Metodi FXML
    //==================================================

    @FXML
    private void handleBackButton(){
        UtilityController.navigateTo(controllerFile, "Taxi Finder",
                "Errore di caricamento file interfaccia main", backButton);
    }

    //==================================================
    //                     Metodi
    //==================================================
    private void handleRegistrationButton(){
        UtilityController.navigateTo(registrationFile,"Registra Cliente",
                               "Interfaccia di registrazione di un cliente non trovata", backButton);
    }
}
