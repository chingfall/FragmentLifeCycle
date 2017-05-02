package com.udnmobile.fragment.fragmentlifecycle;

import android.os.Bundle;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Asus on 2017/4/5.
 */
public class BottomSheetDiaFragment extends BottomSheetDialogFragment {
    private static final String TAG = "BottomSheetDiaFragment";

    private View view;

    //Database
    ItemData itemsData[] = {
            new ItemData("HELP", android.R.drawable.ic_menu_help),
            new ItemData("Delete", android.R.drawable.ic_menu_delete),
            new ItemData("Favorite", android.R.drawable.ic_menu_preferences)};

    private RecyclerView recyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.adapter_bottom_sheet, container, false);

        //recyclerView = (RecyclerView) getActivity().findViewById(R.id.recyclerView);

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
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
//      2. set layoutManger(get a context<背景>)
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//      3. create an adapter
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(itemsData);
//      4. set adapter
        recyclerView.setAdapter(adapter);
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
}
