package com.udnmobile.fragment.fragmentlifecycle;

import android.app.Application;
import android.support.v4.app.FragmentManager;

/**
 * Created by Asus on 2017/3/8.
 */
public class MyGlobalValue extends Application{

    private FragmentManager mainFragmentManager;

    public FragmentManager getMainFragmentManager() { return mainFragmentManager; }

    public void SetMainFragmentManager(FragmentManager mainFragmentManager) {
        this.mainFragmentManager = mainFragmentManager;
    }
}
