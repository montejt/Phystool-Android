package com.gmail.montejt11.phystool;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class SelectedExercisesRecyclerAdapter extends RecyclerView.Adapter<SelectedExercisesRecyclerAdapter.SelectedExercisesViewHolder> implements SwipeAndDragHelper.ActionCompletionContract {

    private Context mContext;
    private final LayoutInflater mInflater;
    private List<Exercise> mExercises;
    private ExerciseViewModel mViewModel;
    private UpdateList mUpdateList; // keeps track of changed exercises which will need
    // to be updated in the database

    public SelectedExercisesRecyclerAdapter(Context context, ExerciseViewModel model, UpdateList updateList) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mViewModel = model;
        mUpdateList = updateList;
    }

    // holds inflated view, provides access to fields of views
    class SelectedExercisesViewHolder extends RecyclerView.ViewHolder {
        private final CardView card;
        private final ImageView img;
        private final TextView name;
        private final TextView description;
        private final EditText numReps;

        private SelectedExercisesViewHolder(View inflatedView) {
            super(inflatedView);
            card = inflatedView.findViewById(R.id.sCCardView);
            img = inflatedView.findViewById(R.id.sCExImageView);
            name = inflatedView.findViewById(R.id.sCExName);
            description = inflatedView.findViewById(R.id.SCExDescription);
            numReps = inflatedView.findViewById(R.id.sCExNumRepsEditText);
        }
    }

    @Override
    public void onViewMoved(int oldPosition, int newPosition) {
        // move the exercise into its new position in the cached array
        Exercise targetExercise = mExercises.get(oldPosition);
        Exercise exercise = new Exercise(targetExercise);
        mExercises.remove(oldPosition);
        mExercises.add(newPosition, exercise);

        // set the orders for all of the exercises in the cached array so that
        // they are in descending order with the lowest being 1
        List<Exercise> updatedExercises = new ArrayList<>();
        for (int i = 0; i < mExercises.size(); i++) {
            // get the ith element
            Exercise target = mExercises.get(i);
            // set its order to be the size of mExercises - its index.
            // in other words the order is the index but going in reverse and + 1
            target.setOrder(mExercises.size() - i);
            // add the updated exercise to the list of updated exercises
            updatedExercises.add(i, target);
        }

        // add all of the updated Exercises to the updateList
        for (Exercise e : updatedExercises) {
            mUpdateList.updateOrder(e);
        }

        // notify the recycler view that an item has moved
        notifyItemMoved(oldPosition, newPosition);
    }

    @Override
    // when the view is swiped, set its associated exercise's order to zero and remove it from the
    // cached exercise list. Also put it into the update list so it can wait to be updated
    public void onViewSwiped(int position) {
        // get the exercise we are removing, store a copy of it with its order set to 0 in the
        // update list, then remove it from the cached exercises
        Exercise target = mExercises.get(position);
        Exercise updatedTarget = new Exercise(target);
        updatedTarget.setOrder(0);
        mUpdateList.updateOrder(updatedTarget);
        mExercises.remove(target);

        // notify the adapter that we removed the exercise
        notifyItemRemoved(position);
    }

    @NonNull
    @Override
    // Creates new viewholder from an inflated activity_select_details layout
    public SelectedExercisesRecyclerAdapter.SelectedExercisesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflatedView = mInflater.inflate(R.layout.card_for_listed_exercises, parent, false);
        return new SelectedExercisesViewHolder(inflatedView);
    }

    @Override
    // when binding to the view holder, set all of the fields to accommodate the new exercise
    public void onBindViewHolder(@NonNull final SelectedExercisesRecyclerAdapter.SelectedExercisesViewHolder holder, int position) {
        if (mExercises != null) {
            // we have exercises, so set the ViewHolders fields for our
            // current exercise
            final Exercise curr = mExercises.get(position);
            holder.name.setText(curr.getName());
            holder.description.setText(curr.getDescription());

            // get the image from the image's entry name stored in the exercise. Then set the
            // image in the imageview. Then set a tag on the imageview which contains the
            // entry name so that we can get the drawable later if we need it.
            Drawable image = ContextCompat.getDrawable(mContext, mContext.getResources().getIdentifier(curr.getImage(), "drawable", mContext.getPackageName()));
            holder.img.setImageDrawable(image);
            holder.img.setTag(curr.getImage());

            // set the num reps to whatever is stored, or display nothing
            if (curr.getNumReps() == 0) {
                holder.numReps.setText("");
            } else {
                holder.numReps.setText(String.valueOf(curr.getNumReps()));
            }

            // crteate watcher for the edittext
            TextWatcher watcher = new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void afterTextChanged(Editable editable) {
                    // copy the current exercise
                    Exercise newExercise = new Exercise(curr);
                    // set the newExercises numreps and add it to the updatelist
                    String number = editable.toString();
                    if (number.equals("")) {
                        number = "0";
                    }
                    newExercise.setNumReps(Integer.parseInt(number));
                    mUpdateList.updateNumReps(newExercise);

                    // set the numreps for curr in the cached list as well
                    curr.setNumReps(newExercise.getNumReps());
                }
            };

            // apply watcher to the numReps editText
            holder.numReps.addTextChangedListener(watcher);
        } else {
            // no exercises, so put placement text and set fields to default
            holder.name.setText("nothing");
            holder.description.setText("nothing");
        }
    }

    // set the cached list of exercises to be the given list
    void setExercises(List<Exercise> exercises) {
        mExercises = exercises;
        notifyDataSetChanged();
    }

    @Override
    // return the number of cached exercises
    public int getItemCount() {
        if (mExercises != null) {
            return mExercises.size();
        } else {
            return 0;
        }
    }
}
