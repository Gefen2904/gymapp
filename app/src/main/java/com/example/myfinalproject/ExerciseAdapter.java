package com.example.myfinalproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {

    private ArrayList<Exercise> exerciseList;
    ExerciseListener listener;

    public ExerciseAdapter(ArrayList<Exercise> exercises) {
        this.exerciseList=exercises;
    }

    interface ExerciseListener{
        void OnExerciseClicked(int i,View view);
        void OnExerciseLongClick(int i,View view);
    }


    public void SetListener(ExerciseListener listener){this.listener=listener;}

    class ExerciseViewHolder extends RecyclerView.ViewHolder{
        TextView nameTv;

        private ExerciseViewHolder(View itemView){
            super(itemView);
            this.nameTv = itemView.findViewById(R.id.new_exercise_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null)
                        listener.OnExerciseClicked(getAdapterPosition(),view);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(listener!=null)
                        listener.OnExerciseLongClick(getAdapterPosition(),view);
                    return false;
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise=exerciseList.get(position);
        holder.nameTv.setText(exercise.getName());
        Context context = holder.itemView.getContext();
        if(exercise instanceof ExerciseStrength){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.light_blue));
        }else if(exercise instanceof ExerciseCardio){
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.purple_200));
        }

    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_layout,parent,false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }
}
