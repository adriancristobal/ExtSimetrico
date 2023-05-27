package ui.screens.fxAccount.register;

import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import model.Usuario;
import ui.screens.common.BaseScreenController;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

public class RegisterController extends BaseScreenController implements Initializable {
    @FXML
    private ComboBox<Integer> cbHabilityLevel;
    @FXML
    private RadioButton rbContratista;
    @FXML
    private ToggleGroup tgType;
    @FXML
    private RadioButton rbSicario;
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
        String username = tfUsername.getText();
        String password = tfPassword.getText();
        String type = ((RadioButton) tgType.getSelectedToggle()).getText();
        int habilidad = cbHabilityLevel.getValue();
        if (username.isEmpty() || password.isEmpty() || type.isEmpty() || habilidad == 0) {
            errorBox.setText("Please complete all fields");
        } else {
            Usuario userRegisterDTO = new Usuario(username, password, type, habilidad);
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
                        alert("Login", "Now you can login", Alert.AlertType.INFORMATION);
                    } else {
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
        cbHabilityLevel.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        cbHabilityLevel.getSelectionModel().select(0);
        changeStatus();
    }

}
