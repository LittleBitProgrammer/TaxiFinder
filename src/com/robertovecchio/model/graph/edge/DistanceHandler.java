package com.robertovecchio.model.graph.edge;

import com.robertovecchio.model.graph.node.Coordinates;

/**
 * Classe che si occupa di calcolare le distanze tra due punti geolocalizzati da latitudine e longitudine
 * @author robertovecchio
 * @version 1.0
 * @since 15/01/2021
 */
public class DistanceHandler {
    /**
     * Coordinate del punto A
     * @see Coordinates
     */
    private final Coordinates coordinatesOne;
    /**
     * Coordinate del punto B
     * @see Coordinates
     */
    private final Coordinates coordinatesTwo;

    // COSTANTI
    /**
     * Costante del raggio terrestre
     */
    private static final double R = 6371;

    /**
     * Costruttore di DistanceHandler
     * @param coordinatesOne Coordinate punto A
     * @param coordinatesTwo Coordinate punto B
     * @see Coordinates
     */
    public DistanceHandler(Coordinates coordinatesOne, Coordinates coordinatesTwo){
        this.coordinatesOne = coordinatesOne;
        this.coordinatesTwo = coordinatesTwo;
    }

    /**
     * Metodo utile a calcolare la distanza dati due punti con latitudine e longitudine
     * @return distanza in KM tra due punti
     */
    public double calculateDistance(){
        double latAlfa, latBeta;
        double lonAlfa, lonBeta;
        double fi;
        double p,d;

        /* Convertiamo i gradi in radianti */
        latAlfa = Math.PI * this.coordinatesOne.getLatitude() / 180;
        latBeta = Math.PI * this.coordinatesTwo.getLatitude() / 180;
        lonAlfa = Math.PI * this.coordinatesOne.getLongitude() / 180;
        lonBeta = Math.PI* this.coordinatesTwo.getLongitude() / 180;

        // Calcola l'angolo compreso fi
        fi = Math.abs(lonAlfa - lonBeta);

        /* Calcola il terzo lato del triangolo sferico */
        p = Math.acos(Math.sin(latBeta) * Math.sin(latAlfa) +
                Math.cos(latBeta) * Math.cos(latAlfa) * Math.cos(fi));

        /* Calcola la distanza sulla superficie terrestre R = ~6371 km */
        d = p * R;
        return d;
    }
}
