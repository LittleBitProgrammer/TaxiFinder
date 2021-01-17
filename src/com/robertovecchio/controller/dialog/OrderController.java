package com.robertovecchio.controller.dialog;

import com.robertovecchio.model.booking.Booking;
import com.robertovecchio.model.booking.OrderState;
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

public class OrderController {
    //==================================================
    //               Variabili d'istanza
    //==================================================

    //DB
    private final TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    // ObservableList
    ObservableList<WaitingStation> stations;

    //==================================================
    //               Variabili FXML
    //==================================================
    @FXML
    ComboBox<WaitingStation> fromComboBox;
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
        // Inizializziamo le Collections
        stations = FXCollections.observableArrayList();
        stations.addAll(taxiFinderData.getWaitingStations());
        stations.addAll(taxiFinderData.getParkings());

        // Inizializziamo le comboBox
        this.fromComboBox.setItems(stations);
        this.toComboBox.setItems(stations);

        // Impostiamo il contenuto delle celle della comboBox
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

        // Impostiamo il contenuto delle celle della comboBox
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

        // Permette di mostrare una stringa personalizzata nell'intestazione del ComboBox
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

        // Permette di mostrare una stringa personalizzata nell'intestazione del ComboBox
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

        // Selezioniamo il primo
        this.fromComboBox.getSelectionModel().selectFirst();
        this.toComboBox.getSelectionModel().select(1);
    }

    //==================================================
    //                     Metodi
    //==================================================

    private String generateString(WaitingStation waitingStation){
        return String.format("%d - %s",  this.stations.indexOf(waitingStation) + 1,
                waitingStation.getStationName());
    }

    public void processOrder(){
        WaitingStation first = fromComboBox.getSelectionModel().getSelectedItem();
        WaitingStation second = toComboBox.getSelectionModel().getSelectedItem();

        Booking booking = new Booking(LocalDate.now(), LocalTime.now(), first, second, (Customer) taxiFinderData.getCurrentUser());
        Customer customer = (Customer) taxiFinderData.getCurrentUser();

        // Mediator Pattern
        customer.send(booking);
    }

    public boolean validateOrder(){
        for (Booking booking : taxiFinderData.getBookings()){
            if (booking.getCustomer().equals(taxiFinderData.getCurrentUser()) && booking.getOrderState() == OrderState.WAITING){
                return false;
            }
        }

        return true;
    }

    public boolean validateField(){
        return this.fromComboBox.getValue().equals(this.toComboBox.getValue());
    }
}