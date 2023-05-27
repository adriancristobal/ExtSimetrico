package jakarta.security;

import domain.service.ServiceLogeo;
import domain.service.impl.ServiceLogeoImpl;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import jakarta.common.Constants;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.credential.BasicAuthenticationCredential;
import jakarta.security.enterprise.credential.Credential;
import jakarta.security.enterprise.credential.RememberMeCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.core.Context;
import lombok.extern.log4j.Log4j2;
import model.Usuario;

import java.security.Key;
import java.util.HashSet;
import java.util.List;

import static jakarta.security.enterprise.identitystore.CredentialValidationResult.INVALID_RESULT;
import static jakarta.security.enterprise.identitystore.CredentialValidationResult.NOT_VALIDATED_RESULT;


@Log4j2
public class TokenIdentityStore implements IdentityStore {
    final ServiceLogeo serviceLogeo;
    final Pbkdf2PasswordHash passwordHash;

    @Context
    HttpServletRequest req;

    private final Key key;

//    @Override
//    public int priority() {
//        return 10;
//    }


    @Inject
    public TokenIdentityStore(ServiceLogeoImpl serviceLogeo, Pbkdf2PasswordHash passwordHash, @Named("JWT") Key key) {
        this.serviceLogeo = serviceLogeo;
        this.passwordHash = passwordHash;
        this.key = key;
    }

    @Override
    public CredentialValidationResult validate(Credential credential) {

        CredentialValidationResult credentialValidationResult = INVALID_RESULT;

        if (credential instanceof BasicAuthenticationCredential) {
            CredentialValidationResult result = null;

            BasicAuthenticationCredential user = (BasicAuthenticationCredential) credential;

            String username = user.getCaller();
            String password = user.getPasswordAsString();
            Usuario user1 = serviceLogeo.getByUsername(username);
            Usuario myUser = serviceLogeo.verifyPassword(user1, password);


            if (myUser != null) {
                List<String> roles = serviceLogeo.getRoles(myUser.getId());
                if (roles != null) {
                    result = new CredentialValidationResult(myUser.getNombre(), new HashSet<>(roles));
                } else {
                    result = NOT_VALIDATED_RESULT;
                }
            } else {
                result = INVALID_RESULT;
            }

            return result;
        }


        if (credential instanceof RememberMeCredential jwt) {

                String jwtString = jwt.getToken();

                //verificar token
                Jws<Claims> jws = Jwts.parserBuilder()
                        .setSigningKey(key)
                        .build()
                        .parseClaimsJws(jwtString);

                String username = (String) jws.getBody().get(Constants.USERNAME);
                List<String> roles = (List<String>) jws.getBody().get(Constants.ROLES);



            if (username != null && roles != null) {
                    credentialValidationResult = new CredentialValidationResult(username, new HashSet<>(roles));
                }

                return credentialValidationResult;

        }
        return credentialValidationResult;
    }
}

       /* if (credential instanceof BasicAuthenticationCredential) {
            CredentialValidationResult result = null;

            BasicAuthenticationCredential user = (BasicAuthenticationCredential) credential;

            String caller = user.getCaller();
            User myUser = serviceLog.getByUsername(caller);


            if (myUser != null) {
                String rol = serviceLog.getRole(myUser.getId());
                if (rol != null) {
                    result = new CredentialValidationResult(myUser.getUsername(), Set.of(rol));
                } else {
                    result = NOT_VALIDATED_RESULT;
                }
            } else {
                result = INVALID_RESULT;
            }

            return result;
        }

        return INVALID_RESULT;
    }*/


