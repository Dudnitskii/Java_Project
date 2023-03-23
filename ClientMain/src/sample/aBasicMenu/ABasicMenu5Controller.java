package sample.aBasicMenu;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import sample.adminUserMenus.AdminMenu;
import sample.classes.solo.User;
import sample.classes.solo.Basic;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class ABasicMenu5Controller {
    private Socket clientSocket = null;
    private ObjectOutputStream writerObj = null;
    private ObjectInputStream readerObj = null;
    private Stage stage = null;
    private String table = "users";

    private static final String SHOW_QUERY = "show";
    private static final String DELETE_QUERY = "delete";
    private static final String UPDATE_QUERY = "update";
    private static final String SEARCH_QUERY = "search";

    private static final String ADD_USER = "add user";

    @FXML
    private Button btnBack;

    @FXML
    private TableView<User> tableUsers;

    @FXML
    private TableColumn<User, Integer> IDColumn;

    @FXML
    private TableColumn<User, String> loginColumn;

    @FXML
    private TableColumn<User, String> passwordColumn;

    @FXML
    private TableColumn<User, Integer> roleColumn;

    @FXML
    private TableColumn<User, String> nameColumn;

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
    private TextField inputLogin;

    @FXML
    private TextField inputPassword;

    @FXML
    private TextField inputRole;

    @FXML
    private TextField inputName;

    @FXML
    void initialize(){
        btnBack.setOnAction(actionEvent -> {
            setWindowBack();
        });

        btnRefresh.setOnAction(actionEvent -> {
            tableUsers.setItems(getNotes());
            infoArea.setText("Обновлено!");
        });

        btnChange.setOnAction(actionEvent -> {
            User selectedItem = (User) tableUsers.getSelectionModel().getSelectedItem();

            if(selectedItem != null) {
                doUpdate(selectedItem.getId());
            } else infoArea.setText("Выберите запись!");
        });

        btnAdd.setOnAction(actionEvent -> {
            doSubmit();
        });

        btnDelete.setOnAction(actionEvent -> {
            int id = 0;
            User selectedItem = (User) tableUsers.getSelectionModel().getSelectedItem();

            if(selectedItem != null) id = selectedItem.getId();


            if(id != 0){
                try {
                    writerObj.writeObject(DELETE_QUERY);
                    writerObj.writeObject(id + ";" + table);

                    tableUsers.setItems(getNotes());
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
                if(Objects.equals(inputID.getText(), "") && Objects.equals(inputLogin.getText(), "") &&
                        Objects.equals(inputPassword.getText(), "") && Objects.equals(inputRole.getText(), "") &&
                        Objects.equals(inputName.getText(), "")) {
                    infoArea.setText("Введите пераметры поиска!");
                } else{
                    String text1 = "=";
                    String text2 = "=";
                    String text3 = "=";
                    String text4 = "=";
                    String text5 = "=";

                    if(!Objects.equals(inputID.getText(), "")) text1 = inputID.getText();
                    else text1 = "0";
                    if(!Objects.equals(inputLogin.getText(), "")){
                        text2 = inputLogin.getText();
                    }
                    if(!Objects.equals(inputPassword.getText(), "")){
                        text3 = inputPassword.getText();
                    }
                    if(!Objects.equals(inputRole.getText(), "")){
                        text4 = inputRole.getText();
                        Integer.parseInt(inputRole.getText());
                    }
                    if(!Objects.equals(inputName.getText(), "")){
                        text5 = inputName.getText();
                    }

                    Basic basic = new Basic(Integer.parseInt(text1), text2, text3, text4, text5);

                    writerObj.writeObject(SEARCH_QUERY);
                    writerObj.writeObject(basic);
                    writerObj.writeObject(table);

                    ArrayList list = (ArrayList) readerObj.readObject();
                    if(list.size() != 0) tableUsers.setItems(FXCollections.observableArrayList(list));
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
            if(inputLogin.getText() == null || inputPassword.getText() == null || inputRole.getText() == null ||
                    inputName.getText() == null) {
                infoArea.setText("Все поля должны быть заполнены!!!");
            } else {
                Integer.parseInt(inputRole.getText());

                String request = inputLogin.getText() + "_" + inputPassword.getText() + "_" + inputRole.getText()
                        + "_" + inputName.getText();

                writerObj.writeObject(ADD_USER);
                writerObj.writeObject(request);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e){
            infoArea.setText("Данные введены неверно!");
        }
    }

    void doUpdate(int id){
        try {
            String text1 = "=";
            String text2 = "=";
            String text3 = "=";
            String text4 = "=";

            if(Objects.equals(inputLogin.getText(), "") && Objects.equals(inputPassword.getText(), "") &&
                    Objects.equals(inputRole.getText(), "") && Objects.equals(inputName.getText(), "")) throw new Exception();

            if(!Objects.equals(inputLogin.getText(), "")) text1 = inputLogin.getText();
            if(!Objects.equals(inputPassword.getText(), "")) text2 = inputPassword.getText();
            if(!Objects.equals(inputRole.getText(), "")) text3 = inputRole.getText();
            if(!Objects.equals(inputName.getText(), "")) text4 = inputName.getText();

            Integer.parseInt(inputRole.getText());

            Basic basic = new Basic(id, text1, text2, text3, text4);

            writerObj.writeObject(UPDATE_QUERY);
            writerObj.writeObject(basic);
            writerObj.writeObject(this.table);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException e){
            infoArea.setText("Данные введены неверно!");
        } catch (Exception e) {
            infoArea.setText("Хотя бы одно поле должно быть заполено!");
        }
    }

    public ObservableList getNotes(){
        ArrayList users = new ArrayList<>();
        try {
            writerObj.writeObject(SHOW_QUERY);
            writerObj.writeObject(table);

            users = (ArrayList) readerObj.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        ObservableList list = FXCollections.observableArrayList(users);
        return list;
    }

    public void getConnected5(Socket clientSocket, ObjectOutputStream writerObj, ObjectInputStream readerObj, Stage stage) throws IOException, ClassNotFoundException {
        this.clientSocket = clientSocket;
        this.writerObj = writerObj;
        this.readerObj = readerObj;
        this.stage = stage;


        IDColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));
        passwordColumn.setCellValueFactory(new PropertyValueFactory<>("password"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<>("role"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        tableUsers.setItems(getNotes());


    }

    public void clearInput(){
        inputID.clear();
        inputLogin.clear();
        inputPassword.clear();
        inputRole.clear();
        inputName.clear();
    }
}
