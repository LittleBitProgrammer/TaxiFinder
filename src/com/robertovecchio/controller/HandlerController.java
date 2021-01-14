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
    private final TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    private boolean isFirstEntryParking = true;
    private boolean isFirstEntryWaiting = true;

    // TableView
    private TableView<TaxiDriver> tableTaxiDriver;
    private TableView<Parking> tableParking;
    private TableView<WaitingStation> tableWaitingStation;

    // Columns - tableTaxiDriver
    private TableColumn<TaxiDriver, String> fiscalCodeColumn;
    private TableColumn<TaxiDriver, String> firstNameColumn;
    private TableColumn<TaxiDriver, String> lastNameColumn;
    private TableColumn<TaxiDriver, String> dateOfBirthColumn;
    private TableColumn<TaxiDriver, String> genderColumn;
    private TableColumn<TaxiDriver, String> emailColumn;
    private TableColumn<TaxiDriver, String> taxiColumn;
    private TableColumn<TaxiDriver, String> licenseNumberColumn;

    // Columns - tableParking
    private TableColumn<Parking, String> latitudeColumn;
    private TableColumn<Parking, String> longitudeColumn;
    private TableColumn<Parking, String> streetNameColumn;
    private TableColumn<Parking, String> streetNumberColumn;
    private TableColumn<Parking, String> stationNameColumn;
    private TableColumn<Parking, String> capacityColumn;

    // Columns - tableWaitingStation
    private TableColumn<WaitingStation, String>  latitudeWaitingStationColumn;
    private TableColumn<WaitingStation, String> longitudeWaitingStationColumn;
    private TableColumn<WaitingStation, String> streetNameWaitingStationColumn;
    private TableColumn<WaitingStation, String> streetNumberWaitingStationColumn;
    private TableColumn<WaitingStation, String> stationNameWaitingStationColumn;

    // Observable List
    ObservableList<TaxiDriver> drivers;
    ObservableList<Parking> parkings;
    ObservableList<WaitingStation> waitingStations;

    // ContextMenu
    private ContextMenu contextMenu;


    //==================================================
    //               Variabili FXML
    //==================================================
    @FXML
    VBox vBoxContainer;
    @FXML
    StackPane vBoxCenterContainer;

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
    private static final String showVehicleControllerFile = "src/com/robertovecchio/view/fxml/dialog/showVehicle.fxml";

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

        // Inizializziamo un contextMenu
        contextMenu = new ContextMenu();

        // Inizializziamo un menu Item
        MenuItem menuItem = new MenuItem("Mostra veicolo");

        menuItem.setOnAction(actionEvent -> {
            TaxiDriver taxiDriver = tableTaxiDriver.getSelectionModel().getSelectedItem();
            this.showVehicle(taxiDriver);
        });

        // Aggiungiamo i menuItem al contextMenu
        contextMenu.getItems().add(menuItem);

        // Inizializziamo la collections
        this.drivers = taxiFinderData.getTaxiDrivers();
        this.parkings = taxiFinderData.getParkings();
        this.waitingStations = taxiFinderData.getWaitingStations();

        // Inizializzo le TableView
        compositeTaxiDriverTableView();

        // Aggiungiamo al vbox centrale il
        vBoxCenterContainer.getChildren().add(tableTaxiDriver);
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
        homeLabel.setOnMouseClicked(actionEvent -> System.out.println("Home premuto"));

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

            // Aggiungiamo il bottone OK e CANCEL al dialogPane
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
                            System.out.println(result);

                            event.consume();
                        }else if (!addTaxiDriverController.validateEmail()){
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Email errata", ButtonType.OK);
                            alert.setHeaderText("Hai inserito una email utente errata");
                            alert.setContentText("Inserisci una email valida");

                            Optional<ButtonType> result = alert.showAndWait();
                            System.out.println(result);

                            event.consume();
                        }else if (!addTaxiDriverController.validatePassword()){
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Password errata", ButtonType.OK);
                            alert.setHeaderText("Hai inserito una password utente errata");
                            alert.setContentText("Inserisci una password con lunghezza maggiore di 3 e minore di 15");

                            Optional<ButtonType> result = alert.showAndWait();
                            System.out.println(result);

                            event.consume();
                        }else if (!addTaxiDriverController.validateFiscalCode()){
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Codice Fiscale errato", ButtonType.OK);
                            alert.setHeaderText("Hai inserito un codice fiscale errato");
                            alert.setContentText("Inserisci un codice fiscale con lunghezza pari a 16");

                            Optional<ButtonType> result = alert.showAndWait();
                            System.out.println(result);


                            event.consume();
                        }else if (addTaxiDriverController.existYet()){
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Già esistente", ButtonType.OK);
                            alert.setHeaderText("Utente o un auto esistente");
                            alert.setContentText("Hai inserito un utente o auto già inserito");

                            Optional<ButtonType> result = alert.showAndWait();
                            System.out.println(result);

                            event.consume();
                        }
                    });

            // Gestiamo il controller mostrandolo e aspettando che l'utente vi interagisca
            Optional<ButtonType> result = dialog.showAndWait();

            // Gestiamo il caso in cui l'utente abbia premuto OK
            if (result.isPresent() && result.get() == ButtonType.APPLY){
                TaxiDriver newTaxiDriver = addTaxiDriverController.processAddTaxiDriver();
                this.tableTaxiDriver.getSelectionModel().select(newTaxiDriver);
            }
        });

        //==================================================
        //                 Congeda Tassista
        //==================================================

        // Aggiungiamo un'azione quando viene cliccato congeda tassista
        firesTaxiDriver.setOnAction(actionEvent -> {
            // creiamo un nuovo dialog da visualizzare
            Dialog<ButtonType> dialog = new Dialog<>();

            // inizializziamo il proprietario
            dialog.initOwner(this.vBoxContainer.getScene().getWindow());

            // Impostiamo il titolo del dialog
            dialog.setTitle("Congeda un tassista");

            // Carichiamo il file di iterfaccia per il dialog
            FXMLLoader loader = new FXMLLoader();

            try{
                Parent root = loader.load(new FileInputStream(removeTaxiDriverControllerFile));
                dialog.getDialogPane().setContent(root);
            }catch (IOException e){
                System.out.println("Errore di caricamento dialog");
                e.printStackTrace();
            }

            // Aggiungiamo il bottone OK e CANCEL al dialogPane
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

            // Gestiamo il controller mostrandolo e aspettando che l'utente vi interagisca
            Optional<ButtonType> result = dialog.showAndWait();

            RemoveTaxiDriverController removeTaxiDriverController = loader.getController();

            // Gestiamo il caso in cui l'utente abbia premuto OK
            if (result.isPresent() && result.get() == ButtonType.APPLY){
                removeTaxiDriverController.processFireTaxiDriver();
                this.tableTaxiDriver.getSelectionModel().selectFirst();
            }
        });

        //==================================================
        //                 Aggiungi Parcheggio
        //==================================================

        // Aggiungiamo un'azione quando viene cliccato aggiungi parcheggio
        addParking.setOnAction(actionEvent -> {
            // creiamo un nuovo dialog da visualizzare
            Dialog<ButtonType> dialog = new Dialog<>();

            // inizializziamo il proprietario
            dialog.initOwner(this.vBoxContainer.getScene().getWindow());

            // Impostiamo il titolo del dialog
            dialog.setTitle("Aggiungi un Parcheggio");

            // Carichiamo il file di iterfaccia per il dialog
            FXMLLoader loader = new FXMLLoader();

            try{
                Parent root = loader.load(new FileInputStream(addParkingControllerFile));
                dialog.getDialogPane().setContent(root);
            }catch (IOException e){
                System.out.println("Errore di caricamento dialog");
                e.printStackTrace();
            }

            // Aggiungiamo il bottone OK e CANCEL al dialogPane
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

            AddParkingController addParkingController = loader.getController();
            dialog.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(addParkingController.invalidInputProperty());

            // Gestione Errori
            dialog.getDialogPane().lookupButton(ButtonType.APPLY).addEventFilter(ActionEvent.ACTION,
                    event->{
                        if (addParkingController.existYet()){
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Già esistente", ButtonType.OK);
                            alert.setHeaderText("Parcheggio esistente");
                            alert.setContentText("Hai inserito un parcheggio già inserito");

                            Optional<ButtonType> result = alert.showAndWait();
                            System.out.println(result);

                            event.consume();
                        }
            });

            // Gestiamo il controller mostrandolo e aspettando che l'utente vi interagisca
            Optional<ButtonType> result = dialog.showAndWait();

            // Gestiamo il caso in cui l'utente abbia premuto OK
            if (result.isPresent() && result.get() == ButtonType.APPLY){
                Parking newParking = addParkingController.processAddParking();
                this.tableParking.getSelectionModel().select(newParking);
            }
        });

        //==================================================
        //                 Rimuovi Parcheggio
        //==================================================

        // Aggiungiamo un'azione quando viene cliccato rimuovi parcheggio
        removeParking.setOnAction(actionEvent -> {
            // creiamo un nuovo dialog da visualizzare
            Dialog<ButtonType> dialog = new Dialog<>();

            // inizializziamo il proprietario
            dialog.initOwner(this.vBoxContainer.getScene().getWindow());

            // Impostiamo il titolo del dialog
            dialog.setTitle("Rimuovi un Parcheggio");

            // Carichiamo il file di iterfaccia per il dialog
            FXMLLoader loader = new FXMLLoader();

            try{
                Parent root = loader.load(new FileInputStream(removeParkingControllerFile));
                dialog.getDialogPane().setContent(root);
            }catch (IOException e){
                System.out.println("Errore di caricamento dialog");
                e.printStackTrace();
            }

            // Aggiungiamo il bottone OK e CANCEL al dialogPane
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

            // Gestiamo il controller mostrandolo e aspettando che l'utente vi interagisca
            Optional<ButtonType> result = dialog.showAndWait();

            RemoveParkingController removeParkingController = loader.getController();

            // Gestiamo il caso in cui l'utente abbia premuto OK
            if (result.isPresent() && result.get() == ButtonType.APPLY){
                removeParkingController.processRemoveParking();
                this.tableParking.getSelectionModel().selectFirst();
            }
        });

        //==================================================
        //                 Aggiungi Postazione
        //==================================================

        // Aggiungiamo un'azione quando viene cliccato aggiungi postazione
        addWaitingStation.setOnAction(actionEvent -> {
            // creiamo un nuovo dialog da visualizzare
            Dialog<ButtonType> dialog = new Dialog<>();

            // inizializziamo il proprietario
            dialog.initOwner(this.vBoxContainer.getScene().getWindow());

            // Impostiamo il titolo del dialog
            dialog.setTitle("Aggiungi un Postazione");

            // Carichiamo il file di iterfaccia per il dialog
            FXMLLoader loader = new FXMLLoader();

            try{
                Parent root = loader.load(new FileInputStream(addWaitingStationControllerFile));
                dialog.getDialogPane().setContent(root);
            }catch (IOException e){
                System.out.println("Errore di caricamento dialog");
                e.printStackTrace();
            }

            // Aggiungiamo il bottone OK e CANCEL al dialogPane
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

            AddWaitingStationController addWaitingStationController = loader.getController();
            dialog.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(addWaitingStationController.invalidInputProperty());

            // Gestione Errori
            dialog.getDialogPane().lookupButton(ButtonType.APPLY).addEventFilter(ActionEvent.ACTION,
                    event->{
                        if (addWaitingStationController.existYet()){
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Già esistente", ButtonType.OK);
                            alert.setHeaderText("Postazione esistente");
                            alert.setContentText("Hai inserito una postazione già inserito");

                            Optional<ButtonType> result = alert.showAndWait();
                            System.out.println(result);

                            event.consume();
                        }
                    });

            // Gestiamo il controller mostrandolo e aspettando che l'utente vi interagisca
            Optional<ButtonType> result = dialog.showAndWait();

            // Gestiamo il caso in cui l'utente abbia premuto OK
            if (result.isPresent() && result.get() == ButtonType.APPLY){
                WaitingStation newWaiting = addWaitingStationController.processAddWaitingStation();
                this.tableWaitingStation.getSelectionModel().select(newWaiting);
            }
        });

        //==================================================
        //                 Rimuovi Postazione
        //==================================================

        // Aggiungiamo un'azione quando viene cliccato rimuovi parcheggio
        removeWaitingStation.setOnAction(actionEvent -> {
            // creiamo un nuovo dialog da visualizzare
            Dialog<ButtonType> dialog = new Dialog<>();

            // inizializziamo il proprietario
            dialog.initOwner(this.vBoxContainer.getScene().getWindow());

            // Impostiamo il titolo del dialog
            dialog.setTitle("Rimuovi una Postazione");

            // Carichiamo il file di iterfaccia per il dialog
            FXMLLoader loader = new FXMLLoader();

            try{
                Parent root = loader.load(new FileInputStream(removeWaitingStationControllerFile));
                dialog.getDialogPane().setContent(root);
            }catch (IOException e){
                System.out.println("Errore di caricamento dialog");
                e.printStackTrace();
            }

            // Aggiungiamo il bottone OK e CANCEL al dialogPane
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

            // Gestiamo il controller mostrandolo e aspettando che l'utente vi interagisca
            Optional<ButtonType> result = dialog.showAndWait();

            RemoveWaitingStationController removeWaitingStationController = loader.getController();

            // Gestiamo il caso in cui l'utente abbia premuto OK
            if (result.isPresent() && result.get() == ButtonType.APPLY){
                removeWaitingStationController.processRemoveWaitingStation();
                this.tableWaitingStation.getSelectionModel().selectFirst();
            }
        });

        // Aggiungiamo un'azione quando Logout viene premuto
        exitLabel.setOnMouseClicked(actionEvent -> UtilityController.changeStageTo(mainControllerFile, "Taxi Finder",
                                  "Errore reperimento interfaccia", exitLabel));

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
        visualizeTaxiDriver.setOnAction(actionEvent -> changeVisibility(this.tableTaxiDriver, this.tableParking, this.tableWaitingStation));

        //Aggiungiamo un'azione quando visualizza parcheggi viene premuto
        visualizeParking.setOnAction(actionEvent -> {
            if (this.isFirstEntryParking){
                compositeParkingTableView();
                vBoxCenterContainer.getChildren().add(tableParking);
                isFirstEntryParking = false;
            }
            changeVisibility(this.tableParking, this.tableTaxiDriver, this.tableWaitingStation);
        });

        //Aggiungiamo un'azione quando visualizza postazioni viene premuto
        visualizeWaitingStations.setOnAction(actionEvent -> {
            if (this.isFirstEntryWaiting){
                compositeWaitingStationTableView();
                vBoxCenterContainer.getChildren().add(tableWaitingStation);
                isFirstEntryWaiting = false;
            }
            changeVisibility(this.tableWaitingStation, this.tableTaxiDriver, this.tableParking);
        });

        return toolBar;
    }

    private void changeVisibility(TableView<?> visible, TableView<?>... invisibles){
        visible.setVisible(true);
        for (TableView<?> invisible : invisibles){
            if (invisible != null){
                invisible.setVisible(false);
            }
        }
    }

    //==================================================
    //               TABLEVIEW - TASSISTI
    //==================================================

    private void compositeTaxiDriverTableView(){
        // Creiamo le colonne della tableView
        this.fiscalCodeColumn = new TableColumn<>("Codice Fiscale");
        this.firstNameColumn = new TableColumn<>("Nome");
        this.lastNameColumn = new TableColumn<>("Cognome");
        this.dateOfBirthColumn = new TableColumn<>("Data di nascita");
        this.genderColumn = new TableColumn<>("Genere");
        this.emailColumn = new TableColumn<>("Email");
        this.taxiColumn = new TableColumn<>("Targa Taxi");
        this.licenseNumberColumn = new TableColumn<>("Nr. Licensa");

        // Inizializziamo la TableView
        this.tableTaxiDriver = new TableView<>();

        // Aggiungiamo le colonne alla tabella
        this.tableTaxiDriver.getColumns().add(fiscalCodeColumn);
        this.tableTaxiDriver.getColumns().add(firstNameColumn);
        this.tableTaxiDriver.getColumns().add(lastNameColumn);
        this.tableTaxiDriver.getColumns().add(dateOfBirthColumn);
        this.tableTaxiDriver.getColumns().add(genderColumn);
        this.tableTaxiDriver.getColumns().add(emailColumn);
        this.tableTaxiDriver.getColumns().add(taxiColumn);
        this.tableTaxiDriver.getColumns().add(licenseNumberColumn);

        // Impostiamo la grandezza massima della tabella per ogni colonna
        this.tableTaxiDriver.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.fiscalCodeColumn.setMaxWidth(Integer.MAX_VALUE * 12.5);      // 12,5%
        this.firstNameColumn.setMaxWidth(Integer.MAX_VALUE * 12.5D);      // 12,5%
        this.lastNameColumn.setMaxWidth(Integer.MAX_VALUE * 12.5D);       // 12,5%
        this.dateOfBirthColumn.setMaxWidth(Integer.MAX_VALUE * 12.5D);    // 12,5%
        this.genderColumn.setMaxWidth(Integer.MAX_VALUE * 12.5D);         // 12,5%
        this.emailColumn.setMaxWidth(Integer.MAX_VALUE * 12.5D);          // 12,5%
        this.taxiColumn.setMaxWidth(Integer.MAX_VALUE * 12.5D);           // 12,5%
        this.licenseNumberColumn.setMaxWidth(Integer.MAX_VALUE * 12.5D);  // 12,5%

        // Impediamo che le tabelle possano essere riordinate dall'utente
        this.tableTaxiDriver.skinProperty().addListener((observableValue, oldWidth, newWidth) ->{
            final TableHeaderRow header = (TableHeaderRow) tableTaxiDriver.lookup("TableHeaderRow");

            header.reorderingProperty().addListener((obs, oldValue, newValue) -> header.setReordering(false));
        });

        // Rendiamo la tableView non editabile
        this.tableTaxiDriver.setEditable(false);

        // Impostiamo le proprietà delle colonne
        setFiscalCodeColumnProperty();
        setFirstNameColumnProperty();
        setLastNameColumnProperty();
        setDateOfBirthColumnProperty();
        setGenderColumnProperty();
        setEmailColumnProperty();
        setTaxiColumnProperty();
        setLicenseNumberColumnProperty();

        // Impostiamo una larghezza base
        this.tableTaxiDriver.setPrefWidth(2048);

        tableTaxiDriver.setRowFactory(taxiDriverTableView -> {
            final TableRow<TaxiDriver> row = new TableRow<>();
            // Impostiamo il contextmenu su di una row, ma usiamo il binding solo se non è vuota
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu)null)
                            .otherwise(contextMenu)
            );
            return row ;
        });

        // Impostiamo gli item da visualizzare
        this.tableTaxiDriver.setItems(this.drivers);
    }

    //==================================================
    //              TABLEVIEW - PARCHEGGI
    //==================================================

    private void compositeParkingTableView(){
        // Creiamo le colonne della tableView
        this.latitudeColumn = new TableColumn<>("Latitudine");
        this.longitudeColumn = new TableColumn<>("Longitude");
        this.streetNameColumn = new TableColumn<>("Strada");
        this.streetNumberColumn = new TableColumn<>("Civico");
        this.stationNameColumn = new TableColumn<>("Parcheggio");
        this.capacityColumn = new TableColumn<>("Capacità");

        // Inizializziamo la TableView
        this.tableParking = new TableView<>();

        // Aggiungiamo le colonne alla tabella
        this.tableParking.getColumns().add(latitudeColumn);
        this.tableParking.getColumns().add(longitudeColumn);
        this.tableParking.getColumns().add(streetNameColumn);
        this.tableParking.getColumns().add(streetNumberColumn);
        this.tableParking.getColumns().add(stationNameColumn);
        this.tableParking.getColumns().add(capacityColumn);

        // Impostiamo la grandezza massima della tabella per ogni colonna
        this.tableParking.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.latitudeColumn.setMaxWidth(Integer.MAX_VALUE * 16.6D);          // 16,6%
        this.longitudeColumn.setMaxWidth(Integer.MAX_VALUE * 16.6D);         // 16,6%
        this.streetNameColumn.setMaxWidth(Integer.MAX_VALUE * 16.6D);        // 16,6%
        this.streetNumberColumn.setMaxWidth(Integer.MAX_VALUE * 16.6D);      // 16,6%
        this.stationNameColumn.setMaxWidth(Integer.MAX_VALUE * 16.6D);       // 16,6%
        this.capacityColumn.setMaxWidth(Integer.MAX_VALUE * 16.6D);          // 16,6%

        // Impediamo che le tabelle possano essere riordinate dall'utente
        this.tableParking.skinProperty().addListener((observableValue, oldWidth, newWidth) ->{
            final TableHeaderRow header = (TableHeaderRow) tableParking.lookup("TableHeaderRow");

            header.reorderingProperty().addListener((obs, oldValue, newValue) -> header.setReordering(false));
        });

        // Rendiamo la tableView non editabile
        this.tableParking.setEditable(false);

        // Impostiamo le proprietà delle colonne
        setLatitudeColumnProperty();
        setLongitudeColumnProperty();
        setStreetNameColumnProperty();
        setStreetNumberColumnProperty();
        setStationNameColumnProperty();
        setCapacityColumnProperty();

        // Impostiamo una larghezza base
        this.tableParking.setPrefWidth(2048);

        // Impostiamo gli item da visualizzare
        this.tableParking.setItems(this.parkings);
    }

    //==================================================
    //              TABLEVIEW - POSTAZIONI
    //==================================================

    private void compositeWaitingStationTableView(){
        // Creiamo le colonne della tableView
        this.latitudeWaitingStationColumn = new TableColumn<>("Latitudine");
        this.longitudeWaitingStationColumn = new TableColumn<>("Longitudine");
        this.streetNameWaitingStationColumn = new TableColumn<>("Strada");
        this.streetNumberWaitingStationColumn = new TableColumn<>("Civico");
        this.stationNameWaitingStationColumn = new TableColumn<>("Postazione");

        // Inizializziamo la TableView
        this.tableWaitingStation = new TableView<>();

        // Aggiungiamo le colonne alla tabella
        this.tableWaitingStation.getColumns().add(latitudeWaitingStationColumn);
        this.tableWaitingStation.getColumns().add(longitudeWaitingStationColumn);
        this.tableWaitingStation.getColumns().add(streetNameWaitingStationColumn);
        this.tableWaitingStation.getColumns().add(streetNumberWaitingStationColumn);
        this.tableWaitingStation.getColumns().add(stationNameWaitingStationColumn);

        // Impostiamo la grandezza massima della tabella per ogni colonna
        this.tableWaitingStation.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.latitudeWaitingStationColumn.setMaxWidth(Integer.MAX_VALUE * 20D); // 20%
        this.longitudeWaitingStationColumn.setMaxWidth(Integer.MAX_VALUE * 20D); // 20%
        this.streetNameWaitingStationColumn.setMaxWidth(Integer.MAX_VALUE * 20D); // 20%
        this.streetNumberWaitingStationColumn.setMaxWidth(Integer.MAX_VALUE * 20D); // 20%
        this.stationNameWaitingStationColumn.setMaxWidth(Integer.MAX_VALUE * 20D); // 20%

        // Impediamo che le tabelle possano essere riordinate dall'utente
        this.tableWaitingStation.skinProperty().addListener((observableValue, oldWidth, newWidth) ->{
            final TableHeaderRow header = (TableHeaderRow) tableWaitingStation.lookup("TableHeaderRow");

            header.reorderingProperty().addListener((obs, oldValue, newValue) -> header.setReordering(false));
        });

        // Rendiamo la tableView non editabile
        this.tableWaitingStation.setEditable(false);

        // Impostiamo le proprietà delle colonne
        setLatitudeWaitingStationColumnProperty();
        setLongitudeWaitingStationColumnProperty();
        setStreetNameWaitingStationColumnProperty();
        setStreetNumberWaitingStationColumnProperty();
        setStationNameWaitingStationColumnProperty();

        // Impostiamo una larghezza base
        this.tableWaitingStation.setPrefWidth(2048);

        // Impostiamo altezza base
        this.tableWaitingStation.setPrefHeight(3096);

        // Impostiamo gli item da visualizzare
        this.tableWaitingStation.setItems(this.waitingStations);
    }

    //==================================================
    //            Metodi Propietà Colonne
    //==================================================

    private void setFiscalCodeColumnProperty(){
        this.fiscalCodeColumn.setCellValueFactory(taxiDriverStringCellDataFeatures -> new SimpleStringProperty(
                taxiDriverStringCellDataFeatures.getValue().getFiscalCode()));

        // Personalizziamo la cella e quello che vogliamo vedere
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

    private void setFirstNameColumnProperty(){
        this.firstNameColumn.setCellValueFactory(taxiDriverStringCellDataFeatures -> new SimpleStringProperty(
                taxiDriverStringCellDataFeatures.getValue().getFirstName()));

        // Personalizziamo la cella e quello che vogliamo vedere
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

    private void setLastNameColumnProperty(){
        this.lastNameColumn.setCellValueFactory(taxiDriverStringCellDataFeatures -> new SimpleStringProperty(
                taxiDriverStringCellDataFeatures.getValue().getLastName()));

        // Personalizziamo la cella e quello che vogliamo vedere
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

    private void setDateOfBirthColumnProperty(){
        this.dateOfBirthColumn.setCellValueFactory(taxiDriverStringCellDataFeatures -> new SimpleStringProperty(
                taxiDriverStringCellDataFeatures.getValue().getDateOfBirth().format(taxiFinderData.getDateTimeFormatter())));

        // Personalizziamo la cella e quello che vogliamo vedere
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

    private void setGenderColumnProperty(){
        this.genderColumn.setCellValueFactory(taxiDriverStringCellDataFeatures -> new SimpleStringProperty(
                taxiDriverStringCellDataFeatures.getValue().getGenderType().getTranslation()));

        // Personalizziamo la cella e quello che vogliamo vedere
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

    private void setTaxiColumnProperty(){
        this.taxiColumn.setCellValueFactory(taxiDriverStringCellDataFeatures -> new SimpleStringProperty(
                taxiDriverStringCellDataFeatures.getValue().getTaxi().getLicensePlate()));

        // Personalizziamo la cella e quello che vogliamo vedere
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

    private void setLicenseNumberColumnProperty(){
        this.licenseNumberColumn.setCellValueFactory(taxiDriverStringCellDataFeatures -> new SimpleStringProperty(
                taxiDriverStringCellDataFeatures.getValue().getLicenseNumber()));

        // Personalizziamo la cella e quello che vogliamo vedere
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

    private void setLatitudeColumnProperty(){
        this.latitudeColumn.setCellValueFactory(streetStringCellDataFeatures -> new SimpleStringProperty(
                String.valueOf(streetStringCellDataFeatures.getValue().getCoordinates().getLatitude())));

        // Personalizziamo la cella e quello che vogliamo vedere
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

    private void setLongitudeColumnProperty(){
        this.longitudeColumn.setCellValueFactory(streetStringCellDataFeatures -> new SimpleStringProperty(
                String.valueOf(streetStringCellDataFeatures.getValue().getCoordinates().getLongitude())));

        // Personalizziamo la cella e quello che vogliamo vedere
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

    private void setStreetNameColumnProperty(){
        this.streetNameColumn.setCellValueFactory(streetStringCellDataFeatures -> new SimpleStringProperty(
                streetStringCellDataFeatures.getValue().getStreetName()));

        // Personalizziamo la cella e quello che vogliamo vedere
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

    private void setStreetNumberColumnProperty(){
        this.streetNumberColumn.setCellValueFactory(streetStringCellDataFeatures -> new SimpleStringProperty(
                streetStringCellDataFeatures.getValue().getStreetNumber()));

        // Personalizziamo la cella e quello che vogliamo vedere
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

    private void setStationNameColumnProperty(){
        this.stationNameColumn.setCellValueFactory(streetStringCellDataFeatures -> new SimpleStringProperty(
                streetStringCellDataFeatures.getValue().getStationName()));

        // Personalizziamo la cella e quello che vogliamo vedere
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

    private void setCapacityColumnProperty(){
        this.capacityColumn.setCellValueFactory(streetStringCellDataFeatures -> new SimpleStringProperty(
                String.valueOf(streetStringCellDataFeatures.getValue().getParkingCapacity())
        ));

        // Personalizziamo la cella e quello che vogliamo vedere
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

    private void setLatitudeWaitingStationColumnProperty(){
        this.latitudeWaitingStationColumn.setCellValueFactory(streetStringCellDataFeatures -> new SimpleStringProperty(
                String.valueOf(streetStringCellDataFeatures.getValue().getCoordinates().getLatitude())));

        // Personalizziamo la cella e quello che vogliamo vedere
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

    private void setLongitudeWaitingStationColumnProperty(){
        this.longitudeWaitingStationColumn.setCellValueFactory(streetStringCellDataFeatures -> new SimpleStringProperty(
                String.valueOf(streetStringCellDataFeatures.getValue().getCoordinates().getLongitude())));

        // Personalizziamo la cella e quello che vogliamo vedere
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

    private void setStreetNameWaitingStationColumnProperty(){
        this.streetNameWaitingStationColumn.setCellValueFactory(streetStringCellDataFeatures -> new SimpleStringProperty(
                streetStringCellDataFeatures.getValue().getStreetName()));

        // Personalizziamo la cella e quello che vogliamo vedere
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

    private void setStreetNumberWaitingStationColumnProperty(){
        this.streetNumberWaitingStationColumn.setCellValueFactory(streetStringCellDataFeatures -> new SimpleStringProperty(
                streetStringCellDataFeatures.getValue().getStationName()));

        // Personalizziamo la cella e quello che vogliamo vedere
        this.streetNumberWaitingStationColumn.setCellFactory(streetStringTableColumn -> new TableCell<>(){
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

    private void setStationNameWaitingStationColumnProperty(){
        this.stationNameWaitingStationColumn.setCellValueFactory(streetStringCellDataFeatures -> new SimpleStringProperty(
                streetStringCellDataFeatures.getValue().getStreetNumber()));

        // Personalizziamo la cella e quello che vogliamo vedere
        this.stationNameWaitingStationColumn.setCellFactory(streetStringTableColumn -> new TableCell<>(){
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

    private void showVehicle(TaxiDriver taxiDriver){
        // creiamo un nuovo dialog da visualizzare
        Dialog<ButtonType> dialog = new Dialog<>();

        // inizializziamo il proprietario
        dialog.initOwner(this.vBoxContainer.getScene().getWindow());

        // Impostiamo il titolo del dialog
        dialog.setTitle(String.format("Veicolo di %s %s", taxiDriver.getFirstName(), taxiDriver.getLastName()));

        // Carichiamo il file di iterfaccia per il dialog
        FXMLLoader loader = new FXMLLoader();

        try{
            Parent root = loader.load(new FileInputStream(showVehicleControllerFile));
            dialog.getDialogPane().setContent(root);
        }catch (IOException e){
            System.out.println("Errore di caricamento dialog");
            e.printStackTrace();
        }

        VehicleController vehicleController = loader.getController();
        vehicleController.initData(taxiDriver);

        // Aggiungiamo il bottone OK e CANCEL al dialogPane
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK);

        // Gestiamo il controller mostrandolo e aspettando che l'utente vi interagisca
        Optional<ButtonType> result = dialog.showAndWait();
        System.out.println(result);
    }
}