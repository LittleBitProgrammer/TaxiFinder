package com.robertovecchio.controller.dialog;

import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.graph.edge.DistanceHandler;
import com.robertovecchio.model.graph.edge.observer.Edge;
import com.robertovecchio.model.graph.node.Parking;
import com.robertovecchio.model.graph.node.WaitingStation;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.Optional;

/**
 * Classe che gestisce la view di rimozione collegamento
 * @author robertovecchio
 * @version 1.0
 * @since 14/01/2021
 * */
public class RemoveEdgeController {
    //==================================================
    //               Variabili d'istanza
    //==================================================

    // DB
    TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    //==================================================
    //               Variabili FXML
    //==================================================
    @FXML
    ComboBox<Edge> fromComboBox;

    //==================================================
    //               Inizializzazione
    //==================================================
    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize(){

        // Inizializziamo le comboBox
        this.fromComboBox.setItems(FXCollections.observableList(taxiFinderData.getGraph().getEdges()));

        // Impostiamo il contenuto delle celle della comboBox
        this.fromComboBox.setCellFactory(param -> new ListCell<>() {
            @Override
            protected void updateItem(Edge edge, boolean empty){
                super.updateItem(edge, empty);
                if(edge == null || empty){
                    setText(null);
                } else {
                    setText(generateString(edge));
                }
            }
        });

        // Permette di mostrare una stringa personalizzata nell'intestazione del ComboBox
        this.fromComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Edge edge) {
                if(edge == null){
                    return null;
                } else {
                    return generateString(edge);
                }
            }

            @Override
            public Edge fromString(String s) {
                return null;
            }
        });

        // Selezioniamo il primo
        this.fromComboBox.getSelectionModel().selectFirst();
    }

    //==================================================
    //                     Metodi
    //==================================================

    private String generateString(Edge edge){
        WaitingStation from = (WaitingStation) edge.getDestination();
        WaitingStation to = (WaitingStation) edge.getSource();

        return String.format("%d - %s => %s",  this.taxiFinderData.getGraph().getEdges().indexOf(edge) + 1,
                                               from.getStationName(),
                                               to.getStationName());
    }

    public void processRemoveEdge(){
        Edge edge = fromComboBox.getSelectionModel().getSelectedItem();

        taxiFinderData.getGraph().getEdges().remove(edge);

        try {
            taxiFinderData.storeGraph();
        }catch (IOException e){
            // Mostriamo l'errore
            Alert alert = new Alert(Alert.AlertType.ERROR, "Memorizzazione impossibile", ButtonType.OK);
            alert.setHeaderText("Impossibile memorizzare");
            alert.setContentText("C'è Stato un'errore durante la memorizzazione, forse non hai opportunamente inserito" +
                    "i dati");

            Optional<ButtonType> result = alert.showAndWait();
            System.out.println(result);
        }
    }
}
