package com.epsi.nn.gui;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.epsi.nn.Network;
import com.epsi.nn.mnist.MnistImageFile;
import com.epsi.nn.mnist.MnistLabelFile;
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

        RadioButton digits = new RadioButton("Chiffres");
        RadioButton letters = new RadioButton("Lettres");
        VBox radioButtons = new VBox(2);
        radioButtons.getChildren().addAll(digits,letters);
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
        digits.setOnAction(e->{
            digits.setSelected(true);
            letters.setSelected(false);
            digits.requestFocus();
            type.set(digits.getText());
        });
        letters.setOnAction(e->{
            letters.setSelected(true);
            digits.setSelected(false);
            letters.requestFocus();
            type.set(letters.getText());
        });

        AtomicReference<String> accuraccy = new AtomicReference<>("");
        filechoose.setOnAction(e->{
            File file = fileToLoad.showOpenDialog(stage);
            if (file != null) {
                openFile(file);
            }

            MnistImageFile images = null;
            MnistLabelFile labels = null;
            //Testing
            try {
                Network network = Network.loadNetwork(file.getAbsolutePath());
                if(type.get().equals("Chiffres")){
                    try {
                        images = new MnistImageFile(path + "/train/train-images-idx3-ubyte", "rw");
                        labels = new MnistLabelFile(path + "/train/train-labels-idx1-ubyte", "rw");
                    }catch (Exception e1) {
                        e1.printStackTrace();}
                } else if (type.get().equals("Lettres")) {
                    try {
                        images = new MnistImageFile(path + "/train/emnist-letters-test-images-idx3-ubyte", "rw");
                        labels = new MnistLabelFile(path + "/train/emnist-letters-test-labels-idx1-ubyte","rw");
                    }catch (Exception e1) {
                        e1.printStackTrace();}
                }

                accuraccy.set(testTrainSet(network, createTrainSet(1000, 2000,images,labels), 10));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            txtResult.setText(accuraccy.get());
        });

        rootPane = new GridPane();
        rootPane.setPadding(new Insets(10));
        rootPane.setAlignment(Pos.CENTER);
        rootPane.setVgap(20);

        rootPane.add(labelFileChoose,0,0);
        rootPane.add(filechoose,1,0);
        rootPane.add(labelType,0,1);
        rootPane.add(radioButtons,1,1);
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
