package com.robertovecchio.model.user;

//Import
import com.robertovecchio.model.veichle.BrandType;
import com.robertovecchio.model.veichle.FuelType;
import com.robertovecchio.model.veichle.builderTaxi.TaxiBuilder;
import java.time.LocalDate;

/**
 * Questa classe rappresenta uno degli attori del sistema software Taxi finder e si occupa di astrarre il concetto
 * di gestore aziendale
 * @author robertovecchio
 * @version 1.0
 * @since 7/01/2021
 * */
public class Handler extends UserAccount{
    //==================================================
    //               Variabili d'istanza
    //==================================================
    /**@see TaxiBuilder*/
    private TaxiBuilder taxiBuilder; // builder del Taxi

    //==================================================
    //                   Costruttori
    //==================================================

    /**
     * Costruttore di un Gestore
     * @param fiscalCode codice fiscale utente
     * @param firstName nome utente
     * @param lastName cognome utente
     * @param dateOfBirth data di nascita utente
     * @param genderType genere sessuale utente
     * @param email email utente
     * @param username username utente
     * @param password password utente
     * @see LocalDate
     * @see GenderType
     * */
    public Handler(String fiscalCode, String firstName,
                   String lastName, LocalDate dateOfBirth,
                   GenderType genderType, String email,
                   String username, String password){

        // Richiamo il costruttore della classe astratta UserAccount
        super(fiscalCode, firstName, lastName, dateOfBirth, genderType, email, username, password);
    }

    /**
     * Costruttore di un Gestore con builder Taxi
     * @param fiscalCode codice fiscale utente
     * @param firstName nome utente
     * @param lastName cognome utente
     * @param dateOfBirth data di nascita utente
     * @param genderType genere sessuale utente
     * @param email email utente
     * @param username username utente
     * @param password password utente
     * @param taxiBuilder builder del taxi
     * @see LocalDate
     * @see GenderType
     * @see TaxiBuilder
     * */
    public Handler(String fiscalCode, String firstName,
                   String lastName, LocalDate dateOfBirth,
                   GenderType genderType, String email,
                   String username, String password,
                   TaxiBuilder taxiBuilder){

        // Richiamo il costruttore della classe astratta UserAccount
        super(fiscalCode, firstName, lastName, dateOfBirth, genderType, email, username, password);

        // inizializzazione delle variabili d'istanza
        this.taxiBuilder = taxiBuilder;

    }

    /**
     * Costruttore di un Gestore
     * @param username username utente
     * @param password password utente
     * */
    public Handler(String username, String password){

        // Richiamo il costruttore della classe astratta UserAccount
        super(username, password);
    }

    //==================================================
    //                      Setter
    //==================================================

    /**
     * Setter builder Taxi dell'handler
     * @param taxiBuilder Builder del taxi
     * @see TaxiBuilder
     * */
    public void setTaxiBuilder(TaxiBuilder taxiBuilder){
        this.taxiBuilder = taxiBuilder;
    }

    //==================================================
    //                      Metodi
    //==================================================
    /**
     * Metodo atto a costruire un oggetto complesso di tipo Taxi
     * @param licensePlate Targa del Taxi
     * @param brandType Nome brand del Taxi
     * @param modelName Nome del modello del Taxi
     * @param capacity Capacità del Taxi in termini di persone trasportabili
     * @param fuelType tipo di carburante del Taxi
     * @param taxiDriver Tassista
     * @see BrandType
     * @see FuelType
     * @see TaxiDriver
     * */
    public void buildTaxi(String licensePlate, BrandType brandType,
                          String modelName, int capacity,
                          FuelType fuelType, TaxiDriver taxiDriver){

        // Build delle variabili d'istanza
       taxiBuilder.buildLicensePlate(licensePlate);
       taxiBuilder.buildBrandName(brandType);
       taxiBuilder.buildModelName(modelName);
       taxiBuilder.buildCapacity(capacity);
       taxiBuilder.buildFuelType(fuelType);
       taxiBuilder.buildTaxiDriver(taxiDriver);

    }
}