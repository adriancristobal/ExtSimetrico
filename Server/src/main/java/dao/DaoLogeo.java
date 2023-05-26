package dao;

import model.Usuario;

import java.util.List;

public interface DaoLogeo {

    Usuario get(String email);
    Usuario getByUsername(String username);
    Usuario getByUsernameAndPassword(String username, String password);
    List<String> getRoles(int userId);
    Usuario add(Usuario user);
    Usuario findOneByActivationCode(String activationCode);
    int updateActivatedByToken(int activated, String activation_code);
}
