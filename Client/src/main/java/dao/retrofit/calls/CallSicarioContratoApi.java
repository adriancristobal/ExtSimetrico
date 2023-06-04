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
    @GET("sicariosContratos/byIdSicario")
    Single<List<SicarioContrato>> getSicarioContratosBySicario(@Query("idSicario") int id);
    @POST("sicariosContratos")
    Single<SicarioContrato> postSicarioContrato(@Body SicarioContrato sicarioContrato);
    @PUT("sicariosContratos")
    Single<SicarioContrato> putSicarioContrato(@Body SicarioContrato sicarioContrato);
    @DELETE("sicariosContratos")
    Single<Response<Void>> deleteSicarioContrato(@Query("id") int id);
}
