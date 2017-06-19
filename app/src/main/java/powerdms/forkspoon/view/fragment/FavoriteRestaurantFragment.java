package powerdms.forkspoon.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import java.util.List;
import powerdms.forkspoon.R;
import powerdms.forkspoon.data.AppDatabaseManager;
import powerdms.forkspoon.data.dao.RestaurantDAO;
import powerdms.forkspoon.helper.geolocation.Geolocation;
import powerdms.forkspoon.helper.geolocation.GeolocationListener;
import powerdms.forkspoon.helper.util.LocationUtility;
import powerdms.forkspoon.model.restaurant.search.ZomatoRestaurant;
import powerdms.forkspoon.view.activity.ActivityRestaurantDetails;
import powerdms.forkspoon.view.adapter.EmptyAdapter;
import powerdms.forkspoon.view.adapter.FavoriteRestaurantAdapter;

public class FavoriteRestaurantFragment extends Fragment implements GeolocationListener {

    SearchView searchView;
    FavoriteRestaurantAdapter adapter;
    RecyclerView recycler;
    View view;
    FloatingActionButton filter;
    Geolocation mGeolocation = null;
    Location mLocation = null;

    public static FavoriteRestaurantFragment newInstance() {
        FavoriteRestaurantFragment fragment = new FavoriteRestaurantFragment();
        Bundle args = new Bundle();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.view_main_containers_ws, container, false);
        getActivity().invalidateOptionsMenu();

        setupView(view);
        setupAdapter();
        initGeolocation();
        return view;
    }

    private void initGeolocation() {
        mGeolocation = null;
        mGeolocation = new Geolocation((LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE), this, getActivity());
    }

    private void setupView(View view) {
        recycler = (RecyclerView) view.findViewById(R.id.item_list);
        filter = (FloatingActionButton) view.findViewById(R.id.fab);
        filter.setVisibility(View.GONE);
    }

    private void setupAdapter() {
        adapter = new FavoriteRestaurantAdapter(getActivity(), recycler, (position, v) -> {
            int pos = adapter.getPosition(position);
            ZomatoRestaurant restaurant = adapter.filtered_items.get(pos);
            view = v;

            openRestaurantDetail(restaurant);
        });
    }

    private void openRestaurantDetail(ZomatoRestaurant restaurant) {

        /**
         * This is a bad service response, because i need to be partner to access photos and reviews
         * so in this case, i
         * will send the restaurant selected to the detail activity otherwise i only need to call the commented code below
         * **/

        //Map<String, String> params = new HashMap<>();
        //params.put("res_id", restaurant.getRestaurant().getId().toString());
        //new RestaurantTask(this).loadRestaurant(params);

        gotoDetail(restaurant);
    }

    private void gotoDetail(ZomatoRestaurant restaurant) {
        Intent intent = new Intent(getActivity(), ActivityRestaurantDetails.class);
        Bundle b = new Bundle();
        Gson gSon = new Gson();
        b.putString(ActivityRestaurantDetails.EXTRA_OBJECT_RESTAURANT_ZM, gSon.toJson(restaurant));
        intent.putExtras(b);
        startActivity(intent);
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<ZomatoRestaurant> restaurants) {
        if (isAdded()) {
            if (restaurants != null)
                if (restaurants.isEmpty()) {
                    recyclerView.setAdapter(new EmptyAdapter(getString(R.string.no_favorite_restaurants_found)));
                    adapter.notifyDataSetChanged();
                } else {
                    recyclerView.setAdapter(adapter);
                    adapter.AddItems(restaurants);
                    adapter.notifyDataSetChanged();
                }
        }
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main, menu);
        final MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setIconified(false);
        searchView.setQueryHint("Search...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                adapter.notifyDataSetChanged();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                adapter.getFilter().filter(s);
                adapter.notifyDataSetChanged();
                return true;
            }
        });

        searchView.setOnSearchClickListener(v -> setItemsVisibility(menu, searchItem, false));

        searchView.setOnCloseListener(() -> {
            setItemsVisibility(menu, searchItem, true);
            adapter.notifyDataSetChanged();
            return false;
        });
        searchView.onActionViewCollapsed();

        super.onCreateOptionsMenu(menu, inflater);
    }

    private void setItemsVisibility(Menu menu, MenuItem exception, boolean visible) {
        for (int i = 0; i < menu.size(); ++i) {
            MenuItem item = menu.getItem(i);
            if (item != exception) item.setVisible(visible);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setupRestaurants() {
        setupRecyclerView(recycler, calculateDistance(new RestaurantDAO(AppDatabaseManager.getInstance().getHelper()).GetAll()));
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onGeolocationRespond(Geolocation geolocation, Location location) {
        mLocation = location;
        setupRestaurants();
    }

    @Override
    public void onGeolocationFail(Geolocation geolocation) {
    }

    private List<ZomatoRestaurant> calculateDistance(List<ZomatoRestaurant> restaurants) {
        if (mLocation != null && restaurants != null && restaurants.size() > 0) {
            for (int i = 0; i < restaurants.size(); i++) {
                LatLng myLocation = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
                LatLng poiLocation = new LatLng(restaurants.get(i).getLat(), restaurants.get(i).getLon());
                int distance = LocationUtility.getDistance(myLocation, poiLocation);
                restaurants.get(i).setDistance(distance);
            }
        }
        return restaurants;
    }

}
