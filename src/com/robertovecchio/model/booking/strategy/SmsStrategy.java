package com.robertovecchio.model.booking.strategy;

import com.robertovecchio.model.booking.Booking;
import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.user.Customer;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class SmsStrategy implements OrderStrategy{
    private String sms;

    public SmsStrategy(String sms){
        this.sms = sms;
    }

    public void setSms(String sms){
        this.sms = sms;
    }

    public String getSms(){
        return this.sms;
    }

    @Override
    public void book(Booking booking){
        Customer user = (Customer) TaxiFinderData.getInstance().getCurrentUser();
        // Mediator Pattern
        user.send(booking);

        Platform.runLater(()->{
            // Mostriamo l'errore
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Prenotazione SMS", ButtonType.OK);
            alert.setHeaderText("Prenotazione avvenuta con successo");
            alert.setContentText("Complimenti hai effettuato un ordine tramite SMS");

            Optional<ButtonType> result = alert.showAndWait();
            System.out.println(result);
        });
    }
}
