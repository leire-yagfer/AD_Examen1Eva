module org.example.ad_entrega2_tiendacosmetica_javafx {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires jdk.compiler;
    requires java.sql;
    requires java.desktop;

    opens org.example.ad_entrega2_tiendacosmetica_javafx to javafx.fxml;
    exports org.example.ad_entrega2_tiendacosmetica_javafx;
    exports org.example.ad_entrega2_tiendacosmetica_javafx.Controller;
    opens org.example.ad_entrega2_tiendacosmetica_javafx.Controller to javafx.fxml;
}