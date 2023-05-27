package utils;

public class QueryConstants {

    public static final String GET_ALL_CONTRATOS= "SELECT * FROM contratos";
    public static final String ADD_CONTRATO = "INSERT INTO contratos (detalle, clave, id_contratista) VALUES (:detalle, :clave, :id_contratista)";
    public static final String DELETE_CONTRATO = "DELETE FROM contratos WHERE id = ?";
    public static final String UPDATE_CONTRATO = "UPDATE contratos SET detalle = ?, clave = ? WHERE id = ?";


    public static final String GET_ALL_SICARIOS_CONTRATOS = "SELECT * FROM sicarios_contratos";
    public static final String ADD_SICARIO_CONTRATO = "INSERT INTO sicarios_contratos (id_contrato, id_sicario, estado) VALUES (:id_contrato, :id_sicario, :estado)";
    public static final String DELETE_SICARIO_CONTRATO  = "DELETE FROM sicarios_contratos WHERE id_contrato = ? AND id_sicario = ?";
    public static final String UPDATE_SICARIO_CONTRATO = "UPDATE recipes SET estado = ? WHERE id_contrato = ? AND id_sicario = ?";


    public static final String GET_USER_BY_USERNAME = "SELECT * FROM usuarios WHERE nombre = ?";
    public static final String GET_USER_BY_USERNAME_AND_PASSWORD = "SELECT * FROM usuarios WHERE nombre = ? AND contrasenia = ?";
    public static final String GET_ROLE_BY_ID = "SELECT u.tipo FROM usuarios u WHERE u.id = ?";
    public static final String ADD_USER = "INSERT INTO usuarios (nombre, contrasenia, tipo, habilidad) VALUES (:nombre, :contrasenia, :tipo, :habilidad)";

}
