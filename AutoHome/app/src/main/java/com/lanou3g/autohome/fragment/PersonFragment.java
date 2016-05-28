package com.lanou3g.autohome.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lanou3g.autohome.mycollect.MyCollectActivity;
import com.lanou3g.autohome.R;
import com.lanou3g.autohome.base.BaseFragment;

/**
 * Created by dllo on 16/5/9.
 * 我
 */
public class PersonFragment extends BaseFragment implements View.OnClickListener {

    private RelativeLayout myCollectLayout;

    @Override
    public int initLayout() {
        return R.layout.fragment_person;
    }

    @Override
    public void initView() {

        myCollectLayout = bindView(R.id.fragment_person_collect_layout);
        myCollectLayout.setOnClickListener(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fragment_person_collect_layout:
                Intent intent = new Intent(context, MyCollectActivity.class);
                startActivity(intent);
                break;
        }

    }
}
