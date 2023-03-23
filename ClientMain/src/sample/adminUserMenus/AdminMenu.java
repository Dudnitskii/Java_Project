package sample.adminUserMenus;

import sample.authReg.AuthController;
import sample.aBasicMenu.ABasicMenu1Controller;
import sample.aBasicMenu.ABasicMenu2Controller;
import sample.aBasicMenu.ABasicMenu3Controller;
import sample.aBasicMenu.ABasicMenu4Controller;
import sample.aBasicMenu.ABasicMenu5Controller;
import sample.aBasicMenu.ABasicMenu6Controller;
import sample.aBasicMenu.ABasicMenu7Controller;
import sample.aBasicMenu.ABasicMenu8Controller;
import sample.aBasicMenu.ABasicMenu9Controller;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class AdminMenu {
    private Socket clientSocket = null;
    private ObjectOutputStream writerObj = null;
    private ObjectInputStream readerObj = null;
    private int role = 1;
    private Stage stage = null;

    @FXML
    private Button btnBack;


    @FXML
    private Button btnTransact;

    @FXML
    private Button btnSellJw;

    @FXML
    private Button btnStock;

    @FXML
    private Button btnChange;

    @FXML
    private Button btnCurrs;

    @FXML
    private Button btnCompanies;

    @FXML
    private Button bthJwls;

    @FXML
    private Button btnUsers;

    @FXML
    private Button btnRefWitdh;


    @FXML
    void initialize(){
        btnBack.setOnAction(actionEvent -> {
            setWindow("../authReg/auth.fxml", "");
        });

        btnTransact.setOnAction(actionEvent -> {
            setWindow("../aBasicMenu/aBasicMenu1.fxml", "transacts");
        });

        btnRefWitdh.setOnAction(actionEvent -> {
            setWindow("../aBasicMenu/aBasicMenu2.fxml", "refwithds");
        });

        btnChange.setOnAction(actionEvent -> {
            setWindow("../aBasicMenu/aBasicMenu3.fxml", "changes");
        });

        bthJwls.setOnAction(actionEvent -> {
            setWindow("../aBasicMenu/aBasicMenu4.fxml", "jwls");
        });

        btnUsers.setOnAction(actionEvent -> {
            setWindow("../aBasicMenu/aBasicMenu5.fxml", "users");
        });

        btnSellJw.setOnAction(actionEvent -> {
            setWindow("../aBasicMenu/aBasicMenu6.fxml", "selljws");
        });

        btnStock.setOnAction(actionEvent -> {
            setWindow("../aBasicMenu/aBasicMenu7.fxml", "stocks");
        });

        btnCurrs.setOnAction(actionEvent -> {
            setWindow("../aBasicMenu/aBasicMenu8.fxml", "currencies");
        });

        btnCompanies.setOnAction(actionEvent -> {
            setWindow("../aBasicMenu/aBasicMenu9.fxml", "companies");
        });

    }

    void setWindow (String windowName, String table){
        Stage stage1 = (Stage) btnBack.getScene().getWindow();
        stage1.hide();
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
                case "../aBasicMenu/aBasicMenu1.fxml" -> {
                    ABasicMenu1Controller controller = loader.getController();
                    controller.getConnected1(clientSocket, writerObj, readerObj, stage1);
                }
                case "../aBasicMenu/aBasicMenu2.fxml" -> {
                    ABasicMenu2Controller controller = loader.getController();
                    controller.getConnected2(clientSocket, writerObj, readerObj, stage1);
                }
                case "../aBasicMenu/aBasicMenu3.fxml" -> {
                    ABasicMenu3Controller controller = loader.getController();
                    controller.getConnected3(clientSocket, writerObj, readerObj, stage1);
                }
                case "../aBasicMenu/aBasicMenu4.fxml" -> {
                    ABasicMenu4Controller controller = loader.getController();
                    controller.getConnected4(clientSocket, writerObj, readerObj, stage1);
                }
                case "../aBasicMenu/aBasicMenu5.fxml" -> {
                    ABasicMenu5Controller controller = loader.getController();
                    controller.getConnected5(clientSocket, writerObj, readerObj, stage1);
                }
                case "../aBasicMenu/aBasicMenu6.fxml" -> {
                    ABasicMenu6Controller controller = loader.getController();
                    controller.getConnected6(clientSocket, writerObj, readerObj, stage1);
                }
                case "../aBasicMenu/aBasicMenu7.fxml" -> {
                    ABasicMenu7Controller controller = loader.getController();
                    controller.getConnected7(clientSocket, writerObj, readerObj, stage1);
                }
                case "../aBasicMenu/aBasicMenu8.fxml" -> {
                    ABasicMenu8Controller controller = loader.getController();
                    controller.getConnected8(clientSocket, writerObj, readerObj, stage1);
                }
                case "../aBasicMenu/aBasicMenu9.fxml" -> {
                    ABasicMenu9Controller controller = loader.getController();
                    controller.getConnected9(clientSocket, writerObj, readerObj, stage1);
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
