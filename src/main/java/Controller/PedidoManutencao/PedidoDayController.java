package Controller.PedidoManutencao;


import PedidoManutencao.PedidoManutencao;
import PedidoManutencao.PedidoManutencaoService;
import com.example.projeto2_desktop.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class PedidoDayController{

    @FXML
    private Label data;

    @FXML
    private ScrollPane scroll;

    @FXML
    private VBox vBox;

    private int dia = 0, mes = 0, ano = 0;

    private final PedidoManutencaoService pedidoManutencaoService = new PedidoManutencaoService();

    public void getDate(int day, int month, int year){
        String date = ("" + day + "/" + month + "/" + year);
        data.setText(date);
        dia = day;
        mes = month;
        ano = year;
        getPedidos();
    }

    public void getPedidos(){
        for(PedidoManutencao pedidoManutencao: pedidoManutencaoService.getPedidosByDate(dia, mes, ano)){
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(App.class.getResource("PedidoItemView.fxml"));
            try {
                HBox hBox = fxmlLoader.load();
                PedidoItemController pedidoItemController = fxmlLoader.getController();
                pedidoItemController.getData(pedidoManutencao);
                vBox.getChildren().add(hBox);
                scroll.setContent(vBox);
                vBox.setSpacing(10);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @FXML
    void closeWindow() {
        Stage stage = (Stage) data.getScene().getWindow();
        stage.close();
    }
}
