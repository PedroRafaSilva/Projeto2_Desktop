package Controller;

import Utilizador.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import Route.Routes;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class LoginController {

    private UtilizadorRepository utilizadorRepository = new UtilizadorRepository();

    @FXML
    private Label errorMessage;

    @FXML
    private PasswordField passwordText;

    @FXML
    private TextField usernameText;

    @FXML
    public void Login() {
        checkLogin();
    }

    public void checkLogin() {
        try {
            Utilizador utilizador = new Utilizador();
            List<Utilizador> utilizadores = utilizadorRepository.getAllUtilizadors();
            boolean login = false;
            for (Utilizador user: utilizadores){
                if (usernameText.getText().equals(user.getUsername()) && passwordText.getText().equals(user.getPassword())){
                    login = true;
                } else {
                    login = false;
                }
            }
            if (login == true){
                goToMenu();
            } else {
                errorMessage.setText("Dados Incorretos!!");
            }
        } catch (SQLException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void goRegister(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "Register", "RegisterView.fxml");
    }

    public void goToMenu() throws IOException {

    }



}
