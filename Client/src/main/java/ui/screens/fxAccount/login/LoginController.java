package ui.screens.fxAccount.login;

import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import ui.screens.common.BaseScreenController;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends BaseScreenController implements Initializable {
    @FXML
    private TextField fxUser;
    @FXML
    private TextField passBox;
    @FXML
    private Label errorBox;
    @FXML
    public BorderPane rootScreenPrincipal;

    LoginViewModel loginViewModel;


    @Inject
    public LoginController(LoginViewModel loginViewModel) {
        this.loginViewModel = loginViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        changeStatus();
    }

    @FXML
    private void clickLogin(ActionEvent actionEvent) {
        String username = fxUser.getText();
        String password = passBox.getText();
        getPrincipalController().rootScreenPrincipal.setCursor(Cursor.WAIT);
        loginViewModel.loginAsync(username, password);
        //loginViewModel.loginHeaderAsync(username, password);
    }


    private void changeStatus() {
        loginViewModel.getState().addListener((observableValue, loginState, loginStateNew) ->
                Platform.runLater(() -> {
                    if (loginStateNew.getMessage() != null) {
                        getPrincipalController().rootScreenPrincipal.setCursor(Cursor.DEFAULT);
                        errorBox.setText(loginStateNew.getMessage());
                    }
                    if (loginStateNew.isLoad()){
                        getPrincipalController().rootScreenPrincipal.setCursor(Cursor.DEFAULT);
                        this.getPrincipalController().onLoginHecho();
                    }
                    if(!loginStateNew.isLoad() && loginStateNew.getMessage() == null){
                        errorBox.setText("User or password incorrect");
                        getPrincipalController().rootScreenPrincipal.setCursor(Cursor.DEFAULT);
                    }

                })
        );
    }

}
