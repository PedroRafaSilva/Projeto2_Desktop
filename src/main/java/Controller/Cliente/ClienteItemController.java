package Controller.Cliente;

import Route.Routes;
import Utilizador.Utilizador;
import Utilizador.UtilizadorService;
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
    public void deleteCliente(MouseEvent event) throws IOException {
        UtilizadorService utilizadorService = new UtilizadorService();
        ButtonType buttonType = new ButtonType("Eliminar");
        ButtonType buttonType1 = new ButtonType(" Não Eliminar");
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "Tem a certeza que pretende eleminar o Cliente?\nEsta ação é irreversivel!",
                buttonType, buttonType1);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonType) {
            System.out.println("LOL");
            utilizadorService.deleteUtilizador(Integer.parseInt(idText.getText()));
        }
        Routes.handleGeneric(event, "", "ClientesView.fxml");
    }




    @FXML
    void editCliente() throws IOException {
        UtilizadorService utilizadorService = new UtilizadorService();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("ClienteEditarView.fxml"));
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
