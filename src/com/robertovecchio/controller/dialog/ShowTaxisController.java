package com.robertovecchio.controller.dialog;

import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.graph.node.Parking;
import com.robertovecchio.model.user.TaxiDriver;
import com.robertovecchio.model.veichle.builderTaxi.Taxi;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.layout.HBox;

import java.util.ArrayList;
import java.util.List;

public class ShowTaxisController {
    //==================================================
    //               Variabili d'istanza
    //==================================================

    //DB
    private final TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    // Table Column
    TableColumn<TaxiDriver, String> nameSurnameColumn;
    TableColumn<TaxiDriver, String> carColumn;
    TableColumn<TaxiDriver, String> modelColumn;

    //==================================================
    //               Variabili FXML
    //==================================================

    // TableView
    @FXML
    TableView<TaxiDriver> taxisTable;

    //==================================================
    //               Inizializzazione
    //==================================================
    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize(){

        // Inizializzo le TableView
        compositetaxisTable();
    }

    private void compositetaxisTable(){
        // Creiamo le colonne della tableView
        this.nameSurnameColumn = new TableColumn<>("Autista");
        this.carColumn = new TableColumn<>("Marchio");
        this.modelColumn = new TableColumn<>("Modello");

        // Aggiungiamo le colonne alla tabella
        this.taxisTable.getColumns().add(nameSurnameColumn);
        this.taxisTable.getColumns().add(carColumn);
        this.taxisTable.getColumns().add(modelColumn);

        // Impostiamo la grandezza massima della tabella per ogni colonna
        this.taxisTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.nameSurnameColumn.setMaxWidth(Integer.MAX_VALUE * 33.33);      // 33.33%
        this.carColumn.setMaxWidth(Integer.MAX_VALUE * 33.33);              // 33.33%
        this.modelColumn.setMaxWidth(Integer.MAX_VALUE * 33.33);            // 33.33%

        // Impediamo che le tabelle possano essere riordinate dall'utente
        this.taxisTable.skinProperty().addListener((observableValue, oldWidth, newWidth) ->{
            final TableHeaderRow header = (TableHeaderRow) taxisTable.lookup("TableHeaderRow");

            header.reorderingProperty().addListener((obs, oldValue, newValue) -> header.setReordering(false));
        });

        // Rendiamo la tableView non editabile
        this.taxisTable.setEditable(false);

        // Impostiamo le proprietà delle colonne
        setNameSurnameColumnProperty();
        setCarColumnProperty();
        setModelColumnProperty();
    }

    private void setNameSurnameColumnProperty(){
        this.nameSurnameColumn.setCellValueFactory(taxiDriverStringCellDataFeatures -> new SimpleStringProperty(
                taxiDriverStringCellDataFeatures.getValue().getFirstName() + " " +
                        taxiDriverStringCellDataFeatures.getValue().getLastName()
        ));

        // Personalizziamo la cella e quello che vogliamo vedere
        this.nameSurnameColumn.setCellFactory(taxiStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String name, boolean empty){
                super.updateItem(name, empty);
                if(empty || name == null){
                    setText(null);
                } else {
                    setText(name);
                }
            }
        });
    }

    private void setCarColumnProperty(){
        this.carColumn.setCellValueFactory(taxiDriverStringCellDataFeatures -> new SimpleStringProperty(
                taxiDriverStringCellDataFeatures.getValue().getTaxi().getBrandType().toString()
        ));

        // Personalizziamo la cella e quello che vogliamo vedere
        this.carColumn.setCellFactory(taxiStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String brand, boolean empty){
                super.updateItem(brand, empty);
                if(empty || brand == null){
                    setText(null);
                } else {
                    setText(brand);
                }
            }
        });
    }

    private void setModelColumnProperty(){
        this.modelColumn.setCellValueFactory(taxiDriverStringCellDataFeatures -> new SimpleStringProperty(
                taxiDriverStringCellDataFeatures.getValue().getTaxi().getModelName()
        ));

        // Personalizziamo la cella e quello che vogliamo vedere
        this.modelColumn.setCellFactory(taxiStringTableColumn -> new TableCell<>(){
            @Override
            protected void updateItem(String model, boolean empty){
                super.updateItem(model, empty);
                if(empty || model == null){
                    setText(null);
                } else {
                    setText(model);
                }
            }
        });
    }

    public void initData(Parking parking){
        List<TaxiDriver> drivers = getTaxidrivers(parking);
        this.taxisTable.setItems(FXCollections.observableList(drivers));
    }

    private List<TaxiDriver> getTaxidrivers(Parking parking){
        Parking parking1 = (Parking) taxiFinderData.getGraph().getVertexes().get(taxiFinderData.getGraph().getVertexes().indexOf(parking));
        List<TaxiDriver> taxiDrivers = new ArrayList<>();

        for (Taxi taxi : parking1.getTaxis()){
            for (TaxiDriver taxiDriver : taxiFinderData.getTaxiDrivers()){
                if (taxiDriver.getTaxi().equals(taxi)){
                    taxiDrivers.add(taxiDriver);
                }
            }
        }

        return taxiDrivers;
    }
}
