package powerdms.forkspoon.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import powerdms.forkspoon.R;
import powerdms.forkspoon.api.IResponseObject;
import powerdms.forkspoon.api.task.RestaurantTask;
import powerdms.forkspoon.helper.geolocation.Geolocation;
import powerdms.forkspoon.helper.geolocation.GeolocationListener;
import powerdms.forkspoon.helper.util.LocationUtility;
import powerdms.forkspoon.model.common.geocode.GeocodeResp;
import powerdms.forkspoon.model.common.geocode.NearbyRestaurant;
import powerdms.forkspoon.view.activity.ActivityRestaurantDetails;
import powerdms.forkspoon.view.adapter.ConnectionAdapter;
import powerdms.forkspoon.view.adapter.EmptyAdapter;
import powerdms.forkspoon.view.adapter.NearbyRestaurantAdapter;

public class NearbyRestaurantFragment extends Fragment implements
        SwipeRefreshLayout.OnRefreshListener, IResponseObject, GeolocationListener {

    SearchView searchView;
    NearbyRestaurantAdapter adapter;
    RecyclerView recycler;
    SwipeRefreshLayout swipeRefreshLayout;
    boolean mRunning;
    View view;
    FloatingActionButton filter;
    Geolocation mGeolocation = null;
    Location mLocation = null;

    public static NearbyRestaurantFragment newInstance() {
        NearbyRestaurantFragment fragment = new NearbyRestaurantFragment();
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
        view = inflater.inflate(R.layout.view_main_containers, container, false);
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
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshList);
        filter = (FloatingActionButton) view.findViewById(R.id.fab);
        filter.setVisibility(View.GONE);
    }

    private void setupAdapter() {
        adapter = new NearbyRestaurantAdapter(getActivity(), recycler, (position, v) -> {
            int pos = adapter.getPosition(position);
            NearbyRestaurant restaurant = adapter.filtered_items.get(pos);
            view = v;
            swipeRefreshLayout.setRefreshing(true);
            openRestaurantDetail(restaurant);
        });
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void openRestaurantDetail(NearbyRestaurant restaurant) {

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

    private void gotoDetail(NearbyRestaurant restaurant) {
        swipeRefreshLayout.setRefreshing(false);
        Intent intent = new Intent(getActivity(), ActivityRestaurantDetails.class);
        Bundle b = new Bundle();
        Gson gSon = new Gson();
        b.putString(ActivityRestaurantDetails.EXTRA_OBJECT_RESTAURANT, gSon.toJson(restaurant));
        intent.putExtras(b);
        startActivity(intent);
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<NearbyRestaurant> restaurants) {
        if(isAdded()) {
            if (restaurants != null)
                if (restaurants.isEmpty()) {
                    recyclerView.setAdapter(new EmptyAdapter(getString(R.string.no_near_restaurants_found)));
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

    @Override
    public void onRefresh() {
        if (mLocation != null)
            setupRestaurants();
    }

    private void setupRestaurants() {
        if (!mRunning) {
            if (isOnline()) {
                mRunning = true;
                swipeRefreshLayout.setRefreshing(true);
                Map<String, String> params = new HashMap<>();
                params.put("lat", String.valueOf(mLocation.getLatitude()));
                params.put("lon", String.valueOf(mLocation.getLongitude()));
                new RestaurantTask(this).loadbyGeocode(params);
            } else {
                recycler.setAdapter(new ConnectionAdapter(getString(R.string.no_connection), (position, v) -> {
                    if (v.getId() == R.id.retry)
                        setupRestaurants();
                }));
                swipeRefreshLayout.setRefreshing(false);
            }
        }
    }

    @Override
    public void onResponse(Object object) {
        if (object instanceof GeocodeResp) {
            mRunning = false;
            GeocodeResp geoResp = (GeocodeResp) object;
            swipeRefreshLayout.setRefreshing(false);
            setupRecyclerView(recycler, calculateDistance(geoResp.getNearbyRestaurants()));
        } else if (object instanceof NearbyRestaurant) {
            gotoDetail((NearbyRestaurant) object);
        }
    }

    @Override
    public void onError(String message, Integer code) {
        mRunning = false;
        swipeRefreshLayout.setRefreshing(false);
        if (recycler != null) {
            List<NearbyRestaurant> restaurantList = new ArrayList<>();
            setupRecyclerView(recycler, restaurantList);
        }
    }

    public boolean isOnline() {
        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
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
        swipeRefreshLayout.setRefreshing(false);
    }

    private List<NearbyRestaurant> calculateDistance(List<NearbyRestaurant> restaurants) {
        if (mLocation != null && restaurants != null && restaurants.size() > 0) {
            for (int i = 0; i < restaurants.size(); i++) {
                LatLng myLocation = new LatLng(mLocation.getLatitude(), mLocation.getLongitude());
                LatLng poiLocation = new LatLng(Double.parseDouble(restaurants.get(i).getRestaurant().getLocation().getLatitude()), Double.parseDouble(restaurants.get(i).getRestaurant().getLocation().getLongitude()));
                int distance = LocationUtility.getDistance(myLocation, poiLocation);
                restaurants.get(i).getRestaurant().setDistance(distance);
            }
        }
        return restaurants;
    }

}
