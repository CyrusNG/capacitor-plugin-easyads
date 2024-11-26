package com.capacitorjs.plugins.easyads.model;

import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.getcapacitor.JSObject;

import auto.parcelgson.AutoParcelGson;

@AutoParcelGson
public abstract class ResultModel extends BaseModel implements Parcelable {
  abstract String call();

  public static ResultModel create(String call) {
    return new AutoParcelGson_ResultModel(call);
  }

  public static ResultModel create(JSObject json) {
    return gson.fromJson(json.toString(), ResultModel.class);
  }
}
