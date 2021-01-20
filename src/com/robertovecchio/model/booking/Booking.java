package com.robertovecchio.model.booking;

import com.robertovecchio.model.graph.node.Node;
import com.robertovecchio.model.user.Customer;
import com.robertovecchio.model.user.TaxiDriver;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Classe che astrae il concetto di prenotazione
 * @author robertovecchio
 * @version 1.0
 * @since 15/01/2021
 */
public class Booking implements Serializable {
    /**
     * Numero seriale utile ai fini della memorizzazione
     */
    @Serial
    private final static long serialVersionUID = 13L;

    //==================================================
    //               Variabili d'istanza
    //==================================================

    /**
     * Data ordine
     * @see LocalDate
     */
    private LocalDate orderDate;
    /**
     * Orario ordine
     * @see LocalTime
     */
    private LocalTime orderTime;
    /**
     * Postazione di partenza
     * @see Node
     */
    private Node from;
    /**
     * Postazione di arrivo
     * @see Node
     */
    private Node to;
    /**
     * Cliente che partecipa alla prenotazione
     * @see Customer
     */
    private Customer customer;
    /**
     * Tassista che partecipa alla prenotazione
     * @see TaxiDriver
     */
    private TaxiDriver driver;
    /**
     * Modalità di recupero cliente
     * @see ArrivalModality
     */
    private ArrivalModality arrivalModality;
    /**
     * Stato dell'ordine
     * @see OrderState
     */
    private OrderState orderState;
    /**
     * Tipologia di prenotazione
     * @see OrderType
     */
    private OrderType orderType;

    //==================================================
    //                   Costruttori
    //==================================================

    /**
     * Costruttore di una prenotazione
     * @param orderDate Data ordine
     * @param orderTime Orario ordine
     * @param from Postazione di partenza
     * @param to Postazione di arrivo
     * @param customer Cliente che partecipa alla prenotazione
     * @param driver Tassista che partecipa alla prenotazione
     * @param arrivalModality Modalità di recupero cliente
     * @param orderState Stato dell'ordine
     * @param orderType Tipologia di prenotazione
     * @see LocalDate
     * @see LocalTime
     * @see Node
     * @see Customer
     * @see TaxiDriver
     * @see ArrivalModality
     * @see OrderState
     * @see OrderType
     */
    public Booking(LocalDate orderDate, LocalTime orderTime, Node from, Node to,
                   Customer customer, TaxiDriver driver, ArrivalModality arrivalModality,
                   OrderState orderState, OrderType orderType) {
        this.orderDate = orderDate;
        this.orderTime = orderTime;
        this.from = from;
        this.to = to;
        this.customer = customer;
        this.driver = driver;
        this.arrivalModality = arrivalModality;
        this.orderState = orderState;
        this.orderType = orderType;
    }

    /**
     * Costruttore di una prenotazione
     * @param orderDate Data ordine
     * @param orderTime Orario ordine
     * @param from Postazione di partenza
     * @param to Postazione di arrivo
     * @param customer Cliente che partecipa alla prenotazione
     * @param orderType Tipologia di prenotazione
     * @see LocalDate
     * @see LocalTime
     * @see Node
     * @see Customer
     * @see OrderType
     */
    public Booking(LocalDate orderDate, LocalTime orderTime, Node from, Node to,
                   Customer customer, OrderType orderType) {
        this(orderDate, orderTime, from, to, customer,null, null, OrderState.WAITING, orderType);
    }

    //==================================================
    //                      Setter
    //==================================================

    /**
     * Metodo setter della data prenotazione
     * @param orderDate Data prenotazione
     * @see LocalDate
     */
    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    /**
     * Metodo sette orario prenotazione
     * @param orderTime Orario prenotazione
     * @see LocalTime
     */
    public void setOrderTime(LocalTime orderTime) {
        this.orderTime = orderTime;
    }

    /**
     * Metodo setter postazione di partenza della prenotazione
     * @param from Postazione di partenza della prenotazione
     * @see Node
     */
    public void setFrom(Node from) {
        this.from = from;
    }

    /**
     * Metodo setter postazione di arrivo della prenotazione
     * @param to Postazione di partenza della prenotazione
     * @see Node
     */
    public void setTo(Node to) {
        this.to = to;
    }

    /**
     * Metodo setter cliente della prenotazione
     * @param customer Cliente della prenotazione
     * @see Customer
     */
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    /**
     * Metodo setter tassista della prenotazione
     * @param driver Tassista della prenotazione
     * @see TaxiDriver
     */
    public void setDriver(TaxiDriver driver) {
        this.driver = driver;
    }

    /**
     * Metodo setter modalità di arrivo presso postazione di partenza
     * @param arrivalModality Modalità di arrivo presso postazione di partenza
     * @see ArrivalModality
     */
    public void setArrivalModality(ArrivalModality arrivalModality) {
        this.arrivalModality = arrivalModality;
    }

    /**
     * Metodo setter stato ordine della prenotazione
     * @param orderState Stato ordine
     * @see OrderState
     */
    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    /**
     * Metodo setter della modalità di prenotazione (SMS, EMAIL)
     * @param orderType MOdalità di prenotazione
     * @see OrderType
     */
    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    //==================================================
    //                      Getter
    //==================================================

    /**
     * Metodo getter data di prenotazione
     * @return Data di prenotazione
     * @see LocalDate
     */
    public LocalDate getOrderDate() {
        return orderDate;
    }

    /**
     * Metodo getter orario di prenotazione
     * @return Orario di prenotazione
     * @see LocalTime
     */
    public LocalTime getOrderTime() {
        return orderTime;
    }

    /**
     * Metodo getter postazione di partenza della prenotazione
     * @return Postazione di partenza della prenotazione
     * @see Node
     */
    public Node getFrom() {
        return from;
    }

    /**
     * Metodo Getter postazione di arrivo della prenotazione
     * @return Postazione di arrivo della prenotazione
     * @see Node
     */
    public Node getTo() {
        return to;
    }

    /**
     * Metodo getter cliente della prenotazione
     * @return Cliente della prenotazione
     * @see Customer
     */
    public Customer getCustomer() {
        return customer;
    }

    /**
     * Metodo getter tassista della prenotazione
     * @return Tassista della prenotazione
     * @see TaxiDriver
     */
    public TaxiDriver getDriver() {
        return driver;
    }

    /**
     * Metodo getter modalità di raggiungimento cliente
     * @return Modalità di raggiungimento cliente
     * @see ArrivalModality
     */
    public ArrivalModality getArrivalModality() {
        return arrivalModality;
    }

    /**
     * Metodo getter stato ordine
     * @return Stato ordine
     * @see OrderState
     */
    public OrderState getOrderState() {
        return orderState;
    }

    /**
     * Metodo getter tipologia prenotazione (SMS, EMAIL)
     * @return Tipologia della prenotazione
     * @see OrderType
     */
    public OrderType getOrderType() {
        return orderType;
    }

    //==================================================
    //               Metodi sovrascritti
    //==================================================

    /**
     * Metodo utile a constatare se due oggetti di tipo Booking sono uguali
     * @param o Oggetto che si vuole confrontare
     * @return Ritorna true se sono uguali, altrimenti false
     * @see Object
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Booking)) return false;
        Booking booking = (Booking) o;
        return Objects.equals(orderDate, booking.orderDate) &&
                Objects.equals(orderTime, booking.orderTime) &&
                Objects.equals(from, booking.from) &&
                Objects.equals(to, booking.to) &&
                Objects.equals(customer, booking.customer);
    }

    /**
     * Metodo hashcode utile per confrontare l'uguaglianza di due oggetti
     * @return Il valore intero dell'istanza
     */
    @Override
    public int hashCode() {
        return Objects.hash(orderDate, orderTime, from, to, customer);
    }
}
