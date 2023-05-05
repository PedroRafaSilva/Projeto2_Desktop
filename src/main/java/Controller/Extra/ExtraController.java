package Controller.Extra;

import Agendamento.Agendamento;
import Agendamento.AgendamentoService;
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
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
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

    private float valorTotal = 0;

    private ExtraService extraService = new ExtraService();

    private  AgendamentoExtraService agendamentoExtraService = new AgendamentoExtraService();

    private List<ExtraItemController> itemControllerList = new ArrayList<>();

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
    void adicionarExtra(ActionEvent event) throws IOException {
        createAgendamentoExtra();
        openVerificarAgend();
    }

    public void openVerificarAgend() throws IOException {
        Stage stage = (Stage) vBox.getScene().getWindow();
        stage.close();
        stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("AgendamentoVerificarView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    public void createAgendamentoExtra(){
        AgendamentoService agendamentoService = new AgendamentoService();
        AgendamentoExtraService agendamentoExtraService = new AgendamentoExtraService();
        Agendamento agendamento = agendamentoService.findMostRecentAgendamento();
        float valorTotal = 0;
        for(ExtraItemController extraItemController: itemControllerList){
            if(extraItemController.getQuantidade() != 0) {
                AgendamentoExtra agendamentoExtra = new AgendamentoExtra();
                agendamentoExtra.setIdagendamento(agendamento.getIdagendamento());
                agendamentoExtra.setIdextra(extraItemController.getIdText());
                agendamentoExtra.setQtd(extraItemController.getQuantidade());
                agendamentoExtra.setValorextra(extraItemController.getPrecoTotalExtra());
                agendamentoExtraService.createAgendamentoExtra(agendamentoExtra);
                valorTotal += extraItemController.getPrecoTotalExtra();
            }
        }
        agendamento.setValorextras(valorTotal);
        agendamentoService.updateAgendamento(agendamento);
    }








}
