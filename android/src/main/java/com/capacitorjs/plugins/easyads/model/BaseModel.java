package com.capacitorjs.plugins.easyads.model;

import android.os.Parcelable;

import com.getcapacitor.JSObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import auto.parcelgson.gson.AutoParcelGsonTypeAdapterFactory;

public class BaseModel {
  protected static final Gson gson = new GsonBuilder().registerTypeAdapterFactory(new AutoParcelGsonTypeAdapterFactory()).create();

  public String toJson() {
    return gson.toJson(this);
  }
}


