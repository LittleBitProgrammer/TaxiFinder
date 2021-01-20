package com.robertovecchio.controller;

import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.db.error.HandlerNotFoundException;
import com.robertovecchio.model.db.error.TaxiDriverNotFoundException;
import com.robertovecchio.model.user.Handler;
import com.robertovecchio.model.user.TaxiDriver;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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

    /**
     * Bottone utile a tornare indietro
     * @see Button
     */
    @FXML
    Button backButton;
    /**
     * Immagine che rappresenta il logo degli admin
     * @see ImageView
     */
    @FXML
    ImageView adminImage;
    /**
     * Immagine che rappresenta il logo dei tassisti
     * @see ImageView
     */
    @FXML
    ImageView driverImage;
    /**
     * Label che fa da intestazione
     * @see Label
     */
    @FXML
    Label header;
    /**
     * Label di sotto-intestazione
     * @see Label
     */
    @FXML
    Label advice;
    /**
     * Textfield utile a comporre l'username dell'handler
     * @see TextField
     */
    @FXML
    TextField handlerUsernameField;
    /**
     * TextField utile a comporre l'username del tasssita
     * @see TextField
     */
    @FXML
    TextField taxiDriverUsernameField;
    /**
     * PasswordField utile a comporre la password dell'handler
     * @see PasswordField
     */
    @FXML
    PasswordField handlerPasswordField;
    /**
     * PasswordField utile a comporre la password del tassista
     * @see PasswordField
     */
    @FXML
    PasswordField taxiDriverPasswordField;
    /**
     * Bottone che gestisce il login dell'handler
     * @see Button
     */
    @FXML
    Button handlerLoginButton;
    /**
     * Bottone che gestisce il login del tassista
     * @see Button
     */
    @FXML
    Button taxiDriverLoginButton;
    /**
     * Label che mostra l'errore di login per credenziali sbagliate all'handler
     * Questa label di default è impostata ad invisibile
     * @see Label
     */
    @FXML
    Label errorHandler;
    /**
     * Label che mostra l'errore di login per credenziali sbagliate al Tassista
     * Questa label di default è impostata ad invisibile
     * @see Label
     */
    @FXML
    Label errorTaxiDriver;

    //==================================================
    //               Variabili Statiche
    //==================================================

    /**
     * Variabile statica che rappresenta il percorso alla view principale
     * */
    private final static String controllerFile = "src/com/robertovecchio/view/fxml/main.fxml";
    /**
     * Variabile statica che rappresenta il percorso alla view dell'handler
     * */
    private final static String handlerControllerFile = "src/com/robertovecchio/view/fxml/handler.fxml";
    /**
     * Variabile statica che rappresenta il percorso alla view del tassista
     * */
    private final static String taxiDriverController = "src/com/robertovecchio/view/fxml/taxiDriver.fxml";
    /**
     * Variabile statica che rappresenta il percorso al logo dell'admin
     * */
    private final static String adminLogo = "Assets/admin.png";
    /**
     * Variabile statica che rappresenta il percorso al logo del tassista
     * */
    private final static String driverLogo = "Assets/driver.png";
    /**
     * Variabile statica che rappresenta il font principale
     * */
    private final static String fontFamily = "Helvetica";

    //==================================================
    //               Variabili d'istanza
    //==================================================

    // DB
    /**
     * Istanza del database
     * @see TaxiFinderData
     * */
    private final TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    //==================================================
    //               Inizializzazione
    //==================================================

    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize(){
        try{
            /* Creiamo una nuova immagine dal path userLogo */
            Image logo = new Image(new FileInputStream(adminLogo));
            Image secondLogo = new Image(new FileInputStream(driverLogo));

            adminImage.setImage(logo); //Impostiamo l'immagine
            adminImage.setFitHeight(80); // impostiamo altezza
            adminImage.setPreserveRatio(true); // impostiamo la conservazione delle proporzioni originali

            driverImage.setImage(secondLogo); //Impostiamo l'immagine
            driverImage.setFitHeight(80); // impostiamo altezza
            driverImage.setPreserveRatio(true); // impostiamo la conservazione delle proporzioni originali

        }catch (FileNotFoundException e){
            /* Mostriamo l'errore a terminale nel caso fosse presente */
            System.out.println("Errore di caricamento Immagine");
        }

        /* Impostiamo le modalità di rappresentazione del testo "header" */
        header.setFont(Font.font(fontFamily, FontWeight.BOLD, 30D));
        header.setTextFill(Color.web("#494949"));

        /* Impostiamo le modalità di rappresentazione del test "advice" */
        advice.setFont(Font.font(fontFamily, FontWeight.BOLD, 30D));
        advice.setTextFill(Color.web("#F3D833"));

        /* impostiamo il bottone login a disabilitato quando l'input utente non corrisponde ai criteri richiesti */
        handlerLoginButton.disableProperty().bind(invalidHandlerLogin());
        taxiDriverLoginButton.disableProperty().bind(invalidTaxiDriverLogin());

        /* Impostiamo un'azione da effettuare quando il bottone di login dell'handler viene premuto */
        handlerLoginButton.setOnAction(actionEvent -> {

            /* Inizializziamo le credenziali con quanto è stato inserito dall'utente */
            String username = this.handlerUsernameField.getText().trim();
            String password = this.handlerPasswordField.getText().trim();

            try {
                /* Inizializziamoo L'handler con i dati di login */
                Handler handler = taxiFinderData.loginHandler(new Handler(username,password));
                /* Carichiamo le liste inerenti all'admin */
                taxiFinderData.setCurrentUser(handler);

                /* Cambiamo Scene */
                UtilityController.navigateTo(handlerControllerFile,
                                                String.format("%s %s - %s", handler.getFirstName(),
                                                                            handler.getLastName(),
                                                                            handler.getUsername()),
                                          "Errore di navigazione, alcuni file non sono stati trovati",
                                                handlerLoginButton);
            }catch (HandlerNotFoundException e){
                /* Stampiamo l'errore */
                System.out.println(e.getMessage());

                /* Mostriamo l'errore ad interfaccia */
                errorHandler.setVisible(true);
            }
        });

        /* Impostiamo un'azione da effettuare quando il bottono di login del taxiDriver viene premuto */
        taxiDriverLoginButton.setOnAction(actionEvent -> {

            /* Inizializziamo le credenziali con quanto è stato inserito dall'utente */
            String username = this.taxiDriverUsernameField.getText().trim();
            String password = this.taxiDriverPasswordField.getText().trim();

            try {
                /* Inizializziamo L'handler con i dati di login */
                TaxiDriver taxiDriver = taxiFinderData.loginTaxiDriver(new TaxiDriver(username,password));

                taxiFinderData.setCurrentUser(taxiDriver);

                /* Cambiamo Stage */
                UtilityController.navigateTo(taxiDriverController,
                        String.format("%s %s - %s", taxiDriver.getFirstName(),
                                taxiDriver.getLastName(),
                                taxiDriver.getUsername()),
                        "Errore di navigazione, alcuni file non sono stati trovati",
                        taxiDriverLoginButton);
            }catch (TaxiDriverNotFoundException e){
                /* Stampiamo l'errore */
                System.out.println(e.getMessage());

                /* Mostriamo l'errore ad interfaccia */
                errorTaxiDriver.setVisible(true);
            }
        });
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

    //==================================================
    //                     Metodi
    //==================================================

    /**
     * Questo metodo constata che le proprietà dei vari controlli siano riempite prima di poter effettuare un
     * login Gestore
     * @return Ritorna una espressione booleana osservabile che constata la qualità di quanto inserito
     * */
    private BooleanExpression invalidHandlerLogin(){
        return Bindings.createBooleanBinding(() -> this.handlerUsernameField.getText().trim().isEmpty() ||
                                                   this.handlerPasswordField.getText().trim().isEmpty(),
                                                   this.handlerUsernameField.textProperty(),
                                                   this.handlerPasswordField.textProperty());
    }

    /**
     * Questo metodo constata che le proprietà dei vari controlli siano riempite prima di poter effettuare un
     * login Tassista
     * @return Ritorna una espressione booleana osservabile che constata la qualità di quanto inserito
     * */
    private BooleanExpression invalidTaxiDriverLogin(){
        return Bindings.createBooleanBinding(() -> this.taxiDriverUsernameField.getText().trim().isEmpty() ||
                        this.taxiDriverPasswordField.getText().trim().isEmpty(),
                        this.taxiDriverUsernameField.textProperty(),
                        this.taxiDriverPasswordField.textProperty());
    }
}