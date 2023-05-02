module com.example.projeto2_desktop {
    requires javafx.controls;
    requires javafx.fxml;
    requires Projeto2;
    requires java.sql;


    opens com.example.projeto2_desktop to javafx.fxml;
    exports com.example.projeto2_desktop;
    exports Controller.Agendamento;
    opens Controller.Agendamento to javafx.fxml;
    exports Controller.Login;
    opens Controller.Login to javafx.fxml;
    exports Controller.Cliente;
    opens Controller.Cliente to javafx.fxml;
    exports Controller.Embarcacao;
    opens Controller.Embarcacao to javafx.fxml;
    exports Controller.Funcionario;
    opens Controller.Funcionario to javafx.fxml;
    exports Controller.Marina;
    opens Controller.Marina to javafx.fxml;
    exports Controller.Oficina;
    opens Controller.Oficina to javafx.fxml;
    exports Controller.PedidoManutencao;
    opens Controller.PedidoManutencao to javafx.fxml;
}