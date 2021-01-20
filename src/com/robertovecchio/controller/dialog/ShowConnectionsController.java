package com.robertovecchio.controller.dialog;

import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.graph.edge.observer.Edge;
import com.robertovecchio.model.graph.node.WaitingStation;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.skin.TableHeaderRow;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che gestisce la view (dialog) utile a mostrare le diverse connessioni tra postazioni e parcheggi
 * @author robertovecchio
 * @version 1.0
 * @since 14/01/2021
 * */
public class ShowConnectionsController {

    // DB
    /**
     * Istanza del database
     * @see TaxiFinderData
     * */
    TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    // TableView
    /**
     * TableView dei collegamenti tra il nodo seleziato e gli altri
     * @see TableView
     * @see Edge
     */
    @FXML
    TableView<Edge> connectionTable;

    // Table Column
    /**
     * TableColumn (colonna) delle sorgenti del collegamento
     * @see TableColumn
     * @see Edge
     */
    TableColumn<Edge, String> fromColumn;
    /**
     * TableColumn (colonna) delle destinazioni del collegamento
     * @see TableColumn
     * @see Edge
     * */
    TableColumn<Edge, String> toColumn;

    //==================================================
    //               Inizializzazione
    //==================================================
    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize(){

        /* Creiamo le colonne della tableView */

        /* Da */
        this.fromColumn = new TableColumn<>("Da");
        /* A */
        this.toColumn = new TableColumn<>("A");

        /* Aggiungiamo le colonne alla tabella */

        /* Da */
        this.connectionTable.getColumns().add(this.fromColumn);
        /* A */
        this.connectionTable.getColumns().add(this.toColumn);

        /* Impostiamo la grandezza massima della tabella per ogni colonna */
        this.connectionTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.fromColumn.setMaxWidth(Integer.MAX_VALUE * 50D);      // 50%
        this.toColumn.setMaxWidth(Integer.MAX_VALUE * 50D);        // 50%

        /* Impediamo che le tabelle possano essere riordinate dall'utente */
        this.connectionTable.skinProperty().addListener((observableValue, oldWidth, newWidth) ->{
            final TableHeaderRow header = (TableHeaderRow) connectionTable.lookup("TableHeaderRow");

            header.reorderingProperty().addListener((obs, oldValue, newValue) -> header.setReordering(false));
        });

        /* Rendiamo la tableView non editabile */
        this.connectionTable.setEditable(false);

        /* Impostiamo le proprietà delle colonne */

        /* Da */
        setFromColumnProperty();
        /* A */
        setToColumnProperty();
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna della sorgente del collegamento
     * */
    private void setFromColumnProperty(){
        this.fromColumn.setCellValueFactory(streetStringCellDataFeatures -> {
            if (streetStringCellDataFeatures.getValue().getSource() instanceof WaitingStation ){
                WaitingStation waitingStation = (WaitingStation) streetStringCellDataFeatures.getValue().getSource();
                return new SimpleStringProperty(waitingStation.getStationName());
            }
            return new SimpleStringProperty(null);
        });

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.fromColumn.setCellFactory(streetStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String stationName, boolean empty){
                super.updateItem(stationName, empty);
                if(empty || stationName == null){
                    setText(null);
                } else {
                    setText(stationName);
                }
            }
        });
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna della destinazione del collegmaento
     * */
    private void setToColumnProperty(){
        this.toColumn.setCellValueFactory(streetStringCellDataFeatures -> {
            if (streetStringCellDataFeatures.getValue().getSource() instanceof WaitingStation ){
                WaitingStation waitingStation = (WaitingStation) streetStringCellDataFeatures.getValue().getDestination();
                return new SimpleStringProperty(waitingStation.getStationName());
            }
            return new SimpleStringProperty(null);
        });

        /* Personalizziamo la cella e quello che vogliamo vedere */
        this.toColumn.setCellFactory(streetStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String stationName, boolean empty){
                super.updateItem(stationName, empty);
                if(empty || stationName == null){
                    setText(null);
                } else {
                    setText(stationName);
                }
            }
        });
    }

    /**
     * Questo metodo è utile a popolare la TableView dei collegamenti
     * @param waitingStation Postazione di cui si vogliono conoscere i collegamenti
     * @see WaitingStation
     */
    public void initData(WaitingStation waitingStation){
        List<Edge> edges = taxiFinderData.getEdgesFromNode(waitingStation);
        this.connectionTable.setItems(FXCollections.observableList(edges));
    }
}
