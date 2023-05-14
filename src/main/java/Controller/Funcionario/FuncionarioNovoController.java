package Controller.Funcionario;

import CodPostal.CodPostal;
import CodPostal.CodPostalService;
import Utilizador.Utilizador;
import Utilizador.UtilizadorService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class FuncionarioNovoController {
    @FXML
    private TextField codPostText;

    @FXML
    private TextField emailText;

    @FXML
    private Label errorText;

    @FXML
    private TextField localidadeText;

    @FXML
    private TextField nifText;

    @FXML
    private TextField nomeText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private TextField portaText; 

    @FXML
    private TextField ruaText;

    @FXML
    private TextField telefoneText;

    @FXML
    private TextField usernameText;

    private final UtilizadorService utilizadorService = new UtilizadorService();

    private final CodPostalService codPostalService = new CodPostalService();

    @FXML
    void CriarFuncionario() {
        checkCriacao();
    }

    public void checkCriacao(){
        if (validations()) {
            CodPostal codPostal = new CodPostal();
            Utilizador utilizador = new Utilizador();

            codPostal.setCpostal(codPostText.getText());
            codPostal.setLocalidade(localidadeText.getText());

            utilizador.setNome(nomeText.getText());
            utilizador.setEmail(emailText.getText());
            utilizador.setTelefone(telefoneText.getText());
            utilizador.setNif(Integer.parseInt(nifText.getText()));
            utilizador.setRua(ruaText.getText());
            utilizador.setPorta(Integer.parseInt(portaText.getText()));
            utilizador.setcPostal(codPostText.getText());
            utilizador.setUsername(usernameText.getText());
            utilizador.setPassword(passwordText.getText());
            utilizador.setIdtipoutilizador(2);

            criationValid(codPostal, utilizador);
            Stage stage = (Stage) codPostText.getScene().getWindow();
            stage.close();
        }
    }

    public boolean validations(){
        boolean hasLettters = false;

        if (nomeText.getText().isEmpty() || emailText.getText().isEmpty() || telefoneText.getText().isEmpty()
                || nifText.getText().isEmpty() || ruaText.getText().isEmpty() || portaText.getText().isEmpty() ||
                codPostText.getText().isEmpty() || usernameText.getText().isEmpty() || passwordText.getText().isEmpty()){
            errorText.setText("Um ou mais campos estão vazios!!!");
            return false;
        }

        if (!emailText.getText().contains("@") || !emailText.getText().contains(".com")){
            errorText.setText("O email deve ser do tipo: joao@algo.com!");
            return false;
        }

        for (int i = 1; i < telefoneText.getText().length(); i++){
            if (Character.isLetter(telefoneText.getText().charAt(i))) {
                hasLettters = true;
                break;
            }
        }
        if (hasLettters){
            errorText.setText("O número de telefone apenas deve ter números!");
            return false;
        }

        if (nifText.getText().length() > 11){
            errorText.setText("O NIF deve ter no máximo 10 números!");
            return false;
        }

        if (!codPostText.getText().equals(codPostText.getText().substring(0, 4) + "-" + codPostText.getText().substring(5))){
            errorText.setText("O codígo de Postal deve ser do tipo: 1234-123.");
            return false;
        }

        if (utilizadorService.isUserAlreadyRegistered(usernameText.getText(), passwordText.getText())) {
            errorText.setText("Escolha outro username ou palavra-passe.");
            return false;
        }
        return true;
    }

    public void criationValid(CodPostal codPostal, Utilizador utilizador){
        if (codPostalService.getCodPostalById(codPostal.getCpostal()) == null) {
            codPostalService.createCodPostal(codPostal);
        }
        utilizadorService.createUtilizador(utilizador);
    }
}
