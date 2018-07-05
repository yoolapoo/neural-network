package com.epsi.nn;

import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class NetworkTools {

    public static double[] createArray(int size, double init_value){
        if(size < 1){
            return null;
        }
        double[] ar = new double[size];
        for(int i = 0; i < size; i++){
            ar[i] = init_value;
        }
        return ar;
    }

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
        ref.put(10,"a");
        ref.put(11,"b");
        ref.put(12,"c");
        ref.put(13,"d");
        ref.put(14,"e");
        ref.put(15,"f");
        ref.put(16,"g");
        ref.put(17,"h");
        ref.put(18,"i");
        ref.put(19,"j");
        ref.put(20,"k");
        ref.put(21,"l");
        ref.put(22,"m");
        ref.put(23,"n");
        ref.put(24,"o");
        ref.put(25,"p");
        ref.put(26,"q");
        ref.put(27,"r");
        ref.put(28,"s");
        ref.put(29,"t");
        ref.put(30,"u");
        ref.put(31,"v");
        ref.put(32,"w");
        ref.put(33,"x");
        ref.put(34,"y");
        ref.put(35,"z");
        ref.put(36,"A");
        ref.put(37,"B");
        ref.put(38,"C");
        ref.put(39,"D");
        ref.put(40,"E");
        ref.put(41,"F");
        ref.put(42,"G");
        ref.put(43,"H");
        ref.put(44,"I");
        ref.put(45,"J");
        ref.put(46,"K");
        ref.put(47,"L");
        ref.put(48,"M");
        ref.put(49,"N");
        ref.put(50,"O");
        ref.put(51,"P");
        ref.put(52,"Q");
        ref.put(53,"R");
        ref.put(54,"S");
        ref.put(55,"T");
        ref.put(56,"U");
        ref.put(57,"V");
        ref.put(58,"W");
        ref.put(59,"X");
        ref.put(60,"Y");
        ref.put(61,"Z");

        ref.forEach((in,st)->{
            if(in == i ){
                res.set(st);
            }
        });

        return res.get();
        
    }
}
