package com.robertovecchio.model.mediator;

import com.robertovecchio.model.booking.Booking;

public interface RadioTaxiCallCenter {
    void sendTaxiDriver(Booking booking);
    void notifyCustomer();
}
