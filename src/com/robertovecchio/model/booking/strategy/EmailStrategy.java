package com.robertovecchio.model.booking.strategy;

import com.robertovecchio.model.booking.Booking;
import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.user.Customer;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class EmailStrategy implements OrderStrategy {
    private String email;

    public EmailStrategy(String email){
        this.email = email;
    }

    public void setEmail(String email){
        this.email = email;
    }

    public String getEmail(){
        return this.email;
    }

    @Override
    public void book(Booking booking){
        Customer user = (Customer) TaxiFinderData.getInstance().getCurrentUser();
        // Mediator Pattern
        user.send(booking);

        Platform.runLater(()->{
            // Mostriamo l'errore
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Prenotazione Email", ButtonType.OK);
            alert.setHeaderText("Prenotazione avvenuta con successo");
            alert.setContentText("Complimenti hai effettuato un ordine tramite Email");

            Optional<ButtonType> result = alert.showAndWait();
            System.out.println(result);
        });
    }
}
