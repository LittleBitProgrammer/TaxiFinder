package com.robertovecchio.model.mediator;

import com.robertovecchio.model.booking.Booking;
import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.db.error.TaxiDriverNotFoundException;
import com.robertovecchio.model.dijkstra.DijkstraAlgorithm;
import com.robertovecchio.model.graph.edge.DistanceHandler;
import com.robertovecchio.model.graph.node.Node;
import com.robertovecchio.model.graph.node.Parking;
import com.robertovecchio.model.user.TaxiDriver;
import com.robertovecchio.model.veichle.builderTaxi.Taxi;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class TaxiCenter implements RadioTaxiCallCenter, Serializable {

    private final static long serialVersionUID = 15L;

    @Override
    public void sendTaxiDriver(Booking booking) {
        new Thread(()->{
            try {
                // Riorniamo il taxi driver/ taxi più vicino alla postazione selezionata
                TaxiDriver driver = this.retrieveNearestTaxiDriver(getParkingsFromGraph(), booking);
                booking.setDriver(driver);

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

    @Override
    public void notifyCustomer() {

    }

    private void memorizeBooking(){
        try {
            TaxiFinderData.getInstance().storeBookings();
            TaxiFinderData.getInstance().storeGraph();
        } catch (IOException e) {
            System.out.println("Errore memorizzazione");
        }
    }

    private List<Parking> getParkingsFromGraph(){
        List<Parking> parkings = new ArrayList<>();
        for (Node node : TaxiFinderData.getInstance().getGraph().getVertexes()){
            if (node instanceof Parking){
                parkings.add((Parking)node);
            }
        }
        return parkings;
    }

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

            if (weight < minValue){
                min = parking;
                minValue = weight;
            }
        }

        assert min != null;
        return takeTaxiDriverFrom(min.getTaxis().poll());
    }

    private TaxiDriver takeTaxiDriverFrom(Taxi taxi) throws TaxiDriverNotFoundException {
        for (TaxiDriver taxiDriver : TaxiFinderData.getInstance().getTaxiDrivers()){
            if (taxiDriver.getTaxi().equals(taxi)){
                return taxiDriver;
            }
        }

        throw new TaxiDriverNotFoundException();
    }
}
