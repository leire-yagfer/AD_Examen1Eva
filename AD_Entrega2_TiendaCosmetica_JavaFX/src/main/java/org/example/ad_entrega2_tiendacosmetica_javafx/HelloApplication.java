package org.example.ad_entrega2_tiendacosmetica_javafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.ad_entrega2_tiendacosmetica_javafx.Util.conexionBBDD;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Principal.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Tienda cosmética");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) throws SQLException, IOException, ClassNotFoundException {
        launch();
    }
}