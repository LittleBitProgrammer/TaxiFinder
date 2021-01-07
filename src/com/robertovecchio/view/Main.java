package com.robertovecchio.view;

// import javafx necessari
import javafx.application.Application; // Gestisce il lifecycle di un'applicazione
import javafx.fxml.FXMLLoader; // Loader di fxml
import javafx.scene.Parent; // Nodo Primario
import javafx.scene.Scene; // Astrae il concetto di View
import javafx.stage.Stage; // Astrae il concetto di finestra desktop

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
     * @param primaryStage stage primario da renderizzare
     * @exception Exception il metodo start può lanciare un'eccezione
     * @see Stage*/
    @Override
    public void start(Stage primaryStage) throws Exception{
        /* Parent è il nodo padre ottenuto attraverso il valore di ritorno del metodo statico load, sfruttando
        *  la classe FXML loader */
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        // Impostiamo il nome della finestra
        primaryStage.setTitle("Hello World");

        /* impostiamo una nuova View passandogli come parametri di input del costruttore il nodo padre e la relativa
         * risoluzione in termini di pixel */
        primaryStage.setScene(new Scene(root, 300, 275));

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
}