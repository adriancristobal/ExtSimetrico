package ui.screens.fxType.contratista;

import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.Contrato;
import model.SicarioContrato;
import service.ServiceContratoClient;
import service.ServiceSicarioContratoClient;
import service.impl.ServiceContratoClientImpl;
import service.impl.ServiceSicarioContratoClientImpl;

public class ContratistaViewModel {

    private final ServiceContratoClient serviceContratoClient;

    private final ServiceSicarioContratoClient serviceSicarioContratoClient;

    @Inject
    public ContratistaViewModel(ServiceContratoClientImpl serviceContratoClient, ServiceSicarioContratoClientImpl serviceSicarioContratoClient) {
        this.serviceContratoClient = serviceContratoClient;
        this.serviceSicarioContratoClient = serviceSicarioContratoClient;
        _state = new SimpleObjectProperty<>(new ContratistaState(null,null, false,false,false,false,false,false, null));
    }

    private final ObjectProperty<ContratistaState> _state;
    public ReadOnlyObjectProperty<ContratistaState> getState() {
        return _state;
    }

    public void getContratosByContratista(int idUsuarioContratista) {
        serviceContratoClient.getContratosByIdContratista(idUsuarioContratista)
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
    public void getAllContratos(/*int idUsuarioContratista*/) {
        serviceContratoClient.getAllContratos(/*idUsuarioContratista*/)
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

    public void addContrato(Contrato contrato) {
        serviceContratoClient.postContrato(contrato)
                .subscribeOn(Schedulers.single())
                .subscribe(either -> {
                    ContratistaState contratistaState = null;
                    if (either.isLeft())
                        contratistaState = new ContratistaState(null, null, true, false, false, false, false, !getState().get().isChange(), either.getLeft());
                    else
                        contratistaState = new ContratistaState(null, null,true,true,false, false, false,!getState().get().isChange(), null);
                    _state.setValue(contratistaState);
                });
    }
}
