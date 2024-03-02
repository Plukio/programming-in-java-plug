package com.harbourspace.lesson06;


public class MathTask {

    public static double randomNum(double min, double max, int d){
        double range = max - min;
        double rand_all = (Math.random() * range) + min;
        double rand = Math.round(rand_all * Math.pow(10, d)) / Math.pow(10, d);
        return rand;
    }

}
