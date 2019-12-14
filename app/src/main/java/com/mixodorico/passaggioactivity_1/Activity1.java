package com.mixodorico.passaggioactivity_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Activity1 extends AppCompatActivity {


    private GoogleSignInOptions gso;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;
    private Context context;

    //private CallAPI callAPI;
    final int duration = Toast.LENGTH_SHORT;
    private static final String TAG = "Demo1";

    //region CREATE,START
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_1);
        context = getApplicationContext();

        //region SIGNIN GOOGLE

        // Cofigurazione della richiesta di sign-in: e-mail, ID e info di base
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                //.requestIdToken(getString(R.string.server_client_id)) // Per mandare un token al proprio server https
                .requestEmail()
                .build();

        // Definizione di un GoogleSignInClient con la configurazione del gso
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Definizione della dimensone del bottone di SignIn
        SignInButton signInButton = findViewById(R.id.sign_in_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(signInClick);

        //endregion
    }

    @Override
    protected void onStart() {
        super.onStart();

        /*
         * Nello Start si controlla l'esistenza di un Account fi Google SignIn e se l'utente e' gia' autenticato. Due casi:
         * - Se GoogleSignIn.getLastSignedInAccount restituisce un GoogleSignInAccount object (invece di null), allora l'itente e' gia' loggato nell'app tramite Google.
         * IN QUESTO CASO SI DEVE PROCEDERE AGGIORNANDO DIRETTAMENTE L'UI, LANCIANDO DIRETTAMENTE L'APPLICAZIONE SENZA MOSTRARE IL BOTTONE DI LOGIN.
         * - Se restituisce null, allora l'utente deve ancora effettuare l'accesso. In questo caso si deve mostrare il bottone per il Signin
        */

        // MODO 1 (Non silent Singin - in genere da usare se non funziona il Silent)
        //GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this); // da utilizzare se il metodo silent non funziona
        //updateUI(account);

        //MODO 2 (Silent SingIn)
        /*
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient (context, gso);
        googleSignInClient.silentSignIn ()
                .addOnCompleteListener ((Activity) context, new OnCompleteListener<GoogleSignInAccount> () {

                    @Override
                    public void onComplete (@NonNull Task<GoogleSignInAccount> task) {
                        handleSignInResult (task);
                    }
                });
         */
    }
    //endregion

    //region LISTENER + SIGNIN-OUT

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

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                    }
                });
    }
    //endregion

    // da usare se non si può effettuare col silent
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);

        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            //handleSignInResult(task);
        }
    }

    /*
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            String idToken = account.getIdToken();

            //region INVIO ID AL SERVER TRAMITE HTTPS POST
            callAPI = new CallAPI("492606205053-3i3unmv23a41ml8eiv8tk7c7lgcehj3k.apps.googleusercontent.com", idToken);
            callAPI.doInBackground("","","");

            //endregion

            // Signed in successfully, show authenticated UI.
            updateUI(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            updateUI(null);
        }
    }
    */

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

    // region MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id=item.getItemId();
        switch(id)
        {
            case R.id.MENU_1: signOut();
                break;
            case R.id.MENU_2: Toast.makeText(context, "Tutto funziona correttamente", duration).show();

        }
        return false;
    }
    //endregion
}

/*
Just add this in your dependencies

compile 'org.apache.httpcomponents:httpcore:4.4.1'
compile 'org.apache.httpcomponents:httpclient:4.5'

Finally

dependencies {
compile fileTree(dir: 'libs', include: ['*.jar'])
testCompile 'junit:junit:4.12'
compile 'com.android.support:appcompat-v7:23.0.1'
compile 'com.android.support:design:23.0.1'
compile 'org.apache.httpcomponents:httpcore:4.4.1'
compile 'org.apache.httpcomponents:httpclient:4.5'
}

And also add this code:

 android {
    useLibrary 'org.apache.http.legacy'
         }
*
FYI

Specify requirement for Apache HTTP Legacy library If your app is targeting API level 28 (Android 9.0) or above, you must include the following declaration within the element of AndroidManifest.xml.

 <uses-library
      android:name="org.apache.http.legacy"
      android:required="false" />
*/