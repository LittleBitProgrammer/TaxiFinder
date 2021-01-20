package com.robertovecchio.controller;

import com.robertovecchio.controller.dialog.*;
import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.graph.node.Parking;
import com.robertovecchio.model.graph.node.WaitingStation;
import com.robertovecchio.model.user.TaxiDriver;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.layout.StackPane;
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
    //               Variabili d'istanza
    //==================================================

    //DB
    /**
     * Istanza del database
     * @see TaxiFinderData
     * */
    private final TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    /**
     * Variabile booleana che serve a gestire quando inizializzare la TableView dei parcheggi, per garantire
     * migliori prestazioni
     */
    private boolean isFirstEntryParking = true;
    /**
     * Variabile booleana che serve a gestire quando inizializzare la TableView delle postazioni di attesa, per
     * garantire migliori prestazioni
     */
    private boolean isFirstEntryWaiting = true;

    // TableView
    /**
     * TableView utile a mostrare la lista dei tassisti
     * @see TableView
     * @see TaxiDriver
     */
    private TableView<TaxiDriver> tableTaxiDriver;
    /**
     * TableView utile a mostrare la lista dei parcheggi
     * @see TableView
     * @see Parking
     */
    private TableView<Parking> tableParking;
    /**
     * TableView utile a mostrare la lista delle postazioni
     * @see TableView
     * @see WaitingStation
     */
    private TableView<WaitingStation> tableWaitingStation;

    // Columns - tableTaxiDriver
    /**
     * TableColumn (colonna) utile a mostrare il codice fiscale dei vari tassisti
     * @see TableColumn
     * @see TaxiDriver
     */
    private TableColumn<TaxiDriver, String> fiscalCodeColumn;
    /**
     * TableColumn (colonna) utile a mostrare il nome dei vari tassisti
     * @see TableColumn
     * @see TaxiDriver
     */
    private TableColumn<TaxiDriver, String> firstNameColumn;
    /**
     * TableColumn (colonna) utile a mostrare il cognome dei vari tassisti
     * @see TableColumn
     * @see TaxiDriver
     */
    private TableColumn<TaxiDriver, String> lastNameColumn;
    /**
     * TableColumn (colonna) utile a mostrare la data di nascita dei vari tassisti
     * @see TableColumn
     * @see TaxiDriver
     */
    private TableColumn<TaxiDriver, String> dateOfBirthColumn;
    /**
     * TableColumn (colonna) utile a mostrare il genere dei vari tassisti
     * @see TableColumn
     * @see TaxiDriver
     */
    private TableColumn<TaxiDriver, String> genderColumn;
    /**
     * TableColumn (colonna) utile a mostrare l'email dei vari tassisti
     * @see TableColumn
     * @see TaxiDriver
     */
    private TableColumn<TaxiDriver, String> emailColumn;
    /**
     * TableColumn (colonna) utile a mostrare la targa dei taxi dei vari tassisti
     * @see TableColumn
     * @see TaxiDriver
     */
    private TableColumn<TaxiDriver, String> taxiColumn;
    /**
     * TableColumn (colonna) utile a mostrare il numero di licensa dei vari tassisti
     * @see TableColumn
     * @see TaxiDriver
     */
    private TableColumn<TaxiDriver, String> licenseNumberColumn;
    /**
     * TableColumn (colonna) utile a mostrare lo stato lavorativo dei vari tassisti
     * @see TableColumn
     * @see TaxiDriver
     */
    private TableColumn<TaxiDriver, String> stateColumn;

    // Columns - tableParking
    /**
     * TableColumn (colonna) utile a mostrare la latitudine di un parcheggio
     * @see TableColumn
     * @see Parking
     */
    private TableColumn<Parking, String> latitudeColumn;
    /**
     * TableColumn (colonna) utile a mostrare la longitudine di un parcheggio
     * @see TableColumn
     * @see Parking
     */
    private TableColumn<Parking, String> longitudeColumn;
    /**
     * TableColumn (colonna) utile a mostrare il nome della strada in cui un parcheggio è locato
     * @see TableColumn
     * @see Parking
     */
    private TableColumn<Parking, String> streetNameColumn;
    /**
     * TableColumn (colonna) utile a mostrare il civico in cui un parcheggio è locato
     * @see TableColumn
     * @see Parking
     */
    private TableColumn<Parking, String> streetNumberColumn;
    /**
     * TableColumn (colonna) utile a mostrare il nome di un parcheggio
     * @see TableColumn
     * @see Parking
     */
    private TableColumn<Parking, String> stationNameColumn;
    /**
     * TableColumn (colonna) utile a mostrare la capacità di un parcheggio
     * @see TableColumn
     * @see Parking
     */
    private TableColumn<Parking, String> capacityColumn;

    // Columns - tableWaitingStation
    /**
     * TableColumn (colonna) utile a mostrare la latitudine di una postazione
     * @see TableColumn
     * @see WaitingStation
     */
    private TableColumn<WaitingStation, String>  latitudeWaitingStationColumn;
    /**
     * TableColumn (colonna) utile a mostrare la longitudine di una postazione
     * @see TableColumn
     * @see WaitingStation
     */
    private TableColumn<WaitingStation, String> longitudeWaitingStationColumn;
    /**
     * TableColumn (colonna) utile a mostrare il nome di una strada in cui una postazione è locata
     * @see TableColumn
     * @see WaitingStation
     */
    private TableColumn<WaitingStation, String> streetNameWaitingStationColumn;
    /**
     * TableColumn (colonna) utile a mostrare il civico in una postazione è locata
     * @see TableColumn
     * @see WaitingStation
     */
    private TableColumn<WaitingStation, String> streetNumberWaitingStationColumn;
    /**
     * TableColumn (colonna) utile a mostrare il nome di una postazione
     * @see TableColumn
     * @see WaitingStation
     */
    private TableColumn<WaitingStation, String> stationNameWaitingStationColumn;

    // Observable List
    /**
     * ObservableList dei tassisti
     * @see ObservableList
     * @see TaxiDriver
     */
    ObservableList<TaxiDriver> drivers;
    /**
     * ObservableList dei parcheggi
     * @see ObservableList
     * @see Parking
     */
    ObservableList<Parking> parkings;
    /**
     * ObservableList delle postazioni
     * @see ObservableList
     * @see WaitingStation
     */
    ObservableList<WaitingStation> waitingStations;

    // ContextMenu
    /**
     * ContextMenu dei tassisti
     * @see ContextMenu
     */
    private ContextMenu contextMenu;
    /**
     * ContextMenu dei parcheggi
     * @see ContextMenu
     */
    private ContextMenu roadContextMenu;
    /**
     * ContextMenu delle postazioni
     * @see ContextMenu
     */
    private ContextMenu roadWaitingContextMenu;

    //==================================================
    //               Variabili FXML
    //==================================================

    /**
     * VBox associato alla view
     * @see VBox
     */
    @FXML
    VBox vBoxContainer;
    /**
     * VBox centrale associato alla view
     * @see VBox
     */
    @FXML
    StackPane vBoxCenterContainer;

    //==================================================
    //               Variabili Statiche
    //==================================================

    /**
     * Variabile statica che rappresenta il percorso alla view principale
     * */
    private static final String mainControllerFile = "src/com/robertovecchio/view/fxml/main.fxml";
    /**
     * Variabile statica che rappresenta il percorso al dialog di aggiunta tassista
     * */
    private static final String addTaxiDriverControllerFile = "src/com/robertovecchio/view/fxml/dialog/addTaxiDriver.fxml";
    /**
     * Variabile statica che rappresenta il percorso al dialog di rimozione tassista
     * */
    private static final String removeTaxiDriverControllerFile = "src/com/robertovecchio/view/fxml/dialog/removeTaxiDriver.fxml";
    /**
     * Variabile statica che rappresenta il percorso al dialog di aggiunta parcheggio
     * */
    private static final String addParkingControllerFile = "src/com/robertovecchio/view/fxml/dialog/addParking.fxml";
    /**
     * Variabile statica che rappresenta il percorso al dialog di rimozione parcheggio
     * */
    private static final String removeParkingControllerFile = "src/com/robertovecchio/view/fxml/dialog/removeParking.fxml";
    /**
     * Variabile statica che rappresenta il percorso al dialog aggiungi postazione
     * */
    private static final String addWaitingStationControllerFile = "src/com/robertovecchio/view/fxml/dialog/addWaitingstation.fxml";
    /**
     * Variabile statica che rappresenta il percorso al dialog rimuovi parcheggio
     */
    private static final String removeWaitingStationControllerFile = "src/com/robertovecchio/view/fxml/dialog/removeWaitingStation.fxml";
    /**
     * Variabile statica che rappresenta il percorso al dialog utile a mostrare un veicolo
     * */
    private static final String showVehicleControllerFile = "src/com/robertovecchio/view/fxml/dialog/showVehicle.fxml";
    /**
     * Variabile statica che rappresenta il percorso al dialog di aggiunta collegamento
     * */
    private static final String addConnectionControllerFile = "src/com/robertovecchio/view/fxml/dialog/addEdge.fxml";
    /**
     * Variabile statica che rappresenta il percorso al dialog di rimozione collegamento
     * */
    private static final String removeConnectionControllerFile = "src/com/robertovecchio/view/fxml/dialog/removeEdge.fxml";
    /**
     * Variabile statica che rappresenta il percorso al dialog utile a mostrare i collegamenti
     * */
    private static final String showConnectionsControllerFile = "src/com/robertovecchio/view/fxml/dialog/showConnections.fxml";
    /**
     * Variabile statica che rappresenta il percorso aldialog utile a mostrare i taxi presenti in un parcheggio
     * */
    private static final String showTaxisControllerFile = "src/com/robertovecchio/view/fxml/dialog/showTaxis.fxml";
    /**
     * Variabile statica che rappresenta il percorso al dialog utile a mostrare dove un auto sosta in quel momento
     * */
    private static final String showWaitingParkingControllerFile = "src/com/robertovecchio/view/fxml/dialog/showWaitingPArking.fxml";

    //==================================================
    //               Inizializzazione
    //==================================================

    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize(){
        /* Aggiungiamo il menuBar al vBox */
        vBoxContainer.getChildren().addAll(compositeHandlerMEnuBar(), compositeToolBar());

        /* Inizializziamo un contextMenu */
        contextMenu = new ContextMenu();
        roadContextMenu = new ContextMenu();
        roadWaitingContextMenu = new ContextMenu();

        /* Inizializziamo un menu Item */
        MenuItem menuItem = new MenuItem("Mostra veicolo");
        MenuItem waitingItem = new MenuItem("Mostra dove sosta");
        MenuItem showItem = new MenuItem("Mostra Collegamenti parcheggio");
        MenuItem showTaxis = new MenuItem("Mostra Tassisti nel parcheggio");
        MenuItem showRoadItem = new MenuItem("Mostra Collegamenti postazione");

        /* Impostiamo un'azione quando menuItem viene cliccato */
        menuItem.setOnAction(actionEvent -> {
            /* Reperiamo il tassista cliccato */
            TaxiDriver taxiDriver = tableTaxiDriver.getSelectionModel().getSelectedItem();

            /* Mostriamo il veicolo passando in input il tassista precedentemente memorizzato */
            this.showVehicle(taxiDriver);
        });

        /* Impostiamo un'azione quando showItem viene cliccato */
        showItem.setOnAction(actionEvent -> {
            /* Reperiamo il parcheggio cliccato */
            WaitingStation waitingStation = tableParking.getSelectionModel().getSelectedItem();

            /* Mostriamo i collegamenti passando in input il parcheggio precedentemente memorizzato */
            this.showConnections(waitingStation);
        });

        /* Impostiamo un'azione quando showRoadItem viene cliccato */
        showRoadItem.setOnAction(actionEvent -> {
            /* Reperiamo la postazione cliccata */
            WaitingStation waitingStation = tableWaitingStation.getSelectionModel().getSelectedItem();

            /* Mostriamo i collegamenti passando in input la postazione precedentemente memorizzata */
            this.showConnections(waitingStation);
        });

        /* Impostiamo un'azione quando showTaxis viene cliccato */
        showTaxis.setOnAction(actionEvent -> {
            /* Reperiamo il parcheggio cliccato */
            Parking parking = tableParking.getSelectionModel().getSelectedItem();

            /* Mostriamo i taxi passando in input il parcheggio precedentemente memorizzata */
            this.showTaxis(parking);
        });

        /* Impostiamo un'azione quando waitingItem viene cliccato */
        waitingItem.setOnAction(actionEvent -> {
            /* Reperiamo il tassista cliccato */
            TaxiDriver taxiDriver = tableTaxiDriver.getSelectionModel().getSelectedItem();

            /* Mostriamo il parcheggio dove è in sosta in questo momento, passando in input il parcheggio
             * precedentemente memorizzata */
            this.showWaiting(taxiDriver);
        });



        /* Aggiungiamo i menuItem ai contextMenu */
        contextMenu.getItems().addAll(menuItem, waitingItem);
        roadContextMenu.getItems().addAll(showItem, showTaxis);
        roadWaitingContextMenu.getItems().add(showRoadItem);

        /* Inizializziamo la collections */

        /* Tassisti */
        this.drivers = taxiFinderData.getTaxiDrivers();
        /* Parcheggi */
        this.parkings = taxiFinderData.getParkings();
        /* Postazioni*/
        this.waitingStations = taxiFinderData.getWaitingStations();

        /* Inizializziamo le TableView */
        compositeTaxiDriverTableView();

        /* Aggiungiamo al vbox centrale la tableView dei tassisti */
        vBoxCenterContainer.getChildren().add(tableTaxiDriver);
    }

    //==================================================
    //                     Metodi
    //==================================================

    /**
     * Questo metodo ha lo scopo di ritornare un menuBar dell' l'admin
     * @return Un MenuBar composto dai vari sottomenu attinenti alla figura del gestore
     * @see MenuBar
     * */
    private MenuBar compositeHandlerMEnuBar(){

        /* Inizializziamo i menuItem del tassista */
        /*-------------->Tassista<----------------*/

        /* Assumi tassista */
        MenuItem hireTaxiDriver = new MenuItem("Assumi Tassista");
        /* Congeda tassista */
        MenuItem firesTaxiDriver = new MenuItem("Congeda Tassista");

        /* Inizializziamo i menuItem del parcheggio */
        /*-------------->Parcheggio<----------------*/

        /* Aggiungi parcheggio */
        MenuItem addParking = new MenuItem("Aggiungi parcheggio");
        /* Rimuovi parcheggio */
        MenuItem removeParking = new MenuItem("Rimuovi Parcheggio");

        /* Inizializziamo i menuItem delle postazioni d'attesa */
        /*-------------->Postazioni<----------------*/

        /* Aggiungi postazione */
        MenuItem addWaitingStation = new MenuItem("Aggiungi postazione");
        /* Rimuovi postazione */
        MenuItem removeWaitingStation = new MenuItem("Rimuovi Postazione");

        /* Inizializziamo i menuItem dei collegamenti */
        /*-------------->Collegamenti<----------------*/

        /* Aggiungi collegamento */
        MenuItem addConnection = new MenuItem("Aggiungi collegamento");
        /* Rimuovi collegamento */
        MenuItem removeConnection = new MenuItem("Rimuovi Collegamento");

        /* Inizializziamo i diversi menu */

        /* Home */
        Menu home = new Menu();
        /* Tassista */
        Menu taxiDriver = new Menu("Tassista");
        /* Parcheggio */
        Menu parking = new Menu("Parcheggio");
        /* Postazione */
        Menu waitingStation = new Menu("Postazione");
        /* Collegamento */
        Menu connections = new Menu("Collegamento");
        /* Logout */
        Menu exit = new Menu();

        /* Creiamo delle Label da inserire nei menu home e logout */

        /* Home */
        Label homeLabel = new Label("Home");
        /*Logout */
        Label exitLabel = new Label("Logout");

        /* Impostiamo il colore delle label a bianco */
        homeLabel.setTextFill(Color.WHITE);
        exitLabel.setTextFill(Color.WHITE);

        /* Inseriamo le Label nei menu */
        home.setGraphic(homeLabel);
        exit.setGraphic(exitLabel);

        /* Inizializziamo un menuBar */
        MenuBar menuBar = new MenuBar();

        /* Incapsuliamo i menuItem di tassista nel suo menu */
        /* Tassista */
        /* --Assumi tassista */
        /* --Congeda tassista */
        taxiDriver.getItems().addAll(hireTaxiDriver, firesTaxiDriver);

        /* Incapsuliamo i menuItem di parcheggio nel suo menu */
        /* Parcheggio */
        /* --Aggiungi parcheggio */
        /* --Rimuovi parcheggio */
        parking.getItems().addAll(addParking, removeParking);

        /* Incapsuliamo i menuItem di postazione nel suo menu */
        /* Postazione */
        /* --Aggiungi postazione */
        /* --Rimuovi postazione*/
        waitingStation.getItems().addAll(addWaitingStation, removeWaitingStation);

        /* Incapsuliamo i menuItem di collegamento nel suo menu */
        /* Collegamento */
        /* --Aggiungi collegamenti*/
        /* --Rimuovi collegamenti */
        connections.getItems().addAll(addConnection,removeConnection);

        /* Incapsuliamo i diversi menu nel menuBar */
        menuBar.getMenus().addAll(home, taxiDriver, parking, waitingStation, connections, exit);

        /* Aggiungiamo un'azione quando viene premuto home */
        homeLabel.setOnMouseClicked(actionEvent -> System.out.println("Home premuto"));

        //==================================================
        //                 Aggiungi Tassista
        //==================================================

        /* Aggiungiamo un'azione quando viene cliccato assumi tassista */
        hireTaxiDriver.setOnAction(actionEvent -> {
            /* creiamo un nuovo dialog da visualizzare */
            Dialog<ButtonType> dialog = new Dialog<>();

            /* inizializziamo il proprietario */
            dialog.initOwner(this.vBoxContainer.getScene().getWindow());

            /* Impostiamo il titolo del dialog */
            dialog.setTitle("Assumi un tassista");

            /* Carichiamo il file di iterfaccia per il dialog */
            FXMLLoader loader = new FXMLLoader();

            try{
                /* Carichiamo il file FXML */
                Parent root = loader.load(new FileInputStream(addTaxiDriverControllerFile));
                dialog.getDialogPane().setContent(root);
            }catch (IOException e){
                /* In caso di errore, questo verrà stampato a terminale */
                System.out.println("Errore di caricamento dialog");
                e.printStackTrace();
            }

            /* Aggiungiamo il bottone OK e CANCEL al dialogPane */
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

            /* Iniazializziamo il controller corrente*/
            AddTaxiDriverController addTaxiDriverController = loader.getController();

            /* Disabilitiamo il bottone ok secondo il seguente criterio */
            dialog.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(addTaxiDriverController.invalidInputProperty());

            /* Gestiamo gli Errori */
            dialog.getDialogPane().lookupButton(ButtonType.APPLY).addEventFilter(ActionEvent.ACTION,
                    event->{
                        if (!addTaxiDriverController.validateDate()){
                            /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Età errata", ButtonType.OK);
                            alert.setHeaderText("L'utente non può essere un minore");
                            alert.setContentText("Hai inserito un utente con età inferiore ai 18 anni");

                            Optional<ButtonType> result = alert.showAndWait();
                            System.out.println(result);

                            event.consume();
                        }else if (!addTaxiDriverController.validateEmail()){
                            /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Email errata", ButtonType.OK);
                            alert.setHeaderText("Hai inserito una email utente errata");
                            alert.setContentText("Inserisci una email valida");

                            Optional<ButtonType> result = alert.showAndWait();
                            System.out.println(result);

                            event.consume();
                        }else if (!addTaxiDriverController.validatePassword()){
                            /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Password errata", ButtonType.OK);
                            alert.setHeaderText("Hai inserito una password utente errata");
                            alert.setContentText("Inserisci una password con lunghezza maggiore di 3 e minore di 15");

                            Optional<ButtonType> result = alert.showAndWait();
                            System.out.println(result);

                            event.consume();
                        }else if (!addTaxiDriverController.validateFiscalCode()){
                            /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Codice Fiscale errato", ButtonType.OK);
                            alert.setHeaderText("Hai inserito un codice fiscale errato");
                            alert.setContentText("Inserisci un codice fiscale con lunghezza pari a 16");

                            Optional<ButtonType> result = alert.showAndWait();
                            System.out.println(result);


                            event.consume();
                        }else if (addTaxiDriverController.existYet()){
                            /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Già esistente", ButtonType.OK);
                            alert.setHeaderText("Utente o un auto esistente");
                            alert.setContentText("Hai inserito un utente o auto già inserito");

                            Optional<ButtonType> result = alert.showAndWait();
                            System.out.println(result);

                            event.consume();
                        }
                    });

            /* Gestiamo il controller mostrandolo e aspettando che l'utente vi interagisca */
            Optional<ButtonType> result = dialog.showAndWait();

            /* Gestiamo il caso in cui l'utente abbia premuto APPLICA */
            if (result.isPresent() && result.get() == ButtonType.APPLY){
                /* Memorizziamo il Tassista processsando opportunamente i dati presenti nel dialog */
                TaxiDriver newTaxiDriver = addTaxiDriverController.processAddTaxiDriver();

                /* Selezioniamo il tassista appena aggiunto */
                this.tableTaxiDriver.getSelectionModel().select(newTaxiDriver);
            }
        });

        //==================================================
        //                 Congeda Tassista
        //==================================================

        /* Aggiungiamo un'azione quando viene cliccato congeda tassista */
        firesTaxiDriver.setOnAction(actionEvent -> {

            /* creiamo un nuovo dialog da visualizzare */
            Dialog<ButtonType> dialog = new Dialog<>();

            /* inizializziamo il proprietario */
            dialog.initOwner(this.vBoxContainer.getScene().getWindow());

            /* Impostiamo il titolo del dialog */
            dialog.setTitle("Congeda un tassista");

            /* Carichiamo il file di iterfaccia per il dialog */
            FXMLLoader loader = new FXMLLoader();

            try{
                /* Carichiamo il file FXML*/
                Parent root = loader.load(new FileInputStream(removeTaxiDriverControllerFile));
                dialog.getDialogPane().setContent(root);
            }catch (IOException e){
                /* In caso si presente l'errore lo stampiamo a terminale */
                System.out.println("Errore di caricamento dialog");
                e.printStackTrace();
            }

            /* Aggiungiamo il bottone OK e CANCEL al dialogPane */
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

            /* Gestiamo il controller mostrandolo e aspettando che l'utente vi interagisca */
            Optional<ButtonType> result = dialog.showAndWait();

            /* Inizializziamo in controller corrente */
            RemoveTaxiDriverController removeTaxiDriverController = loader.getController();

            /* Gestiamo il caso in cui l'utente abbia premuto APPLICA */
            if (result.isPresent() && result.get() == ButtonType.APPLY){
                /* Effettua la rimozione dalla lista dei tassisti del tassista selezionato nel dialog */
                removeTaxiDriverController.processFireTaxiDriver();

                /* Seleziona il primo elemento nella TableView dei tassisti*/
                this.tableTaxiDriver.getSelectionModel().selectFirst();
            }
        });

        //==================================================
        //                 Aggiungi Parcheggio
        //==================================================

        /* Aggiungiamo un'azione quando viene cliccato aggiungi parcheggio */
        addParking.setOnAction(actionEvent -> {

            /* creiamo un nuovo dialog da visualizzare */
            Dialog<ButtonType> dialog = new Dialog<>();

            /* inizializziamo il proprietario */
            dialog.initOwner(this.vBoxContainer.getScene().getWindow());

            /* Impostiamo il titolo del dialog */
            dialog.setTitle("Aggiungi un Parcheggio");

            FXMLLoader loader = new FXMLLoader();

            try{
                /* Carichiamo il file FXML */
                Parent root = loader.load(new FileInputStream(addParkingControllerFile));
                dialog.getDialogPane().setContent(root);
            }catch (IOException e){
                /* In caso di errore stampiamo a terminale */
                System.out.println("Errore di caricamento dialog");
                e.printStackTrace();
            }

            /* Aggiungiamo il bottone APPLICA e CANCEL al dialogPane */
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

            /* Inizializziamo il controller corrente */
            AddParkingController addParkingController = loader.getController();

            /* Disabilitiamo il tasto applica secondo la seguente espressione booleana osservabile */
            dialog.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(addParkingController.invalidInputProperty());

            /* Gestiamo gli Errori */
            dialog.getDialogPane().lookupButton(ButtonType.APPLY).addEventFilter(ActionEvent.ACTION,
                    event->{
                        if (addParkingController.existYet()){
                            /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Già esistente", ButtonType.OK);
                            alert.setHeaderText("Parcheggio esistente");
                            alert.setContentText("Hai inserito un parcheggio già inserito");

                            Optional<ButtonType> result = alert.showAndWait();
                            System.out.println(result);

                            event.consume();
                        }
            });

            /* Gestiamo il controller mostrandolo e aspettando che l'utente vi interagisca */
            Optional<ButtonType> result = dialog.showAndWait();

            /* Gestiamo il caso in cui l'utente abbia premuto APPLICA */
            if (result.isPresent() && result.get() == ButtonType.APPLY){
                /* Memorizziamo il parcheggio processando i dati presenti nel dialog */
                Parking newParking = addParkingController.processAddParking();

                /* Selezioniamo il parcheggio appena aggiunto */
                this.tableParking.getSelectionModel().select(newParking);
            }
        });

        //==================================================
        //                 Rimuovi Parcheggio
        //==================================================

        /* Aggiungiamo un'azione quando viene cliccato rimuovi parcheggio */
        removeParking.setOnAction(actionEvent -> {
            if (this.parkings.size() == 0){
                /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
                Alert alert = new Alert(Alert.AlertType.WARNING, "Impossibile", ButtonType.OK);
                alert.setHeaderText("Aggiungere parcheggi");
                alert.setContentText("Ci sono troppi pochi elementi per rimuovere un parcheggio");

                Optional<ButtonType> result = alert.showAndWait();
                System.out.println(result);
            }else{
                /* creiamo un nuovo dialog da visualizzare */
                Dialog<ButtonType> dialog = new Dialog<>();

                /* inizializziamo il proprietario */
                dialog.initOwner(this.vBoxContainer.getScene().getWindow());

                /* Impostiamo il titolo del dialog */
                dialog.setTitle("Rimuovi un Parcheggio");

                /* Carichiamo il file di iterfaccia per il dialog */
                FXMLLoader loader = new FXMLLoader();

                try{
                    /* Carichiamo il file FXML */
                    Parent root = loader.load(new FileInputStream(removeParkingControllerFile));
                    dialog.getDialogPane().setContent(root);
                }catch (IOException e){
                    /* In caso di errore lo stampiamo a terminale */
                    System.out.println("Errore di caricamento dialog");
                    e.printStackTrace();
                }

                /* Aggiungiamo il bottone OK e CANCEL al dialogPane */
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

                /* Gestiamo il controller mostrandolo e aspettando che l'utente vi interagisca */
                Optional<ButtonType> result = dialog.showAndWait();

                /* Carichiamo il controller corrente */
                RemoveParkingController removeParkingController = loader.getController();

                /* Gestiamo il caso in cui l'utente abbia premuto APPLICA */
                if (result.isPresent() && result.get() == ButtonType.APPLY){
                    /* Rimuoviamo il parcheggio selezionato nel dialog */
                    removeParkingController.processRemoveParking();

                    /* Selezioniamo il primo elemento della lista dei parcheggi */
                    this.tableParking.getSelectionModel().selectFirst();
                }
            }
        });

        //==================================================
        //                 Aggiungi Postazione
        //==================================================

        /* Aggiungiamo un'azione quando viene cliccato aggiungi postazione */
        addWaitingStation.setOnAction(actionEvent -> {
            /* Creiamo un nuovo dialog da visualizzare */
            Dialog<ButtonType> dialog = new Dialog<>();

            /* Inizializziamo il proprietario */
            dialog.initOwner(this.vBoxContainer.getScene().getWindow());

            /* Impostiamo il titolo del dialog */
            dialog.setTitle("Aggiungi un Postazione");

            FXMLLoader loader = new FXMLLoader();

            try{
                /* Carichiamo il file FXML */
                Parent root = loader.load(new FileInputStream(addWaitingStationControllerFile));
                dialog.getDialogPane().setContent(root);
            }catch (IOException e){
                /* In caso di errore, lo stampiamo a terminale */
                System.out.println("Errore di caricamento dialog");
                e.printStackTrace();
            }

            /* Aggiungiamo il bottone APPLICA e CANCEL al dialogPane */
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

            /* Carichiamo il controller corrente */
            AddWaitingStationController addWaitingStationController = loader.getController();

            /* Disabilitiamo l'utilizzo del tasto applica secondo la seguente espresssione boolean osservabile */
            dialog.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(addWaitingStationController.invalidInputProperty());

            /* Gestiamo gli Errori */
            dialog.getDialogPane().lookupButton(ButtonType.APPLY).addEventFilter(ActionEvent.ACTION,
                    event->{
                        if (addWaitingStationController.existYet()){
                            /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Già esistente", ButtonType.OK);
                            alert.setHeaderText("Postazione esistente");
                            alert.setContentText("Hai inserito una postazione già inserito");

                            Optional<ButtonType> result = alert.showAndWait();
                            System.out.println(result);

                            event.consume();
                        }
                    });

            /* Gestiamo il controller mostrandolo e aspettando che l'utente vi interagisca */
            Optional<ButtonType> result = dialog.showAndWait();

            /* Gestiamo il caso in cui l'utente abbia premuto APPLICA */
            if (result.isPresent() && result.get() == ButtonType.APPLY){
                /* Inizializziamo una postazione processando opportunamente i dati presenti nel dialog */
                WaitingStation newWaiting = addWaitingStationController.processAddWaitingStation();

                /* Selezioniamo la postazione appena aggiunta */
                this.tableWaitingStation.getSelectionModel().select(newWaiting);
            }
        });

        //==================================================
        //                 Rimuovi Postazione
        //==================================================

        /* Aggiungiamo un'azione quando viene cliccato rimuovi parcheggio */
        removeWaitingStation.setOnAction(actionEvent -> {
            if (this.waitingStations.size() == 0){
                /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
                Alert alert = new Alert(Alert.AlertType.WARNING, "Impossibile", ButtonType.OK);
                alert.setHeaderText("Aggiungere postazioni");
                alert.setContentText("Ci sono troppi pochi elementi per rimuovere una postazione");

                Optional<ButtonType> result = alert.showAndWait();
                System.out.println(result);
            }else{
                /* creiamo un nuovo dialog da visualizzare */
                Dialog<ButtonType> dialog = new Dialog<>();

                /* inizializziamo il proprietario */
                dialog.initOwner(this.vBoxContainer.getScene().getWindow());

                /* Impostiamo il titolo del dialog */
                dialog.setTitle("Rimuovi una Postazione");

                /* Carichiamo il file di iterfaccia per il dialog */
                FXMLLoader loader = new FXMLLoader();

                try{
                    /* Carichiamo il file FXML */
                    Parent root = loader.load(new FileInputStream(removeWaitingStationControllerFile));
                    dialog.getDialogPane().setContent(root);
                }catch (IOException e){
                    /* In caso di errore stampiamo a terminale */
                    System.out.println("Errore di caricamento dialog");
                    e.printStackTrace();
                }

                /* Aggiungiamo il bottone APPLICA e CANCEL al dialogPane */
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

                /* Gestiamo il controller mostrandolo e aspettando che l'utente vi interagisca */
                Optional<ButtonType> result = dialog.showAndWait();

                /* Carichiamo il controller corrente*/
                RemoveWaitingStationController removeWaitingStationController = loader.getController();

                /* Gestiamo il caso in cui l'utente abbia premuto APPLICA */
                if (result.isPresent() && result.get() == ButtonType.APPLY){
                    /* Rimuoviamo la postazione scelta nel dialog */
                    removeWaitingStationController.processRemoveWaitingStation();

                    /* Selezioniamo il primo elemento della lista delle postazioni */
                    this.tableWaitingStation.getSelectionModel().selectFirst();
                }
            }
        });

        //==================================================
        //                 Aggiungi Collegamento
        //==================================================

        /* Aggiungiamo un'azione quando viene cliccato aggiungi postazione */
        addConnection.setOnAction(actionEvent -> {
            if (taxiFinderData.getGraph().getVertexes().size() <= 1){
                /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
                Alert alert = new Alert(Alert.AlertType.WARNING, "Impossibile", ButtonType.OK);
                alert.setHeaderText("Aggiungere postazioni");
                alert.setContentText("Ci sono troppi pochi elementi per inserire un collegamento, per favore crea " +
                        "un nuovo parcheggio/postazione");

                Optional<ButtonType> result = alert.showAndWait();
                System.out.println(result);
            }else{
                /* creiamo un nuovo dialog da visualizzare */
                Dialog<ButtonType> dialog = new Dialog<>();

                /* inizializziamo il proprietario */
                dialog.initOwner(this.vBoxContainer.getScene().getWindow());

                /* Impostiamo il titolo del dialog */
                dialog.setTitle("Aggiungi Collegamento");

                /* Carichiamo il file di iterfaccia per il dialog */
                FXMLLoader loader = new FXMLLoader();

                try{
                    /* Carichiamo il file FXML */
                    Parent root = loader.load(new FileInputStream(addConnectionControllerFile));
                    dialog.getDialogPane().setContent(root);
                }catch (IOException e){
                    /* In caso di errore stampiamo a terminale */
                    System.out.println("Errore di caricamento dialog");
                    e.printStackTrace();
                }

                /* Aggiungiamo il bottone OK e CANCEL al dialogPane */
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

                /* Inizializziamo il controller corrente*/
                AddEdgeController addEdgeController = loader.getController();

                /* Gestiamo gli Errori */
                dialog.getDialogPane().lookupButton(ButtonType.APPLY).addEventFilter(ActionEvent.ACTION,
                        event->{
                            if (addEdgeController.validateFields()){
                                /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
                                Alert alert = new Alert(Alert.AlertType.WARNING, "Già esistente", ButtonType.OK);
                                alert.setHeaderText("Collegamento esistente");
                                alert.setContentText("Hai inserito un Collegamento già inserito");

                                Optional<ButtonType> result = alert.showAndWait();
                                System.out.println(result);

                                event.consume();
                            }
                        });

                /* Gestiamo il controller mostrandolo e aspettando che l'utente vi interagisca */
                Optional<ButtonType> result = dialog.showAndWait();

                /* Gestiamo il caso in cui l'utente abbia premuto OK */
                if (result.isPresent() && result.get() == ButtonType.APPLY){
                    /* Aggiungiamo il collegamento processando i dati presenti nel dialog */
                    addEdgeController.processAddEdge();
                }
            }
        });

        //==================================================
        //                 Rimuovi Collegamento
        //==================================================

        /* Aggiungiamo un'azione quando viene cliccato rimuovi parcheggio */
        removeConnection.setOnAction(actionEvent -> {
            if (taxiFinderData.getGraph().getEdges().isEmpty()){
                /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
                Alert alert = new Alert(Alert.AlertType.WARNING, "Impossibile", ButtonType.OK);
                alert.setHeaderText("Aggiungere collegamenti");
                alert.setContentText("Ci sono troppi pochi elementi per rimuovere un collegamento");

                Optional<ButtonType> result = alert.showAndWait();
                System.out.println(result);
            }else{
                /* creiamo un nuovo dialog da visualizzare */
                Dialog<ButtonType> dialog = new Dialog<>();

                /* inizializziamo il proprietario */
                dialog.initOwner(this.vBoxContainer.getScene().getWindow());

                /* Impostiamo il titolo del dialog */
                dialog.setTitle("Rimuovi un Collegamento");

                /* Carichiamo il file di iterfaccia per il dialog */
                FXMLLoader loader = new FXMLLoader();

                try{
                    /* Carichiamo il file FXML */
                    Parent root = loader.load(new FileInputStream(removeConnectionControllerFile));
                    dialog.getDialogPane().setContent(root);
                }catch (IOException e){
                    /* In caso di errore lo stampiamo a terminale */
                    System.out.println("Errore di caricamento dialog");
                    e.printStackTrace();
                }

                /* Aggiungiamo il bottone OK e CANCEL al dialogPane */
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

                /* Gestiamo il controller mostrandolo e aspettando che l'utente vi interagisca */
                Optional<ButtonType> result = dialog.showAndWait();

                /* Inizializziamo il controller corrente */
                RemoveEdgeController removeEdgeController = loader.getController();

                /* Gestiamo il caso in cui l'utente abbia premuto APPLICA */
                if (result.isPresent() && result.get() == ButtonType.APPLY){
                    /* Rimuoviamo il collegamento dalla lista collegamenti */
                    removeEdgeController.processRemoveEdge();
                }
            }
        });

        /* Aggiungiamo un'azione quando Logout viene premuto */
        exitLabel.setOnMouseClicked(actionEvent -> UtilityController.navigateTo(mainControllerFile, "Taxi Finder",
                                  "Errore reperimento interfaccia", exitLabel));

        /* Ritorna il MenuBar */
        return menuBar;
    }

    /**
     * Questo metodo ha lo scopo di ritornare una ToolBar dell'admin
     * @return Una ToolBar composta dai vari sottomenu attinenti alla figura del gestore
     * @see ToolBar
     */
    private ToolBar compositeToolBar(){
        /* Inizializziamo la toolBar */
        ToolBar toolBar = new ToolBar();

        /* Inizializziamo i sottomenu presenti nella ToolBar con dei Button */

        /* Visualizza tassisti */
        Button visualizeTaxiDriver = new Button("Visualizza Tassisti");
        /* Visualizza parcheggi */
        Button visualizeParking= new Button("Visualizza Parcheggi");
        /* Visualizza postazioni */
        Button visualizeWaitingStations = new Button("Visualizza Postazioni");

        /* Aggiungiamo i Button alla ToolBar */
        toolBar.getItems().addAll(visualizeTaxiDriver, visualizeParking, visualizeWaitingStations);

        /* Aggiungiamo un'azione quando visualizza tassisti viene premuto */
        visualizeTaxiDriver.setOnAction(actionEvent -> UtilityController.changeVisibility(this.tableTaxiDriver, this.tableParking, this.tableWaitingStation));

        /* Aggiungiamo un'azione quando visualizza parcheggi viene premuto */
        visualizeParking.setOnAction(actionEvent -> {

            /* Se è la prima volta che entriamo nella sezione "parcheggi" */
            if (this.isFirstEntryParking){
                /* Componiamo la TableView dei parcheggi */
                compositeParkingTableView();

                /* Aggiungiamo l'elemento creato al container associato alla view */
                vBoxCenterContainer.getChildren().add(tableParking);

                /* Impostiamo isFirstEntryParking a false */
                isFirstEntryParking = false;
            }

            /* Cambiamo la visibilità dei diversi nodi */
            UtilityController.changeVisibility(this.tableParking, this.tableTaxiDriver, this.tableWaitingStation);
        });

        /* Aggiungiamo un'azione quando visualizza postazioni viene premuto */
        visualizeWaitingStations.setOnAction(actionEvent -> {

            /* Se è la prima volta che entriamo nella sezione "parcheggi" */
            if (this.isFirstEntryWaiting){
                /* Componiamo la TableView delle postazioni */
                compositeWaitingStationTableView();

                /* Aggiungiamo l'elemento creato al container associato alla view */
                vBoxCenterContainer.getChildren().add(tableWaitingStation);

                /* Impostiamo isFirstEntryWaiting a false */
                isFirstEntryWaiting = false;
            }

            /* Cambiamo la visibilità dei diversi nodi */
            UtilityController.changeVisibility(this.tableWaitingStation, this.tableTaxiDriver, this.tableParking);
        });

        /* Ritorniamo la ToolBar*/
        return toolBar;
    }

    //==================================================
    //               TABLEVIEW - TASSISTI
    //==================================================

    /**
     * Metodo utile a comporre la TableView dei tassisti
     */
    private void compositeTaxiDriverTableView(){

        /* Creiamo le colonne della tableView */

        /* Codice fiscale */
        this.fiscalCodeColumn = new TableColumn<>("Codice Fiscale");
        /* Nome */
        this.firstNameColumn = new TableColumn<>("Nome");
        /* Cognome */
        this.lastNameColumn = new TableColumn<>("Cognome");
        /* Data di nascita */
        this.dateOfBirthColumn = new TableColumn<>("Data di nascita");
        /* Genere */
        this.genderColumn = new TableColumn<>("Genere");
        /* Email */
        this.emailColumn = new TableColumn<>("Email");
        /* Targa Taxi */
        this.taxiColumn = new TableColumn<>("Targa Taxi");
        /* Numero licensa */
        this.licenseNumberColumn = new TableColumn<>("Nr. Licensa");
        /* Stato Lavorativo */
        this.stateColumn = new TableColumn<>("Stato");

        /* Inizializziamo la TableView */
        this.tableTaxiDriver = new TableView<>();

        /* Aggiungiamo le colonne alla tabella */

        /* Codice fiscale */
        this.tableTaxiDriver.getColumns().add(fiscalCodeColumn);
        /* Nome */
        this.tableTaxiDriver.getColumns().add(firstNameColumn);
        /* Cognome */
        this.tableTaxiDriver.getColumns().add(lastNameColumn);
        /* Data di nascita */
        this.tableTaxiDriver.getColumns().add(dateOfBirthColumn);
        /* Genere */
        this.tableTaxiDriver.getColumns().add(genderColumn);
        /* Email */
        this.tableTaxiDriver.getColumns().add(emailColumn);
        /* Targa Taxi */
        this.tableTaxiDriver.getColumns().add(taxiColumn);
        /* Numero licensa */
        this.tableTaxiDriver.getColumns().add(licenseNumberColumn);
        /* Stato Lavorativo */
        this.tableTaxiDriver.getColumns().add(stateColumn);

        /* Impostiamo la grandezza massima della tabella per ogni colonna */
        this.tableTaxiDriver.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.fiscalCodeColumn.setMaxWidth(Integer.MAX_VALUE * 11.11);      // 11.11%
        this.firstNameColumn.setMaxWidth(Integer.MAX_VALUE * 11.11);       // 11.11%
        this.lastNameColumn.setMaxWidth(Integer.MAX_VALUE * 11.11);        // 11.11%
        this.dateOfBirthColumn.setMaxWidth(Integer.MAX_VALUE * 11.11);     // 11.11%
        this.genderColumn.setMaxWidth(Integer.MAX_VALUE * 11.11);          // 11.11%
        this.emailColumn.setMaxWidth(Integer.MAX_VALUE * 11.11);           // 11.11%
        this.taxiColumn.setMaxWidth(Integer.MAX_VALUE * 11.11);            // 11.11%
        this.licenseNumberColumn.setMaxWidth(Integer.MAX_VALUE * 11.11);   // 11.11%
        this.stateColumn.setMaxWidth(Integer.MAX_VALUE * 11.11);           // 11.11%

        /* Impediamo che le tabelle possano essere riordinate dall'utente */
        this.tableTaxiDriver.skinProperty().addListener((observableValue, oldWidth, newWidth) ->{
            final TableHeaderRow header = (TableHeaderRow) tableTaxiDriver.lookup("TableHeaderRow");

            header.reorderingProperty().addListener((obs, oldValue, newValue) -> header.setReordering(false));
        });

        /* Rendiamo la tableView non editabile */
        this.tableTaxiDriver.setEditable(false);

        /* Impostiamo le proprietà delle colonne */

        /* Codice fiscale */
        setFiscalCodeColumnProperty();
        /* Nome */
        setFirstNameColumnProperty();
        /* Cognome */
        setLastNameColumnProperty();
        /* Data di nascita */
        setDateOfBirthColumnProperty();
        /* Genere */
        setGenderColumnProperty();
        /* Email */
        setEmailColumnProperty();
        /* Targa Taxi */
        setTaxiColumnProperty();
        /* Numero licensa */
        setLicenseNumberColumnProperty();
        /* Stato Lavorativo */
        setStateColumnProperty();

        /* Impostiamo una larghezza base */
        this.tableTaxiDriver.setPrefWidth(2048);

        tableTaxiDriver.setRowFactory(taxiDriverTableView -> {
            final TableRow<TaxiDriver> row = new TableRow<>();
            /* Impostiamo il contextmenu su di una row, ma usiamo il binding solo se non è vuota */
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu)null)
                            .otherwise(contextMenu)
            );
            return row ;
        });

        /* Impostiamo gli item da visualizzare */
        this.tableTaxiDriver.setItems(this.drivers);
    }

    //==================================================
    //              TABLEVIEW - PARCHEGGI
    //==================================================

    /**
     * Metodo utile a comporre la tableView dei Parcheggi
     */
    private void compositeParkingTableView(){
        /* Creiamo le colonne della tableView */

        /* Latitudine */
        this.latitudeColumn = new TableColumn<>("Latitudine");
        /* Longitudine */
        this.longitudeColumn = new TableColumn<>("Longitude");
        /* Nome Strada */
        this.streetNameColumn = new TableColumn<>("Strada");
        /* Civico */
        this.streetNumberColumn = new TableColumn<>("Civico");
        /* Nome Parcheggio */
        this.stationNameColumn = new TableColumn<>("Parcheggio");
        /* Capacità */
        this.capacityColumn = new TableColumn<>("Capacità");

        /* Inizializziamo la TableView */
        this.tableParking = new TableView<>();

        /* Aggiungiamo le colonne alla tabella */

        /* Latitudine */
        this.tableParking.getColumns().add(latitudeColumn);
        /* Longitudine */
        this.tableParking.getColumns().add(longitudeColumn);
        /* Nome Strada */
        this.tableParking.getColumns().add(streetNameColumn);
        /* Civico */
        this.tableParking.getColumns().add(streetNumberColumn);
        /* Nome Parcheggio */
        this.tableParking.getColumns().add(stationNameColumn);
        /* Capacità */
        this.tableParking.getColumns().add(capacityColumn);

        /* Impostiamo la grandezza massima della tabella per ogni colonna */
        this.tableParking.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.latitudeColumn.setMaxWidth(Integer.MAX_VALUE * 16.6D);          // 16,6%
        this.longitudeColumn.setMaxWidth(Integer.MAX_VALUE * 16.6D);         // 16,6%
        this.streetNameColumn.setMaxWidth(Integer.MAX_VALUE * 16.6D);        // 16,6%
        this.streetNumberColumn.setMaxWidth(Integer.MAX_VALUE * 16.6D);      // 16,6%
        this.stationNameColumn.setMaxWidth(Integer.MAX_VALUE * 16.6D);       // 16,6%
        this.capacityColumn.setMaxWidth(Integer.MAX_VALUE * 16.6D);          // 16,6%

        /* Impediamo che le tabelle possano essere riordinate dall'utente */
        this.tableParking.skinProperty().addListener((observableValue, oldWidth, newWidth) ->{
            final TableHeaderRow header = (TableHeaderRow) tableParking.lookup("TableHeaderRow");

            header.reorderingProperty().addListener((obs, oldValue, newValue) -> header.setReordering(false));
        });

        /* Rendiamo la tableView non editabile */
        this.tableParking.setEditable(false);

        /* Impostiamo le proprietà delle colonne */

        /* Latitudine */
        setLatitudeColumnProperty();
        /* Longitudine */
        setLongitudeColumnProperty();
        /* Nome Strada */
        setStreetNameColumnProperty();
        /* Civico */
        setStreetNumberColumnProperty();
        /* Nome Parcheggio */
        setStationNameColumnProperty();
        /* Capacità */
        setCapacityColumnProperty();

        /* Impostiamo una larghezza base */
        this.tableParking.setPrefWidth(2048);

        tableParking.setRowFactory(taxiDriverTableView -> {
            final TableRow<Parking> row = new TableRow<>();
            // Impostiamo il contextmenu su di una row, ma usiamo il binding solo se non è vuota
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu)null)
                            .otherwise(roadContextMenu)
            );
            return row ;
        });

        /* Impostiamo gli item da visualizzare */
        this.tableParking.setItems(this.parkings);
    }

    //==================================================
    //              TABLEVIEW - POSTAZIONI
    //==================================================

    /**
     * Metodo utile a comporre TableView delle postazioni
     */
    private void compositeWaitingStationTableView(){
        /* Creiamo le colonne della tableView */

        /* Latitudine */
        this.latitudeWaitingStationColumn = new TableColumn<>("Latitudine");
        /* Longitudine */
        this.longitudeWaitingStationColumn = new TableColumn<>("Longitudine");
        /* Nome Strada */
        this.streetNameWaitingStationColumn = new TableColumn<>("Strada");
        /* Civico */
        this.streetNumberWaitingStationColumn = new TableColumn<>("Civico");
        /* Nome Postazione */
        this.stationNameWaitingStationColumn = new TableColumn<>("Postazione");

        /* Inizializziamo la TableView */
        this.tableWaitingStation = new TableView<>();

        /* Aggiungiamo le colonne alla tabella */

        /* Latitudine */
        this.tableWaitingStation.getColumns().add(latitudeWaitingStationColumn);
        /* Longitudine */
        this.tableWaitingStation.getColumns().add(longitudeWaitingStationColumn);
        /* Nome Strada */
        this.tableWaitingStation.getColumns().add(streetNameWaitingStationColumn);
        /* Civico */
        this.tableWaitingStation.getColumns().add(streetNumberWaitingStationColumn);
        /* Nome Postazione */
        this.tableWaitingStation.getColumns().add(stationNameWaitingStationColumn);

        /* Impostiamo la grandezza massima della tabella per ogni colonna */
        this.tableWaitingStation.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.latitudeWaitingStationColumn.setMaxWidth(Integer.MAX_VALUE * 20D); // 20%
        this.longitudeWaitingStationColumn.setMaxWidth(Integer.MAX_VALUE * 20D); // 20%
        this.streetNameWaitingStationColumn.setMaxWidth(Integer.MAX_VALUE * 20D); // 20%
        this.streetNumberWaitingStationColumn.setMaxWidth(Integer.MAX_VALUE * 20D); // 20%
        this.stationNameWaitingStationColumn.setMaxWidth(Integer.MAX_VALUE * 20D); // 20%

        /* Impediamo che le tabelle possano essere riordinate dall'utente */
        this.tableWaitingStation.skinProperty().addListener((observableValue, oldWidth, newWidth) ->{
            final TableHeaderRow header = (TableHeaderRow) tableWaitingStation.lookup("TableHeaderRow");

            header.reorderingProperty().addListener((obs, oldValue, newValue) -> header.setReordering(false));
        });

        /* Rendiamo la tableView non editabile */
        this.tableWaitingStation.setEditable(false);

        /* Impostiamo le proprietà delle colonne */

        /* Latitudine */
        setLatitudeWaitingStationColumnProperty();
        /* Longitudine */
        setLongitudeWaitingStationColumnProperty();
        /* Nome Strada */
        setStreetNameWaitingStationColumnProperty();
        /* Civico */
        setStreetNumberWaitingStationColumnProperty();
        /* Nome Postazione */
        setStationNameWaitingStationColumnProperty();

        /* Impostiamo una larghezza base */
        this.tableWaitingStation.setPrefWidth(2048);

        /* Impostiamo altezza base */
        this.tableWaitingStation.setPrefHeight(3096);

        tableWaitingStation.setRowFactory(taxiDriverTableView -> {
            final TableRow<WaitingStation> row = new TableRow<>();
            // Impostiamo il contextmenu su di una row, ma usiamo il binding solo se non è vuota
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu)null)
                            .otherwise(roadWaitingContextMenu)
            );
            return row ;
        });

        /* Impostiamo gli item da visualizzare */
        this.tableWaitingStation.setItems(this.waitingStations);
    }

    //==================================================
    //            Metodi Propietà Colonne
    //==================================================

    /**
     * Metodo utile ad impostare le proprietà della colonna del codice fiscale dei tassisti
     * */
    private void setFiscalCodeColumnProperty(){
        this.fiscalCodeColumn.setCellValueFactory(taxiDriverStringCellDataFeatures -> new SimpleStringProperty(
                taxiDriverStringCellDataFeatures.getValue().getFiscalCode()));

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.fiscalCodeColumn.setCellFactory(taxiStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String fiscalCode, boolean empty){
                super.updateItem(fiscalCode, empty);
                if(empty || fiscalCode == null){
                    setText(null);
                } else {
                    setText(fiscalCode);
                }
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna del nome dei tassisti
     * */
    private void setFirstNameColumnProperty(){
        this.firstNameColumn.setCellValueFactory(taxiDriverStringCellDataFeatures -> new SimpleStringProperty(
                taxiDriverStringCellDataFeatures.getValue().getFirstName()));

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.firstNameColumn.setCellFactory(taxiStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String firstName, boolean empty){
                super.updateItem(firstName, empty);
                if(empty || firstName == null){
                    setText(null);
                } else {
                    setText(firstName);
                }
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna del cognome dei tassisti
     * */
    private void setLastNameColumnProperty(){
        this.lastNameColumn.setCellValueFactory(taxiDriverStringCellDataFeatures -> new SimpleStringProperty(
                taxiDriverStringCellDataFeatures.getValue().getLastName()));

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.lastNameColumn.setCellFactory(taxiStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String lastName, boolean empty){
                super.updateItem(lastName, empty);
                if(empty || lastName == null){
                    setText(null);
                } else {
                    setText(lastName);
                }
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna della data di nascita dei tassisti
     * */
    private void setDateOfBirthColumnProperty(){
        this.dateOfBirthColumn.setCellValueFactory(taxiDriverStringCellDataFeatures -> new SimpleStringProperty(
                taxiDriverStringCellDataFeatures.getValue().getDateOfBirth().format(taxiFinderData.getDateTimeFormatter())));

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.dateOfBirthColumn.setCellFactory(taxiStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String dateOfBirth, boolean empty){
                super.updateItem(dateOfBirth, empty);
                if(empty || dateOfBirth == null){
                    setText(null);
                } else {
                    setText(dateOfBirth);
                }
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna del genere dei tassisti
     * */
    private void setGenderColumnProperty(){
        this.genderColumn.setCellValueFactory(taxiDriverStringCellDataFeatures -> new SimpleStringProperty(
                taxiDriverStringCellDataFeatures.getValue().getGenderType().getTranslation()));

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.genderColumn.setCellFactory(taxiStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String genderType, boolean empty){
                super.updateItem(genderType, empty);
                if(empty || genderType == null){
                    setText(null);
                } else {
                    setText(genderType);
                }
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna dell'email dei tassisti
     * */
    private void setEmailColumnProperty(){
        this.emailColumn.setCellValueFactory(taxiDriverStringCellDataFeatures -> new SimpleStringProperty(
                taxiDriverStringCellDataFeatures.getValue().getEmail()));

        // Personalizziamo la cella e quello che vogliamo vedere
        this.emailColumn.setCellFactory(taxiStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String email, boolean empty){
                super.updateItem(email, empty);
                if(empty || email == null){
                    setText(null);
                } else {
                    setText(email);
                }
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna della targa del taxi dei tassisti
     * */
    private void setTaxiColumnProperty(){
        this.taxiColumn.setCellValueFactory(taxiDriverStringCellDataFeatures -> new SimpleStringProperty(
                taxiDriverStringCellDataFeatures.getValue().getTaxi().getLicensePlate()));

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.taxiColumn.setCellFactory(taxiStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String licensePlate, boolean empty){
                super.updateItem(licensePlate, empty);
                if(empty || licensePlate == null){
                    setText(null);
                } else {
                    setText(licensePlate);
                }
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna del numero di licenza dei tassisti
     * */
    private void setLicenseNumberColumnProperty(){
        this.licenseNumberColumn.setCellValueFactory(taxiDriverStringCellDataFeatures -> new SimpleStringProperty(
                taxiDriverStringCellDataFeatures.getValue().getLicenseNumber()));

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.licenseNumberColumn.setCellFactory(taxiStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String licenseNumber, boolean empty){
                super.updateItem(licenseNumber, empty);
                if(empty || licenseNumber == null){
                    setText(null);
                } else {
                    setText(licenseNumber);
                }
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna dello stato lavorativo dei tassisti
     * */
    private void setStateColumnProperty(){
        this.stateColumn.setCellValueFactory(taxiDriverStringCellDataFeatures -> new SimpleStringProperty(
                taxiDriverStringCellDataFeatures.getValue().getState().getTranslation()));

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.stateColumn.setCellFactory(taxiStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String state, boolean empty){
                super.updateItem(state, empty);
                if(empty || state == null){
                    setText(null);
                } else {
                    setText(state);
                }
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna della latitudine del parcheggio
     * */
    private void setLatitudeColumnProperty(){
        this.latitudeColumn.setCellValueFactory(streetStringCellDataFeatures -> new SimpleStringProperty(
                String.valueOf(streetStringCellDataFeatures.getValue().getCoordinates().getLatitude())));

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.latitudeColumn.setCellFactory(streetStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String latitude, boolean empty){
                super.updateItem(latitude, empty);
                if(empty || latitude == null){
                    setText(null);
                } else {
                    setText(latitude);
                }
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna della longitudine del parcheggio
     * */
    private void setLongitudeColumnProperty(){
        this.longitudeColumn.setCellValueFactory(streetStringCellDataFeatures -> new SimpleStringProperty(
                String.valueOf(streetStringCellDataFeatures.getValue().getCoordinates().getLongitude())));

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.longitudeColumn.setCellFactory(streetStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String longitude, boolean empty){
                super.updateItem(longitude, empty);
                if(empty || longitude == null){
                    setText(null);
                } else {
                    setText(longitude);
                }
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna del nome della strada in cui è locato il parcheggio
     * */
    private void setStreetNameColumnProperty(){
        this.streetNameColumn.setCellValueFactory(streetStringCellDataFeatures -> new SimpleStringProperty(
                streetStringCellDataFeatures.getValue().getStreetName()));

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.streetNameColumn.setCellFactory(streetStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String streetName, boolean empty){
                super.updateItem(streetName, empty);
                if(empty || streetName == null){
                    setText(null);
                } else {
                    setText(streetName);
                }
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna del civico in cui è locato il parcheggio
     * */
    private void setStreetNumberColumnProperty(){
        this.streetNumberColumn.setCellValueFactory(streetStringCellDataFeatures -> new SimpleStringProperty(
                streetStringCellDataFeatures.getValue().getStreetNumber()));

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.streetNumberColumn.setCellFactory(streetStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String streetNumber, boolean empty){
                super.updateItem(streetNumber, empty);
                if(empty || streetNumber == null){
                    setText(null);
                } else {
                    setText(streetNumber);
                }
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna del nome del parcheggio
     * */
    private void setStationNameColumnProperty(){
        this.stationNameColumn.setCellValueFactory(streetStringCellDataFeatures -> new SimpleStringProperty(
                streetStringCellDataFeatures.getValue().getStationName()));

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.streetNumberColumn.setCellFactory(streetStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String stationName, boolean empty){
                super.updateItem(stationName, empty);
                if(empty || stationName == null){
                    setText(null);
                } else {
                    setText(stationName);
                }
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna della capacità del parcheggio
     * */
    private void setCapacityColumnProperty(){
        this.capacityColumn.setCellValueFactory(streetStringCellDataFeatures -> new SimpleStringProperty(
                String.valueOf(streetStringCellDataFeatures.getValue().getParkingCapacity())
        ));

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.capacityColumn.setCellFactory(streetStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String capacity, boolean empty){
                super.updateItem(capacity, empty);
                if(empty || capacity == null){
                    setText(null);
                } else {
                    setText(capacity);
                }
            }
        });
    }


    /**
     * Metodo utile ad impostare le proprietà della colonna della latitudine della postazione
     * */
    private void setLatitudeWaitingStationColumnProperty(){
        this.latitudeWaitingStationColumn.setCellValueFactory(streetStringCellDataFeatures -> new SimpleStringProperty(
                String.valueOf(streetStringCellDataFeatures.getValue().getCoordinates().getLatitude())));

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.latitudeWaitingStationColumn.setCellFactory(streetStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String latitude, boolean empty){
                super.updateItem(latitude, empty);
                if(empty || latitude == null){
                    setText(null);
                } else {
                    setText(latitude);
                }
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna della longitudine della postazione
     * */
    private void setLongitudeWaitingStationColumnProperty(){
        this.longitudeWaitingStationColumn.setCellValueFactory(streetStringCellDataFeatures -> new SimpleStringProperty(
                String.valueOf(streetStringCellDataFeatures.getValue().getCoordinates().getLongitude())));

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.longitudeWaitingStationColumn.setCellFactory(streetStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String longitude, boolean empty){
                super.updateItem(longitude, empty);
                if(empty || longitude == null){
                    setText(null);
                } else {
                    setText(longitude);
                }
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna del nome della strada in cui è locata la postazione
     * */
    private void setStreetNameWaitingStationColumnProperty(){
        this.streetNameWaitingStationColumn.setCellValueFactory(streetStringCellDataFeatures -> new SimpleStringProperty(
                streetStringCellDataFeatures.getValue().getStreetName()));

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.streetNameWaitingStationColumn.setCellFactory(streetStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String streetName, boolean empty){
                super.updateItem(streetName, empty);
                if(empty || streetName == null){
                    setText(null);
                } else {
                    setText(streetName);
                }
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna del civico in cui è locata la postazione
     * */
    private void setStreetNumberWaitingStationColumnProperty(){
        this.streetNumberWaitingStationColumn.setCellValueFactory(streetStringCellDataFeatures -> new SimpleStringProperty(
                streetStringCellDataFeatures.getValue().getStreetNumber()));

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.streetNumberWaitingStationColumn.setCellFactory(streetStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String streetNumber, boolean empty){
                super.updateItem(streetNumber, empty);
                if(empty || streetNumber == null){
                    setText(null);
                } else {
                    setText(streetNumber);
                }
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna del nome della postazione
     * */
    private void setStationNameWaitingStationColumnProperty(){
        this.stationNameWaitingStationColumn.setCellValueFactory(streetStringCellDataFeatures -> new SimpleStringProperty(
                streetStringCellDataFeatures.getValue().getStationName()));

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.stationNameWaitingStationColumn.setCellFactory(streetStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String stationName, boolean empty){
                super.updateItem(stationName, empty);
                if(empty || stationName == null){
                    setText(null);
                } else {
                    setText(stationName);
                }
            }
        });
    }

    /**
     * Metodo utile a mostrare i collegamenti tra postazioni
     * @param waitingStation Postazioni di cui si vuole mostrare i collegamenti
     * @see WaitingStation
     */
    private void showConnections(WaitingStation waitingStation){
        /* creiamo un nuovo dialog da visualizzare */
        Dialog<ButtonType> dialog = new Dialog<>();

        /* inizializziamo il proprietario */
        dialog.initOwner(this.vBoxContainer.getScene().getWindow());

        /* Impostiamo il titolo del dialog */
        dialog.setTitle(String.format("Postazione %s", waitingStation.getStationName()));

        FXMLLoader loader = new FXMLLoader();

        try{
            /* Carichiamo il FXML */
            Parent root = loader.load(new FileInputStream(showConnectionsControllerFile));
            dialog.getDialogPane().setContent(root);
        }catch (IOException e){
            /* In caso di errore stampiamo a terminale */
            System.out.println("Errore di caricamento dialog");
            e.printStackTrace();
        }

        /* Carichiamo il file controller corrente */
        ShowConnectionsController showConnectionsController = loader.getController();
        showConnectionsController.initData(waitingStation);

        /* Aggiungiamo il bottone OK al dialogPane */
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);

        /* Gestiamo il controller mostrandolo e aspettando che l'utente vi interagisca */
        Optional<ButtonType> result = dialog.showAndWait();
        System.out.println(result);
    }

    /**
     * Metodo utile a mostrare il veicolo dato un determinato tassista
     * @param taxiDriver Tassista di cui si vuole conoscere un veicolo
     * @see TaxiDriver
     */
    private void showVehicle(TaxiDriver taxiDriver){
        /* creiamo un nuovo dialog da visualizzare */
        Dialog<ButtonType> dialog = new Dialog<>();

        /* inizializziamo il proprietario */
        dialog.initOwner(this.vBoxContainer.getScene().getWindow());

        /* Impostiamo il titolo del dialog */
        dialog.setTitle(String.format("Veicolo di %s %s", taxiDriver.getFirstName(), taxiDriver.getLastName()));

        FXMLLoader loader = new FXMLLoader();

        try{
            /* Carichiamo il file FXML */
            Parent root = loader.load(new FileInputStream(showVehicleControllerFile));
            dialog.getDialogPane().setContent(root);
        }catch (IOException e){
            /* In caso di errore stampiamo a terminale */
            System.out.println("Errore di caricamento dialog");
            e.printStackTrace();
        }

        /* Carichiamo il controller corrente*/
        VehicleController vehicleController = loader.getController();
        vehicleController.initData(taxiDriver);

        /* Aggiungiamo il bottone OK al dialogPane */
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);

        /* Gestiamo il controller mostrandolo e aspettando che l'utente vi interagisca */
        Optional<ButtonType> result = dialog.showAndWait();
        System.out.println(result);
    }

    /**
     * Metodo utile a mostrare i veicoli presenti all'interno di un detrminato parcheggio
     * @param parking Parcheggio di cui vogliamo conoscere la lista Taxi locati al suo interno
     * @see Parking
     */
    private void showTaxis(Parking parking){
        /* creiamo un nuovo dialog da visualizzare */
        Dialog<ButtonType> dialog = new Dialog<>();

        /* inizializziamo il proprietario */
        dialog.initOwner(this.vBoxContainer.getScene().getWindow());

        /* Impostiamo il titolo del dialog */
        dialog.setTitle(String.format("Parcheggio di %s", parking.getStationName()));

        FXMLLoader loader = new FXMLLoader();

        try{
            /* Carichiamo il file FXML*/
            Parent root = loader.load(new FileInputStream(showTaxisControllerFile));
            dialog.getDialogPane().setContent(root);
        }catch (IOException e){
            /* In caso di errore lo stampiamo a terminale */
            System.out.println("Errore di caricamento dialog");
            e.printStackTrace();
        }

        /* Carichiamo il controller corrente */
        ShowTaxisController showTaxisController = loader.getController();
        showTaxisController.initData(parking);

        /* Aggiungiamo il bottone OK al dialogPane */
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);

        /* Gestiamo il controller mostrandolo e aspettando che l'utente vi interagisca */
        Optional<ButtonType> result = dialog.showAndWait();
        System.out.println(result);
    }

    /**
     * Metodo utile a mostrare in quale parcheggio sosta un tassista
     * @param taxiDriver Tassista di cui vogliamo conoscere l'ubicazione
     * @see TaxiDriver
     */
    public void showWaiting(TaxiDriver taxiDriver){
        /* creiamo un nuovo dialog da visualizzare */
        Dialog<ButtonType> dialog = new Dialog<>();

        /* inizializziamo il proprietario */
        dialog.initOwner(this.vBoxContainer.getScene().getWindow());

        /* Impostiamo il titolo del dialog */
        dialog.setTitle(String.format("È in sosta nel seguente parcheggio"));

        /* Carichiamo il file di iterfaccia per il dialog */
        FXMLLoader loader = new FXMLLoader();

        try{
            /* Carichiamo il file FXML */
            Parent root = loader.load(new FileInputStream(showWaitingParkingControllerFile));
            dialog.getDialogPane().setContent(root);
        }catch (IOException e){
            /* In caso di errore lo stampiamo a terminale */
            System.out.println("Errore di caricamento dialog");
            e.printStackTrace();
        }

        /* Inizializziamo il controller corrente */
        ShowWaitingParkingController showWaitingParkingController = loader.getController();
        showWaitingParkingController.initData(taxiDriver);

        /* Aggiungiamo il bottone OK  al dialogPane */
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);

        /* Gestiamo il controller mostrandolo e aspettando che l'utente vi interagisca */
        Optional<ButtonType> result = dialog.showAndWait();
        System.out.println(result);
    }
}