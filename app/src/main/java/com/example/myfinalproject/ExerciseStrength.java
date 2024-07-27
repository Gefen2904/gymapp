package com.example.myfinalproject;


import java.io.Serializable;

public class ExerciseStrength extends Exercise implements Serializable {
    private int reps;
    private double weight;

    public ExerciseStrength(String name, int sets, int reps, double weight) {
        super(name, sets);
        this.reps = reps;
        this.weight = weight;
    }

    public int getReps() {
        return reps;
    }

    public void setReps(int reps) {
        this.reps = reps;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
