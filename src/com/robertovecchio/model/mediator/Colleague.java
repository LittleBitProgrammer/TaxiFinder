package com.robertovecchio.model.mediator;

import com.robertovecchio.model.booking.Booking;

public interface Colleague {
    void send(Booking booking);
}
