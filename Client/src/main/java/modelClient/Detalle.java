package modelClient;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Detalle {

    private int idContrato;
    private int idContratista;
    private String titulo;
    private String objetivo;
    private Double precio;
    private int nivelHabilidad;

    public Detalle(String titulo, String objetivo, Double precio) {
        this.titulo = titulo;
        this.objetivo = objetivo;
        this.precio = precio;
    }

    @Override
    public String toString() {
        return idContrato + " - " + titulo + " - " + objetivo + " - " + precio + " - " + nivelHabilidad;
    }

}
