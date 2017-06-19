package powerdms.forkspoon.helper.log;

/**
 * Created by Ambruster on 13/06/2017.
 */

public interface Log {

    void error(String paramString1, String paramString2);

    void info(String paramString1, String paramString2);

    void warning(String paramString1, String paramString2);

    void wl(boolean paramBoolean);
}

