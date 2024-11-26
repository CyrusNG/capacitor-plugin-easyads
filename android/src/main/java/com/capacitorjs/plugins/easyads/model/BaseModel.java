package com.capacitorjs.plugins.easyads.model;

import com.getcapacitor.JSObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONException;
import org.json.JSONObject;

import auto.parcelgson.gson.AutoParcelGsonTypeAdapterFactory;

public class BaseModel {

  protected static final Gson gson = new GsonBuilder().registerTypeAdapterFactory(new AutoParcelGsonTypeAdapterFactory()).create();

  public String toJsonString() { return gson.toJson(this); }

  public JSONObject toJsonObject() {
    try {
      return new JSONObject(gson.toJson(this));
    } catch (JSONException e) {
      return null;
    }
  }

  public JSObject toJsObject() {
    try {
      return new JSObject(gson.toJson(this));
    } catch (JSONException e) {
      return null;
    }
  }
}


