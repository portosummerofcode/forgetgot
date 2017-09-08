package com.forgetgot.selftie;

import android.content.Intent;
import android.widget.Button;
import android.widget.ImageButton;

import java.io.Serializable;
import java.util.ArrayList;

import static com.forgetgot.selftie.R.id.button;
import static com.forgetgot.selftie.R.styleable.View;

public class Task implements Serializable{
    static public String TASK_EXTRA = "com.forgetgot.selftie.TASK_EXTRA";
    String name;
    String category;
    double prediction;
    int Id;
    ArrayList<Double> times;

    public Task(String name, String category, double prediction) {
        this.name = name;
        this.category = category;
        this.prediction = prediction;
        this.times = new ArrayList<>();
    }

    public String getName(){
        return name;
    }

    public String getCategory(){
        return category;
    }

    public double getPrediction(){
        return prediction;
    }

    public double getRealtime(){
        double realtime = 0;

        for(double time: times)
            realtime += time;

        return realtime;
    }

    public double getError(){
        double realtime = getRealtime();
        double error = Math.abs(realtime - prediction)/prediction;

        return error;
    }
}
