package com.robertovecchio.controller;

import com.robertovecchio.model.booking.Booking;
import com.robertovecchio.model.booking.OrderState;
import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.graph.node.WaitingStation;
import com.robertovecchio.model.user.TaxiDriver;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import java.util.Objects;
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
    private final TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    // TableColumn
    private TableColumn<Booking, String> orderDateColumn;
    private TableColumn<Booking, String> orderTimeColumn;
    private TableColumn<Booking, String> fromColumn;
    private TableColumn<Booking, String> toColumn;
    private TableColumn<Booking, String> customerColumn;
    private TableColumn<Booking, String> customerReachment;

    // ObservableList
    private ObservableList<Booking> bookings;

    //==================================================
    //               Variabili FXML
    //==================================================
    @FXML
    VBox vBoxTopContainer;
    @FXML
    StackPane stackContainer;
    @FXML
    private TableView<Booking> bookingTableView;
    @FXML
    private GridPane gridContainer;
    @FXML
    private Button streetChoice;
    @FXML
    private Button timeChoice;


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
        this.vBoxTopContainer.getChildren().addAll(compositeHandlerMEnuBar(), compositeToolBar());

        Predicate<Booking> filteredBooking = booking -> booking.getCustomer().equals(taxiFinderData.getCurrentUser()) && (booking.getOrderState() == OrderState.ACCEPTED);
        FilteredList<Booking> filteredList = new FilteredList<>(taxiFinderData.getBookings());
        filteredList.setPredicate(filteredBooking);

        this.bookings = filteredList;

        compositeBookingsTable();

        streetChoice.setOnAction(actionEvent -> {
            System.out.println("Scelto strada");
        });

        timeChoice.setOnAction(actionEvent -> {
            System.out.println("Scelto meno traffico");
        });

        UtilityController.changeVisibility(this.bookingTableView, this.gridContainer);
    }

    private HBox compositeHandlerMEnuBar(){

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

        // Inizializziamo un menubar di destra
        MenuBar rightBar = new MenuBar();

        TaxiDriver user = (TaxiDriver) taxiFinderData.getCurrentUser();

        Circle circle = new Circle(6,6, 6);

        switch (user.getState()){
            case FREE -> circle.setFill(Color.LIGHTGREEN);
            case OCCUPIED -> circle.setFill(Color.RED);
        }

        Menu state = new Menu(user.getState().getTranslation(), circle);

        // Aggiungiamo un nuovo menu al menu bar di destra
        rightBar.getMenus().addAll(state);

        // Inizializziamo una region
        Region spacer = new Region();

        // aggiungiamo il foglio di stile
        spacer.getStyleClass().add("menu-bar");

        // Impostiamo alcune proprietà dell'hbox
        HBox.setHgrow(spacer, Priority.SOMETIMES);

        // Aggiungiamo elementi all'hbox e ritorniamo tale nodo
        return new HBox(menuBar, spacer, rightBar);
    }

    private ToolBar compositeToolBar() {

        // Inizializzo la toolBar
        ToolBar toolBar = new ToolBar();

        boolean thereIsNewOrder = false;

        for (Booking booking : taxiFinderData.getBookings()){
            if (booking.getDriver().equals(taxiFinderData.getCurrentUser()) && (booking.getOrderState() == OrderState.WAITING)) {
                thereIsNewOrder = true;
                break;
            }
        }

        // inizializzo i Button
        Button orders = new Button("Visualizza ordini effettuattuati");
        Button newOrder = new Button("Nuovo ordine in pendenza");

        // Impostiamo un'azione quando orders viene premuto
        orders.setOnAction(actionEvent -> UtilityController.changeVisibility(this.bookingTableView, this.gridContainer));

        // Impostiamo un'azione quando newOrderVienePremuto
        newOrder.setOnAction(actionEvent -> UtilityController.changeVisibility(this.gridContainer, this.bookingTableView));

        // Aggiungiamo i button alla toolbar
        toolBar.getItems().addAll(orders, newOrder);

        newOrder.setVisible(thereIsNewOrder);

        System.out.println(thereIsNewOrder);
        return toolBar;
    }

    private void compositeBookingsTable(){
        // Creiamo le colonne della tableView
        this.orderDateColumn = new TableColumn<>("Data");
        this.orderTimeColumn = new TableColumn<>("Orario");
        this.fromColumn = new TableColumn<>("Da");
        this.toColumn = new TableColumn<>("A");
        this.customerColumn = new TableColumn<>("Cliente");
        this.customerReachment = new TableColumn<>("Tipo Raggiungimento");

        // Aggiungiamo le colonne alla tabella
        this.bookingTableView.getColumns().add(this.orderDateColumn);
        this.bookingTableView.getColumns().add(this.orderTimeColumn);
        this.bookingTableView.getColumns().add(this.fromColumn);
        this.bookingTableView.getColumns().add(this.toColumn);
        this.bookingTableView.getColumns().add(this.customerColumn);
        this.bookingTableView.getColumns().add(this.customerReachment);

        // Impostiamo la grandezza massima della tabella per ogni colonna
        this.bookingTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.orderDateColumn.setMaxWidth(Integer.MAX_VALUE * 16.6);      // 16.6%
        this.orderTimeColumn.setMaxWidth(Integer.MAX_VALUE * 16.6);      // 16.6%
        this.fromColumn.setMaxWidth(Integer.MAX_VALUE * 16.6);           // 16.6%
        this.toColumn.setMaxWidth(Integer.MAX_VALUE * 16.6);             // 16.6%
        this.customerColumn.setMaxWidth(Integer.MAX_VALUE * 16.6);       // 16.6%
        this.customerReachment.setMaxWidth(Integer.MAX_VALUE * 16.6);    // 16.6%

        // Impediamo che le tabelle possano essere riordinate dall'utente
        this.bookingTableView.skinProperty().addListener((observableValue, oldWidth, newWidth) ->{
            final TableHeaderRow header = (TableHeaderRow) bookingTableView.lookup("TableHeaderRow");

            header.reorderingProperty().addListener((obs, oldValue, newValue) -> header.setReordering(false));
        });

        // Rendiamo la tableView non editabile
        this.bookingTableView.setEditable(false);

        // Impostiamo le proprietà delle colonne
        setOrderDateColumnProperty();
        setOrderTimeColumnProperty();
        setFromColumnProperty();
        setToColumnProperty();
        setCustomerColumnProperty();
        setCustomerReachmentColumnProperty();

        // Impostiamo una larghezza base
        this.bookingTableView.setPrefWidth(2048);

        // Impostiamo gli item da visualizzare
        this.bookingTableView.setItems(this.bookings);
    }

    private void setOrderDateColumnProperty(){
        this.orderDateColumn.setCellValueFactory( bookingStringCellDataFeatures -> new SimpleStringProperty(
                bookingStringCellDataFeatures.getValue().getOrderDate().format(taxiFinderData.getDateTimeFormatter())));

        // Personalizziamo la cella e quello che vogliamo vedere
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

    private void setOrderTimeColumnProperty(){
        this.orderTimeColumn.setCellValueFactory( bookingStringCellDataFeatures -> new SimpleStringProperty(
                bookingStringCellDataFeatures.getValue().getOrderTime().format(taxiFinderData.getTimeFormatter())));

        // Personalizziamo la cella e quello che vogliamo vedere
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

    private void setFromColumnProperty(){
        this.fromColumn.setCellValueFactory( bookingStringCellDataFeatures -> new SimpleStringProperty(
                ((WaitingStation) bookingStringCellDataFeatures.getValue().getFrom()).getStationName()));

        // Personalizziamo la cella e quello che vogliamo vedere
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

    private void setToColumnProperty(){
        this.toColumn.setCellValueFactory( bookingStringCellDataFeatures -> new SimpleStringProperty(
                ((WaitingStation) bookingStringCellDataFeatures.getValue().getTo()).getStationName()));

        // Personalizziamo la cella e quello che vogliamo vedere
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

    private void setCustomerColumnProperty(){
        this.customerColumn.setCellValueFactory( bookingStringCellDataFeatures -> {
            return new SimpleStringProperty(
                        bookingStringCellDataFeatures.getValue().getCustomer().getFirstName() + " "  +
                                bookingStringCellDataFeatures.getValue().getCustomer().getLastName());

        });

        // Personalizziamo la cella e quello che vogliamo vedere
        this.customerColumn.setCellFactory(bookingStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String name, boolean empty){
                super.updateItem(name, empty);
                setText(Objects.requireNonNullElse(name, ""));
            }
        });
    }

    private void setCustomerReachmentColumnProperty(){
        this.customerReachment.setCellValueFactory( bookingStringCellDataFeatures -> {

            if (bookingStringCellDataFeatures.getValue().getArrivalModality() != null){
                return new SimpleStringProperty(bookingStringCellDataFeatures.getValue().getArrivalModality().toString());
            }
            return new SimpleStringProperty("");
        });

        // Personalizziamo la cella e quello che vogliamo vedere
        this.customerReachment.setCellFactory(bookingStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String name, boolean empty){
                super.updateItem(name, empty);
                setText(Objects.requireNonNullElse(name, ""));
            }
        });
    }

    private void compositeNewOrder(){
    }
}
