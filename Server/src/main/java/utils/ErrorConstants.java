package utils;

public class ErrorConstants {

    //GENERAL
    public static final String ERROR_GENERATE_JWT = "Error with generate JWT";

    //REGISTER
    public static final String ERROR_FIND_USER_WITH_EMAIL = "Error finding user with email";
    public static final String ERROR_FIND_USER_WITH_USERNAME = "Error finding user with username";
    public static final String ERROR_FIND_USER_WITH_USERNAME_AND_PASSWORD = "Error finding user with username and password";
    public static final String ERROR_ADD_USER = "Error adding user";
    public static final String ERROR_UPDATE_USER_ACTIVATED = "Error updating user activated";
    public static final String ERROR_FIND_ROLE_WITH_ID = "Error finding role with id";


    public static final String ERROR_LOGIN = "You must login, access forbidden";
    public static final String ERROR_NOT_NUMBER = "Error, not a number";
    public static final String ERROR_ADD_LOGIN_PSP = "Error, add login psp";
    public static final String ERROR_FIND_USER_WITH_ACTIVATION_CODE = "Error, find user with activation code";
    public static final String ERROR_INSERT_USER = "Error, insert user";
    public static final String ERROR_FIND_ROL_WITH_USER_ID_ROL_IS_NULL = "Error, find rol with user id.Rol is null";
    public static final String ERROR_FIND_ROL_WITH_USER_ID = "Error, find rol with user id";


    //INGREDIENT

    public static final String ERROR_FIND_SICARIOS_CONTRATOS_LIST = "Error finding sicariosContratos list";
    public static final String ERROR_UPDATE_SICARIO_CONTRATO = "Error updating sicarioContrato";
    public static final String ERROR_INSERT_SICARIO_CONTRATO = "Error inserting sicarioContrato";
    public static final String ERROR_DELETE_SICARIO_CONTRATO = "Error deleting sicarioContrato";
    public static final String ERROR_DELETE_CONTRATO_INTERNAL_SERVER = "Internal server error deleting contrato";
    public static final String ERROR_UPDATE_CONTRATO_INTERNAL_SERVER = "Internal server error updating contrato";
    public static final String ERROR_UPDATE_CONTRATO_NOT_FOUND_ID = "Error updating contrato, not found id";


    //RECIPE

    public static final String ERROR_FIND_CONTRATO_LIST = "Error finding contrato list";
    public static final String ERROR_FIND_RECIPE_WITH_ID = "Error finding recipe with id";
    public static final String ERROR_INSERT_CONTRATO = "Error inserting contrato";
    public static final String ERROR_DELETE_CONTRATO = "Error deleting contrato";
    public static final String ERROR_DELETE_RECIPE_INTERNAL_SERVER = "Internal server error deleting recipe";
    public static final String ERROR_UPDATE_RECIPE_INTERNAL_SERVER = "Internal server error updating recipe";
    public static final String ERROR_UPDATE_RECIPE_NOT_FOUND_ID = "Error updating recipe, not found id";

    //LOGIN
    public static final String ERROR_FIND_USERS_LIST = "Error finding users list";

    //LOGIN_PSP
    public static final String ERROR_FIND_USERS_PSP_LIST = "Error finding users psp list";
    public static final String ERROR_UPDATE_USER_NOT_FOUND_EMAIL = "Error updating user, not found email";
    public static final String ERROR_UPDATE_USER_INTERNAL_SERVER =  "Error updating user";
}
