package com.ck.project.utilmodule.utils;

import android.text.TextUtils;
import android.util.Log;

import com.ck.project.utilmodule.BuildConfig;

/**
 * Created by CK on 2018/6/5.
 * Email:910663958@qq.com
 * 日志
 */

public class ULog {
    public static boolean DEBUG;
    private static final String TAG = "ck";

    public static void v(String tag, String msg) {
        if (DEBUG) {
            if (TextUtils.isEmpty(tag)) {
                Log.v(TAG, msg);
            } else {
                Log.v(tag, msg);
            }
        }
    }

    public static void d(String tag, String msg) {
        if (DEBUG) {
            if (TextUtils.isEmpty(tag)) {
                Log.d(TAG, msg);
            } else {
                Log.d(tag, msg);
            }
        }
    }

    public static void i(String tag, String msg) {
        if (DEBUG) {
            if (TextUtils.isEmpty(tag)) {
                Log.i(TAG, msg);
            } else {
                Log.i(tag, msg);
            }
        }
    }

    public static void w(String tag, String msg) {
        if (DEBUG) {
            if (TextUtils.isEmpty(tag)) {
                Log.w(TAG, msg);
            } else {
                Log.w(tag, msg);
            }
        }
    }

    public static void e(String tag, String msg) {
        if (DEBUG) {
            if (TextUtils.isEmpty(tag)) {
                Log.e(TAG, msg);
            } else {
                Log.e(tag, msg);
            }
        }
    }


    public static void v(String msg) {
        if (DEBUG) {
            Log.v(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (DEBUG) {
            Log.d(TAG, msg);
        }
    }

    public static void i(String msg) {
        if (DEBUG) {

            Log.i(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (DEBUG) {
            Log.w(TAG, msg);
        }
    }

    public static void e( String msg) {
        if (DEBUG) {
            Log.e(TAG, msg);
        }
    }

}
