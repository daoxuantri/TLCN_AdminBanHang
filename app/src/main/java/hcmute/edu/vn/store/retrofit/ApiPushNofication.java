package hcmute.edu.vn.store.retrofit;



import hcmute.edu.vn.store.model.NotiRespone;

import hcmute.edu.vn.store.model.NotiSendData;
import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiPushNofication {
    @Headers(
            {
                   "content-type: application/json" ,
                    "authorization: key=AAAAvsgI5LE:APA91bFX-RhEzpmej3OsSjjESXy3nsbS8Omq7ppz_hS9Tc7RcDicdc3goUJXrM0EDFp5nkQ4wc6vgYOE63iGWs8p6a0MQSnDNDtk15gYZOsnw8ZKGhC679g3gGGKJFqifOF5qaGS7oJ5"
            }
    )
    @POST("fcm/send")
    Observable<NotiRespone> sendNofitication(@Body NotiSendData data);

}
