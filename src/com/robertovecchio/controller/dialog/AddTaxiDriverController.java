package com.robertovecchio.controller.dialog;

import com.robertovecchio.controller.UtilityController;
import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.user.GenderType;
import com.robertovecchio.model.user.TaxiDriver;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import java.io.FileInputStream;
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
    //               Variabili Statiche
    //==================================================

    private final static String addTaxiControllerFile = "src/com/robertovecchio/view/fxml/dialog/addTaxi.fxml";

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
    @FXML
    Button addAutoButton;

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
        Set<GenderType> genders = EnumSet.of(GenderType.MALE, GenderType.FEMALE);
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

        // Impostiamo un dominio di base
        this.emailField.setText("@taxifinder.com");

        // Impostiamo l'username non editabile
        this.usernameField.setEditable(false);


        // Aggiungiamo un listner al fiscalCodeField
        this.fiscalCodeField.setOnKeyReleased(keyEvent -> {
            this.usernameField.setText(this.fiscalCodeField.getText());
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

    /**
     * Questo metodo processa i dati presenti nel dialog di aggiunta di un tassista
     */
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
                                                    password, licenseNumber, null));
        try {
            taxiFinderData.storeTaxiDrivers();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Questo metodo valida la data inserita, in quanto i minori di 18 anni non possono lavorare/guidare
     * @return ritorna true se viene inserito una persona con età > 18*/
    public boolean validateDate(){
        return !(UtilityController.getYears(this.dateOfBirthField.getValue()) < 18);
    }

    /**
     * Questo metodo valida l'email attraverso l'utilizzo di un regex pattern
     * @return Ritorna true se è una email valida, altrimenti false
     * */
    public boolean validateEmail(){
        return UtilityController.isValidEmailAddress(this.emailField.getText().trim());
    }

    /**
     * Questo metodo valida la password inserita
     * @return Ritorna true se è una password valida, altrimenti false
     * */
    public boolean validatePassword(){
        return UtilityController.isValidPassword(this.passwordField.getText().trim());
    }

    /**
     * Questo metodo valida il codice fiscale inserito
     * @return Ritorna true se è un codice fiscale valido, altrimenti false
     * */
    public boolean validateFiscalCode(){
        return this.fiscalCodeField.getText().length() == 16;
    }

    //==================================================
    //                  Metodi FXML
    //==================================================

    @FXML
    private void handleInsertAuto(){
        // creiamo un nuovo dialog da visualizzare
        Dialog<ButtonType> dialog = new Dialog<>();

        // inizializziamo il proprietario
        dialog.initOwner(this.addAutoButton.getScene().getWindow());

        // Impostiamo il titolo del dialog
        dialog.setTitle("Aggiunun Taxi");

        // Carichiamo il file di iterfaccia per il dialog
        FXMLLoader loader = new FXMLLoader();

        try{
            Parent root = loader.load(new FileInputStream(addTaxiControllerFile));
            dialog.getDialogPane().setContent(root);
        }catch (IOException e){
            System.out.println("Errore di caricamento dialog");
            e.printStackTrace();
        }

        // Aggiungiamo il bottone OK al dialogPane
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);

        // Gestiamo il controller mostrandolo e aspettando che l'utente vi interagisca
        Optional<ButtonType> result = dialog.showAndWait();

        // Gestiamo il caso in cui l'utente abbia premuto OK
        if (result.isPresent() && result.get() == ButtonType.APPLY){
            //stub
        }
    }
}