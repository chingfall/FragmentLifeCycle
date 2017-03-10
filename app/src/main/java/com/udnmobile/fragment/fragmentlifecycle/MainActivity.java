package com.udnmobile.fragment.fragmentlifecycle;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //UI Widget
    private Button btn_recycleview;
    private Button btn_fragment;
    private Button btn_googlemap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //
        btn_fragment = (Button) findViewById(R.id.button_contactFragment);
        btn_recycleview = (Button) findViewById(R.id.button_recyclerView);
        btn_googlemap = (Button) findViewById(R.id.button_googleMap);

        //button click listener
        btn_fragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ContactActivity.class);

                Bundle bundle = new Bundle();
                bundle.putInt("btn_value", 0);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        btn_recycleview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, ContactActivity.class);

                Bundle bundle = new Bundle();
                bundle.putInt("btn_value", 1);
                intent.putExtras(bundle);

                startActivity(intent);
            }
        });

        btn_googlemap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }
}