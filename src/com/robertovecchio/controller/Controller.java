package com.robertovecchio.controller;

import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**Controller della view principale*/
public class Controller {
    //==================================================
    //               Variabili FXML
    //==================================================
    @FXML
    Button companyButton;
    @FXML
    Button userButton;
    @FXML
    GridPane gridRoot;

    //==================================================
    //               Variabili Statiche
    //==================================================

    private final static String fontFamily = "Helvetica";
    private final static double fontSize = 50D;
    private final static Color darkGrayFontColor = Color.web("#494949");
    private final static Color darkYellowFontColor = Color.web("#F3D833");
    private final static String logoFilePath = "Assets/logo.png";

    //==================================================
    //               Inizializzazione
    //==================================================

    @FXML
    public void initialize(){
        // Inizializziamo una nuova imageview
        ImageView logoImage = new ImageView();
        try {
            // Inizializziamo il logo
            Image logo = new Image(new FileInputStream(logoFilePath));
            logoImage.setImage(logo); // impostiamo l'immagine all'imageview
            logoImage.setFitHeight(100); // impostiamo altezza
            logoImage.setPreserveRatio(true); // impostiamo la conservazione delle proporzioni originali
        }catch (FileNotFoundException e){
            // catchiamo l'errore
            System.out.println("File Non trovato");
        }

        // inizializziamo un textFlow
        TextFlow textFlow = new TextFlow();

        // Creiamo la prima parte dell'intestazione
        Text headerTextFirstPart = new Text("T");
        headerTextFirstPart.setFont(Font.font(fontFamily, FontWeight.BOLD, fontSize));
        headerTextFirstPart.setFill(darkYellowFontColor);

        // Creiamo la seconda parte dell'intestazione
        Text headerTextSecondPart = new Text("axi");
        headerTextSecondPart.setFont(Font.font(fontFamily,FontWeight.NORMAL, fontSize));
        headerTextSecondPart.setFill(darkGrayFontColor);

        // Creiamo la terza parte dell'intestazione
        Text headerTextThirdPart = new Text("F");
        headerTextThirdPart.setFont(Font.font(fontFamily,FontWeight.BOLD, fontSize));
        headerTextThirdPart.setFill(darkYellowFontColor);

        // Creiamo la quarta parte dell'intestazione
        Text headerTextFourthPart = new Text("inder");
        headerTextFourthPart.setFont(Font.font(fontFamily,FontWeight.NORMAL, fontSize));
        headerTextFourthPart.setFill(darkGrayFontColor);

        // aggiungiamo varie parti dell'intestazione al textFlow
        textFlow.getChildren().addAll(headerTextFirstPart,
                                      headerTextSecondPart,
                                      headerTextThirdPart,
                                      headerTextFourthPart);

        // aggiungiamo logo e textflow al gridPane secondo la seguente sintassi:
        // 1 - indice di colonna
        // 2 - indice di riga
        // 3 - span colonna
        // 4 - span riga
        gridRoot.add(logoImage,0,0,2,1);
        gridRoot.add(textFlow,0,1,2,1);

        // impostiamo l'allineamento del logo
        GridPane.setHalignment(logoImage, HPos.CENTER);

        // impostiamo il testo del bottone che porta all'area aziendale
        companyButton.setText("Area aziendale");

        // impostiamo il testo del bottone che porta all'area dei clienti
        userButton.setText("Area Clienti");

        // impostiamo l'azione al click del bottone che porta all'area aziendale
        companyButton.setOnAction(actionEvent -> System.out.println("azienda premuto"));

        // impostiamo l'azione al click del bottone che porta all'area cliente
        userButton.setOnAction(actionEvent -> System.out.println("Cliente premuto"));
    }
}