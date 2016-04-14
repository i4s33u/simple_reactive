package com.example.quan_bui.simplereactive;

import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;

/**
 * Created by Quan Bui on 4/14/16.
 */
public class MyUIThread
    implements PostExecutionThread {

    @Override
    public Scheduler getScheduler() {
        return AndroidSchedulers.mainThread();
    }

    public MyUIThread get() {
        return new MyUIThread();
    }
}