module org.example {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.junit.jupiter.engine;

    opens org.example to javafx.fxml;
    opens pojo to javafx.base;
    exports org.example;
    exports configs;
    exports pojo;
    exports services;
}
