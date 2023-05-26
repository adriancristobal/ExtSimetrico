package ui.screens.fxAccount.login;

import lombok.Data;

@Data
public class LoginState {

    private final boolean load;
    private final boolean change;
    private final String message;
}
