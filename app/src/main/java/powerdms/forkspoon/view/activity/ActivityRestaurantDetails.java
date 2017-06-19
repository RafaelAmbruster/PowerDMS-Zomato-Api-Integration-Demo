package powerdms.forkspoon.view.activity;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import powerdms.forkspoon.R;
import powerdms.forkspoon.data.AppDatabaseManager;
import powerdms.forkspoon.data.dao.IOperationDAO;
import powerdms.forkspoon.data.dao.RestaurantDAO;
import powerdms.forkspoon.helper.util.FontTypefaceUtils;
import powerdms.forkspoon.model.restaurant.search.Restaurant;
import powerdms.forkspoon.model.restaurant.search.ZomatoRestaurant;
import powerdms.forkspoon.view.adapter.RestaurantPagerAdapter;
import powerdms.forkspoon.view.fragment.RestaurantAddressFragment;
import ss.com.bannerslider.views.BannerSlider;
import ss.com.bannerslider.views.indicators.IndicatorShape;

public class ActivityRestaurantDetails extends AppCompatActivity {

    public static final String EXTRA_OBJECT_RESTAURANT = "Restaurant";
    public static final String EXTRA_OBJECT_RESTAURANT_ZM = "RestaurantZM";
    ViewPager mViewPager;
    RestaurantAddressFragment frag_address;
    Restaurant restaurant;
    ZomatoRestaurant zomatoRestaurant;
    FloatingActionButton fab;
    ViewGroup containerProgress;
    boolean isRunning = false;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            if (bundle.containsKey(EXTRA_OBJECT_RESTAURANT)) {
                Gson gSon = new Gson();
                restaurant = gSon.fromJson(bundle.getString(EXTRA_OBJECT_RESTAURANT), new TypeToken<Restaurant>() {
                }.getType());
            }

            if (bundle.containsKey(EXTRA_OBJECT_RESTAURANT_ZM)) {
                Gson gSon = new Gson();
                zomatoRestaurant = gSon.fromJson(bundle.getString(EXTRA_OBJECT_RESTAURANT_ZM), new TypeToken<ZomatoRestaurant>() {
                }.getType());
            }
        }

        SetupView();
    }

    private void SetupView() {

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        if (restaurant != null) {
            collapsingToolbarLayout.setTitle(restaurant.getRestaurant().getName());
            setupFavorite(restaurant.getRestaurant());
        } else if (zomatoRestaurant != null) {
            collapsingToolbarLayout.setTitle(zomatoRestaurant.getName());
            setupFavorite(zomatoRestaurant);
        }

        collapsingToolbarLayout.setExpandedTitleColor(getResources().getColor(android.R.color.transparent));
        collapsingToolbarLayout.setCollapsedTitleTextColor(getResources().getColor(android.R.color.white));
        collapsingToolbarLayout.setCollapsedTitleTypeface(FontTypefaceUtils.getRobotoCondensedRegular(ActivityRestaurantDetails.this));
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            toolbar.setNavigationOnClickListener(v -> finish());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        containerProgress = (ViewGroup) findViewById(R.id.container_progress);
        containerProgress.setVisibility(View.GONE);

        setupViewPager();
        setupBannerSlider();

    }

    private void setupBannerSlider() {
        TextView text_tittle = (TextView) findViewById(R.id.text_tittle);
        TextView text_cuisine = (TextView) findViewById(R.id.text_cuisine);
        BannerSlider bannerSlider = (BannerSlider) findViewById(R.id.banner_slider);
        ImageView ivImage = (ImageView) findViewById(R.id.ivImage);

        if (restaurant != null) {
            text_tittle.setText(restaurant.getRestaurant().getName());
            text_cuisine.setText(restaurant.getRestaurant().getCuisines());
        } else if (zomatoRestaurant != null) {
            text_tittle.setText(zomatoRestaurant.getName());
            text_cuisine.setText(zomatoRestaurant.getCuisines());
        }
        bannerSlider.setDefaultIndicator(IndicatorShape.CIRCLE);


        /**
         *
         * is needed a partner with zomato for use photos
         * **/

        /*if (restaurant.getRestaurant().getgetPhotos() != null && !restaurant.getPhotos().isEmpty()) {
            if (Integer.parseInt(restaurant.getPhotoCount()) == 1) {
                ivImage.setVisibility(View.VISIBLE);
                bannerSlider.setVisibility(View.GONE);
                String url = restaurant.getPhotos().get(0).getUrl();

                Glide.with(ActivityRestaurantDetails.this).load(url)
                        .thumbnail(0.5f)
                        .crossFade()
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(ivImage);

            } else if (Integer.parseInt(restaurant.getPhotoCount()) > 1) {
                ivImage.setVisibility(View.GONE);
                bannerSlider.setVisibility(View.VISIBLE);
                for (int i = 0; i < restaurant.getPhotos().size(); i++) {
                    bannerSlider.addBanner(new RemoteBanner(
                            restaurant.getPhotos().get(i).getUrl())
                    );
                }
            } else {
                ivImage.setVisibility(View.VISIBLE);
                bannerSlider.setVisibility(View.GONE);
            }
        }*/
    }

    private void setupFavorite(ZomatoRestaurant zomato) {
        ZomatoRestaurant rest = new RestaurantDAO(AppDatabaseManager.getInstance().getHelper()).Get(zomato);

        if (rest != null)
            zomato.setFavorite(true);
        else
            zomato.setFavorite(false);

        fab.setImageDrawable(zomato.isFavorite() ? getResources().getDrawable(R.drawable.ic_star_white_36dp) : getResources().getDrawable(R.drawable.ic_star_border_white_36dp));
        fab.setOnClickListener(v -> {
            if (zomato.isFavorite()) {
                zomato.setFavorite(false);
                RemoveFromFavorites(zomato);
            } else {
                zomato.setFavorite(true);
                AddFavorite(zomato);
            }

            fab.setImageDrawable(zomato.isFavorite() ? getResources().getDrawable(R.drawable.ic_star_white_36dp) : getResources().getDrawable(R.drawable.ic_star_border_white_36dp));
        });
    }

    private void setupViewPager() {
        mViewPager = (ViewPager)
                findViewById(R.id.viewpager);
        RestaurantPagerAdapter adapter = new RestaurantPagerAdapter(getSupportFragmentManager());

        if (frag_address == null) {
            if (restaurant != null)
                frag_address = new RestaurantAddressFragment().newInstance(restaurant.getRestaurant());
            else if (zomatoRestaurant != null)
                frag_address = new RestaurantAddressFragment().newInstance(zomatoRestaurant);
        }

        /**
         *
         * There is not sufficient information on free api access,
         * i need to be partner
         *
         *
         *  if (frag_info == null) {
         *    frag_info = new RestaurantInformationFragment().newInstance(restaurant);
         * }
         *
         * adapter.addFragment(frag_info, getString(R.string.restaurant_detail));
         **/

        adapter.addFragment(frag_address, getString(R.string.restaurant_address));
        mViewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    private void AddFavorite(ZomatoRestaurant zomato) {
        if (zomato != null) {
            zomato.setAddress(restaurant.getRestaurant().getLocation().getAddress());
            zomato.setAddressInfo(restaurant.getRestaurant().getLocation().getLocality());
            zomato.setLat(Double.parseDouble(restaurant.getRestaurant().getLocation().getLatitude()));
            zomato.setLon(Double.parseDouble(restaurant.getRestaurant().getLocation().getLongitude()));
            zomato.setFavorite(true);
            new RestaurantDAO(AppDatabaseManager.getInstance().getHelper()).Insert(zomato, IOperationDAO.OPERATION_INSERT_OR_UPDATE);
        }
    }

    private void RemoveFromFavorites(ZomatoRestaurant zomato) {
        new RestaurantDAO(AppDatabaseManager.getInstance().getHelper()).Delete(zomato);
    }
}
