package com.robertovecchio.controller;

import com.robertovecchio.controller.dialog.ChangeParkingController;
import com.robertovecchio.controller.dialog.ShowPathController;
import com.robertovecchio.model.booking.ArrivalModality;
import com.robertovecchio.model.booking.Booking;
import com.robertovecchio.model.booking.OrderState;
import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.dijkstra.DijkstraAlgorithm;
import com.robertovecchio.model.graph.node.Node;
import com.robertovecchio.model.graph.node.Parking;
import com.robertovecchio.model.graph.node.WaitingStation;
import com.robertovecchio.model.user.State;
import com.robertovecchio.model.user.TaxiDriver;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

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
    /**
     * Istanza del database
     * @see TaxiFinderData
     * */
    private final TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    // TableColumn
    /**
     * TableColumn (colonna) utile a mostrare la data di una prenotazione
     * @see TableColumn
     * @see Booking
     */
    private TableColumn<Booking, String> orderDateColumn;
    /**
     * TableColumn (colonna) utile a mostrare l'orario di una prenotazione
     * @see TableColumn
     * @see Booking
     */
    private TableColumn<Booking, String> orderTimeColumn;
    /**
     * TableColumn (colonna) utile a mostrare la posizione di partenza di una prenotazione
     * @see TableColumn
     * @see Booking
     */
    private TableColumn<Booking, String> fromColumn;
    /**
     * TableColumn (colonna) utile a mostrare la posizione di destinazione di una prenotazione
     * @see TableColumn
     * @see Booking
     */
    private TableColumn<Booking, String> toColumn;
    /**
     * TableColumn (colonna) utile a mostrare il cliente di una prenotazione
     * @see TableColumn
     * @see Booking
     */
    private TableColumn<Booking, String> customerColumn;
    /**
     * TableColumn (colonna) utile a mostrare le modalità di raggiungimento di una prenotazione
     * @see TableColumn
     * @see Booking
     */
    private TableColumn<Booking, String> customerReachment;

    // ObservableList
    /**
     * ObservableList di prenotazioni
     * @see ObservableList
     * @see Booking
     */
    private ObservableList<Booking> bookings;
    /**
     * Variabile d'istanza utile a memorizzare l'ordine corrente
     * @see Booking
     */
    private Booking currentOrder;
    /**
     * Variabile utile a mostrare lo stato di un tassista con un pallino rosso o verde
     * rosso = occupato
     * verde = libero
     * @see Circle
     */
    private Circle circle;
    /**
     * Bottone utile a gestire un nuovo ordine, impostato inizialmente a default
     * @see Button
     */
    private Button newOrder;
    /**
     * Menu che rappresenta lo stato di un tassista
     * @see Menu
     */
    private Menu state;

    //==================================================
    //               Variabili FXML
    //==================================================
    /**
     * Vbox della view associata ala controller
     * @see VBox
     */
    @FXML
    VBox vBoxTopContainer;
    /**
     * StackPane della view associata al controller
     * @see StackPane
     */
    @FXML
    StackPane stackContainer;
    /**
     * TableView utile a mostrare la lista prenotazioni effettuate presso il tassista corrente
     * @see TableView
     * @see Booking
     * */
    @FXML
    private TableView<Booking> bookingTableView;
    /**
     * GridPane della view associata al controller
     * @see GridPane
     */
    @FXML
    private GridPane gridContainer;
    /**
     * Bottone utile a gestire la scelta del tassista di voler raggiungere dalla propria posizione la postazione del
     * cliente con il percorso più breve (in termini di KM)
     * @see Button
     */
    @FXML
    private Button streetChoice;
    /**
     * Bottone utile a gestire la scelta del tassista di voler raggiungere dalla propria posizione la postazione del
     * cliente con il percorso più veloce (in termini di Tempo, ovvero minuti necessari per il percorrimento)
     * @see Button
     */
    @FXML
    private Button timeChoice;


    //==================================================
    //               Variabili Statiche
    //==================================================

    /**
     * Variabile statica che rappresenta il percorso alla view principale
     * */
    private static final String mainControllerFile = "src/com/robertovecchio/view/fxml/main.fxml";
    /**
     * Variabile statica che rappresenta il percorso al dialog per il cambio parcheggio
     * */
    private static final String changeParkingControllerFile = "src/com/robertovecchio/view/fxml/dialog/changeParking.fxml";
    /**
     * Variabile statica che rappresenta il percorso al dialog utile a mostrare il percorso da seguire
     * */
    private static final String showPathControllerFile = "src/com/robertoVecchio/view/fxml/dialog/showPath.fxml";

    //==================================================
    //               Inizializzazione
    //==================================================

    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize() {
        /* Aggiungiamo il menuBar al vBox */
        this.vBoxTopContainer.getChildren().addAll(compositeHandlerMenuBar(), compositeToolBar());

        Predicate<Booking> filteredBooking = booking -> booking.getDriver().equals(taxiFinderData.getCurrentUser()) && (booking.getOrderState() == OrderState.ACCEPTED);
        FilteredList<Booking> filteredList = new FilteredList<>(taxiFinderData.getBookings());
        filteredList.setPredicate(filteredBooking);

        this.bookings = filteredList;

        compositeBookingsTable();

        streetChoice.setOnAction(actionEvent -> {
            this.currentOrder.setArrivalModality(ArrivalModality.LENGTH);
            this.currentOrder.setOrderState(OrderState.ACCEPTED);
            TaxiDriver taxiDriver = (TaxiDriver) taxiFinderData.getCurrentUser();

            taxiDriver.send(currentOrder);

            this.sendTaxiWithShortestPath();
        });

        timeChoice.setOnAction(actionEvent -> {
            this.currentOrder.setArrivalModality(ArrivalModality.TIME);
            this.currentOrder.setOrderState(OrderState.ACCEPTED);
            TaxiDriver taxiDriver = (TaxiDriver) taxiFinderData.getCurrentUser();

            taxiDriver.send(currentOrder);
            this.sendTaxiWithLessTraffic();
        });

        UtilityController.changeVisibility(this.bookingTableView, this.gridContainer);
    }

    /**
     * Metodo utile alla composizione di un MenuBar
     * @return Un Hbox contenente il MenuBar del Tassista
     * @see HBox
     */
    private HBox compositeHandlerMenuBar(){

        /* Inizializziamo i MenuItem */
        MenuItem moveToPerking = new MenuItem("segnala che sei in un altro parcheggio");

        /* Inizializziamo i diversi Menu */
        Menu home = new Menu();
        Menu parking = new Menu("Parcheggio");
        Menu logout = new Menu();

        /* Creiamo delle Label da inserire nei menu home e logout */
        Label homeLabel = new Label("Home");
        Label exitLabel = new Label("Logout");

        /* Impostiamo il colore delle label a bianco */
        homeLabel.setTextFill(Color.WHITE);
        exitLabel.setTextFill(Color.WHITE);

        /* Inseriamo le Label nei menu */
        home.setGraphic(homeLabel);
        logout.setGraphic(exitLabel);

        /* Inizializziamo un menuBar */
        MenuBar menuBar = new MenuBar();

        /* Incapsuliamo i diversi menuItem nei corrispettivi menu */
        parking.getItems().add(moveToPerking);

        /* incapsuliamo i diversi menu nel menuBar */
        menuBar.getMenus().addAll(home, parking, logout);

        /* Aggiungiamo un'azione quando viene premuto home */
        homeLabel.setOnMouseClicked(actionEvent -> System.out.println("Home premuto"));

        /* Aggiungiamo un'azione quando moveToParking viene premuto */
        moveToPerking.setOnAction(actionEvent -> {
            /* Variabile booleana utile a constatare se ci sono nuovi ordini, di default è impostata a false */
            boolean thereIsNewOrder = false;

            /* Per orgni ordine nella lista ordini */
            for (Booking booking : taxiFinderData.getBookings()){

                /* Se vi è un ordine appartenente all'utente attuale ed è ancora in attesa */
                if (booking.getDriver().equals(taxiFinderData.getCurrentUser()) && (booking.getOrderState() == OrderState.WAITING)) {

                    /* Allora vi è una nuova prenotazione per quel tassista */
                    thereIsNewOrder = true;
                    break;
                }
            }

            /* Se vi è un nuovo ordine */
            if (thereIsNewOrder){
                /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
                Alert alert = new Alert(Alert.AlertType.WARNING, "Spostamento impossibile", ButtonType.OK);
                alert.setHeaderText("C'è un ordine in corso");
                alert.setContentText("Non puoi spostarti di parcheggio durante un ordine");

                Optional<ButtonType> result = alert.showAndWait();
                System.out.println(result);
            }else{
                /* creiamo un nuovo dialog da visualizzare */
                Dialog<ButtonType> dialog = new Dialog<>();

                /* inizializziamo il proprietario */
                dialog.initOwner(this.vBoxTopContainer.getScene().getWindow());

                /* Impostiamo il titolo del dialog */
                dialog.setTitle("Cambia Parcheggio");

                FXMLLoader loader = new FXMLLoader();

                try{
                    /* Carichiamo il file FXML */
                    Parent root = loader.load(new FileInputStream(changeParkingControllerFile));
                    dialog.getDialogPane().setContent(root);
                }catch (IOException e){
                    /* In caso di errore lo stampiamo a terminale */
                    System.out.println("Errore di caricamento dialog");
                    e.printStackTrace();
                }

                /* Aggiungiamo il bottone APPLICA e CANCEL al dialogPane */
                dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

                /* Carichiamo il controller corrente */
                ChangeParkingController changeParkingController = loader.getController();

                /* Gestiamo gli errori */
                dialog.getDialogPane().lookupButton(ButtonType.APPLY).addEventFilter(ActionEvent.ACTION,
                        event->{
                            if (!changeParkingController.validateField()){
                                /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
                                Alert alert = new Alert(Alert.AlertType.WARNING, "Spostamento impossibile", ButtonType.OK);
                                alert.setHeaderText("Già sei nel parcheggio");
                                alert.setContentText("È inutile segnalare che sei in questo parcheggio");

                                Optional<ButtonType> result = alert.showAndWait();
                                System.out.println(result);

                                event.consume();
                            }
                        });

                /* Gestiamo il controller mostrandolo e aspettando che l'utente vi interagisca */
                Optional<ButtonType> result = dialog.showAndWait();

                /* Gestiamo il caso in cui l'utente abbia premuto APPLICA */
                if (result.isPresent() && result.get() == ButtonType.APPLY){
                    /* Effettua il cambiamento di parcheggio */
                    changeParkingController.processChangeParking();
                }
            }
        });

        /* Aggiungiamo un'azione quando Logout viene premuto */
        exitLabel.setOnMouseClicked(actionEvent -> UtilityController.navigateTo(mainControllerFile, "Taxi Finder",
                "Errore reperimento interfaccia", exitLabel));

        /* Inizializziamo un menubar di destra */
        MenuBar rightBar = new MenuBar();

        /* Inizializziamo l'utente corrente*/
        TaxiDriver user = (TaxiDriver) taxiFinderData.getCurrentUser();

        /* Inizializziamo il cerchio con le misure stabilite di seguito*/
        this.circle = new Circle(6,6, 6);

        /* Coloriamo il cerchio in base allo stato del tassista*/
        switch (user.getState()){
            case FREE -> circle.setFill(Color.LIGHTGREEN);
            case OCCUPIED -> circle.setFill(Color.RED);
        }

        /* Inizializziamo la stringa del menu che descrive testualmente lo stato di un tassista*/
        state = new Menu(user.getState().getTranslation(), circle);

        /* Aggiungiamo un nuovo menu al menu bar di destra */
        rightBar.getMenus().addAll(state);

        /* Inizializziamo una region */
        Region spacer = new Region();

        /* aggiungiamo il foglio di stile */
        spacer.getStyleClass().add("menu-bar");

        /* Impostiamo alcune proprietà dell'hbox */
        HBox.setHgrow(spacer, Priority.SOMETIMES);

        /* Aggiungiamo elementi all'hbox e ritorniamo tale nodo */
        return new HBox(menuBar, spacer, rightBar);
    }

    /**
     * Metodo utile a comporre la ToolBar del tassista
     * @return La toolBar del tassista
     */
    private ToolBar compositeToolBar() {

        /* Inizializziamo la toolBar */
        ToolBar toolBar = new ToolBar();

        /* Variabile booleana utile a constatare se ci sono nuovi ordini, di default è impostata a false */
        boolean thereIsNewOrder = false;

        /* Per orgni ordine nella lista ordini */
        for (Booking booking : taxiFinderData.getBookings()){

            /* Se vi è un ordine appartenente all'utente attuale ed è ancora in attesa */
            if (booking.getDriver().equals(taxiFinderData.getCurrentUser()) && (booking.getOrderState() == OrderState.WAITING)) {

                /* Allora vi è una nuova prenotazione per quel tassista */
                thereIsNewOrder = true;
                currentOrder = booking;
                break;
            }
        }

        /* inizializzo i Button */
        Button orders = new Button("Visualizza ordini effettuattuati");
        newOrder = new Button("Nuovo ordine in pendenza");

        /* Impostiamo un'azione quando orders viene premuto */
        orders.setOnAction(actionEvent -> UtilityController.changeVisibility(this.bookingTableView, this.gridContainer));

        /* Impostiamo un'azione quando newOrderVienePremuto */
        newOrder.setOnAction(actionEvent -> UtilityController.changeVisibility(this.gridContainer, this.bookingTableView));

        /* Aggiungiamo i button alla toolbar */
        toolBar.getItems().addAll(orders, newOrder);

        /* Impostiamo la zona di gestione di un nuovo ordine a visibil e*/
        newOrder.setVisible(thereIsNewOrder);

        /* Ritorniamo la ToolBar */
        return toolBar;
    }

    /**
     * Metodo utile a comporre La TableView della lista ordini
     */
    private void compositeBookingsTable(){

        /* Creiamo le colonne della tableView */

        /* Data */
        this.orderDateColumn = new TableColumn<>("Data");
        /* Orario */
        this.orderTimeColumn = new TableColumn<>("Orario");
        /* Da */
        this.fromColumn = new TableColumn<>("Da");
        /* A */
        this.toColumn = new TableColumn<>("A");
        /* Cliente */
        this.customerColumn = new TableColumn<>("Cliente");
        /* Tipologia di raggiungimento */
        this.customerReachment = new TableColumn<>("Tipo Raggiungimento");

        /* Aggiungiamo le colonne alla tabella */

        /* Data */
        this.bookingTableView.getColumns().add(this.orderDateColumn);
        /* Orario */
        this.bookingTableView.getColumns().add(this.orderTimeColumn);
        /* Da */
        this.bookingTableView.getColumns().add(this.fromColumn);
        /* A */
        this.bookingTableView.getColumns().add(this.toColumn);
        /* Cliente */
        this.bookingTableView.getColumns().add(this.customerColumn);
        /* Tipologia di raggiungimento */
        this.bookingTableView.getColumns().add(this.customerReachment);

        /* Impostiamo la grandezza massima della tabella per ogni colonna */
        this.bookingTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.orderDateColumn.setMaxWidth(Integer.MAX_VALUE * 16.6);      // 16.6%
        this.orderTimeColumn.setMaxWidth(Integer.MAX_VALUE * 16.6);      // 16.6%
        this.fromColumn.setMaxWidth(Integer.MAX_VALUE * 16.6);           // 16.6%
        this.toColumn.setMaxWidth(Integer.MAX_VALUE * 16.6);             // 16.6%
        this.customerColumn.setMaxWidth(Integer.MAX_VALUE * 16.6);       // 16.6%
        this.customerReachment.setMaxWidth(Integer.MAX_VALUE * 16.6);    // 16.6%

        /* Impediamo che le tabelle possano essere riordinate dall'utente */
        this.bookingTableView.skinProperty().addListener((observableValue, oldWidth, newWidth) ->{
            final TableHeaderRow header = (TableHeaderRow) bookingTableView.lookup("TableHeaderRow");

            header.reorderingProperty().addListener((obs, oldValue, newValue) -> header.setReordering(false));
        });

        /* Rendiamo la tableView non editabile */
        this.bookingTableView.setEditable(false);

        /* Impostiamo le proprietà delle colonne */

        /* Data */
        setOrderDateColumnProperty();
        /* Orario */
        setOrderTimeColumnProperty();
        /* Da */
        setFromColumnProperty();
        /* A */
        setToColumnProperty();
        /* Cliente */
        setCustomerColumnProperty();
        /* Tipologia di raggiungimento */
        setCustomerReachmentColumnProperty();

        /* Impostiamo una larghezza base */
        this.bookingTableView.setPrefWidth(2048);

        /* Impostiamo gli item da visualizzare */
        this.bookingTableView.setItems(this.bookings);
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna della data di prenotazione
     * */
    private void setOrderDateColumnProperty(){
        this.orderDateColumn.setCellValueFactory( bookingStringCellDataFeatures -> new SimpleStringProperty(
                bookingStringCellDataFeatures.getValue().getOrderDate().format(taxiFinderData.getDateTimeFormatter())));

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.orderDateColumn.setCellFactory(bookingStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String date, boolean empty){
                super.updateItem(date, empty);
                if(empty || date == null){
                    setText(null);
                } else {
                    setText(date);
                }
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna dell'orario di prenotazione
     * */
    private void setOrderTimeColumnProperty(){
        this.orderTimeColumn.setCellValueFactory( bookingStringCellDataFeatures -> new SimpleStringProperty(
                bookingStringCellDataFeatures.getValue().getOrderTime().format(taxiFinderData.getTimeFormatter())));

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.orderTimeColumn.setCellFactory(bookingStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String time, boolean empty){
                super.updateItem(time, empty);
                if(empty || time == null){
                    setText(null);
                } else {
                    setText(time);
                }
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna della postazione di partenza della prenotazione
     * */
    private void setFromColumnProperty(){
        this.fromColumn.setCellValueFactory( bookingStringCellDataFeatures -> new SimpleStringProperty(
                ((WaitingStation) bookingStringCellDataFeatures.getValue().getFrom()).getStationName()));

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.fromColumn.setCellFactory(bookingStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String stationFrom, boolean empty){
                super.updateItem(stationFrom, empty);
                if(empty || stationFrom == null){
                    setText(null);
                } else {
                    setText(stationFrom);
                }
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna della postazione di arrivo della prenotazione
     * */
    private void setToColumnProperty(){
        this.toColumn.setCellValueFactory( bookingStringCellDataFeatures -> new SimpleStringProperty(
                ((WaitingStation) bookingStringCellDataFeatures.getValue().getTo()).getStationName()));

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.toColumn.setCellFactory(bookingStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String stationTo, boolean empty){
                super.updateItem(stationTo, empty);
                if(empty || stationTo == null){
                    setText(null);
                } else {
                    setText(stationTo);
                }
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna del cliente della prenotazione
     * */
    private void setCustomerColumnProperty(){
        this.customerColumn.setCellValueFactory( bookingStringCellDataFeatures -> {
            return new SimpleStringProperty(
                        bookingStringCellDataFeatures.getValue().getCustomer().getFirstName() + " "  +
                                bookingStringCellDataFeatures.getValue().getCustomer().getLastName());

        });

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.customerColumn.setCellFactory(bookingStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String name, boolean empty){
                super.updateItem(name, empty);
                setText(Objects.requireNonNullElse(name, ""));
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna della modalità di raggiungimento del cliente
     * in una prenotazione
     * */
    private void setCustomerReachmentColumnProperty(){
        this.customerReachment.setCellValueFactory( bookingStringCellDataFeatures -> {

            if (bookingStringCellDataFeatures.getValue().getArrivalModality() != null){
                return new SimpleStringProperty(bookingStringCellDataFeatures.getValue().getArrivalModality().toString());
            }
            return new SimpleStringProperty("");
        });

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.customerReachment.setCellFactory(bookingStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String name, boolean empty){
                super.updateItem(name, empty);
                setText(Objects.requireNonNullElse(name, ""));
            }
        });
    }

    /**
     * Metodo utile a gestire l'eventualità in cui un tassista voglia raggiungere il cliente attraverso il percorso più
     * breve possibile
     */
    private void sendTaxiWithShortestPath(){
        /* Inializzo la classe per l'algoritmo di Dijkstra */
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(taxiFinderData.getGraph());

        Parking currentParking = null;
        TaxiDriver user = (TaxiDriver) taxiFinderData.getCurrentUser();

        /* Recuperiamo il parcheggio corrente */
        for (Node node : taxiFinderData.getGraph().getVertexes()){
            if (node instanceof Parking){
                Parking parking = (Parking) node;
                if (parking.getTaxis().contains(user.getTaxi())){
                    currentParking = parking;
                }
            }
        }

        /* Eseguo l'algoritmo di Dijkstra dalla sorgente */
        dijkstraAlgorithm.execute(currentParking);

        /* Inizializziamo il path */
        LinkedList<Node> path = dijkstraAlgorithm.getPath(this.currentOrder.getFrom());

        /* Mostriamo un dialog per il percorso */
        Dialog<ButtonType> dialog = new Dialog<>();

        /* inizializziamo il proprietario */
        dialog.initOwner(this.vBoxTopContainer.getScene().getWindow());

        /* Impostiamo il titolo del dialog */
        dialog.setTitle("Percorso");

        /* Carichiamo il file di iterfaccia per il dialog */
        FXMLLoader loader = new FXMLLoader();

        try{
            /* Carichiamo il file FXML */
            Parent root = loader.load(new FileInputStream(showPathControllerFile));
            dialog.getDialogPane().setContent(root);
        }catch (IOException e){
            /* In caso di errore lo stampiamo a schermo */
            System.out.println("Errore di caricamento dialog");
            e.printStackTrace();
        }

        /* Aggiungiamo il bottone OK e CANCEL al dialogPane */
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        /* Carichiamo il controller corrente */
        ShowPathController showPathController = loader.getController();
        showPathController.init(path);

        /* Gestiamo il controller mostrandolo e aspettando che l'utente vi interagisca */
        Optional<ButtonType> result = dialog.showAndWait();

        /* Gestiamo il caso in cui l'utente abbia premuto OK */
        if (result.isPresent() && result.get() == ButtonType.OK){
            this.circle.setFill(Color.LIGHTGREEN);
            this.currentOrder.getDriver().setState(State.FREE);

            assert currentParking != null;
            currentParking.getTaxis().poll();
            currentParking.getTaxis().add(user.getTaxi());

            UtilityController.changeVisibility(this.bookingTableView, this.gridContainer, this.newOrder);
            state.setText(user.getState().getTranslation());

            try {
                TaxiFinderData.getInstance().storeGraph();
                TaxiFinderData.getInstance().storeTaxiDrivers();
                TaxiFinderData.getInstance().loadBookings();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }else {
            this.circle.setFill(Color.LIGHTGREEN);
            this.currentOrder.getDriver().setState(State.FREE);

            assert currentParking != null;
            currentParking.getTaxis().poll();
            currentParking.getTaxis().add(user.getTaxi());

            UtilityController.changeVisibility(this.bookingTableView, this.gridContainer, this.newOrder);
            state.setText(user.getState().getTranslation());

            try {
                TaxiFinderData.getInstance().storeGraph();
                TaxiFinderData.getInstance().storeTaxiDrivers();
                TaxiFinderData.getInstance().loadBookings();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Metodo utile a gestire l'eventualità in cui un tassista voglia raggiungere il cliente attraverso il percorso
     * con meno traffico possibile
     */
    private void sendTaxiWithLessTraffic(){
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(taxiFinderData.getTrafficGraph());

        Parking currentParking = null;
        TaxiDriver user = (TaxiDriver) taxiFinderData.getCurrentUser();

        /* Recuperiamo il parcheggio corrente */
        for (Node node : taxiFinderData.getGraph().getVertexes()){
            if (node instanceof Parking){
                Parking parking = (Parking) node;
                if (parking.getTaxis().contains(user.getTaxi())){
                    currentParking = parking;
                }
            }
        }

        /* Eseguo l'algoritmo di Dijkstra dalla sorgente */
        dijkstraAlgorithm.execute(currentParking);

        /* Inizializziamo il path */
        LinkedList<Node> path = dijkstraAlgorithm.getPath(this.currentOrder.getFrom());

        /* Mostriamo un dialog per il percorso */
        Dialog<ButtonType> dialog = new Dialog<>();

        /* inizializziamo il proprietario */
        dialog.initOwner(this.vBoxTopContainer.getScene().getWindow());

        /* Impostiamo il titolo del dialog */
        dialog.setTitle("Percorso");

        /* Carichiamo il file di iterfaccia per il dialog */
        FXMLLoader loader = new FXMLLoader();

        try{
            /* Carichiamo il file FXML */
            Parent root = loader.load(new FileInputStream(showPathControllerFile));
            dialog.getDialogPane().setContent(root);
        }catch (IOException e){
            /* In caso di errore stampiamo a terminale */
            System.out.println("Errore di caricamento dialog");
            e.printStackTrace();
        }

        /* Aggiungiamo il bottone OK e CANCEL al dialogPane */
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);

        /* Carichiamo il loader corrente */
        ShowPathController showPathController = loader.getController();
        showPathController.init(path);

        /* Gestiamo il controller mostrandolo e aspettando che l'utente vi interagisca */
        Optional<ButtonType> result = dialog.showAndWait();

        /* Gestiamo il caso in cui l'utente abbia premuto OK */
        if (result.isPresent() && result.get() == ButtonType.OK) {
            this.circle.setFill(Color.LIGHTGREEN);
            this.currentOrder.getDriver().setState(State.FREE);

            assert currentParking != null;
            currentParking.getTaxis().poll();
            currentParking.getTaxis().add(user.getTaxi());

            UtilityController.changeVisibility(this.bookingTableView, this.gridContainer, this.newOrder);
            state.setText(user.getState().getTranslation());

            try {
                TaxiFinderData.getInstance().storeGraph();
                TaxiFinderData.getInstance().storeTaxiDrivers();
                TaxiFinderData.getInstance().loadBookings();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}
