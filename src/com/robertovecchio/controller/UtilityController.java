package com.robertovecchio.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Classe di utilità generale per i vari controller
 * @author robertovecchio
 * @version 1.0
 * @since 08/01/2021
 * */
public class UtilityController {
    //==================================================
    //                  Metodi Statici
    //==================================================

    /**
     * Metodo atto alla navigazione presso il file fxml successivo
     * @param fileName Nome del file fxml
     * @param title Titolo dello stage
     * @param error Errore da stampare in caso di problemi nel caricamento del file fxml
     * @param button Button da cui viene triggerata la navigazione
     * @see Button
     * */
    protected static void navigateTo(String fileName, String title, String error, Button button){
        Stage stage = (Stage)button.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader();
        try {
            Parent root = loader.load(new FileInputStream(fileName));

            stage.setTitle(title);
            stage.getScene().setRoot(root);
        }catch (IOException e){
            System.out.println(error);
        }
    }

    /**
     * Metodo atto alla navigazione presso il file fxml successivo con aggiunta di foglio di stile atto alla
     * personalizzazione dell'interfaccia
     * @param fileName Nome del file fxml
     * @param title Titolo dello stage
     * @param error Errore da stampare in caso di problemi nel caricamento del file fxml
     * @param button Button da cui viene triggerata la navigazione
     * @param stylesheet path foglio di stile
     * @see Button
     * */
    protected static void navigateTo(String fileName, String title, String error, Button button, String stylesheet){
        Stage stage = (Stage)button.getScene().getWindow();

        FXMLLoader loader = new FXMLLoader();
        try {
            Parent root = loader.load(new FileInputStream(fileName));

            stage.setTitle(title);
            stage.getScene().setRoot(root);
            stage.getScene().getStylesheets().add(stylesheet);
        }catch (IOException e){
            System.out.println(error);
        }
    }

    /**
     * Metodo atto alla navigazione presso il file fxml successivo con chiusura dello stage precedente
     * @param fileName Nome del file fxml
     * @param title Titolo dello stage
     * @param error Errore da stampare in caso di problemi nel caricamento del file fxml
     * @param button Button da cui viene triggerata la navigazione
     * @see Button
     * */
    protected static void changeStageTo(String fileName, String title, String error, Button button, String stylesheet){
        // Chiudiamo lo stage precedente
        Stage stage = (Stage)button.getScene().getWindow();
        stage.close();

        // Creazione di un nuovo stage
        Stage newStage = new Stage();

        FXMLLoader loader = new FXMLLoader();
        try {
            Parent root = loader.load(new FileInputStream(fileName));

            newStage.setTitle(title);
            newStage.getScene().setRoot(root);
            newStage.setMaximized(true);
            newStage.getScene().getStylesheets().add(stylesheet);

            //Mostriamo lo stage
            newStage.show();
        }catch (IOException e){
            System.out.println("Errore di navigazione, alcuni file non sono stati trovati");
        }
    }

    /**
     * Metodo atto alla navigazione presso il file fxml successivo con chiusura dello stage precedente. In
     * aggiunta in questo metodo è stato inserito come parametro di input, un foglio di stile
     * personalizzazione dell'interfaccia
     * @param fileName Nome del file fxml
     * @param title Titolo dello stage
     * @param error Errore da stampare in caso di problemi nel caricamento del file fxml
     * @param button Button da cui viene triggerata la navigazione
     * @see Button
     * */
    protected static void changeStageTo(String fileName, String title, String error, Button button){
        // Chiudiamo lo stage precedente
        Stage stage = (Stage)button.getScene().getWindow();
        stage.close();

        // Creazione di un nuovo stage
        Stage newStage = new Stage();

        FXMLLoader loader = new FXMLLoader();
        try {
            Parent root = loader.load(new FileInputStream(fileName));

            newStage.setTitle(title);
            newStage.getScene().setRoot(root);
            newStage.setMaximized(true);

            //Mostriamo lo stage
            newStage.show();
        }catch (IOException e){
            System.out.println("Errore di navigazione, alcuni file non sono stati trovati");
        }
    }

    /**
     * Metodo atto a constatare la validità di una email inserita sfruttando le api ufficiali di java
     * @param email Email da verificare
     * @return true se è una mail valid, altrimenti false
     * */
    protected static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }
}