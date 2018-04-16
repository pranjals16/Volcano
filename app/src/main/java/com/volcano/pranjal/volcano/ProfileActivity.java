package com.volcano.pranjal.volcano;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class ProfileActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    private TextView mTextMessage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_drawer);
        mTextMessage = findViewById(R.id.message);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(3);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        mTextMessage.setText(R.string.title_home);
                        Intent intent0 = new Intent(ProfileActivity.this, MainActivity.class);
                        startActivity(intent0);
                        break;
                    case R.id.navigation_dashboard:
                        mTextMessage.setText(R.string.title_friends);
                        break;

                    case R.id.navigation_notifications:
                        mTextMessage.setText(R.string.title_notifications);
                        break;

                    case R.id.navigation_profile:
                        break;
                }
                return true;
            }
        });
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