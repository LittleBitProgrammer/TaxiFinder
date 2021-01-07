package com.robertovecchio.model.user;

//Import
import java.time.LocalDate;
import java.util.Objects;

/**
 * Questa classe ha la responsabilità di astrarre un utente generico, quindi astratto, il quale non potrà essere
 * istanziato ma utile a definire attributi comuni alle classi che erediteranno da quest'ultima
 * @author robertovecchio
 * @version 1.0
 * @since 7/01/2021
 * */
public abstract class UserAccount {
    //==================================================
    //               Variabili d'istanza
    //==================================================
    private String fiscalCode;     // Codice Fiscale
    private String firstName;      // Nome
    private String lastName;       // Cognome
    /**@see java.time.LocalDate*/
    private LocalDate dateOfBirth; // Data di nascita
    /**@see GenderType */
    private GenderType genderType; // Genere sessuale
    private String email;          // Email utente
    private String username;       // Username utente
    private String password;       // Password utente

    //==================================================
    //                   Costruttori
    //==================================================
    /**
     * Costruttore di un utente
     * @param fiscalCode codice fiscale utente
     * @param firstName nome utente
     * @param lastName cognome utente
     * @param dateOfBirth data di nascita utente
     * @param genderType genere sessuale utente
     * @param email email utente
     * @param username username utente
     * @param password password utente
     * @see LocalDate
     * @see GenderType
     * */
    public UserAccount(String fiscalCode, String firstName,
                       String lastName, LocalDate dateOfBirth,
                       GenderType genderType, String email,
                       String username, String password){

        // inizializzazione delle variabili d'istanza
        this.fiscalCode = fiscalCode;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.genderType = genderType;
        this.email = email;
        this.username = username;
        this.password = password;
    }

    //==================================================
    //                      Setter
    //==================================================

    /**
     * Setter del codice fiscale
     * @param fiscalCode Codice fiscale utente
     * */
    public void setFiscalCode(String fiscalCode){
        this.fiscalCode = fiscalCode;
    }

    /**
     * Setter del nome utente
     * @param firstName Nome utente
     * */
    public void setFirstName(String firstName){
        this.firstName = firstName;
    }

    /**
     * Setter del cognome utente
     * @param lastName Cognome utente
     * */
    public void setLastName(String lastName){
        this.lastName = lastName;
    }

    /**
     * Setter data di nascita utente
     * @see LocalDate
     * @param dateOfBirth Data di nascita utente
     * */
    public void setDateOfBirth(LocalDate dateOfBirth){
        this.dateOfBirth = dateOfBirth;
    }

    /**
     * Setter genere sessuale utente
     * @see GenderType
     * @param genderType Genere sessuale utente
     * */
    public void setGenderType(GenderType genderType){
        this.genderType = genderType;
    }

    /**
     * Setter email utente
     * @param email Email utente
     * */
    public void setEmail(String email){
        this.email = email;
    }

    /**
     * Setter username utente
     * @param username Username utente
     * */
    public void setUsername(String username){
        this.username = username;
    }

    /**
     * Setter password utente
     * @param password Password utente
     * */
    public void setPassword(String password){
        this.password = password;
    }

    //==================================================
    //                      Getter
    //==================================================

    /**
     * Getter codice fiscale
     * @return codice fiscale utente
     * */
    public String getFiscalCode(){
        return this.fiscalCode;
    }

    /**
     * Getter nome utente
     * @return Nome utente
     * */
    public String getFirstName(){
        return this.firstName;
    }

    /**
     * Getter cognome utente
     * @return Cognome utente
     * */
    public String getLastName(){
        return this.lastName;
    }

    /**
     * Getter data di nascita utente
     * @see LocalDate
     * @return Data di nascita utente
     * */
    public LocalDate getDateOfBirth(){
        return this.dateOfBirth;
    }

    /**
     * Getter genere sessuale utente
     * @see GenderType
     * @return Genere sessuale utente
     * */
    public GenderType getGenderType(){
        return this.genderType;
    }

    /**
     * Getter email utente
     * @return Email utente
     * */
    public String getEmail(){
        return this.email;
    }

    /**
     * Getter username utente
     * @return Username utente
     * */
    public String getUsername(){
        return this.username;
    }

    /**
     * Getter password utente
     * @return Password utente
     * */
    public String getPassword(){
        return this.password;
    }

    //==================================================
    //                Metodi Sovrascritti
    //==================================================

    /**
     * Override del metodo to String atto a creare una stringa dato un oggetto di tipo UserAccount
     * @return Stringa dell'oggetto di tipo UserAccount
     * */
    @Override
    public String toString() {
        return "UserAccount{" +
                "fiscalCode='" + fiscalCode + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", genderType=" + genderType +
                ", email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    /**
     * Override del metodo equals atto a constatare l'uguaglianza di due oggetti di tipo UserAccount
     * @return se i due oggetti sono uguali ritorna true, altrimenti false*/
    @Override
    public boolean equals(Object o) {
        // se hanno la stessa reference ritorna true
        if (this == o) return true;

        // se l'oggetto inserito non è istanza di userAccount ritorna false
        if (!(o instanceof UserAccount)) return false;

        // dichiaro un tipo UserAccount castandolo opportunamente e confronto le stringhe codice fiscale
        UserAccount that = (UserAccount) o;
        return fiscalCode.equals(that.fiscalCode);
    }

    /**
     * Override del metodo hascode
     * @return il valore intero rappresentato dall'oggetto*/
    @Override
    public int hashCode() {
        return Objects.hash(fiscalCode);
    }
}