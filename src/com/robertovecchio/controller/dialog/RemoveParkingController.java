package com.robertovecchio.controller.dialog;

import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.graph.node.Parking;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.Optional;

/**
 * Classe che gestisce la view (dialog) di rimozione Parcheggio
 * @author robertovecchio
 * @version 1.0
 * @since 10/01/2021
 * */
public class RemoveParkingController {
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
     * ComboBox utile a mostrare la lista dei parcheggi eliminabili
     * @see ComboBox
     * @see Parking
     */
    @FXML
    private ComboBox<Parking> parkingComboBox;
    /**
     * TextField utile alla composizione del nome della strada in cui è locato il parcheggio
     * @see TextField
     */
    @FXML
    private TextField streetNameField;
    /**
     * TextField utile alla composizione del civico della strada in cui è loccato il parcheggio
     * @see TextField
     */
    @FXML
    private TextField streetNumberField;
    /**
     * TextField utile alla composizione della latitudine del parcheggio
     * @see TextField
     */
    @FXML
    private TextField latitudeField;
    /**
     * TextField utile alla composizione della longitudine del parcheggio
     * @see TextField
     */
    @FXML
    private TextField longitudeField;
    /**
     * TextField utile alla composizione della capacità del parcheggio
     * @see TextField
     */
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
        /* Popoliamo la comboBox */
        this.parkingComboBox.setItems(taxiFinderData.getParkings());

        /* Decidiamo quante possibili righe possiamo vedere contemporaneamente */
        this.parkingComboBox.setVisibleRowCount(6);

        /* Impostiamo il contenuto delle celle della comboBox */
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

        /* Permettiamo di mostrare una stringa personalizzata nell'intestazione del ComboBox */
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

        /* Selezioniamo il primo elemento dalla ComboBox */
        this.parkingComboBox.getSelectionModel().selectFirst();

        /* Popoliamo i diversi campi con i valori del primo selezionato */
        populateFields(taxiFinderData.getParkings().get(0));

        /* Popoliamo i diversi campi diversamente ad ogni nuovo taxi driver selezionato */
        this.parkingComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                populateFields(newValue);
            }
        });
    }

    //==================================================
    //                     Metodi
    //==================================================

    /**
     * Metodo utile a generare una stringa da un parcheggio
     * @return String la quale rappresenterà un parcheggio
     * @param parking Parcheggio di cui si vuole rappresentare una sua stringa
     * @see Parking
     * */
    private String generateString(Parking parking){
        return String.format("%d - %s",  taxiFinderData.getParkings().indexOf(parking) + 1,
                parking.getStationName());
    }

    /**
     * Metodo utile a popolare velocemente diverse TextField, dato un parcheggio
     * @param parking Parcheggio da cui si vogliono ottenere i dati di popolamento
     * @see Parking
     */
    private void populateFields(Parking parking){
        this.streetNameField.setText(parking.getStreetName());
        this.streetNumberField.setText(parking.getStreetNumber());
        this.latitudeField.setText(String.valueOf(parking.getCoordinates().getLatitude()));
        this.longitudeField.setText(String.valueOf(parking.getCoordinates().getLongitude()));
        this.capacityField.setText(String.valueOf(parking.getParkingCapacity()));
    }

    /**
     * Metodo utile a rimuovere un parcheggio dalla lista parcheggi presente
     */
    public void processRemoveParking(){
        /* Inizializziamo il parcheggio da rimuovere con quello appena selezionato */
        Parking parking = this.parkingComboBox.getSelectionModel().getSelectedItem();

        /* Rimuoviamo il parcheggio dalla lista dei parcheggi presenti */
        taxiFinderData.removeParking(parking);

        /* Rimuoviamo il parcheggio dal grafo corrente */
        taxiFinderData.getGraph().getVertexes().remove(parking);

        /* Rimuoviamo tutti i collegamenti in cui è presente il nodo */
        taxiFinderData.getGraph().getEdges().removeIf(edge -> edge.getSource().equals(parking) || edge.getDestination().equals(parking));

        try {
            /* Memorizziamo permanentemente i parcheggi */
            taxiFinderData.storeParkings();

            /* Memorizziamo permanentemente il grafo*/
            taxiFinderData.storeGraph();
        } catch (IOException e) {
            /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
            Alert alert = new Alert(Alert.AlertType.ERROR, "Operazione impossibile", ButtonType.OK);
            alert.setHeaderText("Il parcheggio non può essere eliminato");
            alert.setContentText("Qualcosa è andato storto, contatta lo sviluppatore: roberto.vecchio001@studenti.uniparthenope.it");

            Optional<ButtonType> result = alert.showAndWait();
        }
    }
}
