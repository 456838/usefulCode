package com.salton123.mengmei.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.salton123.base.ActivityFrameIOS;
import com.salton123.mengmei.R;
import com.salton123.mengmei.model.bean.YcProgram;

import org.xutils.x;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import cn.finalteam.rxgalleryfinal.RxGalleryFinal;
import cn.finalteam.rxgalleryfinal.imageloader.ImageLoaderType;
import cn.finalteam.rxgalleryfinal.rxbus.RxBusResultSubscriber;
import cn.finalteam.rxgalleryfinal.rxbus.event.ImageRadioResultEvent;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/10/22 22:46
 * Time: 22:46
 * Description:
 */
public class EditYcProgramActivity extends ActivityFrameIOS {


    @BindView(R.id.cover)
    ImageView cover;
    @BindView(R.id.tv_pic_tip)
    TextView tvPicTip;
    @BindView(R.id.et_title)
    BootstrapEditText etTitle;
    YcProgram mProgram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppendMainBody(R.layout.aty_edit_ycprogram);
        ButterKnife.bind(this);
        SetTopTitle("房间信息");
        SetTopAdditionalListener("确定", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String titleInfo = etTitle.getText().toString();
                if (!TextUtils.isEmpty(titleInfo)) {
                    mProgram.setTitleInfo(titleInfo);
                }
                mProgram.update(new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if (e == null) {
                            Snackbar.make(cover, "房间信息修改成功！", Snackbar.LENGTH_SHORT).show();
                            Intent intent = new Intent();
                            intent.putExtra("program",mProgram);
                            setResult(RESULT_OK,intent);
                            finish();
                        }
                    }
                });
            }
        });
        Intent intent = this.getIntent();
        mProgram = (YcProgram) intent.getSerializableExtra("program");
    }

    @Override
    public void InitView() {
    }

    @Override
    public void InitListener() {

    }

    @Override
    public void InitData() {

    }

    @OnClick(R.id.cover)
    public void onClick() {
        RxGalleryFinal
                .with(EditYcProgramActivity.this)
                .image()
                .radio()
                .crop()
                .cropropCompressionQuality(50)
                .cropMaxResultSize(800, 600)
                .imageLoader(ImageLoaderType.GLIDE)
                .subscribe(new RxBusResultSubscriber<ImageRadioResultEvent>() {
                    @Override
                    protected void onEvent(ImageRadioResultEvent imageRadioResultEvent) throws Exception {
                        System.out.println("location2：" + imageRadioResultEvent.getResult().getCropPath());
                        Toast.makeText(getBaseContext(), imageRadioResultEvent.getResult().getCropPath(), Toast.LENGTH_SHORT).show();
                        final BmobFile bmobFile = new BmobFile(new File(imageRadioResultEvent.getResult().getCropPath()));
                        bmobFile.uploadblock(new UploadFileListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    if (TextUtils.isEmpty((String) bmobFile.getFileUrl())) {
                                        Toast.makeText(GetContext(), getString(R.string.label_file_upload_failed), Toast.LENGTH_LONG).show();
                                        return;
                                    }
                                    mProgram.setThumbnailUrl(bmobFile.getFileUrl());
                                    x.image().bind(cover, bmobFile.getFileUrl());
                                    tvPicTip.setText("上传完毕");
                                } else {
                                    Toast.makeText(getApplicationContext(), "文件上传失败:" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onProgress(Integer value) {
                                // 返回的上传进度（百分比）
                                tvPicTip.setText("上传进度:" + value + "%");
                            }
                        });

                    }
                })
                .openGallery();
    }
}
