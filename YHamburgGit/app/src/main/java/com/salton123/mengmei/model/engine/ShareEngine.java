package com.salton123.mengmei.model.engine;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.widget.Toast;

import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXEmojiObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/3/8 21:19
 * Time: 21:19
 * Description:
 */
public class ShareEngine {

    private static final String APP_ID = "wx924b486016ae97be";
    private static IWXAPI api;

    private static boolean isAvilible(Context context, String packageName) {
        PackageManager packageManager = context.getPackageManager();

        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);
        for (int i = 0; i < pinfo.size(); i++) {
            if (((PackageInfo) pinfo.get(i)).packageName.equalsIgnoreCase(packageName))
                return true;
        }
        return false;
    }

    public static void ShareImageToQQ(Activity p_Activity, String p_LocalImagePath) {
        if (!isAvilible(p_Activity, "com.tencent.mobileqq")) {
            Toast.makeText(p_Activity, "请先安装qq", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent localIntent = new Intent("android.intent.action.SEND");
        localIntent.setType("image/*");
//        Uri uri = Uri.parse("file:///sdcard/temp.jpg");
        Uri uri = Uri.parse("file://" + p_LocalImagePath);
        localIntent.putExtra("android.intent.extra.STREAM", uri);
        localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setComponent(new ComponentName("com.tencent.mobileqq", "com.tencent.mobileqq.activity.JumpActivity"));
        p_Activity.startActivity(Intent.createChooser(localIntent, "分享"));
    }

    public static void ShareImageToWeiXin(Activity p_Activity, String p_LocalImagePath) {
        if (!isAvilible(p_Activity, "com.tencent.mm")) {
            Toast.makeText(p_Activity, "请先安装微信", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent localIntent = new Intent("android.intent.action.SEND");
        localIntent.setType("image/*");
//        Uri uri = Uri.parse("file:///sdcard/temp.jpg");
        Uri uri = Uri.parse("file://" + p_LocalImagePath);
        localIntent.putExtra("android.intent.extra.STREAM", uri);
        localIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        localIntent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.tools.ShareImgUI"));
        p_Activity.startActivity(Intent.createChooser(localIntent, "分享"));
    }

    public static void SendEmojiToWX(Activity p_Activity,String p_localPath) {
        //注册到微信
        api = WXAPIFactory.createWXAPI(p_Activity, APP_ID);
        api.registerApp(APP_ID);
//        final String EMOJI_FILE_THUMB_PATH =Environment.getExternalStorageDirectory().getAbsolutePath() + "/temp.jpg";
        WXEmojiObject emoji = new WXEmojiObject();
        emoji.emojiPath = p_localPath;
        WXMediaMessage msg = new WXMediaMessage(emoji);
        msg.title = "ImageBean Title";
        msg.description = "ImageBean Description";
//        msg.thumbData =Util.readFromFile(EMOJI_FILE_THUMB_PATH, 0, (int) new File(EMOJI_FILE_THUMB_PATH).length());
        try {
            InputStream abpath = p_Activity.getAssets().open("123.png");
            msg.thumbData = InputStreamToByte(abpath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = System.currentTimeMillis() + System.currentTimeMillis() + "";
        req.message = msg;
        if (!req.checkArgs()) {
            Toast.makeText(p_Activity, "请求体参数不正确", Toast.LENGTH_SHORT).show();
            return;
        }
        //发送请求给微信
        api.sendReq(req);
    }


    private static byte[] InputStreamToByte(InputStream is) throws IOException {
        ByteArrayOutputStream bytestream = new ByteArrayOutputStream();
        int ch;
        while ((ch = is.read()) != -1) {
            bytestream.write(ch);
        }
        byte imgdata[] = bytestream.toByteArray();
        bytestream.close();
        return imgdata;
    }


}
