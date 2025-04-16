package ctrls;

import javafx.scene.control.Alert;

public class BaseCtrl {
    public void showMessage(String msg){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }
}
