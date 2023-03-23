package sample.adminUserMenus;

import sample.authReg.AuthController;
import sample.uBasicMenu.UBasicMenu1Controller;
import sample.uBasicMenu.UBasicMenu2Controller;
import sample.uBasicMenu.UBasicMenu3Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class UserMenu {
    private Socket clientSocket = null;
    private ObjectOutputStream writerObj = null;
    private ObjectInputStream readerObj = null;
    private int role = 0;
    private Stage stage = null;

    @FXML
    private Button btnBack;

    @FXML
    private Button btnChange;

    @FXML
    private Button btnSellJw;

    @FXML
    private Button btnStock;

    @FXML
    private Button btnTransact;

    @FXML
    private Button btnRefWithd;


    @FXML
    void initialize(){
        btnBack.setOnAction(actionEvent -> {
            setWindow("../authReg/auth.fxml", "");
        });

        btnChange.setOnAction(actionEvent -> {
            setWindow("../uBasicMenu/uBasicMenu3.fxml", "changes");
        });

        btnSellJw.setOnAction(actionEvent -> {
            setWindow("../uBasicMenu/uBasicMenu4.fxml", "selljws");
        });

        btnStock.setOnAction(actionEvent -> {
            setWindow("../uBasicMenu/uBasicMenu2.fxml", "stocks");
        });

        btnTransact.setOnAction(actionEvent -> {
            setWindow("../uBasicMenu/uBasicMenu1.fxml", "transacts");
        });

        btnRefWithd.setOnAction(actionEvent -> {
            setWindow("../uBasicMenu/uBasicMenu5.fxml", "refwithds");
        });
    }

    void setWindow (String windowName, String table){
        Stage stage1 = (Stage) btnBack.getScene().getWindow();
        stage1.close();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(windowName));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            switch (windowName) {
                case "../authReg/auth.fxml" -> {
                    AuthController controller = loader.getController();
                    controller.getConnect(clientSocket, writerObj, readerObj, stage1);
                }
                case "../uBasicMenu/uBasicMenu1.fxml" -> {
                    UBasicMenu1Controller controller = loader.getController();
                    controller.getConnected1(clientSocket, writerObj, readerObj, stage1);
                }
                case "../uBasicMenu/uBasicMenu2.fxml" -> {
                    UBasicMenu2Controller controller = loader.getController();
                    controller.getConnected2(clientSocket, writerObj, readerObj, stage1, table);
                }
                case "../uBasicMenu/uBasicMenu3.fxml" -> {
                    UBasicMenu3Controller controller = loader.getController();
                    controller.getConnected3(clientSocket, writerObj, readerObj, stage1);
                }
            }

            stage.show();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void getConnection(Socket clientSocket, ObjectOutputStream writerObj, ObjectInputStream readerObj,
                                  Stage stage){
        this.clientSocket = clientSocket;
        this.writerObj = writerObj;
        this.readerObj = readerObj;
        this.stage = stage;
    }
}
