package com.example.myfinalproject;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.IOException;
import java.io.Serializable;

public class Picture implements Serializable {
    transient private Bitmap picture;

    public Picture(Bitmap bitmap) {
        this.picture = bitmap;
    }
    public Bitmap getPicture() {
        return this.picture;
    }

    public void setPicture(Bitmap bitmap) {
        this.picture = bitmap;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException{
        picture.compress(Bitmap.CompressFormat.JPEG,50,out);
        out.defaultWriteObject();
    }
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException{
        picture = BitmapFactory.decodeStream(in);
        in.defaultReadObject();
    }
}
