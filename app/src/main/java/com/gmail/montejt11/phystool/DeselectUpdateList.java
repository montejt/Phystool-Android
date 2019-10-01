package com.gmail.montejt11.phystool;

// serves as a map that keeps track of exercises that are pending update,
// then we can update whenever we want using dumpList().
// will only update exercises that have an order of 0,
// meaning those which are being deselected
public class DeselectUpdateList extends UpdateList {

    DeselectUpdateList(ExerciseViewModel model) {
        super(model);
    }

    // add the given exercise to the map. Will replace any older entries
    @Override
    void add(Exercise e) {
        mExerciseMap.put(e.getName(), e);
    }
}
