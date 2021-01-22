package com.robertovecchio.model.veichle.builderTaxi;


import com.robertovecchio.model.veichle.BrandType;
import com.robertovecchio.model.veichle.FuelType;

/**
 * Questa classe è il ConcreteBuilder del Builder Pattern e si occupa di costruire il Taxi in base ai metodi definiti
 * nel Taxi Builder
 * @author robertovecchio
 * @version 1.0
 * @since 07/01/2021
 * */
public class ConcreteTaxiBuilder extends TaxiBuilder {

    //==================================================
    //             Metodi Sovrascritti
    //==================================================

    /**
     * Metodo atto a buildare il numero di targa
     * @param licensePlate Numero di targa
     * */
    @Override
    public void buildLicensePlate(String licensePlate) {
        taxi.setLicensePlate(licensePlate);
    }

    /**
     * Metodo atto a buildare il tipo di brand
     * @param brandType Tipo di brand
     * @see BrandType
     * */
    @Override
    public void buildBrandName(BrandType brandType) {
        taxi.setBrandType(brandType);
    }

    /**
     * Metodo atto a buildare il nome del modello taxi
     * @param modelName Nome modello taxi
     * */
    @Override
    public void buildModelName(String modelName) {
        taxi.setModelName(modelName);
    }

    /**
     * Metodo atto a buildare la capacità del taxi
     * @param capacity Capacità del taxi in termini di persone trasportabili del taxi
     * */
    @Override
    public void buildCapacity(int capacity) {
        taxi.setCapacity(capacity);
    }

    /**
     * Metodo atto a buildare il tipo di carburante
     * @param fuelType Tipo di carburante
     * @see FuelType
     * */
    @Override
    public void buildFuelType(FuelType fuelType) {
        taxi.setFuelType(fuelType);
    }
}
