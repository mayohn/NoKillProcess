package com.example.mayohn.nokillprocess;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Watcher watcher;
    private List<String> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        watcher = new Watcher(this);
        list.add("am start -n com.android.calendar/com.android.calendar.LaunchActivity");
        new Thread(new Runnable() {
            @Override
            public void run() {
                watcher.createWatcher("tsttt");
            }
        }).start();

//        try {
//            Watcher.doCmds(list);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
