package com.example.luna.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.os.Bundle;
import com.google.firebase.analytics.FirebaseAnalytics;
import android.util.Log;


public class MyActivity extends AppCompatActivity {

    /** Declare the FirebaseAnalytics object at the top of your activity **/
    private FirebaseAnalytics mFirebaseAnalytics;

    public final static String EXTRA_MESSAGE = "com.example.luna.myfirstapp.MESSAGE";
    public final static String USER_TYPE = "com.example.luna.myfirstapp.USER_TYPE";

    public String userType = "Not Defined";

    @Override
    protected void onCreate(Bundle savedInstanceState) {   /** onCreate similar to pageview **/
        Bundle params = new Bundle();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        //** Obtain the FirebaseAnalytics instance. **/
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        params.putString("event_action",this.getClass().toString());
        //params.putString("activity_id",this.getClass().toString());
        mFirebaseAnalytics.logEvent("show_activity", params);
        Log.d("show_activity", params.toString());

    }



    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {
        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.edit_message);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message); // Pass the message to the next activity
        intent.putExtra(USER_TYPE, userType); // Pass the user type to the next activity
        Bundle params = new Bundle();
        params.putString("event_action",userType);
        params.putString("event_label",message);
        mFirebaseAnalytics.logEvent("form_submit",params);
        Log.d("form_submit", params.toString());
        startActivity(intent);
    }

    /** Called when the user clicks on the radio buttons */
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.radio_pirates:
                if (checked) {
                    userType = "pirate!";

                }
                break;
            case R.id.radio_ninjas:
                if (checked) {
                    userType = "ninja!!";
                }
                break;
        }
    }
}
