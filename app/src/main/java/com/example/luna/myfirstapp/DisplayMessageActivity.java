package com.example.luna.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.List;


public class DisplayMessageActivity extends AppCompatActivity {

    /** Declare the FirebaseAnalytics object at the top of your activity **/
    private FirebaseAnalytics mFirebaseAnalytics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_message);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //** Obtain the FirebaseAnalytics instance. **/
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        // Floating button not used
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Hi Jonathan!", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        final View googleLink = findViewById(R.id.link_to_google);
        googleLink.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                onGoogleLinkClicked(googleLink);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Get intent data from previous activity
        Intent intent = getIntent();
        String message = intent.getStringExtra(MyActivity.EXTRA_MESSAGE);
        String userType = intent.getStringExtra(MyActivity.USER_TYPE);
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText(message + " is a " + userType);

        // Add the text from previous activity to the view on this activity
        LinearLayout layout = (LinearLayout) findViewById(R.id.content);
        layout.addView(textView);

        // Set up the spinner
        Spinner spinner = (Spinner) findViewById(R.id.planets_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.planets_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                String ss = parent.getItemAtPosition(pos).toString();
                Snackbar.make(view, ss, Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();

                // An item was selected. You can retrieve the selected item using
                // parent.getItemAtPosition(pos)
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });
    }

    // Click Link to Google
    public void onGoogleLinkClicked(View googleView){
        Uri webpage = Uri.parse(String.valueOf("http://www.google.com"));
        Log.d("outboundLink", String.valueOf(webpage));
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, webpage);

        // Verify it resolves
        PackageManager packageManager = getPackageManager();
        List activities = packageManager.queryIntentActivities(browserIntent, 0);
        boolean isIntentSafe = activities.size() > 0;

        // Start an activity if there is a browser
        if (isIntentSafe) {
            // send Firebase event
            Bundle params = new Bundle();
            params.putString("event_action",webpage.toString());
            mFirebaseAnalytics.logEvent("outbound_link",params);
            Log.d("outbound_link", params.toString());
            // open browser
            startActivity(browserIntent);
        }
    }

    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_beer:
                if (checked) {
                    Snackbar.make(view, "You must be Alex!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    break;
                }
            case R.id.checkbox_coffee:
                if (checked) {
                    Snackbar.make(view, "You must be Jim!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    break;
                }
            case R.id.checkbox_tea:
                if (checked) {
                    Snackbar.make(view, "You must be Michael!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    break;
                }
            case R.id.checkbox_paint:
                if (checked) {
                    Snackbar.make(view, "Are you Megan?!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    break;
                }
            case R.id.checkbox_dirtBike:
                if (checked) {
                    Snackbar.make(view, "Can't guess Amanda or Emily!", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                    break;
                }
            case R.id.checkbox_trains:
                if (checked) {
                    Snackbar.make(view, "No one likes trains but Jimmy!", Snackbar.LENGTH_SHORT)
                            .setAction("Action", null).show();
                    break;
                }
                break;
        }
    }

}
