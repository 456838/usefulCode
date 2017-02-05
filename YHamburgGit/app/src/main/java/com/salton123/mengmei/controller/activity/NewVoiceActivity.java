package com.salton123.mengmei.controller.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.orhanobut.logger.Logger;
import com.salton123.mengmei.R;
import com.salton123.mengmei.model.bmob.VoiceEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/9/5 22:40
 * Time: 22:40
 * Description:
 */
public class NewVoiceActivity extends AppCompatActivity {

    @BindView(R.id.btn_submit)
    Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_new_voice);
        ButterKnife.bind(this);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < 10; i++) {
                    VoiceEntity voiceEntity = new VoiceEntity();
                    voiceEntity.setTitle("有你便是晴天");
                    voiceEntity.setDescribe("有你便是晴天霹雳，还我清白");
                    voiceEntity.setVoiceUrl("http://mp3.haoduoge.com/s/2016-09-05/1473081304.mp3");
                    voiceEntity.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            Logger.e("保存:\n" + s);
                        }
                    });
                }
            }
        });
    }


}
