package com.robertovecchio.controller.dialog;

import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.graph.edge.observer.Edge;
import com.robertovecchio.model.graph.node.Parking;
import com.robertovecchio.model.graph.node.WaitingStation;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import java.io.IOException;
import java.util.Optional;

/**
 * Classe che gestisce la view di rimozione postazione
 * @author robertovecchio
 * @version 1.0
 * @since 10/01/2021
 * */
public class RemoveWaitingStationController {
    //==================================================
    //               Variabili d'istanza
    //==================================================

    // DB
    private final TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    //==================================================
    //               Variabili FXML
    //==================================================

    @FXML
    private ComboBox<WaitingStation> waitingStationComboBox;
    @FXML
    private TextField streetNameField;
    @FXML
    private TextField streetNumberField;
    @FXML
    private TextField latitudeField;
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
        // Popoliamo la comboBox
        this.waitingStationComboBox.setItems(taxiFinderData.getWaitingStations());

        // Decidiamo quante possibili righe possiamo vedere contemporaneamente
        this.waitingStationComboBox.setVisibleRowCount(6);

        // Impostiamo il contenuto delle celle della comboBox
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

        // Permette di mostrare una stringa personalizzata nell'intestazione del ComboBox
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

        // Selezioniamo il primo
        this.waitingStationComboBox.getSelectionModel().selectFirst();

        // Popoliamo i diversi campi con i valori del primo selezionato
        populateFields(taxiFinderData.getWaitingStations().get(0));

        // Popoliamo i diversi campi diversamente ad ogni nuovo taxi driver selezionato
        this.waitingStationComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                populateFields(newValue);
            }
        });
    }

    //==================================================
    //                     Metodi
    //==================================================

    private String generateString(WaitingStation waitingStation){
        return String.format("%d - %s",  taxiFinderData.getWaitingStations().indexOf(waitingStation) + 1,
                waitingStation.getStationName());
    }

    private void populateFields(WaitingStation waitingStation){
        this.streetNameField.setText(waitingStation.getStreetName());
        this.streetNumberField.setText(waitingStation.getStreetNumber());
        this.latitudeField.setText(String.valueOf(waitingStation.getCoordinates().getLatitude()));
        this.longitudeField.setText(String.valueOf(waitingStation.getCoordinates().getLongitude()));
    }

    public void processRemoveWaitingStation(){
        WaitingStation waitingStation = this.waitingStationComboBox.getSelectionModel().getSelectedItem();

        taxiFinderData.removeWaitingStation(waitingStation);
        taxiFinderData.getGraph().getVertexes().remove(waitingStation);

        taxiFinderData.getGraph().getEdges().removeIf(edge -> edge.getSource().equals(waitingStation));

        try {
            taxiFinderData.storeWaitingStations();
            taxiFinderData.storeGraph();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Operazione impossibile", ButtonType.OK);
            alert.setHeaderText("Il parcheggio non può essere eliminato");
            alert.setContentText("Qualcosa è andato storto, contatta lo sviluppatore: roberto.vecchio001@studenti.uniparthenope.it");

            Optional<ButtonType> result = alert.showAndWait();
        }
    }
}
