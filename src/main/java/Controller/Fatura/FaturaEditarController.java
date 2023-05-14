package Controller.Fatura;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import Fatura.Fatura;

public class FaturaEditarController {

    @FXML
    private Label clienteText;

    @FXML
    private Label dataCriacaoText;

    @FXML
    private Label dataPrazoText;

    @FXML
    private Label numFiscalText;

    @FXML
    private Label valorAgendamentosText;

    @FXML
    private Label valorEmbarcacoesText;

    @FXML
    private Label valorManutencoesText;

    @FXML
    private Label valorTotalText;


    //Esta função carrega os dados da fatura
    public void getData(Fatura fatura){
        clienteText.setText(fatura.getUtilizador().getNome());
        dataCriacaoText.setText(fatura.getDatacriacao().toString());
        dataPrazoText.setText(fatura.getPrazo().toString());
        numFiscalText.setText(String.valueOf(fatura.getNumfiscal()));
        valorAgendamentosText.setText(String.valueOf(fatura.getValoragendamento()));
        valorEmbarcacoesText.setText(String.valueOf(fatura.getValorembarcacoes()));
        valorManutencoesText.setText(String.valueOf(fatura.getValormanutencao()));
        valorTotalText.setText(String.valueOf(fatura.getValortotal()));
    }
}
