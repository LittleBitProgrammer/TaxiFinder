package com.robertovecchio.controller.dialog;

import com.robertovecchio.model.user.TaxiDriver;
import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

/**
 * Classe che gestisce la view (dialog) utila a mostrare un veicolo di un tassista
 * @author robertovecchio
 * @version 1.0
 * @since 14/01/2021
 * */
public class VehicleController {

    //==================================================
    //               Variabili FXML
    //==================================================

    /**
     * TextField utile a comporre la targa dell'auto
     * @see TextField
     */
    @FXML
    private TextField licensePlateField;
    /**
     * TextField utile a comporre il brand del Taxi
     * @see TextField
     */
    @FXML
    private TextField brandNameField;
    /**
     * TextField utile a comporre il modello del Taxi
     * @see TextField
     */
    @FXML
    private TextField modelNameField;
    /**
     * TextField utile a comporre la capacità del Taxi in termini di persone trasportabili
     * @see TextField
     */
    @FXML
    private TextField capacityField;
    /**
     * TextField utile a comporre il tipo di carburante del Taxi
     * @see TextField
     */
    @FXML
    private TextField fuelTypeField;
    /**
     * DialogPane associato dlla view
     * @see DialogPane
     */
    @FXML
    private DialogPane pane;

    //==================================================
    //               Inizializzazione
    //==================================================
    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize(){ /* vuoto */ }

    //==================================================
    //                  Metodi
    //==================================================

    /**
     * Metodo utile a popolare velocemnte una serie di campi dato un tassista
     * @param taxiDriver Tassista da cui si vogliono reperire i dati
     * @see TaxiDriver
     */
    public void initData(TaxiDriver taxiDriver){
        this.licensePlateField.setText(taxiDriver.getTaxi().getLicensePlate());
        this.brandNameField.setText(taxiDriver.getTaxi().getBrandType().toString());
        this.modelNameField.setText(taxiDriver.getTaxi().getModelName());
        this.capacityField.setText(String.valueOf(taxiDriver.getTaxi().getCapacity()));
        this.fuelTypeField.setText(taxiDriver.getTaxi().getFuelType().getTranslation());
    }

}
