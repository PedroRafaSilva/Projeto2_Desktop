package Controller.Marina;

import CodPostal.*;
import Marina.Marina;
import Marina.MarinaService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MarinaEditarController {
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

    private MarinaService marinaService = new MarinaService();

    private CodPostalService codPostalService = new CodPostalService();

    private int idMarina;

    @FXML
    void guardar() {
        checkEdicao();
    }

    public void checkEdicao(){
        if (validations()) {
            CodPostal codPostal = new CodPostal();
            Marina marina = new Marina();

            codPostal.setCpostal(codPostText.getText());
            codPostal.setLocalidade(localidadeText.getText());

            marina.setIdmarina(idMarina);
            marina.setNome(nomeText.getText());
            marina.setCpostal(codPostText.getText());

            criationValid(codPostal, marina);

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

    public void criationValid(CodPostal codPostal, Marina marina){
        if (codPostalService.getCodPostalById(codPostal.getCpostal()) == null) {
            codPostalService.createCodPostal(codPostal);
        }
        marinaService.updateMarina(marina);

    }

    public void getData(Marina marina){
        nome.setText(marina.getNome());
        localidade.setText(codPostalService.getCodPostalById(marina.getCpostal()).getLocalidade());
        codPostal.setText(marina.getCpostal());
        nomeText.setText(nome.getText());
        localidadeText.setText(localidade.getText());
        codPostText.setText(codPostal.getText());
        idMarina = marina.getIdmarina();
    }

    @FXML
    void editar() {
        infoVbox.setVisible(false);
        editVbox.setVisible(true);
        editarButton.setVisible(false);
        guardarButton.setVisible(true);
    }

}
