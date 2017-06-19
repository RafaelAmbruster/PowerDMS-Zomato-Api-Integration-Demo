package powerdms.forkspoon.api.task;

import powerdms.forkspoon.api.IResponseObject;
import powerdms.forkspoon.api.ServiceGenerator;
import powerdms.forkspoon.api.ZomatoAPI;
import powerdms.forkspoon.model.common.category.CategoriesResp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ambruster on 13/06/2017.
 */

public class CategoryTask {

    ServiceGenerator serviceGenerator;

    private ZomatoAPI api = null;
    private IResponseObject<Object> callBack;

    public CategoryTask(IResponseObject callBack) {
        this.callBack = callBack;
    }

    public void getCategories() {

        api = serviceGenerator.createService(ZomatoAPI.class);

        Call<CategoriesResp> call = api.getCategories();
        call.enqueue(new Callback<CategoriesResp>() {
            @Override
            public void onResponse(Call<CategoriesResp> call, Response<CategoriesResp> response) {
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
            public void onFailure(Call<CategoriesResp> call, Throwable t) {
                callBack.onError(t.toString(), 500);
            }
        });
    }


}
