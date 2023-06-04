package ui.screens.fxType.sicario;

import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.SicarioContrato;
import service.ServiceContratoClient;
import service.ServiceSicarioContratoClient;
import service.impl.ServiceContratoClientImpl;
import service.impl.ServiceSicarioContratoClientImpl;
import ui.screens.fxType.contratista.ContratistaState;

public class SicarioViewModel {

    private final ServiceContratoClient serviceContratoClient;

    private final ServiceSicarioContratoClient serviceSicarioContratoClient;

    @Inject
    public SicarioViewModel(ServiceContratoClientImpl serviceContratoClient, ServiceSicarioContratoClientImpl serviceSicarioContratoClient) {
        this.serviceContratoClient = serviceContratoClient;
        this.serviceSicarioContratoClient = serviceSicarioContratoClient;
        _state = new SimpleObjectProperty<>(new SicarioState(null, null,false,false,false,null));
    }

    private final ObjectProperty<SicarioState> _state;
    public ReadOnlyObjectProperty<SicarioState> getState() {
        return _state;
    }

    public void getContratosBySicario(int idUsuarioSicario) {
        serviceContratoClient.getContratosByIdSicario(idUsuarioSicario)
                .subscribeOn(Schedulers.single())
                .subscribe(either -> {
                    SicarioState sicarioState = null;
                    if (either.isLeft())
                        sicarioState = new SicarioState(null, null,true, false, !getState().get().isChange(), either.getLeft());
                    else
                        sicarioState = new SicarioState(either.get(), null,true, false, !getState().get().isChange(), null);
                    _state.setValue(sicarioState);
                });
    }

    public void getSicarioContratosBySicario(int idUsuarioSicario) {
        serviceSicarioContratoClient.getSicarioContratosBySicario(idUsuarioSicario)
                .subscribeOn(Schedulers.single())
                .subscribe(either -> {
                    SicarioState sicarioState = null;
                    if (either.isLeft())
                        sicarioState = new SicarioState(null, null,true, false, !getState().get().isChange(), either.getLeft());
                    else
                        sicarioState = new SicarioState(null, either.get(), true, false, !getState().get().isChange(), null);
                    _state.setValue(sicarioState);
                });
    }

    public void updateEstado(SicarioContrato sicarioContrato) {
        serviceSicarioContratoClient.updateEstado(sicarioContrato)
                .subscribeOn(Schedulers.single())
                .subscribe(either -> {
                    SicarioState sicarioState = null;
                    if (either.isLeft())
                        sicarioState = new SicarioState(null, null,true, false, !getState().get().isChange(), either.getLeft());
                    else
                        sicarioState = new SicarioState(null, null,true, true, !getState().get().isChange(), null);
                    _state.setValue(sicarioState);
                });
    }
}
