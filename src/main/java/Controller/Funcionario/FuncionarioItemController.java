package Controller.Funcionario;

import Controller.Cliente.EditarClienteController;
import Route.Routes;
import Utilizador.*;
import com.example.projeto2_desktop.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class FuncionarioItemController {
    @FXML
    private Label emailText;

    @FXML
    private Label idText;

    @FXML
    private Label nifText;

    @FXML
    private Label nomeText;

    @FXML
    private Label telefoneText;

    public void getData(Utilizador utilizador){
        nomeText.setText(utilizador.getNome());
        telefoneText.setText(utilizador.getTelefone());
        emailText.setText(utilizador.getEmail());
        nifText.setText(String.valueOf(utilizador.getNif()));
        idText.setText(String.valueOf(utilizador.getIdutilizador()));
    }


    @FXML
    public void deleteFuncionario(MouseEvent event) throws IOException {
        UtilizadorService utilizadorService = new UtilizadorService();
        ButtonType buttonType = new ButtonType("Eliminar");
        ButtonType buttonType1 = new ButtonType(" Não Eliminar");
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "Tem a certeza que pretende eleminar o Funcionario?\nEsta ação é irreversivel!",
                buttonType, buttonType1);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonType) {
            System.out.println("LOL");
            utilizadorService.deleteUtilizador(Integer.parseInt(idText.getText()));
        }
        Routes.handleGeneric(event, "", "FuncionarioView.fxml");
    }

    @FXML
    void editFuncionario() throws IOException {
        UtilizadorService utilizadorService = new UtilizadorService();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("FuncionarioEditarView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.initOwner(emailText.getScene().getWindow());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        FuncionarioEditarController funcionarioEditarController = fxmlLoader.getController();
        funcionarioEditarController.getData(utilizadorService.getUtilizadorById(Integer.parseInt(idText.getText())));
        stage.showAndWait();
        getData(utilizadorService.getUtilizadorById(Integer.parseInt(idText.getText())));
    }

}
