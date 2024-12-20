package com.capacitorjs.plugins.easyads.model;

import android.os.Parcelable;

import androidx.annotation.Nullable;

import com.easyads.model.EasyAdError;
import com.getcapacitor.JSObject;

import auto.parcelgson.AutoParcelGson;

@AutoParcelGson
public abstract class EventModel extends BaseModel implements Parcelable {
  public abstract String event();
  public abstract String adType();
  public abstract String adTag();
  public abstract String callId();
  @Nullable
  public abstract EasyAdError error();

  public static EventModel create(String event, String adType, String adTag, String callId, EasyAdError error) {
    return new AutoParcelGson_EventModel(event, adType, adTag, callId, error);
  }

  public static EventModel create(JSObject json) {
    return gson.fromJson(json.toString(), EventModel.class);
  }
}
