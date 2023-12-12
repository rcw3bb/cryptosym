module xyz.ronella.crypto.symmetric {
    requires org.slf4j;

    requires xyz.ronella.casual.trivial;
    requires xyz.ronella.logging.logger.plus;

    exports xyz.ronella.crypto.symmetric;
    exports xyz.ronella.crypto.symmetric.generator;
    exports xyz.ronella.crypto.symmetric.util;
    exports xyz.ronella.crypto.symmetric.util.impl;
}