package com.example.myfinalproject;


import java.io.Serializable;

public class ExerciseCardio extends Exercise implements Serializable {
    private int time;

    public ExerciseCardio(String name, int sets, int time) {
        super(name, sets);
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

}
