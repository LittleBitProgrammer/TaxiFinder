package com.robertovecchio.model.user;

/**
 * Questo enum serve a specificare il genere sessuale di un utente, utile ai fini della registrazione e memorizzazione
 * @author robertovecchio
 * @version 1.0
 * @since 07/01/2021
 * */
public enum GenderType {
    /**
     * Caso UOMO
     * */
    MALE("UOMO"),
    /**
     * Caso DONNA
     * */
    FEMALE("DONNA"),
    /**
     * Caso ALTRO
     * */
    OTHER("ALTRO");

    private String translation; // stringa tradotta dell'enum

    /**
     * Metodo costruttore che accetta dei valori default per impostare la stringa dell'enum nella lingua preferita
     * @param translation traduzione sotto forma di stringa dell'enum
     * */
    GenderType(String translation){
        this.translation = translation;
    }

    /**
     * Metodo costruttore di "Default"
     * */
    GenderType(){}

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