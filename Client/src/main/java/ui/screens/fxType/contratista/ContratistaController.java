package ui.screens.fxType.contratista;

import jakarta.inject.Inject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.Contrato;
import model.SicarioContrato;
import model.Usuario;
import modelClient.Detalle;
import ui.screens.common.BaseScreenController;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;

public class ContratistaController extends BaseScreenController implements Initializable {

    @FXML
    private Button cleanSelection;
    @FXML
    private ListView<Detalle> lvContratos;
    @FXML
    private TextField tfTitulo;
    @FXML
    private TextField tfObjetivo;
    @FXML
    private TextField tfPrecio;
    @FXML
    private ComboBox<Integer> cbHabilityLevel;
    @FXML
    private Button btnContratoSendToSicario;
    @FXML
    private ComboBox<Usuario> sicarioComboBox;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;


    ContratistaViewModel contratistaViewModel;
    private Alert alerta;

    @Inject
    public ContratistaController(ContratistaViewModel contratistaViewModel) {
        this.contratistaViewModel = contratistaViewModel;
    }



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        alerta = new Alert(Alert.AlertType.NONE);
//        loadContratoList();
        cbHabilityLevel.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        cbHabilityLevel.getSelectionModel().selectFirst();
        changeStatus();
    }

    @FXML
    private void itemSelectedFromList(MouseEvent mouseEvent) {
        Detalle contrato = lvContratos.getSelectionModel().getSelectedItem();
        if (contrato != null) {
            enableAndDisableOfButtonsOfSelectedItemFromList();
        }
    }
    @FXML
    private void add(ActionEvent actionEvent) {
        Contrato contrato = new Contrato();
        Detalle detalle = new Detalle();
        detalle.setTitulo(tfTitulo.getText());
        detalle.setObjetivo(tfObjetivo.getText());
        detalle.setPrecio(Double.parseDouble(tfPrecio.getText()));
        detalle.setNivelHabilidad(cbHabilityLevel.getValue());
        contrato.setDetalle(detalle.toString());
        contrato.setClave("vinicius");
        contrato.setId_contratista(getPrincipalController().getUsuario().getId());
        getPrincipalController().rootScreenPrincipal.setCursor(Cursor.WAIT);
        contratistaViewModel.addContrato(contrato);
    }

    @FXML
    private void update(ActionEvent actionEvent) {
    }

    @FXML
    private void delete(ActionEvent actionEvent) {
    }

//    private void loadContratoList() {
//        tfTitulo.setText("");
//        tfObjetivo.setText("");
//        tfPrecio.setText("");
//        cbHabilityLevel.getSelectionModel().selectFirst();
//        lvContratos.getItems().clear();
//        contratistaViewModel.getAllContratos();
//        //todo lo que sea llamar al getPrincipalController(), nose si por que esta funcion esta en el inizialize, supongo que es por eso, no funciona
////        getPrincipalController().rootScreenPrincipal.setCursor(Cursor.WAIT);
//    }

    private void changeStatus() {
        contratistaViewModel.getState().addListener((observableValue, state, newState) ->
                Platform.runLater(() -> {
                    if (newState.getMessage() != null) {
                        alert("Error", newState.getMessage(), Alert.AlertType.ERROR);
                    }
                    if (newState.getContratoList() != null) {
                        lvContratos.getItems().clear();
                        lvContratos.getItems().addAll(newState.getContratoList());
                    }
                    if(newState.isAdded()) {
                        clearFields();
                        buttonsRestart();
                        alert("Success", "Contrato added", Alert.AlertType.INFORMATION);
                    }
                    if(newState.isUpdated()) {
                        clearFields();
                        buttonsRestart();
                        alert("Success", "Contrato updated", Alert.AlertType.INFORMATION);
                    }
                    if (newState.isDeleted()) {
                        clearFields();
                        buttonsRestart();
                        alert("Success", "Contrato deleted", Alert.AlertType.INFORMATION);
                    }
                    if (newState.getSicarioList() != null){
                        sicarioComboBox.setDisable(false);
                        sicarioComboBox.getItems().clear();
                        btnContratoSendToSicario.setDisable(false);
                        sicarioComboBox.getItems().addAll(newState.getSicarioList());
                    }
                    if (newState.isSend()){
                        clearFields();
                        buttonsRestart();
                        alert("Success", "Contrato sent to the sicario", Alert.AlertType.INFORMATION);
                    }
                    if (newState.isLoading()){
                        getPrincipalController().rootScreenPrincipal.setCursor(Cursor.DEFAULT);
                    }
                })
        );

    }

    private void alert(String titulo, String texto, Alert.AlertType tipo){
        alerta.setTitle(titulo);
        alerta.setContentText(texto);
        alerta.setAlertType(tipo);
        alerta.showAndWait();
    }

    private void clearFields() {
        tfTitulo.clear();
        tfObjetivo.clear();
        tfPrecio.clear();
        cbHabilityLevel.getSelectionModel().selectFirst();
        sicarioComboBox.getItems().clear();
    }

    private void buttonsRestart() {
        btnAdd.setDisable(false);
        btnUpdate.setDisable(true);
        btnDelete.setDisable(true);
        btnContratoSendToSicario.setDisable(true);
    }


    private void enableAndDisableOfButtonsOfSelectedItemFromList() {
        btnAdd.setDisable(true);
        btnUpdate.setDisable(false);
        btnDelete.setDisable(false);
        tfTitulo.setText(lvContratos.getSelectionModel().getSelectedItem().getTitulo());
        tfObjetivo.setText(lvContratos.getSelectionModel().getSelectedItem().getObjetivo());
        tfPrecio.setText(String.valueOf(lvContratos.getSelectionModel().getSelectedItem().getPrecio()));
        cbHabilityLevel.getSelectionModel().select(lvContratos.getSelectionModel().getSelectedItem().getNivelHabilidad());
        int level = lvContratos.getSelectionModel().getSelectedItem().getNivelHabilidad();
        getPrincipalController().rootScreenPrincipal.setCursor(Cursor.WAIT);
        contratistaViewModel.getSicariosByHabilityLevel(level);
    }


    @FXML
    private void cleanSelection(ActionEvent actionEvent) {
        lvContratos.getSelectionModel().clearSelection();
        buttonsRestart();
        clearFields();
    }

    @FXML
    private void sendContratoToSicario(ActionEvent actionEvent) {
        Usuario sicario = sicarioComboBox.getSelectionModel().getSelectedItem();
        Detalle contrato = lvContratos.getSelectionModel().getSelectedItem();
        SicarioContrato sicarioContrato = new SicarioContrato(contrato.getIdContratista(), sicario.getId());
        contratistaViewModel.sendContratoToSicario(sicarioContrato);
    }

    @FXML
    private void chargeMyContratos(ActionEvent actionEvent) {
        tfTitulo.setText("");
        tfObjetivo.setText("");
        tfPrecio.setText("");
        cbHabilityLevel.getSelectionModel().selectFirst();
        lvContratos.getItems().clear();
        contratistaViewModel.getContratosByContratista(getPrincipalController().getUsuario().getId());
        getPrincipalController().rootScreenPrincipal.setCursor(Cursor.WAIT);
    }
}
