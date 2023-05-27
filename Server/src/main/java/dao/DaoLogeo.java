package dao;

import model.Usuario;

import java.util.List;

public interface DaoLogeo {

    Usuario getByUsername(String username);
    Usuario getByUsernameAndPassword(String username, String password);
    List<String> getRoles(int userId);
    Usuario add(Usuario user);

}
