package com.robertovecchio.controller;

import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.user.Customer;
import com.robertovecchio.model.user.GenderType;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.util.StringConverter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.EnumSet;
import java.util.Set;

/**
 * Classe che gestisce la view della registrazione del cliente
 * @author robertovecchio
 * @version 1.0
 * @since 08/01/2021
 * */
public class CustomerRegistrationController {
    //==================================================
    //               Variabili FXML
    //==================================================

    @FXML
    Button backButton;
    @FXML
    ImageView userImage;
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
    Button registerButton;
    @FXML
    TextField phoneField;
    @FXML
    CheckBox informationField;
    @FXML
    GridPane gridContainer;

    //==================================================
    //               Variabili Statiche
    //==================================================

    private final static String loginController = "src/com/robertovecchio/view/fxml/customerLogin.fxml";
    private final static String dialogController = "src/com/robertovecchio/view/fxml/dialog/termsAndConditions.fxml";
    private final static String userLogo = "Assets/user.png";
    private final static String fontFamily = "Helvetica";
    private final static double fontSize = 15D;

    //==================================================
    //                  Variabili
    //==================================================
    private final TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    //==================================================
    //               Inizializzazione
    //==================================================

    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize(){
        try {
            // Inizializziamo il logo utente
            Image logo = new Image(new FileInputStream(userLogo));
            userImage.setImage(logo); // impostiamo l'immagine all'imageview
            userImage.setFitHeight(70); // impostiamo altezza
            userImage.setPreserveRatio(true); // impostiamo la conservazione delle proporzioni originali
        }catch (FileNotFoundException e){
            // catchiamo l'errore
            System.out.println("File Non trovato");
        }

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

        // Inizializziamo un Texflow
        TextFlow textFlow = new TextFlow();

        // Creiamo la prima parte della label "termini e condizioni"
        Text termsAndConditionFirst = new Text("Qui puoi visualizzare ");
        termsAndConditionFirst.setFont(Font.font(fontFamily, FontWeight.THIN, fontSize));

        // Creiamo la seconda parte della registrazione
        Text termsAndConditionSecond = new Text("Termini e condizioni");
        termsAndConditionSecond.setFont(Font.font(fontFamily + " bold", FontWeight.BOLD, 16));
        termsAndConditionSecond.setFill(Color.web("#1134FD"));
        termsAndConditionSecond.setId("termAndCondition");

        // aggiungiamo varie parti del testo di "termini e condizioni" al textFlow
        textFlow.getChildren().addAll(termsAndConditionFirst, termsAndConditionSecond);

        // allineiamo il texflow al centro
        textFlow.setTextAlignment(TextAlignment.CENTER);

        // aggiungiamo textflow al gridPane secondo la seguente sintassi:
        // 1 - indice di colonna
        // 2 - indice di riga
        // 3 - span colonna
        // 4 - span riga
        gridContainer.add(textFlow, 0, 2, 2,1);

        // Impostiamo un'azione da eseguire quando termini e condizioni viene cliccato
        termsAndConditionSecond.setOnMouseClicked(mouseEvent -> {
            try {
                UtilityController.showDialog(gridContainer.getScene().getWindow(),
                        "Termini e condizioni", dialogController,
                        "File interfaccia dialog termini e condizioni non trovato",
                        ()->{ System.out.println("OK Premuto da termini e condizioni");},
                        ButtonType.OK, ButtonType.CANCEL);
            }catch (Exception e){
                e.printStackTrace();
            }
        });

        // impostiamo il bottone registrati a disabilitato quando l'input utente non corrisponde ai criteri richiesti
        registerButton.disableProperty().bind(invalidInputProperty());
    }

    //==================================================
    //                Metodi FXML
    //==================================================

    /**
     * Questo metodo gestisce il caso in cui si voglia tornare indietro, verso main view
     * */
    @FXML
    private void handleBackButton(){
        UtilityController.navigateTo(loginController, "Taxi Finder",
                "Errore di caricamento file interfaccia main", backButton);
    }

    /**
     * Metodo atto alla registrazione di un utente di tipo cliente
     */
    @FXML
    private void handleRegistration(){
        //TODO:// fare controlli consistenza dei dati
        String fiscalCode = fiscalCodeField.getText().trim();
        String name = nameField.getText().trim();
        String surname = surnameField.getText().trim();
        LocalDate dateOfBirth = dateOfBirthField.getValue();
        GenderType genderType = genreField.getValue();
        String email = emailField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String phoneNumber = phoneField.getText().trim();

        taxiFinderData.addCustomer(new Customer(fiscalCode, name, surname,
                                                dateOfBirth, genderType,
                                                email, username,
                                                password, phoneNumber));
        try {
            taxiFinderData.storeCustomers();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    //==================================================
    //                    Metodi
    //==================================================

    /**
     * Questo metodo constata che le proprietà dei vari controlli siano riempite prima di poter effettuare una
     * registrazione
     * @return Ritorna una espressione booleana osservabile che constata la qualità di quanto inserito
     * */
    private BooleanExpression invalidInputProperty(){
        return Bindings.createBooleanBinding(() -> this.fiscalCodeField.getText().trim().isEmpty() ||
                                                   this.nameField.getText().trim().isEmpty() ||
                                                   this.surnameField.getText().trim().isEmpty() ||
                                                   this.dateOfBirthField.getValue() == null ||
                                                   this.genreField.getValue() == null ||
                                                   this.emailField.getText().trim().isEmpty() ||
                                                   this.usernameField.getText().trim().isEmpty() ||
                                                   this.passwordField.getText().trim().isEmpty() ||
                                                   this.phoneField.getText().trim().isEmpty() ||
                                                   !this.informationField.isSelected(),
                                                   this.fiscalCodeField.textProperty(),
                                                   this.nameField.textProperty(),
                                                   this.surnameField.textProperty(),
                                                   this.dateOfBirthField.valueProperty(),
                                                   this.genreField.valueProperty(),
                                                   this.emailField.textProperty(),
                                                   this.usernameField.textProperty(),
                                                   this.passwordField.textProperty(),
                                                   this.phoneField.textProperty(),
                                                   this.informationField.selectedProperty());
    }

    /**
     * Metodo che ricava l'età data la data di nascita
     * @param birthDate data di nascita
     * @return età*/
    private int getYears(LocalDate birthDate){
        // Ricavo la data attuale
        LocalDate now = LocalDate.now();

        // Calcolo la differenza tra i due periodi
        Period difference = Period.between(birthDate,now);

        // ritorno solo gli anni di differenza
        return difference.getYears();
    }
}