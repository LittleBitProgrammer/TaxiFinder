package com.robertovecchio.model.graph;

import com.robertovecchio.model.graph.node.Coordinates;

public class DistanceHandler {
    private final Coordinates coordinatesOne;
    private final Coordinates coordinatesTwo;

    // COSTANTI
    private static final int R = 6371;
    private static final double PI_GRECO = 3.1415927;

    public DistanceHandler(Coordinates coordinatesOne, Coordinates coordinatesTwo){
        this.coordinatesOne = coordinatesOne;
        this.coordinatesTwo = coordinatesTwo;
    }

    public double calculateDistance(){
        double latAlfa, latBeta;
        double lonAlfa, lonBeta;
        double fi;
        double p,d;

        /* Convertiamo i gradi in radianti */
        latAlfa = PI_GRECO * this.coordinatesOne.getLatitude() / 180;
        latBeta = PI_GRECO * this.coordinatesTwo.getLatitude() / 180;
        lonAlfa = PI_GRECO * this.coordinatesOne.getLongitude() / 180;
        lonBeta = PI_GRECO * this.coordinatesTwo.getLongitude() / 180;

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
