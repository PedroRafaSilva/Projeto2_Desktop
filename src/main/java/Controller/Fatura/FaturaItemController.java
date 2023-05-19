package Controller.Fatura;

import Fatura.Fatura;
import Fatura.FaturaService;
import ListaEstadoFatura.ListaEstadoFaturaService;
import com.example.projeto2_desktop.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class FaturaItemController {

    @FXML
    private Label dataPrazo;

    @FXML
    private Label datacriacao;

    @FXML
    private Label estadoText;

    @FXML
    private Label idText;

    @FXML
    private Label nomeText;

    @FXML
    private Label valorText;

    public void getData(Fatura fatura){
        ListaEstadoFaturaService listaEstadoFaturaService = new ListaEstadoFaturaService();
        String estado = listaEstadoFaturaService.findEstadoByFatura(fatura.getIdfatura()).getEstadoPagamento().getEstado();
        nomeText.setText(fatura.getUtilizador().getNome());
        datacriacao.setText(fatura.getDatacriacao().toString());
        dataPrazo.setText(fatura.getPrazo().toString());
        valorText.setText(fatura.getValortotal() + "€");
        estadoText.setText(estado);
        idText.setText(String.valueOf(fatura.getIdfatura()));
    }


    @FXML
    void editAgendamento() throws IOException {
        FaturaService faturaService = new FaturaService();
        Stage stage= new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FaturaEditarView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        FaturaEditarController faturaEditarController = fxmlLoader.getController();
        faturaEditarController.getData(faturaService.getFaturaById(Integer.parseInt(idText.getText())));
        stage.showAndWait();
    }

}
