package com.udnmobile.fragment.fragmentlifecycle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerActivity extends FragmentActivity {

    private static final String TAG = "ViewpagerActivity";

    //variable declaration
    private ViewPager mViewPager;

    private PagerAdapter mPagerAdapter;
    private TabLayout mTabLayout;

    //array to store pictures
    private List<Fragment> getFragment(){
        List<Fragment> fragments = new ArrayList<Fragment>();
        fragments.add(ViewPagerFragment.newInstance(1, android.R.drawable.ic_menu_camera));
        fragments.add(ViewPagerFragment.newInstance(2, android.R.drawable.ic_menu_camera));
        fragments.add(ViewPagerFragment.newInstance(3, android.R.drawable.ic_menu_camera));
        fragments.add(ViewPagerFragment.newInstance(4, android.R.drawable.ic_menu_camera));
        fragments.add(ViewPagerFragment.newInstance(5, android.R.drawable.ic_menu_camera));

        return fragments;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);

        init();

        initHandler();
    }

    private void init() {

        // Instantiate a ViewPager and a PagerAdapter.
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tablayout);
        List<Fragment> fragments = getFragment();

        mTabLayout.setupWithViewPager(mViewPager, true);
        mPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments);
        mViewPager.setAdapter(mPagerAdapter);
        mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener());
    }

    private void initHandler() {

    }

//    viewPager Adapter inner class
    private static class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> fragments;

        public ViewPagerAdapter(FragmentManager fm, List<Fragment> fragments){
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int position){
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }
}
