package powerdms.forkspoon;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Base64;

import com.orhanobut.logger.Logger;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import powerdms.forkspoon.data.AppDatabaseHelper;
import powerdms.forkspoon.data.AppDatabaseManager;
import powerdms.forkspoon.helper.log.LogManager;

/**
 * Created by Ambruster on 6/14/2017.
 */

public class DMSApplication extends Application {

    private static final DMSApplication ourInstance = new DMSApplication();
    public static String TAG = "forkandspoon";

    static synchronized DMSApplication getInstance() {
        return ourInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.init(TAG).hideThreadInfo().setMethodCount(3).setMethodOffset(2);
        AppDatabaseManager.init(getApplicationContext());
    }

}
