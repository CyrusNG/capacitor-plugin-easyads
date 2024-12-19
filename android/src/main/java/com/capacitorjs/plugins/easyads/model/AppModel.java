package com.capacitorjs.plugins.easyads.model;

import android.os.Parcelable;

import com.getcapacitor.JSObject;

import auto.parcelgson.AutoParcelGson;

@AutoParcelGson
public abstract class AppModel extends BaseModel implements Parcelable {
  public abstract String tag();
  public abstract String appId();
  public abstract Integer index();

  public static AppModel create(String tag, String appId, Integer index) {
    return new AutoParcelGson_AppModel(tag, appId, index);
  }

  public static AppModel create(JSObject json) {
    return gson.fromJson(json.toString(), AppModel.class);
  }
}
