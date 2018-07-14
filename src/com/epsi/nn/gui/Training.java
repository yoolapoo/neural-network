package com.epsi.nn.gui;

import java.io.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.epsi.nn.Network;
import com.epsi.nn.mnist.MnistImageFile;
import com.epsi.nn.mnist.MnistLabelFile;
import com.epsi.nn.trainSet.TrainSet;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import static com.epsi.nn.mnist.Mnist.createTrainSet;
import static com.epsi.nn.mnist.Mnist.trainData;
import static javafx.scene.input.KeyCode.M;

public class Training {

    private final GridPane rootPane ;
    private final VBox root;
    private final TextArea textArea;

    public Training(Stage stage) {

        String path = new File("").getAbsolutePath();


        Label network = new Label("DONNES DU TRAINING: ");
        Label labelIn = new Label("Entrées: ");
        Label labelHidden1 = new Label("Couche cachée 1: ");
        Label labelHidden2 = new Label("Couche cachée 2: ");
        Label labelOut = new Label("Sorties: ");
        Label labelDataSetSize = new Label("Taille du Set: ");
        Label labelEpochs = new Label("Nombre d'epochs: ");
        Label labelFilename = new Label("Nom du fichier: ");
        Label labelType = new Label("Type de réseau: ");

        TextField txtIn = new TextField("784");
        TextField txtHidden1 = new TextField("70");
        TextField txtHidden2 = new TextField("35");
        TextField txtOut = new TextField();
        TextField txtDataSetSize = new TextField();
        TextField txtEpochs = new TextField("2000");
        TextField txtFileName = new TextField();
        RadioButton digits = new RadioButton("Chiffres");
        RadioButton letters = new RadioButton("Lettres");
        VBox radioButtons = new VBox(2);
        radioButtons.getChildren().addAll(digits,letters);


        Button cancel = new Button("Annuler");
        Button train = new Button("Entrainer");
        Button res = new Button("Resultat");

        textArea = new TextArea();
        textArea.prefWidth(400);
        textArea.setWrapText(true);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("noborder-scroll-pane");
        scrollPane.setContent(textArea);
        scrollPane.setFitToWidth(true);
        scrollPane.setPrefWidth(400);
        scrollPane.setPrefHeight(180);
        scrollPane.setVisible(false);

        rootPane = new GridPane();
        rootPane.setPadding(new Insets(10));
        rootPane.setAlignment(Pos.CENTER);
        rootPane.setVgap(20);

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
            txtOut.setText("10");
            txtDataSetSize.setText("4999");
            txtFileName.setText("digits-");
            type.set(digits.getText());
        });
        letters.setOnAction(e->{
            letters.setSelected(true);
            digits.setSelected(false);
            letters.requestFocus();
            txtOut.setText("52");
            txtDataSetSize.setText("4999");
            txtFileName.setText("letters-");
            type.set(letters.getText());
        });

        //On lance l'entrainement avec les valeurs pré-saisies
        train.setOnAction(e->{

            MnistImageFile images = null;
            MnistLabelFile labels = null;

            //Données saisies dans le formulaire
            int entrees = Integer.parseInt(txtIn.getText());
            int hidden1 = Integer.parseInt(txtHidden1.getText());
            int hidden2 = Integer.parseInt(txtHidden2.getText());
            int sorties = Integer.parseInt(txtOut.getText());
            int tailleSet = Integer.parseInt(txtDataSetSize.getText());
            int epochs = Integer.parseInt(txtEpochs.getText());
            String nomFichier = txtFileName.getText();
            if(type.get().equals("Chiffres")){
                try {
                    images = new MnistImageFile(path + "/train/train-images-idx3-ubyte", "rw");
                    labels = new MnistLabelFile(path + "/train/train-labels-idx1-ubyte", "rw");
                }catch (Exception e1) {
                    e1.printStackTrace();}
            } else if (type.get().equals("Lettres")) {
                try {
                    images = new MnistImageFile(path + "/train/emnist-letters-train-images-idx3-ubyte", "rw");
                    labels = new MnistLabelFile(path + "/train/emnist-letters-train-labels-idx1-ubyte","rw");
                }catch (Exception e1) {
                    e1.printStackTrace();}
            }

            Network net = new Network(entrees, hidden1, hidden2, sorties);
            TrainSet set = createTrainSet(0,tailleSet,images,labels);
            trainData(net, set, epochs, 100, 100, "res/"+ nomFichier + ".txt");
        });

        //Boutton afin d'afficher les résultats de l'entrainement du fichier txt
        res.setOnAction(e->{
            scrollPane.setVisible(true);
            FileChooser fileChooser = new FileChooser();

            File defaultDirectory = new File("../digit-character-recognition-nn/res");
            fileChooser.setInitialDirectory(defaultDirectory);

            //Set extension filter
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
            fileChooser.getExtensionFilters().add(extFilter);

            //Show save file dialog
            File file = fileChooser.showOpenDialog(stage);
            if(file != null){
                textArea.setText(readFile(file));
            }
        });

        rootPane.add(network,1,0);
        rootPane.add(labelIn,0,1);
        rootPane.add(txtIn,2,1);
        rootPane.add(labelHidden1,0,2);
        rootPane.add(txtHidden1,2,2);
        rootPane.add(labelHidden2,0,3);
        rootPane.add(txtHidden2,2,3);
        rootPane.add(labelOut,0,4);
        rootPane.add(txtOut,2,4);
        rootPane.add(labelDataSetSize,0,5);
        rootPane.add(txtDataSetSize,2,5);
        rootPane.add(labelEpochs,0,6);
        rootPane.add(txtEpochs,2,6);
        rootPane.add(labelFilename,0,7);
        rootPane.add(txtFileName,2,7);
        rootPane.add(cancel,0,8);
        rootPane.add(train,1,8);
        rootPane.add(res,2,8);
        rootPane.add(labelType,0,9);
        rootPane.add(radioButtons,2,9);

        root = new VBox(20);

        root.getChildren().addAll(rootPane,scrollPane);

    }

    public Pane getRootPane() {
        return root ;
    }

    private String readFile(File file){
        StringBuilder stringBuffer = new StringBuilder();
        BufferedReader bufferedReader = null;

        try {

            bufferedReader = new BufferedReader(new FileReader(file));

            String text;
            while ((text = bufferedReader.readLine()) != null) {
                stringBuffer.append(text);
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Training.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Training.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(Training.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return stringBuffer.toString();
    }

}
