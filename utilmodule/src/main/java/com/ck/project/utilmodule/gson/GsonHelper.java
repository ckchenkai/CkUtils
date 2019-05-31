package com.ck.project.utilmodule.gson;

import com.google.gson.Gson;

import java.lang.reflect.Type;

/**
 * Created by CK on 2018/6/5.
 * Email:910663958@qq.com
 */

public class GsonHelper {
    private static final Gson GSON = new Gson();

    /**
     * @param object
     * @return
     */
    public static String toJson(Object object) {
        return GSON.toJson(object);
    }

    /**
     * @param object
     * @param type
     * @return
     */
    public static String toJson(Object object, Type type) {
        return GSON.toJson(object, type);
    }

    /**
     * 字符串转化成对象
     *
     * @param json
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Class<T> tClass) {
        return GSON.fromJson(json, tClass);
    }

    /**
     * 字符串转化成对象
     *
     * @param json
     * @param type
     * @param <T>
     * @return
     */
    public static <T> T fromJson(String json, Type type) {
        return GSON.fromJson(json, type);
    }
}
