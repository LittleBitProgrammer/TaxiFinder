package com.robertovecchio.model.user;

public enum State {
    FREE("Libero"),
    OCCUPIED("Occupato");

    private String translation; // stringa tradotta dell'enum

    State(String translation){
        this.translation = translation;
    }

    /**
     * Metodo costruttore di "Default"
     * */
    State(){}

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
