
import ctrls.LoginView;
import ctrls.MainView;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import services.IFestivalService;

import java.io.IOException;

public class StartClient extends Application {
    public void start(Stage stage) throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("ClientConfig.xml");

        IFestivalService festivalService = (IFestivalService) context.getBean("festivalProxy");
        FXMLLoader loader = new FXMLLoader(StartClient.class.getResource("login-view.fxml"));

        LoginView ctrl = new LoginView();
        ctrl.setService(festivalService);
        MainView mv = new MainView();
        mv.setServ(festivalService);
        ctrl.setMainView(mv);


        loader.setController(ctrl);

        Scene sc = new Scene(loader.load());
        stage.setScene(sc);
        stage.show();
    }
}
