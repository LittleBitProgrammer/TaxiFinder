package com.robertovecchio.model.graph.node;

import java.time.LocalTime;

/**
 * Questa classe astrae il concetto di strada. Questa classe è intesa come nodo così da poter essere utile all'
 * algoritmo di Dijkstra per poter trovare il percorso più breve o la strada con il tempo di percorrimento minore.
 * @author robertovecchio
 * @version 1.0
 * @since 08/01/2021
 * */
public class Street<T>{
    //==================================================
    //               Variabili d'istanza
    //==================================================

    /**@see LocalTime*/
    private LocalTime travelTime; // Tempo di percorrimento
    private String streetName; // Nome della strada
    /**@see WaitingStation*/
    private T waitingStation; // stazione di attesa, può essere presente o meno in una strada, 1 a strada

    //==================================================
    //                   Costruttori
    //==================================================

    /**
     * Metodo costruttore di Street
     * @param travelTime Tempo di percorrimento della strada basato sui sensori posti ad inizio e fine strada
     * @param streetName Nome della strada
     * @see LocalTime
     * */
    public Street(LocalTime travelTime, String streetName){

        // Inizializzo le variabili d'istanza
        this.travelTime = travelTime;
        this.streetName = streetName;
    }

    /**
     * Metodo costruttore di Street
     * @param travelTime Tempo di percorrimento della strada basato sui sensori posti ad inizio e fine strada
     * @param streetName Nome della strada
     * @param waitingStation Stazione di attesa presente nella strada
     * @see LocalTime
     * @see WaitingStation
     * */
    public Street(LocalTime travelTime,
                  String streetName, T waitingStation){

        // Inizializzo le variabili d'istanza
        this.travelTime = travelTime;
        this.streetName = streetName;
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
     * Setter della stazione di attesa
     * @param waitingStation Stazione di attesa
     * @see WaitingStation
     * */
    public void setWaitingStation(T waitingStation){
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
     * Getter della stazione di attesa
     * @return stazione di attesa
     * @see WaitingStation
     * */
    public T getWaitingStation(){
        return this.waitingStation;
    }
}