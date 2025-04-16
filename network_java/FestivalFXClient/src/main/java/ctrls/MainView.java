package ctrls;

import dtos.AngajatDto;
import dtos.ConcertDto;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import services.FestivalException;
import services.IFestivalObserver;
import services.IFestivalService;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class MainView extends BaseCtrl implements IFestivalObserver {
    public TableView<ConcertDto> tabel;
    public DatePicker datePicker;
    public TableView<ConcertDto> tabel_filtrat;
    public Button vinde;
    public Label concertCurent;
    public Button logout;
    public Button reload;

    private ObservableList<ConcertDto> data;
    private ObservableList<ConcertDto> filtrat;

    private ConcertDto concertSelectat;

    private AngajatDto loggedIn;

    public void setLoggedIn(AngajatDto loggedIn) {
        this.loggedIn = loggedIn;
    }

    IFestivalService serv;

    public void setServ(IFestivalService serv) {
        this.serv = serv;
    }

    private void initMainTable() {
        TableColumn<ConcertDto, String> artist = new TableColumn<>("Artist");
        TableColumn<ConcertDto, String> dataC = new TableColumn<>("Data");
        TableColumn<ConcertDto, String> loc = new TableColumn<>("Loc");
        TableColumn<ConcertDto, String> disponibilitate = new TableColumn<>("Disponibilitate");
        TableColumn<ConcertDto, String> vanzari = new TableColumn<>("Vanzari");

        artist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        dataC.setCellValueFactory(new PropertyValueFactory<>("concertDate"));
        loc.setCellValueFactory(new PropertyValueFactory<>("locatie"));
        disponibilitate.setCellValueFactory(new PropertyValueFactory<>("disponibile"));
        vanzari.setCellValueFactory(new PropertyValueFactory<>("ocupate"));

        tabel.getColumns().addAll(artist, dataC, loc, disponibilitate, vanzari);
        tabel.setItems(data);
        tabel.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            concertSelectat = (ConcertDto) newValue;
            if(concertSelectat != null) {
                concertCurent.setText(newValue.toString());
            } else {
                concertCurent.setText("Selecteaza un concert!");
                concertSelectat = null;
            }

        });
        tabel.setRowFactory(tv -> {
            TableRow<ConcertDto> row = new TableRow<>();
            ChangeListener<Object> updateStyle = (obs, oldVal, newVal) -> {
                ConcertDto item = row.getItem();

                if (item == null || row.isEmpty()) {
                    row.setStyle("");
                    return;
                }

                String backgroundColor;
                if(row.isSelected()) {
                    backgroundColor = "gray";
                }

                else if (item.getDisponibile() < 1) {
                    backgroundColor = "red";
                } else if (item.getDisponibile() < 50) {
                    backgroundColor = "orange";
                } else {
                    backgroundColor = "white";
                }

                // You can also change border or text fill if selected
                String borderColor = row.isSelected() ? "green" : "transparent";

                row.setStyle(
                        "-fx-background-color: " + backgroundColor + ";" +
                                "-fx-border-color: " + borderColor + ";" +
                                "-fx-border-width: 1px;"
                );
            };

            row.itemProperty().addListener(updateStyle);
            row.selectedProperty().addListener(updateStyle);


//            row.setStyle("-fx-background-color: white;-fx-text-fill: gray; -fx-border-width: 1px;"); // Default color
//
//            row.selectedProperty().addListener((obs, wasSelected, isNowSelected) -> {
//                if (isNowSelected) {
//                    row.setStyle("-fx-text-fill: gray; -fx-border-color: green");  // White text on blue
//                } else {
//                    row.setStyle("-fx-border-color: transparent;");
//                }
//            });
//
//            row.itemProperty().addListener((obs, oldItem, newItem) -> {
//                if (newItem != null) {
//                    if (newItem.getDisponibile() < 1) {
//                        row.setStyle("-fx-background-color: red;");
//                    }
//                    else if (newItem.getDisponibile() < 50) {
//                        row.setStyle("-fx-background-color: orange;");
//                    }
//                    else {
//                        row.setStyle("-fx-background-color: white;");
//                    }
//                }
//            });
            Platform.runLater(() -> updateStyle.changed(null, null, null));
            return row;
        });
    }

    private void initFilteredTable() {
        TableColumn<ConcertDto, String> artist = new TableColumn<>("Artist");
        TableColumn<ConcertDto, String> loc = new TableColumn<>("Loc");
        TableColumn<ConcertDto, String> disponibilitate = new TableColumn<>("Disponibilitate");
        TableColumn<ConcertDto, String> vanzari = new TableColumn<>("Vanzari");

        artist.setCellValueFactory(new PropertyValueFactory<>("artist"));
        loc.setCellValueFactory(new PropertyValueFactory<>("locatie"));
        disponibilitate.setCellValueFactory(new PropertyValueFactory<>("disponibile"));
        vanzari.setCellValueFactory(new PropertyValueFactory<>("ocupate"));

        tabel_filtrat.getColumns().addAll(artist, loc, disponibilitate, vanzari);
        tabel_filtrat.setItems(filtrat);
        tabel_filtrat.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            concertSelectat = (ConcertDto) newValue;
            if(concertSelectat != null) {
                concertCurent.setText(newValue.toString());
            } else {
                concertCurent.setText("Selecteaza un concert!");
                concertSelectat = null;
            }
        });
    }

    private void vindeHandler(ActionEvent actionEvent) {
        try{
            if(concertSelectat == null)
            {
                showMessage("Selecteaza un concert!");

                return;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vanzare-view.fxml"));
            VanzareCtrl vanzareCtrl = new VanzareCtrl(concertSelectat);
            vanzareCtrl.setServ(serv);
            loader.setController(vanzareCtrl);
            Scene scene = new Scene(loader.load());
            Stage s = new Stage();
            s.setScene(scene);
            s.setTitle("Bilet");
            s.show();
        } catch (IOException e)
        {
            e.printStackTrace();
            showMessage(e.getMessage());
        }


    }

    void updateFiltrate() {
        Collection<ConcertDto> concerte = serv.getConcerts();
        filtrat.clear();
        LocalDate date = datePicker.getValue();
        if (date == null) {
            filtrat.addAll(concerte);
        }
        else {
            concerte.stream().
                    filter(concerteDto -> concerteDto.getConcertDate().toLocalDate().equals(date)).
                    forEach(concerteDto -> {filtrat.add(concerteDto);});
        }
    }

    void updateAll() {
        Collection<ConcertDto> concerte = serv.getConcerts();
        data.clear();
        data.addAll(concerte);
    }

    private void load() {
        updateAll();
        updateFiltrate();
    }

    private void initData( ) {
        data = FXCollections.observableArrayList();
        filtrat = FXCollections.observableArrayList();
        load();
        datePicker.setOnAction(event -> {updateFiltrate();});
    }

    private void logoutHandler(ActionEvent actionEvent){

        try{
            serv.logout(loggedIn);
            showMessage("Logout successful!");
            Stage stage = (Stage)logout.getScene().getWindow();
            stage.close();
        } catch (FestivalException e) {
            showMessage(e.getMessage());
        }
    }

    private void reloadHandler(ActionEvent actionEvent) {
        load();
    }


    @FXML
    public void initialize() {
        try{
            initData();
            initMainTable();
            initFilteredTable();
            vinde.setOnAction(this::vindeHandler);
            logout.setOnAction(this::logoutHandler);
            reload.setOnAction(this::reloadHandler);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void updateBilete(ConcertDto concert) throws FestivalException {

        Platform.runLater(() -> {
            if(concertSelectat != null && Objects.equals(concertSelectat.getcId(), concert.getcId())) {
                concertSelectat = concert;
                concertCurent.setText(concert.toString());
            }
            for(int i = 0;i<data.size();i++) {
                if(Objects.equals(data.get(i).getcId(), concert.getcId())) {
                    data.set(i, concert);
                }
            }
            for(int i = 0;i<filtrat.size();i++) {
                if(Objects.equals(filtrat.get(i).getcId(), concert.getcId())) {
                    filtrat.set(i, concert);
                }
            }
            tabel.refresh();
            tabel_filtrat.refresh();
        });

    }
}
