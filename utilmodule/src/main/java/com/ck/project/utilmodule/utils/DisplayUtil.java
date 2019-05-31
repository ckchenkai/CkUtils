package com.ck.project.utilmodule.utils;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Pair;

/**
 * Created by CK on 2018/6/4.
 * Email:910663958@qq.com
 */

public class DisplayUtil {
    /**
     * 获取屏幕宽高
     * @return
     */
    public static Pair getScreenParams(){
        DisplayMetrics dm = new DisplayMetrics();
        int width = dm.widthPixels;
        int heigth = dm.heightPixels;
        return new Pair(width,heigth);
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dp2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    public static float getDisplayDensity(Context context) {
        if (context == null) {
            return -1;
        }
        return context.getResources().getDisplayMetrics().density;
    }
}
