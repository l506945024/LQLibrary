package com.lq.lib.http.update;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.text.TextUtils;

import java.io.File;

/**
 * description:
 * app更新管理类
 *
 * @author:mick
 * @time:2018/10/19
 */
public class AppUpdateManager {

    private Context mContext;

    public AppUpdateManager(Context context) {
        mContext = context;
    }

    /**
     * 比较实用的升级版下载功能
     *
     * @param url      下载地址
     * @param title    文件名字
     * @param desc     文件路径
     * @param onlyWifi 是否仅在wifi下下载
     */
    private long updateApk(String url, String title, String desc, boolean onlyWifi) {
        if (TextUtils.isEmpty(url)) {
            try {
                throw new Exception("下载的url为空!");
            } catch (Exception e) {
                e.printStackTrace();
            }
            return 0;
        }

        DownloadManager downloadManager =
                (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);

        //以下两行代码可以让下载的apk文件被直接安装而不用使用Fileprovider,系统7.0或者以上才启动。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder localBuilder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(localBuilder.build());
        }

        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        // 仅允许在WIFI连接情况下下载
        if (onlyWifi) {
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
        }
        // 通知栏中将出现的内容
        request.setTitle(title);
        request.setDescription(desc);
        //7.0以上的系统适配
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            request.setRequiresDeviceIdle(false);
            request.setRequiresCharging(false);
        }
        //制定下载的文件类型为APK
        request.setMimeType("application/vnd.android.package-archive");
        // 下载过程和下载完成后通知栏有通知消息。
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE
                | DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);

        File file = new File(mContext.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), title + ".apk");
        if (file.exists()) {
            file.delete();
        }
        //使用系统默认的下载路径 此处为应用内 /android/data/packages ,所以兼容7.0
        request.setDestinationInExternalFilesDir(mContext, Environment.DIRECTORY_DOWNLOADS, title + ".apk");

        return downloadManager.enqueue(request);
    }

    /**
     * @param url
     * @param title
     * @param desc
     * @return
     */
    public long updateApkWifi(String url, String title, String desc) {
        return updateApk(url, title, desc, true);
    }

    /**
     * @param url
     * @param title
     * @param desc
     * @return
     */
    public long updateApk(String url, String title, String desc) {
        return updateApk(url, title, desc, false);
    }


    /**
     * 下载前先移除前一个任务，防止重复下载
     *
     * @param downloadId
     */
    public void clearCurrentTask(long downloadId) {
        DownloadManager dm = (DownloadManager) mContext.getSystemService(Context.DOWNLOAD_SERVICE);
        try {
            dm.remove(downloadId);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
        }
    }
}
