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
import android.support.v7.app.AlertDialog;
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
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import powerdms.forkspoon.R;
import powerdms.forkspoon.api.IResponseObject;
import powerdms.forkspoon.api.task.CategoryTask;
import powerdms.forkspoon.api.task.CuisineTask;
import powerdms.forkspoon.api.task.EstablishmentTask;
import powerdms.forkspoon.api.task.RestaurantTask;
import powerdms.forkspoon.helper.geolocation.Geolocation;
import powerdms.forkspoon.helper.geolocation.GeolocationListener;
import powerdms.forkspoon.helper.util.LocationUtility;
import powerdms.forkspoon.model.common.category.CategoriesResp;
import powerdms.forkspoon.model.common.category.Category;
import powerdms.forkspoon.model.common.cuisine.Cuisine;
import powerdms.forkspoon.model.common.cuisine.CuisinesResp;
import powerdms.forkspoon.model.common.establishment.Establishment;
import powerdms.forkspoon.model.common.establishment.EstablishmentsResp;
import powerdms.forkspoon.model.restaurant.search.Restaurant;
import powerdms.forkspoon.model.restaurant.search.SearchResp;
import powerdms.forkspoon.view.activity.ActivityRestaurantDetails;
import powerdms.forkspoon.view.adapter.ConnectionAdapter;
import powerdms.forkspoon.view.adapter.EmptyAdapter;
import powerdms.forkspoon.view.adapter.RestaurantAdapter;

public class RestaurantFragment extends Fragment implements
        SwipeRefreshLayout.OnRefreshListener, IResponseObject, GeolocationListener {

    public static final String EXTRA_OBJECT_OPTION = "Option";
    public static final String EXTRA_OBJECT_CATEGORY = "Category";
    public static final String EXTRA_OBJECT_CUISINE = "Cuisine";
    public static final String EXTRA_OBJECT_ESTABLISHMENT = "Establishment";

    SearchView searchView;
    RestaurantAdapter adapter;
    RecyclerView recycler;
    SwipeRefreshLayout swipeRefreshLayout;
    FloatingActionButton filter;
    boolean mRunning;
    View view;

    Category category;
    Cuisine cuisine;
    Establishment establishment;

    List<Category> categories = new ArrayList<>();
    List<Cuisine> cuisines = new ArrayList<>();
    List<Establishment> establishments = new ArrayList<>();

    Geolocation mGeolocation = null;
    Location mLocation = null;

    int option = 1;

    public static RestaurantFragment newInstance(int option) {
        RestaurantFragment fragment = new RestaurantFragment();
        Bundle args = new Bundle();
        args.putInt(EXTRA_OBJECT_OPTION, option);
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

        Bundle bundle = this.getArguments();
        if (bundle != null) {

            if (bundle.containsKey(EXTRA_OBJECT_CATEGORY)) {
                Gson gSon = new Gson();
                category = gSon.fromJson(bundle.getString(EXTRA_OBJECT_CATEGORY), new TypeToken<Category>() {
                }.getType());
                option = 1;
            }

            if (bundle.containsKey(EXTRA_OBJECT_CUISINE)) {
                Gson gSon = new Gson();
                cuisine = gSon.fromJson(bundle.getString(EXTRA_OBJECT_CUISINE), new TypeToken<Cuisine>() {
                }.getType());
                option = 2;
            }

            if (bundle.containsKey(EXTRA_OBJECT_ESTABLISHMENT)) {
                Gson gSon = new Gson();
                establishment = gSon.fromJson(bundle.getString(EXTRA_OBJECT_ESTABLISHMENT), new TypeToken<Establishment>() {
                }.getType());
                option = 3;
            }

            if (bundle.containsKey(EXTRA_OBJECT_OPTION)) {
                option = bundle.getInt(EXTRA_OBJECT_OPTION, 1);
            }
        }


        setupView(view);
        setupAdapter();
        initGeolocation();

        return view;
    }

    private void loadFilters(Location location) {
        Map<String, String> params;
        switch (option) {
            case 1:
                new CategoryTask(this).getCategories();
                break;
            case 2:
                params = new HashMap<>();
                params.put("lat", String.valueOf(location.getLatitude()));
                params.put("lon", String.valueOf(location.getLongitude()));
                new CuisineTask(this).getCuisines(params);
                break;
            case 3:
                params = new HashMap<>();
                params.put("lat", String.valueOf(location.getLatitude()));
                params.put("lon", String.valueOf(location.getLongitude()));
                new EstablishmentTask(this).getEstablishments(params);
                break;
            default:
                filter.setVisibility(View.GONE);
                break;
        }
    }

    private void initGeolocation() {
        mGeolocation = null;
        mGeolocation = new Geolocation((LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE), this, getActivity());
    }

    private void setupView(View view) {
        recycler = (RecyclerView) view.findViewById(R.id.item_list);
        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.refreshList);
        filter = (FloatingActionButton) view.findViewById(R.id.fab);

        filter.setOnClickListener(v ->
                openFilterDialog()
        );
    }

    private void setupAdapter() {
        adapter = new RestaurantAdapter(getActivity(), recycler, (position, v) -> {
            int pos = adapter.getPosition(position);
            Restaurant restaurant = adapter.filtered_items.get(pos);
            view = v;
            swipeRefreshLayout.setRefreshing(true);
            openRestaurantDetail(restaurant);
        });
        swipeRefreshLayout.setOnRefreshListener(this);
    }

    private void openRestaurantDetail(Restaurant restaurant) {

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

    private void gotoDetail(Restaurant restaurant) {
        swipeRefreshLayout.setRefreshing(false);
        Intent intent = new Intent(getActivity(), ActivityRestaurantDetails.class);
        Bundle b = new Bundle();
        Gson gSon = new Gson();
        b.putString(ActivityRestaurantDetails.EXTRA_OBJECT_RESTAURANT, gSon.toJson(restaurant));
        intent.putExtras(b);
        startActivity(intent);
    }

    private void setupRecyclerView(RecyclerView recyclerView, List<Restaurant> restaurants) {
        if (isAdded())
            if (restaurants != null)
                if (restaurants.isEmpty()) {
                    recyclerView.setAdapter(new EmptyAdapter(getString(R.string.no_restaurants_found)));
                    adapter.notifyDataSetChanged();
                } else {
                    recyclerView.setAdapter(adapter);
                    adapter.AddItems(restaurants);
                    adapter.notifyDataSetChanged();
                }
    }

    @Override
    public void onCreateOptionsMenu(final Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
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
        if (category != null)
            if (mLocation != null)
                setupRestaurants();
    }

    private void setupRestaurants() {
        if (!mRunning) {
            if (isOnline()) {
                mRunning = true;
                swipeRefreshLayout.setRefreshing(true);
                Map<String, String> params = new HashMap<>();

                if (category != null)
                    params.put("category", category.getCategories().getId() + "");

                if (cuisine != null)
                    params.put("cuisines", cuisine.getCuisine().getCuisineId() + "");

                if (establishment != null)
                    params.put("establishment_type", establishment.getEstablishment().getId() + "");

                params.put("lat", String.valueOf(mLocation.getLatitude()));
                params.put("lon", String.valueOf(mLocation.getLongitude()));
                new RestaurantTask(this).Search(params);
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
        if (object instanceof SearchResp) {
            mRunning = false;
            SearchResp searchResp = (SearchResp) object;
            swipeRefreshLayout.setRefreshing(false);
            setupRecyclerView(recycler, calculateDistance(searchResp.getRestaurants()));
        }

        if (object instanceof Restaurant) {
            gotoDetail((Restaurant) object);
        }

        if (object instanceof CategoriesResp) {
            categories.addAll(((CategoriesResp) object).getCategories());
        }

        if (object instanceof CuisinesResp) {
            cuisines.addAll(((CuisinesResp) object).getCuisines());
        }

        if (object instanceof EstablishmentsResp) {
            establishments.addAll(((EstablishmentsResp) object).getEstablishments());
        }
    }

    @Override
    public void onError(String message, Integer code) {
        mRunning = false;
        swipeRefreshLayout.setRefreshing(false);
        if (recycler != null) {
            List<Restaurant> restaurantList = new ArrayList<>();
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
        loadFilters(location);
    }

    @Override
    public void onGeolocationFail(Geolocation geolocation) {
        swipeRefreshLayout.setRefreshing(false);
    }

    private List<Restaurant> calculateDistance(List<Restaurant> restaurants) {
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

    private void openFilterDialog() {
        CharSequence[] items = new CharSequence[0];
        switch (option) {
            case 1:
                if (categories != null && !categories.isEmpty()) {
                    items = new CharSequence[categories.size()];
                    for (int i = 0; i < categories.size(); i++) {
                        items[i] = categories.get(i).getCategories().getName();
                    }
                }
                break;
            case 2:
                if (cuisines != null && !cuisines.isEmpty()) {
                    items = new CharSequence[cuisines.size()];
                    for (int i = 0; i < cuisines.size(); i++) {
                        items[i] = cuisines.get(i).getCuisine().getCuisineName();
                    }
                }
                break;
            case 3:
                if (establishments != null && !establishments.isEmpty()) {
                    items = new CharSequence[establishments.size()];
                    for (int i = 0; i < establishments.size(); i++) {
                        items[i] = establishments.get(i).getEstablishment().getName();
                    }
                }
                break;
            default:
                break;
        }

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
                .setTitle(option == 1 ? "Filter by Category " : option == 2 ? "Filter by Cuisine " : " Filter by Establishments")
                .setSingleChoiceItems(items, 0, (dialog1, which) -> {
                    switch (option) {
                        case 1:
                            category = categories.get(which);
                            setupRestaurants();
                            cuisine = null;
                            establishment = null;
                            break;
                        case 2:
                            cuisine = cuisines.get(which);
                            category = null;
                            establishment = null;
                            setupRestaurants();
                            break;
                        case 3:
                            establishment = establishments.get(which);
                            category = null;
                            cuisine = null;
                            setupRestaurants();
                            break;
                    }
                }).setPositiveButton("Filter", (dialog12, which) -> dialog12.dismiss())
                .setNegativeButton("Cancel", (dialog13, which) -> dialog13.dismiss())
                .create();
        dialog.show();
    }
}
