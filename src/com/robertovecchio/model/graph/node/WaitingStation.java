package com.robertovecchio.model.graph.node;

/**
 * Questa classe astrae il concetto di postazione di attesa, ovvero il luogo dove ad un cliente è permesso di attendere
 * un Taxi, ad esso associato (quello più vicino). Questa classe è intesa come nodo così da poter essere utile all'
 * algoritmo di Dijkstra per trovare il Taxi più vicino e per poter trovare il percorso più breve
 * @author robertovecchio
 * @version 1.0
 * @since 08/01/2021
 * @see Node
 * */
public class WaitingStation extends Node{
    //==================================================
    //               Variabili d'istanza
    //==================================================
    private String streetNumber; // numero civico
    private String stationName;  // nome della stazione di attesa
    private String streetName;   // nome della strada

    //==================================================
    //                   Costruttori
    //==================================================

    /**
     * Metodo costruttore di Waiting station
     * @param coordinates Coordinate espresse in latitudine e longitudine
     * @param streetNumber Numero civico della strada
     * @param stationName Nome della stazione di attesa
     * @see Coordinates
     * */
    public WaitingStation(Coordinates coordinates, String streetName, String streetNumber, String stationName){
        // Richiamo il costruttore della classe astratta Node
        super(coordinates);

        // Inizializzo le variabili d'istanza
        this.streetName = streetName;
        this.streetNumber = streetNumber;
        this.stationName = stationName;
    }

    //==================================================
    //                      Setter
    //==================================================

    /**
     * Setter del numero civico
     * @param streetNumber Numero civico della strada
     * */
    public void setStreetNumber(String streetNumber){
        this.streetNumber = streetNumber;
    }

    /**
     * Setter del nome della stazione
     * @param stationName Nome della stazione di attesa
     * */
    public void setStationName(String stationName){
        this.stationName = stationName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    //==================================================
    //                      Getter
    //==================================================

    /**
     * Getter del numero civico della strada
     * @return Numero civico della strada
     * */
    public String getStreetNumber(){
        return this.streetNumber;
    }

    /**
     * Getter del nome della stazione di attesa
     * @return Nome stazione di attesa
     * */
    public String getStationName(){
        return this.stationName;
    }

    public String getStreetName() {
        return streetName;
    }
}
