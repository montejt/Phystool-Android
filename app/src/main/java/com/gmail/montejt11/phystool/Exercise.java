package com.gmail.montejt11.phystool;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "exercise_table")
public class Exercise {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "name")
    private String mName; // name of exercise

    @NonNull
    @ColumnInfo(name = "image_entry")
    private String mImage; // name of image entry file

    @NonNull
    @ColumnInfo(name = "description")
    private String mDescription; // description of exercise

    @NonNull
    @ColumnInfo(name = "num_reps")
    private int mNumReps; // number of required reps

    @NonNull
    @ColumnInfo(name = "order")
    private int mOrder; // the order the exercise appears in on the selected exercise screen.
                           // -1 = not visible to user to select, 0 = not selected, <1 = selected with 1 being the first

    //constructor which takes in order
    public Exercise(String name, String image, String description, int order) {
        mName = name;
        mImage = image;
        mDescription = description;
        mNumReps = 0;
        mOrder = order;
    }

    public Exercise(Exercise toCopy) {
        mName = toCopy.getName();
        mImage = toCopy.getImage();
        mDescription = toCopy.getDescription();
        mNumReps = toCopy.getNumReps();
        mOrder = toCopy.getOrder();
    }

    // getter methods
    public String getName() {return this.mName;}
    public String getImage() {return this.mImage;}
    public String getDescription() {return this.mDescription;}
    public int getNumReps() {return this.mNumReps;}
    public int getOrder() {return this.mOrder;}

    // setter methods
    public void setName(String string) {this.mName = string;}
    public void setImage(String name) {this.mImage = name;}
    public void setDescription(String string) {this.mDescription = string;}
    public void setNumReps(int num) {this.mNumReps = num;}
    public void setOrder(int num) {this.mOrder = num;}
}
