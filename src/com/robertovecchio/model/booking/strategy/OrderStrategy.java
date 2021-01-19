package com.robertovecchio.model.booking.strategy;

import com.robertovecchio.model.booking.Booking;

public interface OrderStrategy {
    void book(Booking booking);
}
