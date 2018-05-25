package com.epsi.nn.gui;

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
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;


public class Test {

    private final VBox root;
    private final  Button cancel;
    private final int CANVAS_WIDTH = 300;
    private final int CANVAS_HEIGHT = 300;
    private Label lblResult;



    public Test(Stage stage) {
        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        ImageView imgView = new ImageView();
        GraphicsContext ctx = canvas.getGraphicsContext2D();

        imgView.setFitHeight(100);
        imgView.setFitWidth(100);
        ctx.setLineWidth(10);
        ctx.setLineCap(StrokeLineCap.SQUARE);
        lblResult = new Label();
        cancel = new Button("Annuler");


        HBox hbBottom = new HBox(10, imgView, lblResult,cancel);
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
            ctx.setStroke(Color.WHITE);
            ctx.beginPath();
            ctx.moveTo(e.getX(), e.getY());
            ctx.stroke();
        });
        canvas.setOnMouseDragged(e -> {
            ctx.setStroke(Color.WHITE);
            ctx.lineTo(e.getX(), e.getY());
            ctx.stroke();
        });
        canvas.setOnMouseClicked(e -> {
            if (e.getButton() == MouseButton.SECONDARY) {
                clear(ctx);
            }
        });
        canvas.setOnKeyReleased(e -> {
            if(e.getCode() == KeyCode.ENTER) {
                BufferedImage scaledImg = getScaledImage(canvas);
                imgView.setImage(SwingFXUtils.toFXImage(scaledImg, null));
                predictImage(scaledImg);
            }
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
        ctx.setFill(Color.BLACK);
        ctx.fillRect(0, 0, 300, 300);
    }

    public Pane getRootPane() {
        return root ;
    }

    private void predictImage(BufferedImage img ) {

        lblResult.setText("Prediction: " );
        System.out.println("lol" + img.toString());
    }


}
