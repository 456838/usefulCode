package com.salton123.base;

import android.os.Environment;

import java.io.File;

/**
 * Created by sunfusheng on 15/8/3.
 */
public class GlobalParams {

    public static int screenWidth;
    public static int screenHeight;
//
//    public static String LOG_TAG_URL = "log-url";
//    public static String LOG_TAG_CONTENT = "log-content";

    public static final int mAppKey = 1285246627;
    public static final String mAppSecret = "172b25da_0";
    public static final int mAppVersion = 1;

    public static final String FOLDER = "newsalton";

    /**
     * 我的头像保存目录
     */
    public static String DIRECTORY_AVATAR = Environment.getExternalStorageDirectory() + File.separator + "artMaster/avatar/";
    public static String DIRECTORY_WORK = Environment.getExternalStorageDirectory() + File.separator + "artMaster/work/";

    /**
     * 拍照回调
     */
    public static final int REQUESTCODE_UPLOAD_CAMERA = 1;//拍照修改头像
    public static final int REQUESTCODE_UPLOAD_LOCAL = 2;//本地相册修改头像
    public static final int REQUESTCODE_UPLOAD_CROP = 3;//系统裁剪头像
    //头像更新
    public static final int UPDATE_HEAD_ICON = 4;
    public static final int FLAG_REQUEST_CODE_100 = 100;
    public static final int FLAG_REQUEST_CODE_200 = 200;

    public static final int TEXT_TYPE = 0;
    public static final int MEMBER_ENTER = 1;
    public static final int MEMBER_EXIT = 2;
    public static final int HOST_LEAVE = 3;
    public static final int HOST_BACK = 4;

    public static final String WXAPP_ID="wx00544fdb4768a7d6";

}
