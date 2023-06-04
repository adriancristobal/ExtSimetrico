package ui.screens.fxType.sicario;

import lombok.Data;
import model.SicarioContrato;
import model.Usuario;
import modelClient.Detalle;

import java.util.List;

@Data
public class SicarioState {

    private final List<Detalle> contratoList;
    private final List<SicarioContrato> sicarioList;
    private final boolean loading;
    private final boolean updated;
    private final boolean change;
    private final String message;
}
