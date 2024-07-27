package com.example.myfinalproject;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;


public class SelectedDays {
    public static SelectedDays instance;
    public Context context;
    public ArrayList<Day> daysToAdd=new ArrayList<>();
    static final String FileName= "days.dat";

    public SelectedDays(Context context) {
        this.context = context;
        try {
            FileInputStream fis= context.openFileInput(FileName);
            ObjectInputStream ois=new ObjectInputStream(fis);
            daysToAdd.addAll((ArrayList<Day>)ois.readObject());
            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }
    public static SelectedDays getInstance(Context context){
        if(instance==null) {
            instance = new SelectedDays(context);
        }

        return instance;
    }

    public ArrayList<Day> getDays() {
        return daysToAdd;
    }
    private void saveDays(){
        try {
            FileOutputStream fos=context.openFileOutput(FileName,Context.MODE_PRIVATE);
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(daysToAdd);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void AddDay(String day,String time){
        this.daysToAdd.add(new Day(day, time));
        saveDays();
    }

    public void RemoveDay(String day) {
        Day baseDay = new Day(day);
        int removeIndex=-1;
        for (int i = 0; i < this.daysToAdd.size(); i++) {
            if (baseDay.equals(this.daysToAdd.get(i))) {
                removeIndex=i;
                saveDays();
            }
        }
        if(removeIndex!=-1)
            this.daysToAdd.remove(removeIndex);
    }

    public void setDaysToAdd(ArrayList<Day> daysToAdd) {
        this.daysToAdd = daysToAdd;
    }

    public void RemoveDay(int i){
        this.daysToAdd.remove(i);
        saveDays();
    }
}
