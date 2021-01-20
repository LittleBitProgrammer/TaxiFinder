package com.robertovecchio.controller.dialog;

import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.graph.node.Coordinates;
import com.robertovecchio.model.graph.node.WaitingStation;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Optional;

/**
 * Classe che gestisce la view (dialog) di aggiunta postazione
 * @author robertovecchio
 * @version 1.0
 * @since 10/01/2021
 * */
public class AddWaitingStationController {

    //==================================================
    //               Variabili d'istanza
    //==================================================

    // DB
    /**
     * Istanza del database
     * @see TaxiFinderData
     * */
    TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    //==================================================
    //               Variabili FXML
    //==================================================

    /**
     * TextField utile a comporre il nome della strada
     * @see TextField
     * */
    @FXML
    private TextField streetNameField;
    /**
     * TextField utile a comporre il numero civico della strada
     * @see TextField
     */
    @FXML
    private TextField streetNumberField;
    /**
     * Textfield utile a comporre la lattitudine della postazione
     * @see TextField
     * */
    @FXML
    private TextField latitudeField;
    /**
     * TextField utile a comporre la longitudine della postazione
     * @see TextField
     * */
    @FXML
    private TextField longitudeField;
    /**
     * TextField utile a comporre il nome della postazione
     * @see TextField
     * */
    @FXML
    private TextField waitingStationNameField;

    //==================================================
    //               Inizializzazione
    //==================================================

    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize(){
        /* Impostiamo che la Textfield latitude potrà accettare solo valori numerici */
        this.latitudeField.textProperty().addListener((observableValue, s, t1) -> {
            if (!t1.matches("\\d{0,2}([\\.]\\d{0,7})?")){
                latitudeField.setText(s);
            }
        });

        /* Impostiamo che la TextField longitude potrà accettare solo valori numerici */
        this.longitudeField.textProperty().addListener((observableValue, s, t1) -> {
            if (!t1.matches("\\d{0,2}([\\.]\\d{0,7})?")){
                longitudeField.setText(s);
            }
        });
    }

    /**
     * Questo metodo constata che le proprietà dei vari controlli siano riempite prima di poter effettuare una
     * registrazione
     * @return Ritorna una espressione booleana osservabile che constata la qualità di quanto inserito
     * */
    public BooleanExpression invalidInputProperty(){

        return Bindings.createBooleanBinding(() -> this.streetNameField.getText().trim().isEmpty() ||
                        this.streetNumberField.getText().trim().isEmpty() ||
                        this.latitudeField.getText().trim().isEmpty() ||
                        this.longitudeField.getText().trim().isEmpty()||
                        this.waitingStationNameField.getText().trim().isEmpty(),
                this.streetNameField.textProperty(),
                this.streetNumberField.textProperty(),
                this.latitudeField.textProperty(),
                this.longitudeField.textProperty(),
                this.waitingStationNameField.textProperty());
    }

    /**
     * Questo metodo è utile ad aggiungere una postazione a quelle già esistenti, processando i dati recuperati dall'
     * interfaccia utente
     * @return La postazione creata
     * @see WaitingStation
     */
    public WaitingStation processAddWaitingStation(){
        /* Inizializziamo i valori delle varie TextField*/
        String streetName = streetNameField.getText().trim().substring(0,1).toUpperCase() + streetNameField.getText().trim().substring(1);
        String streetNumber = streetNumberField.getText().trim().toUpperCase();
        double latitude = Double.parseDouble(latitudeField.getText().trim());
        double longitude = Double.parseDouble(longitudeField.getText().trim());
        String waitingPositionName = waitingStationNameField.getText().trim().substring(0,1).toUpperCase() + waitingStationNameField.getText().trim().substring(1);

        /* Creiamo una nuova istanza di postazione (WaitingStation) con i valori recuperati dalle TextField */
        WaitingStation waitingStation = new WaitingStation(new Coordinates(latitude, longitude), streetName,
                                                           streetNumber, waitingPositionName);

        /* Aggiungiamo la postazione creata a quelle già esisstenti */
        taxiFinderData.addWaitingStation(waitingStation);

        try {
            /* Memorizziamo permanentemente le postazioni ed il grafo*/
            taxiFinderData.storeWaitingStations();
            taxiFinderData.getGraph().getVertexes().add(waitingStation);
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

        /* Ritorniamo la postazione */
        return waitingStation;
    }

    /**
     * Questo metodo serve a constatare se quanto stiamo provando ad inserire in memoria, ovvero, se una nuova
     * postazione sia già stato inserita
     * @return Ritorna true se è già presente in memoria tale postazione, altrimenti false
     */
    public boolean existYet(){
        /* Creiamo una nuova istanza di postazione con i valori reperiti dalle TextField corrispettive */
        WaitingStation waitingStation = new WaitingStation(new Coordinates(Double.parseDouble(latitudeField.getText().trim()),
                Double.parseDouble(longitudeField.getText().trim())));

        /* Ritorniamo opportunamente il valore booleano, secondo il seguente utilizzo del metodo contains, che sfrutta
         * equals */
        return taxiFinderData.getWaitingStations().contains(waitingStation);
    }
}
