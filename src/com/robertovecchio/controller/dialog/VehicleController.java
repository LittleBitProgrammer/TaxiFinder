package com.robertovecchio.controller.dialog;

import com.robertovecchio.model.user.TaxiDriver;
import javafx.fxml.FXML;
import javafx.scene.control.DialogPane;
import javafx.scene.control.TextField;

/**
 * Classe che gestisce la view di "mostra veicolo"
 * @author robertovecchio
 * @version 1.0
 * @since 14/01/2021
 * */
public class VehicleController {

    //==================================================
    //               Variabili FXML
    //==================================================

    @FXML
    private TextField licensePlateField;
    @FXML
    private TextField brandNameField;
    @FXML
    private TextField modelNameField;
    @FXML
    private TextField capacityField;
    @FXML
    private TextField fuelTypeField;
    @FXML
    private DialogPane pane;

    //==================================================
    //               Inizializzazione
    //==================================================
    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize(){ }

    //==================================================
    //                  Metodi
    //==================================================
    public void initData(TaxiDriver taxiDriver){
        this.licensePlateField.setText(taxiDriver.getTaxi().getLicensePlate());
        this.brandNameField.setText(taxiDriver.getTaxi().getBrandType().toString());
        this.modelNameField.setText(taxiDriver.getTaxi().getModelName());
        this.capacityField.setText(String.valueOf(taxiDriver.getTaxi().getCapacity()));
        this.fuelTypeField.setText(taxiDriver.getTaxi().getFuelType().getTranslation());
    }

}
