module com.tubes2_btc {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;

    opens com.tubes2_btc to javafx.fxml;
    exports com.tubes2_btc;
}