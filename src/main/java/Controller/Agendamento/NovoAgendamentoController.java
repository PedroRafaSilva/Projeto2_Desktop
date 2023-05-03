package Controller.Agendamento;

import Agendamento.Agendamento;
import Agendamento.AgendamentoService;
import Embarcacao.Embarcacao;
import Embarcacao.EmbarcacaoService;
import Fatura.FaturaService;
import Route.Routes;
import Utilizador.Utilizador;
import Utilizador.UtilizadorService;
import com.example.projeto2_desktop.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.w3c.dom.events.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ResourceBundle;

public class NovoAgendamentoController implements Initializable {

    @FXML
    private ComboBox<String> clienteBox;

    @FXML
    private Button criarAgendamento;

    @FXML
    private DatePicker data;

    @FXML
    private ComboBox<String> embarcacaoBox;

    @FXML
    private TextField horaFim;

    @FXML
    private TextField horaInicio;

    @FXML
    private Label errorText;

    private UtilizadorService utilizadorService = new UtilizadorService();
    private EmbarcacaoService embarcacaoService = new EmbarcacaoService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getAllClientes();
    }

    public void getAllClientes() {
        for (Utilizador utilizador : utilizadorService.getAllClientes()) {
            clienteBox.getItems().add(utilizador.getNome());
        }
    }
    @FXML
    void getAllEmbarcacoes() {
        embarcacaoBox.getItems().clear();
        EmbarcacaoService embarcacaoService = new EmbarcacaoService();
        for (Embarcacao embarcacao: embarcacaoService.getAllEmbarcacaos()){
            if (embarcacao.getUtilizador().getNome().equals(clienteBox.getValue())) {
                embarcacaoBox.getItems().remove(embarcacao.getNome());
                embarcacaoBox.getItems().add(embarcacao.getNome());
            }
        }

    }

    @FXML
    public void criarAgendamento(ActionEvent event) throws IOException {
        if (validations()) {
            checkCriacao();
            openExtras();
        }
    }

    public void checkCriacao(){
        FaturaService faturaService = new FaturaService();
        int idUtilizador = utilizadorService.getClienteByNome(clienteBox.getValue()).getIdutilizador();

        Agendamento agendamento = new Agendamento();

        agendamento.setData(Date.valueOf(data.getValue()));
        agendamento.setValorextras(0.0F);
        agendamento.setIdembarcacao(embarcacaoService.getEmbarcacaobyName(embarcacaoBox.getValue()).getIdEmbarcacao());
        agendamento.setHorainicio(Time.valueOf(horaInicio.getText()));
        agendamento.setHorafim(Time.valueOf(horaFim.getText()));
        agendamento.setIdutilizador(idUtilizador);
        agendamento.setIdfatura(faturaService.getFaturaOfMothFromCliente(idUtilizador, data.getValue().getMonthValue()).getIdfatura());

        criationValid(agendamento);

    }

    public void openExtras() throws IOException {
        Stage stage = (Stage) clienteBox.getScene().getWindow();
        stage.close();
        Stage stage1 = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("ExtraView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage1.setScene(scene);
        stage1.initModality(Modality.APPLICATION_MODAL);
        stage1.show();
    }

    public boolean validations(){
        AgendamentoService agendamentoService = new AgendamentoService();
        if (clienteBox.getValue() == null || embarcacaoBox.getValue() == null || data.getValue() == null ||
                horaFim.getText().isEmpty() || horaInicio.getText().isEmpty()){
            errorText.setText("Um ou mais campos estão vazios!!!");
            return false;
        }
        if (data.getValue().isBefore(LocalDate.now())){
            errorText.setText("A data tem que ser futura!");
            return false;
        }

        if (agendamentoService.checkClienteAgenAt(utilizadorService.getClienteByNome(clienteBox.getValue()).getIdutilizador(), Date.valueOf(data.getValue()))){
            errorText.setText("Esse cliente já possui um agendmaento nesse dia!");
            return false;
        }
        try {
            LocalTime.parse(horaInicio.getText());
        } catch (Exception e) {
            errorText.setText("A hora tem que ser do tipo HH:mm:ss");
            return false;
        }

        try {
            LocalTime.parse(horaInicio.getText());
        } catch (Exception e) {
            errorText.setText("A hora tem que ser do tipo HH:mm:ss");
            return false;
        }


        if(Time.valueOf(horaInicio.getText()).toLocalTime().isAfter(Time.valueOf(horaFim.getText()).toLocalTime())){
            errorText.setText("A hora de inicio tem que ser antes que a hora de fim!");
            return false;
        }

        return true;
    }

    public void criationValid(Agendamento agendamento){
        AgendamentoService agendamentoService = new AgendamentoService();
        agendamentoService.createAgendamento(agendamento);
    }
}



