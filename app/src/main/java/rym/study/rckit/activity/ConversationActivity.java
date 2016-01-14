package rym.study.rckit.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import rym.study.rckit.R;

public class ConversationActivity extends AppCompatActivity {

    private static final String TAG = "ConversationActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        initView();
        checkPushMsg();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.LTGRAY);
        toolbar.setSubtitle("TargetID: " + getIntent().getData().getQueryParameter("targetId"));
        setSupportActionBar(toolbar);
    }

    private void checkPushMsg() {
        Log.d(TAG, "getIntent() = " + getIntent().toString());
        if (getIntent() == null || getIntent().getData() == null) {
            return;
        }
    }
}
