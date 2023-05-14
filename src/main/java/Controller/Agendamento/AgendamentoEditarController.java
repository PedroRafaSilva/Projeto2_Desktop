package Controller.Agendamento;

import Agendamento.Agendamento;
import Agendamento.AgendamentoService;
import Controller.Extra.ExtraEditarController;
import Embarcacao.Embarcacao;
import Embarcacao.EmbarcacaoService;
import Fatura.Fatura;
import Fatura.FaturaService;
import ListaEstadoAgendamento.ListaEstadoAgendamentoService;
import Utilizador.Utilizador;
import Utilizador.UtilizadorService;
import com.example.projeto2_desktop.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class AgendamentoEditarController implements Initializable {

    @FXML
    private ComboBox<String> clienteBox;

    @FXML
    private Label clienteText;

    @FXML
    private DatePicker data;

    @FXML
    private Label dataText;

    @FXML
    private VBox editVbox;

    @FXML
    private ComboBox<String> embarcacaoBox;

    @FXML
    private Label embarcacaoText;

    @FXML
    private Label errorText;

    @FXML
    private ComboBox<String> horaFimBox;

    @FXML
    private Label horaFimText;

    @FXML
    private ComboBox<String> horaInicioBox;

    @FXML
    private Label horaInicioText;

    @FXML
    private VBox infoVbox;

    @FXML
    private Label valor;

    @FXML
    private Label valorText;

    @FXML
    private Button guardarButton;

    @FXML
    private Button alterarButton;

    private int idAgendamento = 0;

    private final UtilizadorService utilizadorService = new UtilizadorService();
    private final EmbarcacaoService embarcacaoService = new EmbarcacaoService();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAllClientes();
        getHoras();
    }

    public void setAlterarButtonNotVisible(){
        ListaEstadoAgendamentoService listaEstadoAgendamentoService = new ListaEstadoAgendamentoService();
        if (listaEstadoAgendamentoService.findEstadoByAgendamento(idAgendamento).getIdestado() != 2){
            alterarButton.setVisible(false);
        }
    }

    public void getData(Agendamento agendamento) {
        clienteBox.setValue(agendamento.getUtilizador().getNome());
        embarcacaoBox.setValue(agendamento.getEmbarcacao().getNome());
        data.setValue(agendamento.getData().toLocalDate());
        horaInicioBox.setValue(String.valueOf(agendamento.getHorainicio()));
        horaFimBox.setValue(String.valueOf(agendamento.getHoraFim()));
        valorText.setText(String.valueOf(agendamento.getValorextras()));
        clienteText.setText(clienteBox.getValue());
        embarcacaoText.setText(embarcacaoBox.getValue());
        dataText.setText(String.valueOf(data.getValue()));
        horaInicioText.setText(String.valueOf(horaInicioBox.getValue()));
        horaFimText.setText(String.valueOf(horaFimBox.getValue()));
        valor.setText(valorText.getText());
        idAgendamento = agendamento.getIdagendamento();
        setAlterarButtonNotVisible();
    }

    public void getAllClientes() {
        for (Utilizador utilizador : utilizadorService.getAllClientes()) {
            clienteBox.getItems().add(utilizador.getNome());
        }
    }

    public void getHoras(){
        horaInicioBox.getItems().addAll("07:00:00", "07:30:00", "08:00:00", "08:30:00", "09:00:00", "09:30:00", "10:00:00",
                "10:30:00", "11:00:00", "11:30:00", "12:00:00", "12:30:00", "13:00:00", "13:30:00", "14:00:00", "14:30:00",
                "15:00:00", "15:30:00", "16:00:00", "16:30:00", "17:00:00", "17:30:00", "18:00:00", "18:30:00", "19:00:00",
                "19:30:00", "20:00:00", "20:30:00");
        horaFimBox.getItems().addAll("07:00:00", "07:30:00", "08:00:00", "08:30:00", "09:00:00", "09:30:00", "10:00:00",
                "10:30:00", "11:00:00", "11:30:00", "12:00:00", "12:30:00", "13:00:00", "13:30:00", "14:00:00", "14:30:00",
                "15:00:00", "15:30:00", "16:00:00", "16:30:00", "17:00:00", "17:30:00", "18:00:00", "18:30:00", "19:00:00",
                "19:30:00", "20:00:00", "20:30:00");
    }

    @FXML
    void getAllEmbarcacoes() {
        embarcacaoBox.getItems().clear();
        EmbarcacaoService embarcacaoService = new EmbarcacaoService();
        for (Embarcacao embarcacao: embarcacaoService.getAllEmbarcacaos()){
            System.out.println(clienteBox.getValue());
            if (embarcacao.getutilizador().getNome().equals(clienteBox.getValue())) {
                embarcacaoBox.getItems().remove(embarcacao.getNome());
                embarcacaoBox.getItems().add(embarcacao.getNome());
            }
        }
    }

    @FXML
    public void criarAgendamento(){
        if (validations()) {
            checkCriacao();
            Stage stage = (Stage) clienteBox.getScene().getWindow();
            stage.close();
        }
    }

    public void checkCriacao(){
        FaturaService faturaService = new FaturaService();
        int idUtilizador = utilizadorService.getClienteByNome(clienteBox.getValue()).getIdutilizador();
        Agendamento agendamento = new Agendamento();
        System.out.println(idAgendamento);
        agendamento.setIdagendamento(idAgendamento);
        agendamento.setData(Date.valueOf(data.getValue()));
        agendamento.setValorextras(Float.valueOf(valor.getText()));
        agendamento.setIdembarcacao(embarcacaoService.getEmbarcacaobyName(embarcacaoBox.getValue()).getIdEmbarcacao());
        agendamento.setHorainicio(Time.valueOf(horaInicioBox.getValue()));
        agendamento.setHorafim(Time.valueOf(horaFimBox.getValue()));
        agendamento.setIdutilizador(idUtilizador);
        agendamento.setIdfatura(faturaService.getFaturaOfMothFromCliente(idUtilizador, data.getValue().getMonthValue()).getIdfatura());
        criationValid(agendamento);

        Fatura fatura = faturaService.getFaturaOfMothFromCliente(idUtilizador, LocalDate.now().getMonthValue());
        fatura.setValoragendamento(fatura.getValoragendamento() - Integer.parseInt(valorText.getText()) + agendamento.getValorextras());
        fatura.setValortotal(fatura.getValoragendamento() + fatura.getValormanutencao() + fatura.getValorembarcacoes());
        faturaService.updateFatura(fatura);
        criationValid(agendamento);
    }

    public boolean validations(){
        AgendamentoService agendamentoService = new AgendamentoService();
        if (clienteBox.getValue() == null || embarcacaoBox.getValue() == null || data.getValue() == null ||
                horaInicioBox.getValue() == null || horaFimBox.getValue() == null){
            errorText.setText("Um ou mais campos estão vazios!!!");
            return false;
        }
        if (data.getValue().isBefore(LocalDate.now())){
            errorText.setText("A data tem que ser futura!");
            return false;
        }

        if (Time.valueOf(horaInicioBox.getValue()).toLocalTime().isAfter(Time.valueOf(horaFimBox.getValue()).toLocalTime())){
            errorText.setText("A hora de início deve ser antes da hora de fim!");
            return false;
        }
        if (Time.valueOf(horaInicioBox.getValue()).toLocalTime().isBefore(LocalTime.now()) && data.getValue().equals(LocalDate.now())){
            errorText.setText("A hora de início deve ser superior á hora atual.");
            return false;
        }
        if (!dataText.getText().equals(String.valueOf(data.getValue()))) {
            if (agendamentoService.checkClienteAgenAt(utilizadorService.getClienteByNome(clienteBox.getValue()).getIdutilizador(), Date.valueOf(data.getValue()))) {
                errorText.setText("Esse cliente já possui um agendanento nesse dia!");
                return false;
            }
        }
        return true;
    }

    public void criationValid(Agendamento agendamento){
        AgendamentoService agendamentoService = new AgendamentoService();
        agendamentoService.updateAgendamento(agendamento);
    }

    @FXML
    void addExtras() throws IOException {
        Stage stage = (Stage) clienteBox.getScene().getWindow();
        stage.close();
        Stage stage1 = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("ExtraEditarView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage1.setScene(scene);
        stage1.initModality(Modality.APPLICATION_MODAL);
        ExtraEditarController extraEditarController = fxmlLoader.getController();
        extraEditarController.getExtraItens(idAgendamento);
        stage1.show();
    }

    @FXML
    void editarAgendamento() {
        infoVbox.setVisible(false);
        editVbox.setVisible(true);
        alterarButton.setVisible(false);
        guardarButton.setVisible(true);
    }
}
