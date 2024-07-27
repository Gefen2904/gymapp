package com.example.myfinalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class WorkoutsHistoryFragment extends Fragment {

    private RecyclerView recyclerView;
    WorkoutsHistoryAdapter adapter;
    WorkoutsManager manager;
    FloatingActionButton back;
    CoordinatorLayout coordinatorLayout;
    ExerciseManager exerciseManager;
    SelectedDays selectedDays;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.workouts_history, container, false);
        Bundle bundle = new Bundle();
        bundle.putInt("index", 4);
        coordinatorLayout = view.findViewById(R.id.workouts_list_layout);
        selectedDays=SelectedDays.getInstance(getContext());
        back = view.findViewById(R.id.workouts_back_btn);
        manager = WorkoutsManager.getInstance(this.getContext(),selectedDays);
        recyclerView = view.findViewById(R.id.workouts_list);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        exerciseManager = ExerciseManager.getInstance(this.getContext());
        adapter = new WorkoutsHistoryAdapter(manager.getWorkoutList());
        adapter.SetListener(new WorkoutsHistoryAdapter.WorkoutsListener() {
                                @Override
                                public void OnWorkoutClicked(int i, View view) {
                                    selectedDays.setDaysToAdd(manager.getSelectedDays().getDays());
                                    exerciseManager.setExerciseList(manager.getWorkoutList().get(i).getExercises());
                                    exerciseManager.setWorkoutName(manager.getWorkoutList().get(i).workoutName);
                                    Navigation.findNavController(view).navigate(R.id.action_workouts_history_fragment_to_main_fragment, bundle);
                                }

                                @Override
                                public void OnWorkoutLongClick(int i, View view) {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                    builder.setTitle("Are you sure you want to remove this workout?");

                                    builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int index) {
                                            manager.removeWorkout(i);
                                            adapter.notifyDataSetChanged();
                                            Snackbar.make(coordinatorLayout, "This workout has been removed", Snackbar.LENGTH_SHORT).show();
                                        }
                                    });
                                    builder.setNegativeButton("no", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Snackbar.make(coordinatorLayout, "This workout has not been removed", Snackbar.LENGTH_SHORT).show();
                                        }
                                    });
                                    builder.show();
                                }
                            }
        );
        recyclerView.setAdapter(adapter);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(view).navigate(R.id.action_workouts_history_fragment_to_main_fragment);
            }
        });
        return view;

    }
}
