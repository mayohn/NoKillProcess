package com.example.mayohn.nokillprocess;


import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;

import java.io.DataOutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class Watcher {
    private static final String TAG = "Watcher";
    private static final String PACKAGE = "com.example.dameonservice/";
    private String mMonitoredService = "";
    private volatile boolean bHeartBreak = false;
    private Context mContext;
    private boolean mRunning = true;

    public void createAppMonitor(String userId) {
        if (!createWatcher(userId)) {
            Log.e("Watcher", "<<Monitor created failed>>");
        }
    }

    public Watcher(Context context) {
        mContext = context;
        
    }

    public static void doCmds(List<String> cmds) throws Exception {
        Process process = Runtime.getRuntime().exec("su");
        DataOutputStream os = new DataOutputStream(process.getOutputStream());
        for (String tmpCmd : cmds) {
            os.writeBytes(tmpCmd+"\n");
        }
        os.writeBytes("exit\n");
        os.flush();
        os.close();
        process.waitFor();
    }

    private int isServiceRunning() {
        ActivityManager am = (ActivityManager) mContext.getSystemService(Context.ACTIVITY_SERVICE);
        ArrayList<ActivityManager.RunningServiceInfo> runningService = (ArrayList<ActivityManager.RunningServiceInfo>) am.getRunningServices(1024);
        for (int i = 0; i < runningService.size(); ++i) {
            if (mMonitoredService.equals(runningService.get(i).service.getClassName().toString())) {
                return 1;
            }
        }
        Log.i(TAG, "isServiceRunning:");
        return 0;
    }

    public native boolean createWatcher(String userId);


    public native boolean connectToMonitor();


    public native int sendMsgToMonitor(String msg);

    static {
        System.loadLibrary("restart");
    }

}

