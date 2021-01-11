package com.robertovecchio.controller.dialog;

import com.robertovecchio.model.veichle.BrandType;
import com.robertovecchio.model.veichle.FuelType;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

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
