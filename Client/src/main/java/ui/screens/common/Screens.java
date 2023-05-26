package ui.screens.common;

public enum Screens {

    LOGIN("/fxml/fxmlAccount/FXMLLogin.fxml"),
    REGISTER("/fxml/fxmlAccount/FXMLRegister.fxml"),
    LOGOUT("/fxml/fxmlAccount/FXMLLogout.fxml"),

    WELCOME ("/fxml/FXMLWelcome.fxml"),
    SCREEN_GET_ALL_CONTRATO("/fxml/fxmlContrato/FXMLGetAllContratos.fxml"),
    SCREEN_ADD_CONTRATO("/fxml/fxmlContrato/FXMLAddContrato.fxml"),
    SCREEN_DELETE_CONTRATO("/fxml/fxmlContrato/FXMLDeleteContrato.fxml"),
    SCREEN_UPDATE_CONTRATO("/fxml/fxmlContrato/FXMLUpdateContrato.fxml"),
    SCREEN_GET_ALL_ARTICLE("/fxml/fxmlArticle/FXMLGetAllArticle.fxml"),
    SCREEN_ADD_ARTICLE("/fxml/fxmlArticle/FXMLAddArticle.fxml"),
    SCREEN_DELETE_ARTICLE("/fxml/fxmlArticle/FXMLDeleteArticle.fxml"),
    SCREEN_UPDATE_ARTICLE("/fxml/fxmlArticle/FXMLUpdateArticle.fxml"),
    SCREEN_GET_ALL_READER("/fxml/fxmlReader/FXMLGetAllReader.fxml"),
    SCREEN_ADD_READER("/fxml/fxmlReader/FXMLAddReader.fxml"),
    SCREEN_DELETE_READER("/fxml/fxmlReader/FXMLDeleteReader.fxml"),
    SCREEN_UPDATE_READER("/fxml/fxmlReader/FXMLUpdateReader.fxml");
    private final String rutaScreen;
    Screens(String ruta) {
        this.rutaScreen=ruta;
    }
    public String getRutaScreen(){return rutaScreen;}
}
