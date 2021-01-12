package com.robertovecchio.controller;

import com.robertovecchio.controller.dialog.AddTaxiDriverController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

/**
 * Classe che gestisce la main View dell'handler (admin)
 * @author robertovecchio
 * @version 1.0
 * @since 08/01/2021
 * */
public class HandlerController {

    //==================================================
    //               Variabili FXML
    //==================================================
    @FXML
    VBox vBoxContainer;

    //==================================================
    //               Variabili Statiche
    //==================================================

    private static final String mainControllerFile = "src/com/robertovecchio/view/fxml/main.fxml";
    private static final String addTaxiDriverControllerFile = "src/com/robertovecchio/view/fxml/dialog/addTaxiDriver.fxml";
    private static final String removeTaxiDriverControllerFile = "src/com/robertovecchio/view/fxml/dialog/removeTaxiDriver.fxml";
    private static final String addParkingControllerFile = "src/com/robertovecchio/view/fxml/dialog/addParking.fxml";
    private static final String removeParkingControllerFile = "src/com/robertovecchio/view/fxml/dialog/removeParking.fxml";
    private static final String addWaitingStationControllerFile = "src/com/robertovecchio/view/fxml/dialog/addWaitingstation.fxml";
    private static final String removeWaitingStationControllerFile = "src/com/robertovecchio/view/fxml/dialog/removeWaitingStation.fxml";

    //==================================================
    //               Inizializzazione
    //==================================================
    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize(){
        // Aggiungiamo il menuBar al vBox
        vBoxContainer.getChildren().addAll(compositeHandlerMEnuBar(), compositeToolBar());
    }

    //==================================================
    //                     Metodi
    //==================================================

    /**
     * Questo metodo ha lo scopo di ritornare un menuBar composto prettamente per l'admin
     * @return Una MenuBar composta dai vari sottomenu attinenti alla figura del gestore
     * @see MenuBar
     * */
    private MenuBar compositeHandlerMEnuBar(){
        // Inizializzo i menuItem del tassista
        MenuItem hireTaxiDriver = new MenuItem("Assumi Tassista");
        MenuItem firesTaxiDriver = new MenuItem("Congeda Tassista");

        // Inizializzo i menuItem del parcheggio
        MenuItem addParking = new MenuItem("Aggiungi parcheggio");
        MenuItem removeParking = new MenuItem("Rimuovi Parcheggio");

        // Inizializzo i menuItem delle postazioni d'attesa
        MenuItem addWaitingStation = new MenuItem("Aggiungi postazione");
        MenuItem removeWaitingStation = new MenuItem("Rimuovi Postazione");

        // Inizializzo i diversi menu
        Menu home = new Menu();
        Menu taxiDriver = new Menu("Tassista");
        Menu parking = new Menu("Parcheggio");
        Menu waitingStation = new Menu("Postazione");
        Menu exit = new Menu();

        // Creo delle Label da inserire nei menu home e logout
        Label homeLabel = new Label("Home");
        Label exitLabel = new Label("Logout");

        // Imposto il colore delle label a bianco
        homeLabel.setTextFill(Color.WHITE);
        exitLabel.setTextFill(Color.WHITE);

        // Inserisco le Label nei menu
        home.setGraphic(homeLabel);
        exit.setGraphic(exitLabel);

        // Inizializziamo un menuBar
        MenuBar menuBar = new MenuBar();

        // Incapsuliamo i menuItem di tassista nel suo menu
        taxiDriver.getItems().addAll(hireTaxiDriver, firesTaxiDriver);

        // Incapsuliamo i menuItem di parcheggio nel suo menu
        parking.getItems().addAll(addParking, removeParking);

        // Incapsuliamo i menuItem di postazione nel suo menu
        waitingStation.getItems().addAll(addWaitingStation, removeWaitingStation);

        // incapsuliamo i diversi menu nel menuBar
        menuBar.getMenus().addAll(home, taxiDriver, parking, waitingStation,exit);

        // Aggiungiamo un'azione quando viene premuto home
        homeLabel.setOnMouseClicked(actionEvent -> {
            System.out.println("Home premuto");
        });

        //==================================================
        //                 Aggiungi Tassista
        //==================================================

        // Aggiungiamo un'azione quando viene cliccato assumi tassista
        hireTaxiDriver.setOnAction(actionEvent -> {
            // creiamo un nuovo dialog da visualizzare
            Dialog<ButtonType> dialog = new Dialog<>();

            // inizializziamo il proprietario
            dialog.initOwner(this.vBoxContainer.getScene().getWindow());

            // Impostiamo il titolo del dialog
            dialog.setTitle("Assumi un tassista");

            // Carichiamo il file di iterfaccia per il dialog
            FXMLLoader loader = new FXMLLoader();

            try{
                Parent root = loader.load(new FileInputStream(addTaxiDriverControllerFile));
                dialog.getDialogPane().setContent(root);
            }catch (IOException e){
                System.out.println("Errore di caricamento dialog");
                e.printStackTrace();
            }

            // Aggiungiamo il bottone OK al dialogPane
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

            AddTaxiDriverController addTaxiDriverController = loader.getController();
            dialog.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(addTaxiDriverController.invalidInputProperty());

            // Gestione Errori
            dialog.getDialogPane().lookupButton(ButtonType.APPLY).addEventFilter(ActionEvent.ACTION,
                    event->{
                        if (!addTaxiDriverController.validateDate()){
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Età errata", ButtonType.OK);
                            alert.setHeaderText("L'utente non può essere un minore");
                            alert.setContentText("Hai inserito un utente con età inferiore ai 18 anni");

                            Optional<ButtonType> result = alert.showAndWait();

                            event.consume();
                        }else if (!addTaxiDriverController.validateEmail()){
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Email errata", ButtonType.OK);
                            alert.setHeaderText("Hai inserito una email utente errata");
                            alert.setContentText("Inserisci una email valida");

                            Optional<ButtonType> result = alert.showAndWait();

                            event.consume();
                        }else if (!addTaxiDriverController.validatePassword()){
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Password errata", ButtonType.OK);
                            alert.setHeaderText("Hai inserito una password utente errata");
                            alert.setContentText("Inserisci una password con lunghezza maggiore di 3 e minore di 15");

                            Optional<ButtonType> result = alert.showAndWait();

                            event.consume();
                        }else if (!addTaxiDriverController.validateFiscalCode()){
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Codice Fiscale errato", ButtonType.OK);
                            alert.setHeaderText("Hai inserito un codice fiscale errato");
                            alert.setContentText("Inserisci un codice fiscale con lunghezza pari a 16");

                            Optional<ButtonType> result = alert.showAndWait();

                            event.consume();
                        }
                    });

            // Gestiamo il controller mostrandolo e aspettando che l'utente vi interagisca
            Optional<ButtonType> result = dialog.showAndWait();

            // Gestiamo il caso in cui l'utente abbia premuto OK
            if (result.isPresent() && result.get() == ButtonType.APPLY){
                //TODO//: Selezione del modello aggiunto
                addTaxiDriverController.processAddTaxiDriver();
            }
        });

        // Aggiungiamo un'azione quando viene cliccato licenzia tassista
        firesTaxiDriver.setOnAction(actionEvent -> {
            try{
                UtilityController.showDialog(this.vBoxContainer.getScene().getWindow(),
                        "Congeda un tassista",
                        removeTaxiDriverControllerFile,
                        "errore durante il caricamento del dialog",
                        ()->{
                            System.out.println("Callable chiamata su OK");
                        },ButtonType.OK, ButtonType.CANCEL);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        // Aggiungiamo un'azione quando viene cliccato aggiungi parcheggio
        addParking.setOnAction(actionEvent -> {
            try{
                UtilityController.showDialog(this.vBoxContainer.getScene().getWindow(),
                        "Aggiungi un parcheggio",
                        addParkingControllerFile,
                        "errore durante il caricamento del dialog",
                        ()->{
                            System.out.println("Callable chiamata su OK");
                        },ButtonType.OK, ButtonType.CANCEL);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        // Aggiungiamo un'azione quando viene cliccato rimuovi parcheggio
        removeParking.setOnAction(actionEvent -> {
            try{
                UtilityController.showDialog(this.vBoxContainer.getScene().getWindow(),
                        "Rimuovi un parcheggio",
                        removeParkingControllerFile,
                        "errore durante il caricamento del dialog",
                        ()->{
                            System.out.println("Callable chiamata su OK");
                        },ButtonType.OK, ButtonType.CANCEL);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        // Aggiungiamo un'azione quando viene cliccato aggiungi postazione
        addWaitingStation.setOnAction(actionEvent -> {
            try{
                UtilityController.showDialog(this.vBoxContainer.getScene().getWindow(),
                        "Aggiungi una postazione",
                        addWaitingStationControllerFile,
                        "errore durante il caricamento del dialog",
                        ()->{
                            System.out.println("Callable chiamata su OK");
                        },ButtonType.OK, ButtonType.CANCEL);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        // Aggiungiamo un'azione quando viene cliccato rimuovi parcheggio
        removeWaitingStation.setOnAction(actionEvent -> {
            try{
                UtilityController.showDialog(this.vBoxContainer.getScene().getWindow(),
                        "Rimuovere una postazione",
                        removeParkingControllerFile,
                        "errore durante il caricamento del dialog",
                        ()->{
                            System.out.println("Callable chiamata su OK");
                        },ButtonType.OK, ButtonType.CANCEL);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        // Aggiungiamo un'azione quando Logout viene premuto
        exitLabel.setOnMouseClicked(actionEvent -> {
            UtilityController.changeStageTo(mainControllerFile, "Taxi Finder",
                                      "Errore reperimento interfaccia", exitLabel);
        });

        return menuBar;
    }

    /**
     * Questo metodo ha lo scopo di ritornare una ToolBar composta prettamente per l'admin
     * @return Una ToolBar composta dai vari sottomenu attinenti alla figura del gestore
     * @see ToolBar
     */
    private ToolBar compositeToolBar(){
        // Inizializzo la toolBar
        ToolBar toolBar = new ToolBar();

        Button visualizeTaxiDriver = new Button("Visualizza Tassisti");
        Button visualizeParking= new Button("Visualizza Parcheggi");
        Button visualizeWaitingStations = new Button("Visualizza Postazioni");

        toolBar.getItems().addAll(visualizeTaxiDriver, visualizeParking, visualizeWaitingStations);

        // Aggiungiamo un'azione quando visualizza tassisti viene premuto
        visualizeTaxiDriver.setOnAction(actionEvent -> {
            System.out.println("Visualizza tassisti premuto");
        });

        //Aggiungiamo un'azione quando visualizza parcheggi viene premuto
        visualizeParking.setOnAction(actionEvent -> {
            System.out.println("Visualizza parcheggi premuto");
        });

        //Aggiungiamo un'azione quando visualizza postazioni viene premuto
        visualizeWaitingStations.setOnAction(actionEvent -> {
            System.out.println("Visualizza postazioni premuto");
        });

        return toolBar;
    }
}