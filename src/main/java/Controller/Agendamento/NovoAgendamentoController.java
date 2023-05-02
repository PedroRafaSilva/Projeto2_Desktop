package Controller.Agendamento;

import Embarcacao.Embarcacao;
import Embarcacao.EmbarcacaoService;
import Utilizador.Utilizador;
import Utilizador.UtilizadorService;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

import java.net.URL;

import java.util.ResourceBundle;

public class NovoAgendamentoController implements Initializable {

    @FXML
    private ComboBox<String> clienteBox;

    @FXML
    private DatePicker dataAgen;

    @FXML
    private TextField duracaoText;

    @FXML
    private ComboBox<String> embarcacaoBox;

    @FXML
    private TextField horaText;

    @FXML
    private Button registarButton;

    @FXML
    private TextField valorText;

    private UtilizadorService utilizadorService = new UtilizadorService();

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
    void getAllEmbarcacoes(MouseEvent event) {
        EmbarcacaoService embarcacaoService = new EmbarcacaoService();
        for (Embarcacao embarcacao: embarcacaoService.getAllEmbarcacaos()){
            if (embarcacao.getUtilizador().getNome().equals(clienteBox.getValue())) {
                embarcacaoBox.getItems().remove(embarcacao.getNome());
                embarcacaoBox.getItems().add(embarcacao.getNome());
            }
        }

    }

}



