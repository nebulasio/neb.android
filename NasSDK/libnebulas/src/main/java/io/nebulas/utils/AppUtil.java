package io.nebulas.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

/**
 * App utility.
 */
public class AppUtil {

    /**
     * Check the intent's available.
     */
    public static boolean isInstalled(Context context, Intent intent) {
        return intent != null && intent.resolveActivity(context.getPackageManager()) != null;
    }

    /**
     * Open App Market and goto download app
     */
    public static void openAppMarket(Context context, String packageName, String defUrl) {
        try {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + packageName));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, "Can not open App Market", Toast.LENGTH_SHORT).show();

            openBrowser(context, defUrl);
        }
    }

    private static void openBrowser(Context context, String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
