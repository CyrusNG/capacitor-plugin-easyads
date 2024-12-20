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
  public abstract List<RuleModel> rules();
  @SerializedName("suppliers")
  public abstract List<SupplierModel> suppliers();

  public static SettingModel create(List<RuleModel> rules, List<SupplierModel> suppliers) {
    return new AutoParcelGson_SettingModel(rules, suppliers);
  }

  public static SettingModel create(JSObject json) {
    return gson.fromJson(json.toString(), SettingModel.class);
  }
}


