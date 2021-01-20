package com.robertovecchio.model.booking.strategy;

import com.robertovecchio.model.booking.Booking;
import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.user.Customer;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import java.util.Optional;

/**
 * Classe che rappresenta una concreteStrategy per lo Strategy Pattern. In particolare questa classe astrae il concetto
 * Prenotazione di una corsa tramite EMAIL
 * @author robertovecchio
 * @version 1.0
 * @since 15/01/2021
 * @see OrderStrategy
 */
public class EmailStrategy implements OrderStrategy {
    /**
     * Email con cui si vuole inviare la prenotazione
     */
    private String email;

    /**
     * Costruttore di EmailStrategy
     * @param email Smail che si vuole utilizzare per la prenotazione
     */
    public EmailStrategy(String email){
        this.email = email;
    }

    //==================================================
    //                  Setter
    //==================================================

    /**
     * Metodo setter di EMail
     * @param email Email che si vuole impostare
     */
    public void setEmail(String email){
        this.email = email;
    }

    //==================================================
    //                  Getter
    //==================================================

    /**
     * Metodo Getter dell'email
     * @return Email con cui si vuole effettuare la prenotazione
     */
    public String getEmail(){
        return this.email;
    }

    //==================================================
    //                  METODI
    //==================================================

    /**
     * Metodo utile ad effettuare una prenotazione tramite Email
     * @param booking Prenotazione da effettuare
     * @see Booking
     */
    @Override
    public void book(Booking booking){
        /* Inizializziamo l'utente corrente */
        Customer user = (Customer) TaxiFinderData.getInstance().getCurrentUser();

        /* Mediator Pattern */
        user.send(booking);

        Platform.runLater(()->{
            /* Nel caso fosse presente un errore, lo mostriamo a schermo attraverso un alert */
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Prenotazione Email", ButtonType.OK);
            alert.setHeaderText("Prenotazione avvenuta con successo");
            alert.setContentText("Complimenti hai effettuato un ordine tramite Email");

            Optional<ButtonType> result = alert.showAndWait();
            System.out.println(result);
        });
    }
}
