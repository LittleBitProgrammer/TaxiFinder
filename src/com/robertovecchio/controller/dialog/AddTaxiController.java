package com.robertovecchio.controller.dialog;

import com.robertovecchio.model.veichle.BrandType;
import com.robertovecchio.model.veichle.FuelType;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

/**
 * Classe che gestisce la view di aggiunta Taxi
 * @author robertovecchio
 * @version 1.0
 * @since 11/01/2021
 * */
public class AddTaxiController {

    //==================================================
    //               Variabili FXML
    //==================================================

    @FXML
    private TextField licensePlateField;
    @FXML
    private ComboBox<BrandType> brandNameField;
    @FXML
    TextField modelNameField;
    @FXML
    TextField capacityField;
    @FXML
    ComboBox<FuelType> fuelTypeField;
}
