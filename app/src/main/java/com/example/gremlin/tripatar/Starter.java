package com.example.gremlin.tripatar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.example.gremlin.dto.Data;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.parse.GetCallback;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseException;

import com.facebook.FacebookSdk;
import com.parse.ParseUser;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Starter extends AppCompatActivity {

    private LoginButton loginButton;
    //private CallbackManager callbackManager;
    private TextView tView;
    private Starter s;
    public ProgressDialog pd;

    private void showProgressBar() {
        pd = new ProgressDialog(this);
        pd.setTitle("Loading Getaways !");
        pd.setMessage("Please wait....");
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());

        Resources res = getResources();
        Parse.initialize(this, res.getString(R.string.parse_app_id), res.getString(R.string.parse_client_key));
        ParseFacebookUtils.initialize(getApplicationContext());

        setContentView(R.layout.activity_starter);

        // ui textView
        tView = (TextView) findViewById(R.id.textView);

        // Facebook stuff phew!!
        //callbackManager = CallbackManager.Factory.create();
        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("user_friends");

        s = this;

        showProgressBar();
        ParseFacebookUtils.logInWithReadPermissionsInBackground(this, null, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException err) {
                if (user == null) {
                    Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                } else {//if (user.isNew()) {
                    Log.d("MyApp", "User signed up and logged in through Facebook!");
                    //ArrayList<Data> dataList =  getData();
                    Intent mainScreen = new Intent(s, AllGetaways.class);
                    //mainScreen.putExtra("data", dataList);
                    startActivity(mainScreen);
                }/* else {
                    Log.d("MyApp", "User logged in through Facebook!");
                    Intent mainScreen = new Intent(s, AllGetaways.class);
                    startActivity(mainScreen);
                }*/
            }
        });
        /*
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                tView.setText("User ID: " + loginResult.getAccessToken().getUserId().toString() + "\n" + "Auth Token: " + loginResult.getAccessToken().getToken().toString());
            }

            @Override
            public void onCancel() {
                tView.setText("Login Cancelled");
            }

            @Override
            public void onError(FacebookException exception) {
                tView.setText("Login failed" + exception.getMessage().toString());
            }
        });
        */
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ParseFacebookUtils.onActivityResult(requestCode, resultCode, data);
    }

    /* // Uncomment to use FB SDK only
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }
    */

    @Override
    protected void onResume() {
        super.onResume();

        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }


}
