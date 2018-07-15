package com.epsi.nn;

import com.epsi.nn.mnist.MnistImageFile;
import com.epsi.nn.mnist.MnistLabelFile;
import com.epsi.nn.model.MnistModel;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class NetworkTools {

    public static double[] createRandomArray(int size, double lower_bound, double upper_bound){
        if(size < 1){
            return null;
        }
        double[] ar = new double[size];
        for(int i = 0; i < size; i++){
            ar[i] = randomValue(lower_bound,upper_bound);
        }
        return ar;
    }

    public static double[][] createRandomArray(int sizeX, int sizeY, double lower_bound, double upper_bound){
        if(sizeX < 1 || sizeY < 1){
            return null;
        }
        double[][] ar = new double[sizeX][sizeY];
        for(int i = 0; i < sizeX; i++){
            ar[i] = createRandomArray(sizeY, lower_bound, upper_bound);
        }
        return ar;
    }

    public static double randomValue(double lower_bound, double upper_bound){
        return Math.random()*(upper_bound-lower_bound) + lower_bound;
    }

    public static Integer[] randomValues(int lowerBound, int upperBound, int amount) {

        lowerBound --;

        if(amount > (upperBound-lowerBound)){
            return null;
        }

        Integer[] values = new Integer[amount];
        for(int i = 0; i< amount; i++){
            int n = (int)(Math.random() * (upperBound-lowerBound+1) + lowerBound);
            while(containsValue(values, n)){
                n = (int)(Math.random() * (upperBound-lowerBound+1) + lowerBound);
            }
            values[i] = n;
        }
        return values;
    }

    public static <T extends Comparable<T>> boolean containsValue(T[] ar, T value){
        for(int i = 0; i < ar.length; i++){
            if(ar[i] != null){
                if(value.compareTo(ar[i]) == 0){
                    return true;
                }
            }

        }
        return false;
    }

    public static int indexOfHighestValue(double[] values){
        int index = 0;
        for(int i = 1; i < values.length; i++){
            if(values[i] > values[index]){
                index = i;
            }
        }
        return index;
    }

    public static String inTheList(int i){
        AtomicReference<String> res = new AtomicReference<>();

        Map<Integer,String> ref = new HashMap<>();
        ref.put(0,"0");
        ref.put(1,"1");
        ref.put(2,"2");
        ref.put(3,"3");
        ref.put(4,"4");
        ref.put(5,"5");
        ref.put(6,"6");
        ref.put(7,"7");
        ref.put(8,"8");
        ref.put(9,"9");
        ref.put(10,"A");
        ref.put(11,"B");
        ref.put(12,"C");
        ref.put(13,"D");
        ref.put(14,"E");
        ref.put(15,"F");
        ref.put(16,"G");
        ref.put(17,"H");
        ref.put(18,"I");
        ref.put(19,"J");
        ref.put(20,"K");
        ref.put(21,"L");
        ref.put(22,"M");
        ref.put(23,"N");
        ref.put(24,"O");
        ref.put(25,"P");
        ref.put(26,"Q");
        ref.put(27,"R");
        ref.put(28,"S");
        ref.put(29,"T");
        ref.put(30,"U");
        ref.put(31,"V");
        ref.put(32,"W");
        ref.put(33,"X");
        ref.put(34,"Y");
        ref.put(35,"Z");
        ref.put(36,"a");
        ref.put(37,"b");
        ref.put(38,"c");
        ref.put(39,"d");
        ref.put(40,"e");
        ref.put(41,"f");
        ref.put(42,"g");
        ref.put(43,"h");
        ref.put(44,"i");
        ref.put(45,"j");
        ref.put(46,"k");
        ref.put(47,"l");
        ref.put(48,"m");
        ref.put(49,"n");
        ref.put(50,"o");
        ref.put(51,"p");
        ref.put(52,"q");
        ref.put(53,"r");
        ref.put(54,"s");
        ref.put(55,"t");
        ref.put(56,"u");
        ref.put(57,"v");
        ref.put(58,"w");
        ref.put(59,"x");
        ref.put(60,"y");
        ref.put(61,"z");

        ref.forEach((in,st)->{
            if(in == i ){
                res.set(st);
            }
        });

        return res.get();

    }

    public static String inTheListBalanced(int i){
        AtomicReference<String> res = new AtomicReference<>();

        Map<Integer,String> ref = new HashMap<>();
        ref.put(0,"0");
        ref.put(1,"1");
        ref.put(2,"2");
        ref.put(3,"3");
        ref.put(4,"4");
        ref.put(5,"5");
        ref.put(6,"6");
        ref.put(7,"7");
        ref.put(8,"8");
        ref.put(9,"9");
        ref.put(10,"A");
        ref.put(11,"B");
        ref.put(12,"C");
        ref.put(13,"D");
        ref.put(14,"E");
        ref.put(15,"F");
        ref.put(16,"G");
        ref.put(17,"H");
        ref.put(18,"I");
        ref.put(19,"J");
        ref.put(20,"K");
        ref.put(21,"L");
        ref.put(22,"M");
        ref.put(23,"N");
        ref.put(24,"O");
        ref.put(25,"P");
        ref.put(26,"Q");
        ref.put(27,"R");
        ref.put(28,"S");
        ref.put(29,"T");
        ref.put(30,"U");
        ref.put(31,"V");
        ref.put(32,"W");
        ref.put(33,"X");
        ref.put(34,"Y");
        ref.put(35,"Z");
        ref.put(36,"a");
        ref.put(37,"b");
        ref.put(38,"d");
        ref.put(39,"e");
        ref.put(40,"f");
        ref.put(41,"g");
        ref.put(42,"h");
        ref.put(43,"n");
        ref.put(44,"q");
        ref.put(45,"r");
        ref.put(46,"t");

        ref.forEach((in,st)->{
            if(in == i ){
                res.set(st);
            }
        });

        return res.get();

    }

    public static String inTheListByMerge(int i){
        AtomicReference<String> res = new AtomicReference<>();

        Map<Integer,String> ref = new HashMap<>();
        ref.put(0,"0");
        ref.put(1,"1");
        ref.put(2,"2");
        ref.put(3,"3");
        ref.put(4,"4");
        ref.put(5,"5");
        ref.put(6,"6");
        ref.put(7,"7");
        ref.put(8,"8");
        ref.put(9,"9");
        ref.put(10,"A");
        ref.put(11,"B");
        ref.put(12,"C");
        ref.put(13,"D");
        ref.put(14,"E");
        ref.put(15,"F");
        ref.put(16,"G");
        ref.put(17,"H");
        ref.put(18,"I");
        ref.put(19,"J");
        ref.put(20,"K");
        ref.put(21,"L");
        ref.put(22,"M");
        ref.put(23,"N");
        ref.put(24,"O");
        ref.put(25,"P");
        ref.put(26,"Q");
        ref.put(27,"R");
        ref.put(28,"S");
        ref.put(29,"T");
        ref.put(30,"U");
        ref.put(31,"V");
        ref.put(32,"W");
        ref.put(33,"X");
        ref.put(34,"Y");
        ref.put(35,"Z");
        ref.put(36,"a");
        ref.put(37,"b");
        ref.put(38,"d");
        ref.put(39,"e");
        ref.put(40,"f");
        ref.put(41,"g");
        ref.put(42,"h");
        ref.put(43,"n");
        ref.put(44,"q");
        ref.put(45,"r");
        ref.put(46,"t");


        ref.forEach((in,st)->{
            if(in == i ){
                res.set(st);
            }
        });

        return res.get();

    }

    public static String inTheListLetters(int i){
        AtomicReference<String> res = new AtomicReference<>();

        Map<Integer,String> ref = new HashMap<>();
        ref.put(0,"A");
        ref.put(1,"B");
        ref.put(2,"C");
        ref.put(3,"D");
        ref.put(4,"E");
        ref.put(5,"F");
        ref.put(6,"G");
        ref.put(7,"H");
        ref.put(8,"I");
        ref.put(9,"J");
        ref.put(10,"K");
        ref.put(11,"L");
        ref.put(12,"M");
        ref.put(13,"N");
        ref.put(14,"O");
        ref.put(15,"P");
        ref.put(16,"Q");
        ref.put(17,"R");
        ref.put(18,"S");
        ref.put(19,"T");
        ref.put(20,"U");
        ref.put(21,"V");
        ref.put(22,"W");
        ref.put(23,"X");
        ref.put(24,"Y");
        ref.put(25,"Z");


        ref.forEach((in,st)->{
            if(in == i ){
                res.set(st);
            }
        });

        return res.get();

    }


    public static MnistModel loadDataSet(AtomicReference<String> type){
        String path = new File("").getAbsolutePath();

        MnistImageFile images = null;
        MnistLabelFile labels = null;
        MnistModel model = new MnistModel(images,labels);
        if(type.get().equals("Mnist-Digits")){
            try {
                images = new MnistImageFile(path + "/train/emnist-mnist-train-images-idx3-ubyte", "rw");
                labels = new MnistLabelFile(path + "/train/emnist-mnist-train-labels-idx1-ubyte", "rw");
            }catch (Exception e1) {
                e1.printStackTrace();}
        } else if (type.get().equals("Lettres")) {
            try {
                images = new MnistImageFile(path + "/train/emnist-letters-train-images-idx3-ubyte", "rw");
                labels = new MnistLabelFile(path + "/train/emnist-letters-train-labels-idx1-ubyte","rw");
            }catch (Exception e1) {
                e1.printStackTrace();}
        }else if (type.get().equals("Emnist-Digits")) {
            try {
                images = new MnistImageFile(path + "/train/emnist-digits-train-images-idx3-ubyte", "rw");
                labels = new MnistLabelFile(path + "/train/emnist-digits-train-labels-idx1-ubyte","rw");
            }catch (Exception e1) {
                e1.printStackTrace();}
        }else if (type.get().equals("ByClass")) {
            try {
                images = new MnistImageFile(path + "/train/emnist-byclass-train-images-idx3-ubyte", "rw");
                labels = new MnistLabelFile(path + "/train/emnist-byclass-train-labels-idx1-ubyte","rw");
            }catch (Exception e1) {
                e1.printStackTrace();}
        }else if (type.get().equals("ByMerge")) {
            try {
                images = new MnistImageFile(path + "/train/emnist-bymerge-train-images-idx3-ubyte", "rw");
                labels = new MnistLabelFile(path + "/train/emnist-bymerge-train-labels-idx1-ubyte","rw");
            }catch (Exception e1) {
                e1.printStackTrace();}
        }else if (type.get().equals("Balanced")) {
            try {
                images = new MnistImageFile(path + "/train/emnist-balanced-train-images-idx3-ubyte", "rw");
                labels = new MnistLabelFile(path + "/train/emnist-balanced-train-labels-idx1-ubyte","rw");
            }catch (Exception e1) {
                e1.printStackTrace();}
        }
        model.setImageFile(images);
        model.setLabelFile(labels);

        return model;
    }

    public static String buttonActions(List<RadioButton> radioButtons, TextField out,TextField dataSetSize,TextField fileName){
        AtomicReference<String> type = new AtomicReference<>();
        radioButtons.forEach(radioButton -> {
            String buttonText = radioButton.getText();
            if(buttonText.equals("Mnist-Digits")) {
                radioButton.setSelected(true);
                radioButton.requestFocus();
                out.setText("10");
                dataSetSize.setText("4999");
                fileName.setText(buttonText +"-");
                type.set(buttonText);
                radioButtons.forEach(button -> {
                    if(!button.getText().equals(buttonText)){
                        button.setSelected(false);
                    }
                });
            } else if(buttonText.equals("Emnist-Digits")) {
                radioButton.setOnAction(e -> {
                    radioButton.setSelected(true);
                    radioButton.requestFocus();
                    out.setText("10");
                    dataSetSize.setText("19999");
                    fileName.setText(buttonText +"-");
                    type.set(buttonText);
                    radioButtons.forEach(button -> {
                        if(!button.getText().equals(buttonText)){
                            button.setSelected(false);
                        }
                    });                });
            }
            else if(buttonText.equals("Lettres")) {
                radioButton.setOnAction(e -> {
                    radioButton.setSelected(true);
                    radioButton.requestFocus();
                    out.setText("26");
                    dataSetSize.setText("9999");
                    fileName.setText(buttonText +"-");
                    type.set(buttonText);
                    radioButtons.forEach(button -> {
                        if(!button.getText().equals(buttonText)){
                            button.setSelected(false);
                        }
                    });                });
            }
            else if(buttonText.equals("ByMerge")) {
                radioButton.setOnAction(e -> {
                    radioButton.setSelected(true);
                    radioButton.requestFocus();
                    out.setText("47");
                    dataSetSize.setText("59999");
                    fileName.setText(buttonText +"-");
                    type.set(buttonText);
                    radioButtons.forEach(button -> {
                        if(!button.getText().equals(buttonText)){
                            button.setSelected(false);
                        }
                    });                });
            }
            else if(buttonText.equals("Balanced")) {
                radioButton.setOnAction(e -> {
                    radioButton.setSelected(true);
                    radioButton.requestFocus();
                    out.setText("47");
                    dataSetSize.setText("9999");
                    fileName.setText(buttonText +"-");
                    type.set(buttonText);
                    radioButtons.forEach(button -> {
                        if(!button.getText().equals(buttonText)){
                            button.setSelected(false);
                        }
                    });
                });
            }
            else if(buttonText.equals("ByClass")) {
                radioButton.setOnAction(e -> {
                    radioButton.setSelected(true);
                    radioButton.requestFocus();
                    out.setText("62");
                    dataSetSize.setText("59999");
                    fileName.setText(buttonText +"-");
                    type.set(buttonText);
                    radioButtons.forEach(button -> {
                        if(!button.getText().equals(buttonText)){
                            button.setSelected(false);
                        }
                    });
                });
            }
        });

        return type.get();
    }

}