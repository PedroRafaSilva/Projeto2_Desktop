package Controller;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.net.URL;
import java.util.ResourceBundle;

public class AgendamentoController{

    @FXML
    private AnchorPane pane1, pane2;

    @FXML
    void getMenu(MouseEvent event) {
        if (pane1.isVisible()){
            pane1.setVisible(false);
        }
        else {
            pane1.setVisible(true);
        }
    }

}
