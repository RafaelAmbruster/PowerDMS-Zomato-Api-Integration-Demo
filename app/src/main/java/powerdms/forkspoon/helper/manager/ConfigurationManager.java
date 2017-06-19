package powerdms.forkspoon.helper.manager;

import android.os.Environment;
import android.util.SparseArray;

import java.io.File;

import powerdms.forkspoon.DMSApplication;

/**
 * Created by Ambruster on 13/06/2017.
 */

public class ConfigurationManager {

    private static final String FOLDER_NAME = "/" + DMSApplication.TAG + "/";
    private SparseArray<String> paths = new SparseArray<>();
    private static ConfigurationManager singleton;

    private ConfigurationManager() {
        this.paths.put(1, Environment.getExternalStorageDirectory()
                .getAbsolutePath() + FOLDER_NAME);
        this.paths.put(2,
                "/data/data/powerdms.forkspoon/data/");
        this.paths.put(3, Environment.getExternalStorageDirectory()
                .getAbsolutePath() + FOLDER_NAME + "photos/");
        this.paths.put(6, Environment.getExternalStorageDirectory()
                .getAbsolutePath() + FOLDER_NAME + "logs/");
        this.paths.put(5, Environment.getExternalStorageDirectory()
                .getAbsolutePath() + FOLDER_NAME + "temp/");

    }

    public synchronized static ConfigurationManager getInstance() {
        if (singleton == null) {
            singleton = new ConfigurationManager();
        }
        return singleton;
    }

    public String getPath(int paramInt) {
        String str = this.paths.get(paramInt, "");
        if (str.length() > 0) {
            File localFile = new File(str);
            if (!localFile.exists())
                localFile.mkdirs();
        }
        new File(Environment.getExternalStorageDirectory().getAbsoluteFile(), str + FOLDER_NAME);
        return str;
    }

}
