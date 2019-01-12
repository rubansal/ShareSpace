package com.codingblocks.msitnotes;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.ErrorCodes;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.appinvite.FirebaseAppInvite;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static final int PICK_PDF_CODE = 1000;
    private static final int RC_SIGN_IN = 1234;
    private static final int REQUEST_INVITE = 12345;
    EditText editText;
    Button btnUpload;
    ProgressBar progressBar;
    TextView textViewStatus, textViewUploads;
    Spinner branchSpinner, semesterSpinner;
    StorageReference storageReference;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    FirebaseAuth firebaseAuth;
    String branch, semester;
    String brse;
    int i = 1;
    int id=0;
    int idStatus;
    ArrayList<Upload> uploads=new ArrayList<>();

    private LottieAnimationView lottieAnimationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Facebook Login button
       /* mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.login_button);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                //handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
                // ...
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
                // ...
            }
        });*/

// ...

        //facebook login

        editText = findViewById(R.id.editText);
        btnUpload = findViewById(R.id.btnUpload);
        //progressBar = findViewById(R.id.progressBar);
        textViewStatus = findViewById(R.id.textViewStatus);
        textViewUploads = findViewById(R.id.textViewUploads);
        branchSpinner = findViewById(R.id.branchSpinner);
        semesterSpinner = findViewById(R.id.semesterSpinner);
        lottieAnimationView=findViewById(R.id.lottieAnimationView);

        Toolbar toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar()!=null)
            getSupportActionBar().setTitle("Share Space");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser != null) {
            i = 0;
            addListners();
        } else {
           // FacebookSdk.sdkInitialize(getApplicationContext());
            signIn();
            }

        FirebaseDynamicLinks.getInstance().getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData data) {
                        if (data == null) {
                            Log.d("TAG", "getInvitation: no data");
                            return;
                        }

                        // Get the deep link
                        Uri deepLink = data.getLink();

                        // Extract invite
                        FirebaseAppInvite invite = FirebaseAppInvite.getInvitation(data);
                        if (invite != null) {
                            String invitationId = invite.getInvitationId();
                        }

                        // Handle the deep link
                        // ...
                    }
                })
                .addOnFailureListener(this, new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w("TAG", "getDynamicLink:onFailure", e);
                    }
                });

    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        // Pass the activity result back to the Facebook SDK
//        mCallbackManager.onActivityResult(requestCode, resultCode, data);
//    }

//    private void handleFacebookAccessToken(AccessToken token) {
//        Log.d(TAG, "handleFacebookAccessToken:" + token);
//
//        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
//        firebaseAuth.signInWithCredential(credential)
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            Log.d(TAG, "signInWithCredential:success");
//                            FirebaseUser user = firebaseAuth.getCurrentUser();
//                            updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
//                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Toast.makeText(FacebookLoginActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            updateUI(null);
//                        }
//
//                        // ...
//                    }
//                });
//  }

    protected void signIn() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setIsSmartLockEnabled(false)
                        .setAvailableProviders(Arrays.asList(
                                new AuthUI.IdpConfig.GoogleBuilder().build(),
                                new AuthUI.IdpConfig.EmailBuilder().build(),
                                //new AuthUI.IdpConfig.FacebookBuilder().build(),
                                new AuthUI.IdpConfig.PhoneBuilder().build()))
                        .build(),
                RC_SIGN_IN);
    }

//    AuthUI.IdpConfig facebookIdp = new AuthUI.IdpConfig.FacebookBuilder()
//            .setPermissions(Arrays.asList("user_friends"))
//            .build();

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // RC_SIGN_IN is the request code you passed into startActivityForResult(...) when starting the sign in flow.
        if (i == 1) {
            if (requestCode == RC_SIGN_IN) {
                IdpResponse response = IdpResponse.fromResultIntent(data);

                // Successfully signed in
                if (resultCode == RESULT_OK) {
                    firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                    i = 0;
                    addListners();
                } else {
                    // Sign in failed
                    if (response == null) {
                        // User pressed back button
                        return;
                    }

                    if (response.getError().getErrorCode() == ErrorCodes.NO_NETWORK) {
                        return;
                    }

                }
            }
        } else if (i == 2) {
            if (requestCode == REQUEST_INVITE) {
                if (resultCode == RESULT_OK) {
                    // Get the invitation IDs of all sent messages
                    String[] ids = AppInviteInvitation.getInvitationIds(resultCode, data);
                    for (String id : ids) {
                        Log.d("TAG", "onActivityResult: sent invitation " + id);
                    }
                } else {
                    // Sending failed or it was canceled, show failure message to the user
                    // ...
                }
            }
            i=0;
        } else {
            if (requestCode == PICK_PDF_CODE && resultCode == RESULT_OK && data != null) {
                if (data.getData() != null)
                    uploadFile(data.getData());
                else
                    Toast.makeText(getBaseContext(), "No file choosen", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void addListners() {
        ArrayAdapter<CharSequence> branchAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.branch_array,
                android.R.layout.simple_spinner_item
        );

        branchAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        branchSpinner.setAdapter(branchAdapter);

        branchSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                branch = String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                branch = String.valueOf(parent.getItemAtPosition(0));
            }
        });

        final ArrayAdapter<CharSequence> semesterAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.semester_array,
                android.R.layout.simple_spinner_item
        );

        semesterAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        semesterSpinner.setAdapter(semesterAdapter);

        semesterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                semester = String.valueOf(parent.getItemAtPosition(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                semester = String.valueOf(parent.getItemAtPosition(0));
            }
        });

        storageReference = FirebaseStorage.getInstance().getReference();
        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);

        final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Network Status")
                .setMessage("Please connect your phone with internet")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .create();

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

                if (networkInfo != null && networkInfo.isConnected()) {
                    brse=branch+"-"+semester;
                    SharedPreferences prefs= PreferenceManager.getDefaultSharedPreferences(MainActivity.this);
                    SharedPreferences.Editor editor=prefs.edit();
                    editor.putString("string_id",brse);
                    editor.commit();
                    //Log.d("SharedPreference", "addListners: "+brse);
                    if(editText.getText().length()==0)
                        Toast.makeText(MainActivity.this, "Enter the name of file", Toast.LENGTH_SHORT).show();
                    else {
                        getPdf();
                    }
                } else {
                    alertDialog.show();
                }
            }
        });

        textViewUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, viewPager.class);
                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.items, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId()) {
            case R.id.viewUploads:
                Intent i = new Intent(MainActivity.this, viewPager.class);
                startActivity(i);
                break;
//            case R.id.syllabus:
//                Intent intent=new Intent(MainActivity.this,viewPager.class);
//                intent.putExtra("syllabus",1);
//                startActivity(intent);
//                break;
            case R.id.invite:
                onInviteClicked();
                break;
            case R.id.viewDownloads:
                Intent i1=new Intent(MainActivity.this,ViewDownloads.class);
                startActivity(i1);
                break;
            case R.id.signOut:
                AuthUI.getInstance()
                        .signOut(this);
                editText.getText().clear();
                signIn();
                break;
            case R.id.deleteAccount:
                AuthUI.getInstance()
                        .delete(this);
                editText.getText().clear();
                signIn();
                fileList();
                break;
        }
        return true;
    }

    private void onInviteClicked() {
        i = 2;
        Intent intent = new AppInviteInvitation.IntentBuilder(getString(R.string.invitation_title))
                .setMessage(getString(R.string.invitation_message))
                .build();
        startActivityForResult(intent, REQUEST_INVITE);
    }

    private void getPdf() {
        int perm = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //int perm1=ContextCompat.checkSelfPermission(MainActivity.this,Manifest.permission.READ_EXTERNAL_STORAGE);

        if (perm != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this,
                    new String[]{
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                    },
                    121
            );

            return;
        }

        i = 0;
        Intent intent = new Intent();
        intent.setType("application/pdf");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select picture"), PICK_PDF_CODE);
    }

    private void uploadFile(Uri data) {
        lottieAnimationView.setVisibility(View.VISIBLE);
        lottieAnimationView.playAnimation();
        //progressBar.setVisibility(View.VISIBLE);
        editText.setEnabled(false);
        btnUpload.setEnabled(false);
        final StorageReference sRef = storageReference.child(branch + "-" + semester + "/" + editText.getText().toString() + ".pdf");
        sRef.putFile(data)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        //progressBar.setVisibility(View.GONE);
                        lottieAnimationView.cancelAnimation();
                        lottieAnimationView.setVisibility(View.GONE);
                        textViewStatus.setText("File upload successfully");
                        editText.setEnabled(true);
                        btnUpload.setEnabled(true);

                        Log.d("user", "onSuccess: "+firebaseUser.getDisplayName());

                        Long sizeBytes = taskSnapshot.getMetadata().getSizeBytes();
                        Long sizeKb = sizeBytes / 1024;
                        Long sizeMb, sizeGb;
                        final String size;
                        if (sizeKb >= 1024) {
                            sizeMb = sizeKb / 1024;
                            size = String.valueOf(sizeMb + " MB");
                        } else if (sizeKb >= 1048576) {
                            sizeGb = sizeKb / 1048576;
                            size = String.valueOf(sizeGb + " GB");
                        } else {
                            size = String.valueOf(sizeKb + " KB");
                        }

                        final String fileName = editText.getText().toString();
                        final String firstLetter = String.valueOf(fileName.charAt(0));

                        final String timeInMillisecond = String.valueOf(taskSnapshot.getMetadata().getCreationTimeMillis());

                        id++;
                        Log.d("Id", "onSuccess: "+id);

                        sRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                Uri downloadUri = uri;
                                Log.d("Path", "onSuccess: "+downloadUri.getPath());
                                Upload upload = new Upload(editText.getText().toString(), downloadUri.toString(), size, firstLetter.toUpperCase(), timeInMillisecond,id);
                                databaseReference.child(branch + "-" + semester).push().setValue(upload);
                                editText.getText().clear();
                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getBaseContext(), "the file is not uploaded", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                        double progress = taskSnapshot.getBytesTransferred() * 100 / taskSnapshot.getTotalByteCount();
                        textViewStatus.setText((int) progress + "% Uploading");
                    }
                });
    }
}
