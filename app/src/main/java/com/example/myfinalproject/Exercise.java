package com.example.myfinalproject;

import java.io.Serializable;

public class Exercise implements Serializable {
    private String name;
    private int sets;


    public Exercise(String name, int sets) {
        this.name = name;
        this.sets = sets;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSets() {
        return sets;
    }

    public void setSets(int sets) {
        this.sets = sets;
    }


}
