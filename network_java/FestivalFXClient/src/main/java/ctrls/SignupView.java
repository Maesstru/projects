package ctrls;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import java.io.IOException;

public class SignupView extends BaseCtrl{
    public Button login;
    public Button signup;

    private void loginHandler(ActionEvent event) {
//        try{
//            login.getScene().setRoot(views.getLogIn());
//        } catch (IOException e) {
//            showMessage("Something went wrong");
//        }
    }

    private void signupHandler(ActionEvent event) {
        showMessage("Signup");
    }

    @FXML
    public void initialize() {
        login.setOnAction(this::loginHandler);
        signup.setOnAction(this::signupHandler);
    }

}
