package sample.startupMenu;

import sample.authReg.AuthController;
import sample.authReg.RegController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class StartupMenuController {
    private Socket clientSocket = null;
    private ObjectOutputStream writerObj = null;
    private ObjectInputStream readerObj = null;

    @FXML
    private Button btnAuth;

    @FXML
    private Button btnReg;

    @FXML
    void initialize() {
        try {
            this.clientSocket = new Socket("127.0.0.1", 8000);

            writerObj = new ObjectOutputStream(clientSocket.getOutputStream());
            readerObj = new ObjectInputStream(clientSocket.getInputStream());

            btnAuth.setOnAction(actionEvent -> {
                setWindow("auth");
            });

            btnReg.setOnAction(actionEvent -> {
                setWindow("reg");
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void setWindow (String windowName){
        Stage stage1 = (Stage) btnAuth.getScene().getWindow();
        stage1.hide();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../authReg/" + windowName + ".fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            if(Objects.equals(windowName, "reg")) {
                RegController controller = loader.getController();
                controller.getConnect(clientSocket, writerObj, readerObj, stage1);
            } else {
                AuthController controller = loader.getController();
                controller.getConnect(clientSocket, writerObj, readerObj, stage1);
            }

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
