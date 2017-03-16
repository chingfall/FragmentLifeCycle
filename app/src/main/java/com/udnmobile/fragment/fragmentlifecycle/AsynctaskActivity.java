package com.udnmobile.fragment.fragmentlifecycle;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;

public class AsynctaskActivity extends AppCompatActivity {

    private final String TAG = "Asynctask";

    //
    private Button btn_asynctask1;
    private Button btn_asynctask2;
    private Button btn_asynctask3;
    private TextView info;

    private MyGlobalValue myGlobalValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asynctask);

        init();

        initHandler();
    }

    class JobTask1 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

//          HttpURLConnection & BufferedReader need to declared outside the try/catch.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

//          Will contain the raw JSON response as a String.
            String jsonstr = null;

            //
            try {
//                Thread.sleep(5);
                //Construct the URL for the backend query.
                URL url = new URL("https://video.udn.com/api-cowork/com-app");

                //Create the request to backend, and open the connection.
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                //Read the input Stream into a string.
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    return null;
                }
                jsonstr = buffer.toString();
            } catch (MalformedURLException e) {
                Log.d(TAG, "doInBackground: ", e);
            } catch (IOException i) {
                Log.d(TAG, "doInBackground: ", i);
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return jsonstr;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            info.setText(s);
//            info.setText("DONE1");
            Log.i(TAG, "onPostExecute: ");
        }
    }

    class JobTask2 extends AsyncTask<String, Void, String> {

        //
        HttpURLConnection urlConnection = null;

        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;

            try {
                URL url = new URL("http://drupal.udn-device-dept.net/Exhibition/api/getSpecificData.php");
                urlConnection = (HttpURLConnection) url.openConnection();
                //setRequestMethod:set the method for the URL request.
                urlConnection.setRequestMethod("POST");

                //Read the response.
                //The response body may be read from the stream returned by getInputStream().
                urlConnection.setDoInput(true);
                //Instances must be configured with setDoOutput(true) if they include a request body.
                urlConnection.setDoOutput(true);

                urlConnection.setUseCaches(false);
//                urlConnection.setChunkedStreamingMode(0);
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.connect();

                //Create JSONObject here
                JSONObject usuario = new JSONObject();
                usuario.put("app_uid", "automatic");
                usuario.put("get_enroll", "yes");

                // Send POST output.
                OutputStreamWriter out = new OutputStreamWriter(urlConnection.getOutputStream());
                out.write(usuario.toString());
                out.flush();
                out.close();

                //check urlconnection with response code 200(OK).
                int status = urlConnection.getResponseCode();
                if (status == HttpURLConnection.HTTP_OK) {
                    //post request variables.
                    InputStream is = urlConnection.getInputStream();
                    StringBuilder response = new StringBuilder();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    String line;

                    while ((line = reader.readLine()) != null) {
                        response.append(line + "\r");
                    }
                    reader.close();
                    return response.toString();
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException j) {
                Log.d(TAG, "doInBackground: json");
            } finally {
                //cut the connection.
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            parseJSON(s);

            //for(int i = 0; i < myGlobalValue.getLoadData(); i++){
            for (HashMap<String, String> a : myGlobalValue.getLoadData()) {
                info.setText(a.toString());
            }

        }
    }

    class JobTask3 extends AsyncTask<String, Void, String> {

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
//            info.setText(String.valueOf(values[0]));
        }

        @Override
        protected String doInBackground(String... params) {

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            info.setText(s);
        }
    }

    //data store
    private void parseJSON(String result){
        ArrayList<HashMap<String, String>> loadData = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(result);

            for(int i = 0; i < jsonArray.length(); i++){

                HashMap<String, String> loadDataMap = new HashMap<>();

                JSONObject jsonObject = jsonArray.getJSONObject(i);

                loadDataMap.put("exh_uid", jsonObject.getString("exh_uid"));
                loadDataMap.put("app_uid", jsonObject.getString("app_uid"));

                loadData.add(loadDataMap);
            }

            myGlobalValue.setLoadData(loadData);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private void init() {

        myGlobalValue = (MyGlobalValue) getApplication();

        //ui button
        btn_asynctask1 = (Button) findViewById(R.id.btn_asynctask1);
        btn_asynctask2 = (Button) findViewById(R.id.btn_asynctask2);
        btn_asynctask3 = (Button) findViewById(R.id.btn_asynctask3);
        info = (TextView) findViewById(R.id.UrlTv);

        //btn setonclicklistener
        btn_asynctask1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JobTask1().execute();
            }
        });

        btn_asynctask2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JobTask2().execute();
            }
        });

        btn_asynctask3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new JobTask3().execute();
            }
        });

    }

    private void initHandler() {

    }
}
