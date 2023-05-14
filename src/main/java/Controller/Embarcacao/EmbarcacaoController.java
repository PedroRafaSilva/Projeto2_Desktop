package Controller.Embarcacao;

import Embarcacao.Embarcacao;
import Embarcacao.EmbarcacaoService;
import Route.Routes;
import com.example.projeto2_desktop.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class EmbarcacaoController implements Initializable {

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

    @FXML
    private ComboBox<String> clienteBox;

    @FXML
    private ComboBox<String> comprimentoBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        getEmbarcacaoItems();
        getClienteBoxItens();
        getComprimentoBoxItems();
    }

    public void buildItems(Embarcacao embarcacao){
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(App.class.getResource("EmbarcacaoItemView.fxml"));
        try {
            HBox hBox = fxmlLoader.load();
            EmbarcacaoItemController embarcacaoItemController = fxmlLoader.getController();
            embarcacaoItemController.getData(embarcacao);
            list.getChildren().add(hBox);
            scroll.setContent(list);
            list.setSpacing(10);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void Search() {
        String search = searchField.getText();
        clienteBox.setValue(" ");
        comprimentoBox.setValue(" ");
        list.getChildren().clear();
        EmbarcacaoService embarcacaoService = new EmbarcacaoService();
        for(Embarcacao embarcacao: embarcacaoService.getEmbarcacoescomPrefix(search)){
            buildItems(embarcacao);
        }
    }

    public void getEmbarcacaoItems(){
        list.getChildren().clear();
        EmbarcacaoService embarcacaoService = new EmbarcacaoService();
        for(Embarcacao embarcacao: embarcacaoService.getAllEmbarcacaos()){
            buildItems(embarcacao);
        }
    }

    public void getClienteBoxItens(){
        clienteBox.getItems().clear();
        clienteBox.getItems().add("");
        EmbarcacaoService embarcacaoService = new EmbarcacaoService();
        for (Embarcacao embarcacao: embarcacaoService.getAllEmbarcacaos()){
            if (!clienteBox.getItems().contains(embarcacao.getutilizador().getNome())) {
                clienteBox.getItems().add(embarcacao.getutilizador().getNome());
            }
        }
    }

    @FXML
    public void getClienteBoxSearch(){
        searchField.setText(null);
        list.getChildren().clear();
        EmbarcacaoService embarcacaoService = new EmbarcacaoService();
        for (Embarcacao embarcacao: embarcacaoService.getAllEmbarcacaos()){
            if (embarcacao.getutilizador().getNome().contains(clienteBox.getValue())){
                if (comprimentoBox.getValue() == null) {
                    buildItems(embarcacao);
                } else {
                    if (embarcacao.getDescComprimento().getDescricao().contains(comprimentoBox.getValue())){
                        buildItems(embarcacao);
                    }
                }
            }
        }
    }

    @FXML
    public void getComprimentoBoxSearch(){
        searchField.setText(null);
        list.getChildren().clear();
        EmbarcacaoService embarcacaoService = new EmbarcacaoService();
        for (Embarcacao embarcacao: embarcacaoService.getAllEmbarcacaos()){
            if (embarcacao.getDescComprimento().getDescricao().contains(comprimentoBox.getValue())){
                if (clienteBox.getValue() == null) {
                    buildItems(embarcacao);
                } else {
                    if (embarcacao.getutilizador().getNome().contains(clienteBox.getValue())){
                        buildItems(embarcacao);
                    }
                }
            }
        }
    }
    public void getComprimentoBoxItems(){
        comprimentoBox.getItems().clear();
        comprimentoBox.getItems().add(" ");
        EmbarcacaoService embarcacaoService = new EmbarcacaoService();
        for (Embarcacao embarcacao: embarcacaoService.getAllEmbarcacaos()) {
            if (!comprimentoBox.getItems().contains(embarcacao.getDescComprimento().getDescricao())) {
                comprimentoBox.getItems().add(embarcacao.getDescComprimento().getDescricao());
            }
        }
    }

    @FXML
    void createNovoEmbarcacaco() throws IOException {
        clienteBox.valueProperty().setValue(null);
        comprimentoBox.valueProperty().setValue(null);
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("EmbarcacaoNovaView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        list.getChildren().clear();
        getEmbarcacaoItems();
        getClienteBoxItens();
        getComprimentoBoxItems();
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
        Routes.handleGeneric(event, "", "LoginView.fxml");
    }

    @FXML
    void manageEmbarcacoes(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "", "EmbarcacaoView.fxml");
    }

    @FXML
    void manageFaturas(MouseEvent event) throws IOException {
        Routes.handleGeneric(event, "", "FaturaView.fxml");

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
