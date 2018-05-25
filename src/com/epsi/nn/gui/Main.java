package com.epsi.nn.gui;


import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {

    Button training;
    Button accuraccy;
    Button test;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage)  throws Exception{
        primaryStage.setTitle("Multi-Layer Perceptron for OCR");


        VBox buttonPanel = new VBox(10);
        training = new Button("Training");
        accuraccy = new Button("Accuraccy");
        test = new Button("Test");
        training.setPrefSize(200,100);
        accuraccy.setPrefSize(200,100);
        test.setPrefSize(200,100);

        buttonPanel.setMargin(training,new Insets(60,20,20,300));
        buttonPanel.setMargin(accuraccy,new Insets(60,20,20,300));
        buttonPanel.setMargin(test,new Insets(60,20,20,300));


        ObservableList list = buttonPanel.getChildren();

        list.addAll(training,accuraccy,test);


        training.setOnAction(e ->{
            Training training = new Training(primaryStage);
            primaryStage.getScene().setRoot(training.getRootPane());
        });

        accuraccy.setOnAction(e ->{
            Accuraccy accuraccy = new Accuraccy(primaryStage);
            primaryStage.getScene().setRoot(accuraccy.getRootPane());
        });

        test.setOnAction(e ->{
            Test test = new Test(primaryStage);
            primaryStage.getScene().setRoot(test.getRootPane());
        });

        Scene scene = new Scene(buttonPanel,800,600);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

}
