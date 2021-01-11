package com.robertovecchio.controller.dialog;

import com.robertovecchio.model.user.GenderType;
import javafx.fxml.FXML;
import javafx.scene.control.*;

/**
 * Classe che gestisce la view di aggiunta Tassista
 * @author robertovecchio
 * @version 1.0
 * @since 10/01/2021
 * */
public class AddTaxiDriverController {
    //==================================================
    //               Variabili FXML
    //==================================================

    @FXML
    TextField fiscalCodeField;
    @FXML
    TextField surnameField;
    @FXML
    ComboBox<GenderType> genreField;
    @FXML
    TextField usernameField;
    @FXML
    TextField nameField;
    @FXML
    DatePicker dateOfBirthField;
    @FXML
    TextField emailField;
    @FXML
    PasswordField passwordField;
    @FXML
    TextField licenseField;
}
