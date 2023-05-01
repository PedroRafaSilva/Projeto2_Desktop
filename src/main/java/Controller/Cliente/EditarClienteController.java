package Controller.Cliente;

import CodPostal.CodPostal;
import CodPostal.CodPostalService;
import Utilizador.Utilizador;
import Utilizador.UtilizadorService;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class EditarClienteController {
    @FXML
    private TextField codPostText;

    @FXML
    private Label codPostal;

    @FXML
    private VBox editVbox;

    @FXML
    private Button editarButton;

    @FXML
    private Label email;

    @FXML
    private TextField emailText;

    @FXML
    private Label errorText;

    @FXML
    private VBox infoVbox;

    @FXML
    private Label localidade;

    @FXML
    private TextField localidadeText;

    @FXML
    private Label nif;

    @FXML
    private TextField nifText;

    @FXML
    private Label nome;

    @FXML
    private TextField nomeText;

    @FXML
    private Label password;

    @FXML
    private PasswordField passwordText;

    @FXML
    private Label porta;

    @FXML
    private TextField portaText;

    @FXML
    private Label rua;

    @FXML
    private TextField ruaText;

    @FXML
    private Label telefone;

    @FXML
    private TextField telefoneText;

    @FXML
    private Label username;

    @FXML
    private TextField usernameText;

    @FXML
    private Button guardarButton;

    private UtilizadorService utilizadorService = new UtilizadorService();

    private CodPostalService codPostalService = new CodPostalService();

    private int idCliente;

    @FXML
    void guardar() {
        checkEdicao();
    }

    public void checkEdicao(){
        if (validations()) {
            CodPostal codPostal = new CodPostal();
            Utilizador utilizador = new Utilizador();

            codPostal.setCpostal(codPostText.getText());
            codPostal.setLocalidade(localidadeText.getText());

            utilizador.setIdUtilizador(idCliente);
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

            criationValid(codPostal, utilizador);

            Stage stage = (Stage) guardarButton.getScene().getWindow();
            stage.close();


        }
    }

    public boolean validations() {
        boolean hasLettters = false;

        if (nomeText.getText().isEmpty() || emailText.getText().isEmpty() || telefoneText.getText().isEmpty()
                || nifText.getText().isEmpty() || ruaText.getText().isEmpty() || portaText.getText().isEmpty() ||
                codPostText.getText().isEmpty() || usernameText.getText().isEmpty() || passwordText.getText().isEmpty()) {
            errorText.setText("Um ou mais campos estão vazios!!!");
            return false;
        }

        if (!emailText.getText().contains("@") || !emailText.getText().contains(".com")) {
            errorText.setText("O email deve ser do tipo: joao@algo.com!");
            return false;
        }

        for (int i = 1; i < telefoneText.getText().length(); i++) {
            if (Character.isLetter(telefoneText.getText().charAt(i))) {
                hasLettters = true;
                break;
            }
        }
        if (hasLettters) {
            errorText.setText("O número de telefone apenas deve ter números!");
            return false;
        }

        if (nifText.getText().length() > 11) {
            errorText.setText("O NIF deve ter no máximo 10 números!");
            return false;
        }

        if (!codPostText.getText().equals(codPostText.getText().substring(0, 4) + "-" + codPostText.getText().substring(5))) {
            errorText.setText("O codígo de Postal deve ser do tipo: 1234-123.");
            return false;
        }

        if (!usernameText.getText().equals(username.getText()) && !passwordText.getText().equals(password.getText())){
            if (utilizadorService.isUserAlreadyRegistered(usernameText.getText(), passwordText.getText())) {
                errorText.setText("Escolha outro username ou palavra-passe.");
                return false;
            }
        }

        return true;
    }

    public void criationValid(CodPostal codPostal, Utilizador utilizador){
        if (codPostalService.getCodPostalById(codPostal.getCpostal()) == null) {
            codPostalService.createCodPostal(codPostal);
        }
        utilizadorService.updateUtilizador(utilizador);
    }

    public void getData(Utilizador utilizador){
        CodPostalService codPostalService = new CodPostalService();
        nome.setText(utilizador.getNome());
        email.setText(utilizador.getEmail());
        telefone.setText(utilizador.getTelefone());
        nif.setText(String.valueOf(utilizador.getNif()));
        rua.setText(utilizador.getRua());
        porta.setText(String.valueOf(utilizador.getPorta()));
        localidade.setText(codPostalService.getCodPostalById(utilizador.getcPostal()).getLocalidade());
        codPostal.setText(utilizador.getcPostal());
        username.setText(utilizador.getUsername());
        password.setText(utilizador.getPassword());
        nomeText.setText(nome.getText());
        emailText.setText(email.getText());
        telefoneText.setText(telefone.getText());
        nifText.setText(nif.getText());
        ruaText.setText(rua.getText());
        portaText.setText(porta.getText());
        localidadeText.setText(localidade.getText());
        codPostText.setText(codPostal.getText());
        usernameText.setText(username.getText());
        passwordText.setText(password.getText());
        idCliente = utilizador.getIdUtilizador();
    }

    @FXML
    void editar() {
        infoVbox.setVisible(false);
        editVbox.setVisible(true);
        editarButton.setVisible(false);
        guardarButton.setVisible(true);
    }


}
