package com.volcano.pranjal.volcano;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mindorks.placeholderview.ExpandablePlaceHolderView;
import com.mindorks.placeholderview.PlaceHolderView;

public class SaveLaterActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ExpandablePlaceHolderView mExpandableView;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_main);
        mTextMessage = findViewById(R.id.message);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(2);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        mTextMessage.setText(R.string.title_home);
                        Intent intent0 = new Intent(SaveLaterActivity.this, MainActivity.class);
                        startActivity(intent0);
                        break;
                    case R.id.navigation_dashboard:
                        Intent intent1 = new Intent(SaveLaterActivity.this, FeedMainActivity.class);
                        startActivity(intent1);
                        break;

                    case R.id.navigation_notifications:
                        break;

                    case R.id.navigation_profile:
                        mTextMessage.setText(R.string.title_profile);
                        Intent myIntent = new Intent(SaveLaterActivity.this, ProfileActivity.class);
                        startActivity(myIntent);
                        overridePendingTransition(0, 0);
                        break;
                }
                return true;
            }
        });

        mContext = this.getApplicationContext();
        mExpandableView = findViewById(R.id.expandableView);

        for (Feed feed : Utils.loadFeeds(this.getApplicationContext())) {
            mExpandableView.addView(new HeadingView(mContext, feed.getHeading()));
            for (Info info : feed.getInfoList()) {
                if(info.getNewsid().equals(fetchNewsId()))
                    mExpandableView.addView(new SaveLaterInfoView(mContext, info));
                }
        }
    }
    public String fetchNewsId(){
        FeedReaderDbHelper mDbHelper = new FeedReaderDbHelper(this.mContext);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        Cursor c = db.rawQuery("SELECT * FROM " + Constants.USER_SAVE_LATER_TABLE_NAME + " WHERE email='"
                + user.getEmail() + "'", null);
        if(c.moveToFirst())
            return c.getString(2);
        else
            return null;
    }
}
