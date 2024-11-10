module org.example.ad_entrega7_bueno_crudcoches_hibernate1an_javafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens org.example.ad_entrega7_bueno_crudcoches_hibernate1an_javafx to javafx.fxml;
    exports org.example.ad_entrega7_bueno_crudcoches_hibernate1an_javafx;
}