package com.capacitorjs.plugins.easyads.utils;

import com.capacitorjs.plugins.easyads.model.AdspotModel;
import com.capacitorjs.plugins.easyads.model.AppModel;
import com.capacitorjs.plugins.easyads.model.ConfigModel;
import com.capacitorjs.plugins.easyads.model.OptionModel;
import com.capacitorjs.plugins.easyads.model.RuleModel;
import com.capacitorjs.plugins.easyads.model.SettingModel;
import com.capacitorjs.plugins.easyads.model.SupplierModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModelConverter {

    static ModelConverter instance;

    public static ModelConverter getInstance() {
        if (instance == null) {
            instance = new ModelConverter();
        }
        return instance;
    }

    public static SettingModel convertSetting(ConfigModel config, String adspotTag) {
        // 获取所有相关models
        List<RuleModel> rules = config.rules();
        List<AppModel> apps = config.apps();
        List<AdspotModel> adspots = config.adspots();
        List<SupplierModel> suppliers = new ArrayList<>();
        // 遍历adspots找到adspotName一致项
        List<String> targets = Collections.emptyList();
        for (AdspotModel adspot : adspots) {
            if(adspotTag.equals(adspot.tag())) targets = adspot.targets();
        }
        // 遍历apps添加adspotId
        for (AppModel app : apps) {
            for (String target : targets) {
                String[] parts = target.split("-", 2);
                String appTag = parts[0];
                String appTarget = parts[1];
                if(app.tag().equals(appTag)) suppliers.add(SupplierModel.create(app, appTarget));
            }
        }
        // 返回settingModel
        return SettingModel.create(rules, suppliers);
    }

    public static OptionModel convertOption(ConfigModel config, String adspotTag) {
        // 获取所有相关models
        List<AdspotModel> adspots = config.adspots();
        OptionModel target = null;
        for (AdspotModel adspot : adspots) {
            if(adspotTag.equals(adspot.tag())) target = adspot.options();
        }
        return target;
    }
}
