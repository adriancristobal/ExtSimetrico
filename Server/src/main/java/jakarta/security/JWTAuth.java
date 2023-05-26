package jakarta.security;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import jakarta.common.Constants;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.AuthenticationException;
import jakarta.security.enterprise.AuthenticationStatus;
import jakarta.security.enterprise.authentication.mechanism.http.HttpAuthenticationMechanism;
import jakarta.security.enterprise.authentication.mechanism.http.HttpMessageContext;
import jakarta.security.enterprise.credential.BasicAuthenticationCredential;
import jakarta.security.enterprise.credential.RememberMeCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.ws.rs.core.HttpHeaders;

import java.io.IOException;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


@ApplicationScoped
public class JWTAuth implements HttpAuthenticationMechanism {

    @Inject
    private TokenIdentityStore identity;

    Key key;

    @Inject
    public JWTAuth(@Named("JWT") Key key) {
        this.key = key;
    }



    @Override
    public AuthenticationStatus validateRequest(HttpServletRequest httpServletRequest,
                                                HttpServletResponse httpServletResponse
            , HttpMessageContext httpMessageContext) throws AuthenticationException {

        CredentialValidationResult c = CredentialValidationResult.INVALID_RESULT;

        String header = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
        if (header != null) {
            String[] valores = header.split(Constants.SPACE);

             if (valores[0].equalsIgnoreCase(Constants.BASIC)) {
                c = identity.validate(new BasicAuthenticationCredential(valores[1]));

                //generateToken
                try {
                    String jws = generateToken(c.getCallerPrincipal().getName(), c.getCallerGroups().stream().toList());
                    httpServletResponse.setHeader(HttpHeaders.AUTHORIZATION, jws);
                } catch (Exception e) {
                    httpMessageContext.responseNotFound();
                    httpMessageContext.cleanClientSubject();
                }
            }
             //dime que esta todo bien pls

            if (valores[0].equalsIgnoreCase(Constants.BEARER)) {
                try {
                    c = identity.validate(new RememberMeCredential(valores[1]));
                } catch (ExpiredJwtException e) {
                    try {
                        httpServletResponse.sendError(Constants.STATUS_TOKEN_EXPIRED);
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                } catch (Exception e) {
                    httpMessageContext.responseNotFound();
                    httpMessageContext.cleanClientSubject();
                }
            }
        }

        if (!c.getStatus().equals(CredentialValidationResult.Status.VALID)) {
            httpServletRequest.setAttribute(Constants.STATUS, c.getStatus());
            return httpMessageContext.doNothing();
        }


        return httpMessageContext.notifyContainerAboutLogin(c);

    }

    private String generateToken(String username, List<String> roles) {
        return Jwts.builder()
                .setSubject(Constants.SUBJECT)
                .setIssuer(Constants.ISSUER)
                .setExpiration(Date
                        .from(LocalDateTime.now().plusMinutes(2).atZone(ZoneId.systemDefault())
                                .toInstant()))
                .claim(Constants.USERNAME, username)
                .claim(Constants.ROLES, roles)
                .signWith(key).compact();
    }
}
