package com.jaiky.imagespickers.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.TypedValue;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Utils {

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier(
                "status_bar_height",
                "dimen",
                "android"
        );
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @TargetApi(19)
    public static void hideTitleBar(Activity activity, int resource, int steepToolBarColor) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            LinearLayout whole_layout = (LinearLayout) activity.findViewById(resource);
            whole_layout.setBackgroundColor(steepToolBarColor);
            whole_layout.setPadding(
                    0,
                    getStatusBarHeight(activity),
                    0,
                    0
            );
        }
    }

    /**
     * exist SDCard
     */
    public static boolean existSDCard() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    public static String getImageName() {
        String PATTERN = "yyyyMMddHHmmss";
        return new SimpleDateFormat(PATTERN, Locale.CHINA).format(new Date()) + ".jpg";
    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * dp转px，调用系统方法
     *
     * @param context
     * @param dpVal
     * @return
     */
    public int dp2pxFromSystem(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}