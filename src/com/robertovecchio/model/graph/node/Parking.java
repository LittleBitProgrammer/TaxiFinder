package com.robertovecchio.model.graph.node;

// import
import com.robertovecchio.model.veichle.builderTaxi.Taxi;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Classe che astrae il concetto di parcheggio, che può essere a sua volta una stazione di attesa. Questa classe
 * attraverso l'ereditarietà è a sua volta un nodo, quindi utilizzabile dall'algoritmo di Dijkstra
 * @author robertovecchio
 * @version 1.0
 * @since 08/01/2021
 * @see WaitingStation
 * @see Node
 * */
public class Parking extends WaitingStation{

    //==================================================
    //               Variabili d'istanza
    //==================================================

    private int parkingCapacity; // Capacità del parcheggio (posti auto)
    /**
     * @see Queue
     * @see Taxi
     * */
    private Queue<Taxi> taxis;    // Coda taxi presenti

    //==================================================
    //                   Costruttori
    //==================================================

    /**
     * Costruttore della classe Parking
     * @param coordinates Coordinate del parcheggio espresse in latitudine e longitudine
     * @param streetNumber Numero civico del parcheggio
     * @param stationName Nome del parcheggio
     * @param parkingCapacity Capacità del parcheggio in termini di posti auto
     * @param taxis lista taxi presenti
     * @see Coordinates
     * @see Queue
     * @see Taxi
     * */
    public Parking(Coordinates coordinates, String streetName, String streetNumber,
                   String stationName, int parkingCapacity, Queue<Taxi> taxis){

        // Richiamo il costruttore della classe astratta UserAccount
        super(coordinates, streetName, streetNumber, stationName);

        // Inizializzo le variabili d'istanza
        this.parkingCapacity = parkingCapacity;
        this.taxis = taxis;
    }

    /**
     * Costruttore della classe Parking
     * @param coordinates Coordinate del parcheggio espresse in latitudine e longitudine
     * @param streetNumber Numero civico del parcheggio
     * @param stationName Nome del parcheggio
     * @param parkingCapacity Capacità del parcheggio in termini di posti auto
     * @see Coordinates
     * */
    public Parking(Coordinates coordinates, String streetName, String streetNumber,
                   String stationName, int parkingCapacity){
        // Richiamo il costruttore della classe astratta UserAccount
        super(coordinates, streetName, streetNumber, stationName);

        // Inizializzo le variabili d'istanza
        this.parkingCapacity = parkingCapacity;
        this.taxis = new PriorityQueue<>();
    }

    //==================================================
    //                      Setter
    //==================================================

    /**
     * Setter della capacità del parcheggio in termini di posti auto
     * @param parkingCapacity Capacità del parcheggio in termini di posti auto
     * */
    public void setParkingCapacity(int parkingCapacity){
        this.parkingCapacity = parkingCapacity;
    }

    /**
     * Setter della lista tassisti
     * @param taxis Lista tassisti
     * @see Queue
     * @see Taxi
     * */
    public void setTaxis(Queue<Taxi> taxis){
        this.taxis = taxis;
    }

    //==================================================
    //                      Getter
    //==================================================

    /**
     * Getter della capacità del parcheggio in termini di posti auto
     * @return Capacità del parcheggio in termini di posti auto
     * */
    public int getParkingCapacity(){
        return this.parkingCapacity;
    }

    /**
     * Getter della lista tassisti
     * @return Lista tassisti
     * @see Queue
     * @see Taxi
     * */
    public Queue<Taxi> getTaxis(){
        return this.taxis;
    }

    //==================================================
    //                      Metodi
    //==================================================

    /**
     * Metodo atto a constatare i posti occupati in un parcheggio
     * @return Posti occupati in un parcheggio
     * */
    public int getOccupiedParkingSpaces(){
        return this.taxis.size();
    }

    /**
     * Metodo atto a constatare i posti liberi in un parcheggio
     * @return Posti liberi in un parcheggio
     * */
    public int getFreeParkingSpaces(){
        return this.parkingCapacity - this.taxis.size();
    }

    /**
     * Metodo atto a constatare se il parcheggio è pieno
     * @return Se il parcheggio è pieno ritorna true, altrimenti false
     * */
    public boolean isFull(){
        return this.getFreeParkingSpaces() == 0;
    }

    /**
     * Metodo atto a constatare se il parcheggio è vuoto
     * @return Se il parcheggio è vuoto ritorna true, altrimenti false
     * */
    public boolean isEmpty(){
        return this.getFreeParkingSpaces() == this.parkingCapacity;
    }
}