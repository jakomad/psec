import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import org.bouncycastle.util.encoders.Hex;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.File;

public class MyUI {
    File selectedFile = null;
    public MyUI(Stage primaryStage) {
        //primaryStage.setScene(
        mainStage(primaryStage);

        primaryStage.show();
    }

    private void mainStage(Stage primaryStage) {
        HBox hBox = new HBox();
        Button encryptButton = new Button("Encrypt");
        Button decryptButton = new Button("Decrypt");

        encryptButton.setOnAction(e -> {
            primaryStage.setScene(encryptionScene(primaryStage));
        });
        decryptButton.setOnAction(e -> {
            primaryStage.setScene(decryptionScene(primaryStage));
        });

        hBox.getChildren().add(encryptButton);
        hBox.getChildren().add(decryptButton);

        Scene mainScene = new Scene(hBox, 300, 100);
        primaryStage.setTitle("Encrypt/decrypt");

        primaryStage.setScene(mainScene);
    }

    private Scene encryptionScene(Stage primaryStage){
        FileChooser filechooser = new FileChooser();
        Button fileButton = new Button("Select file to encrypt");
        Button backButton = new Button("Back");
        Stage fileStage = new Stage();

        fileButton.setOnAction(e -> {
            selectedFile = filechooser.showOpenDialog(fileStage);
            System.out.println(selectedFile);
        });
        backButton.setOnAction(e -> {
            mainStage(primaryStage);
        });

        PasswordField passField = new PasswordField();

        Button startEncryptionButton = new Button("Encrypt file");

        startEncryptionButton.setOnAction(e -> {
            Encryptor.encryptFile(selectedFile.toString(), passField.getCharacters().toString().toCharArray());
            System.out.println(selectedFile.toString());
        });

        VBox vBox = new VBox();
        vBox.getChildren().add(backButton);
        vBox.getChildren().add(fileButton);
        vBox.getChildren().add(passField);
        vBox.getChildren().add(startEncryptionButton);

        Scene scene = new Scene( vBox,300, 100);

        return scene;
    }

    private Scene decryptionScene(Stage primaryStage){
        FileChooser filechooser = new FileChooser();
        Button fileButton = new Button("Select file to decrypt");
        Button backButton = new Button("Back");
        Stage fileStage = new Stage();

        fileButton.setOnAction(e -> {
            selectedFile = filechooser.showOpenDialog(fileStage);
            System.out.println(selectedFile);
        });

        backButton.setOnAction(e -> {
            mainStage(primaryStage);
        });

        PasswordField passField = new PasswordField();

        Button startDecryptionButton = new Button("Decrypt file");

        startDecryptionButton.setOnAction(e -> {
            Decryptor.decryptFile(selectedFile.toString(), passField.getCharacters().toString().toCharArray());
            System.out.println(selectedFile.toString());
        });

        VBox vBox = new VBox();
        vBox.getChildren().add(backButton);
        vBox.getChildren().add(fileButton);
        vBox.getChildren().add(passField);
        vBox.getChildren().add(startDecryptionButton);

        Scene scene = new Scene( vBox,300, 100);

        return scene;
    }
}