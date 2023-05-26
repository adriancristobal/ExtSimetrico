package dao.retrofit.produces;

import dao.retrofit.CacheAuthorization;
import dao.retrofit.produces.constants.ConstantsInterceptor;
import okhttp3.Credentials;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class AuthorizationInterceptor implements Interceptor {


    private CacheAuthorization ca;


    public AuthorizationInterceptor(CacheAuthorization ca) {
        this.ca = ca;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();
        Request request = original;

        if (ca.getJwt() != null) {
            request = original.newBuilder()
                    .header(ConstantsInterceptor.AUTHORIZATION, ConstantsInterceptor.BEARER + ca.getJwt()).build();
        }

        //con esto va al servidor
        Response response = chain.proceed(request);

        //////////////////////////////////////////

        if (response.code() == ConstantsInterceptor.STATUS_TOKEN_EXPIRED) {
            //token expirado, enviamos al servidor los datos que ya tenemos del usuario guardados en el cache autoriztation
            response.close();
            request = original.newBuilder()
                    .header(ConstantsInterceptor.AUTHORIZATION, Credentials.basic(ca.getUser(), ca.getPassword())).build();
            response = chain.proceed(request);
            if (response.header(ConstantsInterceptor.AUTHORIZATION) != null)
                ca.setJwt(response.header(ConstantsInterceptor.AUTHORIZATION));
        }

        return response;
    }
}
