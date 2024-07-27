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

public class NewWorkoutExerciseStrengthFragment extends Fragment {

    CoordinatorLayout coordinatorLayout;
    EditText nameED;
    EditText setsED;
    EditText repsED;
    EditText weightED;
    FloatingActionButton saveBtn;
    ExerciseManager manager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.new_workout_exercise_details_strength, container, false);
        coordinatorLayout=view.findViewById(R.id.new_exercise_strength_layout);
        nameED=view.findViewById(R.id.new_exercise_name_strength);
        setsED=view.findViewById(R.id.new_exercise_num_of_sets_strength);
        repsED=view.findViewById(R.id.new_exercise_num_of_reps);
        weightED=view.findViewById(R.id.new_exercise_num_of_weight);
        saveBtn=view.findViewById(R.id.new_exercise_save_btn_strength);
        manager = ExerciseManager.getInstance(getActivity());

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isString(nameED.getText().toString()) && isDouble(weightED.getText().toString()) && isInt(setsED.getText().toString()) && isInt(repsED.getText().toString())) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("confirm").setMessage("are you sure you want to save this exercise?").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            ExerciseStrength exerciseStrength = null;
                            if(nameED.getText().toString().isEmpty()||setsED.getText().toString().isEmpty()||repsED.getText().toString().isEmpty()||weightED.getText().toString().isEmpty())
                            {
                                Toast.makeText(getActivity(), "In order to save the exercise you need to fill all fields", Toast.LENGTH_SHORT).show();
                            }else {
                                try {
                                    exerciseStrength = new ExerciseStrength(nameED.getText().toString(),
                                            Integer.parseInt(setsED.getText().toString()),
                                            Integer.parseInt(repsED.getText().toString()),
                                            Double.parseDouble(weightED.getText().toString()));
                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }
                                manager.addExercise(exerciseStrength);
                                nameED.setText("");
                                setsED.setText("");
                                repsED.setText("");
                                weightED.setText("");
                                Bundle bundle=new Bundle();
                                bundle.putInt("index",1);

                                Navigation.findNavController(view).navigate(R.id.action_n_w_e_d_s_fragment_to_n_w_e_l_fragment,bundle);
                            }

                        }


                    }).setNegativeButton("no", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Snackbar.make(coordinatorLayout,"exercise has not been added",Snackbar.LENGTH_SHORT).show();
                        }
                    }).show();
                }else{
                    Snackbar.make(coordinatorLayout,"Some of the fields are filled incorrectly please fix them",Snackbar.LENGTH_SHORT).show();
                }
            }
        });

        return view;}
    private boolean isString(String input) {
        return input != null && !input.isEmpty() && input.trim().length() > 0;
    }
    private boolean isDouble(String input) {
        try {
            Double.parseDouble(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
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

