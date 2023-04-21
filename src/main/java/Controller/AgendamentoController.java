package Controller;

import Marina.Marina;
import Marina.MarinaRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.sql.SQLException;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.ResourceBundle;

import static javafx.scene.control.PopupControl.USE_COMPUTED_SIZE;


public class AgendamentoController implements Initializable {

    private ZonedDateTime dateFocus, today;

    @FXML
    private VBox pane1, pane2;

    @FXML
    private FlowPane calendar;

    @FXML
    private Label year = new Label();

    @FXML
    private Label month = new Label();

    @FXML
    private ComboBox<String> marinaBox;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = ZonedDateTime.now();
        today = ZonedDateTime.now();
        drawCalendar();
        getAllMarinas();
    }

    public void getAllMarinas(){
        MarinaRepository marinaRepository = new MarinaRepository();
        try {
            List<Marina> marinas = marinaRepository.getAllMarinas();
            for (Marina mar : marinas){
                marinaBox.getItems().add(mar.getNome());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void drawCalendar(){
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
                rectangle.setFill(Color.GHOSTWHITE);
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
                    }
                    if(today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate){
                        rectangle.setStroke(Color.BLUE);
                    }
                }
                calendar.getChildren().add(stackPane);
            }
        }
    }

    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }

    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }


    @FXML
    void getMenu(MouseEvent event) {
        if (pane1.isVisible()){
            pane1.setVisible(false);
            pane1.setPrefWidth(0);
            pane2.setPrefWidth(1444);
            calendar.setPrefWidth(pane2.getPrefWidth());
        }
        else {
            pane1.setVisible(true);
            pane1.setPrefWidth(349);
            pane2.setPrefWidth(1095);
            calendar.setPrefWidth(pane2.getPrefWidth());
        }
        calendar.getChildren().clear();
        drawCalendar();

    }

}
