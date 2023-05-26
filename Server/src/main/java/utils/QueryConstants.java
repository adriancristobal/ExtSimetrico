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


    public static final String GET_USER_BY_EMAIL = "SELECT * FROM users WHERE email = ?";
    public static final String GET_USER_BY_USERNAME = "SELECT * FROM users WHERE username = ?";
    public static final String GET_USER_BY_USERNAME_AND_PASSWORD = "SELECT * FROM users WHERE username = ? AND password = ?";
    public static final String GET_ROLE_BY_ID = "SELECT r.name FROM users u INNER JOIN users_roles ur ON u.id = ur.user_id INNER JOIN roles r ON ur.role_id = r.id WHERE u.id = ?";
    public static final String ADD_USER = "INSERT INTO users (username, email, password, date_register, activated, activation_code) VALUES (:username, :email, :password, :date_register, :activated, :activation_code)";
    public static final String GET_USER_BY_ACTIVATION_CODE = "SELECT * FROM users WHERE activation_code = ?";
    public static final String UPDATE_ACTIVATED_BY_ACTIVATION_CODE = "UPDATE users SET activated = ? WHERE activation_code = ?";

}
