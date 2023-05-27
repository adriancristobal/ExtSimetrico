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

    @Override
    public String toString() {
        return id + " - " + detalle;
    }
}
