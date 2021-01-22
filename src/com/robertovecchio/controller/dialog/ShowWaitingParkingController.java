package com.robertovecchio.controller.dialog;

import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.graph.node.Node;
import com.robertovecchio.model.graph.node.Parking;
import com.robertovecchio.model.user.TaxiDriver;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * Classe che gestisce la view (dialog) utile a mostrare in quale parcheggio sosta un tassista
 * @author robertovecchio
 * @version 1.0
 * @since 15/01/2021
 * */
public class ShowWaitingParkingController {

    //DB
    /**
     * Istanza del database
     * @see TaxiFinderData
     * */
    private final TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    //==================================================
    //               Variabili FXML
    //==================================================

    /**
     * TextField utile a comporre il numero civico della strada in cui è locato il parcheggio
     * @see TextField
     */
    @FXML
    TextField streetNumberField;
    /**
     * TextField utile a comporre il nome della strada in cui è locato il parcheggio
     * @see TextField
     * */
    @FXML
    TextField streetNameField;
    /**
     * TextField utile a comporre il nome del parcheggio
     */
    @FXML
    TextField stationNameField;

    //==================================================
    //               Inizializzazione
    //==================================================

    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize(){/*VUOTO*/}

    //==================================================
    //                  Metodi
    //==================================================

    /**
     * Metodo utile a inizializzare una view dato un Taxi Driver come parametro di input
     * @param taxiDriver Tassista di cui si vuole conoscere il parcheggio di sosta
     * @see TaxiDriver
     * */
    public void initData(TaxiDriver taxiDriver){
        try {
            /* Reperiamo il parcheggio in cui è locato il Tassista*/
            Parking parking = taxiFinderData.getParkingFromTaxiDriver(taxiDriver);

            /* Popoliamo i TextField in base al Parametro di input */
            this.stationNameField.setText(parking.getStationName());
            this.streetNameField.setText(parking.getStreetName());
            this.streetNumberField.setText(parking.getStreetNumber());
        } catch (Exception e) {
            /* In caso di errore mostriamo il messaggio */
            System.out.println(e.getMessage());
        }
    }
}
