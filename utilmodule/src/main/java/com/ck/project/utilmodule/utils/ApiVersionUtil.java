package com.ck.project.utilmodule.utils;

import android.os.Build;

/**
 * Created by CK on 2018/6/4.
 * Email:910663958@qq.com
 */

public class ApiVersionUtil {
    /**
     * 6.0以上版本号
     * @return
     */
    public static boolean isMarshmallow(){
        return Build.VERSION.SDK_INT>=Build.VERSION_CODES.M;
    }
}
