package com.example.myfinalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class NewWorkoutExerciseCardioFragment extends Fragment {

    EditText nameED;
    EditText setsED;
    EditText timeEd;
    FloatingActionButton saveBtn;
    ExerciseManager manager;
    CoordinatorLayout coordinatorLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_workout_exercise_details_cardio, container, false);

        nameED=view.findViewById(R.id.new_exercise_name_cardio);
        setsED=view.findViewById(R.id.new_exercise_num_of_sets_cardio);
        timeEd=view.findViewById(R.id.new_exercise_num_of_time);
        saveBtn=view.findViewById(R.id.new_exercise_save_btn_cardio);
        manager = ExerciseManager.getInstance(getActivity());
        coordinatorLayout=view.findViewById(R.id.new_exercise_cardio_layout);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isString(nameED.getText().toString())&& isInt(setsED.getText().toString()) && isInt(timeEd.getText().toString())) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("confirm").setMessage("are you sure you want to save this exercise?").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ExerciseCardio exerciseCardio = null;
                            if (nameED.getText().toString().isEmpty() || setsED.getText().toString().isEmpty() || timeEd.getText().toString().isEmpty()) {
                                Toast.makeText(getActivity(), "In order to save the exercise you need to fill all fields", Toast.LENGTH_SHORT).show();
                            } else {
                                try {
                                    exerciseCardio = new ExerciseCardio(nameED.getText().toString(),
                                            Integer.parseInt(setsED.getText().toString()),
                                            Integer.parseInt(timeEd.getText().toString())
                                    );
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();

                                }
                                manager.addExercise(exerciseCardio);
                                nameED.setText("");
                                setsED.setText("");
                                timeEd.setText("");
                                Bundle bundle=new Bundle();
                                bundle.putInt("index",1);
                                Navigation.findNavController(view).navigate(R.id.action_n_w_e_d_c_fragment_to_n_w_e_l_fragment,bundle);
                            }
                        }


                    }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Snackbar.make(coordinatorLayout,"exercise has not been added",Snackbar.LENGTH_SHORT).show();
                        }
                    }).show();

                } else {
                    Snackbar.make(coordinatorLayout,"Some of the fields are filled incorrectly please fix them",Snackbar.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }
    private boolean isString(String input) {
        return input != null && !input.isEmpty() && input.trim().length() > 0;
    }
    private boolean isInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
