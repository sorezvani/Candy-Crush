module candycrushgame.candycrushnew {
    requires javafx.controls;
    requires javafx.fxml;


    opens CandyCrush to javafx.fxml;
    exports CandyCrush;
}