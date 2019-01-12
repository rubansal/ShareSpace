package com.codingblocks.msitnotes;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.ListIterator;

public class Comment extends AppCompatActivity {

    DatabaseReference databaseReference;

    ArrayList<String> comments = new ArrayList<>();
    ListView listViewComment;
    EditText editTextComment;
    ImageView imageViewComment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        databaseReference = FirebaseDatabase.getInstance().getReference(Constants.DATABASE_PATH_USERS);

        int value = getIntent().getIntExtra("comments", 0);
        final String id = String.valueOf(value);

        listViewComment = findViewById(R.id.listViewComment);
        editTextComment = findViewById(R.id.editTextComment);
        imageViewComment = findViewById(R.id.imageViewComment);

        final ArrayAdapter<String> commentAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1,
                android.R.id.text1,
                comments);
        listViewComment.setAdapter(commentAdapter);

        imageViewComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comment = editTextComment.getText().toString();

                if (comment.length() == 0) {
                    Toast.makeText(Comment.this, "Add a comment here", Toast.LENGTH_SHORT).show();
                } else {
                    databaseReference.child(id).push().setValue(comment);
                }

                editTextComment.getText().clear();
            }
        });

        databaseReference.child(id).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String data = dataSnapshot.getValue(String.class);
                comments.add(data);
                commentAdapter.notifyDataSetChanged();
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
    }
}
