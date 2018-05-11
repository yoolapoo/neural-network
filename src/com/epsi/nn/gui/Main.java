package com.epsi.nn.gui;


import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
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

        buttonPanel.setMargin(training,new Insets(60,20,20,250));
        buttonPanel.setMargin(accuraccy,new Insets(60,20,20,250));
        buttonPanel.setMargin(test,new Insets(60,20,20,250));


        ObservableList list = buttonPanel.getChildren();

        list.addAll(training,accuraccy,test);


        training.setOnAction(e ->{
            Training training = new Training();
            primaryStage.getScene().setRoot(training.getRootPane());
        });

        accuraccy.setOnAction(e ->{
            Accuraccy accuraccy = new Accuraccy(primaryStage);
            primaryStage.getScene().setRoot(accuraccy.getRootPane());
        });

        Scene scene = new Scene(buttonPanel,600,400);
        primaryStage.setScene(scene);
        primaryStage.show();

    }
}
