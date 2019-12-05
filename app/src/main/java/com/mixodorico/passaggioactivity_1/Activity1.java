package com.mixodorico.passaggioactivity_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class Activity1 extends AppCompatActivity {

    private static final String TAG = "Demo1";
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    Context context;
    final int duration = Toast.LENGTH_SHORT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
        context = getApplicationContext();


        //getActionBar().setDisplayHomeAsUpEnabled(true); // occorre per inserire la freccia all'indietro. Basta pero' modificare il Manifest inserendo il parent

        //region SIGNIN GOOGLE

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Set the dimensions of the sign-in button.
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(signInClick);

        //findViewById(R.id.sign_in_button).setOnClickListener((View.OnClickListener) this);
        //endregion
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        //updateUI(account);

        /*
        * - If GoogleSignIn.getLastSignedInAccount returns a GoogleSignInAccount object (rather than null), the user has already signed in to your app with Google.
        * Update your UI accordingly—that is, hide the sign-in button, launch your main activity, or whatever is appropriate for your app.
        * - If GoogleSignIn.getLastSignedInAccount returns null, the user has not yet signed in to your app with Google. Update your UI to display the Google Sign-in button.
        */
    }

    private View.OnClickListener signInClick = new View.OnClickListener() {
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.sign_in_button:
                    signIn();
                    break;
            }
        }
    };

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }

    private void updateUI(GoogleSignInAccount googleSignInAccount){
        if(googleSignInAccount!=null){
            Toast.makeText(context, R.string.oggettoNonNull, duration).show();
            Toast.makeText(context, "Email: "+ googleSignInAccount.getEmail(), duration).show();
            Toast.makeText(context, "ID: "+ googleSignInAccount.getId(), duration).show();
            Toast.makeText(context, "ID_Token: "+ googleSignInAccount.getIdToken(), duration).show();
        }else{
            Toast.makeText(context, R.string.oggettoNull, duration).show();
        }
    }
}
