package com.robertovecchio.controller.dialog;

import com.robertovecchio.model.booking.Booking;
import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.graph.node.Node;
import com.robertovecchio.model.graph.node.Parking;
import com.robertovecchio.model.graph.node.WaitingStation;
import com.robertovecchio.model.user.TaxiDriver;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.Optional;

public class ChangeParkingController {

    //==================================================
    //               Variabili d'istanza
    //==================================================

    //DB
    private final TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    //==================================================
    //               Variabili FXML
    //==================================================
    @FXML
    private ComboBox<Parking> parkingComboBox;

    //==================================================
    //               Inizializzazione
    //==================================================
    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize() {
        this.parkingComboBox.setItems(taxiFinderData.getParkings());

        // Impostiamo il contenuto delle celle della comboBox
        this.parkingComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Parking parking, boolean empty){
                super.updateItem(parking, empty);
                if(parking == null || empty){
                    setText(null);
                } else {
                    setText(generateString(parking));
                }
            }
        });

        // Permette di mostrare una stringa personalizzata nell'intestazione del ComboBox
        this.parkingComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Parking parking) {
                if(parking == null){
                    return null;
                } else {
                    return generateString(parking);
                }
            }

            @Override
            public Parking fromString(String s) {
                return null;
            }
        });

        // Selezioniamo il primo
        this.parkingComboBox.getSelectionModel().selectFirst();
    }

    private String generateString(Parking parking){
        return String.format("%d - %s",  this.taxiFinderData.getParkings().indexOf(parking) + 1,
                parking.getStationName());
    }

    public void processChangeParking(){
        Parking changedParking = this.parkingComboBox.getValue();
        TaxiDriver taxiDriver = (TaxiDriver) taxiFinderData.getCurrentUser();

        for (Node node : taxiFinderData.getGraph().getVertexes()){
            if (node.equals(changedParking)){
                Parking parking = (Parking) node;
                parking.getTaxis().offer(taxiDriver.getTaxi());
                break;
            }
        }

        try {
            taxiFinderData.storeGraph();
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Errore", ButtonType.OK);
            alert.setHeaderText("Errore di memorizzazione");
            alert.setContentText("Impossibile memorizzare tale operazione");

            Optional<ButtonType> result = alert.showAndWait();
            System.out.println(result);

        }
    }

    public boolean validateField(){
        Parking changedParking = this.parkingComboBox.getValue();
        TaxiDriver taxiDriver = (TaxiDriver) taxiFinderData.getCurrentUser();

        for (Node node : taxiFinderData.getGraph().getVertexes()){
            if (node instanceof Parking){
                Parking parking = (Parking) node;
                if (changedParking.equals(node) && parking.getTaxis().contains(taxiDriver.getTaxi())){
                    return false;
                }
            }
        }
        return true;
    }
}
