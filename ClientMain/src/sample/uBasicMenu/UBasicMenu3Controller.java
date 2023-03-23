package sample.uBasicMenu;

//import com.company.classes.MasterClass;
//import com.company.classes.SituationsFromPractise;
//import com.company.classes.Training;
//import com.company.classes.Universal;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.adminUserMenus.UserMenu;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;

public class UBasicMenu3Controller {
    private Socket clientSocket = null;
    private ObjectOutputStream writerObj = null;
    private ObjectInputStream readerObj = null;
    private Stage stage = null;
    private String type = null;

    private static final String ADD_CHANGE = "add change";
    private static final String SET_CHOICES = "set choices";

    @FXML
    private Button btnSubmit;

    @FXML
    private Button btnBack;

    @FXML
    private TextField input1;

    @FXML
    private TextField input2;

    @FXML
    private ChoiceBox choiseBox1;

    @FXML
    private ChoiceBox choiseBox2;

    @FXML
    private DatePicker pickDate;

    @FXML
    private Label infoArea;

    @FXML
    private Text logoTxt;

    @FXML
    private Text choiceTxt;

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
            if(pickDate.getValue() == null || input1.getText() == null || input2.getText() == null
                    || choiseBox1.getSelectionModel().getSelectedItem() == null || choiseBox2.getSelectionModel().getSelectedItem() == null) {
                infoArea.setText("Все поля должны быть заполнены!!!");
            } else {
                Integer.parseInt(input1.getText());

                String request = pickDate.getValue() + "_" + input1.getText() + "_" + input2.getText()
                        + "_" + choiseBox1.getSelectionModel().getSelectedItem() + "_" + choiseBox2.getSelectionModel().getSelectedItem();

                writerObj.writeObject(ADD_CHANGE);
                writerObj.writeObject(request);


            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e){
            infoArea.setText("Неверный тип данных!");
        }
    }

    public void getConnected3(Socket clientSocket, ObjectOutputStream writerObj, ObjectInputStream readerObj, Stage stage) throws IOException, ClassNotFoundException {
        this.clientSocket = clientSocket;
        this.writerObj = writerObj;
        this.readerObj = readerObj;
        this.stage = stage;

        ArrayList<String> choices = new ArrayList<>();



        writerObj.writeObject(SET_CHOICES);
        writerObj.writeObject(type);
        choices = (ArrayList) readerObj.readObject();


        choiseBox1.setItems(FXCollections.observableArrayList(choices));
        choiseBox2.setItems(FXCollections.observableArrayList(choices));

        pickDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
    }
}
