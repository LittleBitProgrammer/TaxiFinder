package com.robertovecchio.model.mediator;

import com.robertovecchio.model.booking.Booking;

/**
 * Questa interfaccia è l'astrazione di un concrete mediator, presente nel mediato pattern
 * @author robertovecchio
 * @version 1.0
 * @since 15/01/2021
 */
public interface RadioTaxiCallCenter {
    /**
     * Metodo utile a notificare un Tassista che ha un incarico in corso, in particolare verrà scelto colui che è locato
     * nel parcheggio più vicino
     * @param booking Prenotazione da inviare al tassissta
     */
    void sendTaxiDriver(Booking booking);
    /**
     * Metodo per nitficare il cliente che la prenotazione è andata a buon fine e che il tassista si sta recando presso
     * la postazione scelta dal cliente stesso
     * @param booking Prenotazione aggiornata con i parametri scelti dal tassista
     */
    void notifyCustomer(Booking booking);
}
