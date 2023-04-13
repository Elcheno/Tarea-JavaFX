module org.javafx {
    requires javafx.controls;
    requires javafx.fxml;

    opens org.javafx to javafx.fxml;
    opens org.javafx.model to javafx.base;
    exports org.javafx;
}
