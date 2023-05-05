package Controller.Extra;

import AgendamentoExtra.AgendamentoExtraService;
import Extra.Extra;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

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

    private int quantidade = 0;
    private float precoTotalExtra = 0;

    private AgendamentoExtraService agendamentoExtraService = new AgendamentoExtraService();

    public void getExtra(Extra extra){
        idText.setText(String.valueOf(extra.getIdextra()));
        nomeText.setText(extra.getDescricao());
        preco.setText(String.valueOf(extra.getValoratualextra()));
        precoTotal.setText(String.valueOf(precoTotalExtra));
        qtd.setText(String.valueOf(quantidade));
    }

    @FXML
    void add(MouseEvent event) {
        quantidade += 1;
        precoTotalExtra = Float.parseFloat(preco.getText()) * quantidade;
        qtd.setText(String.valueOf(quantidade));
        precoTotal.setText(String.valueOf(precoTotalExtra));

    }

    @FXML
    void subtract(MouseEvent event) {
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
