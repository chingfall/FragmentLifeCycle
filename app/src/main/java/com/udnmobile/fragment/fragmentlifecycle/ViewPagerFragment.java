package com.udnmobile.fragment.fragmentlifecycle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class ViewPagerFragment extends Fragment {

    private static final String TAG = "ViewPagerFragment";

    private static final String ARG_PAGE = "PAGE";
    private static final String ARG_IMAGE = "IMAGE";

    private int mPageNumber;
    private int resImageId = 0;

    public static ViewPagerFragment newInstance(int pageNumber, int resImageId) {
        ViewPagerFragment fragment = new ViewPagerFragment();
        Bundle args = new Bundle(1);
        args.putInt(ARG_PAGE, pageNumber);
        args.putInt(ARG_IMAGE, resImageId);
        fragment.setArguments(args);
        return fragment;
    }

    public ViewPagerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init();

        initHandler();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_view_pager, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvLabel = (TextView) view.findViewById(R.id.text1);
        ImageView img = (ImageView) view.findViewById(R.id.img1);

        tvLabel.setText("Page" + mPageNumber);
        img.setImageResource(resImageId);
    }

    public int getPageNumber() {
        return mPageNumber;
    }

    private void init() {
        mPageNumber = getArguments().getInt(ARG_PAGE);
        resImageId = getArguments().getInt(ARG_IMAGE);
    }

    private void initHandler() {
    }
}
