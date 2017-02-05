package com.salton123.hamb.controller.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;

import com.salton123.base.ActivityBase;
import com.salton123.hamb.controller.fragment.SearchFragment;
import com.salton123.mengmei.R;

/**
 * User: 巫金生(newSalton@outlook.com)
 * Date: 2017/2/4 17:19
 * Time: 17:19
 * Description:
 */
public class ScenarioSearchAty extends ActivityBase {
    public final static String TAB_SEARCH_RESULT = "TAB_SEARCH_RESULT";
    public static int tab = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_scenario_search);
        Intent intent = getIntent();
        tab = intent.getIntExtra(TAB_SEARCH_RESULT, 1);
        getSupportFragmentManager().beginTransaction().replace(R.id.search_fragment_container, SearchFragment.getInstance(), "SEARCH_FRAGMENT_TAG").commitAllowingStateLoss();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            SearchFragment fragment = (SearchFragment) getSupportFragmentManager().findFragmentByTag("SEARCH_FRAGMENT_TAG");
            if (fragment == null) return super.onKeyDown(keyCode, event);
            else {
                if (fragment.onBackPress()) {
                    return true;
                }
                super.onKeyDown(keyCode, event);
            }
        }
        return super.onKeyDown(keyCode, event);
    }

}
