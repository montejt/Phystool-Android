package com.gmail.montejt11.phystool;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class ExerciseViewModel extends AndroidViewModel {
    private ExerciseRepository mRepository;
    private LiveData<List<Exercise>> mSelected;
    private LiveData<List<Exercise>> mUnselected;

    public ExerciseViewModel (Application application) {
        super(application);
        mRepository = new ExerciseRepository(application);
        mSelected = mRepository.getSelected();
        mUnselected = mRepository.getUnselected();
    }

    LiveData<List<Exercise>> getSelected() {return mSelected;}
    LiveData<List<Exercise>> getUnselected() {return mUnselected;}

    // updates all exercises passed to us
    public void update(Exercise... exercises) { mRepository.update(exercises);}
}
