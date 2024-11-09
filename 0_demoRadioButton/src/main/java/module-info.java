module com.example.demoradiobutton {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens com.example.demoradiobutton to javafx.fxml;
    exports com.example.demoradiobutton;
}