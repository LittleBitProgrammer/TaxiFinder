package com.robertovecchio.view;

// import javafx necessari
import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.graph.edge.observer.Edge;
import com.robertovecchio.model.graph.edge.observer.SensorHandler;
import javafx.application.Application; // Gestisce il lifecycle di un'applicazione
import javafx.fxml.FXMLLoader; // Loader di fxml
import javafx.scene.Parent; // Nodo Primario
import javafx.scene.Scene; // Astrae il concetto di View
import javafx.stage.Stage; // Astrae il concetto di finestra desktop
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

/**
 * La classe Main ha la responsabilità di lanciare l'applizione in modalità finestra, gestendo alcune responsabilità
 * come il comportamento che questa deve avere, quando viene:
 *
 * - inizializzata (init)
 * - lanciata (start)
 * - chiusa (stop)
 *
 * Questi metodi sono ereditati da Application classe dedita ad astrarre il concetto di applicazione desktop.
 * @author Roberto Vecchio
 * @since 05/01/2021
 * @version 1.0
 * @see javafx.application.Application
 */
public class Main extends Application {

    /**
     * Variabile statica che rappresenta il percorso verso la view principale
     */
    private static final String rootFilename = "src/com/robertovecchio/view/fxml/main.fxml";
    /**
     * Istanza del database
     * @see TaxiFinderData
     * */
    private final TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    /**
     * Metodo Triggerato non appena viene inizializzata l'applicazione, ereditato da application
     * */
    @Override
    public void init(){
        System.out.println("Programma Inizializzato");
        /* Indipendentemente dall'istanza del tipo di utente, il programma all'inizio deve inizializzare alcune
        *  liste:
        *
        *  1 - Lista Clienti
        *  2 - Lista Handler
        * */
        try{
            taxiFinderData.loadCustomers();
            taxiFinderData.loadHandlers();
            taxiFinderData.loadTaxiDrivers();
            taxiFinderData.loadBookings();
            taxiFinderData.loadParkings();
            taxiFinderData.loadWaitingStations();
            taxiFinderData.loadGraph();
        } catch (IOException | ClassNotFoundException e){
            System.out.println("Reperimento dati non riuscito");
        }
    }

    /**
     * @param primaryStage stage primario da renderizzare
     * @exception Exception il metodo start può lanciare un'eccezione
     * @see Stage*/
    @Override
    public void start(Stage primaryStage) throws Exception{

        // DEBUG GRAFO

        taxiFinderData.getGraph().printGraph();

        attachSensorToRoad();

        /* Parent è il nodo padre ottenuto attraverso il valore di ritorno del metodo statico load, sfruttando
        *  la classe FXML loader */
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(new FileInputStream(rootFilename));

        // Impostiamo il nome della finestra
        primaryStage.setTitle("Taxi Finder");

        // Impostiamo lo schermo a full screen
        primaryStage.setMaximized(true);

        // Impostiamo lo stile a caspian
        setUserAgentStylesheet(STYLESHEET_CASPIAN);

        /* impostiamo una nuova View passandogli come parametri di input del costruttore il nodo padre e la relativa
         * risoluzione in termini di pixel */
        primaryStage.setScene(new Scene(root, 800, 600));

        // mostriamo a schermo la finestra appena impostata
        primaryStage.show();
    }

    /**
     * @param args
     * Entry point di qualsiasi programma java, in questo caso della nostra applicazione*/
    public static void main(String[] args) {
        /* lanciamo l'applicazione sfruttando il metodo statico della classe Apllication: launch(), il quale prenderà
        *  in considerazione come parametri di input gli argomenti passati dal metodo main*/
        launch(args);
    }

    /**
     * Metodo Triggerato non appena viene stoppata l'applicazione, ereditato da application
     */
    @Override
    public void stop(){
        System.out.println("Programma in chiusura");
    }

    /**
     * Metodo che si occupa di attaccare alle diverse strade presenti nel grafo sei sensori che aggiorneranno lo stato
     * del traffico
     */
    private void attachSensorToRoad(){
        SensorHandler sensorHandler = new SensorHandler();

        for (Edge edge : taxiFinderData.getTrafficGraph().getEdges()){
            sensorHandler.attach(edge);
        }

        new Timer().schedule(
                new TimerTask() {

                    @Override
                    public void run() {
                       // System.out.println("=============================");
                        Random random = new Random();
                        sensorHandler.setTrafficState(random.nextInt(101));
                    }
                }, 0, 30000);
    }
}