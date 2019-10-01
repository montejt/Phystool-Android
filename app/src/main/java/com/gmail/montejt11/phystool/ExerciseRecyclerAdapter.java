package com.gmail.montejt11.phystool;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class ExerciseRecyclerAdapter extends RecyclerView.Adapter<ExerciseRecyclerAdapter.ExerciseViewHolder> {

    private Context mContext;
    private final LayoutInflater mInflater;
    private List<Exercise> mExercises;
    private UpdateList mUpdateList;

    public ExerciseRecyclerAdapter(Context context, ExerciseViewModel model, UpdateList list) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mUpdateList = list;
    }

    // holds inflated view, provides access to fields of views
    class ExerciseViewHolder extends RecyclerView.ViewHolder {
        private final CardView card;
        private final ImageView img;
        private final TextView name;
        private final TextView description;

        private final Button selectButton;
        private boolean selected;

        private ExerciseViewHolder(View inflatedView) {
            super(inflatedView);
            card = inflatedView.findViewById(R.id.cardCardView);
            img = inflatedView.findViewById(R.id.exerciseImageView);
            name = inflatedView.findViewById(R.id.exerciseNameTextView);
            description = inflatedView.findViewById(R.id.exerciseDecriptionTextView);

            // manage select button. will highlight card when pressed and will update it
            selectButton = inflatedView.findViewById(R.id.addExerciseButton);
            //selectButton.setVisibility(View.INVISIBLE);
            selected = false;
            selectButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // if the given exercise is selected already: deselect it,
                    // unhighlight it and remove it from the updatelist.
                    // otherwise select it, highlight it, and add it to the updatelist
                    if (selected) {
                        selected = false;
                        card.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.cardview_light_background));
                        // add updated exercise to updatelist
                        mUpdateList.add(new Exercise(name.getText().toString(), (String) img.getTag(), description.getText().toString(), 0));
                    } else {
                        selected = true;
                        card.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.colorSelected));
                        // add updated exercise to updatelist
                        mUpdateList.add(new Exercise(name.getText().toString(), (String) img.getTag(), description.getText().toString(), 1));
                    }
                }
            });
        }

    }

    @NonNull
    @Override
    // Creates new exerciseviewholder from an inflated activity_select_details layout
    public ExerciseRecyclerAdapter.ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflatedView = mInflater.inflate(R.layout.activity_select_details, parent, false);
        return new ExerciseViewHolder(inflatedView);
    }

    @Override
    // when binding to the view holder, set all of the fields to accomidate the new exercise
    public void onBindViewHolder(@NonNull ExerciseRecyclerAdapter.ExerciseViewHolder holder, int position) {
        if (mExercises != null) {
            // we have exercises, so initialize the view holder
            Exercise curr = mExercises.get(position);
            holder.name.setText(curr.getName());
            holder.description.setText(curr.getDescription());

            // get the image id from the images recourse entry name
            int imgId = mContext.getResources().getIdentifier(curr.getImage(), "drawable", mContext.getPackageName());
            // get the image from the image's resource id and set the holders image
            Drawable image = ContextCompat.getDrawable(mContext, imgId);
            holder.img.setImageDrawable(image);
            // set a tag on the holders image view that holds the resource entry name for the image
            holder.img.setTag(curr.getImage());

            // if the exercise is in the updatelist, it is selected so set the fields accordingly,
            // otherwise set the fields to default
            if (mUpdateList.contains(curr)) {
                holder.selected = true;
                holder.card.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.colorSelected));
            } else {
                holder.selected = false;
                holder.card.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.cardview_light_background));
            }
        } else {
            // no exercises, so put placement text and set fields to default
            holder.name.setText("nothing");
            holder.description.setText("nothing");
            holder.selected = false;
        }
    }

    // update the cached exercises
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
