package com.epsi.nn.gui;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Training {

    private final GridPane rootPane ;

    public Training() {

        Label network = new Label("DONNES DU TRAINING: ");
        Label labelIn = new Label("Entrées: ");
        Label labelHidden = new Label("Hidden: ");
        Label labelOut = new Label("Sorties: ");
        Label labelDataSetSize = new Label("Taille du Set: ");
        Label labelFilename = new Label("Nom du fichier:               ");

        TextField txtIn = new TextField();
        TextField txtHidden = new TextField();
        TextField txtOut = new TextField();
        TextField txtDataSetSize = new TextField();
        TextField txtFileName = new TextField();

        Button cancel = new Button("Annuler");
        Button train = new Button("Entrainer");
        Button save = new Button("Sauver");

        rootPane = new GridPane();
        rootPane.setPadding(new Insets(10));
        rootPane.setAlignment(Pos.CENTER);
        rootPane.setVgap(20);


        rootPane.add(network,1,0);
        rootPane.add(labelIn,0,1);
        rootPane.add(txtIn,2,1);
        rootPane.add(labelHidden,0,2);
        rootPane.add(txtHidden,2,2);
        rootPane.add(labelOut,0,3);
        rootPane.add(txtOut,2,3);
        rootPane.add(labelDataSetSize,0,4);
        rootPane.add(txtDataSetSize,2,4);
        rootPane.add(labelFilename,0,5);
        rootPane.add(txtFileName,2,5);
        rootPane.add(cancel,0,6);
        rootPane.add(train,1,6);
        rootPane.add(save,2,6);

    }

    public Pane getRootPane() {
        return rootPane ;
    }

}
