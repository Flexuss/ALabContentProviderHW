package com.example.dmitry.alabcontentproviderhw.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by Dmitry on 28.05.2017.
 */

public class MainReceiver extends BroadcastReceiver {

    public static final String STATUS_OK = "success";
    public static final String STATUS_FAIL = "failure";

    public static final String STATUS_KEY = "status";

    private MainReceiverListenet.MainReceiverListener mainReceiverListener;

    public void setMainReceiverListener(MainReceiverListenet.MainReceiverListener mainReceiverListener) {
        this.mainReceiverListener = mainReceiverListener;
    }

    public MainReceiver(MainReceiverListenet.MainReceiverListener mainReceiverListener) {
        this.mainReceiverListener = mainReceiverListener;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        mainReceiverListener.onDataReceive(intent.getStringExtra(STATUS_KEY));
    }
}
