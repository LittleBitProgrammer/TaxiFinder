package com.robertovecchio.controller.dialog;

import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.graph.node.Node;
import com.robertovecchio.model.graph.node.Parking;
import com.robertovecchio.model.user.TaxiDriver;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ShowWaitingParkingController {

    //DB
    private final TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    //==================================================
    //               Variabili FXML
    //==================================================
    @FXML
    TextField streetNumberField;
    @FXML
    TextField streetNameField;
    @FXML
    TextField stationNameField;

    //==================================================
    //               Inizializzazione
    //==================================================
    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize(){ }

    //==================================================
    //                  Metodi
    //==================================================
    public void initData(TaxiDriver taxiDriver){
        try {
            Parking parking = getParking(taxiDriver);
            this.stationNameField.setText(parking.getStationName());
            this.streetNameField.setText(parking.getStreetName());
            this.streetNumberField.setText(parking.getStreetNumber());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private Parking getParking(TaxiDriver taxiDriver) throws Exception {
        for (Node node : taxiFinderData.getGraph().getVertexes()){
            if (node instanceof Parking){
                Parking parking = (Parking) node;
                if (parking.getTaxis().contains(taxiDriver.getTaxi())){
                    return parking;
                }
            }
        }
        throw new Exception("Errore");
    }
}
