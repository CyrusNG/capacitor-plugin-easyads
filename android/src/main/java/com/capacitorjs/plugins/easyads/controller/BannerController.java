package com.capacitorjs.plugins.easyads.controller;

import android.annotation.SuppressLint;
import android.app.Activity;

import androidx.annotation.NonNull;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.capacitorjs.plugins.easyads.R;
import com.capacitorjs.plugins.easyads.model.OptionModel;
import com.capacitorjs.plugins.easyads.model.SettingModel;
import com.capacitorjs.plugins.easyads.utils.AdCallback;
import com.easyads.core.banner.EABannerListener;
import com.easyads.core.banner.EasyAdBanner;
import com.easyads.model.EasyAdError;
import com.easyads.utils.ScreenUtil;


@SuppressLint("ViewConstructor")
public class BannerController extends RelativeLayout implements BaseController {
    Activity context;
    AdCallback pluginCallback;
    SettingModel setting;
    OptionModel option;
    ViewGroup appRootViewGroup;

    private static final String TAG = BannerController.class.getSimpleName();

    public BannerController(@NonNull final Activity context, AdCallback pluginCallback, SettingModel setting, OptionModel option) {
        super(context);
        //保存当前activity
        this.context = context;
        //保存插件回调
        this.pluginCallback = pluginCallback;
        //保存当前setting
        this.setting = setting;
        //保存当前option
        this.option = option;
        //加载layout
        inflate(getContext(), R.layout.adspot_banner, this);
        //获取当前activity的根ViewGroup
        this.appRootViewGroup = (ViewGroup) ((ViewGroup) this.context.findViewById(android.R.id.content)).getChildAt(0);
    }

    @Override
    public void load() {
        //先销毁广告（如有）
        this.destroy();
        //找到banner_layout
        RelativeLayout adContainer = this.findViewById(R.id.banner_container);
        //初始化广告实例
        EasyAdBanner easyAdBanner = new EasyAdBanner(this.context, adContainer, this.createListeners());
        //如果集成穿山甲，这里必须配置，建议尺寸要和穿山甲后台中的"代码位尺寸"宽高比例一致，值单位为dp，这里示例使用的广告位宽高比为640：100。
        int adWidth = ScreenUtil.px2dip(this.context, ScreenUtil.getScreenWidth(this.context));
        int adHeight = (int) (((double) adWidth / (double) 320) * 50);
        //如果高度传入0代表自适应高度
        easyAdBanner.setCsjExpressSize(adWidth, adHeight);
        //必须：设置策略信息
        easyAdBanner.setData(this.setting.toJsonString());
        //必须：请求并展示广告
        easyAdBanner.loadAndShow();
        //展示提示
        Log.d(TAG, "广告请求中");
    }

    @Override
    public void onViewAdded(View child) {
        super.onViewAdded(child);
        underlapStatusBar();
    }

    @Override
    public void destroy() {
        //删除view
        if (this.appRootViewGroup != null) this.appRootViewGroup.removeView(this);
    }

    private void underlapStatusBar() {
        //获取状态栏高度
        Rect rectangle = new Rect();
        this.context.getWindow().getDecorView().getWindowVisibleDisplayFrame(rectangle);
        int statusBarHeight = rectangle.top;
        //// 另一种获取状态栏高度的方法
        //int statusBarHeight = 0;
        //final Resources resources = context.getResources();
        //final int resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
        //if (resourceId > 0) { statusBarHeight = resources.getDimensionPixelSize(resourceId); }
        //else { statusBarHeight = (int) Math.ceil((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M ? 24 : 25) * resources.getDisplayMetrics().density); }
        //设置Padding避免状态栏重叠
        LayoutParams linearParams = new LayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        linearParams.setMargins(0, statusBarHeight, 0, 0);
        this.setLayoutParams(linearParams);
    }


    // MARK: ======================= Banner Listeners =======================
    private EABannerListener createListeners() {
        BannerController self = this;
        //必须：核心事件监听回调
        return new EABannerListener() {

            @Override
            public void onAdFailed(EasyAdError error) {
                Log.d(TAG, "广告加载失败 code=" + error.code + " msg=" + error.msg);
                if(self.pluginCallback != null) self.pluginCallback.fail(error);
            }

            @Override
            public void onAdSucceed() {
                Log.d(TAG, "广告加载成功");
                appRootViewGroup.addView(self);
                if(self.pluginCallback != null) self.pluginCallback.ready();
            }

            @Override
            public void onAdExposure() {
                Log.d(TAG, "广告展现");
                if(self.pluginCallback != null) self.pluginCallback.start();
            }

            @Override
            public void onAdClose() {
                Log.d(TAG, "广告关闭");
                if(self.pluginCallback != null) self.pluginCallback.end();
            }


            @Override
            public void onAdClicked() {
                Log.d(TAG, "广告点击");
                if(self.pluginCallback != null) self.pluginCallback.didClick();
            }
        };
    }

}
