package com.udnmobile.fragment.fragmentlifecycle;

import android.app.Dialog;
import android.app.DialogFragment;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by Asus on 2017/4/11.
 */
public class BottomDialogFragment extends DialogFragment {

    private static final String TAG = "Dialog2";

    public static BottomDialogFragment newInstance() {
        return new BottomDialogFragment();
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.i(TAG, "onStart: ");

        Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.BOTTOM; // 显示在底部
        params.width = WindowManager.LayoutParams.MATCH_PARENT; // 宽度填充满屏
        window.setAttributes(params);

        // 这里用透明颜色替换掉系统自带背景
        int color = ContextCompat.getColor(getActivity(), android.R.color.transparent);
        window.setBackgroundDrawable(new ColorDrawable(color));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        Log.i(TAG, "onCreateView: ");

        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE); // 不显示标题栏

        final View dialogView = inflater.inflate(R.layout.fragment_botttom_dialog, container, false);

        Button okButton = (Button) dialogView.findViewById(R.id.okButton);
        Button cancelButton = (Button) dialogView.findViewById(R.id.cancelButton);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "OK clicked", Toast.LENGTH_SHORT).show();
                startDownAnimation(dialogView);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(), "Cancel clicked", Toast.LENGTH_SHORT).show();
                startDownAnimation(dialogView);
            }
        });

        startUpAnimation(dialogView);

        return dialogView;
    }

    private void startUpAnimation(View view) {
        Animation slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                1.0f, Animation.RELATIVE_TO_SELF, 0.0f);

        slide.setDuration(400);
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
        view.startAnimation(slide);
    }

    private void startDownAnimation(View view) {
        Animation slide = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.0f, Animation.RELATIVE_TO_SELF,
                0.0f, Animation.RELATIVE_TO_SELF, 1.0f);

        slide.setDuration(400);
        slide.setFillAfter(true);
        slide.setFillEnabled(true);
        slide.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                dismiss();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(slide);
    }

}
