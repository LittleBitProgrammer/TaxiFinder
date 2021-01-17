package com.robertovecchio.controller;

import com.robertovecchio.model.db.TaxiFinderData;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

/**
 * Classe che gestisce la main View del Tassista
 * @author robertovecchio
 * @version 1.0
 * @since 14/01/2021
 * */
public class TaxiDriverController {
    //==================================================
    //               Variabili d'istanza
    //==================================================

    //DB
    private final TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    //==================================================
    //               Variabili FXML
    //==================================================
    @FXML
    VBox vBoxTopContainer;
    @FXML
    StackPane stackContainer;


    //==================================================
    //               Variabili Statiche
    //==================================================

    private static final String mainControllerFile = "src/com/robertovecchio/view/fxml/main.fxml";

    //==================================================
    //               Inizializzazione
    //==================================================
    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize() {
        // Aggiungiamo il menuBar al vBox
        this.vBoxTopContainer.getChildren().addAll(compositeHandlerMEnuBar());
    }

    private MenuBar compositeHandlerMEnuBar(){

        // Inizializzo i MenuItem
        MenuItem moveToPerking = new MenuItem("segnala che sei in un altro parcheggio");

        // Inizializzo i diversi Menu
        Menu home = new Menu();
        Menu parking = new Menu("Parcheggio");
        Menu logout = new Menu();

        // Creo delle Label da inserire nei menu home e logout
        Label homeLabel = new Label("Home");
        Label exitLabel = new Label("Logout");

        // Imposto il colore delle label a bianco
        homeLabel.setTextFill(Color.WHITE);
        exitLabel.setTextFill(Color.WHITE);

        // Inserisco le Label nei menu
        home.setGraphic(homeLabel);
        logout.setGraphic(exitLabel);

        // Inizializziamo un menuBar
        MenuBar menuBar = new MenuBar();

        // Incapsuliamo i diversi menuItem nei corrispettivi menu
        parking.getItems().add(moveToPerking);

        // incapsuliamo i diversi menu nel menuBar
        menuBar.getMenus().addAll(home, parking, logout);

        // Aggiungiamo un'azione quando viene premuto home
        homeLabel.setOnMouseClicked(actionEvent -> System.out.println("Home premuto"));

        // Aggiungiamo un'azione quando moveToParking viene premuto
        moveToPerking.setOnAction(actionEvent -> {
            System.out.println("muoviti premuto");
        });

        // Aggiungiamo un'azione quando Logout viene premuto
        exitLabel.setOnMouseClicked(actionEvent -> UtilityController.navigateTo(mainControllerFile, "Taxi Finder",
                "Errore reperimento interfaccia", exitLabel));

        return menuBar;
    }
}
