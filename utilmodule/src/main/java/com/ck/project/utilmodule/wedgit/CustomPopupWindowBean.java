package com.ck.project.utilmodule.wedgit;

/**
 * Created by ck on 2018/9/8.
 */

public class CustomPopupWindowBean {
    private int id;
    private int titleColor;
    private String title;
    private String content;

    public CustomPopupWindowBean(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public CustomPopupWindowBean(int id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public CustomPopupWindowBean(int id, String title, int titleColor) {
        this.id = id;
        this.titleColor = titleColor;
        this.title = title;
    }

    public CustomPopupWindowBean(int id, int titleColor, String title, String content) {
        this.id = id;
        this.titleColor = titleColor;
        this.title = title;
        this.content = content;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getTitleColor() {
        return titleColor;
    }

    public void setTitleColor(int titleColor) {
        this.titleColor = titleColor;
    }
}
