package powerdms.forkspoon.api.task;

import java.util.Map;

import powerdms.forkspoon.api.IResponseObject;
import powerdms.forkspoon.api.ServiceGenerator;
import powerdms.forkspoon.api.ZomatoAPI;
import powerdms.forkspoon.model.common.establishment.EstablishmentsResp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Ambruster on 13/06/2017.
 */

public class EstablishmentTask {

    ServiceGenerator serviceGenerator;

    private ZomatoAPI api = null;
    private IResponseObject<Object> callBack;

    public EstablishmentTask(IResponseObject callBack) {
        this.callBack = callBack;
    }

    public void getEstablishments(Map<String, String> params) {

        api = serviceGenerator.createService(ZomatoAPI.class);

        Call<EstablishmentsResp> call = api.getEstablishments(params);
        call.enqueue(new Callback<EstablishmentsResp>() {
            @Override
            public void onResponse(Call<EstablishmentsResp> call, Response<EstablishmentsResp> response) {
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
            public void onFailure(Call<EstablishmentsResp> call, Throwable t) {
                callBack.onError(t.toString(), 500);
            }
        });
    }


}
