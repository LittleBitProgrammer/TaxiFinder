package com.robertovecchio.model.booking;

/**
 * Questo enum serve a specificare se una prenotazione è in lavorazione o meno, utile ai fini della memorizzazione
 * @author robertovecchio
 * @version 1.0
 * @since 07/01/2021
 * */
public enum OrderState {
    /**
     * Caso ordine IN LAVORAZIONE
     * */
    WAITING("In Lavorazione"),
    /**
     * Caso ordine ACCETTATO
     * */
    ACCEPTED("Corsa accettata");

    /**
     * Stringa tradotta dell'enum
     */
    private String translation;

    /**
     * Metodo costruttore che accetta dei valori default per impostare la stringa dell'enum nella lingua preferita
     * @param translation traduzione sotto forma di stringa dell'enum
     * */
    OrderState(String translation){
        this.translation = translation;
    }


    /**
     * Metodo costruttore di "Default"
     * */
    OrderState(){/* Vuoto */}

    /**
     * Metodo Setter della traduzione
     * @param translation traduzione sotto forma di stringa dell'enum
     * */
    public void setTranslation(String translation){
        this.translation = translation;
    }

    /**
     * Metodo Getter della traduzione
     * @return traduzione sotto forma di stringa dell'enum
     * */
    public String getTranslation(){
        return this.translation;
    }
}