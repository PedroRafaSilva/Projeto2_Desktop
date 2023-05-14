package Controller.PedidoManutencao;

import Controller.Login.LoginController;
import Embarcacao.Embarcacao;
import Embarcacao.EmbarcacaoService;
import Fatura.Fatura;
import Fatura.FaturaService;
import Oficina.Oficina;
import Oficina.OficinaService;
import PedidoDescricao.PedidoDescricao;
import PedidoDescricao.PedidoDescricaoService;
import PedidoManutencao.PedidoManutencao;
import PedidoManutencao.PedidoManutencaoService;
import Utilizador.Utilizador;
import Utilizador.UtilizadorService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class PedidoEditarController implements Initializable {

    @FXML
    private Button alterarButton;

    @FXML
    private Label funcionarioText;

    @FXML
    private DatePicker data;

    @FXML
    private Label dataText;

    @FXML
    private ComboBox<String> descricaoBox;

    @FXML
    private VBox editVbox;

    @FXML
    private ComboBox<String> embarcacaoBox;

    @FXML
    private Label embarcacaoText;

    @FXML
    private Label errorText;

    @FXML
    private ComboBox<String> funcionarioBox;

    @FXML
    private Button guardarButton;

    @FXML
    private Label descricaoText;

    @FXML
    private VBox infoVbox;

    @FXML
    private ComboBox<String> oficinaBox;

    @FXML
    private Label oficinaText;

    @FXML
    private Label valor;

    @FXML
    private Label valorText;

    private int idPedido = 0;

    private final UtilizadorService utilizadorService = new UtilizadorService();
    private final EmbarcacaoService embarcacaoService = new EmbarcacaoService();
    private final OficinaService oficinaService = new OficinaService();

    private final PedidoDescricaoService pedidoDescricaoService = new PedidoDescricaoService();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAllFuncionarios();
        getAllOficinas();
        getAllDescricoes();
        getAllEmbarcacoes();
    }

    public void getAllFuncionarios() {
        if (utilizadorService.getUtilizadorById(LoginController.getUserId()).getIdtipoutilizador() == 2) {
            funcionarioBox.setValue(utilizadorService.getUtilizadorById(LoginController.getUserId()).getNome());
            funcionarioBox.setDisable(true);
        } else {
            for (Utilizador utilizador : utilizadorService.getAllFuncionarios()) {
                funcionarioBox.getItems().add(utilizador.getNome());
            }
        }
    }

    public void getAllOficinas() {
        for (Oficina oficina : oficinaService.getAllOficinas()) {
            oficinaBox.getItems().add(oficina.getNome());
        }
    }

    public void getAllEmbarcacoes() {
        embarcacaoBox.getItems().clear();
        for (Embarcacao embarcacao: embarcacaoService.getAllEmbarcacaos()){
            embarcacaoBox.getItems().add(embarcacao.getNome());
        }
    }

    public void getAllDescricoes() {
        for (PedidoDescricao pedidoDescricao : pedidoDescricaoService.getAllPedidoDescricaos()) {
            descricaoBox.getItems().add(pedidoDescricao.getDescricao());
        }
    }

    @FXML
    public void getValue(){
        valorText.setText(String.valueOf(pedidoDescricaoService.getPedidoDescricaoById(descricaoBox.getValue()).getValor()));
    }

    public void setAlterarButtonNotVisible(){
        if (!LocalDate.now().isBefore(data.getValue())){
            alterarButton.setVisible(false);
        }
    }

    public void getData(PedidoManutencao pedidoManutencao) {
        funcionarioBox.setValue(pedidoManutencao.getUtilizador().getNome());
        embarcacaoBox.setValue(pedidoManutencao.getEmbarcacao().getNome());
        data.setValue(pedidoManutencao.getData().toLocalDate());
        oficinaBox.setValue(pedidoManutencao.getOficina().getNome());
        descricaoBox.setValue(pedidoManutencao.getDescricao());
        valorText.setText(String.valueOf(pedidoManutencao.getValor()));
        funcionarioText.setText(funcionarioBox.getValue());
        embarcacaoText.setText(embarcacaoBox.getValue());
        dataText.setText(String.valueOf(data.getValue()));
        oficinaText.setText(oficinaBox.getValue());
        descricaoText.setText(descricaoBox.getValue());
        valor.setText(valorText.getText());
        idPedido = pedidoManutencao.getIdpedido();
        setAlterarButtonNotVisible();
    }

    @FXML
    void criarPedido() {
        if (validations()) {
            checkCriacao();
            Stage stage = (Stage) funcionarioBox.getScene().getWindow();
            stage.close();
        }
    }

    public void checkCriacao(){
        FaturaService faturaService = new FaturaService();
        int idUtilizador = utilizadorService.getUtilizadorByNome(funcionarioBox.getValue()).getIdutilizador();
        PedidoManutencao pedidoManutencao = new PedidoManutencao();
        pedidoManutencao.setIdpedido(idPedido);
        pedidoManutencao.setData(Date.valueOf(data.getValue()));
        pedidoManutencao.setValor(Float.parseFloat(valorText.getText()));
        pedidoManutencao.setIdembarcacao(embarcacaoService.getEmbarcacaobyName(embarcacaoBox.getValue()).getIdEmbarcacao());
        pedidoManutencao.setIdutilizador(idUtilizador);
        pedidoManutencao.setIdfatura(faturaService.getFaturaOfMothFromCliente(embarcacaoService.getEmbarcacaobyName(embarcacaoBox.getValue()).getutilizador().getIdutilizador(), data.getValue().getMonthValue()).getIdfatura());
        pedidoManutencao.setDescricao(descricaoBox.getValue());
        pedidoManutencao.setIdoficina(oficinaService.getOficinaByName(oficinaBox.getValue()).getIdoficina());

        Fatura fatura = faturaService.getFaturaOfMothFromCliente(idUtilizador, LocalDate.now().getMonthValue());
        fatura.setValormanutencao(fatura.getValormanutencao() - Float.parseFloat(valor.getText()) + pedidoManutencao.getValor());
        faturaService.updateFatura(fatura);
        criationValid(pedidoManutencao);
    }

    public boolean validations(){
        PedidoManutencaoService pedidoManutencaoService = new PedidoManutencaoService();
        if (funcionarioBox.getValue() == null || embarcacaoBox.getValue() == null || data.getValue() == null ||
                descricaoBox.getValue() == null || oficinaBox.getValue() == null){
            errorText.setText("Um ou mais campos estão vazios!!!");
            return false;
        }
        if (data.getValue().isBefore(LocalDate.now())){
            errorText.setText("A data tem que ser futura!");
            return false;
        }
        if (pedidoManutencaoService.checkPedidoEmbarcacaoAt(embarcacaoService.getEmbarcacaobyName(embarcacaoBox.getValue()).getIdEmbarcacao(), Date.valueOf(data.getValue()))){
            errorText.setText("Essa embarcacao já possui um pedido nesse dia!");
            return false;
        }
        return true;
    }

    public void criationValid(PedidoManutencao pedidoManutencao){
        PedidoManutencaoService pedidoManutencaoService = new PedidoManutencaoService();
        pedidoManutencaoService.updatePedidoManutencao(pedidoManutencao);
    }
    @FXML
    void editarPedido() {
        infoVbox.setVisible(false);
        editVbox.setVisible(true);
        alterarButton.setVisible(false);
        guardarButton.setVisible(true);
    }
}
