package com.volcano.pranjal.volcano;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.mindorks.placeholderview.annotations.Click;
import com.mindorks.placeholderview.annotations.Layout;
import com.mindorks.placeholderview.annotations.Resolve;
import com.mindorks.placeholderview.annotations.View;
import com.mindorks.placeholderview.annotations.expand.ChildPosition;
import com.mindorks.placeholderview.annotations.expand.ParentPosition;

/**
 * Created by janisharali on 24/08/16.
 */
@Layout(R.layout.feed_item)
public class InfoView {

    @ParentPosition
    private int mParentPosition;

    @ChildPosition
    private int mChildPosition;

    @View(R.id.titleTxt)
    private TextView titleTxt;

    @View(R.id.captionTxt)
    private TextView captionTxt;

    @View(R.id.timeTxt)
    private TextView timeTxt;

    @View(R.id.imageView)
    private ImageView imageView;

    private Info mInfo;
    private Context mContext;
    private FeedReaderDbHelper mDbHelper;
    FirebaseAuth mAuth;
    FirebaseUser user;

    public InfoView(Context context, Info info) {
        this.mContext = context;
        this.mInfo = info;
        mDbHelper = new FeedReaderDbHelper(context);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
    }

    @Resolve
    private void onResolved() {
        titleTxt.setText(mInfo.getTitle());
        captionTxt.setText(mInfo.getCaption());
        timeTxt.setText(mInfo.getTime());
        Glide.with(mContext).load(mInfo.getImageUrl()).into(imageView);
    }

    @Click(R.id.shareButton)
    private void onClickListener (){
        String share_url = "https://www.nytimes.com/2016/12/15/arts/television/whats-on-tv-thursday-nashville-on-cmt-and-jingle-ball-2016.html";
        Intent sharingIntent = new Intent();
        sharingIntent.setAction(Intent.ACTION_SEND);
        sharingIntent.setType("text/html");
        //sharingIntent.putExtra(Intent.EXTRA_SUBJECT, "Title Of The Post");
        sharingIntent.putExtra(Intent.EXTRA_TEXT, share_url);
        mContext.startActivity(Intent.createChooser(sharingIntent, "Stirrer"));

    }

    @Click(R.id.saveButton)
    private void onSaveClickListener (){
        String share_url = "https://www.nytimes.com/2016/12/15/arts/television/whats-on-tv-thursday-nashville-on-cmt-and-jingle-ball-2016.html";
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + Constants.USER_SAVE_LATER_TABLE_NAME + " WHERE email='"
                        + this.user.getEmail() + "'", null);
        if (c.moveToFirst()) {
            System.out.println("Debug: News Id-" + mInfo.getNewsid());
        } else {
            ContentValues values = new ContentValues();
            values.put(FeedReaderContract.FeedEntry.COLUMN_EMAIL, this.user.getEmail());
            values.put(FeedReaderContract.FeedEntry.COLUMN_NEWS_ID, mInfo.getNewsid());
            System.out.println("Error: User Entered");
            long newRowId = db.insert(
                    FeedReaderContract.FeedEntry.TABLE_NAME,
                    FeedReaderContract.FeedEntry.COLUMN_NAME_NULLABLE,
                    values);
        }
    }

}