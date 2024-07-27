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

public class WorkoutsHistoryAdapter extends RecyclerView.Adapter<WorkoutsHistoryAdapter.WorkoutsViewHolder> {
    private ArrayList<ExerciseManager> workoutsList;
    WorkoutsListener listener;

    public WorkoutsHistoryAdapter(ArrayList<ExerciseManager> workoutsManagerArrayList) {
        this.workoutsList=workoutsManagerArrayList;
    }

    interface WorkoutsListener{
        void OnWorkoutClicked(int i, View view);
        void OnWorkoutLongClick(int i,View view);
    }


    public void SetListener(WorkoutsHistoryAdapter.WorkoutsListener listener){this.listener=listener;}

    class WorkoutsViewHolder extends RecyclerView.ViewHolder{
        TextView nameTv;

        private WorkoutsViewHolder(View itemView){
            super(itemView);
            this.nameTv = itemView.findViewById(R.id.workout_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(listener!=null)
                        listener.OnWorkoutClicked(getAdapterPosition(),view);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(listener!=null)
                        listener.OnWorkoutLongClick(getAdapterPosition(),view);
                    return false;
                }
            });
        }
    }


    @NonNull
    @Override
    public WorkoutsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.workout_layout,parent,false);
        return new WorkoutsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkoutsHistoryAdapter.WorkoutsViewHolder holder, int position) {
        ExerciseManager manager=workoutsList.get(position);
        holder.nameTv.setText(manager.getWorkoutName());
        Context context = holder.itemView.getContext();
        holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.light_brown));

//        Context context = holder.itemView.getContext();
//        if(exercise instanceof ExerciseStrength){
//            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.light_blue));
//        }else if(exercise instanceof ExerciseCardio){
//            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.purple_200));
//        }

    }

    @Override
    public int getItemCount() {
        return workoutsList.size();
    }
}
