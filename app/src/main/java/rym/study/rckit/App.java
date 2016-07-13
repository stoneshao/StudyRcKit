package rym.study.rckit;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.util.Log;

import io.rong.imkit.RongIM;
import io.rong.push.RongPushClient;
import rym.study.rckit.message.CustomizeMessage;
import rym.study.rckit.message.CustomizeMessageItemProvider;

public class App extends Application {
    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate getCurProcessName = " + getCurProcessName(this));
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {
            RongPushClient.registerMiPush(this, "2882303761517489607", "5831748913607");
            RongIM.init(this);
            RongIM.registerMessageType(CustomizeMessage.class);
            RongIM.getInstance().registerMessageTemplate(new CustomizeMessageItemProvider());
        }
    }

    private static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
