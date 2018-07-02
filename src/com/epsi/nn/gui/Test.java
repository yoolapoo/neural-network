package com.epsi.nn.gui;

import com.epsi.nn.Network;
import com.epsi.nn.NetworkTools;
import com.epsi.nn.mnist.MnistImageFile;
import com.epsi.nn.mnist.MnistLabelFile;
import com.epsi.nn.trainSet.TrainSet;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Pos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.epsi.nn.mnist.Mnist.createTrainSet;
import static com.epsi.nn.mnist.Mnist.testTrainSet;


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
        File trainingDirectory = new File("../neural-network2/res");
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
            //imgView.setImage(SwingFXUtils.toFXImage(scaledImg, null));
            predictImage(network,scaledImg);
        });
        clear(ctx);
        canvas.requestFocus();

        filechoose.setOnAction(e->{
            File file = fileToLoad.showOpenDialog(stage);

            //Testing
            try {
                network = Network.loadNetwork(file.getAbsolutePath());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        });
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

    private void predictImage(Network net, BufferedImage img) {

        double[] input = new double[784];
        for (int i = 0; i < 28; i++) {
            for (int n = 0; n < 28; n++) {
                input[n * 28 + i] = (float)(new java.awt.Color(img.getRGB(i, n)).getRed()) / 256f;
            }
        }

        System.out.print("output neuron values: ");
        double[] output = net.calculate(input);
        for (double neuronValue: output) {
            System.out.printf("%02.3f  ", neuronValue);
        }
        System.out.println();
        System.out.print("corresponding number:     0      1      2      3      4      5      6      7      8      9");
        System.out.println();

        System.out.println("I think, that the handwritten number is: " + NetworkTools.indexOfHighestValue(output) + "!");
        lblResult.setText("Prediction: " + NetworkTools.indexOfHighestValue(output));
    }
}
