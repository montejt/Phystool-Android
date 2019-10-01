package com.gmail.montejt11.phystool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;

public class SelectActivity extends AppCompatActivity {

    private ExerciseViewModel mExerciseViewModel;
    private UpdateList mUpdateList; // stores exercises pending updates

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select);

        mExerciseViewModel = ViewModelProviders.of(this).get(ExerciseViewModel.class);
        mUpdateList = new UpdateList(mExerciseViewModel);

        RecyclerView recyclerView = findViewById(R.id.exerciseRecycleView);
        final ExerciseRecyclerAdapter adapter = new ExerciseRecyclerAdapter(this, mExerciseViewModel, mUpdateList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // create observer for the unselected exercise data which will determine what is available
        // to be listed for the user
        mExerciseViewModel.getUnselected().observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                // update the cached copy of exercises in the adapter when changed in the database
                adapter.setExercises(exercises);
            }
        });

        // get the complete selection button and set it to where whenever it is
        // clicked it will dump the updatelist, updating the exercises
        Button selectionCompleteButton = findViewById(R.id.completeAddSelectionButton);
        selectionCompleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUpdateList.dumpList();
            }
        });
    }
}
