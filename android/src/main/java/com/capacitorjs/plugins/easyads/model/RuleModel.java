package com.capacitorjs.plugins.easyads.model;

import android.os.Parcelable;

import com.getcapacitor.JSObject;

import java.util.List;

import auto.parcelgson.AutoParcelGson;

@AutoParcelGson
public abstract class RuleModel extends BaseModel implements Parcelable {
  public abstract String tag();
  public abstract List<Integer> sort();
  public abstract Integer percent();

  public static RuleModel create(String tag, List<Integer> sort, Integer percent) {
    return new AutoParcelGson_RuleModel(tag, sort, percent);
  }

  public static RuleModel create(JSObject json) {
    return gson.fromJson(json.toString(), RuleModel.class);
  }

}
