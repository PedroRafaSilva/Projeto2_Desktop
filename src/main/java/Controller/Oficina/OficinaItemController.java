package Controller.Oficina;

import Controller.Marina.MarinaEditarController;
import Oficina.Oficina;
import Oficina.OficinaService;
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

public class OficinaItemController {
    @FXML
    private Label idText;

    @FXML
    private Label nomeText;

    public void getData(Oficina oficina){
        nomeText.setText(oficina.getNome());
        idText.setText(String.valueOf(oficina.getIdoficina()));
    }


    @FXML
    public void deleteOficina(MouseEvent event) throws IOException {
        OficinaService oficinaService = new OficinaService();
        ButtonType buttonType = new ButtonType("Eliminar");
        ButtonType buttonType1 = new ButtonType(" Não Eliminar");
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "Tem a certeza que pretende eleminar a Oficina?\nEsta ação é irreversivel!",
                buttonType, buttonType1);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonType) {
            oficinaService.deleteOficina(Integer.parseInt(idText.getText()));
        }
        Routes.handleGeneric(event, "", "OficinaView.fxml");
    }

    @FXML
    void editOficina() throws IOException {
        OficinaService oficinaService = new OficinaService();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("OficinaEditarView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        OficinaEditarController oficinaEditarController = fxmlLoader.getController();
        oficinaEditarController.getData(oficinaService.getOficinaById(Integer.parseInt(idText.getText())));
        stage.showAndWait();
        getData(oficinaService.getOficinaById(Integer.parseInt(idText.getText())));
    }


}
