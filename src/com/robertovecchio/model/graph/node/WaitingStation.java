package com.robertovecchio.model.graph.node;

import java.io.Serial;
import java.io.Serializable;

/**
 * Questa classe astrae il concetto di postazione di attesa, ovvero il luogo dove ad un cliente è permesso di attendere
 * un Taxi, ad esso associato (quello più vicino). Questa classe è intesa come nodo così da poter essere utile all'
 * algoritmo di Dijkstra per trovare il Taxi più vicino e per poter trovare il percorso più breve
 * @author robertovecchio
 * @version 1.0
 * @since 08/01/2021
 * @see Node
 * */
public class WaitingStation extends Node implements Serializable {

    /**
     * Numero seriale utile ai fini della memorizzazione
     */
    @Serial
    private final static long serialVersionUID = 8L;

    //==================================================
    //               Variabili d'istanza
    //==================================================
    /**Numero civico*/
    private String streetNumber;
    /**Nome della postazione di attesa*/
    private String stationName;
    /**nome della strada*/
    private String streetName;

    //==================================================
    //                   Costruttori
    //==================================================

    /**
     * Metodo costruttore di Waiting station
     * @param coordinates Coordinate espresse in latitudine e longitudine
     * @param streetName Nome della strada in cui è locata la postazione
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

    /**
     * Metodo costruttore di Waiting station
     * @param coordinates Coordinate espresse in latitudine e longitudine
     * @see Coordinates
     * */
    public WaitingStation(Coordinates coordinates){
        super(coordinates);
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

    /**
     * Metodo setter della strada in cui è locata la postazione
     * @param streetName Nome  della strada in cui è locata la postazione
     */
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

    /**
     * Metodo getter per il nome della strada in cui è locata la postazione
     * @return Nome della strada in cui è locata la postazione
     */
    public String getStreetName() {
        return streetName;
    }

    /**
     * Metodo che si occupa di restituire una stringa dato un oggetto di tipo WaitingStation
     * @return Stringa dell'oggetto
     */
    @Override
    public String toString() {
        return super.toString() + "\nWaitingStation{" +
                "streetNumber='" + streetNumber + '\'' +
                ", stationName='" + stationName + '\'' +
                ", streetName='" + streetName + '\'' +
                '}';
    }
}
