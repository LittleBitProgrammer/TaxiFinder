package com.robertovecchio.model.graph.edge.observer;

/**
 * Interfaccia che costituisce l'astrazione dell'observable nell'observer Pattern
 * @author robertovecchio
 * @version 1.0
 * @since 15/01/2021
 */
public interface Sensorable {
     /**
      * Metodo per attaccare un abserver
      * @param archObserver observer da aggiungere
      * @see ArchObserver
      */
     void attach(ArchObserver archObserver);
     /**
      * Metodo per staccare un abserver
      * @param archObserver observer da aggiungere
      * @see ArchObserver
      */
     void detach(ArchObserver archObserver);

     /**
      * Metodo per aggiornare tutti gli observable
      * @param trafficState stato del traffico in termini di minuti necessari per attraversare un tratto
      */
     void notifyObs(int trafficState);
}
