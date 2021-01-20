package com.robertovecchio.controller.dialog;

import com.robertovecchio.model.booking.Booking;
import com.robertovecchio.model.booking.OrderState;
import com.robertovecchio.model.booking.OrderType;
import com.robertovecchio.model.booking.strategy.EmailStrategy;
import com.robertovecchio.model.booking.strategy.SmsStrategy;
import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.graph.node.Parking;
import com.robertovecchio.model.graph.node.WaitingStation;
import com.robertovecchio.model.user.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.util.StringConverter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;

/**
 * Classe che gestisce la view (dialog) di creazione di un ordine
 * @author robertovecchio
 * @version 1.0
 * @since 10/01/2021
 * */
public class OrderController {
    //==================================================
    //               Variabili d'istanza
    //==================================================

    //DB
    /**
     * Istanza del database
     * @see TaxiFinderData
     * */
    private final TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    // ObservableList
    /**
     * Lista osservabile delle postazioni
     * @see ObservableList
     * @see WaitingStation
     * */
    ObservableList<WaitingStation> stations;

    //==================================================
    //               Variabili FXML
    //==================================================

    /**
     * ComboBox utile a mostrare le possibili postazioni da cui è possibile partire
     * @see ComboBox
     * @see WaitingStation
     */
    @FXML
    ComboBox<WaitingStation> fromComboBox;
    /**
     * ComboBox utile a mostrare le possibili postazioni a cui è possibile arrivare
     * @see ComboBox
     * @see WaitingStation
     */
    @FXML
    ComboBox<WaitingStation> toComboBox;

    //==================================================
    //               Inizializzazione
    //==================================================

    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize(){
        /* Inizializziamo le Collections */
        stations = FXCollections.observableArrayList();
        stations.addAll(taxiFinderData.getWaitingStations());
        stations.addAll(taxiFinderData.getParkings());

        /* Inizializziamo le comboBox */
        this.fromComboBox.setItems(stations);
        this.toComboBox.setItems(stations);

        /* Impostiamo il contenuto delle celle della comboBox */
        this.fromComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(WaitingStation waitingStation, boolean empty){
                super.updateItem(waitingStation, empty);
                if(waitingStation == null || empty){
                    setText(null);
                } else {
                    setText(generateString(waitingStation));
                }
            }
        });

        /* Impostiamo il contenuto delle celle della comboBox */
        this.toComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(WaitingStation waitingStation, boolean empty){
                super.updateItem(waitingStation, empty);
                if(waitingStation == null || empty){
                    setText(null);
                } else {
                    setText(generateString(waitingStation));
                }
            }
        });

        /* Permettiamo di mostrare una stringa personalizzata nell'intestazione della ComboBox  */
        this.fromComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(WaitingStation waitingStation) {
                if(waitingStation == null){
                    return null;
                } else {
                    return generateString(waitingStation);
                }
            }

            @Override
            public Parking fromString(String s) {
                return null;
            }
        });

        /* Permettiamo di mostrare una stringa personalizzata nell'intestazione della ComboBox */
        this.toComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(WaitingStation waitingStation) {
                if(waitingStation == null){
                    return null;
                } else {
                    return generateString(waitingStation);
                }
            }

            @Override
            public Parking fromString(String s) {
                return null;
            }
        });

        /* Selezioniamo il primo elemento della prima ComboBox */
        this.fromComboBox.getSelectionModel().selectFirst();

        /* Selezioniamo il secondo elemento della seconda ComboBox */
        this.toComboBox.getSelectionModel().select(1);
    }

    //==================================================
    //                     Metodi
    //==================================================

    /**
     * Metodo utile a generare una stringa da una postazione
     * @return String la quale rappresenterà una postazione
     * @param waitingStation Postazione di cui si vuole rappresentare una sua stringa
     * @see WaitingStation
     * */
    private String generateString(WaitingStation waitingStation){
        return String.format("%d - %s",  this.stations.indexOf(waitingStation) + 1,
                waitingStation.getStationName());
    }

    /**
     * Metodo utile a constatare se un cliente ha già effettuato una prenotazione per cui è in attesa
     * @return Ritorna true se non vi è alcuna prenotazione in pendenza, altrimenti false
     */
    public boolean validateOrder(){
        /* Iteriamo la lista di prenotazioni */
        for (Booking booking : taxiFinderData.getBookings()){

            /*Se vi è una prenotazione con lo stesso cliente che è ancora in attesa*/
            if (booking.getCustomer().equals(taxiFinderData.getCurrentUser()) && booking.getOrderState() == OrderState.WAITING){

                /* Ritorna false*/
                return false;
            }
        }

        /* Ritorna true*/
        return true;
    }

    /**
     * Metodo utile a constatare se l'utente ha scelto una postazione che coincide con il suo punto di arrivo
     * @return Ritorna true nel caso le due postazioni siano uguali
     */
    public boolean validateField(){
        return this.fromComboBox.getValue().equals(this.toComboBox.getValue());
    }

    //==================================================
    //        Parte inerente allo Strategy Pattern
    //==================================================

    /**
     * Metodo utile a gestire la prenotazione via SMS
     */
    public void handleSmsOrder(){
        WaitingStation first = fromComboBox.getSelectionModel().getSelectedItem();
        WaitingStation second = toComboBox.getSelectionModel().getSelectedItem();

        Customer user = (Customer) TaxiFinderData.getInstance().getCurrentUser();

        Booking booking = new Booking(LocalDate.now(), LocalTime.now(), first, second,
                                     user, OrderType.SMS);

        SmsStrategy smsStrategy = new SmsStrategy(user.getPhoneNumber());
        smsStrategy.book(booking);
    }

    /**
     * Metodo utile a gestire la prenotazione via EMAIL
     */
    public void handleEmailOrder(){
        WaitingStation first = fromComboBox.getSelectionModel().getSelectedItem();
        WaitingStation second = toComboBox.getSelectionModel().getSelectedItem();

        Customer user = (Customer) TaxiFinderData.getInstance().getCurrentUser();

        Booking booking = new Booking(LocalDate.now(), LocalTime.now(), first, second,
                user, OrderType.SMS);

        EmailStrategy emailStrategy = new EmailStrategy(user.getEmail());
        emailStrategy.book(booking);
    }
}