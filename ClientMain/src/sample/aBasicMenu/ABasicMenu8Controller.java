package sample.aBasicMenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.adminUserMenus.AdminMenu;
import sample.classes.solo.Currencies;
import sample.classes.solo.Basic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Objects;

public class ABasicMenu8Controller {
    private Socket clientSocket = null;
    private ObjectOutputStream writerObj = null;
    private ObjectInputStream readerObj = null;
    private Stage stage = null;
    private String type = "currencies";

    private static final String SHOW_QUERY = "show";
    private static final String DELETE_QUERY = "delete";
    private static final String UPDATE_QUERY = "update";
    private static final String SEARCH_QUERY = "search";

    private static final String ADD_CURRENCY = "add currency";

    @FXML
    private Button btnBack;

    @FXML
    private TableView<Object> tableTransact;

    @FXML
    private TableColumn<Currencies, Integer> IDColumn;

    @FXML
    private TableColumn<Currencies, String> infoColumn;

    @FXML
    private Button btnAdd;

    @FXML
    private Button btnChange;

    @FXML
    private Button btnDelete;

    @FXML
    private Button btnRefresh;

    @FXML
    private Button btnSearch;

    @FXML
    private Label infoArea;

    @FXML
    private TextField inputID;

    @FXML
    private TextField inputInfo;

    @FXML
    private Text logoTxt;

    @FXML
    void initialize(){
        btnBack.setOnAction(actionEvent -> {
            setWindowBack();
        });

        btnRefresh.setOnAction(actionEvent -> {
            tableTransact.setItems(getNotes());
            infoArea.setText("Обновлено!");
        });

        btnChange.setOnAction(actionEvent -> {
            Currencies selectedItem = (Currencies) tableTransact.getSelectionModel().getSelectedItem();

            if(selectedItem != null) {
                doUpdate(selectedItem.getId());
            } else infoArea.setText("Выберите запись!");
        });

        btnAdd.setOnAction(actionEvent -> {
            doSubmit();
        });

        btnDelete.setOnAction(actionEvent -> {
            int id = 0;
            Currencies selectedItem = (Currencies) tableTransact.getSelectionModel().getSelectedItem();

            if(selectedItem != null) id = selectedItem.getId();


            if(id != 0){
                try {
                    writerObj.writeObject(DELETE_QUERY);
                    writerObj.writeObject(id + ";" + type);

                    tableTransact.setItems(getNotes());
                    infoArea.setText("Удалено!");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                infoArea.setText("Выберите запись!");
            }
        });

        btnSearch.setOnAction(actionEvent -> {
            try {
                if(Objects.equals(inputID.getText(), "") && Objects.equals(inputInfo.getText(), "")) {
                    infoArea.setText("Введите пераметры поиска!");
                } else{
                    String text1 = "=";
                    String text2 = "=";

                    if(!Objects.equals(inputID.getText(), "")) text1 = inputID.getText();
                    else text1 = "0";

                    if(!Objects.equals(inputInfo.getText(), "")){
                        text2 = inputInfo.getText();
                    }

                    Basic basic = new Basic(Integer.parseInt(text1), text2);

                    writerObj.writeObject(SEARCH_QUERY);
                    writerObj.writeObject(basic);
                    writerObj.writeObject(type);

                    ArrayList list = (ArrayList) readerObj.readObject();
                    if(list.size() != 0) tableTransact.setItems(FXCollections.observableArrayList(list));
                    else infoArea.setText("Ничего не найдено");

                    clearInput();
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NumberFormatException e){
                infoArea.setText("Данные введены неверно!");
            }
        });
    }

    void setWindowBack(){
        Stage stage1 = (Stage) btnBack.getScene().getWindow();
        stage1.close();
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../adminUserMenus/adminMenu.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));

            AdminMenu controller = loader.getController();
            controller.getConnection(clientSocket, writerObj, readerObj, stage1);

            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void doSubmit (){
        try {
            if(inputInfo.getText() == null) {
                infoArea.setText("Все поля должны быть заполнены!!!");
            } else {
                String request = inputInfo.getText();

                writerObj.writeObject(ADD_CURRENCY);
                writerObj.writeObject(request);


            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e){
            infoArea.setText("Неверный тип данных!");
        }
    }

    void doUpdate(int id){
        try {
            String text1 = "=";

            if(Objects.equals(inputInfo.getText(), "")) throw new Exception();

            if(!Objects.equals(inputInfo.getText(), "")) text1 = inputInfo.getText();

            Basic basic = new Basic(id, text1);

            writerObj.writeObject(UPDATE_QUERY);
            writerObj.writeObject(basic);
            writerObj.writeObject(this.type);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e){
            infoArea.setText("Данные введены неверно!");
        } catch (Exception e) {
            infoArea.setText("Хотя бы одно поле должно быть заполено!");
        }
    }

    public ObservableList getNotes(){
        ArrayList currencies = new ArrayList<>();
        try {
            writerObj.writeObject(SHOW_QUERY);
            writerObj.writeObject(type);

            currencies = (ArrayList) readerObj.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        ObservableList list = FXCollections.observableArrayList(currencies);
        return list;
    }

    public void getConnected8(Socket clientSocket, ObjectOutputStream writerObj, ObjectInputStream readerObj, Stage stage) throws IOException, ClassNotFoundException {
        this.clientSocket = clientSocket;
        this.writerObj = writerObj;
        this.readerObj = readerObj;
        this.stage = stage;
    }

    public void clearInput(){
        inputID.clear();
        inputInfo.clear();
    }
}
