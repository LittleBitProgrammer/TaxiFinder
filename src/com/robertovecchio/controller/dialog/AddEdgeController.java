package com.robertovecchio.controller.dialog;

import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.graph.edge.DistanceHandler;
import com.robertovecchio.model.graph.node.Parking;
import com.robertovecchio.model.graph.node.WaitingStation;
import com.robertovecchio.model.graph.edge.observer.Edge;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.Optional;

/**
 * Classe che gestisce la view di aggiunta collegamento
 * @author robertovecchio
 * @version 1.0
 * @since 14/01/2021
 * */
public class AddEdgeController {
    //==================================================
    //               Variabili d'istanza
    //==================================================

    // DB
    TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

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
        this.toComboBox.getSelectionModel().selectFirst();
    }

    //==================================================
    //                     Metodi
    //==================================================

    private String generateString(WaitingStation waitingStation){
        return String.format("%d - %s",  this.stations.indexOf(waitingStation) + 1,
                waitingStation.getStationName());
    }

   public boolean validateFields(){
        WaitingStation first = fromComboBox.getSelectionModel().getSelectedItem();
        WaitingStation second = fromComboBox.getSelectionModel().getSelectedItem();

        DistanceHandler distanceHandler = new DistanceHandler(first.getCoordinates(), second.getCoordinates());

        Edge<WaitingStation> edge = new Edge<>(first, second, 10, distanceHandler.calculateDistance());
        return this.fromComboBox.getSelectionModel().getSelectedItem().equals(this.toComboBox.getSelectionModel().getSelectedItem()) ||
                taxiFinderData.getGraph().contains(edge);
    }

    public void processAddEdge(){
        WaitingStation first = fromComboBox.getSelectionModel().getSelectedItem();
        WaitingStation second = fromComboBox.getSelectionModel().getSelectedItem();

        DistanceHandler distanceHandler = new DistanceHandler(first.getCoordinates(), second.getCoordinates());

        taxiFinderData.getGraph().addEdge(first, second, 10, distanceHandler.calculateDistance());

        try {
            taxiFinderData.storeGraph();
            taxiFinderData.getGraph().printGraph();
        }catch (IOException e){
            //e.printStackTrace();
            // Mostro l'errore
            Alert alert = new Alert(Alert.AlertType.ERROR, "Memorizzazione impossibile", ButtonType.OK);
            alert.setHeaderText("Impossibile memorizzare");
            alert.setContentText("C'è Stato un'errore durante la memorizzazione, forse non hai opportunamente inserito" +
                    "i dati");

            Optional<ButtonType> result = alert.showAndWait();
            System.out.println(result);
        }
    }
}
