package modelClient;

import lombok.Data;

@Data
public class Detalle {

    private int idContratista;
    private String titulo;
    private String objetivo;
    private Double precio;
    private int nivelHabilidad;


    @Override
    public String toString() {
        return idContratista + " - " + titulo + " - " + objetivo + " - " + precio + " - " + nivelHabilidad;
    }
}
