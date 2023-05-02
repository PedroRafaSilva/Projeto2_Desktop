package Controller.Embarcacao;

import Comprimento.Comprimento;
import Comprimento.ComprimentoService;
import Embarcacao.Embarcacao;
import Embarcacao.EmbarcacaoService;
import Marina.Marina;
import Marina.MarinaService;
import Utilizador.Utilizador;
import Utilizador.UtilizadorService;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class EmbarcacaoNovaController implements Initializable {

    @FXML
    private ComboBox<String> comprimentoBox;

    @FXML
    private Label errorText;

    @FXML
    private ComboBox<String> marinBox;

    @FXML
    private TextField nomeText;

    @FXML
    private ComboBox<String> titularBox;

    private EmbarcacaoService embarcacaoService = new EmbarcacaoService();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getTitularBoxItems();
        getMarinaBoxItems();
        getComprimentoBoxItems();
    }

    @FXML
    void CriarEmbarcacao() {
        checkCriacao();
    }

    public void checkCriacao(){
        if (validations()) {
            UtilizadorService utilizadorService = new UtilizadorService();
            MarinaService marinaService = new MarinaService();
            ComprimentoService comprimentoService = new ComprimentoService();
            Embarcacao embarcacao = new Embarcacao();

            embarcacao.setNome(nomeText.getText());
            embarcacao.setIdUtilizador(utilizadorService.getClienteByNome(titularBox.getValue()).getIdutilizador());
            embarcacao.setIdMarina(marinaService.getMarinaByName(marinBox.getValue()).getIdmarina());
            embarcacao.setComprimento(comprimentoService.getComprimentoByName(comprimentoBox.getValue()).getComprimento());
            embarcacaoService.createEmbarcacao(embarcacao);

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
            marinBox.getItems().add(marina.getNome());
        }
    }

    public void getComprimentoBoxItems(){
        ComprimentoService comprimentoService = new ComprimentoService();
        for (Comprimento comprimento : comprimentoService.getAllComprimentos()){
            comprimentoBox.getItems().add(comprimento.getDescricao());
        }
    }

    public boolean validations() {
        if(nomeText.getText().isEmpty() || titularBox.getValue() == null || marinBox.getValue() == null
                || comprimentoBox.getValue() == null){
            errorText.setText("Um ou mais campos estão vazios!!!");
            return false;
        }
        return true;
    }
}
