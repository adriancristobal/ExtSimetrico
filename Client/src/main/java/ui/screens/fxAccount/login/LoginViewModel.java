package ui.screens.fxAccount.login;

import io.reactivex.rxjava3.schedulers.Schedulers;
import jakarta.inject.Inject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import service.ServiceLogeoClient;
import service.impl.ServiceLogeoClientImpl;


public class LoginViewModel {

    private final ServiceLogeoClient serviceLogClient;

    @Inject
    public LoginViewModel(ServiceLogeoClientImpl serviceLogClient) {
        this.serviceLogClient = serviceLogClient;
        _state = new SimpleObjectProperty<>(new LoginState(null,false,false, null));
    }

    private final ObjectProperty<LoginState> _state;
    public ReadOnlyObjectProperty<LoginState> getState() {
        return _state;
    }


    public void loginAsync(String username, String password) {
        serviceLogClient.login(username, password)
                .subscribeOn(Schedulers.single())
                .subscribe(either -> {
                    if (either.isRight()) {
                        either.peek(result -> _state.setValue(new LoginState(result,true, !getState().get().isChange(), null)));
                    } else if (either.isLeft()) {
                        _state.setValue(new LoginState(null,false, !getState().get().isChange(), either.getLeft()));
                    }
                });
    }

//    public void loginHeaderAsync(String username, String password) {
//        serviceLogClient.loginHeader(username, password)
//                .subscribeOn(Schedulers.single())
//                .subscribe(either -> {
//                    if (either.isRight()) {
//                        either.peek(result -> _state.setValue(new LoginState(result, !getState().get().isChange(), null)));
//                    } else if (either.isLeft()) {
//                        _state.setValue(new LoginState(false, !getState().get().isChange(), either.getLeft()));
//                    }
//                });
//    }


}
