package com.robertovecchio.model.user;

/**
 * Enum che astrae il concetto dello stato di un tassista, il quale, in un determinato momento può essere occupato o
 * libero
 * @author robertovecchio
 * @version 1.0
 * @since 01/15/2021
 */
public enum State {
    /**
     * Caso dell'enum LIBERO
     */
    FREE("Libero"),
    /**
     * Caso dell'enum OCCUPATO
     */
    OCCUPIED("Occupato");

    /**
     * Stringa tradotta dell'enum
     */
    private String translation;

    /**
     * Metodo costruttore che accetta dei valori default per impostare la stringa dell'enum nella lingua preferita
     * @param translation traduzione sotto forma di stringa dell'enum
     * */
    State(String translation){
        this.translation = translation;
    }

    /**
     * Metodo costruttore di "Default"
     * */
    State(){/* vuoto */}

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
