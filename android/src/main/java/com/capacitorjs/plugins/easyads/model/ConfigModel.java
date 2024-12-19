package com.capacitorjs.plugins.easyads.model;

import android.os.Parcelable;

import com.getcapacitor.JSObject;

import java.util.List;

import auto.parcelgson.AutoParcelGson;
import auto.parcelgson.gson.annotations.SerializedName;

@AutoParcelGson
public abstract class ConfigModel extends BaseModel implements Parcelable {
  public abstract List<RuleModel> rules();
  public abstract List<AppModel> apps();
  public abstract List<AdspotModel> adspots();

  public static ConfigModel create(List<RuleModel> rules, List<AppModel> apps, List<AdspotModel> adspots) {
    return new AutoParcelGson_ConfigModel(rules, apps, adspots);
  }

  public static ConfigModel create(JSObject json) {
    return gson.fromJson(json.toString(), ConfigModel.class);
  }
}


