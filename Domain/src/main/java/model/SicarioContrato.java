package model;

import lombok.*;
import model.enums.Estado;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
@ToString
public class SicarioContrato {


        private int id_contrato;
        private int id_sicario;
        private Estado estado;
}
