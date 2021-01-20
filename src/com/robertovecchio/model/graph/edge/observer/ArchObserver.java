package com.robertovecchio.model.graph.edge.observer;

/**
 * Classe che rappresenta l'Observer nell'observer Pattern. Questa serve a monitorare i sensori sulle strade
 * Che monitorano il traffico ogni 30 secondi.
 * @author robertovecchio
 * @version 1.0
 * @since 15/10/2021
 */
public abstract class ArchObserver {
    /**
     * Reference ad observable
     * @see Sensorable
     */
    protected Sensorable sensorable;

    /**
     * Metodo di update per l'observer
     * @param trafficState Stato del traffico (attraversamento in termini di minuti)
     */
    abstract void update(int trafficState);
}
