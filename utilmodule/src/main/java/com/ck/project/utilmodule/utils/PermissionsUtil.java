package com.ck.project.utilmodule.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.ck.project.utilmodule.wedgit.CustomDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by CK on 2018/6/4.
 * Email:910663958@qq.com
 * 运行时权限控制类
 */

public class PermissionsUtil {
    private static final int PERMISSION_REQUESTCODE = 0;
    private List<String> needRequestPermissions = new ArrayList<>();
    private static PermissionsUtil instance;
    public PermissionsUtil getInstance(){
        if (instance == null) {
            instance = new PermissionsUtil();
        }
        return instance;
    }

    public void needPermissions(Activity mContext, String [] permissions){
        if(ApiVersionUtil.isMarshmallow()){
            //不需权限
            return;
        }
        for(String permission:permissions){
            if(ActivityCompat.checkSelfPermission(mContext,permission) != PackageManager.PERMISSION_GRANTED){
                needRequestPermissions.add(permission);
            }
        }
        if(needRequestPermissions!=null&&needRequestPermissions.size()>0){
            ActivityCompat.requestPermissions(mContext,needRequestPermissions.toArray(new String[needRequestPermissions.size()]),PERMISSION_REQUESTCODE);
        }else{
            //已有权限
        }
    }

    public void onRequestPermissionsResult(Context context, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode==PERMISSION_REQUESTCODE){
            if(!verifyPermissions(grantResults)){
                //提示无权限
                CustomDialog dialog = new CustomDialog.Builder(context)
                        .setContent("去系统里设置权限")
                        .setCommitListener(() -> {

                        })
                        .create();
                dialog.show();
            }else{
                //已有权限
            }
        }
    }

    /**
     * 检测是否已授权
     *
     * @param grantResults
     * @return
     */
    private boolean verifyPermissions(int[] grantResults) {
        for (int result : grantResults) {
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }
}
