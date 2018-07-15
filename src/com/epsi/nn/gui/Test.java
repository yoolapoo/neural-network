package com.epsi.nn.gui;

import com.epsi.nn.Network;
import com.epsi.nn.NetworkTools;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;


public class Test {

    private final VBox root;
    private final  Button cancel;
    private final int CANVAS_WIDTH = 300;
    private final int CANVAS_HEIGHT = 300;
    private Button filechoose;



    private Label lblResult;
    private Button bPredic;
    private Network network;



    public Test(Stage stage) {
        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        ImageView imgView = new ImageView();
        GraphicsContext ctx = canvas.getGraphicsContext2D();

        FileChooser fileToLoad = new FileChooser();
        File trainingDirectory = new File("../digit-character-recognition-nn/res");
        fileToLoad.setInitialDirectory(trainingDirectory);

        imgView.setFitHeight(100);
        imgView.setFitWidth(100);
        ctx.setLineWidth(10);
        ctx.setLineCap(StrokeLineCap.SQUARE);
        lblResult = new Label();
        cancel = new Button("Annuler");
        bPredic = new Button("Predict");
        filechoose  = new Button("Ouvrir...");


        HBox hbBottom = new HBox(10, filechoose,imgView,cancel,bPredic, lblResult);
        root = new VBox(5, canvas, hbBottom);
        hbBottom.setAlignment(Pos.CENTER);
        root.setAlignment(Pos.CENTER);

        AtomicReference<String> typeReseau = new AtomicReference<>("");
        filechoose.setOnAction(e->{
            File file = fileToLoad.showOpenDialog(stage);
            typeReseau.set(file.getName());

            //Testing
            try {
                network = Network.loadNetwork(file.getAbsolutePath());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });

        //Retour ecran d'accueil
        cancel.setOnAction(e->{
            Main main = new Main();
            try {
                main.start(stage);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });


        canvas.setOnMousePressed(e -> {
            ctx.setStroke(javafx.scene.paint.Color.WHITE);
            ctx.beginPath();
            ctx.moveTo(e.getX(), e.getY());
            ctx.stroke();
        });
        canvas.setOnMouseDragged(e -> {
            ctx.setStroke(javafx.scene.paint.Color.WHITE);
            ctx.lineTo(e.getX(), e.getY());
            ctx.stroke();
        });
        canvas.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                clear(ctx);
            }
        });
        bPredic.setOnAction(e -> {
            BufferedImage scaledImg = getScaledImage(canvas);
            predictImage(network,scaledImg, typeReseau.get());
        });
        clear(ctx);
        canvas.requestFocus();



    }

    private BufferedImage getScaledImage(Canvas canvas) {
        // for a better recognition we should improve this part of how we retrieve the image from the canvas
        WritableImage writableImage = new WritableImage(CANVAS_WIDTH, CANVAS_HEIGHT);
        canvas.snapshot(null, writableImage);
        Image tmp =  SwingFXUtils.fromFXImage(writableImage, null).getScaledInstance(28, 28, Image.SCALE_SMOOTH);
        BufferedImage scaledImg = new BufferedImage(28, 28, BufferedImage.TYPE_BYTE_GRAY);
        Graphics graphics = scaledImg.getGraphics();
        graphics.drawImage(tmp, 0, 0, null);
        graphics.dispose();
        return scaledImg;
    }

    private void clear(GraphicsContext ctx) {
        ctx.setFill(javafx.scene.paint.Color.BLACK);
        ctx.fillRect(0, 0, 300, 300);
    }

    public Pane getRootPane() {
        return root ;
    }

    private void predictImage(Network net, BufferedImage img, String type) {

        double[] input = new double[784];
        for (int i = 0; i < 28; i++) {
            for (int n = 0; n < 28; n++) {
                input[n * 28 + i] = (float)(new java.awt.Color(img.getRGB(i, n)).getRed()) / 256f;
            }
        }

        System.out.print("valeurs du neurone output: ");
        double[] output = net.calculate(input);
        for (double neuronValue: output) {
            System.out.printf("%02.3f  ", neuronValue);
        }

        int valuePredicted = NetworkTools.indexOfHighestValue(output);
        String res = "";
        if(type.contains("Mnist-Digits") || type.contains("Emnist-Digits") || type.contains("ByClass")){
           res = NetworkTools.inTheList(valuePredicted);
        } else if(type.contains("Lettres")){
            res = NetworkTools.inTheListLetters(valuePredicted);
        } else if(type.contains("Balanced")){
            res = NetworkTools.inTheListBalanced(valuePredicted);
        } else if(type.contains("ByMerge")){
            res = NetworkTools.inTheListByMerge(valuePredicted);
        }

        System.out.println("Prédiction : le caractère écrit est le : " + res + "!");
        lblResult.setText("Prediction: " + res);
    }
}
