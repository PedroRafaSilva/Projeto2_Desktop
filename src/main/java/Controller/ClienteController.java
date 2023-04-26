package Controller;

import Route.Routes;
import com.example.projeto2_desktop.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class ClienteController {

    @FXML
    private FlowPane calendar;

    @FXML
    private VBox pane1;

    @FXML
    private VBox pane2;

    @FXML
    void getMenu(MouseEvent event) {
        if (pane1.isVisible()){
            pane1.setVisible(false);
            pane1.setPrefWidth(0);
            pane2.setPrefWidth(1444);
        }
        else {
            pane1.setVisible(true);
            pane1.setPrefWidth(349);
            pane2.setPrefWidth(1095);
        }
    }

    @FXML
    void logout(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "Login", "LoginView.fxml");
    }



    @FXML
    void manageEmbarcacoes(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "Embarcações", "EmbarcacoesView.fxml");
    }

    @FXML
    void manageFaturas(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "Faturas", "FaturasView.fxml");

    }

    @FXML
    void manageFuncionarios(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "Funcionarios", "FuncionariosView.fxml");
    }

    @FXML
    void manageMarinas(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "Marinas", "MarinaView.fxml");
    }

    @FXML
    void manageOficinas(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "Oficinas", "OficinaView.fxml");
    }
    @FXML
    void manageClientes(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "Clientes", "ClientesView.fxml");
    }

    @FXML
    void managePedidos(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "PedidoManutencao", "PedidoManutencaoView.fxml");
    }

    @FXML
    void userPerfil(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "Meu Perfil", "PerfilView.fxml");
    }

    @FXML
    void manageAgendamentos(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "Agendamentos", "AgendamentoView.fxml");
    }
}