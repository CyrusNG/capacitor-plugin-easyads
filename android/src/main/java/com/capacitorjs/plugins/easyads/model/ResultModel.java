package com.capacitorjs.plugins.easyads.model;

import android.os.Parcelable;

import com.getcapacitor.JSObject;

import auto.parcelgson.AutoParcelGson;

@AutoParcelGson
public abstract class ResultModel extends BaseModel implements Parcelable {
  abstract String code();
  abstract Object data();
  abstract Object error();

  public static ResultModel create(String code, Object data, Object error) {
    return new AutoParcelGson_ResultModel(code, data, error);
  }

  public static ResultModel create(JSObject json) {
    return gson.fromJson(json.toString(), ResultModel.class);
  }
}
