import javafx.application.Application;
import javafx.stage.Stage;

import java.security.Provider;
import java.security.Security;

public class Main extends Application{
    public void start(Stage primaryStage) {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        MyUI ui = new MyUI(primaryStage);
    }
}