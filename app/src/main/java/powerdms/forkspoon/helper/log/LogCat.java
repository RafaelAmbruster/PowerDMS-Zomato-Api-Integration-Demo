package powerdms.forkspoon.helper.log;

/**
 * Created by Ambruster on 13/06/2017.
 */
import com.orhanobut.logger.Logger;

public class LogCat
        implements Log {

    public void error(String paramString1, String paramString2) {
        Logger.t(paramString1).e(paramString2);
    }

    public void info(String paramString1, String paramString2) {
        Logger.t(paramString1).d(paramString2);
    }

    public void warning(String paramString1, String paramString2) {
        Logger.t(paramString1).w(paramString2);
    }

    public void wl(boolean paramBoolean) {
    }

    public void json_format(String paramString1) {
        Logger.json(paramString1);
    }

    public void xml_format(String paramString1) {
        Logger.xml(paramString1);
    }
}

