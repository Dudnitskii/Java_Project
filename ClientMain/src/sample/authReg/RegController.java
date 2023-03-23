package sample.authReg;

import sample.classes.solo.User;
import sample.adminUserMenus.UserMenu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class RegController {
    private Socket clientSocket = null;
    private ObjectOutputStream writerObj = null;
    private ObjectInputStream readerObj = null;
    private Stage stage = null;

    private static final String ADD_USER = "add user";
    private static final String CHECK_USER = "check user";

    @FXML
    private Button btnCreate;

    @FXML
    private TextField inputName;

    @FXML
    private TextField inputLogin;

    @FXML
    private PasswordField inputPass;

    @FXML
    private PasswordField rePass;

    @FXML
    private Label infoArea;

    @FXML
    void initialize() {
        btnCreate.setOnAction(actionEvent -> {
            String name, login, pass, rePsw, newWindow = "userMenu";
            int role = 0;

            name = inputName.getText();
            login = inputLogin.getText();
            pass = inputPass.getText();
            rePsw = rePass.getText();

            if(Objects.equals(name, "") || Objects.equals(login, "") || Objects.equals(pass, "")
                 || Objects.equals(rePsw, "")){
                infoArea.setText("Все поля должны быть заполнены!");
            } else {
                try {
                    writerObj.writeObject(CHECK_USER);
                    writerObj.writeObject(login);
                    Boolean unique = (Boolean) readerObj.readObject();

                    if(unique){
                        if(Objects.equals(pass, rePsw)){


                            User user = new User(login, pass, role, name);

                            writerObj.writeObject(ADD_USER);
                            writerObj.writeObject(user);

                            String res = (String) readerObj.readObject();
                            System.out.println(res);

                            Stage stage1 = (Stage) btnCreate.getScene().getWindow();
                            stage1.close();

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("../adminUserMenus/" + newWindow + ".fxml"));
                            Parent root = loader.load();
                            Stage stage = new Stage();
                            stage.setScene(new Scene(root));

                            UserMenu controller = loader.getController();
                            controller.getConnection(clientSocket, writerObj, readerObj, stage1);

                            stage.show();
                        } else{
                            System.out.println("Пароли должны совпадать!");
                        }
                    } else infoArea.setText("Такой логин уже существует!");
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getConnect(Socket clientSocket, ObjectOutputStream writerObj, ObjectInputStream readerObj, Stage stage){
        this.clientSocket = clientSocket;
        this.writerObj = writerObj;
        this.readerObj = readerObj;
        this.stage = stage;
    }
}
