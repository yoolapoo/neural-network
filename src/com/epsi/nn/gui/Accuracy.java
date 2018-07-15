package com.epsi.nn.gui;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.epsi.nn.Network;
import com.epsi.nn.NetworkTools;
import com.epsi.nn.model.MnistModel;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import static com.epsi.nn.mnist.Mnist.createTrainSet;
import static com.epsi.nn.mnist.Mnist.testTrainSet;

public class Accuracy {

    private final GridPane rootPane ;

    private Desktop desktop = Desktop.getDesktop();

    public Accuracy(Stage stage) {

        String path = new File("").getAbsolutePath();


        Label labelFileChoose = new Label("Fichier à charger: ");
        Button filechoose = new Button("Ouvrir...");

        FileChooser fileToLoad = new FileChooser();
        File trainingDirectory = new File("../digit-character-recognition-nn/res");
        fileToLoad.setInitialDirectory(trainingDirectory);

        Label labelResult = new Label("Exactitude: ");
        TextField txtResult = new TextField();
        txtResult.setDisable(true);
        txtResult.setMinSize(300,20);

        Button cancel = new Button("Annuler");

        RadioButton mnistDigits = new RadioButton("Mnist-Digits");
        RadioButton emnistDigits = new RadioButton("Emnist-Digits");
        RadioButton letters = new RadioButton("Lettres");
        RadioButton balanced = new RadioButton("Balanced");
        RadioButton byMerge = new RadioButton("ByMerge");
        RadioButton byClass = new RadioButton("ByClass");
        VBox radioButtons = new VBox(2);
        radioButtons.getChildren().addAll(mnistDigits,emnistDigits,letters,balanced,byClass,byMerge);
        Label labelType = new Label("Type de réseau: ");


        //Retour ecran d'accueil
        cancel.setOnAction(e->{
            Main main = new Main();
            try {
                main.start(stage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        AtomicReference<String> type = new AtomicReference<>("");
        List<RadioButton> radioButtonList = new ArrayList<>();
        radioButtonList.add(mnistDigits);
        radioButtonList.add(emnistDigits);
        radioButtonList.add(balanced);
        radioButtonList.add(byClass);
        radioButtonList.add(byMerge);
        radioButtonList.add(letters);

        AtomicInteger start = new AtomicInteger(0);
        AtomicInteger end = new AtomicInteger(0);
        AtomicInteger classes = new AtomicInteger(0);

        mnistDigits.setOnAction(e->{
            start.set(1000);
            end.set(2000);
            classes.set(10);
            mnistDigits.setSelected(true);
            mnistDigits.requestFocus();
            type.set(mnistDigits.getText());
            radioButtonList.forEach(radioButton -> {
                if(!radioButton.getText().equals(mnistDigits.getText())){
                    radioButton.setSelected(false);
                }
            });
        });
        emnistDigits.setOnAction(e->{
            start.set(4000);
            end.set(8000);
            classes.set(10);
            emnistDigits.setSelected(true);
            emnistDigits.requestFocus();
            type.set(emnistDigits.getText());
            radioButtonList.forEach(radioButton -> {
                if(!radioButton.getText().equals(emnistDigits.getText())){
                    radioButton.setSelected(false);
                }
            });
        });
        letters.setOnAction(e->{
            start.set(2000);
            end.set(4000);
            classes.set(26);
            letters.setSelected(true);
            letters.requestFocus();
            type.set(letters.getText());
            radioButtonList.forEach(radioButton -> {
                if(!radioButton.getText().equals(letters.getText())){
                    radioButton.setSelected(false);
                }
            });
        });
        balanced.setOnAction(e->{
            start.set(2000);
            end.set(4000);
            classes.set(47);
            balanced.setSelected(true);
            balanced.requestFocus();
            type.set(balanced.getText());
            radioButtonList.forEach(radioButton -> {
                if(!radioButton.getText().equals(balanced.getText())){
                    radioButton.setSelected(false);
                }
            });
        });
        byClass.setOnAction(e->{
            start.set(12000);
            end.set(24000);
            classes.set(62);
            byClass.setSelected(true);
            byClass.requestFocus();
            type.set(byClass.getText());
            radioButtonList.forEach(radioButton -> {
                if(!radioButton.getText().equals(byClass.getText())){
                    radioButton.setSelected(false);
                }
            });
        });
        byMerge.setOnAction(e->{
            start.set(12000);
            end.set(24000);
            classes.set(47);
            byMerge.setSelected(true);
            byMerge.requestFocus();
            type.set(byMerge.getText());
            radioButtonList.forEach(radioButton -> {
                if(!radioButton.getText().equals(byMerge.getText())){
                    radioButton.setSelected(false);
                }
            });
        });

        AtomicReference<String> accuraccy = new AtomicReference<>("");
        filechoose.setOnAction(e->{
            File file = fileToLoad.showOpenDialog(stage);
            if (file != null) {
                openFile(file);
            }

            //Testing
            try {
                Network network = Network.loadNetwork(file.getAbsolutePath());
                MnistModel model = NetworkTools.loadDataSet(type);

                accuraccy.set(testTrainSet(network, createTrainSet(start.get(), end.get(),model.getImageFile(),model.getLabelFile(),classes.get()), 10));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            txtResult.setText(accuraccy.get());
        });

        rootPane = new GridPane();
        rootPane.setPadding(new Insets(10));
        rootPane.setAlignment(Pos.CENTER);
        rootPane.setVgap(20);


        rootPane.add(labelType,0,0);
        rootPane.add(radioButtons,1,0);
        rootPane.add(labelFileChoose,0,1);
        rootPane.add(filechoose,1,1);
        rootPane.add(labelResult,0,2);
        rootPane.add(txtResult,1,2);
        rootPane.add(cancel,0,3);


    }

    public Pane getRootPane() {
        return rootPane ;
    }

    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
                    Accuracy.class.getName()).log(
                    Level.SEVERE, null, ex
            );
        }
    }

}
