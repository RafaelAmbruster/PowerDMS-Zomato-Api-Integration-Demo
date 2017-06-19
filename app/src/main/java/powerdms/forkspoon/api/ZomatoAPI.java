package powerdms.forkspoon.api;

import java.util.List;
import java.util.Map;

import powerdms.forkspoon.model.common.category.CategoriesResp;
import powerdms.forkspoon.model.common.city.CitiesResp;
import powerdms.forkspoon.model.common.cuisine.CuisinesResp;
import powerdms.forkspoon.model.common.establishment.EstablishmentsResp;
import powerdms.forkspoon.model.common.geocode.GeocodeResp;
import powerdms.forkspoon.model.common.geocode.Review;
import powerdms.forkspoon.model.location.LocationResp;
import powerdms.forkspoon.model.location.locationdetail.LocationDetailsResp;
import powerdms.forkspoon.model.restaurant.dailymenu.DailyMenuResp;
import powerdms.forkspoon.model.restaurant.detail.Restaurant;
import powerdms.forkspoon.model.restaurant.search.SearchResp;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Ambruster on 13/06/2017.
 */

public interface ZomatoAPI {

    /**
     * Categories
     * **/

    @GET("categories")
    Call<CategoriesResp> getCategories();

    /**
     * Cities
     * **/

    @GET("categories")
    Call<CitiesResp> getCities(@QueryMap Map<String, String> params);

    /**
     * Collections
     * **/

    @GET("collections")
    Call<CitiesResp> getCollections(@QueryMap Map<String, String> params);

    /**
     * Cuisines
     * **/

    @GET("cuisines")
    Call<CuisinesResp> getCuisines(@QueryMap Map<String, String> params);

    /**
     * Establishments
     * **/

    @GET("establishments")
    Call<EstablishmentsResp> getEstablishments(@QueryMap Map<String, String> params);

    /**
     * GeocodeResp
     * **/

    @GET("geocode")
    Call<GeocodeResp> getGeocode(@QueryMap Map<String, String> params);

    /**
     * Location-Details
     * **/

    @GET("location_details")
    Call<LocationDetailsResp> getLocationDetails(@QueryMap Map<String, String> params);

    /**
     * Location
     * **/

    @GET("locations")
    Call<LocationResp> getLocation(@QueryMap Map<String, String> params);

    /**
     * Daily-Menu
     * **/

    @GET("dailymenu")
    Call<DailyMenuResp> getDaiyMenu(@QueryMap Map<String, String> params);

    /**
     * Restaurant
     * **/

    @GET("restaurant")
    Call<Restaurant> getRestaurant(@QueryMap Map<String, String> params);


    /**
     * Reviews
     * **/

    @GET("reviews")
    Call<List<Review>> getReviews(@QueryMap Map<String, String> params);


    /**
     * Search
     * **/

    @GET("search")
    Call<SearchResp> Search(@QueryMap Map<String, String> params);


}
