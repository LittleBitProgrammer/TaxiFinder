package com.robertovecchio.model.booking;

import com.robertovecchio.model.graph.node.Node;
import com.robertovecchio.model.user.Customer;
import com.robertovecchio.model.user.TaxiDriver;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class Booking implements Serializable {

    private final static long serialVersionUID = 13L;

    private LocalDate orderDate;
    private LocalTime orderTime;
    private Node from;
    private Node to;
    private Customer customer;
    private TaxiDriver driver;
    private ArrivalModality arrivalModality;

    public Booking(LocalDate orderDate, LocalTime orderTime, Node from, Node to,
                   Customer customer, TaxiDriver driver, ArrivalModality arrivalModality) {
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.from = from;
        this.to = to;
        this.customer = customer;
        this.driver = driver;
        this.arrivalModality = arrivalModality;
    }

    public Booking(LocalDate orderDate, LocalTime orderTime, Node from, Node to,
                   Customer customer) {
        this(orderDate, orderTime, from, to, customer,null, ArrivalModality.NONE);
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

    public Node getFrom() {
        return from;
    }

    public void setFrom(Node from) {
        this.from = from;
    }

    public Node getTo() {
        return to;
    }

    public void setTo(Node to) {
        this.to = to;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public TaxiDriver getDriver() {
        return driver;
    }

    public void setDriver(TaxiDriver driver) {
        this.driver = driver;
    }

    public ArrivalModality getArrivalModality() {
        return arrivalModality;
    }

    public void setArrivalModality(ArrivalModality arrivalModality) {
        this.arrivalModality = arrivalModality;
    }
}
