package Controller.Marina;

import Marina.Marina;
import Marina.MarinaService;
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

public class MarinaItemController {

    @FXML
    private Label idText;

    @FXML
    private Label nomeText;

    public void getData(Marina marina){
        nomeText.setText(marina.getNome());
        idText.setText(String.valueOf(marina.getIdmarina()));
    }


    @FXML
    public void deleteMarina(MouseEvent event) throws IOException {
        MarinaService marinaService = new MarinaService();
        ButtonType buttonType = new ButtonType("Eliminar");
        ButtonType buttonType1 = new ButtonType(" Não Eliminar");
        Alert alert = new Alert(Alert.AlertType.WARNING,
                "Tem a certeza que pretende eleminar a Marina?\nEsta ação é irreversivel!",
                buttonType, buttonType1);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == buttonType) {
            marinaService.deleteMarina(Integer.parseInt(idText.getText()));
        }
        Routes.handleGeneric(event, "", "MarinaView.fxml");
    }

    @FXML
    void editMarina() throws IOException {
        MarinaService marinaService = new MarinaService();
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("MarinaEditarView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        MarinaEditarController marinaEditarController = fxmlLoader.getController();
        marinaEditarController.getData(marinaService.getMarinaById(Integer.parseInt(idText.getText())));
        stage.showAndWait();
        getData(marinaService.getMarinaById(Integer.parseInt(idText.getText())));
    }


}
