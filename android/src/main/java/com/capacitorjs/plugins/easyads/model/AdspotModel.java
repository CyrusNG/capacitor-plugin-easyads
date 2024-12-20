package com.capacitorjs.plugins.easyads.model;

import android.os.Parcelable;

import com.getcapacitor.JSObject;

import java.util.List;

import auto.parcelgson.AutoParcelGson;

@AutoParcelGson
public abstract class AdspotModel extends BaseModel implements Parcelable {
  public abstract String tag();
  public abstract List<String> targets();

  public abstract OptionModel options();

  public static AdspotModel create(String tag, List<String> targets, OptionModel options) {
    return new AutoParcelGson_AdspotModel(tag, targets, options);
  }

  public static AdspotModel create(JSObject json) {
    return gson.fromJson(json.toString(), AdspotModel.class);
  }

}


