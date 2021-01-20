package com.robertovecchio.controller.dialog;

import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.graph.node.Parking;
import com.robertovecchio.model.graph.node.WaitingStation;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import java.io.IOException;
import java.util.Optional;

/**
 * Classe che gestisce la view (dialog) di rimozione postazione
 * @author robertovecchio
 * @version 1.0
 * @since 10/01/2021
 * */
public class RemoveWaitingStationController {

    //==================================================
    //               Variabili d'istanza
    //==================================================

    // DB
    /**
     * Istanza del database
     * @see TaxiFinderData
     * */
    private final TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    //==================================================
    //               Variabili FXML
    //==================================================

    /**
     * ComboBox utile a mostrare tutte le possibili postazioni da eliminare
     * @see ComboBox
     * @see WaitingStation
     */
    @FXML
    private ComboBox<WaitingStation> waitingStationComboBox;
    /**
     * TextField utile a comporre il nome della strada in cui è locata la postazione
     * @see TextField
     * */
    @FXML
    private TextField streetNameField;
    /**
     * TextField del numero civico della strada in cui è locata la postazione
     * @see TextField
     */
    @FXML
    private TextField streetNumberField;
    /**
     * TextField della latitudine della Postazione
     * @see TextField
     */
    @FXML
    private TextField latitudeField;
    /**
     * TextField della longitudine della postazione
     * @see TextField
     */
    @FXML
    private TextField longitudeField;

    //==================================================
    //               Inizializzazione
    //==================================================

    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize() {
        /* Popoliamo la comboBox */
        this.waitingStationComboBox.setItems(taxiFinderData.getWaitingStations());

        /* Decidiamo quante possibili righe possiamo vedere contemporaneamente */
        this.waitingStationComboBox.setVisibleRowCount(6);

        /* Impostiamo il contenuto delle celle della comboBox */
        this.waitingStationComboBox.setCellFactory(param -> new ListCell<>() {
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

        /* Permettiamo di mostrare una stringa personalizzata nell'intestazione della ComboBox */
        this.waitingStationComboBox.setConverter(new StringConverter<>() {
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

        /* Selezioniamo il primo elemento della ComboBox */
        this.waitingStationComboBox.getSelectionModel().selectFirst();

        /* Popoliamo i diversi campi con i valori del primo selezionato */
        populateFields(taxiFinderData.getWaitingStations().get(0));

        /* Popoliamo i diversi campi diversamente ad ogni nuovo taxi driver selezionato */
        this.waitingStationComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                populateFields(newValue);
            }
        });
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
        return String.format("%d - %s",  taxiFinderData.getWaitingStations().indexOf(waitingStation) + 1,
                waitingStation.getStationName());
    }

    /**
     * Metodo utile a popolare velocemnte una serie di campi data una postazione
     * @param waitingStation Postazione da cui si vogliono reperire i dati
     * @see WaitingStation
     */
    private void populateFields(WaitingStation waitingStation){
        this.streetNameField.setText(waitingStation.getStreetName());
        this.streetNumberField.setText(waitingStation.getStreetNumber());
        this.latitudeField.setText(String.valueOf(waitingStation.getCoordinates().getLatitude()));
        this.longitudeField.setText(String.valueOf(waitingStation.getCoordinates().getLongitude()));
    }

    /**
     * Metodo utile alla rimozione di una postazione scelta, processando i dati provenienti da interfaccia utente
     */
    public void processRemoveWaitingStation(){

        /* Memorizziamo la postazione che vogliamo rimuovere*/
        WaitingStation waitingStation = this.waitingStationComboBox.getSelectionModel().getSelectedItem();

        /* Rimuoviamo la postazione dalla lista delle postazioni */
        taxiFinderData.removeWaitingStation(waitingStation);

        /* Rimuoviamo la postazione dal grafo */
        taxiFinderData.getGraph().getVertexes().remove(waitingStation);

        /* Rimuoviamo la postazione dai collegamenti in cui vi è presente il nodo */
        taxiFinderData.getGraph().getEdges().removeIf(edge -> edge.getSource().equals(waitingStation) || edge.getDestination().equals(waitingStation));

        try {
            /* Memorizziamo permanentemente le postazioni */
            taxiFinderData.storeWaitingStations();

            /* Memorizziamo permanentemente il grafo */
            taxiFinderData.storeGraph();
        } catch (IOException e) {
            /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
            Alert alert = new Alert(Alert.AlertType.ERROR, "Operazione impossibile", ButtonType.OK);
            alert.setHeaderText("Il parcheggio non può essere eliminato");
            alert.setContentText("Qualcosa è andato storto, contatta lo sviluppatore: roberto.vecchio001@studenti.uniparthenope.it");

            Optional<ButtonType> result = alert.showAndWait();
            System.out.println(result);
        }
    }
}
