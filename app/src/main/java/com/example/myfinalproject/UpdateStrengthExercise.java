package com.example.myfinalproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class UpdateStrengthExercise extends Fragment {

    ExerciseManager exerciseManager;
    int index;
    ExerciseStrength exerciseStrength;
    TextView nameTV;
    CoordinatorLayout coordinatorLayout;
    EditText setsED;
    EditText repsED;
    EditText weightED;
    FloatingActionButton saveBtn;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_strength_exercise, container, false);

        exerciseManager=ExerciseManager.getInstance(getActivity());
        index=getArguments().getInt("index");
        exerciseStrength= (ExerciseStrength) exerciseManager.getExercise(index);
        nameTV=view.findViewById(R.id.update_exercise_name_strength);
        coordinatorLayout=view.findViewById(R.id.update_exercise_strength_layout);
        setsED=view.findViewById(R.id.update_exercise_num_of_sets_strength);
        repsED=view.findViewById(R.id.update_exercise_num_of_reps);
        weightED=view.findViewById(R.id.update_exercise_num_of_weight_strength);
        saveBtn=view.findViewById(R.id.update_exercise_save_btn_strength);
        nameTV.setText("Name: "+exerciseStrength.getName());
        setsED.setText(String.valueOf(exerciseStrength.getSets()));
        repsED.setText(String.valueOf(exerciseStrength.getReps()));
        weightED.setText(String.valueOf(exerciseStrength.getWeight()));
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isDouble(weightED.getText().toString()) && isInt(setsED.getText().toString()) && isInt(repsED.getText().toString()))
                {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("confirm").setMessage("are you sure you want to save this exercise?").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            exerciseManager.getExercise(index).setSets(Integer.parseInt(setsED.getText().toString()));
                            ((ExerciseStrength) exerciseManager.getExercise(index)).setReps(Integer.parseInt(repsED.getText().toString()));
                            ((ExerciseStrength) exerciseManager.getExercise(index)).setWeight(Double.parseDouble(weightED.getText().toString()));

                            setsED.setText("");
                            repsED.setText("");
                            weightED.setText("");
                            Bundle bundle=new Bundle();
                            bundle.putInt("index",3);
                            Navigation.findNavController(view).navigate(R.id.action_u_e_d_s_fragment_to_main_fragment,bundle);
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

        return view;
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
