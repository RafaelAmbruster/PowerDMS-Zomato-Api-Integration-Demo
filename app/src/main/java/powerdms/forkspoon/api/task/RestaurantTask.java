package powerdms.forkspoon.api.task;

import java.util.Map;

import powerdms.forkspoon.api.IResponseObject;
import powerdms.forkspoon.api.ServiceGenerator;
import powerdms.forkspoon.api.ZomatoAPI;
import powerdms.forkspoon.model.common.geocode.GeocodeResp;
import powerdms.forkspoon.model.restaurant.detail.Restaurant;
import powerdms.forkspoon.model.restaurant.search.SearchResp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ambruster on 13/06/2017.
 */

public class RestaurantTask {

    ServiceGenerator serviceGenerator;

    private ZomatoAPI api = null;
    private IResponseObject<Object> callBack;

    public RestaurantTask(IResponseObject callBack) {
        this.callBack = callBack;
    }

    public void Search(Map<String, String> params) {

        api = serviceGenerator.createService(ZomatoAPI.class);

        Call<SearchResp> call = api.Search(params);
        call.enqueue(new Callback<SearchResp>() {
            @Override
            public void onResponse(Call<SearchResp> call, Response<SearchResp> response) {
                if (response != null) {
                    try {
                        switch (response.code()) {
                            case 401:
                                callBack.onError(response.message(), response.code());
                                break;
                            case 200:
                                callBack.onResponse(response.body());
                                break;
                            case 500:
                                callBack.onError(response.message(), response.code());
                                break;
                        }
                    } catch (Exception ex) {
                        callBack.onError(ex.toString(), response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<SearchResp> call, Throwable t) {
                callBack.onError(t.toString(), 500);
            }
        });
    }

    public void loadRestaurant(Map<String, String> params) {

        api = serviceGenerator.createService(ZomatoAPI.class);

        Call<Restaurant> call = api.getRestaurant(params);
        call.enqueue(new Callback<Restaurant>() {
            @Override
            public void onResponse(Call<Restaurant> call, Response<Restaurant> response) {
                if (response != null) {
                    try {
                        switch (response.code()) {
                            case 401:
                                callBack.onError(response.message(), response.code());
                                break;
                            case 200:
                                callBack.onResponse(response.body());
                                break;
                            case 500:
                                callBack.onError(response.message(), response.code());
                                break;
                        }
                    } catch (Exception ex) {
                        callBack.onError(ex.toString(), response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<Restaurant> call, Throwable t) {
                callBack.onError(t.toString(), 500);
            }
        });
    }

    public void loadbyGeocode(Map<String, String> params) {

        api = serviceGenerator.createService(ZomatoAPI.class);

        Call<GeocodeResp> call = api.getGeocode(params);
        call.enqueue(new Callback<GeocodeResp>() {
            @Override
            public void onResponse(Call<GeocodeResp> call, Response<GeocodeResp> response) {
                if (response != null) {
                    try {
                        switch (response.code()) {
                            case 401:
                                callBack.onError(response.message(), response.code());
                                break;
                            case 200:
                                callBack.onResponse(response.body());
                                break;
                            case 500:
                                callBack.onError(response.message(), response.code());
                                break;
                        }
                    } catch (Exception ex) {
                        callBack.onError(ex.toString(), response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<GeocodeResp> call, Throwable t) {
                callBack.onError(t.toString(), 500);
            }
        });
    }


}
