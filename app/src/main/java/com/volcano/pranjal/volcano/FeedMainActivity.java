package com.volcano.pranjal.volcano;

import android.content.Context;
import android.content.Intent;
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

import com.mindorks.placeholderview.ExpandablePlaceHolderView;
import com.mindorks.placeholderview.PlaceHolderView;

public class FeedMainActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ExpandablePlaceHolderView mExpandableView;
    private Context mContext;
    private ExpandablePlaceHolderView.OnScrollListener mOnScrollListener;
    private boolean mIsLoadingMore = false;
    private boolean mNoMoreToLoad = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_main);
        mTextMessage = findViewById(R.id.message);
        BottomNavigationView bottomNavigationView = findViewById(R.id.navigation);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        Menu menu = bottomNavigationView.getMenu();
        MenuItem menuItem = menu.getItem(1);
        menuItem.setChecked(true);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.navigation_home:
                        mTextMessage.setText(R.string.title_home);
                        Intent intent0 = new Intent(FeedMainActivity.this, MainActivity.class);
                        startActivity(intent0);
                        break;
                    case R.id.navigation_dashboard:
                        break;

                    case R.id.navigation_notifications:
                        mTextMessage.setText(R.string.title_notifications);
                        break;

                    case R.id.navigation_profile:
                        mTextMessage.setText(R.string.title_profile);
                        Intent myIntent = new Intent(FeedMainActivity.this, ProfileActivity.class);
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
                mExpandableView.addView(new InfoView(mContext, info));
            }
        }
        System.out.println("hey you");
    }
}
