package com.example.myfinalproject;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.Calendar;

public class NewWorkoutExerciseListFragment extends Fragment {
    private SelectedDays trainingDays;
    private  String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private  boolean[] selectedDays = {false, false, false, false, false, false, false};
    private static boolean firstTime = true;
    ExerciseManager manager;
    ExerciseManager managerToAdd;
    private ExerciseAdapter adapter;
    RecyclerView exerciseList;
    CoordinatorLayout coordinatorLayout;
    FloatingActionButton addBtn;
    FloatingActionButton pickDays;
    String dayTime;
    FloatingActionButton finishBtn;
    EditText editTextTitle;
    WorkoutsManager workoutsManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_workout_exercise_list, container, false);
        trainingDays=SelectedDays.getInstance(getActivity());
        addBtn=view.findViewById(R.id.add_btn);
        Intent serviceIntent = new Intent(getContext(), Service.class);
        pickDays=view.findViewById(R.id.what_days_btn);
        finishBtn=view.findViewById(R.id.finish_btn);
        editTextTitle=view.findViewById(R.id.edit_text_title);
        coordinatorLayout=view.findViewById(R.id.new_exercise_list_layout);
        exerciseList = view.findViewById(R.id.new_workout_list);
        exerciseList.setHasFixedSize(true);
        exerciseList.setLayoutManager(new LinearLayoutManager(this.getContext()));
        workoutsManager=WorkoutsManager.getInstance(this.getContext(),trainingDays);
        manager=ExerciseManager.getInstance(this.getContext());
        adapter =new ExerciseAdapter(manager.getExercises());
        adapter.SetListener(new ExerciseAdapter.ExerciseListener() {
            @Override
            public void OnExerciseClicked(int i, View view) {
                Snackbar.make(coordinatorLayout,"This is a exercise from your workout to remove long click",Snackbar.LENGTH_SHORT).show();
            }

            @Override
            public void OnExerciseLongClick(int i, View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Are you sure you want to remove this exercise?");

                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int index) {
                        manager.removeExercise(i);
                        adapter.notifyDataSetChanged();
                        Snackbar.make(coordinatorLayout,"This exercise has been removed",Snackbar.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Snackbar.make(coordinatorLayout,"This exercise has not been removed",Snackbar.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
        exerciseList.setAdapter(adapter);
        if(firstTime){
            getActivity().stopService(serviceIntent);
            manager.getExercises().clear();
            manager.setWorkoutName("");
            trainingDays.getDays().clear();
            firstTime=false;
        }else{
            for(int i =0;i<trainingDays.getDays().size();i++){
                for(int j =0;j<7;j++) {
                    if (trainingDays.getDays().get(i).getDay().equals(daysOfWeek[j]))
                        selectedDays[j] = true;
                }
            }
            editTextTitle.setText(manager.getWorkoutName());
            int num=getArguments().getInt("index",0);
            if(num==1)
                Snackbar.make(coordinatorLayout,"Exercise saved",Snackbar.LENGTH_SHORT).show();
        }

        pickDays.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDaysOfWeekDialog();
            }
        });

        finishBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(manager.getExercises().size()>0){
                        if (!trainingDays.getDays().isEmpty()) {
                            if(!editTextTitle.getText().toString().isEmpty()) {
                                manager.setWorkoutName(editTextTitle.getText().toString());
                                managerToAdd=new ExerciseManager(getContext(),manager.getWorkoutName());
                                managerToAdd.setExerciseList(new ArrayList<>(manager.getExercises()));

                                workoutsManager.setSelectedDays(trainingDays);
                                workoutsManager.addWorkout(managerToAdd);
                                getActivity().startService(serviceIntent);
                                Bundle bundle = new Bundle();
                                bundle.putInt("index", 1);
                                Navigation.findNavController(v).navigate(R.id.action_n_w_e_l_fragment_to_main_fragment, bundle);
                                firstTime = true;
                            }else
                                Snackbar.make(coordinatorLayout, "No title selected please fix it", Snackbar.LENGTH_SHORT).show();
                        } else {
                            Snackbar.make(coordinatorLayout, "No days selected please select some", Snackbar.LENGTH_SHORT).show();
                        }
                }else{
                    Snackbar.make(coordinatorLayout,"There are no exercises in your workout pleas add some",Snackbar.LENGTH_SHORT).show();
                }
            }
        });


        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                manager.setWorkoutName(editTextTitle.getText().toString());

                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

                builder.setTitle("choose which kind of exercise to add");
                builder.setPositiveButton("Cardio", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Navigation.findNavController(v).navigate(R.id.action_n_w_e_l_fragment_to_n_w_e_d_c_fragment);

                    }
                });

                builder.setNegativeButton("Strength", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Navigation.findNavController(v).navigate(R.id.action_n_w_e_l_fragment_to_n_w_e_d_s_fragment);
                    }
                });
                builder.create().show();
            }
        });

        return view;
    }

    private void showDaysOfWeekDialog() {
        manager.setWorkoutName(editTextTitle.getText().toString());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Select the days you would like to workout");

        builder.setMultiChoiceItems(daysOfWeek, selectedDays, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                selectedDays[which] = isChecked;
                if(isChecked)
                    showTimePickerDialog(daysOfWeek[which]);
                else
                    trainingDays.RemoveDay(daysOfWeek[which]);
            }
        });

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        builder.create().show();
    }
    private void showTimePickerDialog(String day) {
        TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(),
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        if(minute>30)
                            minute-=30;
                        else {
                            hourOfDay-=1;
                            minute+=30;
                        }
                        dayTime=  hourOfDay+"/"+minute;  //String.format("%02d:%02d", hourOfDay, minute);
                        trainingDays.AddDay(day,dayTime);
                    }
                }, 0, 0, true);
        timePickerDialog.setTitle("Select Workout Time for " + day);
        timePickerDialog.show();
    }
}
