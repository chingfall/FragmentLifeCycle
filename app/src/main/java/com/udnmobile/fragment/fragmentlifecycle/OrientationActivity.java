package com.udnmobile.fragment.fragmentlifecycle;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Asus on 2017/4/25.
 */
public class OrientationActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orientation);

        Log.v("orientationActivity", "orientationActivity");

        //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        Log.v("orientationActivity", "sensor");

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        Log.v("orientation", "onConfigurationChanged called.");

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            Log.v("orientation", "橫屏");
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
            //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            //newConfig.orientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT;
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            Log.v("orientation", "豎屏");
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
            //this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            //newConfig.orientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
        }
    }
}
