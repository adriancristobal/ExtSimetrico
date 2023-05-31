package ui.screens.fxType.sicario;

import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.input.MouseEvent;
import modelClient.Detalle;
import ui.screens.common.BaseScreenController;

import java.net.URL;
import java.util.ResourceBundle;

public class SicarioController extends BaseScreenController implements Initializable {
    @FXML
    private Button btnUpdate;
    @FXML
    private ListView<Detalle> lvContratos;
    @FXML
    private ComboBox<String> cbEstado;

    private SicarioViewModel sicarioViewModel;
    private Alert alerta;



    @Inject
    public SicarioController(SicarioViewModel sicarioViewModel) {
        this.sicarioViewModel = sicarioViewModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alerta = new Alert(Alert.AlertType.NONE);
        changeStatus();
    }
    @FXML
    private void updateEstadoContrato(ActionEvent actionEvent) {

    }

    @FXML
    private void selectedItem(MouseEvent mouseEvent) {
        if (lvContratos.getSelectionModel().getSelectedItem() != null) {
            cbEstado.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    private void chargeList(ActionEvent actionEvent) {
        sicarioViewModel.getContratosBySicario(getPrincipalController().usuario.getId());
        getPrincipalController().rootScreenPrincipal.setCursor(Cursor.WAIT);
    }



    private void changeStatus() {
        sicarioViewModel.getState().addListener((observableValue, state, newState) ->
                Platform.runLater(() -> {
                    if (newState.getMessage() != null) {
                        alert("Error", newState.getMessage(), Alert.AlertType.ERROR);
                    }
                    if (newState.getContratoList() != null) {
                        lvContratos.getItems().clear();
                        lvContratos.getItems().addAll(newState.getContratoList());
                    }
                    if(newState.isUpdated()) {
                        clearFields();
                        buttonsRestart();
                        alert("Success", "Contrato updated", Alert.AlertType.INFORMATION);
                    }
                    if (newState.isLoading()){
                        getPrincipalController().rootScreenPrincipal.setCursor(Cursor.DEFAULT);
                    }
                })
        );

    }

    private void buttonsRestart() {
        lvContratos.getSelectionModel().clearSelection();
        cbEstado.setDisable(true);
        btnUpdate.setDisable(true);
    }

    private void clearFields() {
        cbEstado.setValue(null);
    }

    private void alert(String titulo, String texto, Alert.AlertType tipo){
        alerta.setTitle(titulo);
        alerta.setContentText(texto);
        alerta.setAlertType(tipo);
        alerta.showAndWait();
    }
}
