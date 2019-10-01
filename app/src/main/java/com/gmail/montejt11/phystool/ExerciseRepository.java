package com.gmail.montejt11.phystool;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;

import java.util.List;

public class ExerciseRepository {
    private ExerciseDao mExerciseDao;
    private LiveData<List<Exercise>> mSelected;
    private LiveData<List<Exercise>> mUnselected;

    ExerciseRepository(Application application) {
        ExerciseRoomDatabase db = ExerciseRoomDatabase.getDatabase(application);
        mExerciseDao = db.exerciseDao();
        mSelected = mExerciseDao.getSelected();
        mUnselected = mExerciseDao.getUnselected();
    }

    // wrappers for getting data
    LiveData<List<Exercise>> getSelected() {
        return mSelected;
    }
    LiveData<List<Exercise>> getUnselected() {
        return mUnselected;
    }

    // wrapper for update. Takes in multiple exercises and updates them.
    // modifying data must take place on non-UI thread, so we use an AsyncTask
    public void update (Exercise... exercises) {
        new insertAsyncTask(mExerciseDao).execute(exercises);
    }

    // insertAsyncTask class.
    private static class insertAsyncTask extends AsyncTask<Exercise, Void, Void> {

        private ExerciseDao mAsyncTaskDao;

        insertAsyncTask(ExerciseDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Exercise... params) {
            // for every exercise passed to us, update it
            for (Exercise e : params) {
                mAsyncTaskDao.update(e);
            }
            return null;
        }
    }
}
