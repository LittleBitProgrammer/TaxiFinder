package com.robertovecchio.controller;

import com.robertovecchio.controller.dialog.DialogAction;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.stage.Window;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Period;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
     * @param stylesheet path foglio di stile
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
            newStage.setMaximized(true);
            newStage.setScene(new Scene(root));
            newStage.getScene().getStylesheets().add(stylesheet);

            //Mostriamo lo stage
            newStage.show();
        }catch (IOException e){
            System.out.println(error);
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
            newStage.setMaximized(true);
            newStage.setScene(new Scene(root));

            //Mostriamo lo stage
            newStage.show();
        }catch (IOException e){
            System.out.println(error);
        }
    }

    /**
     * Metodo atto alla navigazione presso il file fxml successivo con chiusura dello stage precedente. In
     * aggiunta in questo metodo è stato inserito come parametro di input, un foglio di stile
     * personalizzazione dell'interfaccia
     * @param fileName Nome del file fxml
     * @param title Titolo dello stage
     * @param error Errore da stampare in caso di problemi nel caricamento del file fxml
     * @param label Label da cui viene triggerata la navigazione
     * @see Button
     * */
    protected static void changeStageTo(String fileName, String title, String error, Label label){
        // Chiudiamo lo stage precedente
        Stage stage = (Stage)label.getScene().getWindow();
        stage.close();

        // Creazione di un nuovo stage
        Stage newStage = new Stage();

        FXMLLoader loader = new FXMLLoader();
        try {
            Parent root = loader.load(new FileInputStream(fileName));

            newStage.setTitle(title);
            newStage.setMaximized(true);
            newStage.setScene(new Scene(root));

            //Mostriamo lo stage
            newStage.show();
        }catch (IOException e){
            System.out.println(error);
        }
    }

    /**
     * Metodo atto a constatare la validità di una email (REGEX PATTERN)
     * @param email Email da verificare
     * @return true se è una mail valid, altrimenti false
     * */
    public static boolean isValidEmailAddress(String email) {
        final String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        // Inizializzo un PATTERN
        Pattern pattern = Pattern.compile(regex);

        // Inizializzo un Matcher
        Matcher matcher = pattern.matcher(email);

        System.out.println(matcher.matches());
        return matcher.matches();
    }

    protected static void showDialog(Window window, String title, String FXMLPath, String error,
                                     DialogAction callable, ButtonType... buttonTypes){
        // creiamo un nuovo dialog da visualizzare
        Dialog<ButtonType> dialog = new Dialog<>();

        // inizializziamo il proprietario
        dialog.initOwner(window);

        // Impostiamo il titolo del dialog
        dialog.setTitle(title);

        // Carichiamo il file di iterfaccia per il dialog
        FXMLLoader loader = new FXMLLoader();
        try{
            Parent root = loader.load(new FileInputStream(FXMLPath));
            dialog.getDialogPane().setContent(root);
        }catch (IOException e){
            System.out.println(error);
        }

        // Aggiungiamo il bottone OK al dialogPane
        dialog.getDialogPane().getButtonTypes().addAll(buttonTypes);

        // Gestiamo il controller mostrandolo e aspettando che l'utente vi interagisca
        Optional<ButtonType> result = dialog.showAndWait();

        // Gestiamo il caso in cui l'utente abbia premuto OK
        if (result.isPresent() && result.get() == ButtonType.OK){
            callable.doDialogAction();
        }
    }

    /**
     * Metodo che ricava l'età data la data di nascita
     * @param birthDate data di nascita
     * @return età*/
    public static int getYears(LocalDate birthDate){
        // Ricavo la data attuale
        LocalDate now = LocalDate.now();

        // Calcolo la differenza tra i due periodi
        Period difference = Period.between(birthDate,now);

        // ritorno solo gli anni di differenza
        return difference.getYears();
    }

    public static boolean isValidPassword(String password){
        return !(password.length() < 4 || password.length() > 15);
    }
}