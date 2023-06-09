package domain.service;

import model.Usuario;

import java.util.List;

public interface ServiceLogeo {

    Usuario getByUsername(String username);
    Usuario getByUsernameAndPassword(String username, String password);
    List<String> getRoles(int userId);
    Usuario add(Usuario user);
    Usuario verifyPassword(Usuario user, String password);
    void login(Usuario user, char[] toCharArray, String password);

    String generateJWS(Usuario user);
}
