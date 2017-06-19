package powerdms.forkspoon.helper.util;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.ConfigurationInfo;

public class Tools {

    public static boolean isSupportedOpenGlEs2(Context context) {
        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
        boolean supportedOpenGlEs2 = configurationInfo.reqGlEsVersion >= 0x20000;
        return supportedOpenGlEs2;
    }

}
