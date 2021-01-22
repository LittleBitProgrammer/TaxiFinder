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
 * Classe che gestisce la view (dialog) di aggiunta collegamento ai nodi del grafo
 * @author robertovecchio
 * @version 1.0
 * @since 14/01/2021
 * */
public class AddEdgeController {

    //==================================================
    //               Variabili d'istanza
    //==================================================

    // DB
    /**
     * Istanza del database
     * @see TaxiFinderData
     * */
    TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    // ObservableList
    /**
     * Lista delle stazioni che possono essere collegate
     * @see ObservableList
     * @see WaitingStation
     * */
    ObservableList<WaitingStation> stations;

    //==================================================
    //               Variabili FXML
    //==================================================

    /**
     * Combobox scelta collegamento (A partire da:)
     * @see ComboBox
     * @see WaitingStation
     * */
    @FXML
    ComboBox<WaitingStation> fromComboBox;
    /**
     * ComboBox scelta collegamento (A partire a:)
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

        /* Permette di mostrare una stringa personalizzata nell'intestazione del ComboBox */
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

        /* Permette di mostrare una stringa personalizzata nell'intestazione del ComboBox */
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

        /* Selezioniamo il primo */
        this.fromComboBox.getSelectionModel().selectFirst();
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
     * Metodo utile a validare i parametri contenuti nelle comboBox
     * @return Ritorna true se i due comboBoxSono uguali oppure se il collegamento creato dall'unione delle due
     * stazioni è già esistente, altrimenti ritorna false, per cui, nel secondo caso possiamo procedere con il
     * processare il dialog
     */
   public boolean validateFields(){
       /* Recuperiamo i valori dalle comboBox */
        WaitingStation first = fromComboBox.getSelectionModel().getSelectedItem();
        WaitingStation second = toComboBox.getSelectionModel().getSelectedItem();

        /* Inizializziamo un DistanceHandler che gestirà la distanza tra i due nodi, date le loro coordinate */
        DistanceHandler distanceHandler = new DistanceHandler(first.getCoordinates(), second.getCoordinates());

        /* Inizializziamo un Edge dati i due diversi nodi ed il loro perso, calcolato dal Distance Handler*/
        Edge edge = new Edge(first, second, distanceHandler.calculateDistance());

        /* Ritorniamo il valore opportunamente, seguendo la seguente espresione booleana */
        return this.fromComboBox.getSelectionModel().getSelectedItem().equals(this.toComboBox.getSelectionModel().getSelectedItem()) ||
                taxiFinderData.getGraph().contains(edge);
    }

    /**
     * Metodo utile a processare l'operazione per cui viene aperto il dialog associato a questa classe
     */
    public void processAddEdge(){
        /* Recuperiamo i valori dalle comboBox */
        WaitingStation first = fromComboBox.getSelectionModel().getSelectedItem();
        WaitingStation second = toComboBox.getSelectionModel().getSelectedItem();

        /* Inizializziamo un DistanceHandler che gestirà la distanza tra i due nodi, date le loro coordinate */
        DistanceHandler distanceHandler = new DistanceHandler(first.getCoordinates(), second.getCoordinates());

        /* Aggiungiamo alla lista dei collegamenti un nuovo collegamento */
        taxiFinderData.getGraph().getEdges().add(new Edge(first, second, distanceHandler.calculateDistance()));

        /* Proviamo a Memorizzare il grafo */
        try {
            taxiFinderData.storeGraph();
        }catch (IOException e){
            /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
            Alert alert = new Alert(Alert.AlertType.ERROR, "Memorizzazione impossibile", ButtonType.OK);
            alert.setHeaderText("Impossibile memorizzare");
            alert.setContentText("C'è Stato un'errore durante la memorizzazione, forse non hai opportunamente inserito" +
                    "i dati");

            Optional<ButtonType> result = alert.showAndWait();
            System.out.println(result);
        }
    }
}
