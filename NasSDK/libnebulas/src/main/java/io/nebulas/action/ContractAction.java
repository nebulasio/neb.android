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
            context.startActivity(intent);
        } catch (Exception e) {
            handleException(context);
        }
    }

    private static void handleException(Context context){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(String.format("market://details?id=%s", Constants.NAS_NANO_PACKAGE_NAME)));

        if (intent.resolveActivity(context.getPackageManager()) != null) {
            Toast.makeText(context, "没安装Nebulas智能数字钱包，请下载安装", Toast.LENGTH_SHORT).show();
            context.startActivity(intent);
        } else {
            intent.setData(Uri.parse(String.format("https://play.google.com/store/apps/details?id=%s",Constants.NAS_NANO_PACKAGE_NAME)));
            if (intent.resolveActivity(context.getPackageManager()) != null) {
                context.startActivity(intent);
            } else {
                Toast.makeText(context, "没安装应用市场或浏览器", Toast.LENGTH_SHORT).show();
            }
        }
    }


}

