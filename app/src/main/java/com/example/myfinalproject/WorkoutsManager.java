package com.example.myfinalproject;
import android.content.Context;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class WorkoutsManager {

    public static WorkoutsManager instance;
    public Context context;
    public ArrayList<ExerciseManager> workoutList=new ArrayList<>();
    static final String FileName= "workoutLists.dat";
    SelectedDays selectedDays;

    public WorkoutsManager(Context context,SelectedDays days) {
        this.context = context;
        this.selectedDays=days;
        try {
            FileInputStream fis= context.openFileInput(FileName);
            ObjectInputStream ois=new ObjectInputStream(fis);
            workoutList=(ArrayList<ExerciseManager>)ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public SelectedDays getSelectedDays() {
        return selectedDays;
    }

    public void setSelectedDays(SelectedDays selectedDays) {
        this.selectedDays = selectedDays;
    }

    public static WorkoutsManager getInstance(Context context,SelectedDays days) {
        if(instance==null)
            instance=new WorkoutsManager(context,days);

        return instance;
    }

    public ExerciseManager getSingleWorkout(int i){
        if(i<workoutList.size())
            return workoutList.get(i);

        return null;
    }
    private void saveWorkouts(){
        try {
            FileOutputStream fos = context.openFileOutput(FileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(workoutList);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<ExerciseManager> getWorkoutList(){
        return workoutList;
    }
    public void addWorkout(ExerciseManager exerciseManager){
        this.workoutList.add(exerciseManager);
        saveWorkouts();
    }
    public void removeWorkout(int i){
        if(i<workoutList.size())
            workoutList.remove(i);
        saveWorkouts();
    }
}
