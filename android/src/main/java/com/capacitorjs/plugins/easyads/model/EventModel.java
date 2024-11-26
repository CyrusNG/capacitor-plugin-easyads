package com.capacitorjs.plugins.easyads.model;

import android.os.Parcelable;

import com.getcapacitor.JSObject;

import auto.parcelgson.AutoParcelGson;

@AutoParcelGson
public abstract class EventModel extends BaseModel implements Parcelable {
  abstract String type();
  abstract String event();
  abstract String call();
  abstract String tag();
  abstract Object error();

  public static EventModel create(String type, String event, String call, String tag, Object error) {
    return new AutoParcelGson_EventModel(type, event, call, tag, error);
  }

  public static EventModel create(JSObject json) {
    return gson.fromJson(json.toString(), EventModel.class);
  }
}
