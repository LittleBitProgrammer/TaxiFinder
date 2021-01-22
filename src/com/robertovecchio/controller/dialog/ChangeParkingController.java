package com.robertovecchio.controller.dialog;


import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.graph.node.Node;
import com.robertovecchio.model.graph.node.Parking;
import com.robertovecchio.model.user.TaxiDriver;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.util.StringConverter;
import java.io.IOException;
import java.util.Optional;

/**
 * Classe che gestisce la view (dialog) di modifica del parcheggio corrente
 * @author robertovecchio
 * @version 1.0
 * @since 10/01/2021
 * */
public class ChangeParkingController {

    //==================================================
    //               Variabili d'istanza
    //==================================================

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
     * ComboBox utile a mostrare la lista dei parcheggi presenti in memoria
     * @see ComboBox
     * @see Parking
     * */
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
        /* Inizilizziamo gli item della ComboBox */
        this.parkingComboBox.setItems(taxiFinderData.getParkings());

        /* Impostiamo il contenuto delle celle della comboBox */
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

        /* Permettiamo di mostrare una stringa personalizzata nell'intestazione della ComboBox */
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

        /* Selezioniamo il primo */
        this.parkingComboBox.getSelectionModel().selectFirst();
    }

    /**
     * Metodo utile a generare una stringa da un parcheggio
     * @return String la quale rappresenterà un parcheggio
     * @param parking parcheggio di cui si vuole rappresentare una sua stringa
     * @see Parking
     * */
    private String generateString(Parking parking){
        return String.format("%d - %s",  this.taxiFinderData.getParkings().indexOf(parking) + 1,
                parking.getStationName());
    }

    /**
     * Metodo utile a cambiare il parcheggio in cui è locato il Tassista, processando opportunamente i dati inseriti
     * da interfaccia utente
     */
    public void processChangeParking(){

        /* Memorizziamo il parcheggio in cui si vuole andare, reperendo opportunamente i dati dalla ComboBox */
        Parking changedParking = this.parkingComboBox.getValue();

        /* Memorizziamo l'utente corrente*/
        TaxiDriver taxiDriver = (TaxiDriver) taxiFinderData.getCurrentUser();

        /* Iteriamo ogni nodo nel grafo */
        for (Node node : taxiFinderData.getGraph().getVertexes()){

            /* Se il nodo corrente corrisponde a quello che in cui noi vogliamo andare */
            if (node.equals(changedParking)){

                /* Vuol dire che il nodo corrente è un parcheggio per cui possiamo effettuare un'operazione di cast */
                Parking parking = (Parking) node;

                /* Per cui inseriamo il taxi nella coda di taxi presente in questo parcheggio */
                parking.getTaxis().offer(taxiDriver.getTaxi());

            /* Se il nodo non è quello in cui vogliamo andare */
            }else{

                /* Controlliamo che il nodo corrente sia un Parcheggio */
                if (node instanceof Parking){

                    /*In questo caso allora sappiamo sicuramente che è un parcheggio, per cui possiamo effettuare
                     * un' operazione di cast */
                    Parking parking = (Parking) node;

                    /* Nel caso in cui contenga il taxi dell'utente allora dobbiamo rimuoverlo affinchè lo spostamento
                     * Possa andare a buon fine */
                    /* Rimuoviamo il taxi dalla "testa" della coda */
                    parking.getTaxis().remove(taxiDriver.getTaxi());
                }

            }
        }

        try {
            /* Memorizziamo il grafo */
            taxiFinderData.storeGraph();
        } catch (IOException e) {
            /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
            Alert alert = new Alert(Alert.AlertType.WARNING, "Errore", ButtonType.OK);
            alert.setHeaderText("Errore di memorizzazione");
            alert.setContentText("Impossibile memorizzare tale operazione");

            Optional<ButtonType> result = alert.showAndWait();
            System.out.println(result);

        }
    }

    /**
     * Metodo che ritorna una variabile booleana la quale constata se un tassista sta cercando di notificare il sistema
     * che è nello stesso parcheggio di cui il sistema è già a conoscenza
     * @return Ritorna true se sis sta cercando di cambiare lo stato del taxi in un parcheggio di cui il sistema è
     * già a conoscenza, altrimenti false
     */
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
