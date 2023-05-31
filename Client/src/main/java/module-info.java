module Client {

    requires jakarta.inject;
    requires jakarta.cdi;
    requires javafx.fxml;
    requires lombok;
    requires javafx.graphics;
    requires javafx.controls;
    requires org.yaml.snakeyaml;
    requires org.apache.logging.log4j;
    requires Domain;
    requires io.reactivex.rxjava3;
    requires io.vavr;
    requires com.google.gson;
    requires okhttp3;
    requires retrofit2;
    requires gson.javatime.serialisers;
    requires retrofit2.adapter.rxjava3;
    requires retrofit2.converter.gson;
    requires retrofit2.converter.scalars;
    requires java.sql;
    requires Security;

    opens dao.impl;
    opens common.config;
    opens common.utils;
    opens ui.main;
    opens ui.screens.principal;
    opens ui.screens.fxType.contratista;
    opens ui.screens.fxType.sicario;
    opens ui.screens.fxAccount.login;
    opens ui.screens.fxAccount.register;
    opens modelClient;
    opens service.jsonConverter;




    exports service.impl;
    exports ui.main;
    exports ui.screens.principal;
    exports common.config;
    exports ui.screens.common;
    exports common.utils;
    exports dao.retrofit.calls;
    exports dao.retrofit.produces;
    exports ui.screens.fxType.contratista;
    exports ui.screens.fxType.sicario;
    exports dao.retrofit;
    exports ui.screens.welcome;
    exports ui.screens.fxAccount.register;
    exports ui.screens.fxAccount.logout;
    exports ui.screens.fxAccount.login;
    exports dao.retrofit.produces.constants;
    exports modelClient;
    exports dao.impl;
    exports service.jsonConverter;
}