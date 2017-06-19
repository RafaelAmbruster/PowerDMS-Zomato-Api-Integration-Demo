package powerdms.forkspoon.helper.geolocation;

import android.location.Location;

/**
 * Created by Ambruster on 13/06/2017.
 */

public interface GeolocationListener {
     void onGeolocationRespond(Geolocation geolocation, Location location);

     void onGeolocationFail(Geolocation geolocation);
}
