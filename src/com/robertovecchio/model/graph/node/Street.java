package com.robertovecchio.model.graph.node;

import java.time.LocalTime;

/**
 * Questa classe astrae il concetto di strada. Questa classe è intesa come nodo così da poter essere utile all'
 * algoritmo di Dijkstra per poter trovare il percorso più breve o la strada con il tempo di percorrimento minore.
 * Essendo un nodo questa verrà individuata da delle coordinate che di fatto coincidono con il primo numero civico
 * disponibile all'inizio della strada.
 * @author robertovecchio
 * @version 1.0
 * @since 08/01/2021
 * */
public class Street extends Node{
    //==================================================
    //               Variabili d'istanza
    //==================================================

    /**@see LocalTime*/
    private LocalTime travelTime; // Tempo di percorrimento
    private String streetName; // Nome della strada
    private double streetLength; // lunghezza della strada espressa in KM
    /**@see WaitingStation*/
    private WaitingStation waitingStation; // stazione di attesa, può essere presente o meno in una strada, 1 a strada

    //==================================================
    //                   Costruttori
    //==================================================

    /**
     * Metodo costruttore di Street
     * @param coordinates Coordinate della strada espresse in latitudine e longitudine
     * @param travelTime Tempo di percorrimento della strada basato sui sensori posti ad inizio e fine strada
     * @param streetName Nome della strada
     * @param streetLength Lunghezza della strada espressa in KM
     * @see Coordinates
     * @see LocalTime
     * */
    public Street(Coordinates coordinates, LocalTime travelTime, String streetName, double streetLength){
        // Richiamo il costruttore della classe astratta UserAccount
        super(coordinates);

        // Inizializzo le variabili d'istanza
        this.travelTime = travelTime;
        this.streetName = streetName;
        this.streetLength = streetLength;
    }

    /**
     * Metodo costruttore di Street
     * @param coordinates Coordinate della strada espresse in latitudine e longitudine
     * @param travelTime Tempo di percorrimento della strada basato sui sensori posti ad inizio e fine strada
     * @param streetName Nome della strada
     * @param streetLength Lunghezza della strada espressa in KM
     * @param waitingStation Stazione di attesa presente nella strada
     * @see Coordinates
     * @see LocalTime
     * @see WaitingStation
     * */
    public Street(Coordinates coordinates, LocalTime travelTime,
                  String streetName, double streetLength, WaitingStation waitingStation){

        // Richiamo il costruttore della classe astratta UserAccount
        super(coordinates);

        // Inizializzo le variabili d'istanza
        this.travelTime = travelTime;
        this.streetName = streetName;
        this.streetLength = streetLength;
        this.waitingStation = waitingStation;
    }

    //==================================================
    //                      Setter
    //==================================================

    /**
     * Setter del tempo di percorrimento di una strada
     * @param travelTime Tempo di percorrimento di una strada
     * @see LocalTime
     * */
    public void setTravelTime(LocalTime travelTime){
        this.travelTime = travelTime;
    }

    /**
     * Setter del del nome di una strada
     * @param streetName Nome di una strada
     * */
    public void setStreetName(String streetName){
        this.streetName = streetName;
    }

    /**
     * Setter della lunghezza di una strada espressa in KM
     * @param streetLength Lunghezza di una strada espressa in KM
     * */
    public void setStreetLength(double streetLength){
        this.streetLength = streetLength;
    }

    /**
     * Setter della stazione di attesa
     * @param waitingStation Stazione di attesa
     * @see WaitingStation
     * */
    public void setWaitingStation(WaitingStation waitingStation){
        this.waitingStation = waitingStation;
    }

    //==================================================
    //                      Getter
    //==================================================

    /**
     * Getter del tempo di percorrimento di una strada
     * @return Tempo di percorrimento di una strada
     * @see LocalTime
     * */
    public LocalTime getTravelTime(){
        return this.travelTime;
    }

    /**
     * Getter del nome di una strada
     * @return Nome di una strada
     * */
    public String getStreetName(){
        return this.streetName;
    }

    /**
     * Getter della lunghezza di una strada espressa in KM
     * @return Lunghezza di una strada espressa in KM
     * */
    public double getStreetLength() {
        return streetLength;
    }

    /**
     * Getter della stazione di attesa
     * @return stazione di attesa
     * @see WaitingStation
     * */
    public WaitingStation getWaitingStation(){
        return this.waitingStation;
    }
}