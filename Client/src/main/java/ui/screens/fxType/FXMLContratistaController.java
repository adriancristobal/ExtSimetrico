package ui.screens.fxType;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import model.Contrato;
import model.SicarioContrato;
import ui.screens.common.BaseScreenController;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLContratistaController extends BaseScreenController implements Initializable {

    @FXML
    private ListView<Contrato> lvContratos;
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
    private ComboBox<SicarioContrato> sicarioComboBox;
    @FXML
    private Button btnAdd;
    @FXML
    private Button btnUpdate;
    @FXML
    private Button btnDelete;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        cbHabilityLevel.getItems().addAll(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        cbHabilityLevel.getSelectionModel().selectFirst();
        cbHabilityLevel.getSelectionModel().selectFirst();
    }
}
