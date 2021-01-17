package com.robertovecchio.controller.dialog;

import com.robertovecchio.model.graph.node.Node;
import com.robertovecchio.model.graph.node.WaitingStation;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.LinkedList;

public class ShowPathController {
    @FXML
    private VBox vBoxContainer;

    public void init(LinkedList<Node> nodes){
        for (Node node : nodes){
            WaitingStation waitingStation = (WaitingStation) node;
            Label label = new Label("Prosegui per: "  + waitingStation.getStreetName() + ", " +  waitingStation.getStreetNumber());
            label.setPadding(new Insets(10,0,10,0));
            vBoxContainer.getChildren().add(label);
        }
    }
}
