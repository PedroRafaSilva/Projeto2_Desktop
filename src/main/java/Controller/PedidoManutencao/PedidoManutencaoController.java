package Controller.PedidoManutencao;

import PedidoManutencao.PedidoManutencao;
import PedidoManutencao.PedidoManutencaoService;
import Route.Routes;
import com.example.projeto2_desktop.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.ResourceBundle;

public class PedidoManutencaoController implements Initializable {

    private ZonedDateTime dateFocus, today;

    @FXML
    private VBox pane1, pane2;

    @FXML
    private FlowPane calendar;

    @FXML
    private Label year = new Label();

    @FXML
    private Label month = new Label();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawCalendar();
    }


    public void drawCalendar(){
        PedidoManutencaoService pedidoManutencaoService = new PedidoManutencaoService();
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 1;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();

        int monthMaxDate = dateFocus.getMonth().maxLength();
        //Check for leap year
        if(dateFocus.getYear() % 4 != 0 && monthMaxDate == 29){
            monthMaxDate = 28;
        }
        int dateOffset = ZonedDateTime.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1,0,0,0,0,dateFocus.getZone()).getDayOfWeek().getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();

                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.WHITE);
                rectangle.setStroke(Color.LIGHTBLUE);
                rectangle.setArcWidth(30.0);
                rectangle.setArcHeight(20.0);
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth = (calendarWidth/8) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight/6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j+1)+(7*i);
                if(calculatedDate > dateOffset){
                    int currentDate = calculatedDate - dateOffset;
                    if(currentDate <= monthMaxDate){
                        Text date = new Text(String.valueOf(currentDate));
                        double textTranslationY = - (rectangleHeight / 2) * 0.75;
                        date.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(date);

                        rectangle.setCursor(Cursor.HAND);
                        rectangle.setOnMouseClicked(event -> {
                            try {
                                openPedidosList(currentDate, dateFocus.getMonthValue(), dateFocus.getYear());
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        });
                        if (!pedidoManutencaoService.getPedidosByDate(currentDate, dateFocus.getMonthValue(), dateFocus.getYear()).isEmpty()) {
                            createCalendarActivity(pedidoManutencaoService.getPedidosByDate(currentDate, dateFocus.getMonthValue(), dateFocus.getYear()), rectangleHeight, stackPane);
                        }
                    }
                    if(today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate){
                        rectangle.setStroke(Color.BLUE);
                    }
                }
                calendar.getChildren().add(stackPane);
            }
        }
    }

    private void createCalendarActivity(List<PedidoManutencao> calendarActivities, double rectangleHeight, StackPane stackPane) {
        VBox calendarActivityBox = new VBox();

        for (int k = 0; k < calendarActivities.size(); k++) {
            if(k >= 1) {
                Text moreActivities = new Text("+" + (calendarActivities.size() - 1));
                calendarActivityBox.getChildren().add(moreActivities);
                break;
            }
            Text text = new Text("Funcionario: " + calendarActivities.get(k).getUtilizador().getNome() +
                    "\nNome da Embarcação: " + calendarActivities.get(k).getEmbarcacao().getNome() +
                    "\nDescrição: " + calendarActivities.get(k).getDescricao() +
                    "\nOficina: " + calendarActivities.get(k).getOficina().getNome());
            text.setStyle("-fx-font-size: 10");
            calendarActivityBox.getChildren().add(text);
        }
        calendarActivityBox.setTranslateY((rectangleHeight / 2) * 0.20);
        calendarActivityBox.setMaxWidth(20);
        calendarActivityBox.setMaxHeight(10);
        calendarActivityBox.setStyle("-fx-background-radius: 10; -fx-background-color: GHOSTWHITE");

        stackPane.getChildren().add(calendarActivityBox);
    }

    public void openPedidosList(int day, int month, int year) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("PedidoDayView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        PedidoDayController pedidoDayController =  fxmlLoader.getController();
        pedidoDayController.getDate(day, month, year);
        stage.showAndWait();
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void backOneMonth() {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth() {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void getMenu() {
        if (pane1.isVisible()){
            pane1.setVisible(false);
            pane1.setPrefWidth(0);
            pane2.setPrefWidth(1444);
            calendar.setPrefWidth(pane2.getPrefWidth());
        }
        else {
            pane1.setVisible(true);
            pane1.setPrefWidth(295);
            pane2.setPrefWidth(1165);
            calendar.setPrefWidth(pane2.getPrefWidth());
        }
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void createNovoPedido() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("PedidoNovoView.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setScene(scene);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
        calendar.getChildren().clear();
        drawCalendar();
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
