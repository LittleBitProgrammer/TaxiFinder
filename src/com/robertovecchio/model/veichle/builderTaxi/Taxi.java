package com.robertovecchio.model.veichle.builderTaxi;

//Import
import com.robertovecchio.model.veichle.BrandType;
import com.robertovecchio.model.veichle.FuelType;
import java.io.Serializable;
import java.util.Objects;

/**
 * Questa classe ha la responsabilità di astrarre un taxi, fornendo una sua rappresentazione
 * @author robertovecchio
 * @version 1.0
 * @since 7/01/2021
 * */
public class Taxi implements Serializable {

    private final static long serialVersionUID = 5L;

    //==================================================
    //               Variabili d'istanza
    //==================================================
    private String licensePlate; // Targa del taxi
    /**@see BrandType */
    private BrandType brandType; // Brand del Taxi
    private String modelName;    // Modello del Taxi
    private int capacity;        // Capcità del Taxi in termini di persone trasportabili
    /**@see FuelType */
    private FuelType fuelType;   // Tipo di carburante del Taxi

    //==================================================
    //                   Costruttori
    //==================================================
    /**
     * Costruttore del Taxi
     * */
    public Taxi(){/*Costruttore di "Default"*/}

    //==================================================
    //                      Setter
    //==================================================
    /**
     * Setter della Targa del Taxi
     * @param licensePlate Targa del Taxi
     * */
    public void setLicensePlate(String licensePlate){
        this.licensePlate = licensePlate;
    }

    /**
     * Setter della brand del Taxi
     * @param brandType Brand del Taxi
     * @see BrandType
     * */
    public void setBrandType(BrandType brandType){
        this.brandType = brandType;
    }

    /**
     * Setter nome modello del Taxi
     * @param modelName Nome modello del Taxi
     * */
    public void setModelName(String modelName){
        this.modelName = modelName;
    }

    /**
     * Setter della capacità in termini di persone del Taxi
     * @param capacity Capacità del Taxi
     * */
    public void setCapacity(int capacity){
        this.capacity = capacity;
    }

    /**
     * Setter del tipo carburante del Taxi
     * @param fuelType Tipo carburante del Taxi
     * @see FuelType
     * */
    public void setFuelType(FuelType fuelType){
        this.fuelType = fuelType;
    }

    //==================================================
    //                      Getter
    //==================================================

    /**
     * Getter della targa del taxi
     * @return Targa del Taxi
     * */
    public String getLicensePlate(){
        return this.licensePlate;
    }

    /**
     * Getter del Brand del taxi
     * @return Brand del Taxi
     * @see BrandType
     * */
    public BrandType getBrandType(){
        return this.brandType;
    }

    /**
     * Getter del nome modello del taxi
     * @return Nome modello del Taxi
     * */
    public String getModelName(){
        return this.modelName;
    }

    /**
     * Getter della capacità del taxi
     * @return Capacità del Taxi
     * */
    public int getCapacity(){
        return this.capacity;
    }

    /**
     * Getter del tipo carburante del taxi
     * @return Tipo carburante del Taxi
     * @see FuelType
     * */
    public FuelType getFuelType(){
        return this.fuelType;
    }
    //==================================================
    //                Metodi Sovrascritti
    //==================================================

    /**
     * Override del metodo toString atto a generare una stringa dato un oggetto del tipo Taxi
     * @return Stringa dell'oggetto di tipo Taxi
     * */
    @Override
    public String toString() {
        return "Taxi{" +
                "licensePlate='" + licensePlate + '\'' +
                ", brandType=" + brandType +
                ", modelName='" + modelName + '\'' +
                ", capacity=" + capacity +
                ", fuelType=" + fuelType +
                '}';
    }

    /**
     * Override del metodo equals atto a constatare l'uguaglianza di due oggetti di tipo Taxi
     * @return se i due oggetti sono uguali ritorna true, altrimenti false
     * */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Taxi)) return false;
        Taxi taxi = (Taxi) o;
        return Objects.equals(licensePlate, taxi.licensePlate);
    }

    /**
     * Override del metodo hascode
     * @return il valore intero rappresentato dall'oggetto
     * */
    @Override
    public int hashCode() {
        return Objects.hash(licensePlate);
    }
}
