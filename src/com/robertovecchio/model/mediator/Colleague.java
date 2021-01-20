package com.robertovecchio.model.mediator;

import com.robertovecchio.model.booking.Booking;

/**
 * Questa interfaccia rappresenta il Collegue del pattern Mediator
 * @author robertovecchio
 * @version 1.0
 * @since 15/01/2021
 */
public interface Colleague {
    /**
     * Tutti i collegue per comunicare con il mediator devono utilizzare il metodo send
     * @param booking Prenotazione che ha bisogno di essere aggiornata e comunicata
     */
    void send(Booking booking);
}
