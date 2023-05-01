package Controller.Cliente;

import Route.Routes;
import Utilizador.*;
import com.example.projeto2_desktop.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ClienteController implements Initializable {

    @FXML
    private VBox list;

    @FXML
    private VBox pane1;

    @FXML
    private VBox pane2;

    @FXML
    private ScrollPane scroll;

    @FXML
    private TextField searchField;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getClientesItems();
    }


    @FXML
    void Search() {
        UtilizadorService utilizadorService = new UtilizadorService();
        list.getChildren().clear();
        for(Utilizador user: utilizadorService.getClientesComPrefix(searchField.getText())){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("ClienteItemView.fxml"));
            try {
                HBox hBox = fxmlLoader.load();
                ClienteItemController clienteItemController = fxmlLoader.getController();
                clienteItemController.getData(user);
                list.getChildren().add(hBox);
                scroll.setContent(list);
                list.setSpacing(10);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void getMenu() {
        if (pane1.isVisible()){
            pane1.setVisible(false);
            pane1.setPrefWidth(0);
            pane2.setPrefWidth(1444);
        }
        else {
            pane1.setVisible(true);
            pane1.setPrefWidth(295);
            pane2.setPrefWidth(1165);
        }
    }

    public void getClientesItems(){
        UtilizadorService utilizadorService = new UtilizadorService();
        for(Utilizador user: utilizadorService.getAllClientes()){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("ClienteItemView.fxml"));
            try {
                HBox hBox = fxmlLoader.load();
                ClienteItemController clienteItemController = fxmlLoader.getController();
                clienteItemController.getData(user);
                list.getChildren().add(hBox);
                scroll.setContent(list);
                list.setSpacing(10);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void createNovoCliente() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("ClienteNovoView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        list.getChildren().clear();
        getClientesItems();
    }

    @FXML
    void logout(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "", "LoginView.fxml");
    }

    @FXML
    void manageEmbarcacoes(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "", "EmbarcacoesView.fxml");
    }

    @FXML
    void manageFaturas(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "", "FaturasView.fxml");

    }

    @FXML
    void manageFuncionarios(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "", "FuncionarioView.fxml");
    }

    @FXML
    void manageMarinas(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "", "MarinaView.fxml");
    }

    @FXML
    void manageOficinas(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "", "OficinaView.fxml");
    }
    @FXML
    void manageClientes(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "", "ClientesView.fxml");
    }

    @FXML
    void managePedidos(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "", "PedidoManutencaoView.fxml");
    }

    @FXML
    void userPerfil(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "", "PerfilView.fxml");
    }

    @FXML
    void manageAgendamentos(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "", "AgendamentoView.fxml");
    }
}