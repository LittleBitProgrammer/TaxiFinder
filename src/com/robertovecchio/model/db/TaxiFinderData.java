package com.robertovecchio.model.db;

import com.robertovecchio.model.user.Customer;
import com.robertovecchio.model.user.GenderType;
import com.robertovecchio.model.user.Handler;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private final static String customerFileName = "files/customer.txt";        // Percorso file dei clienti
    private final static String handlerFileName = "files/handler.txt";          // Percorso file dei gestori
    /**
     * Map utile ad associare ad ogni Stringa un enum
     * @see Map
     * @see GenderType
     */
    private final Map<String,GenderType> genders;
    /**
     * Formatter per la gestione delle date
     * @see DateTimeFormatter
     * */
    private final DateTimeFormatter dateTimeFormatter;
    /**
     * lista osservabile di clienti
     * @see ObservableList
     * @see Customer
     * */
    private final ObservableList<Customer> customers;
    /**
     * lista osservabile di gestori
     * @see List
     * @see Handler
     */
    private List<Handler> handlers;

    //==================================================
    //                   Costruttori
    //==================================================

    /**
     * Metodo costruttore privato volto alla realizzazione di un Singleton Pattern - versione Eager
     * */
    private TaxiFinderData(){
        // Inizializzo le collections
        this.customers = FXCollections.observableArrayList();
        this.handlers = new ArrayList<>();
        this.genders = new HashMap<>();

        // Popolo l'hasmap
        genders.put("UOMO", GenderType.MALE);
        genders.put("DONNA", GenderType.FEMALE);
        genders.put("ALTRO", GenderType.OTHER);

        // Inizializzo il formatter
        this.dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

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
    //                 Metodi SETTER
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
     * Metodo setter degli handlers
     * @param handlers Gestori da impostare
     * @see List
     * @see Handler
     */
    public void setHandlers(List<Handler> handlers){
        this.handlers = handlers;
    }

    //==================================================
    //                 Metodi GETTER
    //==================================================

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
     * Metodo Getter degli handler
     * @return la lista dei gestori
     * @see List
     * @see Handler
     */
    public List<Handler> getHandlers(){
        return this.handlers;
    }

    //==================================================
    //                 Metodi ADDER
    //==================================================

    /**
     * Metodo che aggiunge un cliente all'observablelist di clienti
     * @param customer Cliente da aggiungere
     * @see Customer
     */
    public void addCustomer(Customer customer){
        this.customers.add(customer);
    }

    /**
     * Metodo che aggiunge un handler alla lista diegli handler
     * @param handler Gestore da aggiungere
     * @see Handler
     */
    public void addHandler(Handler handler){
        this.handlers.add(handler);
    }

    //==================================================
    //                 Metodi REMOVER
    //==================================================

    /**
     * Metodo che rimuove un cliente dall'observablelist di clienti
     * @param customer Cliente da rimuovere
     * @see Customer
     */
    public void removeCustomer(Customer customer){
        this.customers.remove(customer);
    }

    /**
     * Metodo che rimuove un handler dalla lista di handlers
     * @param handler Gestore da rimuovere
     * @see Handler
     */
    public void removeHandler(Handler handler){
        this.handlers.remove(handler);
    }

    //==================================================
    //                 Metodi STORE
    //==================================================

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

    //==================================================
    //                 Metodi LOADER
    //==================================================

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

    public void loadHandlers() throws IOException{
        // Costruiamo un path sfruttando la stringa che compone il percorso verso il file degli handler
        Path path = Paths.get(handlerFileName);

        // Inizializziamo una stringa vuota
        String input;

        // try with resources viene sfruttato per chiamare automaticamente il metodo close
        // Inizializziamo un buffered reader per la lettura del file, sfruttando path per il suo costruttore
        try (BufferedReader br = Files.newBufferedReader(path)){
            // Inizializzo input con la stringa letta lungo tutta la linea, questa però viene
            // reinizializzata ad ogni ciclo con il valore alla riga successiva, permettendo l'iterazione
            // su tutto il file
            while ((input = br.readLine()) != null){
                // Inizializzo un array di stringhe sfruttando il REGEX PATTERN del metodo split
                String[] handlerPieces = input.split(" - ");

                // Inizializzo i valori utili al costruttore di handler
                String fiscalCode = handlerPieces[0];
                String name = handlerPieces[1];
                String surname = handlerPieces[2];
                LocalDate dateOfBirth = LocalDate.parse(handlerPieces[3],dateTimeFormatter);
                GenderType genderType = this.genders.get(handlerPieces[4]);
                String email = handlerPieces[5];
                String username = handlerPieces[6];
                String password = handlerPieces[7];

                Handler newHandler = new Handler(fiscalCode, name, surname, dateOfBirth,
                                              genderType, email, username, password);

                this.addHandler(newHandler);
            }
        }
    }
}