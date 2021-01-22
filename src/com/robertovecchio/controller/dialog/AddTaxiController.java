package com.robertovecchio.controller.dialog;

import com.robertovecchio.model.db.TaxiFinderData;
import com.robertovecchio.model.user.Handler;
import com.robertovecchio.model.veichle.BrandType;
import com.robertovecchio.model.veichle.FuelType;
import com.robertovecchio.model.veichle.builderTaxi.Taxi;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanExpression;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import java.util.EnumSet;
import java.util.Set;

/**
 * Classe che gestisce la view (dialog) di aggiunta di un Taxi
 * @author robertovecchio
 * @version 1.0
 * @since 11/01/2021
 * */
public class AddTaxiController {

    //==================================================
    //               Variabili d'istanza
    //==================================================
    /**
     * Istanza del database
     * @see TaxiFinderData
     * */
    private final TaxiFinderData taxiFinderData = TaxiFinderData.getInstance();

    //==================================================
    //               Variabili FXML
    //==================================================

    /**
     * TextField della targa auto
     * @see TextField
     * */
    @FXML
    private TextField licensePlateField;
    /**
     * ComboBox utile a mostrare la lista dei Brand
     * @see ComboBox
     * @see BrandType
     */
    @FXML
    private ComboBox<BrandType> brandNameField;
    /**
     * TextField del modello dell'auto
     * @see TextField
     */
    @FXML
    TextField modelNameField;
    /**
     * TextField della capacità del veicolo in termini di persone trasportabili
     * @see TextField
     */
    @FXML
    TextField capacityField;
    /**
     * ComboBox utile a mostrare la lista di tipologie carburante esistenti
     * @see TextField
     */
    @FXML
    ComboBox<FuelType> fuelTypeField;

    //==================================================
    //               Inizializzazione
    //==================================================
    /**
     * Questo metodo inizializza la view a cui è collegato il controller corrente
     * */
    @FXML
    public void initialize(){
        /* inizializziamo la comboBox della lista brand con tutti i possibili casi di BrandType */
        Set<BrandType> brands = EnumSet.of(BrandType.ABARTH,
                                            BrandType.ALFA_ROMEO,
                                            BrandType.AUDI,
                                            BrandType.BMW,
                                            BrandType.CITROEN,
                                            BrandType.DACIA,
                                            BrandType.FIAT,
                                            BrandType.FORD,
                                            BrandType.HONDA,
                                            BrandType.HYUNDAI,
                                            BrandType.KIA,
                                            BrandType.LANCIA,
                                            BrandType.LAND_ROVER,
                                            BrandType.MAZDA,
                                            BrandType.MINI,
                                            BrandType.MITSUBISHI,
                                            BrandType.NISSAN,
                                            BrandType.OPEL,
                                            BrandType.PEUGEOT,
                                            BrandType.RENAULT,
                                            BrandType.SEAT,
                                            BrandType.SKODA,
                                            BrandType.SUBARU,
                                            BrandType.SUZUKI,
                                            BrandType.TATA,
                                            BrandType.TOYOTA,
                                            BrandType.VOLKSWAGEN,
                                            BrandType.VOLVO);

        this.brandNameField.getItems().addAll(brands);

        /* inizializziamo la comboBox della tipologia carburante con tutti i possibili casi di FuelType */
        Set<FuelType> fuels = EnumSet.of(FuelType.GASOLINE, FuelType.DIESEL, FuelType.GAS, FuelType.ELECTRIC_ENERGY);
        this.fuelTypeField.getItems().addAll(fuels);

        /* Impostiamo che la textfield potrà accettare solo valori numerici */
        this.capacityField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observableValue, String s, String t1) {
                if (!t1.matches("\\d{0,2}")){
                    capacityField.setText(s);
                }
            }
        });

        /* Permettiamo di mostrare una stringa personalizzata nell'intestazione della ComboBox */
        this.fuelTypeField.setConverter(new StringConverter<>() {
            @Override
            public String toString(FuelType fuelType) {
                if (fuelType == null){
                    return null;
                }else {
                    return fuelType.getTranslation();
                }
            }

            @Override
            public FuelType fromString(String s) {
                return null;
            }
        });
    }

    //==================================================
    //               Metodi
    //==================================================
    /**
     * Questo metodo constata che le proprietà dei vari controlli siano riempite prima di poter creare un Taxi
     * @return Ritorna una espressione booleana osservabile che constata la qualità di quanto inserito
     * */
    public BooleanExpression invalidInputProperty(){
        return Bindings.createBooleanBinding(() -> this.licensePlateField.getText().trim().isEmpty() ||
                        this.brandNameField.getValue() == null ||
                        this.modelNameField.getText().trim().isEmpty() ||
                        this.capacityField.getText().trim().isEmpty()||
                        this.fuelTypeField.getValue() == null,
                this.licensePlateField.textProperty(),
                this.brandNameField.valueProperty(),
                this.modelNameField.textProperty(),
                this.capacityField.textProperty(),
                this.fuelTypeField.valueProperty());
    }

    /**
     * Metodo che ha lo scopo di buildare un Taxi attraverso i dati inseriti nei vari controlli posti su interfaccia
     * utnte
     * @return Taxi aggiunto
     * @see Taxi*/
    public Taxi processTaxiResult(){
        /* Ricavo l'handler da cui buildare il taxi */
        Handler handler = (Handler) taxiFinderData.getCurrentUser();

        /* Buildiamo il taxi */
        handler.buildTaxi(this.licensePlateField.getText().trim().toUpperCase(),
                          this.brandNameField.getValue(),
                          this.modelNameField.getText().trim().substring(0,1).toUpperCase() +
                                  this.modelNameField.getText().trim().substring(1),
                          Integer.parseInt(this.capacityField.getText().trim()),
                          this.fuelTypeField.getValue());

        /* Ritorniamo il Taxi Buildato */
        return handler.getTaxiBuilder().getResult();
    }
}
