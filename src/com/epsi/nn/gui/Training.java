package com.epsi.nn.gui;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.epsi.nn.Network;
import com.epsi.nn.trainSet.TrainSet;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import static com.epsi.nn.mnist.Mnist.createTrainSet;
import static com.epsi.nn.mnist.Mnist.trainData;

public class Training {

    private final GridPane rootPane ;
    private final VBox root;
    private final TextArea textArea;

    public Training(Stage stage) {

        Label network = new Label("DONNES DU TRAINING: ");
        Label labelIn = new Label("Entrées: ");
        Label labelHidden = new Label("Hidden: ");
        Label labelOut = new Label("Sorties: ");
        Label labelDataSetSize = new Label("Taille du Set: ");
        Label labelEpochs = new Label("Nombre d'epochs: ");
        Label labelFilename = new Label("Nom du fichier:               ");

        TextField txtIn = new TextField();
        TextField txtHidden = new TextField();
        TextField txtOut = new TextField();
        TextField txtDataSetSize = new TextField();
        TextField txtEpochs = new TextField();
        TextField txtFileName = new TextField();

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

        //On lance l'entrainement avec les valeurs pré-saisies
        train.setOnAction(e->{
            //Données saisies dans le formulaire
            int entrees = Integer.parseInt(txtIn.getText());
            int hidden = Integer.parseInt(txtHidden.getText());
            int sorties = Integer.parseInt(txtOut.getText());
            int tailleSet = Integer.parseInt(txtDataSetSize.getText());
            int epochs = Integer.parseInt(txtEpochs.getText());
            String nomFichier = txtFileName.getText();

            Network net = new Network(entrees, hidden, sorties);
            TrainSet set = createTrainSet(0,tailleSet);
            trainData(net, set, epochs, 100, 100, "res/"+ nomFichier + ".txt");
        });

        //Boutton afin d'afficher les résultats de l'entrainement du fichier txt
        res.setOnAction(e->{
            scrollPane.setVisible(true);
            FileChooser fileChooser = new FileChooser();

            File defaultDirectory = new File("../neural-network2/res");
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
        rootPane.add(labelHidden,0,2);
        rootPane.add(txtHidden,2,2);
        rootPane.add(labelOut,0,3);
        rootPane.add(txtOut,2,3);
        rootPane.add(labelDataSetSize,0,4);
        rootPane.add(txtDataSetSize,2,4);
        rootPane.add(labelEpochs,0,5);
        rootPane.add(txtEpochs,2,5);
        rootPane.add(labelFilename,0,6);
        rootPane.add(txtFileName,2,6);
        rootPane.add(cancel,0,7);
        rootPane.add(train,1,7);
        rootPane.add(res,2,7);

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
