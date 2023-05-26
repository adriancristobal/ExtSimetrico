package domain.service.impl;

import dao.DaoLogeo;
import dao.impl.DaoLogeoImpl;
import domain.exceptions.NotFoundException;
import domain.service.ServiceLogeo;
import io.jsonwebtoken.Jwts;
import jakarta.common.Constants;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.security.enterprise.identitystore.Pbkdf2PasswordHash;
import lombok.extern.log4j.Log4j2;
import model.Usuario;
import utils.ErrorConstants;

import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@Log4j2
public class ServiceLogeoImpl implements ServiceLogeo {

    private final DaoLogeo dao;

    final Pbkdf2PasswordHash passwordHash;
    private final Key key;

    @Inject
    public ServiceLogeoImpl(DaoLogeoImpl dao, Pbkdf2PasswordHash passwordHash, @Named("JWT") Key key) {
        this.dao = dao;
        this.passwordHash = passwordHash;
        this.key = key;
    }

    @Override
    public Usuario get(String email) {
        return dao.get(email);
    }

    @Override
    public Usuario getByUsername(String username) {
        return dao.getByUsername(username);
    }

    @Override
    public Usuario getByUsernameAndPassword(String username, String password) {
        return dao.getByUsernameAndPassword(username, password);
    }

    @Override
    public List<String> getRoles(int userId) {
        return dao.getRoles(userId);
    }

    @Override
    public Usuario add(Usuario user) {
        return dao.add(user);
    }

    @Override
    public boolean verifyEmail(String activationCode) {
        boolean result = false;
        Usuario user = dao.findOneByActivationCode(activationCode);
//        Duration duration = Duration.between(LocalTime.now(), user.getDate_register());
//        if (duration.toMinutes() < 5) {
//            try {
//                if (user.getActivation_code().equals(activationCode)) {
//                    int r = dao.updateActivatedByToken(1, activationCode);
//                    if (r == 1) {
//                        result = true;
//                    } else {
//                        log.error("Error with update");
//                    }
//                } else {
//                    log.error("Error with tokens. Not equals");
//                }
//            } catch (Exception e) {
//                log.error(e.getMessage());
//            }
//        } else {
//            log.error("Time expired");
//        }
        return result;
    }

    @Override
    public Usuario verifyPassword(Usuario user, String password) {
        try {
            passwordHash.verify(password.toCharArray(), user.getContrasenia());
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new NotFoundException("Password incorrect");
        }
        return user;
    }

    @Override
    public void login(Usuario user, char[] toCharArray, String password) {
        if (user != null) {
                if (passwordHash.verify(toCharArray, user.getContrasenia())) {
                    log.info("Password correct");
                } else {
                    log.error("Password incorrect");
                    throw new NotFoundException("Password incorrect");
                }
        } else {
            log.error("User not found");
            throw new NotFoundException("User not found");
        }
    }

    @Override
    public String generateJWS(Usuario user) {
        try {
            List<String> roles = getRoles(user.getId());
            return Jwts.builder()
                    .setSubject(Constants.SUBJECT)
                    .setIssuer(Constants.ISSUER)
                    .setExpiration(Date
                            .from(LocalDateTime.now().plusSeconds(1).atZone(ZoneId.systemDefault())
                                    .toInstant()))
                    .claim(Constants.USERNAME, user.getNombre())
                    .claim(Constants.ROLES, roles)
                    .signWith(key).compact();
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new NotFoundException(ErrorConstants.ERROR_GENERATE_JWT);
        }
    }

}
