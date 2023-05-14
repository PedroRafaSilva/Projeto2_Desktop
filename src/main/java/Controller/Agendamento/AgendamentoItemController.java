package Controller.Agendamento;

import Agendamento.Agendamento;
import Agendamento.AgendamentoService;
import Fatura.Fatura;
import Fatura.FaturaService;
import ListaEstadoAgendamento.ListaEstadoAgendamento;
import ListaEstadoAgendamento.ListaEstadoAgendamentoService;
import com.example.projeto2_desktop.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Optional;

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

    @FXML
    private Label estadoText;

    @FXML
    private ImageView cancelButton;

    private final AgendamentoService agendamentoService = new AgendamentoService();

    private final ListaEstadoAgendamentoService listaEstadoAgendamentoService = new ListaEstadoAgendamentoService();

    public void getData(Agendamento agendamento){
        updateEstado(agendamento);
        nomeText.setText(agendamento.getUtilizador().getNome());
        embarcacaoText.setText(agendamento.getEmbarcacao().getNome());
        HoraInicioText.setText(String.valueOf(agendamento.getHorainicio()));
        horaFimText.setText(String.valueOf(agendamento.getHoraFim()));
        estadoText.setText(listaEstadoAgendamentoService.findEstadoByAgendamento(agendamento.getIdagendamento()).getEstadoAgendamento().getEstado());
        idText.setText(String.valueOf(agendamento.getIdagendamento()));
        checkEstado(estadoText.getText());
        visibleButtons(estadoText.getText(), agendamento);
    }

    public void updateEstado(Agendamento agendamento){
        String estado = String.valueOf(listaEstadoAgendamentoService.findEstadoByAgendamento(agendamento.getIdagendamento()).getEstadoAgendamento());
        ListaEstadoAgendamento listaEstadoAgendamento = new ListaEstadoAgendamento();
        listaEstadoAgendamento.setIdagendamento(agendamento.getIdagendamento());
        listaEstadoAgendamento.setData(LocalDateTime.now());
        if (!estado.equals("Cancelado") && !estado.equals("Concluída") &&
                agendamento.getHorainicio().toLocalTime().isBefore(LocalTime.now()) &&
                agendamento.getHoraFim().toLocalTime().isAfter(LocalTime.now()) &&
                agendamento.getData().toLocalDate().isEqual(LocalDate.now())){
            listaEstadoAgendamento.setIdestado(5);
            listaEstadoAgendamentoService.updateListaEstadoAgendamento(listaEstadoAgendamento);
        }
        if (!estado.equals("Cancelado") && !estado.equals("Concluída") &&
                agendamento.getHorainicio().toLocalTime().isBefore(LocalTime.now()) &&
                agendamento.getData().toLocalDate().isEqual(LocalDate.now())){
            listaEstadoAgendamento.setIdestado(4);
            listaEstadoAgendamentoService.updateListaEstadoAgendamento(listaEstadoAgendamento);
        }
    }


    public void checkEstado(String estado){
        if(estado.equals("Agendado")) {
            estadoText.setStyle("-fx-text-fill: #0b750b;");
        }
        if (estado.equals("Cancelado")) {
            estadoText.setStyle("-fx-text-fill: #d42b15;");
        }
        if (estado.equals("Concluido")) {
            estadoText.setStyle("-fx-text-fill: #07fa07;");
        }
        if (estado.equals("Em curso")) {
            estadoText.setStyle("-fx-text-fill: #03c6fc;");
        }
    }

    public void visibleButtons(String estado, Agendamento agendamento){
        cancelButton.setVisible(true);
        if (estado.equals("Cancelado") || estado.equals("Concluido")
        || agendamento.getHorainicio().toLocalTime().isBefore(LocalTime.now().plusHours(1)) &&
        agendamento.getData().toLocalDate().isEqual(LocalDate.now())){
            cancelButton.setVisible(false);
        }
    }



    @FXML
    void deleteAgendamento() {
        ListaEstadoAgendamento listaEstadoAgendamento = new ListaEstadoAgendamento();
        listaEstadoAgendamento.setIdagendamento(Integer.parseInt(idText.getText()));
        listaEstadoAgendamento.setIdestado(1);
        listaEstadoAgendamento.setData(LocalDateTime.now());
        ButtonType buttonType = new ButtonType("Cancelar Agendamento");
        ButtonType buttonType1 = new ButtonType(" Não Cancelar");
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "Tem a certeza que pretende cancelar o Agendamento?\nEsta ação é irreversivel!",
                buttonType, buttonType1);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonType) {
            listaEstadoAgendamentoService.createListaEstadoAgendamento(listaEstadoAgendamento);
        }

        FaturaService faturaService = new FaturaService();
        Agendamento agendamento = agendamentoService.getAgendamentoById(Integer.parseInt(idText.getText()));
        Fatura fatura = faturaService.getFaturaOfMothFromCliente(agendamento.getIdutilizador(), LocalDate.now().getMonthValue());
        fatura.setValoragendamento(fatura.getValoragendamento() - agendamento.getValorextras());
        fatura.setValortotal(fatura.getValoragendamento() + fatura.getValormanutencao() + fatura.getValorembarcacoes());
        faturaService.updateFatura(fatura);

        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    void editAgendamento() throws IOException {
        Stage stage= new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("AgendamentoEditarView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        AgendamentoEditarController agendamentoEditarController = fxmlLoader.getController();
        agendamentoEditarController.getData(agendamentoService.getAgendamentoById(Integer.parseInt(idText.getText())));
        stage.showAndWait();
        Stage stage1 = (Stage) cancelButton.getScene().getWindow() ;
        stage1.close();
    }
}
