package com.capacitorjs.plugins.easyads.model;

import android.os.Parcelable;

import com.getcapacitor.JSObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import auto.parcelgson.AutoParcelGson;

@AutoParcelGson
public abstract class OptionModel extends BaseModel implements Parcelable {
  public abstract int width();
  public abstract int height();
  public abstract boolean singleActivity();

  public static OptionModel create(int width, int height) {
    return new AutoParcelGson_OptionModel(width, height, false);
  }

  public static OptionModel create(Boolean singleActivity) {
    return new AutoParcelGson_OptionModel(0, 0, singleActivity);
  }

  public static OptionModel create(JSObject json) {
    return gson.fromJson(json.toString(), OptionModel.class);
  }
}
