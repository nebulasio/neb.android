package io.nebulas.action;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

/**
 * Created by donald99 on 18/5/23.
 */

public class Action {

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
        }
    }
}

