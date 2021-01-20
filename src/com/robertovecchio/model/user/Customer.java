package com.robertovecchio.model.user;

//Import
import com.robertovecchio.model.booking.Booking;
import com.robertovecchio.model.mediator.Colleague;
import com.robertovecchio.model.mediator.TaxiCenter;

import java.io.Serial;
import java.time.LocalDate;

/**
 * Questa classe rappresenta uno degli attori del sistema software Taxi finder e si occupa di astrarre il concetto
 * di cliente
 * @author robertovecchio
 * @version 1.0
 * @since 7/01/2021
 * @see UserAccount
 * */
public class Customer extends UserAccount implements Colleague {

    /**
     * Numero seriale utile ai fini della memorizzazione
     */
    @Serial
    private final static long serialVersionUID = 1L;

    //==================================================
    //               Variabili d'istanza
    //==================================================

    /**Numero telefonico utente*/
    private String phoneNumber;
    /**
     * Centro taxi con cui comunicherà il cliente
     */
    private final TaxiCenter taxiCenter = new TaxiCenter();

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

    /**
     * @param username username utente
     * @param password password utente
     * @see LocalDate
     * @see GenderType
     * */
    public Customer(String username, String password){

        // Richiamo il costruttore della classe astratta UserAccount
        super(username, password);
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

    //==================================================
    //                Metodi Sovrascritti
    //==================================================

    /**
     * Metodo che restituisce una stringa dell'oggetto corrente
     * @return Ritorna l'oggeto sotto forma di stringa
     */
    @Override
    public String toString() {
        return super.toString() + "\nCustomer{" +
                "phoneNumber='" + phoneNumber + '\'' +
                '}';
    }

    /**
     * Metodo che sfrutta il mediator del mediator pattern per comunicare l'aggiornamento della prenotazione con
     * i parametri scelti dal tassista.
     * @param booking Prenotazione che ha bisogno di essere aggiornata e comunicata
     */
    @Override
    public void send(Booking booking) {
        taxiCenter.sendTaxiDriver(booking);
    }
}