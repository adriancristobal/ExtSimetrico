package ui.screens.fxAccount.register;

import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import model.Usuario;
import ui.screens.common.BaseScreenController;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class RegisterController extends BaseScreenController implements Initializable {
    @FXML
    private TextField tfEmail;
    @FXML
    private PasswordField tfPassword;
    @FXML
    private TextField tfUsername;
    @FXML
    private Label errorBox;

    RegisterViewModel registerViewModel;

    Alert alerta;

    @Inject
    public RegisterController(RegisterViewModel registerViewModel) {
        this.registerViewModel = registerViewModel;
    }

    @FXML
    private void btnRegisterClick(ActionEvent actionEvent) {
        String email = tfEmail.getText();
        String password = tfPassword.getText();
        String username = tfUsername.getText();
        int activated = 0;
        String activation_token = null;
        Date date_register = null;
        int id_reader = 3;
        if (email.isEmpty() || password.isEmpty() || username.isEmpty()) {
            errorBox.setText("Email, username or password empty");
        } else {
            Usuario userRegisterDTO = new Usuario(username, password/*, tipo, habilidad*/);
            errorBox.setText("");
            getPrincipalController().rootScreenPrincipal.setCursor(Cursor.WAIT);
            registerViewModel.register(userRegisterDTO);
        }

    }

    private void changeStatus() {
        registerViewModel.getState().addListener((observableValue, registerState, registerStateNew) ->
                Platform.runLater(() -> {
                    if (registerStateNew.getMessage() != null) {
                        errorBox.setText("User and password incorrect or the user just registered");
                        //errorBox.setText("User already exists");
                        getPrincipalController().rootScreenPrincipal.setCursor(Cursor.DEFAULT);
                    }
                    if (registerStateNew.isRegister()) {
                        getPrincipalController().rootScreenPrincipal.setCursor(Cursor.DEFAULT);
                        alert("Login", "You have receive a mail to your gmail count. Verify you are", Alert.AlertType.INFORMATION);
                    } else {
                        alert("Email", "This email exit yet. You receive another mail to that email or you can log normally", Alert.AlertType.INFORMATION);
                        getPrincipalController().rootScreenPrincipal.setCursor(Cursor.DEFAULT);
                    }

                })
        );
    }

    private void alert(String titulo, String texto, Alert.AlertType tipo) {
        alerta.setTitle(titulo);
        alerta.setContentText(texto);
        alerta.setAlertType(tipo);
        alerta.showAndWait();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alerta = new Alert(Alert.AlertType.NONE);
        changeStatus();
    }

}
