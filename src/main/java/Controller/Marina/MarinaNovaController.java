package Controller.Marina;

import CodPostal.CodPostal;
import CodPostal.CodPostalService;
import Marina.Marina;
import Marina.MarinaService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class MarinaNovaController {

    @FXML
    private TextField codPostText;

    @FXML
    private Label errorText;

    @FXML
    private TextField localidadeText;

    @FXML
    private TextField nomeText;

    private final MarinaService marinaService = new MarinaService();

    private final CodPostalService codPostalService = new CodPostalService();

    @FXML
    void CriarMarina() {
        checkCriacao();
    }

    public void checkCriacao(){
        if (validations()){
            CodPostal codPostal = new CodPostal();
            Marina marina = new Marina();

            codPostal.setCpostal(codPostText.getText());
            codPostal.setLocalidade(localidadeText.getText());

            marina.setNome(nomeText.getText());
            marina.setCpostal(codPostText.getText());


            criationValid(codPostal, marina);
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

    public void criationValid(CodPostal codPostal, Marina marina){
        if (codPostalService.getCodPostalById(codPostal.getCpostal()) == null) {
            codPostalService.createCodPostal(codPostal);
        }
        marinaService.createMarina(marina);
    }

}
