package com.aldoapps.popularmovies;

import android.app.Application;
import android.os.StrictMode;

/**
 * Created by aldokelvianto on 3/14/16.
 */
public class PMApplication extends Application {
    public static final String TAG = PMApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();


//        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
//                .detectDiskReads()
//                .detectDiskWrites()
//                .detectNetwork()   // or .detectAll() for all detectable problems
//                .detectAll()
//                .penaltyLog()
//                .build());
//        StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
//                .detectLeakedSqlLiteObjects()
//                .detectLeakedClosableObjects()
//                .penaltyLog()
//                .detectAll()
//                .penaltyDeath()
//                .build());
    }
}
