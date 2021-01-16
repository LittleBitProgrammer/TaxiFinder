package com.robertovecchio.model.booking;

import com.robertovecchio.model.graph.node.Node;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public class Booking {
    private LocalDate orderDate;
    private LocalTime orderTime;
    private Node station;

    public Booking(LocalDate orderDate, LocalTime orderTime, Node station){
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.station = station;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalTime orderTime) {
        this.orderTime = orderTime;
    }

    public Node getStation() {
        return station;
    }

    public void setStation(Node station) {
        this.station = station;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking)) return false;
        Booking booking = (Booking) o;
        return Objects.equals(orderDate, booking.orderDate) &&
                Objects.equals(orderTime, booking.orderTime) &&
                Objects.equals(station, booking.station);
    }

    @Override
    public int hashCode() {
        return Objects.hash(orderDate, orderTime, station);
    }
}
