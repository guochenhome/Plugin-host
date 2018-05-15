package com.eyoun.app.android.host.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * 要下载的插件-实体类
 * Created by guochen on 2018/5/15.
 */

data class PluginBean(var url: String,var path:String) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(url)
        parcel.writeString(path)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PluginBean> {
        override fun createFromParcel(parcel: Parcel): PluginBean {
            return PluginBean(parcel)
        }

        override fun newArray(size: Int): Array<PluginBean?> {
            return arrayOfNulls(size)
        }
    }


}