package dao.retrofit.produces;

import com.fatboyindustrial.gsonjavatime.Converters;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import common.config.ConfigSingleton_Client;
import dao.retrofit.CacheAuthorization;
import dao.retrofit.calls.CallContratoApi;
import dao.retrofit.calls.CallLogeoApi;
import dao.retrofit.calls.CallSicarioContratoApi;
import jakarta.enterprise.inject.Produces;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class ProducesRetrofit {

    private CacheAuthorization cacheAuthorization;

    @Inject
    public ProducesRetrofit(CacheAuthorization cacheAuthorization) {
        this.cacheAuthorization = cacheAuthorization;
    }


    @Produces
    @Singleton
    public Gson gson(){
        return Converters.registerAll(new GsonBuilder()).create();
        /* Esto es lo que tenia Oscar
        return  new GsonBuilder()
                .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, type, jsonDeserializationContext) -> LocalDateTime.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (localDateTime, type, jsonSerializationContext) -> new JsonPrimitive(localDateTime.toString()))
                .registerTypeAdapter(LocalDate.class, (JsonDeserializer<LocalDate>) (json, type, jsonDeserializationContext) -> LocalDate.parse(json.getAsJsonPrimitive().getAsString()))
                .registerTypeAdapter(LocalDate.class, (JsonSerializer<LocalDate>) (localDate, type, jsonSerializationContext) -> new JsonPrimitive(localDate.toString())
                ).create();

        */
    }

    @Produces
    @Singleton
    public OkHttpClient okHttpClient(){

        return new OkHttpClient.Builder()
                .readTimeout(Duration.of(10, ChronoUnit.MINUTES)) //los Timeout son el tiempo que espera el cliente a que el servidor le responda
                .callTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .connectTimeout(Duration.of(10, ChronoUnit.MINUTES))
                .connectionPool(new okhttp3.ConnectionPool(1, 1, java.util.concurrent.TimeUnit.SECONDS))
                .addInterceptor(new AuthorizationInterceptor(cacheAuthorization))
                .build();

    }

    @Produces
    @Singleton
    public Retrofit retrofit(Gson gson, OkHttpClient clientOk, ConfigSingleton_Client config){

        Retrofit retro = new Retrofit.Builder()
                .baseUrl(config.getPath_base())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .client(clientOk)
                .build();

        return retro;
    }

    @Produces
    public CallContratoApi CallContratoApi(Retrofit retrofit){
        return retrofit.create(CallContratoApi.class);
    }
    @Produces
    public CallSicarioContratoApi CallSicarioContratoApi(Retrofit retrofit){
        return retrofit.create(CallSicarioContratoApi.class);
    }

    @Produces
    public CallLogeoApi CallLogeoApi(Retrofit retrofit){
        return retrofit.create(CallLogeoApi.class);
    }

}
