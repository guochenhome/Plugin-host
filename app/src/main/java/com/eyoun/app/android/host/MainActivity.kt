package com.eyoun.app.android.host

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.qihoo360.replugin.RePlugin
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        intent = RePlugin.createIntent("com.eyoun.app.android.plugin","com.eyoun.app.android.plugin.MainActivity")
        RePlugin.startActivity(this,intent)

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
