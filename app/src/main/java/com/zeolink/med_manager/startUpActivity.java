package com.zeolink.med_manager;

import com.zeolink.med_manager.datasource.User;
import android.support.design.widget.Snackbar;
import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class startUpActivity extends AppCompatActivity implements View.OnClickListener {
    public static final int RC_SIGN_IN = 100;
    private GoogleSignInClient signInClient;
    private FirebaseAuth fAuth;
    private ProgressDialog progressDialog;
    private SignInButton btnSignIn;
    private DatabaseReference dbRef;
    private TextView statTextview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_up);
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(this);
        fAuth = FirebaseAuth.getInstance();
        dbRef = FirebaseDatabase.getInstance().getReference().child("Users");
         /*
        * Authenticate users with google
        * */

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.server_client_id))
                .requestEmail()
                .build();
        signInClient = GoogleSignIn.getClient(this, gso);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Connecting...");

    }

    @Override
    public void onClick(View view) {
        signInWithGoogle();
    }

    private void signInWithGoogle() {
        progressDialog.show();
        Intent signInIntent = signInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        statTextview.setText(resultCode);
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {

        /*
        * Handle the results from our authentication with google
        * */
        try {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            } else {
                progressDialog.show();
            }
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            //Authenticate with firebase
            firebaseAuthWithGoogle(account);
        } catch (ApiException e) {
            if (progressDialog != null && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            Snackbar.make(btnSignIn, "Sign in error!", Snackbar.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        fAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, (task -> {
                    if (task.isSuccessful()) {
                        /*
                        * Check for valid signed in user exists
                        * */
                        isUserAccountValid(account);
                    } else {
                        /*
                        * Error authenticating with firebase
                        * */
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        Snackbar.make(btnSignIn, task.getException().getMessage(), Snackbar.LENGTH_SHORT).show();
                    }
                }));
    }


   private void isUserAccountValid(GoogleSignInAccount account){
        dbRef.child(fAuth.getCurrentUser().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dSnapshot) {
                if (dSnapshot.exists()){
                    //User exists
                    startMedicationActivity(fAuth.getCurrentUser().getUid());
                }else {
                    //User doesn't exits, go ahead and save user information to database
                    addUser(new User(fAuth.getCurrentUser().getUid(), account.getDisplayName(),account.getEmail(),account.getPhotoUrl().toString()));
                }
            }

            @Override
            public void onCancelled(DatabaseError dbError) {

            }
        });
   }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = fAuth.getCurrentUser();
        if (user!= null){
            startMedicationActivity(user.getUid());
        }
    }

    private void addUser(User user) {
        /*
        * Helper method to save user to database
        * */
        DatabaseReference userRef = dbRef.child(user.getId());
        userRef.setValue(user);
        startMedicationActivity(user.getId());
    }

    private void startMedicationActivity(String userId) {
        if (progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
        //startActivity(MedicationActivity.newIntent(this,userId));
        finish();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (progressDialog != null && progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}

