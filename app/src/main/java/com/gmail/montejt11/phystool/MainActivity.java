package com.gmail.montejt11.phystool;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize the buttons
        initializeMapButton();
        initializeContactButton();
        initializeAddButton();
        initializeRoutineButton();
    }

    // initializes add button, which takes the user to the exercise add page
    private void initializeAddButton() {
        final Button addButton = findViewById(R.id.plusButton);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToExerciseAdder = new Intent(getApplicationContext(), SelectActivity.class);
                startActivity(goToExerciseAdder);
            }
        });
    }

    // initializes the map button's functionality and image.
    private void initializeMapButton() {
        // get the mapImage button
        final ImageButton mapImageButton = findViewById(R.id.mapImageButton);
        // make a bitmap option
        final BitmapFactory.Options options = new BitmapFactory.Options();
        // get the map picture for the button
        final int pic = R.drawable.map;

        // Only runs after the rest of the layout is finalized so that we can access view dimensions.
        // Resizes and sets the image in the mapImageButton
        mapImageButton.post(new Runnable() {
            @Override
            public void run() {
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeResource(getResources(), pic, options);
                // get widths and calculate ratio
                int imgWidth = options.outWidth;
                int viewWidth = mapImageButton.getWidth();
                int ratio = Math.round((float) imgWidth / (float) viewWidth);
                // set sample size to ratio
                options.inSampleSize = ratio;
                options.inJustDecodeBounds = false;
                //remake bitmap, now rendering it
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), pic, options);
                // resize the bitmap. Our new height is the image height divided by the ratio of the image width to the
                // view width
                int newHeight = Math.round((float) options.outHeight / ((float) imgWidth / (float) viewWidth));
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, viewWidth, newHeight, false);

                // set the image to our new scaled map
                mapImageButton.setImageBitmap(scaledBitmap);
            }
        });

        // make the map button open up map to clinic using default android map app
        mapImageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent openMap = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:46.979933, -123.595139?q=" + Uri.encode("Montesano Physical Therapy")));
                if (openMap.resolveActivity(getPackageManager()) != null) {
                    startActivity(openMap);
                }
            }
        });
    }

    // initializes the contact us button and functionality
    private void initializeContactButton() {
        // find the contact button
        Button contactUsButton = (Button) findViewById(R.id.contactUsButton);

        // when clicked, the button will take us to the contact info activity
        contactUsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToContactInfo = new Intent(getApplicationContext(), ContactInfoActivity.class);
                startActivity(goToContactInfo);
            }
        });
    }

    private void initializeRoutineButton() {
        final ImageButton button = (ImageButton) findViewById(R.id.currentRoutineImageButton);
        ExerciseViewModel viewModel = ViewModelProviders.of(this).get(ExerciseViewModel.class);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goToRoutine = new Intent(getApplicationContext(), SelectedExercisesActivity.class);
                startActivity(goToRoutine);
            }
        });

        // create observer for the list of exercises which will set the image of the button to the first selected exercise
        // in the routine whenever the routine is changed.
        viewModel.getSelected().observe(this, new Observer<List<Exercise>>() {
            @Override
            public void onChanged(List<Exercise> exercises) {
                if (exercises != null) {
                    Context context = getApplicationContext();
                    Drawable img = ContextCompat.getDrawable(context, R.drawable.lunge);
                    if (exercises.size() != 0) {
                        img = ContextCompat.getDrawable(context, context.getResources().getIdentifier(exercises.get(0).getImage(), "drawable", context.getPackageName()));
                    }
                    button.setImageDrawable(img);
                }
            }
        });
    }
}
