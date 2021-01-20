package com.robertovecchio.controller.dialog;

import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.graph.edge.observer.Edge;
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
 * Classe che gestisce la view (dialog) di rimozione collegamento
 * @author robertovecchio
 * @version 1.0
 * @since 14/01/2021
 * */
public class RemoveEdgeController {
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
     * ComboBox utile a mostrare tutti i possibili collegamenti da rimuovere
     * @see ComboBox
     * @see Edge
     */
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

        /* Inizializziamo le comboBox */
        this.fromComboBox.setItems(FXCollections.observableList(taxiFinderData.getGraph().getEdges()));

        /* Impostiamo il contenuto delle celle della comboBox */
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

        /* Permettiamo di mostrare una stringa personalizzata nell'intestazione della ComboBox */
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

        /* Selezioniamo il primo elemento della ComboBox */
        this.fromComboBox.getSelectionModel().selectFirst();
    }

    //==================================================
    //                     Metodi
    //==================================================

    /**
     * Metodo utile a generare una stringa da un collegamento
     * @return String la quale rappresenterà un collegamento
     * @param edge collegamento di cui si vuole rappresentare una sua stringa
     * @see Edge
     * */
    private String generateString(Edge edge){
        /* Memorizziamo i valori selezionati dalle due ComboBox */
        WaitingStation from = (WaitingStation) edge.getSource();
        WaitingStation to = (WaitingStation) edge.getDestination();

        /* Ritorniamo una stringa */
        return String.format("%d - %s => %s",  this.taxiFinderData.getGraph().getEdges().indexOf(edge) + 1,
                                               from.getStationName(),
                                               to.getStationName());
    }

    /**
     * Metodo utile a rimuovere un Collogamento reperendo i dati da processare da interfaccia utente
     */
    public void processRemoveEdge(){
        /* Inizializziamo un Edge attraverso il valore selezionato nella ComboBox */
        Edge edge = fromComboBox.getSelectionModel().getSelectedItem();

        /* Rimuoviamo il collegamento dalla lista di collegamenti */
        taxiFinderData.getGraph().getEdges().remove(edge);

        try {
            /* Memorizziamo il grafo */
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
    }
}
