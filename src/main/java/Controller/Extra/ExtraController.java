package Controller.Extra;

import AgendamentoExtra.AgendamentoExtra;
import AgendamentoExtra.AgendamentoExtraService;
import Controller.Cliente.ClienteItemController;
import Extra.ExtraService;
import Extra.Extra;
import com.example.projeto2_desktop.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ExtraController implements Initializable {

    @FXML
    private VBox vBox;

    @FXML
    private ScrollPane scroll;

    private float valorTotal = 0;

    private ExtraService extraService = new ExtraService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getExtraItens();
    }

    public void getExtraItens(){
        for(Extra extra: extraService.getAllExtras()){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("ExtraItemView.fxml"));
            try {
                HBox hBox = fxmlLoader.load();
                ExtraItemController extraItemController = fxmlLoader.getController();
                extraItemController.getExtra(extra);
                vBox.getChildren().add(hBox);
                scroll.setContent(vBox);
                vBox.setSpacing(10);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void adicionarExtra(ActionEvent event) {
        for (Extra extra: extraService.getAllExtras()){
            ExtraItemController extraItemController = new ExtraItemController();
        }

    }

    public void createAgendamentoExtra(){
        AgendamentoExtraService agendamentoExtraService= new AgendamentoExtraService();
        AgendamentoExtra agendamentoExtra = new AgendamentoExtra();

    }




}
