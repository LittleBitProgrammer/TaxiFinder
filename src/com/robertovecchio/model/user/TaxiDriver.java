package com.robertovecchio.model.user;

//Import
import com.robertovecchio.model.veichle.builderTaxi.Taxi;

import java.time.LocalDate;

/**
 * Questa classe rappresenta uno degli attori del sistema software Taxi finder e si occupa di astrarre il concetto
 * di TaxiDriver
 * @author robertovecchio
 * @version 1.0
 * @since 7/01/2021*/
public class TaxiDriver extends UserAccount{
    //==================================================
    //               Variabili d'istanza
    //==================================================
    /**Numero di licenza utente*/
    private String licenseNumber;
    /**Taxi associato al tassista
     * @see Taxi*/
    private Taxi taxi;

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
     * @param licenseNumber numero di licenza taxi
     * @param taxi Taxi del tassista
     * @see LocalDate
     * @see GenderType
     * */
    public TaxiDriver(String fiscalCode, String firstName,
                      String lastName, LocalDate dateOfBirth,
                      GenderType genderType, String email,
                      String username, String password,
                      String licenseNumber, Taxi taxi){

        // Richiamo il costruttore della classe astratta UserAccount
        super(fiscalCode, firstName, lastName, dateOfBirth, genderType, email, username, password);

        // inizializzazione delle variabili d'istanza
        this.licenseNumber = licenseNumber;
        this.taxi = taxi;
    }

    //==================================================
    //                      Setter
    //==================================================

    /**
     * Setter numero di licenza taxi utente
     * @param licenseNumber Numero di licenza taxi
     * */
    public void setLicenseNumber(String licenseNumber){
        this.licenseNumber = licenseNumber;
    }

    /**
     * Setter del Taxi
     * @param taxi Taxi da impostare
     * */
    public void setTaxi(Taxi taxi){
        this.taxi = taxi;
    }

    //==================================================
    //                      Getter
    //==================================================
    /**
     * Getter numero di licenza tassista
     * @return Numero di licenza tassista
     * */
    public String getLicenseNumber(){
        return this.licenseNumber;
    }

    /**
     * Getter del Taxi
     * @return Taxi del tassista
     * */
    public Taxi getTaxi(){
        return this.taxi;
    }

    //==================================================
    //                Metodi Sovrascritti
    //==================================================

    @Override
    public String toString() {
        return super.toString() + "\nTaxiDriver{" +
                "licenseNumber='" + licenseNumber + '\'' +
                '}';
    }
}
