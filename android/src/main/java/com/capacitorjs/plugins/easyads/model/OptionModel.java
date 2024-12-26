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
  public abstract boolean showLogo();
  public abstract boolean showLater();

  public static OptionModel create(int width, int height, Boolean showLogo, Boolean showLater) {
    return new AutoParcelGson_OptionModel(width, height, showLogo, showLater);
  }

  public static OptionModel create(JSObject json) {
    return gson.fromJson(json.toString(), OptionModel.class);
  }
}
