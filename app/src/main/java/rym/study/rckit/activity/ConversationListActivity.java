package rym.study.rckit.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.RongIM;
import io.rong.imkit.fragment.ConversationListFragment;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.UserInfo;
import rym.study.rckit.R;
import rym.study.rckit.message.CustomizeMessage;
import rym.study.rckit.utils.MathUtil;

public class ConversationListActivity extends AppCompatActivity {

    private static final String TAG = "ConListActivity";

    public static String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation_list);

        initView();
        initSettings();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        RongIM.getInstance().disconnect();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_conversation_list, menu);
        return true;
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.LTGRAY);
        toolbar.setSubtitle("UserID: " + getIntent().getStringExtra("UserID"));
        setSupportActionBar(toolbar);

        ConversationListFragment fragment = (ConversationListFragment) getSupportFragmentManager().findFragmentById(R.id.conversationlist_fragment);
        Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon()
                .appendPath("conversationlist")
                .appendQueryParameter(Conversation.ConversationType.PRIVATE.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.GROUP.getName(), "true")
                .appendQueryParameter(Conversation.ConversationType.DISCUSSION.getName(), "false")
                .appendQueryParameter(Conversation.ConversationType.SYSTEM.getName(), "false")
                .build();
        fragment.setUri(uri);
    }

    private void initSettings() {
        RongIM.setUserInfoProvider(new RongIM.UserInfoProvider() {
            @Override
            public UserInfo getUserInfo(String userId) {
                Log.d(TAG, "getUserInfo id = " + userId);
                int hash = MathUtil.getHashInt(userId, 108);
                if (++hash >= 38)
                    hash++;
                String img = String.format("http://image4.360doc.com/DownloadImg/2009/10/10/349878_7044878_%d.jpg", hash);
                return new UserInfo(userId, userId + "_nc", Uri.parse(img));
            }
        }, true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_chat:
                LayoutInflater inflater = getLayoutInflater();
                final View view = inflater.inflate(R.layout.dialog_open_chat, null);
                new AlertDialog.Builder(this).setTitle("Private chat").setView(view)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                String targetId = ((EditText) view.findViewById(R.id.edit_chat_to)).getText().toString();
                                Log.d(TAG, "chat to = " + targetId);
                                RongIM.getInstance().startPrivateChat(ConversationListActivity.this, targetId, null);
                            }
                        }).setNegativeButton("Cancel", null).show();
                break;
            case R.id.menu_test_case1:
                break;
            case R.id.menu_test_case2:
                break;
            default:
        }
        return super.onOptionsItemSelected(item);
    }
}
