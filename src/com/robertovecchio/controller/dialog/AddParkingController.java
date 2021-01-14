package com.robertovecchio.controller.dialog;


import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Classe che gestisce la view di aggiunta Parcheggio
 * @author robertovecchio
 * @version 1.0
 * @since 10/01/2021
 * */
public class AddParkingController {
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
            if (!t1.matches("\\d{0,4}?")){
                capacityField.setText(s);
            }
        });
    }
}
