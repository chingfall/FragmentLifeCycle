package com.udnmobile.fragment.fragmentlifecycle;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.udnmobile.fragment.fragmentlifecycle.Util.SnackbarUtil;

/**
 * Created by Asus on 2017/4/25.
 */
public class DesignSupportLibraryActivity extends AppCompatActivity {

    private static final String TAG = "DesignSupportLibraryActivity";

    //抽屜variable
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    //toolbar variable
    private Toolbar mToolbar;

    //會員輸入框 variable
    private EditText inputName, inputMail, inputPassword;
    private TextInputLayout inputLayoutName, inputLayoutEmail, inputLayoutPassword;
    private Button btnSignUp;

    //layout variable
    private RelativeLayout touch_layout;

    //Floating Action Buttons variable
    private FloatingActionButton fab;

    //Snackbar with CoordinatorLayout & Togglebutton
    private CoordinatorLayout coordinatorLayout;
    private Snackbar snackbar;
    private ToggleButton toggleButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_designsupportlibrary);

        init();

        initHandler();
    }

    private void init() {

        //NavigationView [DesignSupportLibrary 1]
        mDrawerLayout = (DrawerLayout) findViewById(R.id.id_drawer_layout);
        mNavigationView = (NavigationView) findViewById(R.id.id_nv_menu);

        mToolbar = (Toolbar) findViewById(R.id.id_toolbar);
        setSupportActionBar(mToolbar);

        final ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.mipmap.ic_launcher);
        ab.setDisplayHomeAsUpEnabled(true);

        //自訂客製化drawer list
        //setupDrawer();
        /*-----------------------------------------------------------------------------*/

        //Floating Labels for EditText [DesignSupportLibrary 2]
        inputName = (EditText) findViewById(R.id.input_name);
        inputMail = (EditText) findViewById(R.id.input_email);
        inputPassword = (EditText) findViewById(R.id.input_password);
        inputLayoutName = (TextInputLayout) findViewById(R.id.input_layout_name);
        inputLayoutEmail = (TextInputLayout) findViewById(R.id.input_layout_email);
        inputLayoutPassword = (TextInputLayout) findViewById(R.id.input_layout_password);
        btnSignUp = (Button) findViewById(R.id.btn_signup);

        //
        touch_layout = (RelativeLayout) findViewById(R.id.touch_layout);

        /*-----------------------------------------------------------------------------*/
        //Floating Action Buttons(FAB) [DesignSupportLibrary 3]
        fab = (FloatingActionButton) findViewById(R.id.fab);

        /*-----------------------------------------------------------------------------*/
        //SnackBar [DesignSupportLibrary 4]
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id
                .coordinatorLayout);
        toggleButton = (ToggleButton) findViewById(R.id.toggle);

    }

    private void initHandler() {

        //NavigationView [DesignSupportLibrary 1]
        setupDrawerContent(mNavigationView);

        //Floating Labels for EditText [DesignSupportLibrary 2]
        //edittext監聽式
        inputName.addTextChangedListener(new MyTextWatcher(inputName));
        inputMail.addTextChangedListener(new MyTextWatcher(inputMail));
        inputPassword.addTextChangedListener(new MyTextWatcher(inputPassword));

        //login button
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitForm();
            }
        });

        // 點擊空白區域,自動隱藏鍵盤
        touch_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager m = (InputMethodManager) getApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
                m.hideSoftInputFromWindow(getWindow().getCurrentFocus().getWindowToken(), 0);
            }
        });

        //Floating Action Buttons(FAB) [DesignSupportLibrary 3]
        //Floating Action Buttons 點擊事件
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
            }
        });

        //SnackBar [DesignSupportLibrary 4]
        //togglebutton點擊事件
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (snackbar == null) {
                    snackbar = Snackbar
                            .make(coordinatorLayout, "Click ToggleButton", Snackbar.LENGTH_LONG)
                            .setAction("Undo", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    toggleButton.toggle();
                                }
                            });
                }

                if (isChecked) {

                    //設置snackbar字體顏色
                    snackbar.setActionTextColor(Color.parseColor("#F44336"));

                    //得到Snackbar的view
                    View snackbarView = snackbar.getView();
                    //設置snackbar背景顏色
                    snackbarView.setBackgroundColor(Color.parseColor("#90CAF9"));

                    //得到Snackbar的message元件，修改字體颜色
                    TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
                    textView.setTextColor(Color.parseColor("#0D47A1"));

                    //使用snackbar library
                    /*SnackbarUtil.setSnackbarColor(snackbar, SnackbarUtil.blue);
                    SnackbarUtil.SnackbarAddView(snackbar, R.layout.snackbar_addview, 0);*/

                    //顯示snackbar
                    snackbar.show();
                } else if (snackbar.isShown()) { //if snackbar已經顯示,取消顯示狀態
                    snackbar.dismiss();
                    snackbar = null;
                }

            }
        });
    }

    private void submitForm() {
        if (!validateName() && !validateEmail() && !validatePassword()) {
            return;
        }
        /*if(!validateEmail()){
            return;
        }
        if(!validatePassword()){
            return;
        }*/
    }

    private class MyTextWatcher implements TextWatcher {
        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        public void afterTextChanged(Editable editable) {
            switch (view.getId()) {
                case R.id.input_name:
                    validateName();
                    break;
                case R.id.input_email:
                    validateEmail();
                    break;
                case R.id.input_password:
                    validatePassword();
                    break;
            }
        }
    }

    private boolean validateName() {
        if (inputName.getText().toString().trim().isEmpty()) {
            inputLayoutName.setError("Enter Your Full Name");
            requestFocus(inputName);
            return false;
        } else {
            inputLayoutName.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validateEmail() {
        String email = inputMail.getText().toString().trim();

        if (email.isEmpty() || !isValidEmail(email)) {
            inputLayoutEmail.setError("Enter valid email address");
            requestFocus(inputMail);
            return false;
        } else {
            inputLayoutEmail.setErrorEnabled(false);
        }

        return true;
    }

    private boolean validatePassword() {
        if (inputPassword.getText().toString().trim().isEmpty()) {
            inputLayoutPassword.setError("Enter the Password");
            return false;
        } else {
            inputLayoutPassword.setErrorEnabled(false);
        }

        return true;
    }

    private static boolean isValidEmail(String email) {
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    //客製化抽屜設置
    /*private void setupDrawer() {
        //RecyclerView rv = new RecyclerView(R.id.);
        //LayoutInflater inflater = LayoutInflater.from(this);
        //mLvLeftMenu.addHeaderView(inflater.inflate(R.layout.header_just_username, mLvLeftMenu, false));
        //rv.setAdapter(new MenuItemAdapter(this));
    }*/


    //item被點擊就關閉drawer.
    private void setupDrawerContent(NavigationView mNavigationView) {
        mNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            private MenuItem mPreMenuItem;

            @Override
            public boolean onNavigationItemSelected(MenuItem item) {

                //手動切換主menu和次menu的點擊事件
                if (mPreMenuItem != null) mPreMenuItem.setChecked(false);
                item.setChecked(true);
                mDrawerLayout.closeDrawers();
                mPreMenuItem = item;
                return true;
            }
        });
    }

    //
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_drawer, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if (item.getItemId() == android.R.id.home) {

            mDrawerLayout.openDrawer(GravityCompat.START);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
