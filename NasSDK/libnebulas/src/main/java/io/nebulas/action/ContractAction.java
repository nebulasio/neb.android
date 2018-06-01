package io.nebulas.action;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.widget.Toast;

import io.nebulas.Constants;

/**
 * Created by donald99 on 18/5/23.
 */

public class ContractAction {

    /**
     * Schema 方式启动星云钱包
     * @param context 上下文
     * @param url     schema
     */
    public static void start(Context context, String url) {
        if (context == null || TextUtils.isEmpty(url)) {
            return;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            handleException(context);
        }
    }

    private static void handleException(Context context){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Toast.makeText(context, "没安装Nebulas智能数字钱包，正在前往官网下载...", Toast.LENGTH_LONG).show();
        intent.setData(Uri.parse(String.format(Constants.NAS_NANO_DOWNLOAD_URL)));
        if (intent.resolveActivity(context.getPackageManager()) != null) {
            context.startActivity(intent);
        } else {
            Toast.makeText(context, "没安装应用市场或浏览器，下载失败", Toast.LENGTH_SHORT).show();
        }
    }


}

