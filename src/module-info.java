/**
 * Questo modulo identifica il sistema software Taxi Finder
 * @author robertovecchio
 * @since 15/01/2021
 */
open module TaxiFinder {
    /* Dichiaro una dipendenza con javafx.controls, necessario per gestire input dell'utente */
    requires javafx.controls;
    /* Dichiaro una dipendenza con javafx.fxml, necessario per poter utilizzare file fxml */
    requires javafx.fxml;
    /* Dichiaro una dipendenza con javafx.graphics, necessario per poter utilizzare immagini */
    requires javafx.graphics;
    /* Dichiaro una dipendenza con jlfgr, necessario per poter caricare alcune icone di default */
    requires jlfgr;
}