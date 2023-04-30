package Controller.Cliente;

import Route.Routes;
import Utilizador.*;
import com.example.projeto2_desktop.App;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ClienteItemController{

    @FXML
    private Label emailText  = new Label();

    @FXML
    private Label idText;

    @FXML
    private Label nifText  = new Label();

    @FXML
    private Label nomeText = new Label();

    @FXML
    private Label telefoneText  = new Label();

    public void getData(Utilizador utilizador){
        nomeText.setText(utilizador.getNome());
        telefoneText.setText(utilizador.getTelefone());
        emailText.setText(utilizador.getEmail());
        nifText.setText(String.valueOf(utilizador.getNif()));
        idText.setText(String.valueOf(utilizador.getIdUtilizador()));
    }


    @FXML
    void deleteCliente(MouseEvent event) throws IOException {
        UtilizadorService utilizadorService = new UtilizadorService();
        utilizadorService.deleteUtilizador(Integer.parseInt(idText.getText()));
        Routes.handleGeneric(event, "Clientes", "ClientesView.fxml");
    }

    @FXML
    void editCliente(MouseEvent event) throws IOException {
        UtilizadorService utilizadorService = new UtilizadorService();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("EditarClienteView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.initOwner(emailText.getScene().getWindow());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        EditarClienteController editarClienteController = fxmlLoader.getController();
        editarClienteController.getData(utilizadorService.getUtilizadorById(Integer.parseInt(idText.getText())));
        stage.showAndWait();
        getData(utilizadorService.getUtilizadorById(Integer.parseInt(idText.getText())));
    }

}
