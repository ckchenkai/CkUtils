package com.example.ckutils;

import android.app.Application;

import com.ohosure.component.componentlib.AppConfig;
import com.ohosure.component.componentlib.IComponentApp;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initHuiXue();
    }

    // 初始化荟学模块
    private void initHuiXue() {
        for (String component : AppConfig.COMPONENTS) {
            try {
                Class<?> clazz = Class.forName(component);
                Object object = clazz.newInstance();
                if (object instanceof IComponentApp) {
                    ((IComponentApp) object).init(this);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
