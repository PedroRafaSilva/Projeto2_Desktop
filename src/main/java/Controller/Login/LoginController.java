package Controller.Login;

import Utilizador.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import Route.Routes;


import java.io.IOException;


public class LoginController {

    private UtilizadorService utilizadorService = new UtilizadorService();

    @FXML
    private Label errorMessage;

    @FXML
    private PasswordField passwordText;

    @FXML
    private TextField usernameText;

    @FXML
    public void Login(ActionEvent event) {
        checkLogin(event);
    }

    public void checkLogin(ActionEvent event) {
        try {
            boolean login = false;
            login = utilizadorService.isUserAlreadyRegistered(usernameText.getText(), passwordText.getText());
            if (login){
                goToMenu(event);
            } else {
                errorMessage.setText("Dados Incorretos!!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void goRegister(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "Register", "RegisterView.fxml");
    }

    public void goToMenu(ActionEvent event) throws IOException {
        Routes.handleGeneric(event, "Agendamento", "AgendamentoView.fxml");
    }



}
