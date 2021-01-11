package com.robertovecchio.controller.dialog;

import com.robertovecchio.controller.UtilityController;
import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.user.GenderType;
import com.robertovecchio.model.user.TaxiDriver;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.EnumSet;
import java.util.Optional;
import java.util.Set;

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

    //==================================================
    //                  Attributi
    //==================================================

    TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    //==================================================
    //               Inizializzazione
    //==================================================
    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize(){
        // inizializziamo la comboBox con tutti i possibili enum
        Set<GenderType> genders = EnumSet.of(GenderType.MALE, GenderType.FEMALE, GenderType.OTHER);
        genreField.getItems().addAll(genders);

        // Permette di mostrare una stringa personalizzata nell'intestazione del ComboBox
        genreField.setConverter(new StringConverter<>() {
            @Override
            public String toString(GenderType genderType) {
                if (genderType == null){
                    return null;
                }else {
                    return genderType.getTranslation();
                }
            }

            @Override
            public GenderType fromString(String s) {
                return null;
            }
        });
    }

    //==================================================
    //                    Metodi
    //==================================================

    /**
     * Questo metodo constata che le proprietà dei vari controlli siano riempite prima di poter effettuare una
     * registrazione
     * @return Ritorna una espressione booleana osservabile che constata la qualità di quanto inserito
     * */
    public BooleanExpression invalidInputProperty(){
        return Bindings.createBooleanBinding(() -> this.fiscalCodeField.getText().trim().isEmpty() ||
                        this.nameField.getText().trim().isEmpty() ||
                        this.surnameField.getText().trim().isEmpty() ||
                        this.dateOfBirthField.getValue() == null ||
                        this.genreField.getValue() == null ||
                        this.emailField.getText().trim().isEmpty() ||
                        this.usernameField.getText().trim().isEmpty() ||
                        this.passwordField.getText().trim().isEmpty() ||
                        this.licenseField.getText().trim().isEmpty(),
                this.fiscalCodeField.textProperty(),
                this.nameField.textProperty(),
                this.surnameField.textProperty(),
                this.dateOfBirthField.valueProperty(),
                this.genreField.valueProperty(),
                this.emailField.textProperty(),
                this.usernameField.textProperty(),
                this.passwordField.textProperty(),
                this.licenseField.textProperty());
    }

    public void processAddTaxiDriver(){
        String fiscalCode = fiscalCodeField.getText().trim();
        String name = nameField.getText().trim();
        String surname = surnameField.getText().trim();
        LocalDate dateOfBirth = dateOfBirthField.getValue();
        GenderType genderType = genreField.getValue();
        String email = emailField.getText().trim();
        String username = "TF" + usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String licenseNumber = licenseField.getText().trim();

        taxiFinderData.addTaxiDriver(new TaxiDriver(fiscalCode, name, surname,
                                                    dateOfBirth, genderType,
                                                    email, username,
                                                    password, licenseNumber));
        try {
            taxiFinderData.storeTaxiDrivers();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean validateDate(){
        return !(UtilityController.getYears(this.dateOfBirthField.getValue()) < 18);
    }

    public boolean validateEmail(){
        return UtilityController.isValidEmailAddress(this.emailField.getText().trim());
    }

    public boolean validatePassword(){
        return UtilityController.isValidPassword(this.passwordField.getText().trim());
    }
}
