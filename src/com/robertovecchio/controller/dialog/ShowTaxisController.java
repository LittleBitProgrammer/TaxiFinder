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
import java.util.ArrayList;
import java.util.List;

/**
 * Classe che gestisce la view (dialog) utile a mostrare i taxi in un determinato parcheggio
 * @author robertovecchio
 * @version 1.0
 * @since 15/01/2021
 * */
public class ShowTaxisController {
    //==================================================
    //               Variabili d'istanza
    //==================================================

    //DB
    /**
     * Istanza del database
     * @see TaxiFinderData
     * */
    private final TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    // Table Column
    /**
     * TableColumn (colonna) del nome e cognome del Tassista
     * @see TableColumn
     * @see TaxiDriver
     */
    TableColumn<TaxiDriver, String> nameSurnameColumn;
    /**
     * TableColumn (colonna) del brand Taxi del Tassista
     * @see TableColumn
     * @see TaxiDriver
     */
    TableColumn<TaxiDriver, String> carColumn;
    /**
     * TableColumn (colonna) del modello Taxi del Tassista
     * @see TableColumn
     * @see TaxiDriver
     */
    TableColumn<TaxiDriver, String> modelColumn;

    //==================================================
    //               Variabili FXML
    //==================================================

    // TableView
    /**
     * TableView utile a mostrare la lista di taxi/ tassisti che sostano in un determinato parcheggio
     * @see TableView
     * @see TaxiDriver
     */
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

        /* Inizializziamo le TableView */
        compositetaxisTable();
    }

    /**
     * Metodo utile a comporre la TableView dei Tassisti e Taxi associati in un determinato parcheggio
     * */
    private void compositetaxisTable(){

        /* Creiamo le colonne della tableView */

        /* Nome e cognome */
        this.nameSurnameColumn = new TableColumn<>("Autista");
        /* Brand */
        this.carColumn = new TableColumn<>("Marchio");
        /* Modello */
        this.modelColumn = new TableColumn<>("Modello");

        /* Aggiungiamo le colonne alla tabella */

        /* Nome e cognome */
        this.taxisTable.getColumns().add(nameSurnameColumn);
        /* Brand */
        this.taxisTable.getColumns().add(carColumn);
        /* Modello */
        this.taxisTable.getColumns().add(modelColumn);

        /* Impostiamo la grandezza massima della tabella per ogni colonna */
        this.taxisTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        this.nameSurnameColumn.setMaxWidth(Integer.MAX_VALUE * 33.33);      // 33.33%
        this.carColumn.setMaxWidth(Integer.MAX_VALUE * 33.33);              // 33.33%
        this.modelColumn.setMaxWidth(Integer.MAX_VALUE * 33.33);            // 33.33%

        /* Impediamo che le tabelle possano essere riordinate dall'utente */
        this.taxisTable.skinProperty().addListener((observableValue, oldWidth, newWidth) ->{
            final TableHeaderRow header = (TableHeaderRow) taxisTable.lookup("TableHeaderRow");

            header.reorderingProperty().addListener((obs, oldValue, newValue) -> header.setReordering(false));
        });

        /* Rendiamo la tableView non editabile */
        this.taxisTable.setEditable(false);

        /* Impostiamo le proprietà delle colonne */

        /* Nome e cognome */
        setNameSurnameColumnProperty();
        /* Brand */
        setCarColumnProperty();
        /* Modello */
        setModelColumnProperty();
    }

    /**
     * Metodo utile ad impostare le proprietà della colonna inerente al nome e cognome del Tassista
     * */
    private void setNameSurnameColumnProperty(){
        this.nameSurnameColumn.setCellValueFactory(taxiDriverStringCellDataFeatures -> new SimpleStringProperty(
                taxiDriverStringCellDataFeatures.getValue().getFirstName() + " " +
                        taxiDriverStringCellDataFeatures.getValue().getLastName()
        ));

        /* Personalizziamo la cella e quello che vogliamo vedere */
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

    /**
     * Metodo utile ad impostare le proprietà della colonna del brand del Taxi associato al tassita
     * */
    private void setCarColumnProperty(){
        this.carColumn.setCellValueFactory(taxiDriverStringCellDataFeatures -> new SimpleStringProperty(
                taxiDriverStringCellDataFeatures.getValue().getTaxi().getBrandType().toString()
        ));

        /* Personalizziamo la cella e quello che vogliamo vedere */
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

    /**
     * Metodo utile ad impostare le proprietà della colonna del modello del Taxi associato al Tassista
     * */
    private void setModelColumnProperty(){
        this.modelColumn.setCellValueFactory(taxiDriverStringCellDataFeatures -> new SimpleStringProperty(
                taxiDriverStringCellDataFeatures.getValue().getTaxi().getModelName()
        ));

        /* Personalizziamo la cella e quello che vogliamo vedere */
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

    /**
     * Metodo utile a inizializzare la TableView dato un parcheggio
     * @param parking Parcheggio da cui si vogliono reperire i dati
     * @see Parking
     */
    public void initData(Parking parking){
        /* Reperiamo la lista di tassisti fornendone il corrispettivo parcheggio in cui sono locati */
        List<TaxiDriver> drivers = taxiFinderData.getTaxiDriversFromParking(parking);

        /* Impostiamo la lista appena reperita come elementi della TableView */
        this.taxisTable.setItems(FXCollections.observableList(drivers));
    }
}
