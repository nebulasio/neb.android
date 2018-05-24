package io.nebulas.okhttp;

import okhttp3.OkHttpClient;

/**
 * Created by donald99 on 18/5/24.
 */

public class OkHttpManager {

    private static OkHttpClient okHttpClient;
    private static OkHttpManager okHttpManager;

    private OkHttpManager() {
        okHttpClient = new OkHttpClient();
    }

    public static OkHttpManager getInstance() {
        synchronized (OkHttpManager.class) {
            if (okHttpManager == null) {
                okHttpManager = new OkHttpManager();
                return okHttpManager;
            }
        }
        return okHttpManager;
    }

    public OkHttpClient getOkHttpClient(){
        if (okHttpClient == null) {
            getInstance();
        }
        return okHttpClient;
    }
}
