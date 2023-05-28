package dao.retrofit.calls;

import io.reactivex.rxjava3.core.Single;
import model.Contrato;
import model.SicarioContrato;
import model.Usuario;
import retrofit2.Response;
import retrofit2.http.*;

import java.util.List;

public interface CallContratoApi {

    @GET("contratos")
    Single<List<Contrato>> getAllContratos();
    @GET("contratos/{id}")
    Single<Contrato> getContratoById(@Path("id") int id);
    @POST("contratos")
    Single<Contrato> postContrato(@Body Contrato contrato);
    @PUT("contratos")
    Single<Contrato> putContrato(@Body Contrato contrato);
    @DELETE("contratos")
    Single<Response<Void>> deleteContrato(@Query("id") int id);
    @GET("contratos/byHabilityLevel")
    Single<List<Usuario>> getSicariosByHabilityLevel(@Query("habilityLevel") Integer habilityLevel);

}
