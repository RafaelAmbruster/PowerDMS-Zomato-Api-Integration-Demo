package powerdms.forkspoon.helper.util;

/**
 * Created by Ambruster on 6/14/2017.
 */

import android.content.Context;
import android.graphics.Typeface;

import java.util.HashMap;

public class FontTypefaceUtils {

    private static HashMap<String, Typeface> typefaceHashMap = new HashMap<String, Typeface>();

    static Typeface getTypeface(Context context, String key) {
        if (!typefaceHashMap.containsKey(key))
            typefaceHashMap.put(key, Typeface.createFromAsset(context.getAssets(), key));
        return typefaceHashMap.get(key);
    }

    public static Typeface getRobotoCondensedRegular(Context context) {
        return getTypeface(context, "fonts/RobotoCondensed-Regular.ttf");
    }
}
