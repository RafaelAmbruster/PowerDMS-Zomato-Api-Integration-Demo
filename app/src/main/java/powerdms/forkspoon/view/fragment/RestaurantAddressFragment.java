package powerdms.forkspoon.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.BuildConfig;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import powerdms.forkspoon.R;
import powerdms.forkspoon.helper.util.FontTypefaceUtils;
import powerdms.forkspoon.model.restaurant.search.ZomatoRestaurant;

public class RestaurantAddressFragment extends Fragment implements View.OnClickListener {

    public static final String EXTRA_OBJECT_RESTAURANT = "Restaurant";
    private ZomatoRestaurant restaurant;
    private View parent_view;
    private TextView text_restaurant_address, text_restaurant_address_info;
    private Button btn_address;
    private ImageView imageView;

    public static RestaurantAddressFragment newInstance(ZomatoRestaurant restaurant) {
        RestaurantAddressFragment fragment = new RestaurantAddressFragment();
        Bundle args = new Bundle();
        Gson gSon = new Gson();
        args.putString(EXTRA_OBJECT_RESTAURANT, gSon.toJson(restaurant));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        parent_view = inflater.inflate(R.layout.view_address, container, false);

        getActivity().invalidateOptionsMenu();
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            if (bundle.containsKey(EXTRA_OBJECT_RESTAURANT)) {
                Gson gSon = new Gson();
                restaurant = gSon.fromJson(bundle.getString(EXTRA_OBJECT_RESTAURANT), new TypeToken<ZomatoRestaurant>() {
                }.getType());
            }
        }

        setupAddress(parent_view);
        return parent_view;
    }

    protected void setupAddress(View parent_view) {
        getActivity().invalidateOptionsMenu();

        text_restaurant_address = (TextView) parent_view.findViewById(R.id.text_restaurant_address);
        text_restaurant_address.setTypeface(FontTypefaceUtils.getRobotoCondensedRegular(getActivity()));

        text_restaurant_address_info = (TextView) parent_view.findViewById(R.id.text_restaurant_address_info);
        text_restaurant_address_info.setTypeface(FontTypefaceUtils.getRobotoCondensedRegular(getActivity()));

        imageView = (ImageView) parent_view.findViewById(R.id.map_image);
        btn_address = (Button) parent_view.findViewById(R.id.btn_address);


        /**
         *
         * Binding
         *
         * **/

        text_restaurant_address.setText(restaurant.getLocation() != null ? restaurant.getLocation().getAddress() : restaurant.getAddress());
        text_restaurant_address_info.setText(restaurant.getLocation() != null ? restaurant.getLocation().getLocality() : restaurant.getAddressInfo());

        Double lat = Double.parseDouble(restaurant.getLocation() != null ? restaurant.getLocation().getLatitude() : String.valueOf(restaurant.getLat()));
        Double lon = Double.parseDouble(restaurant.getLocation() != null ? restaurant.getLocation().getLongitude() : String.valueOf(restaurant.getLat()));

        String url = getStaticMapUrl(lat, lon, 14);

        Glide.with(getActivity()).load(url)
                .thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);

        btn_address.setOnClickListener(view -> {
                    Handler mainHandler = new Handler(getActivity().getMainLooper());
                    Runnable myRunnable = () -> startNavigateActivity(lat, lon);
                    mainHandler.post(myRunnable);
                }
        );
    }

    private void startNavigateActivity(double lat, double lon) {
        try {
            String uri = String.format("http://maps.google.com/maps?daddr=%s,%s", Double.toString(lat), Double.toString(lon));
            Intent intent = new Intent(android.content.Intent.ACTION_VIEW, Uri.parse(uri));
            getActivity().startActivity(intent);
        } catch (android.content.ActivityNotFoundException e) {
        }
    }

    private String getStaticMapUrl(double lat, double lon, int zoom) {
        TypedValue typedValue = new TypedValue();
        getActivity().getTheme().resolveAttribute(R.attr.colorAccent, typedValue, true);
        int markerColor = typedValue.data;
        String markerColorHex = String.format("0x%06x", (0xffffff & markerColor));

        StringBuilder builder = new StringBuilder();
        builder.append("https://maps.googleapis.com/maps/api/staticmap");
        builder.append("?key=");
        builder.append(getActivity().getString(R.string.google_maps_key));
        builder.append("&size=320x320");
        builder.append("&scale=2");
        builder.append("&maptype=roadmap");
        builder.append("&zoom=");
        builder.append(zoom);
        builder.append("&center=");
        builder.append(lat);
        builder.append(",");
        builder.append(lon);
        builder.append("&markers=color:");
        builder.append(markerColorHex);
        builder.append("%7C");
        builder.append(lat);
        builder.append(",");
        builder.append(lon);
        return builder.toString();
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }
}