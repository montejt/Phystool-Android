package com.gmail.montejt11.phystool;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ExerciseDao {

    @Insert
    public void insert(Exercise... exercises); // insert given exercises

    @Update
    public void update(Exercise... exercises); // update given exercises

    @Delete
    public void delete(Exercise... exercises); // deletes all given exercises

    // get selected exercises in Descending order organized by the order field
    @Query("SELECT * FROM exercise_table WHERE `order` > 0 ORDER BY `order` DESC")
    public LiveData<List<Exercise>> getSelected();

    // gets all unselected but visible exercises
    @Query("SELECT * FROM exercise_table WHERE `order` == 0")
    public LiveData<List<Exercise>> getUnselected();

    // test getter
    @Query("SELECT * FROM exercise_table WHERE `order` == 0")
    public List<Exercise> getUnselectedTest();

    // gets all invisible exercises
    @Query("SELECT * FROM exercise_table WHERE `order` == -1")
    public LiveData<List<Exercise>> getInvisible();

    // deletes all entries from exercise table, ONLY FOR TESTING
    @Query("DELETE FROM exercise_table")
    void nuke();
}