package com.capacitorjs.plugins.easyads.activity;

import android.os.Bundle;
import android.view.View;

import com.capacitorjs.plugins.easyads.EasyADController;
import com.capacitorjs.plugins.easyads.R;
import com.capacitorjs.plugins.easyads.model.SettingModel;
import com.easyads.core.reward.EasyAdRewardVideo;

public class RewardVideoActivity extends BaseActivity {
    EasyAdRewardVideo rewardVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reward_video);

    }

    public void onLoad(View view) { 
        // 获取配置
        SettingModel setting = getIntent().getParcelableExtra("setting");
        rewardVideo = new EasyADController(this).initReward(setting.toJson());
        rewardVideo.loadOnly();

    }

    public void onShow(View view) {
        if (rewardVideo != null) {
            rewardVideo.show();
        } else {
            EasyADController.logAndToast(this, "需要先调用loadOnly()");
        }
    }

    public void loadAndShow(View view) {
        // 获取配置
        SettingModel setting = getIntent().getParcelableExtra("setting");
        new EasyADController(this).initReward(setting.toJson()).loadAndShow();
    }
}
