package com.robertovecchio.model.mediator;

import com.robertovecchio.model.booking.Booking;
import com.robertovecchio.model.db.TaxiFinderData;

import java.io.Serializable;

public class TaxiCenter implements RadioTaxiCallCenter, Serializable {

    private final static long serialVersionUID = 15L;

    @Override
    public void sendTaxiDriver(Booking booking) {
        TaxiFinderData.getInstance().addBooking(booking);
    }

    @Override
    public void notifyCustomer() {

    }
}
