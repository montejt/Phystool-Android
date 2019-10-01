package com.gmail.montejt11.phystool;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.gmail.montejt11.phystool.R;

public class ContactInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_info);

        // initialize buttons and functionalities
        initializeCallButton();
        initializeEmailButton();
        initializeWebsiteButton();
    }

    // initializes the call us button and functionality
    private void initializeCallButton() {
        // find the call button
        Button callButton = (Button) findViewById(R.id.callButton);

        // when clicked, the button will open a call app with our number
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri phoneUri = Uri.fromParts("tel", getString(R.string.clinic_phone_number), null);
                Intent call = new Intent(Intent.ACTION_DIAL, phoneUri);
                startActivity(call);
            }
        });
    }

    // initializes the email us button and functionality
    private void initializeEmailButton() {
        // find the email button
        Button emailButton = (Button) findViewById(R.id.emailButton);

        // when clicked, the button will open an email app with our email
        emailButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri emailUri = Uri.parse("mailto:" + getString(R.string.clinic_email));
                Intent email = new Intent(Intent.ACTION_VIEW, emailUri);
                startActivity(email);
            }
        });
    }

    // initializes the visit us button and functionality
    private void initializeWebsiteButton() {
        // find the website button
        final Button websiteButton = (Button) findViewById(R.id.websiteButton);

        // when clicked, the button will open our website in a search browser
        websiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri websiteUri = Uri.parse(getString(R.string.clinic_website));
                Intent visit = new Intent(Intent.ACTION_VIEW, websiteUri);
                startActivity(visit);
            }
        });
    }
}
