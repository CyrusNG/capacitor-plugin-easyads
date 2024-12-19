package com.capacitorjs.plugins.easyads.model;

import android.os.Parcelable;

import com.getcapacitor.JSObject;

import auto.parcelgson.AutoParcelGson;

@AutoParcelGson
public abstract class SupplierModel extends BaseModel implements Parcelable {
  public abstract String tag();
  public abstract String appId();
  public abstract Integer index();
  public abstract String adspotId();

  public static SupplierModel create(String tag, String appId, Integer index, String adspotId) {
    return new AutoParcelGson_SupplierModel(tag, appId, index, adspotId);
  }

  public static SupplierModel create(AppModel supplierModel, String adspotId) {
    return new AutoParcelGson_SupplierModel(supplierModel.tag(), supplierModel.appId(), supplierModel.index(), adspotId);
  }

  public static SupplierModel create(JSObject json) {
    return gson.fromJson(json.toString(), SupplierModel.class);
  }
}
