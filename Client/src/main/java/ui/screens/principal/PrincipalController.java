package ui.screens.principal;

import jakarta.enterprise.inject.Instance;
import jakarta.inject.Inject;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import lombok.extern.log4j.Log4j2;
import ui.screens.common.BaseScreenController;
import ui.screens.common.Screens;

import java.io.IOException;

@Log4j2
public class PrincipalController {

    @FXML
    public BorderPane rootScreenPrincipal;
    @FXML
    private MenuItem menuLogin;
    @FXML
    private MenuItem menuRegister;
    @FXML
    private MenuItem menuLogout;
    private Stage primaryStage;

    Instance<Object> instance;

    private final Alert alert;

    @Inject
    public PrincipalController(Instance<Object> instance) {
        this.instance = instance;
        alert= new Alert(Alert.AlertType.NONE);
    }



    private void cambioScreen(Pane screenNueva) {
        rootScreenPrincipal.setCenter(screenNueva);
    }

    private void cargarScreen(Screens screen) {
                cambioScreen(cargarScreenPane(screen.getRutaScreen()));
    }

    public void initialize() {
        menuLogout.setVisible(false);
        menuRegister.setVisible(true);
        menuLogin.setVisible(true);
        cargarScreen(Screens.LOGIN);
    }

    private Pane cargarScreenPane(String ruta) {
        Pane paneScreen = null;
        try {

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(controller -> instance.select(controller).get());
            paneScreen = fxmlLoader.load(getClass().getResourceAsStream(ruta));
            BaseScreenController screenController = fxmlLoader.getController();
            screenController.setPrincipalController(this);
            screenController.principalCargado();


        } catch (IOException e) {
            log.error(e.getMessage(),e);
        }
        return paneScreen;
    }

    public void onLoginHecho() {
        menuLogout.setVisible(true);
        menuLogin.setVisible(false);
        menuRegister.setVisible(false);
        cargarScreen(Screens.WELCOME);
    }

    @FXML
    private void menuClick(ActionEvent actionEvent) {
        switch (((MenuItem) actionEvent.getSource()).getId()) {
            case "menuRegister" -> cargarScreen(Screens.REGISTER);
            case "menuLogin" -> cargarScreen(Screens.LOGIN);
            case "menuLogout" -> cargarScreen(Screens.LOGOUT);
            case "menuGetAllIngredients" -> cargarScreen(Screens.SCREEN_GET_ALL_CONTRATO);
            case "menuAddIngredient" -> cargarScreen(Screens.SCREEN_ADD_CONTRATO);
            case "menuDeleteIngredient" -> cargarScreen(Screens.SCREEN_DELETE_CONTRATO);
            case "menuUpdateIngredient" -> cargarScreen(Screens.SCREEN_UPDATE_CONTRATO);
        }
        /*if ("menuGetAllNewspaper".equals(((MenuItem) actionEvent.getSource()).getId())) {
            cargarScreen(Screens.Screen_GET_ALL_NEWSPAPER);
        } else if ("menuGetAllArticle".equals(((MenuItem) actionEvent.getSource()).getId())) {
            cargarScreen(Screens.Screen_GET_ALL_ARTICLE);
        } else if ("menuAddArticle".equals(((MenuItem) actionEvent.getSource()).getId())) {
            cargarScreen(Screens.Screen_ADD_ARTICLE);
        }
         */
    }

        public void setStage (Stage stage){
            primaryStage = stage;
            //primaryStage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
        }

    }
