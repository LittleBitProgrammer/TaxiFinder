package com.robertovecchio.controller.dialog;


import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.graph.node.Coordinates;
import com.robertovecchio.model.graph.node.Parking;
import com.robertovecchio.model.user.TaxiDriver;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.Optional;

/**
 * Classe che gestisce la view di aggiunta Parcheggio
 * @author robertovecchio
 * @version 1.0
 * @since 10/01/2021
 * */
public class AddParkingController {
    //==================================================
    //               Variabili d'istanza
    //==================================================

    // DB
    TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    //==================================================
    //               Variabili FXML
    //==================================================

    @FXML
    private TextField streetNameField;
    @FXML
    private TextField streetNumberField;
    @FXML
    private TextField latitudeField;
    @FXML
    private TextField longitudeField;
    @FXML
    private TextField parkingNameField;
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
        // Impostiamo che la textfield latitude potrà accettare solo valori numerici
        this.latitudeField.textProperty().addListener((observableValue, s, t1) -> {
            if (!t1.matches("\\d{0,2}([\\.]\\d{0,7})?")){
                latitudeField.setText(s);
            }
        });

        // Impostiamo che la textfield longitude potrà accettare solo valori numerici
        this.longitudeField.textProperty().addListener((observableValue, s, t1) -> {
            if (!t1.matches("\\d{0,2}([\\.]\\d{0,7})?")){
                longitudeField.setText(s);
            }
        });

        // Impostiamo che la textfield capacity potrà accettare solo valori numerici
        this.capacityField.textProperty().addListener((observableValue, s, t1) -> {
            if (!t1.matches("\\d{0,4}")){
                capacityField.setText(s);
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
                        this.parkingNameField.getText().trim().isEmpty() ||
                        this.capacityField.getText().trim().isEmpty(),
                this.streetNameField.textProperty(),
                this.streetNumberField.textProperty(),
                this.latitudeField.textProperty(),
                this.longitudeField.textProperty(),
                this.parkingNameField.textProperty(),
                this.capacityField.textProperty());
    }

    public Parking processAddParking(){
        String streetName = streetNameField.getText().trim().substring(0,1).toUpperCase() + streetNameField.getText().trim().substring(1);
        String streetNumber = streetNameField.getText().trim().toUpperCase();
        double latitude = Double.parseDouble(latitudeField.getText().trim());
        double longitude = Double.parseDouble(longitudeField.getText().trim());
        String parkingName = parkingNameField.getText().trim().substring(0,1).toUpperCase() + parkingNameField.getText().trim().substring(1);
        int capacity = Integer.parseInt(capacityField.getText().trim());

        Parking newParking = new Parking(new Coordinates(latitude, longitude), streetName,
                                         streetNumber, parkingName, capacity);

        taxiFinderData.addParking(newParking);

        try {
            taxiFinderData.storeParkings();
            taxiFinderData.getGraph().addNode();
        }catch (IOException e){
            // Mostro l'errore
            Alert alert = new Alert(Alert.AlertType.ERROR, "Memorizzazione impossibile", ButtonType.OK);
            alert.setHeaderText("Impossibile memorizzare");
            alert.setContentText("C'è Stato un'errore durante la memorizzazione, forse non hai opportunamente inserito" +
                    "i dati");

            Optional<ButtonType> result = alert.showAndWait();
        }

        return newParking;
    }

    public boolean existYet(){
        Parking parking = new Parking(new Coordinates(Double.parseDouble(latitudeField.getText().trim()),
                                      Double.parseDouble(longitudeField.getText().trim())));

        return taxiFinderData.getParkings().contains(parking);
    }
}
