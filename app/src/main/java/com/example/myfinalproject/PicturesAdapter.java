package com.example.myfinalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PicturesAdapter extends RecyclerView.Adapter<PicturesAdapter.PictureViewHolder> {
    private final ArrayList<Picture> pictureList;
    private PictureListener listener;

    interface PictureListener{
        void OnPictureClicked(int i,View view);
        void OnPictureLongClick(int i,View view);

    }
    public PicturesAdapter( ArrayList<Picture> pictureList) {
        this.pictureList = pictureList;
    }


    public  void SetListener(PictureListener listener){this.listener=listener;}

    @NonNull
    @Override
    public PictureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.picture_layout, parent, false);
        return new PictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PictureViewHolder holder, int position) {
        Picture pictureModel = pictureList.get(position);
        holder.imageView.setImageBitmap(pictureModel.getPicture());
    }

    @Override
    public int getItemCount() {
        return pictureList.size();
    }

    class PictureViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        private PictureViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.picture);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null)
                        listener.OnPictureClicked(getAdapterPosition(),view);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (listener != null)
                        listener.OnPictureLongClick(getAdapterPosition(), view);
                    return false;
                }
            });
        }
    }

}