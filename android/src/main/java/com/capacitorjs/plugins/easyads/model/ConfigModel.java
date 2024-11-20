package com.capacitorjs.plugins.easyads.model;

import android.os.Parcelable;

import com.getcapacitor.JSObject;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import auto.parcelgson.gson.AutoParcelGsonTypeAdapterFactory;
import auto.parcelgson.AutoParcelGson;
import auto.parcelgson.gson.annotations.SerializedName;

@AutoParcelGson
public abstract class ConfigModel implements Parcelable {
  abstract List<RuleModel> rules();
  abstract List<SupplierModel> suppliers();

  public static ConfigModel create(List<RuleModel> rules, List<SupplierModel> suppliers) {
    return new AutoParcelGson_ConfigModel(rules, suppliers);
  }

  public static ConfigModel fromJson(JSObject json) {
    Gson gson = new GsonBuilder().registerTypeAdapterFactory(new AutoParcelGsonTypeAdapterFactory()).create();
    return gson.fromJson(json.toString(), ConfigModel.class);
  }

  public static String toJson(ConfigModel model) {
    Gson gson = new GsonBuilder().registerTypeAdapterFactory(new AutoParcelGsonTypeAdapterFactory()).create();
    return gson.toJson(model);
  }
}

@AutoParcelGson
abstract class RuleModel implements Parcelable {
  abstract String tag();
  abstract List<Integer> sort();
  abstract Integer percent();

  public static RuleModel create(String tag, List<Integer> sort, Integer percent) {
    return new AutoParcelGson_RuleModel(tag, sort, percent);
  }

  public static RuleModel fromJson(JSObject json) {
    Gson gson = new GsonBuilder().registerTypeAdapterFactory(new AutoParcelGsonTypeAdapterFactory()).create();
    return gson.fromJson(json.toString(), RuleModel.class);
  }

  public static String toJson(RuleModel model) {
    Gson gson = new GsonBuilder().registerTypeAdapterFactory(new AutoParcelGsonTypeAdapterFactory()).create();
    return gson.toJson(model);
  }
}

@AutoParcelGson
abstract class SupplierModel implements Parcelable {
  abstract String tag();
  abstract String adspotId();
  abstract String appId();
  abstract Integer index();

  public static SupplierModel create(String tag, String adspotId, String appId, Integer index) {
    return new AutoParcelGson_SupplierModel(tag, adspotId, appId, index);
  }

  public static SupplierModel fromJson(JSObject json) {
    Gson gson = new GsonBuilder().registerTypeAdapterFactory(new AutoParcelGsonTypeAdapterFactory()).create();
    return gson.fromJson(json.toString(), SupplierModel.class);
  }

  public static String toJson(SupplierModel model) {
    Gson gson = new GsonBuilder().registerTypeAdapterFactory(new AutoParcelGsonTypeAdapterFactory()).create();
    return gson.toJson(model);
  }
}