package Controller.Fatura;

import Fatura.Fatura;
import Fatura.FaturaService;
import Route.Routes;
import com.example.projeto2_desktop.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FaturaController implements Initializable {

    @FXML
    private VBox list;

    @FXML
    private VBox pane1;

    @FXML
    private VBox pane2;

    @FXML
    private ScrollPane scroll;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        getFaturasItems();
    }



    public void getFaturasItems(){
        FaturaService faturaService = new FaturaService();
        for(Fatura fatura: faturaService.getAllFaturas()){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("FaturaItemView.fxml"));
            try {
                HBox hBox = fxmlLoader.load();
                FaturaItemController faturaItemController = fxmlLoader.getController();
                faturaItemController.getData(fatura);
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


    @FXML
    void logout(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "Login", "LoginView.fxml");
    }

    @FXML
    void manageClientes(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "Clientes", "ClientesView.fxml");
    }

    @FXML
    void manageEmbarcacoes(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "Embarcações", "EmbarcacaoView.fxml");
    }

    @FXML
    void manageFaturas(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "Faturas", "FaturaView.fxml");

    }

    @FXML
    void manageFuncionarios(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "Funcionarios", "FuncionarioView.fxml");
    }

    @FXML
    void managePedidos(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "", "PedidoManutencaoView.fxml");
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
    void manageAgendamentos(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "Agendamentos", "AgendamentoView.fxml");
    }

    @FXML
    void userPerfil(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "Meu Perfil", "PerfilView.fxml");
    }
}
