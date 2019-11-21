package com.gmail.montejt11.phystool;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class SelectedExercisesActivity extends AppCompatActivity {

    private ExerciseViewModel mViewModel;
    private UpdateList mUpdateList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_routine);

        mViewModel = ViewModelProviders.of(this).get(ExerciseViewModel.class);
        mUpdateList = new UpdateList(mViewModel);

        RecyclerView recyclerView = findViewById(R.id.SelectedExercisesRecycleView);
        final SelectedExercisesRecyclerAdapter adapter = new SelectedExercisesRecyclerAdapter(this, mViewModel, mUpdateList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SwipeAndDragHelper swipeAndDragHelper = new SwipeAndDragHelper(adapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(swipeAndDragHelper);
        touchHelper.attachToRecyclerView(recyclerView);

        mViewModel.getSelected().observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                adapter.setExercises(exercises);
            }
        });
    }

    @Override
    protected void onStop() {
        // once the activity is stopped, we will update all of the
        // changed exercises in the database
        mUpdateList.dumpList();
        super.onStop();
    }
}
