package Controller.Embarcacao;

import CodPostal.CodPostal;
import Comprimento.Comprimento;
import Comprimento.ComprimentoService;
import Embarcacao.Embarcacao;
import Embarcacao.EmbarcacaoService;
import Marina.Marina;
import Marina.MarinaService;
import Utilizador.Utilizador;
import Utilizador.UtilizadorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EmbarcacaoEditarController implements Initializable {

    @FXML
    private Label comprimento;

    @FXML
    private ComboBox<String> marinaBox;

    @FXML
    private ComboBox<String> titularBox;

    @FXML
    private ComboBox<String> comprimentoBox;

    @FXML
    private VBox editVbox;

    @FXML
    private Button editarButton;

    @FXML
    private Label errorText;

    @FXML
    private Button guardarButton;

    @FXML
    private VBox infoVbox;

    @FXML
    private Label marina;

    @FXML
    private Label nome;

    @FXML
    private TextField nomeText;

    @FXML
    private Label titular;

    private int idEmbarcacao;

    private EmbarcacaoService embarcacaoService = new EmbarcacaoService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getTitularBoxItems();
        getMarinaBoxItems();
        getComprimentoBoxItems();
    }

    @FXML
    void guardar() {
        checkEdicao();
    }

    public void checkEdicao(){
        if (validations()) {
            UtilizadorService utilizadorService = new UtilizadorService();
            MarinaService marinaService = new MarinaService();
            ComprimentoService comprimentoService = new ComprimentoService();
            Embarcacao embarcacao = new Embarcacao();

            embarcacao.setIdEmbarcacao(idEmbarcacao);
            embarcacao.setNome(nomeText.getText());
            embarcacao.setIdUtilizador(utilizadorService.getClienteByNome(titularBox.getValue()).getIdUtilizador());
            embarcacao.setIdMarina(marinaService.getMarinaByName(marinaBox.getValue()).getIdMarina());
            embarcacao.setComprimento(comprimentoService.getComprimentoByName(comprimentoBox.getValue()).getComprimento());
            embarcacaoService.updateEmbarcacao(embarcacao);

            Stage stage = (Stage) nomeText.getScene().getWindow();
            stage.close();
        }
    }

    public void getTitularBoxItems(){
        UtilizadorService utilizadorService = new UtilizadorService();
        for (Utilizador utilizador: utilizadorService.getAllClientes()){
            titularBox.getItems().add(utilizador.getNome());
        }
    }

    public void getMarinaBoxItems(){
        MarinaService marinaService = new MarinaService();
        for (Marina marina: marinaService.getAllMarinas()){
            marinaBox.getItems().add(marina.getNome());
        }
    }

    public void getComprimentoBoxItems(){
        ComprimentoService comprimentoService = new ComprimentoService();
        for (Comprimento comprimento : comprimentoService.getAllComprimentos()){
            comprimentoBox.getItems().add(comprimento.getDescricao());
        }
    }

    public boolean validations() {
        if(nomeText.getText().isEmpty() || titularBox.getValue() == null || marinaBox.getValue() == null
                || comprimentoBox.getValue() == null){
            errorText.setText("Um ou mais campos estão vazios!!!");
            return false;
        }
        return true;
    }

    public void getData(Embarcacao embarcacao){
        nome.setText(embarcacao.getNome());
        nomeText.setText(nome.getText());
        titular.setText(embarcacao.getUtilizador().getNome());
        titularBox.setValue(titular.getText());
        marina.setText(embarcacao.getMarina().getNome());
        marinaBox.setValue(marina.getText());
        comprimento.setText(embarcacao.getDescComprimento().getDescricao());
        comprimentoBox.setValue(comprimento.getText());
        idEmbarcacao = embarcacao.getIdEmbarcacao();
    }

    @FXML
    void editar() {
        infoVbox.setVisible(false);
        editVbox.setVisible(true);
        editarButton.setVisible(false);
        guardarButton.setVisible(true);
    }

}
