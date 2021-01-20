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
 * Prenotazione di una corsa tramite SMS
 * @author robertovecchio
 * @version 1.0
 * @since 15/01/2021
 * @see OrderStrategy
 */
public class SmsStrategy implements OrderStrategy{
    /**
     * SMS con cui si vuole inviare la prenotazione
     */
    private String sms;

    /**
     * Costruttore di SmsStrategy
     * @param sms SMS che si vuole utilizzare per la prenotazione
     */
    public SmsStrategy(String sms){
        this.sms = sms;
    }

    //==================================================
    //                  Setter
    //==================================================

    /**
     * Metodo setter di sms
     * @param sms SMS che si vuole impostare
     */
    public void setSms(String sms){
        this.sms = sms;
    }

    //==================================================
    //                  Getter
    //==================================================

    /**
     * Metodo Getter SMS
     * @return SMS con cui si vuole effettuare la prenotazione
     */
    public String getSms(){
        return this.sms;
    }

    //==================================================
    //                  METODI
    //==================================================

    /**
     * Metodo utile ad effettuare una prenotazione tramite SMS
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
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Prenotazione SMS", ButtonType.OK);
            alert.setHeaderText("Prenotazione avvenuta con successo");
            alert.setContentText("Complimenti hai effettuato un ordine tramite SMS");

            Optional<ButtonType> result = alert.showAndWait();
            System.out.println(result);
        });
    }
}
