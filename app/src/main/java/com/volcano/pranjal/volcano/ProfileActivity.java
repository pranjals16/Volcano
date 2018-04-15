package com.volcano.pranjal.volcano;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_drawer);
        mAuth = FirebaseAuth.getInstance();
        findViewById(R.id.logoutbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
            }
        });
    }
    private void signOut() {
        mAuth.signOut();
        Intent myIntent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(myIntent);
        finish();
    }
    public void onBackPressed() {
        super.onBackPressed();
        //finish();
    }
}