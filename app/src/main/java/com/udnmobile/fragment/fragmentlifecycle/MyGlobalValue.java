package com.udnmobile.fragment.fragmentlifecycle;

import android.app.Application;
import android.support.v4.app.FragmentManager;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Asus on 2017/3/8.
 */
public class MyGlobalValue extends Application{

    private FragmentManager mainFragmentManager;

    //data

    private ArrayList<HashMap<String, String>> loadData = new ArrayList<>();

    public void setLoadData(ArrayList<HashMap<String, String>> loadData) {
        this.loadData = loadData;
    }

    public ArrayList<HashMap<String, String>> getLoadData() {
        return loadData;
    }

    public FragmentManager getMainFragmentManager() { return mainFragmentManager; }

    public void SetMainFragmentManager(FragmentManager mainFragmentManager) {
        this.mainFragmentManager = mainFragmentManager;
    }

}
