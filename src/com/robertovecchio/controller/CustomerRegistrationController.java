package com.robertovecchio.controller;

import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.user.Customer;
import com.robertovecchio.model.user.GenderType;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
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
import java.util.EnumSet;
import java.util.Optional;
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

    /**
     * Bottone utile a gestire il caso in cui l'utente voglia tornare indietro rispetto alla view associata a questo
     * Controller
     * @see Button
     */
    @FXML
    Button backButton;
    /**
     * Immagine utile a mostrare il logo utente
     * @see ImageView
     */
    @FXML
    ImageView userImage;
    /**
     * TextField utile a comporre il codice fiscale dell'utente
     * @see TextField
     */
    @FXML
    TextField fiscalCodeField;
    /**
     * TextField utile a comporre il cognome dell'utente
     * @see TextField
     */
    @FXML
    TextField surnameField;
    /**
     * ComboBox utile a mostrare i generi in cui un utente si identifica (uomo, donna, altro)
     * @see ComboBox
     * @see GenderType
     */
    @FXML
    ComboBox<GenderType> genreField;
    /**
     * TextField utile a comporre l'username utente
     * @see TextField
     */
    @FXML
    TextField usernameField;
    /**
     * TextField utile a comporre il nome utente
     * @see TextField
     */
    @FXML
    TextField nameField;
    /**
     * DatePicker utile a comporre la data di nascita dell'utente
     * @see DatePicker
     */
    @FXML
    DatePicker dateOfBirthField;
    /**
     * TextField utile a comporre l'email utente
     * @see TextField
     */
    @FXML
    TextField emailField;
    /**
     * PasswordField utile a comporre la password utente
     * @see PasswordField
     */
    @FXML
    PasswordField passwordField;
    /**
     * Bottone utile a gestire il processo di registrazione
     * @see Button
     */
    @FXML
    Button registerButton;
    /**
     * TextField utile a comporre il numero di telefono utente
     * @see TextField
     */
    @FXML
    TextField phoneField;
    /**
     * CheckBox utile a far prendere visione l'utente dei termini e condizioni, di conseguenza anche la loro
     * accettazione
     * @see CheckBox
     */
    @FXML
    CheckBox informationField;
    /**
     * Gridpane associato alla view
     * @see GridPane
     */
    @FXML
    GridPane gridContainer;

    //==================================================
    //               Variabili Statiche
    //==================================================

    /**
     * Variabile statica che rappresenta il percorso alla view di login utente
     * */
    private final static String loginController = "src/com/robertovecchio/view/fxml/customerLogin.fxml";
    /**
     * Variabile statica che rappresenta il percorso al dialog dedito a mostrare i termini e le condizioni
     * */
    private final static String dialogController = "src/com/robertovecchio/view/fxml/dialog/termsAndConditions.fxml";
    /**
     * Variabile statica che rappresenta il percorso al logo utente
     * */
    private final static String userLogo = "Assets/user.png";
    /**
     * Variabile statica che rappresenta il font utilizzato nella view
     * */
    private final static String fontFamily = "Helvetica";
    /**
     * Variabile statica che rappresenta la grandezza del font
     * */
    private final static double fontSize = 15D;

    //==================================================
    //                  Variabili
    //==================================================
    /**
     * Istanza del database
     * @see TaxiFinderData
     * */
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
            /* Inizializziamo il logo utente */
            Image logo = new Image(new FileInputStream(userLogo));
            userImage.setImage(logo); // impostiamo l'immagine all'imageview
            userImage.setFitHeight(70); // impostiamo altezza
            userImage.setPreserveRatio(true); // impostiamo la conservazione delle proporzioni originali
        }catch (FileNotFoundException e){
            /* Nel caso di errore stampiamo nel terminale il seguente messaggio */
            System.out.println("File Non trovato");
        }

        /* Inizializziamo la comboBox con tutti i possibili casi dell' enum GenderType */
        Set<GenderType> genders = EnumSet.of(GenderType.MALE, GenderType.FEMALE, GenderType.OTHER);
        genreField.getItems().addAll(genders);

        /* Permettiamo di mostrare una stringa personalizzata nell'intestazione della ComboBox */
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

        /* Inizializziamo un Texflow */
        TextFlow textFlow = new TextFlow();

        /* Creiamo la prima parte della label "termini e condizioni" */
        Text termsAndConditionFirst = new Text("Qui puoi visualizzare ");
        termsAndConditionFirst.setFont(Font.font(fontFamily, FontWeight.THIN, fontSize));

        /* Creiamo la seconda parte della registrazione */
        Text termsAndConditionSecond = new Text("Termini e condizioni");
        termsAndConditionSecond.setFont(Font.font(fontFamily + " bold", FontWeight.BOLD, 16));
        termsAndConditionSecond.setFill(Color.web("#1134FD"));
        termsAndConditionSecond.setId("termAndCondition");

        /* aggiungiamo varie parti del testo di "termini e condizioni" al textFlow */
        textFlow.getChildren().addAll(termsAndConditionFirst, termsAndConditionSecond);

        /* allineiamo il texflow al centro */
        textFlow.setTextAlignment(TextAlignment.CENTER);

        /* aggiungiamo textflow al gridPane secondo la seguente sintassi: */

        // 1 - indice di colonna
        // 2 - indice di riga
        // 3 - span colonna
        // 4 - span riga
        gridContainer.add(textFlow, 0, 2, 2,1);

        /* Impostiamo un'azione da eseguire quando termini e condizioni viene cliccato */
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

        /* Impostiamo che la textfield potrà accettare solo valori numerici */
        this.phoneField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d{0,12}")){
                    phoneField.setText(s);
                }
            }
        });

        /* Aggiungiamo degli avvisi nel caso in cui alcuni elementi non siano validi */
        this.registerButton.addEventFilter(ActionEvent.ACTION, actionEvent -> {
            if (UtilityController.getYears(this.dateOfBirthField.getValue()) < 18){
                /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
                Alert alert = new Alert(Alert.AlertType.WARNING, "Età errata", ButtonType.OK);
                alert.setHeaderText("L'utente non può essere un minore");
                alert.setContentText("Hai inserito un utente con età inferiore ai 18 anni");

                Optional<ButtonType> result = alert.showAndWait();
                System.out.println(result);

                actionEvent.consume();
            }else if (!UtilityController.isValidEmailAddress(this.emailField.getText().trim())){
                /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
                Alert alert = new Alert(Alert.AlertType.WARNING, "Email errata", ButtonType.OK);
                alert.setHeaderText("Hai inserito una email utente errata");
                alert.setContentText("Inserisci una email valida");

                Optional<ButtonType> result = alert.showAndWait();
                System.out.println(result);

                actionEvent.consume();
            }else if (!UtilityController.isValidPassword(this.passwordField.getText().trim())){
                /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
                Alert alert = new Alert(Alert.AlertType.WARNING, "Password errata", ButtonType.OK);
                alert.setHeaderText("Hai inserito una password utente errata");
                alert.setContentText("Inserisci una password con lunghezza maggiore di 3 e minore di 15");

                Optional<ButtonType> result = alert.showAndWait();
                System.out.println(result);

                actionEvent.consume();
            }else if (!(this.fiscalCodeField.getText().length() == 16)){
                /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
                Alert alert = new Alert(Alert.AlertType.WARNING, "Codice Fiscale errato", ButtonType.OK);
                alert.setHeaderText("Hai inserito un codice fiscale errato");
                alert.setContentText("Inserisci un codice fiscale con lunghezza pari a 16");

                Optional<ButtonType> result = alert.showAndWait();
                System.out.println(result);


                actionEvent.consume();
            }else if (this.existYet()){
                /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
                Alert alert = new Alert(Alert.AlertType.WARNING, "Già esistente", ButtonType.OK);
                alert.setHeaderText("Utente o un auto esistente");
                alert.setContentText("Hai inserito un utente già inserito");

                Optional<ButtonType> result = alert.showAndWait();
                System.out.println(result);

                actionEvent.consume();
            }
        });

        /* Impostiamo il bottone registrati a disabilitato quando l'input utente non corrisponde ai criteri richiesti */
        registerButton.disableProperty().bind(invalidInputProperty());
    }

    //==================================================
    //                Metodi FXML
    //==================================================

    /**
     * Questo metodo è utile a gestire il caso in cui si voglia tornare indietro rispetto alla view a cui è associato
     * questo controller
     * */
    @FXML
    private void handleBackButton(){
        UtilityController.navigateTo(loginController, "Taxi Finder",
                "Errore di caricamento file interfaccia main", backButton);
    }

    /**
     * Metodo utile a gestire la registrazione di un utente di tipo cliente
     */
    @FXML
    private void handleRegistration(){
        /* Memorizziamo i vari dati inseriti nei diversi controlli posti su interfaccia utente */
        String fiscalCode = fiscalCodeField.getText().trim().toUpperCase();
        String name = nameField.getText().trim().substring(0,1).toUpperCase() + nameField.getText().trim().substring(1);
        String surname = surnameField.getText().trim().substring(0,1).toUpperCase() + nameField.getText().trim().substring(1);
        LocalDate dateOfBirth = dateOfBirthField.getValue();
        GenderType genderType = genreField.getValue();
        String email = emailField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String phoneNumber = phoneField.getText().trim();

        /* Aggiungiamo un cliente nella lista clienti */
        taxiFinderData.addCustomer(new Customer(fiscalCode, name, surname,
                                                dateOfBirth, genderType,
                                                email, username,
                                                password, phoneNumber));

        try {
            /* Memorizziamo permanentemente i clienti */
            taxiFinderData.storeCustomers();

            /* Mostriamo a schermo un alert che conferma l'avvenuta registrazione*/
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Utente Registrato", ButtonType.OK);
            alert.setHeaderText("Registrazione Avvenuta con successo");
            alert.setContentText("Complimenti ti sei registrato come cliente, ora verrai rimandato in area di login");

            Optional<ButtonType> result = alert.showAndWait();
            System.out.println(result);

            /* Riportiamo l'utente in schermata login*/
            UtilityController.navigateTo(loginController, "Taxi Finder", "Errore di navigazione",this.registerButton);
        }catch (IOException e){
            /* In caso di errore, lo stampiamo a terminale */
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
     * Metodo utile a constata l'esistenza del cliente nei file
     * @return Ritorna true se l'utente esiste già, altrimenti false
     */
    private boolean existYet(){
        Customer customer = new Customer(this.usernameField.getText().trim(), this.passwordField.getText().trim());
        return taxiFinderData.getCustomers().contains(customer);
    }
}