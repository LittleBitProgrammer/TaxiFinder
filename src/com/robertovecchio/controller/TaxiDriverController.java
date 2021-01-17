package com.robertovecchio.controller;

import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.user.TaxiDriver;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Classe che gestisce la main View del Tassista
 * @author robertovecchio
 * @version 1.0
 * @since 14/01/2021
 * */
public class TaxiDriverController {
    //==================================================
    //               Variabili d'istanza
    //==================================================

    //DB
    private final TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    //==================================================
    //               Variabili FXML
    //==================================================
    @FXML
    VBox vBoxTopContainer;
    @FXML
    StackPane stackContainer;


    //==================================================
    //               Variabili Statiche
    //==================================================

    private static final String mainControllerFile = "src/com/robertovecchio/view/fxml/main.fxml";

    //==================================================
    //               Inizializzazione
    //==================================================
    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize() {
        // Aggiungiamo il menuBar al vBox
        this.vBoxTopContainer.getChildren().addAll(compositeHandlerMEnuBar(), compositeToolBar());
    }

    private HBox compositeHandlerMEnuBar(){

        // Inizializzo i MenuItem
        MenuItem moveToPerking = new MenuItem("segnala che sei in un altro parcheggio");

        // Inizializzo i diversi Menu
        Menu home = new Menu();
        Menu parking = new Menu("Parcheggio");
        Menu logout = new Menu();

        // Creo delle Label da inserire nei menu home e logout
        Label homeLabel = new Label("Home");
        Label exitLabel = new Label("Logout");

        // Imposto il colore delle label a bianco
        homeLabel.setTextFill(Color.WHITE);
        exitLabel.setTextFill(Color.WHITE);

        // Inserisco le Label nei menu
        home.setGraphic(homeLabel);
        logout.setGraphic(exitLabel);

        // Inizializziamo un menuBar
        MenuBar menuBar = new MenuBar();

        // Incapsuliamo i diversi menuItem nei corrispettivi menu
        parking.getItems().add(moveToPerking);

        // incapsuliamo i diversi menu nel menuBar
        menuBar.getMenus().addAll(home, parking, logout);

        // Aggiungiamo un'azione quando viene premuto home
        homeLabel.setOnMouseClicked(actionEvent -> System.out.println("Home premuto"));

        // Aggiungiamo un'azione quando moveToParking viene premuto
        moveToPerking.setOnAction(actionEvent -> {
            System.out.println("muoviti premuto");
        });

        // Aggiungiamo un'azione quando Logout viene premuto
        exitLabel.setOnMouseClicked(actionEvent -> UtilityController.navigateTo(mainControllerFile, "Taxi Finder",
                "Errore reperimento interfaccia", exitLabel));

        // Inizializziamo un menubar di destra
        MenuBar rightBar = new MenuBar();

        TaxiDriver user = (TaxiDriver) taxiFinderData.getCurrentUser();

        Circle circle = new Circle(6,6, 6);

        switch (user.getState()){
            case FREE -> circle.setFill(Color.LIGHTGREEN);
            case OCCUPIED -> circle.setFill(Color.RED);
        }

        Menu state = new Menu(user.getState().getTranslation(), circle);

        // Aggiungiamo un nuovo menu al menu bar di destra
        rightBar.getMenus().addAll(state);

        // Inizializziamo una region
        Region spacer = new Region();

        // aggiungiamo il foglio di stile
        spacer.getStyleClass().add("menu-bar");

        // Impostiamo alcune proprietà dell'hbox
        HBox.setHgrow(spacer, Priority.SOMETIMES);

        // aggiungiamo elementi all'hbox
        HBox menubars = new HBox(menuBar, spacer, rightBar);

        return menubars;
    }

    private ToolBar compositeToolBar() {

        // Inizializzo la toolBar
        ToolBar toolBar = new ToolBar();

        // inizializzo i Button
        Button orders = new Button("Visualizza ordini effettuattuati");
        Button newOrder = new Button("Nuovo ordine in pendenza");

        // Aggiungiamo i button alla toolbar
        toolBar.getItems().addAll(orders, newOrder);

        return toolBar;
    }
}
