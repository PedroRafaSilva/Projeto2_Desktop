package Controller.Login;

import Embarcacao.Embarcacao;
import Embarcacao.EmbarcacaoService;
import Fatura.Fatura;
import Fatura.FaturaService;
import ListaEstadoFatura.ListaEstadoFatura;
import ListaEstadoFatura.ListaEstadoFaturaService;
import Route.Routes;
import Utilizador.Utilizador;
import Utilizador.UtilizadorService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;


public class LoginController {

    private final UtilizadorService utilizadorService = new UtilizadorService();

    @FXML
    private Label errorMessage;

    @FXML
    private PasswordField passwordText;

    @FXML
    private TextField usernameText;

    private static int userId;

    public static int getUserId() {
        return userId;
    }

    @FXML
    public void Login(ActionEvent event) {
        checkLogin(event);
    }

    public void checkLogin(ActionEvent event) {
        try {
            boolean login;
            login = utilizadorService.isUserAlreadyRegistered(usernameText.getText(), passwordText.getText());
            if (login){
                generateFaturas();
                userId = utilizadorService.getUtilizadorByUserNome(usernameText.getText()).getIdutilizador();
                goToMenu(event);
            } else {
                errorMessage.setText("Dados Incorretos!!");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void goToMenu(ActionEvent event) throws IOException {
        Routes.handleGeneric(event, "Marinar - Gestão de Várias Marinas", "AgendamentoView.fxml");
    }

    public void generateFaturas(){
        UtilizadorService utilizadorService = new UtilizadorService();
        FaturaService faturaService = new FaturaService();
        ListaEstadoFaturaService listaEstadoFaturaService = new ListaEstadoFaturaService();
        ListaEstadoFatura listaEstadoFatura = new ListaEstadoFatura();
        for (Utilizador utilizador: utilizadorService.getAllClientes()){
            if(!faturaService.checkFaturaOfMothFromCliente(utilizador.getIdutilizador(), LocalDate.now().getMonthValue())){
                Fatura fatura = new Fatura();
                fatura.setDatacriacao(Date.valueOf(LocalDate.now()));
                fatura.setPrazo(Date.valueOf(LocalDate.now().plusMonths(1)));
                fatura.setValorembarcacoes(getValorEmbarcacoes(utilizador));
                fatura.setValoragendamento(0f);
                fatura.setValormanutencao(0f);
                fatura.setValortotal(0f);
                fatura.setNumfiscal(utilizador.getIdutilizador() + LocalDate.now().getYear() + LocalDate.now().getMonthValue() * 1234567);
                fatura.setIdutilizador(utilizador.getIdutilizador());
                faturaService.createFatura(fatura);
                listaEstadoFatura.setIdfatura(fatura.getIdfatura());
                listaEstadoFatura.setIdestado(1);
                listaEstadoFatura.setData(Date.valueOf(LocalDate.now()));
                listaEstadoFaturaService.createListaEstadoFatura(listaEstadoFatura);
            } else {
                Fatura fatura = faturaService.getFaturaOfMothFromCliente(utilizador.getIdutilizador(), LocalDate.now().getMonthValue());
                fatura.setValorembarcacoes(getValorEmbarcacoes(utilizador));
                fatura.setValortotal(fatura.getValoragendamento() + fatura.getValorembarcacoes()  + fatura.getValormanutencao());
                faturaService.updateFatura(fatura);
            }
        }
    }

    public float getValorEmbarcacoes(Utilizador utilizador){
        EmbarcacaoService embarcacaoService = new EmbarcacaoService();
        float valor = 0;
        for (Embarcacao embarcacao: embarcacaoService.getAllEmbarcacaos()){
            if (embarcacao.getIdUtilizador() == utilizador.getIdutilizador()) {
                valor += embarcacao.getDescComprimento().getValorcomprimento();
            }
        }
        return valor;
    }



}
