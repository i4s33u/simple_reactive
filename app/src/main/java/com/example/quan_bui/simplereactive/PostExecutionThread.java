package com.example.quan_bui.simplereactive;

import rx.Scheduler;

/**
 * Created by Quan Bui on 4/14/16.
 */
public interface PostExecutionThread {
    Scheduler getScheduler();
}
