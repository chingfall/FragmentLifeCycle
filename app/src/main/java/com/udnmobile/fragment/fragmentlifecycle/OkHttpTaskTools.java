package com.udnmobile.fragment.fragmentlifecycle;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Asus on 2017/3/22.
 */
public class OkHttpTaskTools extends AsyncTask<String, Integer, String>{

    private final String TAG = "OkHttpTaskTools";

    public final static int GET_TYPE = 0;
    public final static int POST_JSON_TYPE = 1;
    public final static int POST_KEY_VALUE_TYPE = 2;
    public final static int POST_FORM_TYPE = 3;

    private int clientType;
    private String putJson;
    private OnKeyValueHelperListener onKeyValueHelperListener;

    private OnPostExecuteListener onPostExecuteListener;

    private OnImageAndStringKVListener onImageAndStringKVListener;

    private OnCookieSettingListener onCookieSettingListener;

    private OkHttpClient client;


    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");


    private static final String IMGUR_CLIENT_ID = "...";
    private static final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

    private Context mContext;

    //讀取中處理
    private ProgressDialog progressDialog;
    private boolean isHasProgress = false;  //是否有讀取中處理

    /**
     * GET_TYPE 呼叫此方法
     */
    public OkHttpTaskTools(Context mContext) {

        this.mContext = mContext;
        this.clientType = GET_TYPE;

    }

    /**
     * POST_JSON_TYPE 呼叫此方法
     * @param putJson
     */
    public OkHttpTaskTools(Context mContext, String putJson) {

        this.mContext = mContext;
        this.clientType = POST_JSON_TYPE;
        this.putJson = putJson;
    }

    /**
     * POST_KEY_VALUE_TYPE 呼叫此方法
     * @param onKeyValueHelperListener
     */
    public OkHttpTaskTools(Context mContext, OnKeyValueHelperListener onKeyValueHelperListener) {

        this.mContext = mContext;
        this.clientType = POST_KEY_VALUE_TYPE;
        this.onKeyValueHelperListener = onKeyValueHelperListener;
    }

    /**
     * POST_FORM_TYPE 呼叫此方法
     * @param onImageAndStringKVListener
     */
    public OkHttpTaskTools(Context mContext, OnImageAndStringKVListener onImageAndStringKVListener) {

        this.mContext = mContext;
        this.clientType = POST_FORM_TYPE;
        this.onImageAndStringKVListener = onImageAndStringKVListener;
    }

    /**
     * 讀取中dialog建置
     */
    private void initProgressDialog() {
        if(isHasProgress) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }
    }

    /**
     * 設定是否要有 ProgressDialog
     * @param isHasProgress
     * @return
     */
    public OkHttpTaskTools setProgressDialog(boolean isHasProgress) {
        this.isHasProgress = isHasProgress;
        return this;
    }


    @Override
    protected void onPreExecute() {
        initProgressDialog();
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... params) {

        client = new OkHttpClient();

        //-----cookie對策-----
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cookieJar(new CookieJar() {

            private final HashMap<HttpUrl, List<Cookie>> cookieStore = new HashMap<>();
            @Override
            public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
                if(onCookieSettingListener != null) {
                    onCookieSettingListener.onCustomCookieSet(url, cookies);
                }
                cookieStore.put(url, cookies);
            }

            @Override
            public List<Cookie> loadForRequest(HttpUrl url) {
                List<Cookie> cookies = cookieStore.get(url);
                return cookies != null ? cookies : new ArrayList<Cookie>();
            }
        });
        client = builder.build();

        //-----cookie對策 end-----

        Request request;

        switch (clientType) {
            case GET_TYPE:

                request = new Request.Builder().url(params[0]).build();
                try {
                    Response response = client.newCall(request).execute();

                    if(response.isSuccessful()) {

                        String result = response.body().string();
                        Log.d(TAG, "GET_TYPE response: " + result);
                        return result;
                    }else {
                        throw new IOException("GET_TYPE Unexpected code " + response);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case POST_JSON_TYPE:

                RequestBody bodyJson = RequestBody.create(JSON, putJson);
                request = new Request.Builder()
                        .url(params[0])
                        .post(bodyJson)
                        .build();

                try {
                    Response response = client.newCall(request).execute();
                    if(response.isSuccessful()) {
                        String result = response.body().string();
                        Log.d(TAG, "POST_JSON_TYPE response: " + result);
                        return result;
                    }else {
                        throw new IOException("POST_JSON_TYPE Unexpected code " + response);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
            case POST_KEY_VALUE_TYPE:

                String[] keys = onKeyValueHelperListener.onBuildKey();
                String[] values = onKeyValueHelperListener.onBuildValue();

//                MultipartBody.Builder builder = new MultipartBody.Builder();
//                builder.setType(MultipartBody.FORM);
//                for (int i = 0; i < keys.length; i++) {
//
//                    builder.addFormDataPart(keys[i], values[i]);
//
//                    Log.d(TAG, "key: " + keys[i] + ", value: " + values[i]);
//
//                }
//                RequestBody bodyKV = builder.build();

                FormBody.Builder formBody = new FormBody.Builder();
                for (int i = 0; i < keys.length; i++) {

                    formBody.add(keys[i], values[i]);

                    Log.d(TAG, "POST_KEY_VALUE_TYPE key: " + keys[i] + ", value: " + values[i]);

                }
                RequestBody bodyKV = formBody.build();




                request = new Request.Builder()
                        .url(params[0])
                        .post(bodyKV)
                        .build();


                try {
                    Response response = client.newCall(request).execute();
                    if(response.isSuccessful()) {

                        String result = response.body().string();
                        Log.d(TAG, "POST_KEY_VALUE_TYPE response: " + result);
                        return result;

                    }else {

                        throw new IOException("POST_KEY_VALUE_TYPE Unexpected code " + response);

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;

            case POST_FORM_TYPE:

                String[] stringKeys = onImageAndStringKVListener.onBuildStringKey();
                String[] stringValues = onImageAndStringKVListener.onBuildStringValue();
                String[] imageKeys = onImageAndStringKVListener.onBuildImageKey();
                Uri[] imageValues = onImageAndStringKVListener.onBuildImageValue();

                MultipartBody.Builder formBuilder = new MultipartBody.Builder();
                formBuilder.setType(MultipartBody.FORM);

                for (int i = 0; i < stringKeys.length; i++) {

                    try {

                        Log.d(TAG, "POST_FORM_TYPE string key: " + stringKeys[i] + ", value: " + stringValues[i]);

                        formBuilder.addPart(
                                Headers.of("Content-Disposition", "form-data; name=\"" + stringKeys[i] +"\""),
                                RequestBody.create(null, URLEncoder.encode(stringValues[i], "UTF-8"))
                        );
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }

                for (int j = 0; j < imageKeys.length; j++) {

                    String pathToOurFile = imageValues[j].getPath();


                    Log.d(TAG, "POST_FORM_TYPE image key: " + imageKeys[j] + ", value: " + stringValues[j]);

                    formBuilder.addPart(
                            Headers.of("Content-Disposition", "form-data; name=\""+ imageKeys[j] + "\"" + ";filename=\"" + pathToOurFile.toString() + "\""),
                            RequestBody.create(MEDIA_TYPE_PNG, new File(pathToOurFile))
                    );

                }

                RequestBody requestBody = formBuilder.build();

                request = new Request.Builder()
//                .header("Authorization", "Client-ID " + IMGUR_CLIENT_ID)
                        .url(params[0])
                        .post(requestBody)
                        .build();

                try {
                    Response response = client.newCall(request).execute();

                    if(response.isSuccessful()) {

                        String result = response.body().string();
                        Log.d(TAG, "POST_FORM_TYPE result: " + result);
                        return result;

                    }else {

                        throw new IOException("POST_FORM_TYPE Unexpected code " + response);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

                break;
        }


        return null;
    }

    @Override
    protected void onPostExecute(String result) {

        if(result != null && !result.equals("")) {
            Log.d(TAG, "成功囉\nresult: " + result);
        }else {
            Log.d(TAG, "失敗囉\nresult: " + result);
        }

        if(isHasProgress) {
            progressDialog.dismiss();
        }

        if(onPostExecuteListener != null) {
            onPostExecuteListener.onCustomPostExecute(result);
        }

    }

    /**
     * 建構單純字串key value 監聽
     */
    public static interface OnKeyValueHelperListener {

        String[] onBuildKey();

        String[] onBuildValue();

    }

    /**
     * 建構圖片與字串key value 監聽
     */
    public static interface OnImageAndStringKVListener {

        String[] onBuildStringKey();

        String[] onBuildStringValue();

        String[] onBuildImageKey();

        Uri[] onBuildImageValue();
    }

    /**
     * 後續動作相關監聽
     */
    public static interface OnPostExecuteListener {

        /**
         * 客製化後續相關動作
         * @param result
         */
        void onCustomPostExecute(String result);

    }

    /**
     * 後續相關監聽設定
     * @param onPostExecuteListener
     * @return
     */
    public OkHttpTaskTools setOnPostExecuteListener(OnPostExecuteListener onPostExecuteListener) {
        this.onPostExecuteListener = onPostExecuteListener;
        return this;
    }

    /**
     * cookie相關監聽
     */
    public static interface OnCookieSettingListener {

        /**
         * 客製化cookie相關設定
         * @param url
         * @param cookies
         */
        void  onCustomCookieSet(HttpUrl url, List<Cookie> cookies);

    }

    /**
     * cookie監聽設定
     * @param onCookieSettingListener
     * @return
     */
    public OkHttpTaskTools setOnCookieSettingListener(OnCookieSettingListener onCookieSettingListener) {
        this.onCookieSettingListener = onCookieSettingListener;
        return this;
    }

    /**
     * 範例
     * @param mContext
     */
    private void example(Context mContext){

        /**
         *
         *GET_TYPE 單純GET
         *
         *****/
        new OkHttpTaskTools(mContext)
                .setOnPostExecuteListener(new OnPostExecuteListener() {
                    @Override
                    public void onCustomPostExecute(String result) {
                        //後續要做的事項
                    }
                })
                .setProgressDialog(true)
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "url");



        /**
         *
         *POST_JSON_TYPE 有帶cookie的範例(每種type皆可帶入) 可傳遞JSON字串
         *
         *****/
        String json = "";
        new OkHttpTaskTools(mContext, json)
                .setOnCookieSettingListener(new OnCookieSettingListener() {
                    @Override
                    public void onCustomCookieSet(HttpUrl url, List<Cookie> cookies) {
                        //針對cookie要做的事項
                    }
                })
                .setOnPostExecuteListener(new OnPostExecuteListener() {
                    @Override
                    public void onCustomPostExecute(String result) {
                        //後續要做的事項
                    }
                })
                .setProgressDialog(true)
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "url");



        /**
         *
         * POST_KEY_VALUE_TYPE 單純傳遞文字 key value
         *
         *****/
        new OkHttpTaskTools(mContext, new OnKeyValueHelperListener() {
            @Override
            public String[] onBuildKey() {
                return new String[]{"key1", "key2"};
            }

            @Override
            public String[] onBuildValue() {
                return new String[]{"value1", "value2"};
            }
        }).setOnPostExecuteListener(new OnPostExecuteListener() {
            @Override
            public void onCustomPostExecute(String result) {
                //後續要做的事項
            }
        })
                .setProgressDialog(true)
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "url");



        /**
         *
         * POST_FORM_TYPE 可同時傳文字與圖片 key value
         *
         *****/
        new OkHttpTaskTools(mContext, new OnImageAndStringKVListener() {
            @Override
            public String[] onBuildStringKey() {
                return new String[]{"text_key1", "text_key2"};
            }

            @Override
            public String[] onBuildStringValue() {
                return new String[]{"text_value1", "text_value2"};
            }

            @Override
            public String[] onBuildImageKey() {
                return new String[]{"img_key1", "img_key2"};
            }

            @Override
            public Uri[] onBuildImageValue() {
                return new Uri[0]; //將圖片的Uri帶入
            }
        }).setOnPostExecuteListener(new OnPostExecuteListener() {
            @Override
            public void onCustomPostExecute(String result) {
                //後續要做的事項
            }
        })
                .setProgressDialog(true)
                .executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, "url");

    }

}
