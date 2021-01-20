package com.robertovecchio.controller.dialog;

import com.robertovecchio.model.graph.node.Node;
import com.robertovecchio.model.graph.node.WaitingStation;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.LinkedList;

/**
 * Classe che gestisce la view (dialog) utile a mostrare il percorso da seguire
 * @author robertovecchio
 * @version 1.0
 * @since 15/01/2021
 * */
public class ShowPathController {
    /**
     * VBox associato alla View
     * @see VBox
     * */
    @FXML
    private VBox vBoxContainer;

    /**
     * Metodo utile ad inizializzare la View differentemente in base ad una linkedList di nodi passata come parametro
     * di input
     * @param nodes LinkedList di nodi attraverso la quale possiamo popolare la View
     * @see LinkedList
     * @see Node
     * */
    public void init(LinkedList<Node> nodes){

        /* Per ogni nodo nella linkedList */
        for (Node node : nodes){

            /* Essendo WaitingStation direttamente dipendente da Node, posssiamo effettuare un'operazine di Casting */
            WaitingStation waitingStation = (WaitingStation) node;

            /* Istanziamo opportunamente una Label con relativo padding */
            Label label = new Label("Prosegui per: "  + waitingStation.getStreetName() + ", " +  waitingStation.getStreetNumber());
            label.setPadding(new Insets(10,0,10,0));

            /* Aggiungiamo al VBox la label appena creata */
            vBoxContainer.getChildren().add(label);
        }
    }
}
