package com.robertovecchio.model.mediator;

import com.robertovecchio.model.booking.Booking;
import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.db.error.TaxiDriverNotFoundException;
import com.robertovecchio.model.dijkstra.DijkstraAlgorithm;
import com.robertovecchio.model.graph.edge.DistanceHandler;
import com.robertovecchio.model.graph.node.Node;
import com.robertovecchio.model.graph.node.Parking;
import com.robertovecchio.model.user.State;
import com.robertovecchio.model.user.TaxiDriver;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.io.IOException;
import java.io.Serial;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * Questa classe astrae il concetto di callCenter di un'azienda taxi. Concretamente rappresenta il concreteMediator
 * del Mediator Pattern.
 * @author robertovecchio
 * @version 1.0
 * @since 15/01/2021
 * @see RadioTaxiCallCenter
 * @see Serializable
 */
public class TaxiCenter implements RadioTaxiCallCenter, Serializable {

    /**
     * Numero seriale utile ai fini della memorizzazione
     */
    @Serial
    private final static long serialVersionUID = 15L;

    /**
     * Metodo utile a notificare un Tassista che ha un incarico in corso, in particolare verrà scelto colui che è locato
     * nel parcheggio più vicino
     * @param booking Prenotazione da inviare al tassissta
     */
    @Override
    public void sendTaxiDriver(Booking booking) {
        new Thread(()->{
            try {
                // Riorniamo il taxi driver/ taxi più vicino alla postazione selezionata
                TaxiDriver driver = this.retrieveNearestTaxiDriver(TaxiFinderData.getInstance().getParkingsFromGraph(), booking);
                booking.setDriver(driver);
                driver.setState(State.OCCUPIED);

                // Aggiungo in memoria la prenotazione
                TaxiFinderData.getInstance().addBooking(booking);
                memorizeBooking();

                System.out.println("Prenotazione avvenuta con successo");
            } catch (TaxiDriverNotFoundException e) {
                Platform.runLater(()->{
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Tassista non trovato", ButtonType.OK);
                    alert.setHeaderText("Nessun tassista trovato");
                    alert.setContentText("Ci dispiace non è stato trovato alcun tassista");

                    Optional<ButtonType> result = alert.showAndWait();
                    System.out.println(result);
                });
            }
        }).start();
    }

    /**
     * Metodo per nitficare il cliente che la prenotazione è andata a buon fine e che il tassista si sta recando presso
     * la postazione scelta dal cliente stesso
     * @param booking Prenotazione aggiornata con i parametri scelti dal tassista
     */
    @Override
    public void notifyCustomer(Booking booking) {
        memorizeBooking();
    }

    /**
     * Metodo utile a memorizzare permanentemente ed in diversi passi, le prenotazioni
     */
    private void memorizeBooking(){
        try {
            TaxiFinderData.getInstance().storeBookings();
            TaxiFinderData.getInstance().storeGraph();
            TaxiFinderData.getInstance().storeTaxiDrivers();
        } catch (IOException e) {
            System.out.println("Errore memorizzazione");
        }
    }

    /**
     * Metodo utile a ritornare il Tassista più vicino alla postazione del cliente
     * @param parkings lista dei parcheggi presenti nel grafo
     * @param booking Prenotazione effettuata dal cliente
     * @return Il tassista più vicino
     * @throws TaxiDriverNotFoundException Questo metodo potrebbe non trovare un tassista disponibile
     */
    private TaxiDriver retrieveNearestTaxiDriver(List<Parking> parkings, Booking booking) throws TaxiDriverNotFoundException {

        // Inializzo la classe per l'algoritmo di Dijkstra
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(TaxiFinderData.getInstance().getGraph());

        // Eseguo l'algoritmo di Dijkstra dalla sorgente, ovvero dal punto di partenza deciso dal cliente
        dijkstraAlgorithm.execute(booking.getFrom());

        Parking min = null;
        double minValue = Double.MAX_VALUE;

        // Iteriamo l'algoritmo per trovare il percorso, corrispettivamente per i diversi parcheggi
        for (Parking parking : parkings){
            LinkedList<Node> path = dijkstraAlgorithm.getPath(parking);
            double weight = 0;

            for (int i = 0; i < path.size() - 1; i++){
                DistanceHandler dh = new DistanceHandler(path.get(i).getCoordinates(), path.get(i+1).getCoordinates());
                weight += dh.calculateDistance();
            }

            System.out.println("Parcheggio: " + parking.getStationName());
            System.out.println("Lunghezza: " + weight);

            if (weight < minValue && !parking.getTaxis().isEmpty()){
                min = parking;
                minValue = weight;
            }
        }

        assert min != null;
        return TaxiFinderData.getInstance().takeTaxiDriverFrom(min.getTaxis().peek());
    }
}
