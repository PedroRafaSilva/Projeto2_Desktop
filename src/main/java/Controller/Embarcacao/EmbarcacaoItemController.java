package Controller.Embarcacao;
import Embarcacao.EmbarcacaoService;
import Embarcacao.Embarcacao;
import Route.Routes;
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

public class EmbarcacaoItemController {


    @FXML
    private Label comprimentoText;

    @FXML
    private Label idText;

    @FXML
    private Label nomeText;

    @FXML
    private Label titularText;

    public void getData(Embarcacao embarcacao){
        nomeText.setText(embarcacao.getNome());
        titularText.setText(embarcacao.getutilizador().getNome());
        comprimentoText.setText(embarcacao.getDescComprimento().getDescricao());
        idText.setText(String.valueOf(embarcacao.getIdEmbarcacao()));
    }


    @FXML
    void deleteEmbarcacao(MouseEvent event) throws IOException {
        EmbarcacaoService embarcacaoService = new EmbarcacaoService();
        ButtonType buttonType = new ButtonType("Eliminar");
        ButtonType buttonType1 = new ButtonType(" Não Eliminar");
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "Tem a certeza que pretende eleminar o Cliente?\nEsta ação é irreversivel!",
                buttonType, buttonType1);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonType) {
            embarcacaoService.deleteEmbarcacao(Integer.parseInt(idText.getText()));
        }
        Routes.handleGeneric(event, "", "EmbarcacaoView.fxml");

    }

    @FXML
    void editEmbarcacao() throws IOException {
        EmbarcacaoService embarcacaoService = new EmbarcacaoService();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("EmbarcacaoEditarView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        EmbarcacaoEditarController embarcacaoEditarController = fxmlLoader.getController();
        embarcacaoEditarController.getData(embarcacaoService.getEmbarcacaoById(Integer.parseInt(idText.getText())));
        stage.showAndWait();
        getData(embarcacaoService.getEmbarcacaoById(Integer.parseInt(idText.getText())));
    }
}
