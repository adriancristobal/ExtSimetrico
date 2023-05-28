package service.jsonConverter;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class ConverterToJsonDetalleString {
    private static final Gson gson = new Gson();

    public static String toJson(String text) {
        String[] parts = text.split(" - ");

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("idContratista", Integer.parseInt(parts[0]));
        jsonObject.addProperty("titulo", parts[1]);
        jsonObject.addProperty("objetivo", parts[2]);
        jsonObject.addProperty("precio", Double.parseDouble(parts[3]));
        jsonObject.addProperty("nivelHabilidad", Integer.parseInt(parts[4]));

        return gson.toJson(jsonObject);
    }
}
