package com.robertovecchio.controller.dialog;

import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.graph.node.Node;
import com.robertovecchio.model.graph.node.Parking;
import com.robertovecchio.model.user.TaxiDriver;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import java.io.IOException;
import java.util.Optional;

/**
 * Classe che gestisce la view di rimozione Tassista
 * @author robertovecchio
 * @version 1.0
 * @since 10/01/2021
 * */
public class RemoveTaxiDriverController {
    //==================================================
    //               Variabili d'istanza
    //==================================================

    // DB
    /**
     * Istanza del database
     * @see TaxiFinderData
     * */
    private final TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    //==================================================
    //               Variabili FXML
    //==================================================

    /**
     * ComboBox utile a mostrare la lista dei possibili Tassisti da congedare
     * @see ComboBox
     * @see TaxiDriver
     */
    @FXML
    ComboBox<TaxiDriver> taxiDriverComboBox;
    /**
     * Textfield utile a comporre il codice fiscale del tassista da congedare
     * @see TextField
     */
    @FXML
    TextField fiscalCodeField;
    /**
     * TextField utile a comporre il nome del tassista da congedare
     * @see TextField
     */
    @FXML
    TextField nameField;
    /**
     * TextField utile a comporre il cognome del tassista da congedare
     * @see TextField
     */
    @FXML
    TextField surnameField;
    /**
     * TextField utile a comporre la data di nascita del tassista da congedare
     * @see TextField
     */
    @FXML
    DatePicker dateOfBirthField;
    /**
     * TextField utile a comporre il genere del tassista da congedare
     * @see TextField
     */
    @FXML
    TextField genreField;
    /**
     * TextField utile a comporre il numero di licenza del tassista da congedare
     * @see TextField
     */
    @FXML
    TextField licensePlateField;

    //==================================================
    //               Inizializzazione
    //==================================================

    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize(){
        /* Popoliamo la comboBox */
        this.taxiDriverComboBox.setItems(taxiFinderData.getTaxiDrivers());

        /* Decidiamo quante possibili righe possiamo vedere contemporaneamente */
        this.taxiDriverComboBox.setVisibleRowCount(6);

        /* Impostiamo il contenuto delle celle della comboBox */
        this.taxiDriverComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(TaxiDriver taxiDriver, boolean empty){
                super.updateItem(taxiDriver, empty);
                if(taxiDriver == null || empty){
                    setText(null);
                } else {
                    setText(generateContactString(taxiDriver));
                }
            }
        });

        /* Permettiamo di mostrare una stringa personalizzata nell'intestazione del ComboBox */
        this.taxiDriverComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(TaxiDriver contact) {
                if(contact == null){
                    return null;
                } else {
                    return generateContactString(contact);
                }
            }

            @Override
            public TaxiDriver fromString(String s) {
                return null;
            }
        });

        /* Selezioniamo il primo elemento dalla ComboBox */
        this.taxiDriverComboBox.getSelectionModel().selectFirst();

        /* Popoliamo i diversi campi con i valori del primo selezionato */
        populateFields(taxiFinderData.getTaxiDrivers().get(0));

        /* Popoliamo i diversi campi diversamente ad ogni nuovo taxi driver selezionato */
        this.taxiDriverComboBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null){
                populateFields(newValue);
            }
        });
    }

    //==================================================
    //                     Metodi
    //==================================================

    /**
     * Metodo utile a generare una stringa da una Tassista
     * @return String la quale rappresenterà un tassista
     * @param taxiDriver Tassista di cui si vuole rappresentare una sua stringa
     * @see TaxiDriver
     * */
    private String generateContactString(TaxiDriver taxiDriver){
        return String.format("%d - %s %s: %s",  taxiFinderData.getTaxiDrivers().indexOf(taxiDriver) + 1,
                taxiDriver.getFirstName(),
                taxiDriver.getLastName(),
                taxiDriver.getFiscalCode());
    }

    /**
     * Metodo utile a popolare velocemnte una serie di campi dato un Tassista
     * @param taxiDriver Tassista da cui si vogliono reperire i dati
     * @see TaxiDriver
     */
    private void populateFields(TaxiDriver taxiDriver){
        this.fiscalCodeField.setText(taxiDriver.getFiscalCode());
        this.nameField.setText(taxiDriver.getFirstName());
        this.surnameField.setText(taxiDriver.getLastName());
        this.dateOfBirthField.setValue(taxiDriver.getDateOfBirth());
        this.genreField.setText(taxiDriver.getGenderType().getTranslation());
        this.licensePlateField.setText(taxiDriver.getTaxi().getLicensePlate());
    }

    /**
     * Metodo che ha lo scopo di processare i dati recuperati all'interno dei vari controlli mostrati su
     * Interfaccia utente con l'obiettivo di rimuovere un tassista ed un taxi ad esso associato
     * */
    public void processFireTaxiDriver(){
        /* Memorizziamo il tassista che vogliamo congedare con quello selezionato nella ComboBox */
        TaxiDriver taxiDriver = this.taxiDriverComboBox.getSelectionModel().getSelectedItem();

        /* Rimuoviamo il tassista dalla lista dei tasisti */
        taxiFinderData.removeTaxiDriver(taxiDriver);

        for (Node node : taxiFinderData.getGraph().getVertexes()){
            if (node instanceof Parking){
                Parking parking = (Parking) node;
                taxiFinderData.getParkings().get(taxiFinderData.getParkings().indexOf(parking)).getTaxis().remove(taxiDriver.getTaxi());
                parking.getTaxis().remove(taxiDriver.getTaxi());
            }
        }

        try {
            /* Memorizziamo permanentemente i tassisti */
            taxiFinderData.storeTaxiDrivers();
            taxiFinderData.storeParkings();
            taxiFinderData.storeGraph();
        } catch (IOException e) {
            /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
            Alert alert = new Alert(Alert.AlertType.ERROR, "Operazione impossibile", ButtonType.OK);
            alert.setHeaderText("L'utente non può essere eliminato");
            alert.setContentText("Qualcosa è andato storto, contatta lo sviluppatore: roberto.vecchio001@studenti.uniparthenope.it");

            Optional<ButtonType> result = alert.showAndWait();
            System.out.println(result);
        }
    }
}
