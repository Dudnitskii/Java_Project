package sample.uBasicMenu;

import sample.adminUserMenus.UserMenu;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;
import java.time.LocalDate;

public class UBasicMenu1Controller {
    private Socket clientSocket = null;
    private ObjectOutputStream writerObj = null;
    private ObjectInputStream readerObj = null;
    private Stage stage = null;

    private static final String ADD_TRANSACT = "add transact";

    @FXML
    private Button btnSubmit;

    @FXML
    private Button btnBack;

    @FXML
    private TextField inputSum;

    @FXML
    private TextField inputCVV;

    @FXML
    private TextField inputOtherId;

    @FXML
    private TextField inputComment;

    @FXML
    private DatePicker pickDate;

    @FXML
    private Label infoArea;

    @FXML
    void initialize() {
        btnBack.setOnAction(actionEvent -> {
            setWindow();
        });

        btnSubmit.setOnAction(actionEvent -> {
            doSubmit();
        });
    }

    void setWindow (){

        Stage stage1 = (Stage) btnBack.getScene().getWindow();
        stage1.close();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../adminUserMenus/userMenu.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            UserMenu controller = loader.getController();
            controller.getConnection(clientSocket, writerObj, readerObj, stage1);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void doSubmit (){
        try {
            if(pickDate.getValue() == null || inputSum.getText() == null || inputCVV.getText() == null
                    || inputComment.getText() == null || inputOtherId.getText() == null) {
                infoArea.setText("Все поля должны быть заполнены!!!");
            } else {
                Integer.parseInt(inputSum.getText());
                Integer.parseInt(inputCVV.getText());
                Integer.parseInt(inputOtherId.getText());

                String request = pickDate.getValue() + "_" + inputSum.getText() + "_" + inputCVV.getText()
                        + "_" + inputOtherId.getText() + "_" + inputComment.getText();

                writerObj.writeObject(ADD_TRANSACT);
                writerObj.writeObject(request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e){
            infoArea.setText("Неверный тип данных!");
        }
    }

    public void getConnected1(Socket clientSocket, ObjectOutputStream writerObj, ObjectInputStream readerObj, Stage stage) throws IOException, ClassNotFoundException {
        this.clientSocket = clientSocket;
        this.writerObj = writerObj;
        this.readerObj = readerObj;
        this.stage = stage;




        pickDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
    }
}
