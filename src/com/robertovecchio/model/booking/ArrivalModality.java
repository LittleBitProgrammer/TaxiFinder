package com.robertovecchio.model.booking;

/**
 * Enum che astrae le diverse modalità di raggiungimento del tassista verso la postazione del cliente
 * @author robertovecchio
 * @version 1.0
 * @since 15/01/2021
 */
public enum ArrivalModality {
    /**
     * Caso modalità di arrivo con PERCORSO BREVE
     * */
    TIME,
    /**
     * Caso modalità di arrivo con PERCORSO CON MENO TRAFFICO
     */
    LENGTH
}
