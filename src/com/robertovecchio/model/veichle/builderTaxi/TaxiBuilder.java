package com.robertovecchio.model.veichle.builderTaxi;

// import
import com.robertovecchio.model.user.TaxiDriver;
import com.robertovecchio.model.veichle.BrandType;
import com.robertovecchio.model.veichle.FuelType;

/**
 * Questa classe è l'astrazione di un builder che servirà a specificare un'interfaccia atta alla creazione del product
 * @author robertovecchio
 * @version 1.0
 * @since 07/01/2021*/
public abstract class TaxiBuilder {
    //==================================================
    //               Variabili d'istanza
    //==================================================
    /**@see Taxi*/
    protected Taxi taxi; // Taxi da creare

    //==================================================
    //                   Metodi astratti
    //==================================================

    /**
     * Metodo atto a buildare il numero di targa
     * @param licensePlate Numero di targa
     * */
    public abstract void buildLicensePlate(String licensePlate);

    /**
     * Metodo atto a buildare il tipo di brand
     * @param brandType Tipo di brand
     * @see BrandType
     * */
    public abstract void buildBrandName(BrandType brandType);

    /**
     * Metodo atto a buildare il nome del modello taxi
     * @param modelName Nome modello taxi
     * */
    public abstract void buildModelName(String modelName);

    /**
     * Metodo atto a buildare la capacità del taxi
     * @param capacity Capacità del taxi in termini di persone trasportabili del taxi
     * */
    public abstract void buildCapacity(int capacity);

    /**
     * Metodo atto a buildare il tipo di carburante
     * @param fuelType Tipo di carburante
     * @see FuelType
     * */
    public abstract void buildFuelType(FuelType fuelType);

    /**
     * Metodo atto a buildare il Tassista
     * @param taxiDriver Tassista
     * @see TaxiDriver
     * */
    public abstract void buildTaxiDriver(TaxiDriver taxiDriver);

    //==================================================
    //                      Metodi
    //==================================================

    /**
     * Metodo atto a creare una nuova istanza di Taxi
     * */
    public final void createTaxi(){
        this.taxi = new Taxi();
    }

    /**
     * Getter Del Taxi
     * @return Il Taxi buildato
     * @see Taxi
     * */
    public final Taxi getResult(){
        return this.taxi;
    }

}