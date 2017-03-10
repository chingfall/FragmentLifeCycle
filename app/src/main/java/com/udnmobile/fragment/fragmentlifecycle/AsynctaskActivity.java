package com.udnmobile.fragment.fragmentlifecycle;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class AsynctaskActivity extends AppCompatActivity {

    private final String TAG = "Asynctask";

    //
    private Button btn_asynctask1;
    private Button btn_asynctask2;
    private Button btn_asynctask3;
    private TextView info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);

        init();

        initHandler();
    }

    class JobTask1 extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            info.setText("DONE1");
        }

        @Override
        protected Void doInBackground(Void... params) {

            //
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    class JobTask2 extends AsyncTask<Integer, Void, Void> {
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);

            info.setText("DONE2");
        }

        @Override
        protected Void doInBackground(Integer... params) {

            //
            try {
                Thread.sleep(params[0]*1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

    class JobTask3 extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            info.setText(String.valueOf(values[0]));
        }

        @Override
        protected Void doInBackground(Integer... params) {

            for (int i = params[0]; i > 0; i--) {
                publishProgress(i);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            info.setText("DONE3");
        }
    }

    private void init() {

        //
        btn_asynctask1 = (Button) findViewById(R.id.btn_asynctask1);
        btn_asynctask2 = (Button) findViewById(R.id.btn_asynctask2);
        btn_asynctask3 = (Button) findViewById(R.id.btn_asynctask3);
        info = (TextView) findViewById(R.id.asynctaskTv);

        //
        btn_asynctask1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JobTask1().execute();
            }
        });

        btn_asynctask2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JobTask2().execute(3);
            }
        });

        btn_asynctask3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JobTask3().execute(6);
            }
        });

    }

    private void initHandler() {

    }
}
