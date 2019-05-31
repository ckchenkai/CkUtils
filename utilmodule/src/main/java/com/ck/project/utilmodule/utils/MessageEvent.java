package com.ck.project.utilmodule.utils;

import java.util.List;

public class MessageEvent {
    public static final int EVENT_MSG_NAME = 0;
    public static final int EVENT_MSG_EDU = 1;
    public static final int EVENT_MSG_HOMETOWN = 2;
    public static final int EVENT_MSG_LANGUAGE = 3;
    public static final int EVENT_MSG_RELIGION = 4;
    public static final int EVENT_MSG_SELFINTRO = 5;
    public static final int EVENT_MSG_EXPECTED_CITY = 6;
    public static final int EVENT_MSG_EXPECTED_SALARY = 7;
    public static final int EVENT_MSG_CERTIFICATE = 8;
    public static final int EVENT_MSG_WORK_EXPERIENCE = 9;
    public static final int EVENT_MSG_LIFE_PHOTO = 10;
    public static final int EVENT_MSG_IS_AUTHENTICATED = 11;
    //    public static final int EVENT_MSG_EDIT_CONTRACT= 12;
//    public static final int EVENT_MSG_CANCLE_CONTRACT= 13;
//    public static final int EVENT_MSG_CONFIRM_CONTRACT= 14;
    public static final int EVENT_MSG_ORDER = 12;
    public static final int EVENT_MSG_SEND_BACK_CONTRACT = 13;
    public static final int EVENT_MSG_SERVICE_ABILITY = 14;

    public int msgType;
    public String msgContent;
    public List<String> mPathList;
    public Object object;
}
