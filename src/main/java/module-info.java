module com.maddev.coozy {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;

    requires java.sql;
    requires jbcrypt;
    requires java.desktop;
    requires de.jensd.fx.glyphs.fontawesome;

    opens com.maddev.coozy to javafx.fxml;
    exports com.maddev.coozy;
    exports com.maddev.coozy.controller;
    opens com.maddev.coozy.controller to javafx.fxml;
    exports com.maddev.coozy.model;
    opens com.maddev.coozy.model to javafx.fxml;
    exports com.maddev.coozy.controller.chore;
    opens com.maddev.coozy.controller.chore to javafx.fxml;
}