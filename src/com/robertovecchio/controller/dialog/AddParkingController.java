package com.robertovecchio.controller.dialog;


import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.graph.node.Coordinates;
import com.robertovecchio.model.graph.node.Parking;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.util.Optional;

/**
 * Classe che gestisce la view (dialog) di aggiunta di un parcheggio
 * @author robertovecchio
 * @version 1.0
 * @since 10/01/2021
 * */
public class AddParkingController {

    //==================================================
    //               Variabili d'istanza
    //==================================================

    // DB
    /**
     * Istanza del database
     * @see TaxiFinderData
     * */
    TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    //==================================================
    //               Variabili FXML
    //==================================================

    /**
     * Textfield del nome strada
     * @see TextField
     * */
    @FXML
    private TextField streetNameField;
    /**
     * Textfield del civico stradale
     * @see TextField
     */
    @FXML
    private TextField streetNumberField;
    /**
     * Textfield della latitudine
     * @see TextField
     */
    @FXML
    private TextField latitudeField;
    /**
     * TextField della longitudine
     * @see TextField
     */
    @FXML
    private TextField longitudeField;
    /**
     * TextField del nome del parcheggio
     * @see TextField
     */
    @FXML
    private TextField parkingNameField;
    /**
     * TextField della capacità del parcheggio
     * @see Parking
     */
    @FXML
    private TextField capacityField;

    //==================================================
    //               Inizializzazione
    //==================================================
    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize(){

        /* Impostiamo che la textfield latitude potrà accettare solo valori numerici */
        this.latitudeField.textProperty().addListener((observableValue, s, t1) -> {
            if (!t1.matches("\\d{0,2}([\\.]\\d{0,7})?")){
                latitudeField.setText(s);
            }
        });

        /* Impostiamo che la textfield longitude potrà accettare solo valori numerici */
        this.longitudeField.textProperty().addListener((observableValue, s, t1) -> {
            if (!t1.matches("\\d{0,2}([\\.]\\d{0,7})?")){
                longitudeField.setText(s);
            }
        });

        /* Impostiamo che la textfield capacity potrà accettare solo valori numerici */
        this.capacityField.textProperty().addListener((observableValue, s, t1) -> {
            if (!t1.matches("\\d{0,4}")){
                capacityField.setText(s);
            }
        });
    }

    /**
     * Questo metodo constata che le proprietà dei vari controlli siano riempite prima di poter effettuare una
     * registrazione
     * @return Ritorna una espressione booleana osservabile che constata la qualità di quanto inserito
     * */
    public BooleanExpression invalidInputProperty(){

        return Bindings.createBooleanBinding(() -> this.streetNameField.getText().trim().isEmpty() ||
                        this.streetNumberField.getText().trim().isEmpty() ||
                        this.latitudeField.getText().trim().isEmpty() ||
                        this.longitudeField.getText().trim().isEmpty()||
                        this.parkingNameField.getText().trim().isEmpty() ||
                        this.capacityField.getText().trim().isEmpty(),
                this.streetNameField.textProperty(),
                this.streetNumberField.textProperty(),
                this.latitudeField.textProperty(),
                this.longitudeField.textProperty(),
                this.parkingNameField.textProperty(),
                this.capacityField.textProperty());
    }

    /**
     * Metodo utile a processare l'operazione per cui viene aperto il dialog associato a questa classe
     * @return Il Parcheggio aggiunto
     * @see Parking
     */
    public Parking processAddParking(){
        /* Inizializziamo i valori delle varie TextField*/
        String streetName = streetNameField.getText().trim().substring(0,1).toUpperCase() + streetNameField.getText().trim().substring(1);
        String streetNumber = streetNumberField.getText().trim().toUpperCase();
        double latitude = Double.parseDouble(latitudeField.getText().trim());
        double longitude = Double.parseDouble(longitudeField.getText().trim());
        String parkingName = parkingNameField.getText().trim().substring(0,1).toUpperCase() + parkingNameField.getText().trim().substring(1);
        int capacity = Integer.parseInt(capacityField.getText().trim());

        /* Creiamo una nuova istanza di parcheggio con i valori recuperati dalle TextField */
        Parking newParking = new Parking(new Coordinates(latitude, longitude), streetName,
                                         streetNumber, parkingName, capacity);

        /* Aggiungiamo un parcheggio alla lista di parcheggi */
        taxiFinderData.addParking(newParking);

        /* Proviamo a salvare permanentemente la lista parcheggi ed il grafo*/
        try {
            taxiFinderData.storeParkings();
            taxiFinderData.getGraph().getVertexes().add(newParking);
            taxiFinderData.storeGraph();
        }catch (IOException e){
            /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
            Alert alert = new Alert(Alert.AlertType.ERROR, "Memorizzazione impossibile", ButtonType.OK);
            alert.setHeaderText("Impossibile memorizzare");
            alert.setContentText("C'è Stato un'errore durante la memorizzazione, forse non hai opportunamente inserito" +
                    "i dati");

            Optional<ButtonType> result = alert.showAndWait();
            System.out.println(result);
        }

        /* Ritorniamo il nuovo parcheggio inserito */
        return newParking;
    }

    /**
     * Questo metodo serve a constatare se quanto stiamo provando ad inserire in memoria, ovvero, se un nuovo parcheggio
     * sia già stato inserito
     * @return Ritorna true se è già presente in memoria tale parcheggio, altrimenti false
     */
    public boolean existYet(){
        /* Creiamo una nuova istanza di parcheggio con i valori reperiti dalle TextField corrispettive */
        Parking parking = new Parking(new Coordinates(Double.parseDouble(latitudeField.getText().trim()),
                                      Double.parseDouble(longitudeField.getText().trim())));

        /* Ritorniamo opportunamente il valore booleano, secondo il seguente utilizzo del metodo contains,
         * che sfrutta equals */
        return taxiFinderData.getParkings().contains(parking);
    }
}