package com.robertovecchio.controller.dialog;

import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.graph.edge.observer.Edge;
import com.robertovecchio.model.graph.node.WaitingStation;
import com.robertovecchio.model.user.TaxiDriver;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.skin.TableHeaderRow;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe che gestisce la view utile a mostrare le diverse connessioni tra postazioni e parcheggi
 * @author robertovecchio
 * @version 1.0
 * @since 14/01/2021
 * */
public class ShowConnectionsController {

    TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    // TableView
    @FXML
    TableView<Edge> connectionTable;

    // Table Column
    TableColumn<Edge, String> fromColumn;
    TableColumn<Edge, String> toColumn;

    @FXML
    public void initialize(){
        // Creiamo le colonne della tableView
        this.fromColumn = new TableColumn<>("Da");
        this.toColumn = new TableColumn<>("A");

        // Aggiungiamo le colonne alla tabella
        this.connectionTable.getColumns().add(this.fromColumn);
        this.connectionTable.getColumns().add(this.toColumn);

        // Impostiamo la grandezza massima della tabella per ogni colonna
        this.connectionTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.fromColumn.setMaxWidth(Integer.MAX_VALUE * 50D);      // 50%
        this.toColumn.setMaxWidth(Integer.MAX_VALUE * 50D);        // 50%

        // Impediamo che le tabelle possano essere riordinate dall'utente
        this.connectionTable.skinProperty().addListener((observableValue, oldWidth, newWidth) ->{
            final TableHeaderRow header = (TableHeaderRow) connectionTable.lookup("TableHeaderRow");

            header.reorderingProperty().addListener((obs, oldValue, newValue) -> header.setReordering(false));
        });

        // Rendiamo la tableView non editabile
        this.connectionTable.setEditable(false);

        // Impostiamo le proprietà delle colonne
        setFromColumnProperty();
        setToColumnProperty();
    }

    private void setFromColumnProperty(){
        this.fromColumn.setCellValueFactory(streetStringCellDataFeatures -> {
            if (streetStringCellDataFeatures.getValue().getSource() instanceof WaitingStation ){
                WaitingStation waitingStation = (WaitingStation) streetStringCellDataFeatures.getValue().getSource();
                return new SimpleStringProperty(waitingStation.getStationName());
            }
            return new SimpleStringProperty(null);
        });

        // Personalizziamo la cella e quello che vogliamo vedere
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

    private void setToColumnProperty(){
        this.toColumn.setCellValueFactory(streetStringCellDataFeatures -> {
            if (streetStringCellDataFeatures.getValue().getSource() instanceof WaitingStation ){
                WaitingStation waitingStation = (WaitingStation) streetStringCellDataFeatures.getValue().getDestination();
                return new SimpleStringProperty(waitingStation.getStationName());
            }
            return new SimpleStringProperty(null);
        });

        // Personalizziamo la cella e quello che vogliamo vedere
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



    public void initData(WaitingStation waitingStation){
        List<Edge> edges = getEdgesFromNode(waitingStation);
        this.connectionTable.setItems(FXCollections.observableList(edges));
    }

    private List<Edge> getEdgesFromNode(WaitingStation node){
        List<Edge> edges = new ArrayList<>();

        for (Edge edge : taxiFinderData.getGraph().getEdges()){
            if (edge.getSource().equals(node) || edge.getDestination().equals(node)){
                edges.add(edge);
            }
        }
        System.out.println(edges);

        return edges;
    }
}
