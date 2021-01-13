package com.robertovecchio.controller.dialog;

import com.robertovecchio.controller.UtilityController;
import com.robertovecchio.model.db.TaxiFinderData;
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
 * Classe che gestisce la view di aggiunta Tassista
 * @author robertovecchio
 * @version 1.0
 * @since 10/01/2021
 * */
public class AddTaxiDriverController {

    //==================================================
    //               Variabili d'istanza
    //==================================================

    // TableView
    private TableView<Taxi> tableTaxi;

    // TableColumn
    private TableColumn<Taxi,String> licensePlateColumn;
    private TableColumn<Taxi,String> brandNameColumn;
    private TableColumn<Taxi,String> modelNameColumn;
    private TableColumn<Taxi,String> capacityColumn;
    private TableColumn<Taxi,String> fuelTypeColumn;

    // Taxi
    private Taxi newTaxi;

    //==================================================
    //               Variabili Statiche
    //==================================================

    private final static String addTaxiControllerFile = "src/com/robertovecchio/view/fxml/dialog/addTaxi.fxml";

    //==================================================
    //               Variabili FXML
    //==================================================

    @FXML
    GridPane gridContainer;
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
        this.fiscalCodeField.setOnKeyReleased(keyEvent -> this.usernameField.setText(this.fiscalCodeField.getText()));

        // Creiamo la TableView
        tableTaxi = createTableView();

        // aggiungiamo la tableView al gridContainer
        this.gridContainer.add(tableTaxi,0,6,4,1);

        // Rendiamo la tableview invisibile
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
     * Questo metodo processa i dati presenti nel dialog di aggiunta di un tassista
     */
    public TaxiDriver processAddTaxiDriver(){
        String fiscalCode = fiscalCodeField.getText().trim().toUpperCase();
        String name = nameField.getText().trim().substring(0,1).toUpperCase() + nameField.getText().trim().substring(1);
        String surname = surnameField.getText().trim().substring(0,1).toUpperCase() + surnameField.getText().trim().substring(1);
        LocalDate dateOfBirth = dateOfBirthField.getValue();
        GenderType genderType = genreField.getValue();
        String email = emailField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String licenseNumber = licenseField.getText().trim().toUpperCase();

        TaxiDriver newTaxiDriver = new TaxiDriver(fiscalCode, name, surname,
                                                  dateOfBirth, genderType,
                                                  email, username,
                                                  password, licenseNumber, newTaxi);
        taxiFinderData.addTaxiDriver(newTaxiDriver);

        try {
            taxiFinderData.storeTaxiDrivers();
        }catch (IOException e){
            e.printStackTrace();
        }

        return newTaxiDriver;
    }

    /**
     * Questo metodo valida la data inserita, in quanto i minori di 18 anni non possono lavorare/guidare
     * @return ritorna true se viene inserito una persona con età > 18*/
    public boolean validateDate(){
        return !(UtilityController.getYears(this.dateOfBirthField.getValue()) < 18);
    }

    public boolean existYet(){
        TaxiDriver driver = new TaxiDriver(usernameField.getText().trim(), passwordField.getText().trim(), this.newTaxi);
        return taxiFinderData.getTaxiDrivers().contains(driver) || existTaxi(driver);
    }

    public boolean existTaxi(TaxiDriver taxiDriver){
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

        AddTaxiController addTaxiController = loader.getController();

        // Aggiungiamo il bottone OK al dialogPane
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.APPLY, ButtonType.CANCEL);
        dialog.getDialogPane().lookupButton(ButtonType.APPLY).disableProperty().bind(addTaxiController.invalidInputProperty());

        // Gestiamo il controller mostrandolo e aspettando che l'utente vi interagisca
        Optional<ButtonType> result = dialog.showAndWait();

        // Gestiamo il caso in cui l'utente abbia premuto OK
        if (result.isPresent() && result.get() == ButtonType.APPLY){
            // Inizializziamo il nuovo taxi
            // Taxi
            this.newTaxi = addTaxiController.processTaxiResult();

            // modifichiamo il testo del botton
            this.addAutoButton.setText("Modifica Auto");

            // Modifichiamo la grandezza del Button
            this.addAutoButton.setPrefWidth(150D);

            // Creiamo una lista per popolare la tableview
            List<Taxi> taxi = new ArrayList<>();
            taxi.add(newTaxi);

            // Impostiamo gli item da visualizzare, nel nostro casoil singolo taxi
            this.tableTaxi.setItems(FXCollections.observableList(taxi));

            // rendiamo la tableView Visibile
            this.tableTaxi.setVisible(true);
        }
    }

    private TableView<Taxi> createTableView(){

        // Creiamo le colonne della tableView
        this.licensePlateColumn = new TableColumn<>("Targa");
        this.brandNameColumn = new TableColumn<>("Marchio");
        this.modelNameColumn = new TableColumn<>("Modello");
        this.capacityColumn = new TableColumn<>("Capacità");
        this.fuelTypeColumn = new TableColumn<>("Carburante");

        // Inizializziamo la TableView
        this.tableTaxi = new TableView<>();

        // Aggiungiamo le colonne alla tabella
        this.tableTaxi.getColumns().add(licensePlateColumn);
        this.tableTaxi.getColumns().add(brandNameColumn);
        this.tableTaxi.getColumns().add(modelNameColumn);
        this.tableTaxi.getColumns().add(capacityColumn);
        this.tableTaxi.getColumns().add(fuelTypeColumn);

        // Impostiamo la grandezza massima della tabella per ogni colonna
        this.tableTaxi.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.licensePlateColumn.setMaxWidth(Integer.MAX_VALUE * 20D);  // 20%
        this.brandNameColumn.setMaxWidth(Integer.MAX_VALUE * 20D);     // 20%
        this.modelNameColumn.setMaxWidth(Integer.MAX_VALUE * 20D);     // 20%
        this.capacityColumn.setMaxWidth(Integer.MAX_VALUE * 20D);      // 20%
        this.fuelTypeColumn.setMaxWidth(Integer.MAX_VALUE * 20D);      // 20%

        // Impostiamo le colonne a non ordinabili
        this.licensePlateColumn.setSortable(false);
        this.brandNameColumn.setSortable(false);
        this.modelNameColumn.setSortable(false);
        this.capacityColumn.setSortable(false);
        this.fuelTypeColumn.setSortable(false);

        // Impediamo che le tabelle possano essere riordinate dall'utente
        this.tableTaxi.skinProperty().addListener((observableValue, oldWidth, newWidth) ->{
            final TableHeaderRow header = (TableHeaderRow) tableTaxi.lookup("TableHeaderRow");

            header.reorderingProperty().addListener((obs, oldValue, newValue) -> header.setReordering(false));
        });

        // Rendiamo la tableView non editabile
        this.tableTaxi.setEditable(false);

        // Impostiamo le proprietà delle colonne
        setLicensePlateColumnProperty();
        setBrandNameColumnProperty();
        setModelNameColumnProperty();
        setCapacityColumnProperty();
        setFuelTypeColumnProperty();

        return tableTaxi;
    }

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