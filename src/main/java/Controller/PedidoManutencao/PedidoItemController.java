package Controller.PedidoManutencao;

import PedidoManutencao.PedidoManutencao;
import PedidoManutencao.PedidoManutencaoService;
import com.example.projeto2_desktop.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Optional;

public class PedidoItemController {

    @FXML
    private ImageView cancelButton;

    @FXML
    private Label embarcacaoText;

    @FXML
    private Label idText;

    @FXML
    private Label nomeText;

    @FXML
    private Label oficinaText;

    @FXML
    private Label valorText;

    private final PedidoManutencaoService pedidoManutencaoService = new PedidoManutencaoService();

    public void getData(PedidoManutencao pedidoManutencao){
        nomeText.setText(pedidoManutencao.getUtilizador().getNome());
        embarcacaoText.setText(pedidoManutencao.getEmbarcacao().getNome());
        oficinaText.setText(pedidoManutencao.getOficina().getNome());
        valorText.setText(String.valueOf(pedidoManutencao.getValor()));
        idText.setText(String.valueOf(pedidoManutencao.getIdpedido()));
        visibleButtons(pedidoManutencao);
    }

    public void visibleButtons(PedidoManutencao pedidoManutencao){
        cancelButton.setVisible(true);
        if(!pedidoManutencao.getData().toLocalDate().isAfter(LocalDate.now())){
            cancelButton.setVisible(false);
        }
    }

    @FXML
    void deletePedido() {
        ButtonType buttonType = new ButtonType("Cancelar Agendamento");
        ButtonType buttonType1 = new ButtonType(" Não Cancelar");
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "Tem a certeza que pretende cancelar o Pedido?\nEsta ação é irreversivel!",
                buttonType, buttonType1);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonType) {
            pedidoManutencaoService.deletePedidoManutencao(Integer.parseInt(idText.getText()));
        }
        Stage stage = (Stage) oficinaText.getScene().getWindow() ;
        stage.close();
    }

    @FXML
    void editPedido() throws IOException {
        Stage stage= new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("PedidoEditarView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        PedidoEditarController pedidoEditarController = fxmlLoader.getController();
        pedidoEditarController.getData(pedidoManutencaoService.getPedidoManutencaoById(Integer.parseInt(idText.getText())));
        stage.showAndWait();
        Stage stage1 = (Stage) oficinaText.getScene().getWindow() ;
        stage1.close();
    }

}
