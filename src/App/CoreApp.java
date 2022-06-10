package App;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class CoreApp extends Application {
    @Override
    public void start(Stage stageCore) throws Exception {
        Parent root1 = FXMLLoader.load(getClass().getResource("core.fxml"));
        stageCore.setTitle("ComChat");
        stageCore.setScene(new Scene(root1, 846.0, 567.0));
        stageCore.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
