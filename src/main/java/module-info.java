module com.tubes2_btc {
    requires javafx.controls;
    requires javafx.fxml;
    // requires javafx.media;
    requires org.controlsfx.controls;
    requires java.desktop;
    requires javafx.media;

    opens com.tubes2_btc to javafx.fxml;
    opens com.tubes2_btc.Controllers to javafx.fxml;
    exports com.tubes2_btc;
}