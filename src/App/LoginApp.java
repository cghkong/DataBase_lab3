package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginApp extends Application {
    @Override
    public void start(Stage stageLogin) throws Exception {
        Parent root1 = FXMLLoader.load(getClass().getResource("Login.fxml"));
        stageLogin.setTitle("ComChat");
        stageLogin.setScene(new Scene(root1, 600, 400));
        stageLogin.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
