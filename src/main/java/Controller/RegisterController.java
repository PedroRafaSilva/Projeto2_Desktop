package Controller;

import CodPostal.CodPostal;
import Route.Routes;
import Utilizador.Utilizador;
import Utilizador.UtilizadorRepository;
import CodPostal.CodPostalRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class RegisterController {

    private UtilizadorRepository utilizadorRepository = new UtilizadorRepository();

    private CodPostalRepository codPostalRepository = new CodPostalRepository();

    @FXML
    private TextField codPostText;

    @FXML
    private TextField localidadeText;

    @FXML
    private PasswordField confirmText;

    @FXML
    private TextField emailText;

    @FXML
    private TextField nifText;

    @FXML
    private TextField nomeText;

    @FXML
    private PasswordField passwordText;

    @FXML
    private TextField portaText;

    @FXML
    private Button registarButton;

    @FXML
    private TextField ruaText;

    @FXML
    private TextField telefoneText;

    @FXML
    private TextField usernameText;

    @FXML
    private Label errorText;

    @FXML
    void Registar(ActionEvent event) {
        checkRegistar();
    }

    public void checkRegistar(){
        try {
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
                utilizador.setIdTipoUtilizador(3);

                registrationValid(codPostal, utilizador);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean validations() throws SQLException {
        List<Utilizador> utilizadores = utilizadorRepository.getAllUtilizadors();
        boolean userExist = false;
        boolean hasLettters = false;

        if (nomeText.getText().isEmpty() || emailText.getText().isEmpty() || telefoneText.getText().isEmpty()
        || nifText.getText().isEmpty() || ruaText.getText().isEmpty() || portaText.getText().isEmpty() ||
        codPostText.getText().isEmpty() || usernameText.getText().isEmpty() || passwordText.getText().isEmpty()
        || confirmText.getText().isEmpty()){
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
            errorText.setText("O codígo de Postal deve ser do tipo: 1234-123");
            return false;
        }

        for (Utilizador user: utilizadores) {
            if (usernameText.getText().equals(user.getUsername())) {
                userExist = true;
                break;
            }
        }

        if (userExist){
            errorText.setText("Esse username já está a ser utilizado!!");
            return false;
        }

        if (!passwordText.getText().equals(confirmText.getText())){
            errorText.setText("As passwords não são iguais!");
            return false;
        }

        return true;
    }

    public void registrationValid(CodPostal codPostal, Utilizador utilizador) throws SQLException {
        codPostalRepository.saveCodPostal(codPostal);
        utilizadorRepository.saveUtilizador(utilizador);
        errorText.setStyle("-fx-text-fill: #07f20f");
        errorText.setText("Utilizador registado com sucesso!!!");
        registarButton.setVisible(false);
    }

    @FXML
    void GoBack(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "Login", "LoginView.fxml");
    }

}
