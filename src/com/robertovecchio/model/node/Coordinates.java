package com.robertovecchio.model.node;

// import
import java.util.Objects;

/**
 * Classe atta ad astrarre il concetto di coordinate geografiche, individuate da latitudine e longitudine
 * @author robertovecchio
 * @version 1.0
 * @since 07/01/2021
 * */
public class Coordinates {
    //==================================================
    //               Variabili d'istanza
    //==================================================
    private double latitude;  // Latitudine
    private double longitude; // Longitudine

    //==================================================
    //                  Costruttori
    //==================================================
    /**
     * Costruttore della classe Coordinates
     * @param latitude Latitudine
     * @param longitude Longitudine
     * */
    public Coordinates(double latitude, double longitude){
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * Costruttore vuoto della classe Coordinates, inizializza a 0 latitudine e longitudine
     * */
    public Coordinates(){
        this(0,0);
    }

    /**
     * Setter della latitudine
     * @param latitude Latitudine
     * */
    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    /**
     * Setter della longitudine
     * @param longitude Longitudine
     * */
    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    /**
     * getter della Latitudine
     * @return Latitudine
     * */
    public double getLatitude(){
        return this.latitude;
    }

    /**
     * getter della Longitudine
     * @return Longitudine
     * */
    public double getLongitude(){
        return this.longitude;
    }

    /**
     * Override del metodo toString atto a generare una stringa dato un oggetto del tipo Coordinates
     * @return Stringa dell'oggetto di tipo Coordinates
     * */
    @Override
    public String toString() {
        return "Coordinates{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }

    /**
     * Override del metodo equals atto a constatare l'uguaglianza di due oggetti di tipo Coordinates
     * @return se i due oggetti sono uguali ritorna true, altrimenti false
     * */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Coordinates)) return false;
        Coordinates that = (Coordinates) o;
        return Double.compare(that.latitude, latitude) == 0 &&
                Double.compare(that.longitude, longitude) == 0;
    }

    /**
     * Override del metodo hascode
     * @return il valore intero rappresentato dall'oggetto
     * */
    @Override
    public int hashCode() {
        return Objects.hash(latitude, longitude);
    }
}