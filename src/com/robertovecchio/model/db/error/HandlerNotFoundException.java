package com.robertovecchio.model.db.error;

public class HandlerNotFoundException extends Exception{

    private final static long serialVersionUID = 3L;

    @Override
    public String getMessage(){
        return "Handler non Trovato";
    }
}
