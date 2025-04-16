package ctrls;

import dtos.ConcertDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import services.IFestivalService;

public class VanzareCtrl extends BaseCtrl{

    public Label artist;
    public Label data;
    public Label locatie;
    public Spinner<Integer> cantitate;
    public TextField nume;
    public Button vinde;

    ConcertDto concert;

    IFestivalService serv;

    public void setServ(IFestivalService serv) {
        this.serv = serv;
    }

    public VanzareCtrl(ConcertDto concert) {
        this.concert = concert;
    }

    private void vindeHandler(ActionEvent event) {
        String numeText = nume.getText();
        Integer cantitateValue = cantitate.getValue();

        if(numeText.isEmpty())
        {
            showMessage("Trebuie sa completezi numele!");
            return;
        }
        try{
            serv.sellTickets(concert, cantitateValue);
        } catch (Exception e) {
            showMessage(e.getMessage());
            return;
        }

//        showMessage("Ai vandut " + cantitateValue + " bilete catre " + numeText);
        Stage st = (Stage) nume.getScene().getWindow();
        st.close();
    }

    @FXML
    public void initialize() {

        SpinnerValueFactory.IntegerSpinnerValueFactory valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, 1);
        valueFactory.setMin(1);
        cantitate.setValueFactory(valueFactory);

        artist.setText("Artist: " + concert.getArtist());
        locatie.setText("Locatie: " + concert.getLocatie());
        data.setText("Data: " + concert.getConcertDate().toLocalDate().toString());
        vinde.setOnAction(this::vindeHandler);
    }
}
