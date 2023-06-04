package model;

import lombok.*;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor

public class Contrato {

    private int id;
    private String detalle;
    private String clave;
    private int id_contratista;

    public Contrato(int id, String detalle) {
        this.id = id;
        this.detalle = detalle;
    }

    @Override
    public String toString() {
        return id + " - " + detalle;
    }
}
