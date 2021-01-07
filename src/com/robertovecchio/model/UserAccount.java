package com.robertovecchio.model;

//Import
import java.time.LocalDate;

/**
 * Questa classe ha la responsabilità di astrarre un utente generico, quindi astratto, il quale non potrà essere
 * istanziato ma utile a definire attributi comuni alle classi che erediteranno da quest'ultima*/
public abstract class UserAccount {
    // Variabili d'istanza
    private String fiscalCode;     // Codice Fiscale
    private String firstName;      // Nome
    private String lastName;       // Cognome
    /**@see java.time.LocalDate*/
    private LocalDate dateOfBirth; // Data di nascita

}
