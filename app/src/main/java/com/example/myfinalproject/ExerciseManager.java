package com.example.myfinalproject;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class ExerciseManager implements Serializable {

    public static ExerciseManager instance;
    public Context context;
    public ArrayList<Exercise> exerciseList=new ArrayList<>();
    static final String FileName= "exercise.dat";
    public String workoutName="";

    public ExerciseManager(Context context,String name) {
        this.context = context;
        this.workoutName=name;
        try {
            FileInputStream fis= context.openFileInput(FileName);
            ObjectInputStream ois=new ObjectInputStream(fis);
            exerciseList.addAll((ArrayList<Exercise>)ois.readObject());
            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
    public boolean GetType(int i){
        return instance.getExercise(i) instanceof ExerciseStrength;
    }

    public static ExerciseManager getInstance(Context context){
        if(instance==null) {
            instance = new ExerciseManager(context,"");
        }

        return instance;
    }
    public Exercise getExercise(int i){
        if(i<exerciseList.size())
            return exerciseList.get(i);

        return null;
    }
    private void saveExercises(){
        try {
            FileOutputStream fos=context.openFileOutput(FileName,Context.MODE_PRIVATE);
            fos.write(workoutName.getBytes());
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(exerciseList);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Exercise> getExercises(){
        return exerciseList;
    }

    public void addExercise(Exercise exercise){
        this.exerciseList.add(exercise);
        saveExercises();
    }
    public void removeExercise(int i){
        if(i<exerciseList.size())
            exerciseList.remove(i);
        saveExercises();
    }

    public void setExerciseList(ArrayList<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
        saveExercises();
    }

    public String getWorkoutName() {
        return workoutName;
    }

    public void setWorkoutName(String workoutName) {
        this.workoutName = workoutName;
    }
}
