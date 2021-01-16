package com.robertovecchio.controller;

import com.robertovecchio.model.db.TaxiFinderData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Classe che gestisce la main View del Cliente
 * @author robertovecchio
 * @version 1.0
 * @since 14/01/2021
 * */
public class CustomerController {
    //==================================================
    //               Variabili d'istanza
    //==================================================

    //DB
    private final TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    //==================================================
    //               Variabili Statiche
    //==================================================

    private static final String mainControllerFile = "src/com/robertovecchio/view/fxml/main.fxml";

    //==================================================
    //               Variabili FXML
    //==================================================

    @FXML
    private VBox vBoxTopContainer;

    //==================================================
    //               Inizializzazione
    //==================================================
    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize(){
        vBoxTopContainer.getChildren().addAll(compositeCustomerMenu());
    }

    //==================================================
    //               Metodi
    //==================================================
    private MenuBar compositeCustomerMenu(){
        // Inizializziamo il menuItem
        MenuItem addBooking = new MenuItem("Prenota una corsa");

        // Inizializziamo i menu
        Menu home = new Menu();
        Menu booking = new Menu("Prenotazione");
        Menu logOut = new Menu();

        // Creiamo delle Label da inserire nei menu home e logout
        Label homeLabel = new Label("Home");
        Label exitLabel = new Label("Logout");

        // Impostiamo il colore delle label a bianco
        homeLabel.setTextFill(Color.WHITE);
        exitLabel.setTextFill(Color.WHITE);

        // Inserisco le Label nei menu
        home.setGraphic(homeLabel);
        logOut.setGraphic(exitLabel);

        // Inizializziamo un menuBar
        MenuBar menuBar = new MenuBar();

        // Incapsuliamo i menuItem nei corrispettivi menu
        booking.getItems().add(addBooking);

        // Aggiungiamo un'azione quando viene premuto home
        homeLabel.setOnMouseClicked(actionEvent -> System.out.println("Home premuto"));

        // Aggiungiamo un'azione quando viene premuto Prenota una corsa
        addBooking.setOnAction(actionEvent -> System.out.println("Prenotazione premuto"));

        // Aggiungiamo un'azione quando Logout viene premuto
        exitLabel.setOnMouseClicked(actionEvent -> UtilityController.navigateTo(mainControllerFile, "Taxi Finder",
                "Errore reperimento interfaccia", exitLabel));

        // incapsuliamo i diversi menu nel menuBar
        menuBar.getMenus().addAll(home, booking, logOut);

        return menuBar;
    }
}
