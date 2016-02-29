package com.aldoapps.popularmovies;

import android.support.v4.app.Fragment;
import android.util.Log;

public class MainActivity extends SingleFragmentActivity {

    private boolean mTwoPane;

    @Override
    protected Fragment createFragment() {
        return new MainFragment();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onPause() {
        super.onPause();

        Log.d("asdf", "Main Activity on Pause called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.d("asdf", "Main Activity on Destroy called");
    }

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        if(findViewById(R.id.movie_detail_container) != null){
//            mTwoPane = true;
//        }
//    }

}
