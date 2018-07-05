package com.epsi.nn.gui;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.epsi.nn.Network;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import static com.epsi.nn.mnist.Mnist.createTrainSet;
import static com.epsi.nn.mnist.Mnist.testTrainSet;

public class Accuraccy {

    private final GridPane rootPane ;

    private Desktop desktop = Desktop.getDesktop();

    public Accuraccy(Stage stage) {

        Label labelFileChoose = new Label("Fichier à charger: ");
        Button filechoose = new Button("Ouvrir...");

        FileChooser fileToLoad = new FileChooser();
        File trainingDirectory = new File("../digit-character-recognition-nn/res");
        fileToLoad.setInitialDirectory(trainingDirectory);

        Label labelResult = new Label("Exactitude: ");
        TextField txtResult = new TextField();
        txtResult.setMinSize(300,20);

        Button cancel = new Button("Annuler");

        //Retour ecran d'accueil
        cancel.setOnAction(e->{
            Main main = new Main();
            try {
                main.start(stage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
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
                accuraccy.set(testTrainSet(network, createTrainSet(1000, 2000), 10));
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
        rootPane.add(labelResult,0,1);
        rootPane.add(txtResult,1,1);
        rootPane.add(cancel,0,2);


    }

    public Pane getRootPane() {
        return rootPane ;
    }

    private void openFile(File file) {
        try {
            desktop.open(file);
        } catch (IOException ex) {
            Logger.getLogger(
                    Accuraccy.class.getName()).log(
                    Level.SEVERE, null, ex
            );
        }
    }

}
