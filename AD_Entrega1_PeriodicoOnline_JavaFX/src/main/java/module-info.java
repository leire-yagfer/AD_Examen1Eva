module org.example.ad_entrega1_periodicoonline_javafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens org.example.ad_entrega1_periodicoonline_javafx to javafx.fxml;
    exports org.example.ad_entrega1_periodicoonline_javafx;
    exports org.example.ad_entrega1_periodicoonline_javafx.Controller;
    opens org.example.ad_entrega1_periodicoonline_javafx.Controller to javafx.fxml;
}