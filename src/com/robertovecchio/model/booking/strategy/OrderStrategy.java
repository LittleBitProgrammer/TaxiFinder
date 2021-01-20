package com.robertovecchio.model.booking.strategy;

import com.robertovecchio.model.booking.Booking;

/**
 * Intefaccia che rappresenta lo strategy da cui erediteranno i concreteStrategy
 * @author robertovecchio
 * @version 1.0
 * @since 15/01/2021
 */
public interface OrderStrategy {
    /**
     * Metodo astratto che permette di concretizzare delle prenotazioni con strategie differenti
     * @param booking Prenotazione da effettuare
     */
    void book(Booking booking);
}
