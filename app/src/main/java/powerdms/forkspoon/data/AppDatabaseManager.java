package powerdms.forkspoon.data;

import android.content.Context;

public class AppDatabaseManager {

    private static AppDatabaseManager instance;

    private AppDatabaseHelper helper;

    private AppDatabaseManager(Context ctx) {
        helper = new AppDatabaseHelper(ctx);
    }

    public static void init(Context ctx) {
        if (null == instance) {
            instance = new AppDatabaseManager(ctx);
        }
    }

    public static AppDatabaseManager getInstance() {
        return instance;
    }

    public AppDatabaseHelper getHelper() {
        return helper;
    }

}