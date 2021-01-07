package com.robertovecchio.model.user;

//Import
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
    private String licenseNumber; // Numero di licenza utente

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
     * @see LocalDate
     * @see GenderType
     * */
    public TaxiDriver(String fiscalCode, String firstName,
                    String lastName, LocalDate dateOfBirth,
                    GenderType genderType, String email,
                    String username, String password,
                    String licenseNumber){

        // Richiamo il costruttore della classe astratta UserAccount
        super(fiscalCode, firstName, lastName, dateOfBirth, genderType, email, username, password);

        // inizializzazione delle variabili d'istanza
        this.licenseNumber = licenseNumber;
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

    //==================================================
    //                      Getter
    //==================================================
    /**
     * Getter numero di licenza taxi
     * @return Numero di licenza taxi
     * */
    public String getLicenseNumber(){
        return this.licenseNumber;
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
