package ui.screens.common;

public enum Screens {

    LOGIN("/fxml/fxmlAccount/FXMLLogin.fxml"),
    REGISTER("/fxml/fxmlAccount/FXMLRegister.fxml"),
    LOGOUT("/fxml/fxmlAccount/FXMLLogout.fxml"),

    WELCOME ("/fxml/FXMLWelcome.fxml"),
    SCREEN_CONTRATISTAS("/fxml/fxmlType/contratista/FXMLContratista.fxml"),
    SCREEN_SICARIOS("/fxml/fxmlType/sicario/FXMLSicario.fxml");

    private final String rutaScreen;
    Screens(String ruta) {
        this.rutaScreen=ruta;
    }
    public String getRutaScreen(){return rutaScreen;}
}
