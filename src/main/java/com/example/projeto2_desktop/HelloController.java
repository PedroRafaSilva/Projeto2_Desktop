package com.example.projeto2_desktop;

import Agendamento.*;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.SQLException;

public class HelloController {

    private AgendamentoRepository agendamentoRepository = new AgendamentoRepository();

    private Agendamento agendamento = new Agendamento();

    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        try {
            welcomeText.setText(String.valueOf(agendamentoRepository.findAgendamentoById(1).getData()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}