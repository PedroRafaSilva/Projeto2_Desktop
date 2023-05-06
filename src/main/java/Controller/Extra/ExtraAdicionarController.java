package Controller.Extra;

import Agendamento.Agendamento;
import Agendamento.AgendamentoService;
import AgendamentoExtra.AgendamentoExtra;
import AgendamentoExtra.AgendamentoExtraService;
import Controller.Agendamento.AgendamentoVerificarController;
import com.example.projeto2_desktop.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ExtraAdicionarController {

    @FXML
    private ScrollPane scroll;

    @FXML
    private VBox vBox;

    private final AgendamentoExtraService agendamentoExtraService = new AgendamentoExtraService();

    private final List<ExtraItemController> itemControllerList = new ArrayList<>();

    private int idAgendamento = 0;

    public void getExtraItens(int idagendamento){
        idAgendamento = idagendamento;
        for(AgendamentoExtra agendamentoExtra: agendamentoExtraService.findAgendamentoExtraByIdAgendamento(idagendamento)){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("ExtraItemView.fxml"));
            try {
                HBox hBox = fxmlLoader.load();
                ExtraItemController extraItemController = fxmlLoader.getController();
                extraItemController.getExtrasFromAgendamento(agendamentoExtra);
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
    void addExtra() throws IOException {
        createAgendamentoExtra();
        openVerificarAgend();
    }

    public void openVerificarAgend() throws IOException {
        AgendamentoService agendamentoService = new AgendamentoService();
        Stage stage = (Stage) vBox.getScene().getWindow();
        stage.close();
        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("AgendamentoVerificarView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        AgendamentoVerificarController agendamentoVerificarController = fxmlLoader.getController();
        agendamentoVerificarController.getData(agendamentoService.findMostRecentAgendamento());
        stage.show();
    }

    public void createAgendamentoExtra(){
        AgendamentoService agendamentoService = new AgendamentoService();
        AgendamentoExtraService agendamentoExtraService = new AgendamentoExtraService();
        Agendamento agendamento = agendamentoService.getAgendamentoById(idAgendamento);
        float valorTotal = 0;
        for(ExtraItemController extraItemController: itemControllerList){
            if(extraItemController.getQuantidade() != 0) {
                AgendamentoExtra agendamentoExtra = new AgendamentoExtra();
                agendamentoExtra.setIdagendamento(agendamento.getIdagendamento());
                agendamentoExtra.setIdextra(extraItemController.getIdText());
                agendamentoExtra.setQtd(extraItemController.getQuantidade());
                agendamentoExtra.setValorextra(extraItemController.getPrecoTotalExtra());
                agendamentoExtraService.updateAgendamentoExtra(agendamentoExtra);
                valorTotal += extraItemController.getPrecoTotalExtra();
            }
        }
        agendamento.setValorextras(valorTotal);
        agendamentoService.updateAgendamento(agendamento);
    }

}
