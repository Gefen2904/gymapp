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

public class ShowProgressCardioExerciseDetailsFragment extends Fragment {
    ExerciseManager manager;
    TextView nameTv;
    TextView setsTv;
    TextView timeTv;
    FloatingActionButton back;
    int num;
    ExerciseCardio exerciseCardio;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.show_progress_exercise_details_cardio, container, false);
        manager=ExerciseManager.getInstance(getActivity());
        nameTv=view.findViewById(R.id.show_exercise_name_cardio);
        setsTv=view.findViewById(R.id.show_exercise_num_of_sets_cardio);
        timeTv=view.findViewById(R.id.show_exercise_num_of_time_cardio);
        back=view.findViewById(R.id.show_exercise_save_btn_cardio);
        num=getArguments().getInt("index");
        exerciseCardio= (ExerciseCardio) manager.getExercise(num);
        nameTv.setText("Name: "+exerciseCardio.getName());
        setsTv.setText("Number of sets: "+exerciseCardio.getSets());
        timeTv.setText("Time of each set: "+exerciseCardio.getTime());

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle=new Bundle();
                bundle.putInt("index",2);
                Navigation.findNavController(view).navigate(R.id.action_s_p_e_d_c_fragment_to_main_fragment,bundle);
            }
        });
        return view;
    }
}
