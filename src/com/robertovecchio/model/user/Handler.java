package com.robertovecchio.model.user;

import java.time.LocalDate;

/**
 * Questa classe rappresenta uno degli attori del sistema software Taxi finder*/
public class Handler extends UserAccount{

    //==================================================
    //                   Costruttori
    //==================================================
    public Handler(String fiscalCode, String firstName,
                   String lastName, LocalDate dateOfBirth,
                   GenderType genderType, String email,
                   String username, String password){

        super(fiscalCode, firstName, lastName, dateOfBirth, genderType, email, username, password);
    }
}
