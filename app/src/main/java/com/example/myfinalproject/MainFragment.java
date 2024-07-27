package com.example.myfinalproject;

import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class MainFragment extends Fragment {

    private SelectedDays trainingDays;
    private ExerciseManager manager;
    private PictureManager pictureManager;
    private ExerciseAdapter adapter ;
    private RecyclerView recyclerView;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private CoordinatorLayout coordinatorLayout;
    private static boolean firstTime=true;
    private TextView TitleTV;
    private  String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    private  boolean[] selectedDays = {false, false, false, false, false, false, false};
    String dayTime;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        trainingDays=SelectedDays.getInstance(getActivity());
        manager =  ExerciseManager.getInstance(getActivity());
        pictureManager=PictureManager.getInstance(getActivity());
        recyclerView=view.findViewById(R.id.show_progress_exercise_list_recycler_view);
        drawerLayout=view.findViewById(R.id.drawer_layout);
        coordinatorLayout=view.findViewById(R.id.main_layout);
        TitleTV=view.findViewById(R.id.workout_title);
        navigationView=view.findViewById(R.id.navigation_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter= new ExerciseAdapter(manager.getExercises());
        adapter.SetListener(new ExerciseAdapter.ExerciseListener() {
            @Override
            public void OnExerciseClicked(int i, View view) {
                if (manager.getExercise(i) instanceof ExerciseCardio) {
                    Bundle bundle = new Bundle();
                    bundle.putInt("index",i);
                    Navigation.findNavController(view).navigate(R.id.action_main_fragment_to_s_p_e_d_c_fragment,bundle);
                }
                else{
                    Bundle bundle=new Bundle();
                    bundle.putInt("index",i);
                    Navigation.findNavController(view).navigate(R.id.action_main_fragment_to_s_p_e_d_s_fragment,bundle);
                }
            }

            @Override
            public void OnExerciseLongClick(int i, View view) {
                if(manager.getExercise(i)instanceof ExerciseCardio) {
                    Bundle bundle=new Bundle();
                    bundle.putInt("index",i);
                    Navigation.findNavController(view).navigate(R.id.action_main_fragment_to_u_e_d_c_fragment,bundle);
                }else{
                    Bundle bundle=new Bundle();
                    bundle.putInt("index",i);
                    Navigation.findNavController(view).navigate(R.id.action_main_fragment_to_u_e_d_s_fragment,bundle);
                }
            }
        });
        for(int i =0;i<trainingDays.getDays().size();i++){
            for(int j =0;j<7;j++) {
                if (trainingDays.getDays().get(i).getDay().equals(daysOfWeek[j]))
                    selectedDays[j] = true;
            }
        }
        TitleTV.setText(manager.getWorkoutName());
        recyclerView.setAdapter(adapter);
        ItemTouchHelper.SimpleCallback callback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                manager.removeExercise(viewHolder.getAdapterPosition());
                adapter.notifyDataSetChanged();
                Snackbar.make(coordinatorLayout,"This exercise has been removed",Snackbar.LENGTH_SHORT).show();
            }
        };
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        Toolbar toolbar = view.findViewById(R.id.toolbar);
        AppCompatActivity activity = (AppCompatActivity) getActivity();
        activity.setSupportActionBar(toolbar);
        ActionBar actionBar = activity.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeAsUpIndicator(R.drawable.menu_symbol);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                item.setCheckable(true);
                drawerLayout.closeDrawers();
                int id=item.getItemId();
                if(id==R.id.about){
                    AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                    builder.setMessage(R.string.about).show();
                }else if(id==R.id.build_new_workout){
                    Navigation.findNavController(view).navigate(R.id.action_main_fragment_to_n_w_e_l_fragment);
                }else if(id==R.id.see_pictures){
                    Navigation.findNavController(view).navigate(R.id.action_main_fragment_to_pics_fragment);
                }else if(id==R.id.music){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com"));
                    try{
                        startActivity(intent);
                    }catch (Exception e){
                        Snackbar.make(coordinatorLayout,"No youtube installed",Snackbar.LENGTH_SHORT).show();
                    }
                }if(id==R.id.load_old_workout) {
                    Navigation.findNavController(view).navigate(R.id.action_main_fragment_to_workouts_history_fragment);
                }else if(id==R.id.change_days){
                    if(!trainingDays.getDays().isEmpty())
                    showDaysOfWeekDialog();
                    else
                        Snackbar.make(coordinatorLayout,"No workout built you first need to build a workout through the build workout button in the menu",Snackbar.LENGTH_SHORT).show();
                }

                return false;
            }
        });
        setHasOptionsMenu(true);
        if(!firstTime&&getArguments() != null)
        {
            int num=getArguments().getInt("index",0);
            if(num==1)
                Snackbar.make(coordinatorLayout,"Workout saved",Snackbar.LENGTH_SHORT).show();
            if(num==2)
                Snackbar.make(coordinatorLayout,"To update the exercise long click",Snackbar.LENGTH_SHORT).show();
            if(num==3)
                Snackbar.make(coordinatorLayout,"Exercise updated",Snackbar.LENGTH_SHORT).show();
            if(num==4)
                Snackbar.make(coordinatorLayout,"Workout changed",Snackbar.LENGTH_SHORT).show();
        }else
            firstTime=false;
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            drawerLayout.openDrawer(GravityCompat.START);

        return super.onOptionsItemSelected(item);
    }

    private void showDaysOfWeekDialog() {
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
