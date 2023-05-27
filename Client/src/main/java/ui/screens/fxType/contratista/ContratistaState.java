package ui.screens.fxType.contratista;

import lombok.Data;
import model.Contrato;
import model.SicarioContrato;
import model.Usuario;
import modelClient.Detalle;

import java.util.List;

@Data
public class ContratistaState {

    private final List<Detalle> contratoList;
    private final List<Usuario> sicarioList;
    private final boolean loading;
    private final boolean added;
    private final boolean updated;
    private final boolean deleted;
    private final boolean change;
    private final String message;
}
