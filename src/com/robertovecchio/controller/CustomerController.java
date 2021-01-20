package com.robertovecchio.controller;

import com.robertovecchio.controller.dialog.OrderController;
import com.robertovecchio.model.booking.Booking;
import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.graph.node.WaitingStation;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
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
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

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
    /**
     * Istanza del database
     * @see TaxiFinderData
     * */
    private final TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    // TableView
    /**
     * TableView utile a mostrare le prenotazioni del cliente
     * @see TableView
     * @see Booking
     */
    private TableView<Booking> bookingTableView;

    // TableColumn
    /**
     * TableColumn (colonna) utile a mostrare le date delle prenotazioni
     * @see TableColumn
     * @see Booking
     */
    private TableColumn<Booking, String> orderDateColumn;
    /**
     * TableColumn (colonna) utile a mostrare gli orari delle prenotazioni
     * @see TableColumn
     * @see Booking
     */
    private TableColumn<Booking, String> orderTimeColumn;
    /**
     * TableColumn (colonna) utile a mostrare le partenze delle prenotazioni
     * @see TableColumn
     * @see Booking
     */
    private TableColumn<Booking, String> fromColumn;
    /**
     * TableColumn (colonna) utile a mostrare le destinazioni delle prenotazioni
     * @see TableColumn
     * @see Booking
     */
    private TableColumn<Booking, String> toColumn;
    /**
     * TableColumn (colonna) utile a mostrare i tassisti delle prenotazioni
     * @see TableColumn
     * @see Booking
     */
    private TableColumn<Booking, String> driverColumn;
    /**
     * TableColumn (colonna) utile a mostrare gli stati delle prenotazioni
     * @see TableColumn
     * @see Booking
     */
    private TableColumn<Booking, String> stateColumn;

    // ObservableList
    /**
     * Lista osservabile di prenotazioni
     * @see ObservableList
     * @see Booking
     */
    private ObservableList<Booking> bookings;

    //==================================================
    //               Variabili Statiche
    //==================================================

    /**
     * Variabile statica che rappresenta il percorso alla view principale
     * */
    private static final String mainControllerFile = "src/com/robertovecchio/view/fxml/main.fxml";
    /**
     * Variabile statica che rappresenta il percorso al dialog degli ordini
     * */
    private static final String orderControllerFile = "src/com/robertoVecchio/view/fxml/dialog/order.fxml";

    //==================================================
    //               Variabili FXML
    //==================================================

    /**
     * Vbox della View associata
     * @see VBox
     */
    @FXML
    private VBox vBoxTopContainer;
    /**
     * StackPane della View Associata
     * @see StackPane
     */
    @FXML
    private StackPane stackContainer;

    //==================================================
    //               Inizializzazione
    //==================================================

    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize(){
        vBoxTopContainer.getChildren().addAll(compositeCustomerMenu());

        Predicate<Booking> filterdBooking = booking -> booking.getCustomer().equals(taxiFinderData.getCurrentUser());

        FilteredList<Booking> filteredList = new FilteredList<>(taxiFinderData.getBookings());
        filteredList.setPredicate(filterdBooking);

        this.bookings = filteredList;

        // Inizializzo le TableView
        compositeCustomerTable();

        // Aggiungiamo la tableView allo Stack Container
        this.stackContainer.getChildren().add(this.bookingTableView);
    }

    //==================================================
    //               Metodi
    //==================================================

    /**
     * Metodo utile a comporre un menu per il cliente
     * @return Ritorna un MEnuBar già composto
     * @see MenuBar
     */
    private MenuBar compositeCustomerMenu(){
        /* Inizializziamo il menuItem */
        MenuItem addBooking = new MenuItem("Prenota una corsa");

        /* Inizializziamo i menu */
        Menu home = new Menu();
        Menu booking = new Menu("Prenotazione");
        Menu logOut = new Menu();

        /* Creiamo delle Label da inserire nei menu home e logout */
        Label homeLabel = new Label("Home");
        Label exitLabel = new Label("Logout");

        /* Impostiamo il colore delle label a bianco */
        homeLabel.setTextFill(Color.WHITE);
        exitLabel.setTextFill(Color.WHITE);

        /* Inserisco le Label nei menu */
        home.setGraphic(homeLabel);
        logOut.setGraphic(exitLabel);

        /* Inizializziamo un menuBar */
        MenuBar menuBar = new MenuBar();

        /* Incapsuliamo i menuItem nei corrispettivi menu */
        booking.getItems().add(addBooking);

        /* Aggiungiamo un'azione quando viene premuto home */
        homeLabel.setOnMouseClicked(actionEvent -> System.out.println("Home premuto"));

        /* Aggiungiamo un'azione quando viene premuto Prenota una corsa */
        addBooking.setOnAction(actionEvent -> {
            /* creiamo un nuovo dialog da visualizzare */
            Dialog<ButtonType> dialog = new Dialog<>();

            /* inizializziamo il proprietario */
            dialog.initOwner(this.vBoxTopContainer.getScene().getWindow());

            /* Impostiamo il titolo del dialog */
            dialog.setTitle("Prenota una corsa");

            /* Carichiamo il file di iterfaccia per il dialog */
            FXMLLoader loader = new FXMLLoader();

            try{
                /* Carichiamo la view del dialog */
                Parent root = loader.load(new FileInputStream(orderControllerFile));
                dialog.getDialogPane().setContent(root);
            }catch (IOException e){
                /* Stampiamo l'errore a terminale*/
                System.out.println("Errore di caricamento dialog");
            }

            /* Creiamo i bottoni */
            ButtonType sms = new ButtonType("Prenotazione SMS", ButtonBar.ButtonData.OK_DONE);
            ButtonType email = new ButtonType("Prenotazione Email", ButtonBar.ButtonData.OK_DONE);

            /* Aggiungiamo il bottone SMS, EMAIL e CANCEL al dialogPane */
            dialog.getDialogPane().getButtonTypes().addAll(sms, email, ButtonType.CANCEL);

            /* Carichiamo il controller del dialog */
            OrderController orderController = loader.getController();

            /* Gestiamo gli Errori */
            dialog.getDialogPane().lookupButton(sms).addEventFilter(ActionEvent.ACTION,
                    event->{
                if (orderController.validateField()){
                    /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Campi uguali", ButtonType.OK);
                    alert.setHeaderText("La destinazione è identica");
                    alert.setContentText("Non puoi scegliere di andare in un luogo in cui sei già locato");

                    Optional<ButtonType> result = alert.showAndWait();
                    System.out.println(result);

                    event.consume();
                }else if (!orderController.validateOrder()){
                    /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
                    Alert alert = new Alert(Alert.AlertType.WARNING, "Numero ordini superato", ButtonType.OK);
                    alert.setHeaderText("Non puoi effettuare altri ordini");
                    alert.setContentText("Hai già effettuato un ordine per cui sei in attesa, attendi che il taxi si" +
                            "rechi sul posto");

                    Optional<ButtonType> result = alert.showAndWait();
                    System.out.println(result);

                    event.consume();
                }
            });

            dialog.getDialogPane().lookupButton(email).addEventFilter(ActionEvent.ACTION,
                    event->{
                        if (orderController.validateField()){
                            /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Campi uguali", ButtonType.OK);
                            alert.setHeaderText("La destinazione è identica");
                            alert.setContentText("Non puoi scegliere di andare in un luogo in cui sei già locato");

                            Optional<ButtonType> result = alert.showAndWait();
                            System.out.println(result);

                            event.consume();
                        }else if (!orderController.validateOrder()){
                            /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
                            Alert alert = new Alert(Alert.AlertType.WARNING, "Numero ordini superato", ButtonType.OK);
                            alert.setHeaderText("Non puoi effettuare altri ordini");
                            alert.setContentText("Hai già effettuato un ordine per cui sei in attesa, attendi che il taxi si" +
                                    "rechi sul posto");

                            Optional<ButtonType> result = alert.showAndWait();
                            System.out.println(result);

                            event.consume();
                        }
                    });

            /* Gestiamo il controller mostrandolo e aspettando che l'utente vi interagisca */
            Optional<ButtonType> result = dialog.showAndWait();
            System.out.println(result);

            /* Gestiamo il caso in cui l'utente abbia premuto prenotazione SMS ed EMAIL */
            if (result.isPresent() && result.get() == sms){
                orderController.handleSmsOrder();
            }else if (result.isPresent() && result.get() == email){
                orderController.handleEmailOrder();
            }
        });

        /* Aggiungiamo un'azione quando Logout viene premuto */
        exitLabel.setOnMouseClicked(actionEvent -> UtilityController.navigateTo(mainControllerFile, "Taxi Finder",
                "Errore reperimento interfaccia", exitLabel));

        /* incapsuliamo i diversi menu nel menuBar */
        menuBar.getMenus().addAll(home, booking, logOut);

        /* Ritorniamo il MenuBar */
        return menuBar;
    }

    /**
     * Metodo utile a comporre la tableView che mostra la lista di prenotazioni
     */
    private void compositeCustomerTable(){

        /* Creiamo le colonne della tableView */

        /* Data */
        this.orderDateColumn = new TableColumn<>("Data");
        /* Orario */
        this.orderTimeColumn = new TableColumn<>("Orario");
        /* Da */
        this.fromColumn = new TableColumn<>("Da");
        /* A */
        this.toColumn = new TableColumn<>("A");
        /* Targa Taxi */
        this.driverColumn = new TableColumn<>("Targa Veicolo");
        /* Stato Prenotazione */
        this.stateColumn = new TableColumn<>("Stato prenotazione");

        /* Inizializziamo la tableView */
        this.bookingTableView = new TableView<>();

        /* Aggiungiamo le colonne alla tabella */

        /* Data */
        this.bookingTableView.getColumns().add(this.orderDateColumn);
        /* Orario */
        this.bookingTableView.getColumns().add(this.orderTimeColumn);
        /* Da */
        this.bookingTableView.getColumns().add(this.fromColumn);
        /* A */
        this.bookingTableView.getColumns().add(this.toColumn);
        /* Targa Taxi */
        this.bookingTableView.getColumns().add(this.driverColumn);
        /* Stato Prenotazione */
        this.bookingTableView.getColumns().add(this.stateColumn);

        /* Impostiamo la grandezza massima della tabella per ogni colonna */
        this.bookingTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.orderDateColumn.setMaxWidth(Integer.MAX_VALUE * 14.28D);      // 14.28%
        this.orderTimeColumn.setMaxWidth(Integer.MAX_VALUE * 14.28D);      // 14.28%
        this.fromColumn.setMaxWidth(Integer.MAX_VALUE * 14.28D);           // 14.28%
        this.toColumn.setMaxWidth(Integer.MAX_VALUE * 14.28D);             // 14.28%
        this.driverColumn.setMaxWidth(Integer.MAX_VALUE * 14.28D);         // 14.28%
        this.stateColumn.setMaxWidth(Integer.MAX_VALUE * 14.28D);          // 14.28%

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
        /* Targa Taxi */
        setDriverColumnProperty();
        /* Stato Prenotazione */
        setStateColumnProperty();

        /* Impostiamo una larghezza base */
        this.bookingTableView.setPrefWidth(2048);

        /* Impostiamo gli item da visualizzare */
        this.bookingTableView.setItems(this.bookings);
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna delle date delle prenotazioni
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
     * Metodo utile ad impostare le proprietà della colonna degli orari delle prenotazioni
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
     * Metodo utile ad impostare le proprietà della colonna delle partenze delle prenotazioni
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
     * Metodo utile ad impostare le proprietà della colonna delle destinazioni delle prenotazioni
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
     * Metodo utile ad impostare le proprietà della colonna dei tassisti delle prenotazioni
     * */
    private void setDriverColumnProperty(){
        this.driverColumn.setCellValueFactory( bookingStringCellDataFeatures -> {
            if (bookingStringCellDataFeatures.getValue().getDriver() != null){
                return new SimpleStringProperty(
                        bookingStringCellDataFeatures.getValue().getDriver().getTaxi().getLicensePlate());
            }else {
                return new SimpleStringProperty("Attendere");
            }
        });

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.driverColumn.setCellFactory(bookingStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String licensePlate, boolean empty){
                super.updateItem(licensePlate, empty);
                setText(Objects.requireNonNullElse(licensePlate, ""));
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna degli stati delle prenotazioni
     * */
    private void setStateColumnProperty(){
        this.stateColumn.setCellValueFactory( bookingStringCellDataFeatures -> new SimpleStringProperty(
                bookingStringCellDataFeatures.getValue().getOrderState().getTranslation()));

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.stateColumn.setCellFactory(bookingStringTableColumn -> new TableCell<>(){
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
}