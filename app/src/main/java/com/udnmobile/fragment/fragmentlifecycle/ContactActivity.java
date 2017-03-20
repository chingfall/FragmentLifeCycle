package com.udnmobile.fragment.fragmentlifecycle;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ContactActivity extends AppCompatActivity {

    //測試用TAG
    private final String TAG = "MainActivity";

    //這一頁需要的全域變數
    private MyGlobalValue myGlobalValue;

    private int btn_value;

    //
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);

        init();

        if(btn_value == 0){//bundle傳直,看按哪一個按鈕

            initTestFragment();
        }else if(btn_value == 1){

            initExampleFragment();
        }


        initHandler();
    }

//  實作區(第一頁fragment layout)
    private void initExampleFragment() {

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.main_fragment, RecyclerViewFragment.newInstance(), RecyclerViewFragment.class.getName());
        fragmentTransaction.commit();
//        fragmentTransaction.replace(R.id.main_fragment, id);
    }

//  實作區(第二頁fragment layout)
    private void initTestFragment() {

        //動態改變transaction需要一個過程,呼叫fragmentManager
        //fragmentManager = getSupportFragmentManager();

        //呼叫fragmentmanager的begintransaction() method建立一個物件
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        //用另外一個Fragment取代目前程式畫面中的Fragment，它的功能等同於先呼叫remove()移除目前的Fragment，再呼叫add()加入另一個Fragment。
        fragmentTransaction.replace(R.id.main_fragment, DetailFragment.newInstance(), DetailFragment.class.getName());

        //commit()，送出以上建立的Fragment處理流程，系統會根據這個處理流程來切換Fragment
        fragmentTransaction.commit();

        //如果程式需要立刻執行Fragment的切換，可以呼叫FragmentManager的executePendingTransactions()
//        fragmentManager.executePendingTransactions();
    }

    //  初始化變數
    private void init() {

        //getApplication()用來共享全局變數,之後Activity和Service就能取得
        //透過 Activity.getApplication() 或 Service.getApplication() 取得
        myGlobalValue = (MyGlobalValue) getApplication();

        fragmentManager = getSupportFragmentManager();
        myGlobalValue.SetMainFragmentManager(fragmentManager);

        //btn_bundle
        Bundle bundle = getIntent().getExtras();
        btn_value =bundle.getInt("btn_value");

    }

    private void initHandler() {

    }
}
