package Controller.Oficina;

import CodPostal.CodPostal;
import CodPostal.CodPostalService;
import Oficina.Oficina;
import Oficina.OficinaService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class OficinaNovoController {
    @FXML
    private TextField codPostText;

    @FXML
    private Label errorText;

    @FXML
    private TextField localidadeText;

    @FXML
    private TextField nomeText;

    private final OficinaService oficinaService = new OficinaService();

    private final CodPostalService codPostalService = new CodPostalService();

    @FXML
    void CriarOficina() {
        checkCriacao();
    }

    public void checkCriacao(){
        if (validations()){
            CodPostal codPostal = new CodPostal();
            Oficina oficina = new Oficina();

            codPostal.setCpostal(codPostText.getText());
            codPostal.setLocalidade(localidadeText.getText());

            oficina.setNome(nomeText.getText());
            oficina.setCpostal(codPostText.getText());


            criationValid(codPostal, oficina);
            Stage stage = (Stage) codPostText.getScene().getWindow();
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
        oficinaService.createOficina(oficina);
    }

}
