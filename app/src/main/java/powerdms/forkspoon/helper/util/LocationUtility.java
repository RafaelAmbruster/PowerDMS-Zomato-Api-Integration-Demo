package powerdms.forkspoon.helper.util;

import android.content.Context;
import android.location.Location;
import android.location.LocationManager;
import com.google.android.gms.maps.model.LatLng;

import java.util.Locale;

import powerdms.forkspoon.R;


public class LocationUtility {
    public static int getDistance(LatLng l1, LatLng l2) {

        Location location1 = new Location(LocationManager.PASSIVE_PROVIDER);
        location1.setLatitude(l1.latitude);
        location1.setLongitude(l1.longitude);

        Location location2 = new Location(LocationManager.PASSIVE_PROVIDER);
        location2.setLatitude(l2.latitude);
        location2.setLongitude(l2.longitude);

        return (int) location1.distanceTo(location2);
    }

    public static String getDistanceString(double distance, boolean useMetricSystem, Context context) {
        String result;

        if (useMetricSystem) {
            if (distance < 1000.0d) {
                result = String.format(Locale.US, "%d " + context.getString(R.string.unit_meter), (int) distance);
            } else if (distance < 5000.0d) {
                result = String.format(Locale.US, "%.1f " + context.getString(R.string.unit_kilometer), distance / 1000);
            } else {
                result = String.format(Locale.US, "%d " + context.getString(R.string.unit_kilometer), (int) distance / 1000);
            }
        } else {
            double distanceMiles = distance * 0.000621371192;

            if (distanceMiles < 0.1d) {
                result = String.format(Locale.US, "%.2f " + context.getString(R.string.unit_mile), distanceMiles);
            } else if (distanceMiles < 10.0d) {
                result = String.format(Locale.US, "%.1f " + context.getString(R.string.unit_mile), distanceMiles);
            } else {
                result = String.format(Locale.US, "%d " + context.getString(R.string.unit_mile), (int) distanceMiles);
            }
        }

        return result;
    }

    public static boolean isMetricSystem() {
        Locale locale = Locale.getDefault();
        String countryCode = locale.getCountry();
        return (!"US".equals(countryCode) && !"LR".equals(countryCode) && !"MM".equals(countryCode));
    }

}
