package com.eyoun.app.android.host

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.eyoun.app.android.host.bean.PluginBean
import com.eyoun.app.android.host.bean.RealPluginBean
import com.eyoun.app.android.host.task.CreateDownloadTask
import com.liulishuo.filedownloader.util.FileDownloadUtils
import com.qihoo360.replugin.RePlugin
import com.qihoo360.replugin.model.PluginInfo
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity(), CreateDownloadTask.CreateDownloadListent {

    var context:Context?=null

    var path:String?=null

    var pluginUrl="http://upai.yxvzb.com/APK/kln/plugin/demo/debug/app-release.apk"


    override fun createDownloadeTaskCompleted(realPluginBean: RealPluginBean) {
        wz_plugin_btn.setText("插件下载成功")

        Log.d("FileDownLoade", "下载回调：${realPluginBean.localPath + "\n" + realPluginBean.pluginBean.path + "\n" + realPluginBean.pluginBean.url}")

        var info=RePlugin.install(realPluginBean.localPath)

        if(info!=null) {
            RePlugin.startActivity(context,RePlugin.createIntent(info.name,"com.eyoun.app.android.online_plugin.MainActivity"))
        }else{
            Toast.makeText(context,"--",Toast.LENGTH_SHORT).show()
        }


    }





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context=this

        path=FileDownloadUtils.getDefaultSaveRootPath()+File.separator+"app-release.apk"



        nz_plugin_btn.setOnClickListener(View.OnClickListener {
            intent = RePlugin.createIntent("com.eyoun.app.android.plugin", "com.eyoun.app.android.plugin.MainActivity")
            RePlugin.startActivity(this, intent)
        })

        wz_plugin_btn.setOnClickListener(View.OnClickListener {

            var plugin=PluginBean(pluginUrl,path!!)

            CreateDownloadTask(context as Context,plugin,this)
        })

    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
//    external fun stringFromJNI(): String
//
//    companion object {
//
//        // Used to load the 'native-lib' library on application startup.
//        init {
//            System.loadLibrary("native-lib")
//        }
//    }
}
