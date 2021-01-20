package com.robertovecchio.model.graph.edge.observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Classe Observable dell' Observer Pattern
 * @author robertovecchio
 * @version 1.0
 * @since 15/01/2021
 * @see Sensorable
 */
public class SensorHandler implements Sensorable {
    /**
     * Lista di Observer
     * @see List
     * @see ArchObserver
     */
    private final List<ArchObserver> observers = new ArrayList<>();
    /**
     * Stato del traffico
     */
    private int trafficState;

    /**
     * Metodo getter dello stato
     * @return Stato del traffico
     */
    public int getState(){
        return this.trafficState;
    }

    /**
     * Metodo setter dello stato
     * @param trafficState Stato del traffico
     */
    public void setTrafficState(int trafficState){
        this.trafficState = trafficState;
        notifyObs(trafficState);
    }

    /**
     * Metodo per attaccare un abserver
     * @param archObserver observer da aggiungere
     * @see ArchObserver
     */
    @Override
    public void attach(ArchObserver archObserver) {
        observers.add(archObserver);
    }

    /**
     * Metodo per staccare un abserver
     * @param archObserver observer da aggiungere
     * @see ArchObserver
     */
    @Override
    public void detach(ArchObserver archObserver) {
        observers.remove(archObserver);
    }

    /**
     * Metodo per aggiornare tutti gli observable
     * @param trafficState stato del traffico in termini di minuti necessari per attraversare un tratto
     */
    @Override
    public void notifyObs(int trafficState) {
        for (ArchObserver observer : this.observers){
            observer.update(trafficState);
        }
    }
}
