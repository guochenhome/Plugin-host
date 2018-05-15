package com.eyoun.app.android.host.task

import android.content.Context
import android.util.Log
import android.webkit.DownloadListener
import com.eyoun.app.android.host.bean.PluginBean
import com.eyoun.app.android.host.bean.RealPluginBean
import com.liulishuo.filedownloader.BaseDownloadTask
import com.liulishuo.filedownloader.FileDownloadListener
import com.liulishuo.filedownloader.FileDownloader

/**
 * Created by guochen on 2018/5/15.
 */
class CreateDownloadTask constructor(context: Context, bean: PluginBean, var createDownloadListener: CreateDownloadListent) {


    var instance: CreateDownloadTask? = null

    fun getInstance(context: Context, bean: PluginBean, createDownloadListent: CreateDownloadListent): CreateDownloadTask {
        if (instance == null) {
            synchronized(CreateDownloadTask::class) {
                if (instance == null) {
                    instance = CreateDownloadTask(context, bean, createDownloadListent)
                }
            }
        }
        return instance!!
    }


    init {

        PluginDownLoad(context, bean, createDownloadListener)

    }

    fun PluginDownLoad(context: Context, bean: PluginBean, createDownloadListener: CreateDownloadListent) {

        FileDownloader.getImpl().create(bean.url)
                .setPath(bean.path)
                .setListener(object : FileDownloadListener() {
                    override fun warn(task: BaseDownloadTask?) {
                        Log.d("FileDownLoade", "下载队列正在等待中。。。。。。")
                    }

                    override fun completed(task: BaseDownloadTask?) {
                        Log.d("FileDownLoade", "下载完成：。。。。。")
                        if (createDownloadListener != null && task != null) {
                            createDownloadListener.createDownloadeTaskCompleted(RealPluginBean(bean, task.targetFilePath))
                        }
                    }

                    override fun pending(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                        /**
                         * 等待，已进入下载队列 返回函数：数据库中的soFarBytes 和 totalBytes
                         */
                        Log.d("FileDownLoade", "等待中：已经进入下载队列。。。。。soFarBytes=${soFarBytes}--totalBytes${totalBytes}")
                    }

                    override fun error(task: BaseDownloadTask?, e: Throwable?) {
                        Log.d("FileDownLoade", "下载出现错误：。。。。。${e.toString()}")
                    }

                    override fun progress(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {

                        /**
                         * 下载回调：返回函数：soFarBytes
                         */
                        Log.d("FileDownLoade", "下载中：已经进入下载队列。。。。。soFarBytes=${soFarBytes}--totalBytes${totalBytes}")
                    }

                    override fun paused(task: BaseDownloadTask?, soFarBytes: Int, totalBytes: Int) {
                        Log.d("FileDownLoade", "暂停下载：。。。。。soFarBytes=${soFarBytes}--totalBytes${totalBytes}")
                    }

                    override fun connected(task: BaseDownloadTask?, etag: String?, isContinue: Boolean, soFarBytes: Int, totalBytes: Int) {
                        super.connected(task, etag, isContinue, soFarBytes, totalBytes)
                        Log.d("FileDownLoade", "连接成功：。。。。。soFarBytes=${soFarBytes}--totalBytes${totalBytes}")

                    }

                    override fun blockComplete(task: BaseDownloadTask?) {
                        super.blockComplete(task)
                        Log.d("FileDownLoade", "完成下载：开始同步")
                    }

                    override fun retry(task: BaseDownloadTask?, ex: Throwable?, retryingTimes: Int, soFarBytes: Int) {
                        super.retry(task, ex, retryingTimes, soFarBytes)
                        Log.d("FileDownLoade", "重试连接下载：已经进入下载队列。。。。。soFarBytes=${soFarBytes}--retryingTimes${retryingTimes}")
                    }
                }).start()

    }

    /**
     * 下载回调监听
     * <p>
     * 通过次监听实现对下载的判断
     *     </p>
     */

    interface CreateDownloadListent {
        /*
         *下载成功回调
         */
        fun createDownloadeTaskCompleted(realPluginBean: RealPluginBean)
    }


}