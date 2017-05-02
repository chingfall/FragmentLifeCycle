package com.udnmobile.fragment.fragmentlifecycle;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by Asus on 2017/4/11.
 */
public class BottomDialogActivity extends AppCompatActivity {

    private Button btn_dialogFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_botttom_dialog);

        init();

        initHandler();
    }

    private void init(){

        btn_dialogFragment = (Button) findViewById(R.id.btn_dialogfragment);
    }

    private void initHandler(){

        btn_dialogFragment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BottomDialogFragment editNameDialog = new BottomDialogFragment();
                editNameDialog.show(getFragmentManager(), "fragment_bottom_dialog");
            }
        });
    }

}
