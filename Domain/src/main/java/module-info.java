module Domain {
    requires lombok;
    requires org.apache.logging.log4j;
    requires java.logging;
    requires io.vavr;
    requires java.sql;

    opens model;
    opens model.exception;
    opens model.enums;

    exports model;
    exports model.exception;
    exports model.enums;

}