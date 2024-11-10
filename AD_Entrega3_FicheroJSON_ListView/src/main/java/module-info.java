module org.example.ad_entrega3_ficherojson_listview {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.fasterxml.jackson.databind;

    opens org.example.ad_entrega3_ficherojson_listview to javafx.fxml;
    exports org.example.ad_entrega3_ficherojson_listview;
    exports org.example.ad_entrega3_ficherojson_listview.Model;
    opens org.example.ad_entrega3_ficherojson_listview.Model to javafx.fxml;
    exports org.example.ad_entrega3_ficherojson_listview.Controller;
    opens org.example.ad_entrega3_ficherojson_listview.Controller to javafx.fxml;
}