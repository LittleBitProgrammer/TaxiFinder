package com.robertovecchio.controller;

import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.db.error.CustomerNotFoundException;
import com.robertovecchio.model.user.Customer;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
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
    //               Variabili d'istanza
    //==================================================

    // DB
    /**
     * Istanza del database
     * @see TaxiFinderData
     * */
    TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    //==================================================
    //               Variabili FXML
    //==================================================

    /**
     * Bottone utile a gestire il caso in cui si voglia tornare indietro
     * @see Button
     */
    @FXML
    Button backButton;
    /**
     * Immagine che rappresenta il logo degli utenti
     * @see ImageView
     */
    @FXML
    ImageView userImage;
    /**
     * VBox associato alla View
     * @see VBox
     */
    @FXML
    VBox vboxUser;
    /**
     * TextField utile a comporre l'username dell'utente
     * @see TextField
     */
    @FXML
    TextField usernameField;
    /**
     * PasswordField utile a comporre la password dell'utente
     * @see PasswordField
     */
    @FXML
    PasswordField passwordField;
    /**
     * Bottone utile a gestire il caso in cui si voglia effettuare l'accesso come cliente
     * @see Button
     */
    @FXML
    Button loginButton;
    /**
     * Label che mostra l'errore per credenziali sbagliate. Quest'ultima di default è impostata ad invisibile e lo sarà
     * Fino a quando non viene effettivamente constatato l'errore
     */
    @FXML
    Label errorCustomer;

    //==================================================
    //               Variabili Statiche
    //==================================================

    /**
     * Variabile statica che rappresenta il percorso alla view principale
     * */
    private final static String controllerFile = "src/com/robertovecchio/view/fxml/main.fxml";
    /**
     * Variabile statica che rappresenta il percorso alla view di registrazione
     * */
    private final static String registrationFile = "src/com/robertovecchio/view/fxml/customerRegistration.fxml";
    /**
     * Variabile statica che rappresenta il percorso alla view del cliente
     * */
    private final static String customerControllerFile = "src/com/robertovecchio/view/fxml/customer.fxml";
    /**
     * Variabile statica che rappresenta il percorso al foglio di stile associato alla corrispettiva view
     * */
    private final static String customerRegistrationStyle = "com/robertovecchio/view/fxml/style/customerRegistration.css";
    /**
     * Variabile statica che rappresenta il percorso al logo utente
     * */
    private final static String userLogo = "Assets/user.png";
    /**
     * Variabile statica che rappresenta il font principale
     * */
    private final static String fontFamily = "Helvetica";
    /**
     * Variabile statica che rappresenta la grandezza del font
     * */
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
            /* Creiamo una nuova immagine dal path userLogo */
            Image logo = new Image(new FileInputStream(userLogo));

            /* Impostiamo l'immagine */
            userImage.setImage(logo);
            userImage.setFitHeight(80); // impostiamo altezza
            userImage.setPreserveRatio(true); // impostiamo la conservazione delle proporzioni originali
        }catch (FileNotFoundException e){
            /* Nel caso in cui l'errore sia presente stampiamolo a terminale */
            System.out.println("Errore di caricamento Immagine");
        }

        /* inizializziamo un textFlow */
        TextFlow textFlow = new TextFlow();

        /* Creiamo la prima parte della registrazione */
        Text registrationText = new Text("Non sei ancora registrato? ");
        registrationText.setFont(Font.font(fontFamily, FontWeight.THIN, fontSize));

        /* Creiamo la seconda parte della registrazione */
        Text registrationTextSecondPart = new Text("Clicca qui!");
        registrationTextSecondPart.setFont(Font.font(fontFamily + " bold", FontWeight.BOLD, 17));
        registrationTextSecondPart.setFill(Color.web("#1134FD"));
        registrationTextSecondPart.setId("registration");

        /* aggiungiamo varie parti del testo di registrazione al textFlow */
        textFlow.getChildren().addAll(registrationText, registrationTextSecondPart);

        /* allineiamo il texflow al centro */
        textFlow.setTextAlignment(TextAlignment.CENTER);

        /* impostiamo un piccolo distanziamento dai campi di login */
        textFlow.setPadding(new Insets(20));

        /* aggiungiamo al vbox il textflow */
        vboxUser.getChildren().add(textFlow);

        /* aggiungiamo un'azione da compiere quando l'utente clicca sul tasto di registrazione */
        registrationTextSecondPart.setOnMouseClicked(mouseEvent -> {
            handleRegistrationButton();
        });

        /* impostiamo il bottone login a disabilitato quando l'input utente non corrisponde ai criteri richiesti */
        this.loginButton.disableProperty().bind(invalidCustomerLoginField());
    }

    //==================================================
    //                  Metodi FXML
    //==================================================

    /**
     * Metodo utile a gestire il caso in cui si voglia tornare indietro rispetto alla view attuale
     */
    @FXML
    private void handleBackButton(){
        UtilityController.navigateTo(controllerFile, "Taxi Finder",
                "Errore di caricamento file interfaccia main", backButton);
    }

    /**
     * Metodo utile a gestire il login
     */
    @FXML
    private void handleLogin(){
        /* Inizializzo le credenziali con quanto è stato inserito dall'utente */
        String username = this.usernameField.getText().trim();
        String password = this.passwordField.getText();

        /* Cambiamo Scene */
        try {
            Customer customer = taxiFinderData.loginCustomer(new Customer(username, password));
            taxiFinderData.setCurrentUser(customer);

            UtilityController.navigateTo(customerControllerFile,
                                         String.format("Cliente - %s %s", customer.getFirstName(), customer.getLastName()),
                                         "Errore di navigazione", this.loginButton);
        }catch (CustomerNotFoundException e){
            /* Stampiamo l'errore */
            System.out.println(e.getMessage());

            /* Impostiamo la label di errore a visibile */
            errorCustomer.setVisible(true);
        }
    }

    //==================================================
    //                     Metodi
    //==================================================

    /**
     * Metodo utile a gestire il caso in cui un utente abbia intenzione di registrarsi
     */
    private void handleRegistrationButton(){
        UtilityController.navigateTo(registrationFile,"Registra Cliente",
                               "Interfaccia di registrazione di un cliente non trovata", backButton,
                                     customerRegistrationStyle);
    }

    /**
     * Questo metodo constata che le proprietà dei vari controlli siano riempite prima di poter effettuare un login
     * @return Ritorna una espressione booleana osservabile che constata la qualità di quanto inserito
     * @see BooleanExpression
     * */
    private BooleanExpression invalidCustomerLoginField(){
        return Bindings.createBooleanBinding(() -> this.usernameField.getText().trim().isEmpty() ||
                                                   this.passwordField.getText().trim().isEmpty(),
                                                   this.usernameField.textProperty(),
                                                   this.passwordField.textProperty());
    }
}
