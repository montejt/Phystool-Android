package com.gmail.montejt11.phystool;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

// serves as a map that keeps track of exercises that are pending update,
// then we can update whenever we want using dumpList().
// will only update exercises that have an order of 1 or higher,
// meaning those which are being selected
public class UpdateList {

    protected Map<String, Exercise> mExerciseMap;
    protected ExerciseViewModel mModel;

    UpdateList(ExerciseViewModel model) {
        mExerciseMap = new HashMap<String, Exercise>();
        mModel = model;
    }

    // update all the exercises in the map and then clear the list
    void dumpList() {
        for ( String key : mExerciseMap.keySet()) {
            mModel.update(mExerciseMap.get(key));
        }
        mExerciseMap.clear();
    }

    // add the given exercise to the map. Will replace any older entries
    void add(Exercise e) {
        // if the order is 0, just remove the old one from the map since we no longer
        // need to update it
        if (e.getOrder() == 0) {
            mExerciseMap.remove(e.getName());
        } else {
            mExerciseMap.put(e.getName(), e);
        }
    }

    // return whether updatelist contains the given exercise
    public boolean contains(Exercise e) {
        return mExerciseMap.containsKey(e.getName());
    }
}
