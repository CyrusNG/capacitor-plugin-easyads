package com.capacitorjs.plugins.easyads.model;

import android.os.Parcelable;

import com.getcapacitor.JSObject;

import auto.parcelgson.AutoParcelGson;

@AutoParcelGson
public abstract class OptionModel extends BaseModel implements Parcelable {
  public abstract Float width();
  public abstract Float height();
  public abstract Boolean singleActivity();

  public static OptionModel create(Float width, Float height) {
    return new AutoParcelGson_OptionModel(width, height, null);
  }

  public static OptionModel create(Boolean singleActivity) {
    return new AutoParcelGson_OptionModel(null, null, singleActivity);
  }

  public static OptionModel create(JSObject json) {
    return gson.fromJson(json.toString(), OptionModel.class);
  }
}
