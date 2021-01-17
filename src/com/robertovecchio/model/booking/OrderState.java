package com.robertovecchio.model.booking;

public enum OrderState {
    WAITING("In Lavorazione"),
    ACCEPTED("Corsa accettata");

    private String translation; // stringa tradotta dell'enum

    OrderState(String translation){
        this.translation = translation;
    }


    OrderState(){}

    public void setTranslation(String translation){
        this.translation = translation;
    }

    public String getTranslation(){
        return this.translation;
    }
}
