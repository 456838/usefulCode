package com.salton123.mengmei.model.engine;

import android.content.Context;

import com.salton123.common.net.HttpResponseHandler;
import com.salton123.common.util.RandomUtils;
import com.salton123.mengmei.model.bean.Consignee;
import com.salton123.mengmei.model.bean.EmojiGroup;
import com.salton123.mengmei.model.bean.User;
import com.salton123.mengmei.model.bean.YcProgram;
import com.salton123.mengmei.model.bmob.ImageBean;
import com.salton123.mengmei.model.bmob.ImageBeanResult;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.datatype.BmobPointer;
import cn.bmob.v3.datatype.BmobRelation;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadBatchListener;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2015/12/22 12:17
 * Time: 12:17
 * Description:
 */
public class BmobDataEngine {

    public static void RegisterWithInfo(final String p_PhoneNum, final String p_Password, final String p_signature, final String p_PicUrl,
                                        final String p_Province, final String p_nickname, final SaveListener<User> p_SaveListener) {
        User _User = new User();
        //产生用户唯一yc_UID
        String yc_UID = System.currentTimeMillis() + RandomUtils.getRandomNumbers(3);
        String subYc_UID = yc_UID.substring(yc_UID.length() - 8, yc_UID.length());
//        Logger.e(subYc_UID);
        _User.setYc_UID(Integer.parseInt(subYc_UID));
        _User.setNickname(p_nickname);
        _User.setPassword(p_Password);
        _User.setAvatar(p_PicUrl);
        _User.setProvince(p_Province);
        _User.setSignature(p_signature);
        _User.setUsername(p_PhoneNum);
        _User.signUp(p_SaveListener);
    }

//    //注册
//    public static void Register(String p_PhoneNum, String p_Password, final HttpResponseHandler<User> p_HttpResponseHandler) {
//        User user = new User();
//        user.setUsername(p_PhoneNum);
//        user.setPassword(p_Password);
//        //产生用户唯一yc_UID
//        String yc_UID = System.currentTimeMillis() + RandomUtils.getRandomNumbers(3);
//        String subYc_UID = yc_UID.substring(yc_UID.length() - 8, yc_UID.length());
////        Logger.e(subYc_UID);
//        user.setYc_UID(Integer.parseInt(subYc_UID));
//        user.setPhoneNum(p_PhoneNum);
//        //   user.setMobilePhoneNumber(p_PhoneNum);
//        user.signUp(new SaveListener<User>() {
//            @Override
//            public void done(User user, BmobException e) {
//                if (e == null) {
//                    p_HttpResponseHandler.onSuccess(user);
//                } else {
//                    p_HttpResponseHandler.onFailure(e.getMessage());
//                }
//            }
//        });
//    }

    //登陆
    public static void Login(String p_PhoneNum, String p_Password, final HttpResponseHandler<User> p_HttpResponseHandler) {
        User user = new User();
        user.setUsername(p_PhoneNum);
        user.setPassword(p_Password);
//           user.setMobilePhoneNumber(p_PhoneNum);
        user.setPhoneNum(p_PhoneNum);
        user.login(new SaveListener<User>() {
            @Override
            public void done(User user, BmobException e) {
                if (e == null) {
                    p_HttpResponseHandler.onSuccess(user);
                } else {
                    e.printStackTrace();
                    p_HttpResponseHandler.onFailure(e.getMessage());
                }
            }
        });
    }


    public static void getYcProgrameByPage(int p_Skip, int p_Limit, FindListener<YcProgram> p_FindListener) {
        BmobQuery<YcProgram> _query = new BmobQuery<>();
        _query.setLimit(p_Limit);
        _query.setSkip(p_Skip);
        _query.order("-createdAt");
        _query.findObjects(p_FindListener);
    }

    /**
     * 批量上圖
     *
     * @param p_FilePaths
     */
    public static void UploadMultiImage(final String[] p_FilePaths, UploadBatchListener p_UploadBatchListener) {
        BmobFile.uploadBatch(p_FilePaths, p_UploadBatchListener);
    }

    /**
     * 获取用户信息
     *
     * @param p_UserId
     */
    public static void GetUser(String p_UserId, QueryListener<User> p_QueryListener) {
        BmobQuery<User> _orderQuery = new BmobQuery<>();
        _orderQuery.addWhereEqualTo("obecjectId", p_UserId);
        _orderQuery.getObject(p_UserId, p_QueryListener);
    }

    public static void FetchImageModelByPage(int p_Skip, int p_Limit, final HttpResponseHandler<List<ImageBean>> p_HttpResponseHandler) {
        BmobQuery<ImageBean> _query = new BmobQuery<>();
        _query.setLimit(p_Limit);
        _query.setSkip(p_Skip);
        _query.order("-createdAt");
        _query.findObjects(new FindListener<ImageBean>() {
            @Override
            public void done(List<ImageBean> list, BmobException e) {
                if (e != null) {
                    p_HttpResponseHandler.onSuccess(list);
                } else {
                    p_HttpResponseHandler.onFailure(e.getMessage());
                }
            }
        });
    }

    /**
     * 添加作品到自己的收藏
     *
     * @param p_ImageBean
     * @param p_HttpResponseHandler
     */
    public static void AddToCollection(ImageBean p_ImageBean, final HttpResponseHandler<String> p_HttpResponseHandler) {
        //当前用户
        User _currentUser = User.getCurrentUser(User.class);
        BmobRelation _BmobRelation = new BmobRelation();
        _BmobRelation.add(_currentUser);
        p_ImageBean.setLinks(_BmobRelation);
        p_ImageBean.setShareCount(p_ImageBean.getShareCount() + 1);
        p_ImageBean.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    p_HttpResponseHandler.onSuccess("收藏成功");
                } else {
                    p_HttpResponseHandler.onFailure("收藏失败:" + e.getMessage());
                }
            }
        });
    }


    public static void FetchMyEmojiByPage(String p_UserId, int p_Skip, int p_Limit, final HttpResponseHandler<List<ImageBean>> p_HttpResponseHandler) {
        BmobQuery<ImageBean> _query = new BmobQuery<>();
        _query.setLimit(p_Limit);
        _query.setSkip(p_Skip);
        _query.order("-createdAt");
        User emUser = new User();
        emUser.setObjectId(p_UserId);
        _query.addWhereEqualTo("links", new BmobPointer(emUser));
        _query.findObjects(new FindListener<ImageBean>() {
            @Override
            public void done(List<ImageBean> list, BmobException e) {
                if (e == null) {
                    p_HttpResponseHandler.onSuccess(list);
                } else {
                    p_HttpResponseHandler.onFailure(e.getMessage());
                }
            }
        });
    }

//    public static void FetchMyEmojiByPage(String p_UserId, int p_Skip, int p_Limit, final HttpResponseHandler<ImageBeanResult> p_HttpResponseHandler) {
//        Call<ImageBeanResult> voiceCall = BmobApi.getImage().getImageBeanList();
//        voiceCall.enqueue(new Callback<ImageBeanResult>() {
//            @Override
//            public void onResponse(Call<ImageBeanResult> call, Response<ImageBeanResult> response) {
//                if (response.isSuccessful()) {
//                    p_HttpResponseHandler.onSuccess(response.body());
//                } else {
//                    p_HttpResponseHandler.onFailure(response.raw().toString());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<ImageBeanResult> call, Throwable throwable) {
//                p_HttpResponseHandler.onFailure(throwable.getMessage());
//            }
//        });
//    }


    public static void postMyEmojiList(final String[] filePathArr, final HttpResponseHandler<ImageBeanResult> p_HttpResponseHandler) {
        BmobFile.uploadBatch(filePathArr, new UploadBatchListener() {
            @Override
            public void onSuccess(List<BmobFile> list, List<String> list1) {
                if (list1.size() == filePathArr.length) {//如果数量相等，则代表文件全部上传完成
                    //do something

                }
            }

            @Override
            public void onProgress(int i, int i1, int i2, int i3) {

            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    public static void uploadImages(List<String> files) {


    }


    /**
     * 删除自己喜欢的作品
     *
     * @param p_ImageBean
     * @param p_HttpResponseHandler
     */
    public static void DelteMyEmoji(ImageBean p_ImageBean, final HttpResponseHandler p_HttpResponseHandler) {
        //当前用户
        User _currentUser = User.getCurrentUser(User.class);
        BmobRelation _BmobRelation = new BmobRelation();
        _BmobRelation.remove(_currentUser);
        p_ImageBean.setLinks(_BmobRelation);
        p_ImageBean.update(new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    p_HttpResponseHandler.onSuccess("删除成功");
                } else {
                    p_HttpResponseHandler.onFailure("删除失败:" + e.getMessage());
                }
            }
        });
    }

    public static void FetchEmojiGroupByPage(Context p_Context, int p_Skip, int p_Limit, final HttpResponseHandler<List<EmojiGroup>> p_HttpResponseHandler) {
        BmobQuery<EmojiGroup> _query = new BmobQuery<>();
        _query.setLimit(p_Limit);
        _query.setSkip(p_Skip);
        _query.order("-createdAt");
        _query.include("owner");
        _query.findObjects(new FindListener<EmojiGroup>() {
            @Override
            public void done(List<EmojiGroup> list, BmobException e) {
                if (e == null) {
                    p_HttpResponseHandler.onSuccess(list);
                } else {
                    p_HttpResponseHandler.onFailure(e.getMessage());
                }
            }
        });
    }

    public static void FetchConsigneeByPage(String p_OwnerId, int p_Skip, int p_Limit, final HttpResponseHandler p_HttpResponseHandler) {
        BmobQuery<Consignee> _ArtQuery = new BmobQuery<>();
        _ArtQuery.setLimit(p_Limit);
        _ArtQuery.setSkip(p_Skip);
        _ArtQuery.addWhereEqualTo("ownerId", p_OwnerId);
        _ArtQuery.order("-createdAt");
        _ArtQuery.findObjects(new FindListener<Consignee>() {
            @Override
            public void done(List<Consignee> list, BmobException e) {
                if (e == null) {
                    p_HttpResponseHandler.onSuccess(list);
                } else {
                    p_HttpResponseHandler.onFailure(e.getMessage());
                }
            }
        });
    }

    public static void SetConsigneeListNotDefault(String p_OwnerId, final HttpResponseHandler<String> p_HttpResponseHandler) {
        BmobQuery<Consignee> _ArtQuery = new BmobQuery<>();
        _ArtQuery.addWhereEqualTo("ownerId", p_OwnerId);
        _ArtQuery.order("-createdAt");
        _ArtQuery.findObjects(new FindListener<Consignee>() {
            @Override
            public void done(List<Consignee> list, BmobException e) {
                if (e == null) {
                    for (int i = 0; i < list.size(); i++) {
                        Consignee _temp = list.get(i);
                        _temp.setIsdefault(false);
                        _temp.update();
                    }
                    p_HttpResponseHandler.onSuccess("修改成功");
                } else {
                    p_HttpResponseHandler.onFailure("修改失败" + e.getMessage());
                }
            }
        });

    }

    public static void queryMyYcProgram(String objectId, final HttpResponseHandler<YcProgram> httpResponseHandler) {
        BmobQuery<YcProgram> ycProgramBmobQuery = new BmobQuery<>();
        ycProgramBmobQuery.addWhereEqualTo("ownerObjectId", objectId);
        ycProgramBmobQuery.setLimit(1);
        ycProgramBmobQuery.order("-createdAt");
        ycProgramBmobQuery.findObjects(new FindListener<YcProgram>() {
            @Override
            public void done(List<YcProgram> list, BmobException e) {
                if (e == null && list.size() > 0) {
                    httpResponseHandler.onSuccess(list.get(0));
                } else {
                    httpResponseHandler.onFailure(e.getMessage());
                }
            }
        });
    }
}
