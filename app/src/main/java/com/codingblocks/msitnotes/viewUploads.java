package com.codingblocks.msitnotes;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.gms.appinvite.AppInviteInvitation;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class viewUploads extends AppCompatActivity {
    RecyclerView rvUploads;
    ArrayList<Upload> uploads = new ArrayList<>();

    DatabaseReference databaseReference;

    //LottieAnimationView lottieNetworkAnimation;

    int i=0;

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_upload_recycler);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null)
            getSupportActionBar().setTitle("Share Space");

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String brse = prefs.getString("string_id", "no_id");
        //Log.d("SharedPreference", "onCreate: "+brse);

        String brase = getIntent().getStringExtra("brase");
        if (brase != null)
            brse = brase;

        //int s = getIntent().getIntExtra("sl", 0);
//        Log.d("Syllabus", "onCreate: "+s);
//        Log.d("Syllbrase", "onCreate: "+brase);

        rvUploads = findViewById(R.id.rvUploads);
        TextView textViewInternet = findViewById(R.id.textViewInternet);
        //lottieNetworkAnimation=findViewById(R.id.lottieNetworkAnimation);
        View progressBar = findViewById(R.id.progressBar);
        //ListView listViewSubject=findViewById(R.id.listViewSubject);
        //final ImageView imageViewSubject=findViewById(R.id.imageViewSubject);

        String[] subjects=new String[5];

        final AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle("Upload Status")
                .setMessage("There is no video yet uploaded here")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .create();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        //if (s == 0) {
            if (networkInfo != null && networkInfo.isConnected()) {
                rvUploads.setLayoutManager(new LinearLayoutManager(this));
                rvUploads.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                final ViewUploadsAdapter viewUploadsAdapter = new ViewUploadsAdapter(uploads, rvUploads, getBaseContext(),brse);
                rvUploads.setAdapter(viewUploadsAdapter);
                progressBar.setVisibility(View.GONE);
//                Log.d("Uploadsize", "onCreate: "+viewUploadsAdapter.getItemCount());

                databaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_UPLOADS);
                databaseReference.child(brse).addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Upload data = dataSnapshot.getValue(Upload.class);
                        uploads.add(data);
                        viewUploadsAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            } else {
                progressBar.setVisibility(View.GONE);
                //lottieNetworkAnimation.setVisibility(View.VISIBLE);
                //startCheckAnimation();
                textViewInternet.setText("No Internet Connection");
            }
//        } else {
//            progressBar.setVisibility(View.GONE);
//            switch (brase){
//                case "CSE-sem1":
//                    subjects=new String[]{"a","b","c","d"};
//                case "CSE-sem2":
//                    subjects=new String[]{"a","b","c","d"};
//                case "CSE-sem3":
//                    subjects=new String[]{"a","b","c","d"};
//                case "CSE-sem4":
//                    subjects=new String[]{"a","b","c","d"};
//                case "CSE-sem5":
//                    subjects=new String[]{"a","b","c","d"};
////                    listViewSubject.setOnItemClickListener(new AdapterView.OnItemClickListener() {
////                        @Override
////                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                            if(position==0)
////                                imageViewSubject.setImageResource(R.drawable.no_internet);
////                        }
////                    });
//                case "CSE-sem6":
//                    subjects=new String[]{"a","b","c","d"};
//                case "CSE-sem7":
//                     subjects=new String[]{"a","b","c","d"};
//                case "CSE-sem8":
//                    //subjects=new String[]{"a","b","c","d"};
//            }
////            ArrayAdapter<String> subjectAdapter=new ArrayAdapter<>(this,
////                    android.R.layout.simple_list_item_1,
////                    android.R.id.text1,
////                    subjects);
//            //listViewSubject.setAdapter(subjectAdapter);
//        }
    }

//    private void startCheckAnimation() {
//        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f).setDuration(500);
//        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//            @Override
//            public void onAnimationUpdate(ValueAnimator valueAnimator) {
//                lottieNetworkAnimation.setProgress((Float) valueAnimator.getAnimatedValue());
//            }
//        });
//
//        if (lottieNetworkAnimation.getProgress() == 0f) {
//            animator.start();
//        } else {
//            lottieNetworkAnimation.setProgress(0f);
//        }
//    }
}
