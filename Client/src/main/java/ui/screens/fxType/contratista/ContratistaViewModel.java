package ui.screens.fxType.contratista;

import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.SicarioContrato;
import service.ServiceContratoClient;
import service.ServiceSicarioContratoClient;
import service.impl.ServiceContratoClientImpl;

public class ContratistaViewModel {

    private final ServiceContratoClient serviceContratoClient;

    private ServiceSicarioContratoClient serviceSicarioContratoClient;

    @Inject
    public ContratistaViewModel(ServiceContratoClientImpl serviceContratoClient, ServiceSicarioContratoClient serviceSicarioContratoClient) {
        this.serviceContratoClient = serviceContratoClient;
        this.serviceSicarioContratoClient = serviceSicarioContratoClient;
        _state = new SimpleObjectProperty<>(new ContratistaState(null,null, false,false,false,false,false,false, null));
    }

    private final ObjectProperty<ContratistaState> _state;
    public ReadOnlyObjectProperty<ContratistaState> getState() {
        return _state;
    }

    public void getContratosByContratista() {
        serviceContratoClient.getAllContratos()
                .subscribeOn(Schedulers.single())
                .subscribe(either -> {
                    ContratistaState contratistaState = null;
                    if (either.isLeft())
                        contratistaState = new ContratistaState(null, null, true, false, false, false, false,!getState().get().isChange(), either.getLeft());
                    else
                        contratistaState = new ContratistaState(either.get(), null, true,false,false, false, false,!getState().get().isChange(), null);
                    _state.setValue(contratistaState);
                });
    }

    public void getSicariosByHabilityLevel(int habilityLevel) {
        serviceContratoClient.getSicariosByHabilityLevel(habilityLevel)
                .subscribeOn(Schedulers.single())
                .subscribe(either -> {
                    ContratistaState contratistaState = null;
                    if (either.isLeft())
                        contratistaState = new ContratistaState(null, null, true, false, false, false, false,!getState().get().isChange(), either.getLeft());
                    else
                        contratistaState = new ContratistaState(null, either.get(),true,false,false, false, false,!getState().get().isChange(), null);
                    _state.setValue(contratistaState);
                });
    }

    public void sendContratoToSicario(SicarioContrato sicarioContrato) {
        serviceSicarioContratoClient.sendContratoToSicario(sicarioContrato)
                .subscribeOn(Schedulers.single())
                .subscribe(either -> {
                    ContratistaState contratistaState = null;
                    if (either.isLeft())
                        contratistaState = new ContratistaState(null, null, true, false, false, false, false, !getState().get().isChange(), either.getLeft());
                    else
                        contratistaState = new ContratistaState(null, null,true,false,false, false, true,!getState().get().isChange(), null);
                    _state.setValue(contratistaState);
                });
    }
}
