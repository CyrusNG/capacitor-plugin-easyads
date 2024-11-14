package com.capacitorjs.plugins.easyads;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.easyads.EasyAds;
import com.baidu.mobads.sdk.api.AdSettings;
import com.capacitorjs.plugins.easyads.custom.CustomActivity;
import com.capacitorjs.plugins.easyads.utils.BaseCallBack;
import com.capacitorjs.plugins.easyads.utils.Constant;
import com.capacitorjs.plugins.easyads.utils.NormalSetting;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.kwad.sdk.api.KsAdSDK;
import com.qq.e.comm.managers.status.SDKStatus;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {
    TextView version;
    CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        version = findViewById(R.id.tv_version);
        checkBox = findViewById(R.id.cb_log);
        checkBox.setChecked(NormalSetting.getInstance().showLogcat);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                NormalSetting.getInstance().showLogcat = isChecked;
            }
        });

        TextView title = findViewById(R.id.tv_title);
        title.setText("EasyAds-简单聚合 急速变现");

        version.setText("版本号：EasyAds聚合SDK：v" + EasyAds.getVersion() +
                "\n穿山甲：v" + TTAdSdk.getAdManager().getSDKVersion() +
                "    优量汇：v" + SDKStatus.getSDKVersion() +
                "    百度：v" + AdSettings.getSDKVersion() + "    快手：v" + KsAdSDK.getSDKVersion());

    }

    @TargetApi(Build.VERSION_CODES.M)
    private void checkAndRequestPermission() {
        List<String> lackedPermission = new ArrayList<String>();
        if (!(checkSelfPermission(Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.READ_PHONE_STATE);
        }

        if (!(checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)) {
            lackedPermission.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }

        // 缺少权限，进行申请
        if (lackedPermission.size() > 0) {
            // 请求所缺少的权限，在onRequestPermissionsResult中再看是否获得权限，如果获得权限就可以调用SDK，否则不要调用SDK。
            String[] requestPermissions = new String[lackedPermission.size()];
            lackedPermission.toArray(requestPermissions);
            requestPermissions(requestPermissions, 1024);
        }
    }


    public void onBanner(View view) {
        startActivity(new Intent(this, BannerActivity.class));
    }

    public void onSplash(View view) {
        Intent intent = new Intent(this, SplashActivity.class);
        startActivity(intent);
    }

    public void onNativeExpress(View view) {
        startActivity(new Intent(this, NativeExpressActivity.class));
    }

    public void onRewardVideo(View view) {
        startActivity(new Intent(this, RewardVideoActivity.class));
    }

    public void onNativeExpressRecyclerView(View view) {
        startActivity(new Intent(this, NativeExpressRecyclerViewActivity.class));
    }

    public void onInterstitial(View view) {
        startActivity(new Intent(this, InterstitialActivity.class));
    }

    public void onFullVideo(View view) {
        startActivity(new Intent(this, FullScreenVideoActivity.class));
    }


    public void onSplashDialog(View view) {
        SplashDialog dialog = new SplashDialog(this);
        dialog.show();
    }

    public void draw(View view) {
        startActivity(new Intent(this, DrawActivity.class));
    }

    public void customChannel(View view) {
        startActivity(new Intent(this, CustomActivity.class));
    }

}
