package com.robertovecchio.model.user;

//Import
import com.robertovecchio.model.booking.Booking;
import com.robertovecchio.model.mediator.Colleague;
import com.robertovecchio.model.mediator.TaxiCenter;
import com.robertovecchio.model.veichle.builderTaxi.Taxi;

import java.time.LocalDate;

/**
 * Questa classe rappresenta uno degli attori del sistema software Taxi finder e si occupa di astrarre il concetto
 * di TaxiDriver
 * @author robertovecchio
 * @version 1.0
 * @since 7/01/2021*/
public class TaxiDriver extends UserAccount implements Colleague {
    //==================================================
    //               Variabili d'istanza
    //==================================================
    /**Numero di licenza utente*/
    private String licenseNumber;
    /**Taxi associato al tassista
     * @see Taxi*/
    private Taxi taxi;
    private State state;
    private final TaxiCenter taxiCenter;

    //==================================================
    //                   Costruttori
    //==================================================

    /**
     * Costruttore di un Tassista
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
     * @see Taxi
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
        state = State.FREE;
        taxiCenter = new TaxiCenter();
    }

    /**
     * Costruttore di un Tassista
     * @param username username utente
     * @param password password utente
     * @param taxi Taxi del tassista
     * @see Taxi
     * */
    public TaxiDriver(String username, String password, Taxi taxi){

        // Richiamo il costruttore della classe astratta UserAccount
        super(username, password);

        // inizializzazione delle variabili d'istanza
        this.taxi = taxi;
        taxiCenter = new TaxiCenter();
    }

    /**
     * Costruttore di un Tassista
     * @param username username utente
     * @param password password utente
     * */
    public TaxiDriver(String username, String password){

        // Richiamo il costruttore della classe astratta UserAccount
        super(username, password);
        taxiCenter = new TaxiCenter();
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

    public void setState(State state) {
        this.state = state;
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

    public State getState() {
        return state;
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


    @Override
    public void send(Booking booking) {
        // stub
    }
}
