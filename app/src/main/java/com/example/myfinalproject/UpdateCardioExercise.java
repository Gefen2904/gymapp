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

public class UpdateCardioExercise extends Fragment {
    ExerciseManager exerciseManager;
    int index;
    ExerciseCardio exerciseCardio;
    TextView nameTv;
    EditText setsEd;
    EditText timeEd;
    FloatingActionButton saveBtn;
    CoordinatorLayout coordinatorLayout;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.update_cardio_exercise, container, false);
        exerciseManager=ExerciseManager.getInstance(getActivity());
        index=getArguments().getInt("index");
        exerciseCardio= (ExerciseCardio) exerciseManager.getExercise(index);
        coordinatorLayout=view.findViewById(R.id.update_exercise_cardio_layout);
        nameTv=view.findViewById(R.id.update_exercise_name_cardio);
        setsEd=view.findViewById(R.id.update_exercise_num_of_sets_cardio);
        timeEd=view.findViewById(R.id.update_exercise_num_of_time);
        saveBtn=view.findViewById(R.id.update_exercise_save_btn_cardio);
        nameTv.setText("Name: "+ exerciseCardio.getName());
        setsEd.setText(String.valueOf(exerciseCardio.getSets()));
        timeEd.setText(String.valueOf(exerciseCardio.getTime()));
        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isInt(setsEd.getText().toString()) && isInt(timeEd.getText().toString())) {

                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setTitle("confirm").setMessage("are you sure you want to save this exercise?").setPositiveButton("yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {

                            exerciseManager.getExercise(index).setSets(Integer.parseInt(setsEd.getText().toString()));
                            ((ExerciseCardio) exerciseManager.getExercise(index)).setTime(Integer.parseInt(timeEd.getText().toString()));
                            setsEd.setText("");
                            timeEd.setText("");
                            Bundle bundle = new Bundle();
                            bundle.putInt("index", 3);
                            Navigation.findNavController(view).navigate(R.id.action_u_e_d_c_fragment_to_main_fragment, bundle);
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
    private boolean isInt(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
