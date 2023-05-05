package Controller.Agendamento;

import Agendamento.Agendamento;
import Embarcacao.Embarcacao;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

public class AgendamentoItemController {

    @FXML
    private Label HoraInicioText;

    @FXML
    private Label embarcacaoText;

    @FXML
    private Label horaFimText;

    @FXML
    private Label idText;

    @FXML
    private Label nomeText;

    public void getData(Agendamento agendamento){
        nomeText.setText(agendamento.getUtilizador().getNome());
        embarcacaoText.setText(agendamento.getEmbarcacao().getNome());
        HoraInicioText.setText(String.valueOf(agendamento.getHorainicio()));
        horaFimText.setText(String.valueOf(agendamento.getHoraFim()));
        idText.setText(String.valueOf(agendamento.getIdagendamento()));

    }



    @FXML
    void deleteCliente(MouseEvent event) {

    }

    @FXML
    void editCliente(MouseEvent event) {

    }
}
