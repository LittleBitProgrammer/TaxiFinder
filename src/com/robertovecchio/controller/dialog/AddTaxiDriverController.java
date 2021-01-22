package com.robertovecchio.controller.dialog;

import com.robertovecchio.controller.UtilityController;
import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.graph.node.Node;
import com.robertovecchio.model.graph.node.Parking;
import com.robertovecchio.model.user.GenderType;
import com.robertovecchio.model.user.TaxiDriver;
import com.robertovecchio.model.veichle.builderTaxi.Taxi;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.layout.GridPane;
import javafx.util.StringConverter;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

/**
 * Classe che gestisce la view (dialog) di aggiunta Tassista
 * @author robertovecchio
 * @version 1.0
 * @since 10/01/2021
 * */
public class AddTaxiDriverController {

    //==================================================
    //               Variabili d'istanza
    //==================================================

    // TableView
    /**
     * TableView utile a mostrate il Taxi Aggiunto
     * @see TableView
     * @see Taxi
     * */
    private TableView<Taxi> tableTaxi;

    // TableColumn
    /**
     * TableColumn (colonna) utile a mostrare la targa del Taxi
     * @see TableColumn
     * @see Taxi
     */
    private TableColumn<Taxi,String> licensePlateColumn;
    /**
     * TableColumn (colonna) utile a mostrare il nomi del brand del Taxi
     * @see TableColumn
     * @see Taxi
     */
    private TableColumn<Taxi,String> brandNameColumn;
    /**
     * TableColumn (colonna) utile a mostrare il modello del Taxi
     * @see TableColumn
     * @see Taxi
     */
    private TableColumn<Taxi,String> modelNameColumn;
    /**
     * TableColumn (colonna) utile a mostrare la capacità del taxi in termini di persone trasportabili
     * @see TableColumn
     * @see Taxi
     * */
    private TableColumn<Taxi,String> capacityColumn;
    /**
     * TableColumn (colonna) utile a mostrare il tipo del carburante del taxi
     * @see TableColumn
     * @see Taxi
     */
    private TableColumn<Taxi,String> fuelTypeColumn;

    // Taxi
    /**
     * Taxi da mostrare nella TableView
     * @see Taxi
     */
    private Taxi newTaxi;

    // DB
    /**
     * Istanza del database
     * @see TaxiFinderData
     * */
    TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    //==================================================
    //               Variabili Statiche
    //==================================================

    /**
     * Variabile statica che rappresenta il percorso al dialog dell'aggiunta taxi
     * */
    private final static String addTaxiControllerFile = "src/com/robertovecchio/view/fxml/dialog/addTaxi.fxml";

    //==================================================
    //               Variabili FXML
    //==================================================

    /**
     * GridPane della view associata
     * @see GridPane
     */
    @FXML
    GridPane gridContainer;
    /**
     * TextField utile alla composizione del codice fiscale
     * @see TextField
     */
    @FXML
    TextField fiscalCodeField;
    /**
     * TextField utile alla composizione del cognome
     * @see TextField
     */
    @FXML
    TextField surnameField;
    /**
     * ComboBox utile a mostrare i generi sessuali (Uomo, Donna) possibili
     * @see ComboBox
     * @see GenderType
     */
    @FXML
    ComboBox<GenderType> genreField;
    /**
     * TextField utile alla composizione dell'username, Da notare bene che su questo TextField è disabilitata la
     * modifica di default, in quanto l'username coinciderà con il corrispettivo codice fiscale. Quindi questo campo
     * verrà riempito automaticamente attraverso il riempimento della TextField del codice fiscale
     * @see TextField
     */
    @FXML
    TextField usernameField;
    /**
     * TextField utile alla composizione del nome
     * @see TextField
     */
    @FXML
    TextField nameField;
    /**
     * TextField utile alla composizione della data di nascita
     * @see TextField
     */
    @FXML
    DatePicker dateOfBirthField;
    /**
     * TextField utile alla composizione dell'email
     * @see TextField
     */
    @FXML
    TextField emailField;
    /**
     * TextField utile alla composizione della password
     * @see TextField
     */
    @FXML
    PasswordField passwordField;
    /**
     * TextField utile alla composizione del numero di licenza del tassista
     * @see TextField
     */
    @FXML
    TextField licenseField;
    /**
     * Bottone che gestirà l'aggiunta / modifica di un'auto durante l'aggiunta di un tassista. Questo potrà aprire un
     * dialog dove è possibile inserire opportunamente un Taxi associato al tassista
     * @see Button
     */
    @FXML
    Button addAutoButton;

    //==================================================
    //               Inizializzazione
    //==================================================
    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize(){
        /* inizializziamo la comboBox con tutti i possibili casi dell'enum GenderType (Uomo, Donna,
         * Altro non viene consentito perchè stiamo assumendo una persona, per cui sono necessari dati di genere
         * e non di identità */
        Set<GenderType> genders = EnumSet.of(GenderType.MALE, GenderType.FEMALE);
        genreField.getItems().addAll(genders);

        /* Permette di mostrare una stringa personalizzata nell'intestazione del ComboBox */
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

        /* Impostiamo un dominio di base */
        this.emailField.setText("@taxifinder.com");

        /* Impostiamo l'username non editabile */
        this.usernameField.setEditable(false);


        /* Aggiungiamo un listner al fiscalCodeField */
        this.fiscalCodeField.setOnKeyReleased(keyEvent -> this.usernameField.setText(this.fiscalCodeField.getText()));

        /* Creiamo la TableView */
        tableTaxi = createTableView();

        /* aggiungiamo la tableView al gridContainer */
        this.gridContainer.add(tableTaxi,0,6,4,1);

        /* Rendiamo la tableview invisibile */
        this.tableTaxi.setVisible(false);
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
                        this.licenseField.getText().trim().isEmpty() ||
                        this.addAutoButton.getText().equals("Inserisci Auto"),
                this.fiscalCodeField.textProperty(),
                this.nameField.textProperty(),
                this.surnameField.textProperty(),
                this.dateOfBirthField.valueProperty(),
                this.genreField.valueProperty(),
                this.emailField.textProperty(),
                this.usernameField.textProperty(),
                this.passwordField.textProperty(),
                this.licenseField.textProperty(),
                this.addAutoButton.textProperty());
    }

    /**
     * Metodo che ha lo scopo di processare i dati recuperati all'interno dei vari controlli mostrati su
     * Interfaccia utente, con l'obiettivo di aggiungere un nuovo tassista ed un taxi ad esso associato
     * @return Tassista aggiunto
     * @see TaxiDriver*/
    public TaxiDriver processAddTaxiDriver(){
        /* Inizializziamo i valori delle varie TextField / ComboBox / DatePicker */
        String fiscalCode = fiscalCodeField.getText().trim().toUpperCase();
        String name = nameField.getText().trim().substring(0,1).toUpperCase() + nameField.getText().trim().substring(1);
        String surname = surnameField.getText().trim().substring(0,1).toUpperCase() + surnameField.getText().trim().substring(1);
        LocalDate dateOfBirth = dateOfBirthField.getValue();
        GenderType genderType = genreField.getValue();
        String email = emailField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String licenseNumber = licenseField.getText().trim().toUpperCase();

        /* Creiamo una nuova istanza di TaxiDriver con i valori reperiti da interfaccia */
        TaxiDriver newTaxiDriver = new TaxiDriver(fiscalCode, name, surname,
                                                  dateOfBirth, genderType,
                                                  email, username,
                                                  password, licenseNumber, newTaxi);

        try {
            /* Impostiamo una variabile booleana a true che ci sarà utile durante l'iterazione */
            boolean isFull = true;

            /* Iteriamo tutti i nodi del grafo */
            for (Node parking : taxiFinderData.getGraph().getVertexes()){

                /* Prendiamo in considerazione solo le istanze di parcheggio*/
                if (parking instanceof Parking){
                    /* In questo caso siamo sicuri che il nodo corrente è un parcheggio, per cui viene effettuata
                     * Un'operazione di casting*/
                    Parking park = (Parking) parking;

                    /*Se lo spazio libero in parcheggio, in termini di posti auto, è maggiore di 0*/
                    if (park.getFreeParkingSpaces() > 0) {

                        /* Aggiungiamo un tassista (Quello creato attraverso i dati recuperati da interfaccia utente)
                         * alla lista tassisti*/
                        taxiFinderData.addTaxiDriver(newTaxiDriver);

                        /* Memorizziamo permanentemente i tassisti */
                        taxiFinderData.storeTaxiDrivers();

                        /* Inseriamo il nuovo tassista nel parcheggio */
                        taxiFinderData.insertTaxiDriverInParking(newTaxi, park);

                        /* Memorizziamo permanentemente il grafo*/
                        taxiFinderData.storeGraph();

                        /* Impostiamo is false a true */
                        isFull = false;

                        /* In questo caso non serve continuare oltre l'iterazione, quindi interrompiamola */
                        break;
                    }
                }
            }

            /* Se dopo l'iterazione isFull è ancora a true allora tutti i parcheggi appartenenti all'azienda sono
             * Pieni, per cui bisogna gestire l'eventualità*/
            if (isFull){
                /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
                Alert alert = new Alert(Alert.AlertType.WARNING, "Parcheggi pieni", ButtonType.OK);
                alert.setHeaderText("Assunzione impossibile");
                alert.setContentText("Non puoi assumere altri dipendenti, i tuoi parcheggi sono pieni");

                Optional<ButtonType> result = alert.showAndWait();
                System.out.println(result);
            }
        }catch (IOException e){
            /* In caso di errore verrà stampato a terminale */
            e.printStackTrace();
        }

        /* Ritorniamo il nuovo Taxi Creato */
        return newTaxiDriver;
    }

    /**
     * Questo metodo valida la data inserita, in quanto i minori di 18 anni non possono lavorare/guidare
     * @return ritorna true se viene inserito una persona con età > 18*/
    public boolean validateDate(){
        return !(UtilityController.getYears(this.dateOfBirthField.getValue()) < 18);
    }

    /**
     * Metodo che ha lo scopo di constatare se l'utente inserito esiste già
     * @return Se esiste già un utente torna true, altrimenti false
     * */
    public boolean existYet(){
        TaxiDriver driver = new TaxiDriver(usernameField.getText().trim(), passwordField.getText().trim(), this.newTaxi);
        return taxiFinderData.getTaxiDrivers().contains(driver) || existTaxi(driver);
    }

    /**
     * Metodo che ha lo scopo di verificare se un utente ha già inserito quel taxi (Stessa targa)
     * @param taxiDriver Tassista di cui cerchiamo l'esistenza
     * @return Se esiste già un taxi con targa uguale ritorna true, altrimenti false
     * */
    private boolean existTaxi(TaxiDriver taxiDriver){
        boolean isContained = false;
        for (TaxiDriver driver : taxiFinderData.getTaxiDrivers()){
            if (driver.getTaxi().equals(taxiDriver.getTaxi())) {
                isContained = true;
                break;
            }
        }

        return isContained;
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

    /**
     * Questo metodo gestisce associazione tra una nuova auto ed il tassista che si sta creando, lanciando un dialog
     * che gestirà l'inserimento di una nuova auto
     */
    @FXML
    private void handleInsertAuto(){
        /* creiamo un nuovo dialog da visualizzare */
        Dialog<ButtonType> dialog = new Dialog<>();

        /* inizializziamo il proprietario */
        dialog.initOwner(this.addAutoButton.getScene().getWindow());

        /* Impostiamo il titolo del dialog */
        dialog.setTitle("Aggiunun Taxi");

        /* Carichiamo il file di iterfaccia per il dialog é */
        FXMLLoader loader = new FXMLLoader();

        /* Impostiamo il contenuto del dialog */
        try{
            Parent root = loader.load(new FileInputStream(addTaxiControllerFile));
            dialog.getDialogPane().setContent(root);
        }catch (IOException e){
            System.out.println("Errore di caricamento dialog");
            e.printStackTrace();
        }

        /* Carichiamo il controller del dialog */
        AddTaxiController addTaxiController = loader.getController();

        /* Aggiungiamo il bottone Applica e Cancella al dialogPane */
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);
        dialog.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(addTaxiController.invalidInputProperty());

        /* Gestiamo il controller mostrandolo e aspettando che l'utente vi interagisca */
        Optional<ButtonType> result = dialog.showAndWait();

        /* Gestiamo il caso in cui l'utente abbia premuto Applica */
        if (result.isPresent() && result.get() == ButtonType.APPLY){
            /* Inizializziamo il nuovo Taxi */
            this.newTaxi = addTaxiController.processTaxiResult();

            /* Modifichiamo il testo del bottone */
            this.addAutoButton.setText("Modifica Auto");

            /* Modifichiamo la grandezza del Button */
            this.addAutoButton.setPrefWidth(150D);

            /* Creiamo una lista per popolare la tableview */
            List<Taxi> taxi = new ArrayList<>();

            /* Carichiamo il nuovo taxi nella lista appena creata */
            taxi.add(newTaxi);

            /* Impostiamo gli item da visualizzare, nel nostro caso il singolo taxi */
            this.tableTaxi.setItems(FXCollections.observableList(taxi));

            /* Rendiamo la tableView Visibile */
            this.tableTaxi.setVisible(true);
        }
    }

    /**
     * Metoodo utile alla creazione della TableView del Taxi, visibile un volta che il Taxi è stato opportunamente
     * creato o modificato
     * @return Una TableView di Taxi
     * @see TableView
     * @see Taxi
     * */
    private TableView<Taxi> createTableView(){

        /* Creiamo le colonne della tableView */

        /* Targa Taxi */
        this.licensePlateColumn = new TableColumn<>("Targa");
        /* Brand Taxi */
        this.brandNameColumn = new TableColumn<>("Marchio");
        /* Modello Taxi */
        this.modelNameColumn = new TableColumn<>("Modello");
        /* Capacità Taxi */
        this.capacityColumn = new TableColumn<>("Capacità");
        /* Carburante Taxi */
        this.fuelTypeColumn = new TableColumn<>("Carburante");

        /* Inizializziamo la TableView */
        this.tableTaxi = new TableView<>();

        /* Aggiungiamo le colonne alla tabella */

        /* Targa Taxi */
        this.tableTaxi.getColumns().add(licensePlateColumn);
        /* Brand Taxi */
        this.tableTaxi.getColumns().add(brandNameColumn);
        /* Modello Taxi */
        this.tableTaxi.getColumns().add(modelNameColumn);
        /* Capacità Taxi */
        this.tableTaxi.getColumns().add(capacityColumn);
        /* Carburante Taxi */
        this.tableTaxi.getColumns().add(fuelTypeColumn);

        /* Impostiamo la grandezza massima della tabella per ogni colonna */
        this.tableTaxi.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.licensePlateColumn.setMaxWidth(Integer.MAX_VALUE * 20D);  // 20%
        this.brandNameColumn.setMaxWidth(Integer.MAX_VALUE * 20D);     // 20%
        this.modelNameColumn.setMaxWidth(Integer.MAX_VALUE * 20D);     // 20%
        this.capacityColumn.setMaxWidth(Integer.MAX_VALUE * 20D);      // 20%
        this.fuelTypeColumn.setMaxWidth(Integer.MAX_VALUE * 20D);      // 20%

        /* Impostiamo le colonne a non ordinabili */

        /* Targa Taxi */
        this.licensePlateColumn.setSortable(false);
        /* Brand Taxi */
        this.brandNameColumn.setSortable(false);
        /* Modello Taxi */
        this.modelNameColumn.setSortable(false);
        /* Capacità Taxi */
        this.capacityColumn.setSortable(false);
        /* Carburante Taxi */
        this.fuelTypeColumn.setSortable(false);

        /* Impediamo che le tabelle possano essere riordinate dall'utente */
        this.tableTaxi.skinProperty().addListener((observableValue, oldWidth, newWidth) ->{
            final TableHeaderRow header = (TableHeaderRow) tableTaxi.lookup("TableHeaderRow");

            header.reorderingProperty().addListener((obs, oldValue, newValue) -> header.setReordering(false));
        });

        /* Rendiamo la tableView non editabile */
        this.tableTaxi.setEditable(false);

        /* Impostiamo le proprietà delle colonne */

        /* Targa Taxi */
        setLicensePlateColumnProperty();
        /* Brand Taxi */
        setBrandNameColumnProperty();
        /* Modello Taxi */
        setModelNameColumnProperty();
        /* Capacità Taxi */
        setCapacityColumnProperty();
        /* Carburante Taxi */
        setFuelTypeColumnProperty();

        /* Ritorniamo la TableView*/
        return tableTaxi;
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna della targa del taxi
     * */
    private void setLicensePlateColumnProperty(){
        this.licensePlateColumn.setCellValueFactory(taxiStringCellDataFeatures -> new SimpleStringProperty(
                taxiStringCellDataFeatures.getValue().getLicensePlate()));

        // Personalizziamo la cella e quello che vogliamo vedere
        this.licensePlateColumn.setCellFactory(taxiStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String licensePlate, boolean empty){
                super.updateItem(licensePlate, empty);
                if(empty || licensePlate == null){
                    setText(null);
                } else {
                    setText(licensePlate);
                }
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna del brand del taxi
     * */
    private void setBrandNameColumnProperty(){
        this.brandNameColumn.setCellValueFactory(taxiStringCellDataFeatures -> new SimpleStringProperty(
                taxiStringCellDataFeatures.getValue().getBrandType().toString()));

        // Personalizziamo la cella e quello che vogliamo vedere
        this.brandNameColumn.setCellFactory(taxiStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String brandName, boolean empty){
                super.updateItem(brandName, empty);
                if(empty || brandName == null){
                    setText(null);
                } else {
                    setText(brandName);
                }
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna del modello del taxi
     * */
    private void setModelNameColumnProperty(){
        this.modelNameColumn.setCellValueFactory(taxiStringCellDataFeatures -> new SimpleStringProperty(
                taxiStringCellDataFeatures.getValue().getModelName()));

        // Personalizziamo la cella e quello che vogliamo vedere
        this.modelNameColumn.setCellFactory(taxiStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String modelName, boolean empty){
                super.updateItem(modelName, empty);
                if(empty || modelName == null){
                    setText(null);
                } else {
                    setText(modelName);
                }
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna della capacità del taxi
     * */
    private void setCapacityColumnProperty(){
        this.capacityColumn.setCellValueFactory(taxiStringCellDataFeatures -> new SimpleStringProperty(
                String.valueOf(taxiStringCellDataFeatures.getValue().getCapacity())));

        // Personalizziamo la cella e quello che vogliamo vedere
        this.capacityColumn.setCellFactory(taxiStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String capacity, boolean empty){
                super.updateItem(capacity, empty);
                if(empty || capacity == null){
                    setText(null);
                } else {
                    setText(capacity);
                }
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna della tipologia carburante del taxi
     * */
    private void setFuelTypeColumnProperty(){
        this.fuelTypeColumn.setCellValueFactory(taxiStringCellDataFeatures -> new SimpleStringProperty(
                taxiStringCellDataFeatures.getValue().getFuelType().getTranslation()));

        // Personalizziamo la cella e quello che vogliamo vedere
        this.fuelTypeColumn.setCellFactory(taxiStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String fuelType, boolean empty){
                super.updateItem(fuelType, empty);
                if(empty || fuelType == null){
                    setText(null);
                } else {
                    setText(fuelType);
                }
            }
        });
    }
}