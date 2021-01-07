package com.robertovecchio.model.user;

//Import
import java.time.LocalDate;

/**
 * Questa classe rappresenta uno degli attori del sistema software Taxi finder e si occupa di astrarre il concetto
 * di cliente
 * @author robertovecchio
 * @version 1.0
 * @since 7/01/2021
 * */
public class Customer extends UserAccount{

    //==================================================
    //               Variabili d'istanza
    //==================================================
    private String phoneNumber; // Numero telefonico utente

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
     * @param phoneNumber numero telefonico utente
     * @see LocalDate
     * @see GenderType
     * */
    public Customer(String fiscalCode, String firstName,
                    String lastName, LocalDate dateOfBirth,
                    GenderType genderType, String email,
                    String username, String password,
                    String phoneNumber){

        // Richiamo il costruttore della classe astratta UserAccount
        super(fiscalCode, firstName, lastName, dateOfBirth, genderType, email, username, password);

        // inizializzazione delle variabili d'istanza
        this.phoneNumber = phoneNumber;
    }

    //==================================================
    //                      Setter
    //==================================================

    /**
     * Setter password utente
     * @param phoneNumber Password utente
     * */
    public void setPhoneNumber(String phoneNumber){
        this.phoneNumber = phoneNumber;
    }

    //==================================================
    //                      Getter
    //==================================================
    /**
     * Getter numero di telefono
     * @return Numero di telefono utente
     * */
    public String getPhoneNumber(){
        return this.phoneNumber;
    }

    @Override
    public String toString() {
        return super.toString() + "\nCustomer{" +
                "phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}