package com.robertovecchio.controller;

import com.robertovecchio.controller.dialog.AddTaxiDriverController;
import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.graph.node.Street;
import com.robertovecchio.model.user.TaxiDriver;
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
    TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    // TableView
    private TableView<TaxiDriver> tableTaxiDriver;
    private TableView<Street> tableParking;
    private TableView<Street> tableWaitingStation;

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
    private TableColumn<Street, String> latitudeColumn;
    private TableColumn<Street, String> longitudeColumn;
    private TableColumn<Street, String> streetNameColumn;
    private TableColumn<Street, String> streetNumberColumn;
    private TableColumn<Street, String> stationNameColumn;
    private TableColumn<Street, String> capacityColumn;

    // Columns - tableWaitingStation
    private TableColumn<Street, String>  latitudeWaitingStationColumn;
    private TableColumn<Street, String> longitudeWaitingStationColumn;
    private TableColumn<Street, String> streetNameWaitingStationColumn;
    private TableColumn<Street, String> streetNumberWaitingStationColumn;
    private TableColumn<Street, String> stationNameWaitingStationColumn;


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

        // Inizializzo le TableView
        compositeTaxiDriverTableView();
        compositeParkingTableView();
        compositeWaitingStationTableView();

        // Aggiungiamo al vbox centrale il
        vBoxCenterContainer.getChildren().addAll(tableTaxiDriver, tableParking, tableWaitingStation);

        // Impostiamo la visibilità
        changeVisibility(this.tableTaxiDriver, this.tableParking, this.tableWaitingStation);
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
            changeVisibility(this.tableTaxiDriver, this.tableParking, this.tableWaitingStation);
        });

        //Aggiungiamo un'azione quando visualizza parcheggi viene premuto
        visualizeParking.setOnAction(actionEvent -> {
            changeVisibility(this.tableParking, this.tableTaxiDriver, this.tableWaitingStation);
        });

        //Aggiungiamo un'azione quando visualizza postazioni viene premuto
        visualizeWaitingStations.setOnAction(actionEvent -> {
            changeVisibility(this.tableWaitingStation, this.tableTaxiDriver, this.tableParking);
        });

        return toolBar;
    }

    private void changeVisibility(TableView<?> visible, TableView<?>... invisibles){
        visible.setVisible(true);
        for (TableView<?> invisible : invisibles){
            invisible.setVisible(false);
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

        this.tableTaxiDriver.setPrefWidth(2048);
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

        this.tableParking.setPrefWidth(2048);
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

        this.tableWaitingStation.setPrefWidth(2048);
    }

    //==================================================
    //            Metodi Propietà Colonne
    //==================================================

    private void setFiscalCodeColumnProperty(){
        //stub
    }

    private void setFirstNameColumnProperty(){
        //stub
    }

    private void setLastNameColumnProperty(){
        //stub
    }

    private void setDateOfBirthColumnProperty(){
        //stub
    }

    private void setGenderColumnProperty(){
        //stub
    }

    private void setEmailColumnProperty(){
        //stub
    }

    private void setTaxiColumnProperty(){
        //stub
    }

    private void setLicenseNumberColumnProperty(){
        //stub
    }

    private void setLatitudeColumnProperty(){
        //stub
    }

    private void setLongitudeColumnProperty(){
        //stub
    }

    private void setStreetNameColumnProperty(){
        //stub
    }

    private void setStreetNumberColumnProperty(){
        //stub
    }

    private void setStationNameColumnProperty(){
        //stub
    }

    private void setCapacityColumnProperty(){
        //stub
    }

    private void setLatitudeWaitingStationColumnProperty(){
        //stub
    }

    private void setLongitudeWaitingStationColumnProperty(){
        //stub
    }

    private void setStreetNameWaitingStationColumnProperty(){
        //stub
    }

    private void setStreetNumberWaitingStationColumnProperty(){
        //stub
    }

    private void setStationNameWaitingStationColumnProperty(){
        //stub
    }
}