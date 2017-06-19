package powerdms.forkspoon.view.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import powerdms.forkspoon.R;
import powerdms.forkspoon.helper.util.FontTypefaceUtils;
import powerdms.forkspoon.model.restaurant.search.Restaurant;

public class RestaurantInformationFragment extends Fragment implements View.OnClickListener {

    public static final String EXTRA_OBJECT_RESTAURANT = "Restaurant";
    private Restaurant restaurant;
    private View parent_view;
    private TextView restaurant_menu, restaurant_events, restaurant_photos;


    public static RestaurantInformationFragment newInstance(Restaurant restaurant) {
        RestaurantInformationFragment fragment = new RestaurantInformationFragment();
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
        parent_view = inflater.inflate(R.layout.view_information, container, false);

        getActivity().invalidateOptionsMenu();
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            if (bundle.containsKey(EXTRA_OBJECT_RESTAURANT)) {
                Gson gSon = new Gson();
                restaurant = gSon.fromJson(bundle.getString(EXTRA_OBJECT_RESTAURANT), new TypeToken<Restaurant>() {
                }.getType());
            }
        }

        setupAddress(parent_view);
        return parent_view;
    }

    protected void setupAddress(View parent_view) {
        getActivity().invalidateOptionsMenu();

        restaurant_menu = (TextView) parent_view.findViewById(R.id.restaurant_menu);
        restaurant_menu.setTypeface(FontTypefaceUtils.getRobotoCondensedRegular(getActivity()));

        restaurant_events = (TextView) parent_view.findViewById(R.id.restaurant_events);
        restaurant_events.setTypeface(FontTypefaceUtils.getRobotoCondensedRegular(getActivity()));

        restaurant_photos = (TextView) parent_view.findViewById(R.id.restaurant_photos);
        restaurant_photos.setTypeface(FontTypefaceUtils.getRobotoCondensedRegular(getActivity()));

        /**
         *
         * Binding
         *
         * **/

        restaurant_menu.setText(restaurant.getRestaurant().getMenuUrl());
        restaurant_events.setText(restaurant.getRestaurant().getEventsUrl());
        restaurant_photos.setText(restaurant.getRestaurant().getPhotosUrl());

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