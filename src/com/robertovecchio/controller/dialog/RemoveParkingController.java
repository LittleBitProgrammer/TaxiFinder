package com.robertovecchio.controller.dialog;

import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.graph.node.Parking;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.Optional;

/**
 * Classe che gestisce la view di rimozione Parcheggio
 * @author robertovecchio
 * @version 1.0
 * @since 10/01/2021
 * */
public class RemoveParkingController {
    //==================================================
    //               Variabili d'istanza
    //==================================================

    // DB
    private final TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    //==================================================
    //               Variabili FXML
    //==================================================

    @FXML
    private ComboBox<Parking> parkingComboBox;
    @FXML
    private TextField streetNameField;
    @FXML
    private TextField streetNumberField;
    @FXML
    private TextField latitudeField;
    @FXML
    private TextField longitudeField;
    @FXML
    private TextField capacityField;

    //==================================================
    //               Inizializzazione
    //==================================================
    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize(){
        // Popoliamo la comboBox
        this.parkingComboBox.setItems(taxiFinderData.getParkings());

        // Decidiamo quante possibili righe possiamo vedere contemporaneamente
        this.parkingComboBox.setVisibleRowCount(6);

        // Impostiamo il contenuto delle celle della comboBox
        this.parkingComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Parking parking, boolean empty){
                super.updateItem(parking, empty);
                if(parking == null || empty){
                    setText(null);
                } else {
                    setText(generateString(parking));
                }
            }
        });

        // Permette di mostrare una stringa personalizzata nell'intestazione del ComboBox
        this.parkingComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Parking parking) {
                if(parking == null){
                    return null;
                } else {
                    return generateString(parking);
                }
            }

            @Override
            public Parking fromString(String s) {
                return null;
            }
        });

        // Selezioniamo il primo
        this.parkingComboBox.getSelectionModel().selectFirst();

        // Popoliamo i diversi campi con i valori del primo selezionato
        populateFields(taxiFinderData.getParkings().get(0));

        // Popoliamo i diversi campi diversamente ad ogni nuovo taxi driver selezionato
        this.parkingComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                populateFields(newValue);
            }
        });
    }

    //==================================================
    //                     Metodi
    //==================================================

    private String generateString(Parking parking){
        return String.format("%d - %s",  taxiFinderData.getParkings().indexOf(parking) + 1,
                parking.getStationName());
    }

    private void populateFields(Parking parking){
        this.streetNameField.setText(parking.getStreetName());
        this.streetNumberField.setText(parking.getStreetNumber());
        this.latitudeField.setText(String.valueOf(parking.getCoordinates().getLatitude()));
        this.longitudeField.setText(String.valueOf(parking.getCoordinates().getLongitude()));
        this.capacityField.setText(String.valueOf(parking.getParkingCapacity()));
    }

    public void processRemoveParking(){
        Parking parking = this.parkingComboBox.getSelectionModel().getSelectedItem();

        taxiFinderData.removeParking(parking);
        //taxiFinderData.getGraph().removeNode(parking);

        try {
            taxiFinderData.storeParkings();
            taxiFinderData.storeGraph();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Operazione impossibile", ButtonType.OK);
            alert.setHeaderText("Il parcheggio non può essere eliminato");
            alert.setContentText("Qualcosa è andato storto, contatta lo sviluppatore: roberto.vecchio001@studenti.uniparthenope.it");

            Optional<ButtonType> result = alert.showAndWait();
        }
    }
}
