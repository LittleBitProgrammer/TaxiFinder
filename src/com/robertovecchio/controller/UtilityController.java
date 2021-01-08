package com.robertovecchio.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

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
}
