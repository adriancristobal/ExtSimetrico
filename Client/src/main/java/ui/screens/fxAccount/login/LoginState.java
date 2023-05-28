package ui.screens.fxAccount.login;

import lombok.Data;
import model.Usuario;

@Data
public class LoginState {

    private final Usuario usuario;
    private final boolean load;
    private final boolean change;
    private final String message;
}
