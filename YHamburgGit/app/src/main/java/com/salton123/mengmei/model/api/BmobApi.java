package com.salton123.mengmei.model.api;

import com.salton123.mengmei.model.bmob.ImageBean;
import com.salton123.mengmei.model.bmob.ImageBeanResult;
import com.salton123.mengmei.model.bmob.UpdateInfo;
import com.salton123.mengmei.model.bmob.UpdateInfoResult;
import com.salton123.mengmei.model.bmob.UploadImageResult;
import com.salton123.mengmei.model.bmob.VoiceEntityResult;
import com.salton123.mengmei.model.config.RetrofitManager;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/8/22 21:59
 * Time: 21:59
 * Description:
 */
public class BmobApi {

    public interface Update {
        @Headers({
                "X-Bmob-Application-Id:65ff5eb52303b06bc588627d64938757",
                "X-Bmob-REST-API-Key:26da41a79afa32d88b00bc6dacf89a10",
                "Content-Type:application/json"
        })
        @GET(BmobString.UPDATE_INFO)
        Call<UpdateInfoResult> getUpdateInfo();

        @Headers({
                "X-Bmob-Application-Id:" + BmobString.APPLICATION_ID,
                "X-Bmob-REST-API-Key:" + BmobString.REST_API_KEY,
                "Content-Type: application/json"
        })
        @POST(BmobString.UPDATE_INFO)
        Call<UpdateInfo> postUpdate(
                @Body UpdateInfo updateInfo
        );
    }

    public interface Voice {
        @Headers({
                "X-Bmob-Application-Id:65ff5eb52303b06bc588627d64938757",
                "X-Bmob-REST-API-Key:26da41a79afa32d88b00bc6dacf89a10",
                "Content-Type:application/json"
        })
        @GET(BmobString.VOICE_ENTIRY)
        Call<VoiceEntityResult> getVoiceList();
    }

    public interface Image {
        @Headers({
                "X-Bmob-Application-Id:65ff5eb52303b06bc588627d64938757",
                "X-Bmob-REST-API-Key:26da41a79afa32d88b00bc6dacf89a10",
                "Content-Type:application/json"
        })
        @GET(BmobString.IMAGE_BEAN)
        Call<ImageBeanResult> getImageBeanList();

        @Headers({
                "X-Bmob-Application-Id:" + BmobString.APPLICATION_ID,
                "X-Bmob-REST-API-Key:" + BmobString.REST_API_KEY,
                "Content-Type: application/json"
        })
        @POST(BmobString.IMAGE_BEAN)
        Call<ImageBean> postImageBean(
                @Body ImageBean updateInfo
        );
    }

    public interface File{
        @Headers({
                "X-Bmob-Application-Id:65ff5eb52303b06bc588627d64938757",
                "X-Bmob-REST-API-Key:26da41a79afa32d88b00bc6dacf89a10",
                "Content-Type:image/*"
        })
        @POST(BmobString.FILE)
        Call<UploadImageResult> uploadImage();
    }

    public static Update getUpdate() {
        return RetrofitManager.getBmob().create(Update.class);
    }

    public static Voice getVoice() {
        return RetrofitManager.getBmob().create(Voice.class);
    }

    public static Image getImage() {
        return RetrofitManager.getBmob().create(Image.class);
    }


}
