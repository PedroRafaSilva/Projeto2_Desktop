package Controller.Oficina;

import CodPostal.CodPostal;
import CodPostal.CodPostalService;
import Marina.Marina;
import Marina.MarinaService;
import Oficina.Oficina;
import Oficina.OficinaService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OficinaEditarController {

    @FXML
    private TextField codPostText;

    @FXML
    private Label codPostal;

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
    private Label localidade;

    @FXML
    private TextField localidadeText;

    @FXML
    private Label nome;

    @FXML
    private TextField nomeText;

    private OficinaService oficinaService = new OficinaService();

    private CodPostalService codPostalService = new CodPostalService();

    private int idOficina;

    @FXML
    void guardar() {
        checkEdicao();
    }

    public void checkEdicao(){
        if (validations()) {
            CodPostal codPostal = new CodPostal();
            Oficina oficina = new Oficina();

            codPostal.setCpostal(codPostText.getText());
            codPostal.setLocalidade(localidadeText.getText());

            oficina.setIdOficina(idOficina);
            oficina.setNome(nomeText.getText());
            oficina.setCpostal(codPostText.getText());

            criationValid(codPostal, oficina);

            Stage stage = (Stage) guardarButton.getScene().getWindow();
            stage.close();

        }
    }

    public boolean validations() {

        if (nomeText.getText().isEmpty() || codPostText.getText().isEmpty()) {
            errorText.setText("Um ou mais campos estão vazios!!!");
            return false;
        }

        if (!codPostText.getText().equals(codPostText.getText().substring(0, 4) + "-" + codPostText.getText().substring(5))) {
            errorText.setText("O codígo de Postal deve ser do tipo: 1234-123.");
            return false;
        }


        return true;
    }

    public void criationValid(CodPostal codPostal, Oficina oficina){
        if (codPostalService.getCodPostalById(codPostal.getCpostal()) == null) {
            codPostalService.createCodPostal(codPostal);
        }
        oficinaService.updateOficina(oficina);

    }

    public void getData(Oficina oficina){
        nome.setText(oficina.getNome());
        localidade.setText(codPostalService.getCodPostalById(oficina.getCpostal()).getLocalidade());
        codPostal.setText(oficina.getCpostal());
        nomeText.setText(nome.getText());
        localidadeText.setText(localidade.getText());
        codPostText.setText(codPostal.getText());
        idOficina = oficina.getIdOficina();
    }

    @FXML
    void editar() {
        infoVbox.setVisible(false);
        editVbox.setVisible(true);
        editarButton.setVisible(false);
        guardarButton.setVisible(true);
    }
}
