package com.udnmobile.fragment.fragmentlifecycle;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class RecyclerViewFragment extends Fragment {

    private final String TAG = "RecyclerViewFragment";

    private View view;

    //Database
    ItemData itemsData[] = {
            new ItemData("HELP", android.R.drawable.ic_menu_help),
            new ItemData("Delete", android.R.drawable.ic_menu_delete),
            new ItemData("Favorite", android.R.drawable.ic_menu_preferences),
            new ItemData("Like", android.R.drawable.ic_menu_call),
            new ItemData("Rating", android.R.drawable.ic_menu_camera),
            new ItemData("HELP", android.R.drawable.ic_menu_help),
            new ItemData("Delete", android.R.drawable.ic_menu_delete),
            new ItemData("Favorite", android.R.drawable.ic_menu_preferences),
            new ItemData("Like", android.R.drawable.ic_menu_call),
            new ItemData("Rating", android.R.drawable.ic_menu_camera)};

    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_recycler_view, container, false);

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerView);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        init();

        initHandler();
    }

    private void init() {

//      1. get a reference to recyclerView
        recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerView);
//      2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//      3. create an adapter
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(itemsData);
//      4. set adapter
        recyclerView.setAdapter(adapter);
        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());

    }

    private void initHandler() {

    }

    //newinstance() 確保class已經加載並已連接正確後，接收參數
    public static RecyclerViewFragment newInstance() {

//        Bundle args = new Bundle();
        RecyclerViewFragment fragment = new RecyclerViewFragment();
//        fragment.setArguments(args);
        return fragment;
    }

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 1. get a reference to recyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //Database
        ItemData itemsData[] = {
                new ItemData("HELP", android.R.drawable.ic_menu_help),
                new ItemData("Delete", android.R.drawable.ic_menu_delete),
                new ItemData("Favorite", android.R.drawable.ic_menu_preferences),
                new ItemData("Like", android.R.drawable.ic_menu_call),
                new ItemData("Rating", android.R.drawable.ic_menu_camera),
                new ItemData("HELP", android.R.drawable.ic_menu_help),
                new ItemData("Delete", android.R.drawable.ic_menu_delete),
                new ItemData("Favorite", android.R.drawable.ic_menu_preferences),
                new ItemData("Like", android.R.drawable.ic_menu_call),
                new ItemData("Rating", android.R.drawable.ic_menu_camera)};

        // 2. set layoutManger
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        // 3. create an adapter
        RecyclerViewAdapter mAdapter = new RecyclerViewAdapter(itemsData);
        // 4. set adapter
        recyclerView.setAdapter(mAdapter);
        // 5. set item animator to DefaultAnimator
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }*/
}
