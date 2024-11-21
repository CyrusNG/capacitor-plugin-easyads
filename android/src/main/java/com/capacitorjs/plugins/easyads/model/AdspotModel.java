package com.capacitorjs.plugins.easyads.model;

import android.os.Parcelable;

import com.getcapacitor.JSObject;

import java.util.List;

import auto.parcelgson.AutoParcelGson;

@AutoParcelGson
public abstract class AdspotModel extends BaseModel implements Parcelable {
  abstract String tag();
  abstract List<String> targets();

  public static AdspotModel create(String tag, List<String> targets) {
    return new AutoParcelGson_AdspotModel(tag, targets);
  }

  public static AdspotModel create(JSObject json) {
    return gson.fromJson(json.toString(), AdspotModel.class);
  }

}


