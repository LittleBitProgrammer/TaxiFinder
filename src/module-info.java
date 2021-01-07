/**
 * Questo modulo identifica il sistema software Taxi Finder
 */
module TaxiFinder {
    // Dichiaro una dipendenza con javafx.controls, necessario per gestire input dell'utente
    requires javafx.controls;
    // Dichiaro una dipendenza con javafx.fxml, necessario per poter utilizzare file fxml
    requires javafx.fxml;

    // Dichiaro un'apertura qualificata dei seguenti package
    opens com.robertovecchio.model.veichle;
    opens com.robertovecchio.model.veichle.builderTaxi;
    opens com.robertovecchio.model.graph;
    opens com.robertovecchio.model.graph.node;
    opens com.robertovecchio.model.user;
    opens com.robertovecchio.model;
    opens com.robertovecchio.controller;
    opens com.robertovecchio.view;
}