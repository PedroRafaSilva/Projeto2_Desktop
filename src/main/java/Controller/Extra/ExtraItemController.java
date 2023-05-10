package Controller.Extra;

import Agendamento.AgendamentoService;
import AgendamentoExtra.AgendamentoExtra;
import Extra.Extra;
import Extra.ExtraService;
import ListaEstadoAgendamento.ListaEstadoAgendamentoService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

public class ExtraItemController{

    @FXML
    private Label idText;

    @FXML
    private Label nomeText;

    @FXML
    private Label preco;

    @FXML
    private Label precoTotal;

    @FXML
    private Label qtd;

    @FXML
    private ImageView minusButton;

    @FXML
    private ImageView plusButton;

    private int quantidade = 0;
    private float precoTotalExtra = 0;
    private final ExtraService extraService = new ExtraService();

    private ListaEstadoAgendamentoService listaEstadoAgendamentoService = new ListaEstadoAgendamentoService();

    public void getExtra(Extra extra){
        idText.setText(String.valueOf(extra.getIdextra()));
        nomeText.setText(extra.getDescricao());
        preco.setText(String.valueOf(extra.getValoratualextra()));
        precoTotal.setText(String.valueOf(precoTotalExtra));
        qtd.setText(String.valueOf(quantidade));
    }

    public void getExtrasFromAgendamento(AgendamentoExtra agendamentoExtra){
        Extra extra = extraService.getExtraById(agendamentoExtra.getIdextra());
        idText.setText(String.valueOf(extra.getIdextra()));
        nomeText.setText(extra.getDescricao());
        preco.setText(String.valueOf(extra.getValoratualextra()));
        precoTotal.setText(String.valueOf(agendamentoExtra.getValorextra()));
        qtd.setText(String.valueOf(agendamentoExtra.getQtd()));
        quantidade = agendamentoExtra.getQtd();
        precoTotalExtra = agendamentoExtra.getValorextra();
        noUpdate(agendamentoExtra);
    }

    public void noUpdate(AgendamentoExtra agendamentoExtra){
        if(listaEstadoAgendamentoService.findEstadoByAgendamento(agendamentoExtra.getIdagendamento()).getIdestado() != 2) {
            plusButton.setVisible(false);
            minusButton.setVisible(false);
        }
    }

    @FXML
    void add() {
        quantidade += 1;
        precoTotalExtra = Float.parseFloat(preco.getText()) * quantidade;
        qtd.setText(String.valueOf(quantidade));
        precoTotal.setText(String.valueOf(precoTotalExtra));

    }

    @FXML
    void subtract() {
        if (quantidade != 0) {
            quantidade -= 1;
            precoTotalExtra = Float.parseFloat(preco.getText()) * quantidade;
            qtd.setText(String.valueOf(quantidade));
            precoTotal.setText(String.valueOf(precoTotalExtra));
        }
    }

    public float getPrecoTotalExtra() {
        return precoTotalExtra;
    }

    public int getIdText() {
        return Integer.parseInt(idText.getText());
    }

    public int getQuantidade() {
        return quantidade;
    }
}
