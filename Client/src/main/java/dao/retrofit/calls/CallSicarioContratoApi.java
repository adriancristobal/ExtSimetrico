package dao.retrofit.calls;

import io.reactivex.rxjava3.core.Single;
import model.Contrato;
import model.SicarioContrato;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface CallSicarioContratoApi {

    @GET("sicariosContratos")
    Single<List<SicarioContrato>> getAllSicarioContratos();
    @GET("sicariosContratos/{id}")
    Single<Contrato> getSicarioContratoById(@Path("id") int id);
    @POST("sicariosContratos")
    Single<Contrato> postSicarioContrato(@Body SicarioContrato sicarioContrato);
    @PUT("sicariosContratos")
    Single<Contrato> putSicarioContrato(@Body SicarioContrato sicarioContrato);
    @DELETE("sicariosContratos")
    Single<Response<Void>> deleteSicarioContrato(@Query("id") int id);
}
