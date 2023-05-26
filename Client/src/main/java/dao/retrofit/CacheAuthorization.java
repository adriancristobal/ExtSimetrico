package dao.retrofit;

import jakarta.inject.Singleton;
import lombok.Data;


@Data
@Singleton
public class CacheAuthorization {

    private String user;
    private String password;
    private String jwt;
}
