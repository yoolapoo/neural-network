package com.epsi.nn.gui;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Accuraccy {

    private final GridPane rootPane ;

    private Desktop desktop = Desktop.getDesktop();

    public Accuraccy(Stage stage) {

        Label labelFileChoose = new Label("Fichier à charger: ");
        Button filechoose = new Button("Ouvrir...");

        FileChooser fileToLoad = new FileChooser();
        File trainingDirectory = new File("../neural-network/train");
        fileToLoad.setInitialDirectory(trainingDirectory);

        Label labelResult = new Label("Exactitude: ");
        TextField txtResult = new TextField();

        filechoose.setOnAction(e->{
                        File file = fileToLoad.showOpenDialog(stage);
                        if (file != null) {
                            openFile(file);
                        }
                });

        rootPane = new GridPane();
        rootPane.setPadding(new Insets(10));
        rootPane.setAlignment(Pos.CENTER);
        rootPane.setVgap(20);

        rootPane.add(labelFileChoose,0,0);
        rootPane.add(filechoose,1,0);
        rootPane.add(labelResult,0,1);
        rootPane.add(txtResult,1,1);


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
