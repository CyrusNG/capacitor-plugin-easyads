package com.capacitorjs.plugins.easyads.model;

import android.os.Parcelable;

import com.getcapacitor.JSObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import auto.parcelgson.AutoParcelGson;
import auto.parcelgson.gson.annotations.SerializedName;

@AutoParcelGson
public abstract class SettingModel extends BaseModel implements Parcelable {
  @SerializedName("rules")
  abstract List<RuleModel> rules();
  @SerializedName("suppliers")
  abstract List<SupplierModel> suppliers();

  public static SettingModel create(ConfigModel config, String adspotTag) {
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
    return new AutoParcelGson_SettingModel(rules, suppliers);
  }

  public static SettingModel create(JSObject json) {
    return gson.fromJson(json.toString(), SettingModel.class);
  }
}


