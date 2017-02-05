package com.salton123.mengmei.controller.activity;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.redbooth.WelcomeCoordinatorLayout;
import com.salton123.mengmei.R;
import com.salton123.mengmei.view.splash.animators.ChatAvatarsAnimator;
import com.salton123.mengmei.view.splash.animators.InSyncAnimator;
import com.salton123.mengmei.view.splash.animators.RocketAvatarsAnimator;
import com.salton123.mengmei.view.splash.animators.RocketFlightAwayAnimator;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Observable;
import rx.Observer;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2016/8/26 7:36
 * Time: 7:36
 * Description:
 */
public class SplashActivity extends AppCompatActivity {
    @BindView(R.id.skip)
    Button skip;
    private boolean animationReady = false;
    private ValueAnimator backgroundAnimator;
    @BindView(R.id.coordinator)
    WelcomeCoordinatorLayout coordinatorLayout;
    private RocketAvatarsAnimator rocketAvatarsAnimator;
    private ChatAvatarsAnimator chatAvatarsAnimator;
    private RocketFlightAwayAnimator rocketFlightAwayAnimator;
    private InSyncAnimator inSyncAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_splash);
        ButterKnife.bind(this);
        initializeListeners();
        initializePages();
        initializeBackgroundTransitions();
    }
    Observable intervalObservable ;
    private void initializePages() {
        final WelcomeCoordinatorLayout coordinatorLayout
                = (WelcomeCoordinatorLayout) findViewById(R.id.coordinator);
        coordinatorLayout.addPage(R.layout.welcome_page_1,
                R.layout.welcome_page_2,
                R.layout.welcome_page_3,
                R.layout.welcome_page_4);
        coordinatorLayout.showIndicators(true);
//
//        intervalObservable =  Observable.interval(3, TimeUnit.SECONDS);
//        intervalObservable.subscribe(new Action1<Long>() {
//            @Override
//            public void call(Long aLong) {
//                if (coordinatorLayout.getPageSelected()==3){
//                        startActivity(new Intent(SplashActivity.this,MainActivity.class));
//                        finish();
//                }
//            }
//        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initializeListeners() {
        coordinatorLayout.setOnPageScrollListener(new WelcomeCoordinatorLayout.OnPageScrollListener() {
            @Override
            public void onScrollPage(View v, float progress, float maximum) {
                if (!animationReady) {
                    animationReady = true;
                    backgroundAnimator.setDuration((long) maximum);
                }
                backgroundAnimator.setCurrentPlayTime((long) progress);
            }

            @Override
            public void onPageSelected(View v, int pageSelected) {
                Observable.just(pageSelected).subscribe(new Observer<Integer>() {
                    @Override
                    public void onCompleted() {

                    }
                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onNext(Integer integer) {
                        if (integer==3){
//                            try {
//                                Thread.sleep(2000);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
                            startActivity(new Intent(SplashActivity.this,MainActivity.class));
                            finish();
                        }
                    }
                });
                switch (pageSelected) {
                    case 0:
                        if (rocketAvatarsAnimator == null) {
                            rocketAvatarsAnimator = new RocketAvatarsAnimator(coordinatorLayout);
                            rocketAvatarsAnimator.play();
                        }
                        break;
                    case 1:
                        if (chatAvatarsAnimator == null) {
                            chatAvatarsAnimator = new ChatAvatarsAnimator(coordinatorLayout);
                            chatAvatarsAnimator.play();
                        }
                        break;
                    case 2:
                        if (inSyncAnimator == null) {
                            inSyncAnimator = new InSyncAnimator(coordinatorLayout);
                            inSyncAnimator.play();
                        }
                        break;
                    case 3:
                        if (rocketFlightAwayAnimator == null) {
                            rocketFlightAwayAnimator = new RocketFlightAwayAnimator(coordinatorLayout);
                            rocketFlightAwayAnimator.play();
                        }
                        break;
                }
            }
        });
    }

    private void initializeBackgroundTransitions() {
        final Resources resources = getResources();
        final int colorPage1 = ResourcesCompat.getColor(resources, R.color.page1, getTheme());
        final int colorPage2 = ResourcesCompat.getColor(resources, R.color.page2, getTheme());
        final int colorPage3 = ResourcesCompat.getColor(resources, R.color.page3, getTheme());
        final int colorPage4 = ResourcesCompat.getColor(resources, R.color.page4, getTheme());
        backgroundAnimator = ValueAnimator
                .ofObject(new ArgbEvaluator(), colorPage1, colorPage2, colorPage3, colorPage4);
        backgroundAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                coordinatorLayout.setBackgroundColor((int) animation.getAnimatedValue());
            }
        });
    }

    @OnClick(R.id.skip)
    void skip() {
        coordinatorLayout.setCurrentPage(coordinatorLayout.getNumOfPages() - 1, true);
    }
}