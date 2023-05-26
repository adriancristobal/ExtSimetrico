package ui.screens.fxAccount.register;

import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import model.Usuario;
import service.ServiceLogeoClient;
import service.impl.ServiceLogeoClientImpl;

public class RegisterViewModel {

    private final ServiceLogeoClient serviceLogeoClient;

    @Inject
    public RegisterViewModel(ServiceLogeoClientImpl serviceLogeoClient) {
        this.serviceLogeoClient = (ServiceLogeoClient) serviceLogeoClient;
        _state = new SimpleObjectProperty<>(new RegisterState(false,false, null));
    }

    private final ObjectProperty<RegisterState> _state;
    public ReadOnlyObjectProperty<RegisterState> getState() {
        return _state;
    }

    public void register(Usuario userRegisterDTO) {
        serviceLogeoClient.register(userRegisterDTO)
                .subscribeOn(Schedulers.io())
                .subscribe(either -> {
                    if (either.isRight()) {
                        either.peek(result -> _state.setValue(new RegisterState(result, !getState().get().isChange(), null)));
                    } else if (either.isLeft()) {
                        _state.setValue(new RegisterState(false, !getState().get().isChange(), either.getLeft()));
                    }
                });
    }
}
