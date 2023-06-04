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
import model.Contrato;
import model.SicarioContrato;
import model.enums.Estado;
import modelClient.Detalle;
import ui.screens.common.BaseScreenController;

import java.net.URL;
import java.util.ResourceBundle;

public class SicarioController extends BaseScreenController implements Initializable {
    @FXML
    private ListView<Estado> lvEstados;
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
        cbEstado.getItems().addAll("ACEPTADO", "RECHAZADO", "PENDIENTE", "COMPLETADO");
    }
    @FXML
    private void updateEstadoContrato(ActionEvent actionEvent) {
        if (lvContratos.getSelectionModel().getSelectedItem() != null && cbEstado.getValue() != null) {
            Detalle detalle = lvContratos.getSelectionModel().getSelectedItem();
            SicarioContrato sicarioContrato = new SicarioContrato();
            sicarioContrato.setId_sicario(getPrincipalController().usuario.getId());
            sicarioContrato.setId_contrato(detalle.getIdContrato());
            sicarioContrato.setEstado(Estado.valueOf(cbEstado.getValue()));
            sicarioViewModel.updateEstado(sicarioContrato);
            getPrincipalController().rootScreenPrincipal.setCursor(Cursor.WAIT);
        } else {
            alert("Error", "Select a contract and a state", Alert.AlertType.WARNING);
        }
    }

    @FXML
    private void selectedItem(MouseEvent mouseEvent) {
        if (lvContratos.getSelectionModel().getSelectedItem() != null) {
            cbEstado.setDisable(false);
            btnUpdate.setDisable(false);
        }
    }

    @FXML
    private void chargeList() {
        sicarioViewModel.getContratosBySicario(getPrincipalController().usuario.getId());
        sicarioViewModel.getSicarioContratosBySicario(getPrincipalController().usuario.getId());
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
                    if (newState.getSicarioList() != null) {
                        lvEstados.getItems().clear();
                        for (SicarioContrato sicarioContrato : newState.getSicarioList()) {
                            lvEstados.getItems().add(sicarioContrato.getEstado());
                        }
                    }
                    if(newState.isUpdated()) {
                        clearFields();
                        buttonsRestart();
//                        updateDetalleOfContrato();
                        chargeList();
                        alert("Success", "Contrato updated", Alert.AlertType.INFORMATION);
                    }
                    if (newState.isLoading()){
                        getPrincipalController().rootScreenPrincipal.setCursor(Cursor.DEFAULT);
                    }
                })
        );

    }

//    private void updateDetalleOfContrato() {
//        Detalle detalle = lvContratos.getSelectionModel().getSelectedItem();
//        detalle.setEstado(cbEstado.getValue());
//        Contrato contrato = new Contrato(detalle.getIdContrato(), detalle.toString());
//        sicarioViewModel.updateDetalleOfContrato();
//        getPrincipalController().rootScreenPrincipal.setCursor(Cursor.WAIT);
//    }


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
