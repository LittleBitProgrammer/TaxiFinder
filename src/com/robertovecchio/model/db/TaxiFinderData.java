package com.robertovecchio.model.db;

import com.robertovecchio.model.user.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.util.ArrayList;

/**
 * Questa classe si occupa di simulare un database attraverso l'utilizzo di file. Inoltre vi è applicato un singleton
 * Che restituisce una singola istanza per evitare la costruzione di molteplici "db"
 * @author robertovecchio
 * @version 1.0
 * @since 09/01/2021
 * */
public class TaxiFinderData {

    //==================================================
    //                   Attributi
    //==================================================

    // Singleton - versione Eager
    private final static TaxiFinderData instance = new TaxiFinderData();
    private final static String customerFileName = "files/customer.txt";        // percorso file dei clienti
    /**
     * lista osservabile di clienti
     * @see ObservableList
     * @see Customer*/
    private final ObservableList<Customer> customers;

    //==================================================
    //                   Costruttori
    //==================================================

    /**
     * Metodo costruttore privato volto alla realizzazione di un Singleton Pattern - versione Eager
     * */
    private TaxiFinderData(){
        this.customers = FXCollections.observableArrayList();
        //this.dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyy");
        System.out.println("Istanza db creata");
    }

    //==================================================
    //                Metodi statici
    //==================================================
    /**
     * Metodo atto a ritornare la singola istanza di TaxiFinderData (Singleton - versione Eager)
     * @return Istanza di TaxiFinderData (singleton)
     * @see TaxiFinderData
     * */
    public static TaxiFinderData getInstance(){
        return instance;
    }

    //==================================================
    //                    Metodi
    //==================================================

    /**
     * Metodo setter dei customers
     * @param customers clienti da impostare
     * @see ObservableList
     * @see Customer
     * */
    public void setCustomers(ObservableList<Customer> customers){
        this.customers.setAll(customers);
    }

    /**
     * Metodo Getter dei Customers
     * @return la lista di customers
     * @see ObservableList
     * @see Customer
     */
    public ObservableList<Customer> getCustomers(){
        return this.customers;
    }

    /**
     * Metodo che aggiunge un cliente all'observablelist di clienti
     * @param customer Cliente da aggiungere
     * @see Customer
     */
    public void addCustomer(Customer customer){
        this.customers.add(customer);
    }

    /**
     * Metodo che aggiunge un cliente dall'observablelist di clienti
     * @param customer Cliente da rimuovere
     * @see Customer
     */
    public void removeCustomer(Customer customer){
        this.customers.remove(customer);
    }

    /**
     * Metodo atto alla memorizzazione dei dati inirenti al cliente (Customer)
     * @exception IOException Questo metodo può lanciare una exception nel caso in cui vi sia un errore di input/output
     * */
    public void storeCustomers() throws IOException{
        // try with resources viene sfruttato per chiamare automaticamente il metodo close
        // Creiamo uno stream serializzato di risorse
        try(ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(customerFileName))) {
            oos.writeObject(new ArrayList<>(this.customers));
        }
    }

    /**
     * Metodo atto a popolare la lista dei clienti
     * @exception ClassNotFoundException questo metodo potrebbe non trovare la classe richiesta
     * @exception IOException Questo metodo potrebbe generare errori di input/output
     * */
    @SuppressWarnings("unchecked")
    public void loadCustomers() throws ClassNotFoundException, IOException{
        // try with resources viene sfruttato per chiamare automaticamente il metodo close
        // sfruttiamo uno stream per deserializzare risorse
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(customerFileName))){
            this.customers.setAll((ArrayList<Customer>) ois.readObject());
        }
    }
}
