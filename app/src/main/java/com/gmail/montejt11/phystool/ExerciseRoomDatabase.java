package com.gmail.montejt11.phystool;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Exercise.class}, version = 1)
public abstract class ExerciseRoomDatabase extends RoomDatabase {
    public abstract ExerciseDao exerciseDao();

    // save instance as static and volatile (synced between different threads to assure data consistency)
    private static volatile ExerciseRoomDatabase INSTANCE;
    private static Context mContext;

    // get database function
    public static ExerciseRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ExerciseRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            ExerciseRoomDatabase.class, "exercise_database")
                            .addCallback(sExerciseDatabaseCallback)
                            .build();
                    mContext = context;
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sExerciseDatabaseCallback =
            new RoomDatabase.Callback() {

                // when being created for the first time, the database populates itself
                @Override
                public void onCreate(@NonNull SupportSQLiteDatabase db) {
                    super.onCreate(db);
                    new PopulateDbAsync(INSTANCE).execute();
                }
            };

    private static class PopulateDbAsync extends AsyncTask<Void, Void, Void> {

        private final ExerciseDao mDao;

        PopulateDbAsync(ExerciseRoomDatabase db) {
            mDao = db.exerciseDao();
        }

        // after being created, put population into here
        @Override
        protected Void doInBackground(final Void... params) {
            mDao.insert(new Exercise("Squats", mContext.getResources().getResourceEntryName(R.drawable.squat), "Keep your legs apart and back straight and slowly bend down is if trying to sit. Get low, and then lift yourself back up", 0));
            mDao.insert(new Exercise("Lunges", mContext.getResources().getResourceEntryName(R.drawable.lunge), "Bring one leg in front of the other and slowly bend down. Lift yourself back up and alternate legs", 0));
            mDao.insert(new Exercise("Quad Stretch", mContext.getResources().getResourceEntryName(R.drawable.quad_stretch), "Find somewhere to balance yourself and bend your leg back so you can grab your ankle. Hold for 10 seconds", 0));
            mDao.insert(new Exercise("Hip Abduction", mContext.getResources().getResourceEntryName(R.drawable.hip_abduction), "While keeping your leg and back straightened, lift your leg to the side and bring it back down", 0));
            mDao.insert(new Exercise("Hip Flexion", mContext.getResources().getResourceEntryName(R.drawable.hip_flexion), "While keeping your leg straightened, lift your leg forward and then bring it back down", 0));
            mDao.insert(new Exercise("Hip Extension", mContext.getResources().getResourceEntryName(R.drawable.hip_extension), "While keeping your leg straightened, lift your leg backward and then bring it back down", 0));

            return null;
        }
    }
}
