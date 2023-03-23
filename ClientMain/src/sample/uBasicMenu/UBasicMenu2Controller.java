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

public class UBasicMenu2Controller {
    private Socket clientSocket = null;
    private ObjectOutputStream writerObj = null;
    private ObjectInputStream readerObj = null;
    private Stage stage = null;
    private String type = null;

    private static final String ADD_REFWITHD = "add refwithd";
    private static final String ADD_SELLJW = "add selljw";
    private static final String ADD_STOCK = "add stock";
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
    private TextField input3;

    @FXML
    private ChoiceBox choiceBox;

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
                    || choiceBox.getSelectionModel().getSelectedItem() == null || input3.getText() == null) {
                infoArea.setText("Все поля должны быть заполнены!!!");
            } else {
                Integer.parseInt(input1.getText());
                Integer.parseInt(input3.getText());

                String request = pickDate.getValue() + "_" + input1.getText() + "_" + input2.getText()
                        + "_" + choiceBox.getSelectionModel().getSelectedItem() + "_" + input3.getText();

                switch (type){
                    case "refwithds":
                        writerObj.writeObject(ADD_REFWITHD);
                        writerObj.writeObject(request);
                        break;
                    case "selljws":
                        writerObj.writeObject(ADD_SELLJW);
                        writerObj.writeObject(request);
                        break;
                    case "stocks":
                        writerObj.writeObject(ADD_STOCK);
                        writerObj.writeObject(request);
                        break;
                }


            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e){
            infoArea.setText("Неверный тип данных!");
        }
    }

    public void getConnected2(Socket clientSocket, ObjectOutputStream writerObj, ObjectInputStream readerObj, Stage stage, String type) throws IOException, ClassNotFoundException {
        this.clientSocket = clientSocket;
        this.writerObj = writerObj;
        this.readerObj = readerObj;
        this.stage = stage;
        this.type = type;

        String[] type1 = {"Пополнение счета/снятие денег", "Сумма", "Адрес", "Операция", "CVV", "Подтвердить"};
        String[] type2 = {"Продажа ценностей", "Сумма", "Адрес", "Наименование", "Масса", "Продать"};
        String[] type3 = {"Покупка акций", "Сумма", "Адрес", "Компания", "Кол-во акций", "Купить"};

        String[] placeholders = switch (type) {
            case "refwithds" -> type1;
            case "selljws" -> type2;
            case "stocks" -> type3;
            default -> new String[]{};
        };

        logoTxt.setText(placeholders[0]);
        input1.setPromptText(placeholders[1]);
        input2.setPromptText(placeholders[2]);
        choiceTxt.setText(placeholders[3]);
        input3.setPromptText(placeholders[4]);
        btnSubmit.setText(placeholders[5]);

        ArrayList<String> choices = new ArrayList<>();

        switch (type) {
            case "refwithds" -> {
                choices.add("Вывод");
                choices.add("Пополнение");
            }
            case "selljws", "stocks" -> {
                writerObj.writeObject(SET_CHOICES);
                writerObj.writeObject(type);
                choices = (ArrayList) readerObj.readObject();
            }
        }




        choiceBox.setItems(FXCollections.observableArrayList(choices));

        pickDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
    }
}
