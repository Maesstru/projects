package ctrls;

import dtos.AngajatDto;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.FestivalException;
import services.IFestivalObserver;
import services.IFestivalService;

import java.io.IOException;

public class LoginView extends BaseCtrl {

    IFestivalService fs;
    MainView mv;

    public Button login;
    public Button signup;

    public TextField username;
    public PasswordField parola;

    public void setService(IFestivalService fs) {
        this.fs = fs;
    }

    public void setMainView(MainView mv) {
        this.mv = mv;
    }

    private void loginHandler(ActionEvent event) {
        try{
            String _username = username.getText();
            String _password = parola.getText();

            AngajatDto ang = new AngajatDto();
            ang.setNume(_username);
            ang.setPassword(_password);

            fs.login(_username,_password,mv);

            mv.setLoggedIn(ang);
            showMessage("Login OK");
            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/main-view.fxml"));

            loader.setController(mv);
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle("Logged in as " + _username);
            stage.show();

            Stage st = (Stage) login.getScene().getWindow();
            st.close();

        } catch (FestivalException | IOException e) {
            showMessage(e.getMessage());
        }
    }

    private void signnupHandler(ActionEvent event) {
//        try{
//            signup.getScene().setRoot(views.getSignUp());
//        } catch (IOException _) {
//            showMessage("Something went wrong");
//        }

    }

    @FXML
    public void initialize() {
        login.setOnAction(this::loginHandler);
        signup.setOnAction(this::signnupHandler);
    }
}
