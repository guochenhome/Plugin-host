package com.eyoun.app.android.host.bean

import android.os.Parcel
import android.os.Parcelable

/**
 * 下载在本地的 插件实体类
 * Created by guochen on 2018/5/15.
 */
data class RealPluginBean(var pluginBean: PluginBean,var localPath:String):Parcelable{
    constructor(parcel: Parcel) : this(
            parcel.readParcelable(PluginBean::class.java.classLoader),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeParcelable(pluginBean, flags)
        parcel.writeString(localPath)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<RealPluginBean> {
        override fun createFromParcel(parcel: Parcel): RealPluginBean {
            return RealPluginBean(parcel)
        }

        override fun newArray(size: Int): Array<RealPluginBean?> {
            return arrayOfNulls(size)
        }
    }

}