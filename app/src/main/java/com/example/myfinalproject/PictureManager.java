package com.example.myfinalproject;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class PictureManager {
    public static PictureManager instance;
    public Context context;
    public ArrayList<Picture> pictureList=new ArrayList<>();
    static final String FileName= "picture.dat";

    public PictureManager(Context context) {
        this.context = context;
        try {
            FileInputStream fis= context.openFileInput(FileName);
            ObjectInputStream ois=new ObjectInputStream(fis);
            pictureList=(ArrayList<Picture>)ois.readObject();
            ois.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static PictureManager getInstance(Context context) {
        if(instance==null)
            instance=new PictureManager(context);

        return instance;
    }

    public Picture getPicture(int i){
        if(i<pictureList.size())
            return pictureList.get(i);

        return null;
    }
    private void savePictures(){
        try {
            FileOutputStream fos = context.openFileOutput(FileName, Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(pictureList);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public ArrayList<Picture> getPictureList(){
        return pictureList;
    }
    public void addPicture(Picture picture){
        this.pictureList.add(picture);
        savePictures();
    }
    public void removePicture(int i){
        if(i<pictureList.size())
            pictureList.remove(i);
        savePictures();
    }
}
