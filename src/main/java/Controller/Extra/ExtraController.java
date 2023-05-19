package Controller.Extra;

import Agendamento.Agendamento;
import Agendamento.AgendamentoService;
import AgendamentoExtra.AgendamentoExtra;
import AgendamentoExtra.AgendamentoExtraService;
import Controller.Agendamento.AgendamentoVerificarController;
import Extra.Extra;
import Extra.ExtraService;
import com.example.projeto2_desktop.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class ExtraController implements Initializable {

    @FXML
    private VBox vBox;

    @FXML
    private ScrollPane scroll;

    private final ExtraService extraService = new ExtraService();

    private final AgendamentoExtraService agendamentoExtraService = new AgendamentoExtraService();

    private final AgendamentoService agendamentoService = new AgendamentoService();

    private final List<ExtraItemController> itemControllerList = new ArrayList<>();

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
                itemControllerList.add(extraItemController);
                vBox.getChildren().add(hBox);
                scroll.setContent(vBox);
                vBox.setSpacing(10);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void adicionarExtra() throws IOException {
        createAgendamentoExtra();
        openVerificarAgend();
    }

    public void openVerificarAgend() throws IOException {
        Stage stage = (Stage) vBox.getScene().getWindow();
        stage.close();
        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("AgendamentoVerificarView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        AgendamentoVerificarController agendamentoVerificarController = fxmlLoader.getController();
        agendamentoVerificarController.getData(agendamentoService.findMostRecentAgendamento());
        stage.show();
    }

    public void createAgendamentoExtra(){
        Agendamento agendamento = agendamentoService.findMostRecentAgendamento();
        float valorTotal = 0;
        for(ExtraItemController extraItemController: itemControllerList){
            AgendamentoExtra agendamentoExtra = new AgendamentoExtra();
            agendamentoExtra.setIdagendamento(agendamento.getIdagendamento());
            agendamentoExtra.setIdextra(extraItemController.getIdText());
            agendamentoExtra.setQtd(extraItemController.getQuantidade());
            agendamentoExtra.setValorextra(extraItemController.getPrecoTotalExtra());
            agendamentoExtraService.createAgendamentoExtra(agendamentoExtra);
            valorTotal += extraItemController.getPrecoTotalExtra();
        }
        agendamento.setValorextras(valorTotal);
        agendamentoService.updateAgendamento(agendamento);
    }

}
