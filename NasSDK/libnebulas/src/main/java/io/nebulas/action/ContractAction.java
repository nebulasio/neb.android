package io.nebulas.action;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;

import io.nebulas.Constants;
import io.nebulas.utils.AppUtil;

/**
 * Created by donald99 on 18/5/23.
 */

public class ContractAction {

    /**
     * Schema 方式启动星云钱包
     *
     * @param context 上下文
     * @param url     schema
     */
    public static void start(Context context, String url) {
        if (context == null || TextUtils.isEmpty(url)) {
            return;
        }
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            if (AppUtil.isInstalled(context, intent)) {
                context.startActivity(intent);
            } else {
                AppUtil.openAppMarket(context, Constants.NAS_NANO_WALLET_PKG, Constants.NAS_NANO_WALLET_DOWNLOAD_URL);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

