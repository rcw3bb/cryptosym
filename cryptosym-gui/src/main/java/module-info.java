module xyz.ronella.crypto.symmetric.gui {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires xyz.ronella.crypto.symmetric;

    requires org.slf4j;
    requires org.apache.logging.log4j;

    requires xyz.ronella.casual.trivial;
    requires xyz.ronella.logging.logger.plus;

    opens xyz.ronella.crypto.symmetric.gui.controller to javafx.fxml;
    exports xyz.ronella.crypto.symmetric.gui;
}