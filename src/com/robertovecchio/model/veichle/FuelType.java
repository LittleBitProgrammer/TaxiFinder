package com.robertovecchio.model.veichle;

/**
 * Questo enum serve a specificare il tipo di benzina del Taxi, utile ai fini della memorizzazione
 * @author robertovecchio
 * @version 1.0
 * @since 07/01/2021
 * */
public enum FuelType {
    /**
     * Caso tipo carburante BENZINA
     * */
    GASOLINE("BENZINA"),
    /**
     * Caso tipo carburante DIESEL
     * */
    DIESEL("DIESEL"),
    /**
     * Caso tipo carburante, impianto a GAS
     * */
    GAS("IMPIANTO A GAS"),
    /**
     * Caso tipo carburante ENERGIA ELETTRICA
     * */
    ELECTRIC_ENERGY("ENERGIA ELETTRICA");

    private String translation; // stringa tradotta dell'enum

    /**
     * Metodo costruttore che accetta dei valori default per impostare la stringa dell'enum nella lingua preferita
     * @param translation traduzione sotto forma di stringa dell'enum
     * */
    FuelType(String translation){
        this.translation = translation;
    }

    /**
     * Metodo costruttore di "Default"
     * */
    FuelType(){}

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
