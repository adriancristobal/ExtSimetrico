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

        public SicarioContrato(int id_contrato, int id_sicario) {
                this.id_contrato = id_contrato;
                this.id_sicario = id_sicario;
        }
}
