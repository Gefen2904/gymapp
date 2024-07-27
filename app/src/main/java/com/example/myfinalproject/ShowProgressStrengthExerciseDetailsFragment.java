package com.example.myfinalproject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class ShowProgressStrengthExerciseDetailsFragment extends Fragment {

    ExerciseManager manager;
    TextView nameTv;
    TextView setsTv;
    TextView repsTv;
    TextView weightTv;
    FloatingActionButton back;

    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, @Nullable ViewGroup container,  Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_progress_exercise_details_strength, container, false);
        manager=ExerciseManager.getInstance(getActivity());
        nameTv=view.findViewById(R.id.show_exercise_name_strength);
        setsTv=view.findViewById(R.id.show_exercise_num_of_sets_strength);
        repsTv=view.findViewById(R.id.show_exercise_num_of_reps_strength);
        weightTv=view.findViewById(R.id.show_exercise_num_of_weight_strength);
        back=view.findViewById(R.id.show_progress_back_btn_strength);

        int num=getArguments().getInt("index");
        ExerciseStrength exerciseStrength= (ExerciseStrength) manager.getExercise(num);
        nameTv.setText("Name: "+ exerciseStrength.getName());
        setsTv.setText("Number of sets: "+ exerciseStrength.getSets());
        repsTv.setText("Number of reps: "+ exerciseStrength.getReps());
        weightTv.setText("Amount of weight for every rep: "+Double.toString(exerciseStrength.getWeight()));

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putInt("index",2);
                Navigation.findNavController(view).navigate(R.id.action_s_p_e_d_s_fragment_to_main_fragment,bundle);
            }
        });
        return view;



    }
}
