package com.robertovecchio.model.booking;

/**
 * Questo enum serve a specificare se una prenotazione è avvenuta con SMS o EMAIL, utile ai fini della memorizzazione
 * @author robertovecchio
 * @version 1.0
 * @since 07/01/2021
 * */
public enum  OrderType {
    /**
     * Caso Prenotazione con SMS
     * */
    SMS,
    /**
     * Caso prenotazione con EMAIL
     * */
    EMAIL
}
