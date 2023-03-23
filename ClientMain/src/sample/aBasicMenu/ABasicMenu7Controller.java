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
import sample.classes.descent.Stock;
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

public class ABasicMenu7Controller {
    private Socket clientSocket = null;
    private ObjectOutputStream writerObj = null;
    private ObjectInputStream readerObj = null;
    private Stage stage = null;
    private String type = null;

    private static final String SHOW_QUERY = "show";
    private static final String DELETE_QUERY = "delete";
    private static final String UPDATE_QUERY = "update";
    private static final String SEARCH_QUERY = "search";

    private static final String ADD_STOCK = "add stock";
    private static final String SET_CHOICES = "set choices";

    @FXML
    private Button btnBack;

    @FXML
    private TableView<Object> tableTransact;

    @FXML
    private TableColumn<Stock, Integer> IDColumn;

    @FXML
    private TableColumn<Stock, Integer> userIDColumn;

    @FXML
    private TableColumn<Stock, Date> dateColumn;

    @FXML
    private TableColumn<Stock, Integer> sumColumn;

    @FXML
    private TableColumn<Stock, Integer> firstColumn;

    @FXML
    private TableColumn<Stock, String> secondColumn;

    @FXML
    private TableColumn<Stock, Integer> thirdColumn;

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
    private TextField inputUserID;

    @FXML
    private DatePicker pickDate;

    @FXML
    private TextField inputSum;

    @FXML
    private TextField input1;

    @FXML
    private TextField input2;

    @FXML
    private ChoiceBox choiceBox;

    @FXML
    private Text choiceTxt;

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
            Stock selectedItem = (Stock) tableTransact.getSelectionModel().getSelectedItem();

            if(selectedItem != null) {
                doUpdate(selectedItem.getId());
            } else infoArea.setText("Выберите запись!");
        });

        btnAdd.setOnAction(actionEvent -> {
            doSubmit();
        });

        btnDelete.setOnAction(actionEvent -> {
            int id = 0;
            Stock selectedItem = (Stock) tableTransact.getSelectionModel().getSelectedItem();

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
                if(Objects.equals(inputID.getText(), "") && Objects.equals(inputUserID.getText(), "") && Objects.equals(pickDate.getValue(), "") &&
                        Objects.equals(inputSum.getText(), "") && input1.getText() == null &&
                        Objects.equals(choiceBox.getSelectionModel().getSelectedItem(), "") && Objects.equals(input2.getText(), "")) {
                    infoArea.setText("Введите пераметры поиска!");
                } else{
                    String text1 = "=";
                    String text2 = "=";
                    String text3 = "=";
                    String text4 = "=";
                    String text5 = "=";
                    String text6 = "=";
                    String text7 = "=";

                    if(!Objects.equals(inputID.getText(), "")) text1 = inputID.getText();
                    else text1 = "0";
                    if(pickDate.getValue() != null){
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        text2 = pickDate.getValue().format(formatter);
                    }
                    if(!Objects.equals(inputUserID.getText(), "")){
                        text3 = inputUserID.getText();
                        Integer.parseInt(inputUserID.getText());
                    }
                    if(!Objects.equals(inputSum.getText(), "")){
                        text4 = inputSum.getText();
                        Integer.parseInt(inputSum.getText());
                    }
                    if(!Objects.equals(input1.getText(), "")){
                        text5 = input1.getText();
                        Integer.parseInt(input1.getText());
                    }
                    if(!Objects.equals(choiceBox.getSelectionModel().getSelectedItem(), "")){
                        text6 = (String) choiceBox.getSelectionModel().getSelectedItem();
                    }
                    if(!Objects.equals(input2.getText(), "")){
                        text5 = input2.getText();
                        Integer.parseInt(input2.getText());
                    }

                    Basic basic = new Basic(Integer.parseInt(text1), text2, text3, text4, text5, text6, text7);

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
            if(pickDate.getValue() == null || inputSum.getText() == null || input1.getText() == null
                    || choiceBox.getSelectionModel().getSelectedItem() == null || input2.getText() == null) {
                infoArea.setText("Все поля должны быть заполнены!!!");
            } else {
                Integer.parseInt(inputSum.getText());
                Integer.parseInt(input2.getText());
                Integer.parseInt(input1.getText());

                String request = pickDate.getValue() + "_" + inputSum.getText() + "_" + input1.getText()
                        + "_" + choiceBox.getSelectionModel().getSelectedItem() + "_" + input2.getText();

                writerObj.writeObject(ADD_STOCK);
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
            String text2 = "=";
            String text3 = "=";
            String text4 = "=";
            String text5 = "=";
            String text6 = "=";

            if(pickDate.getValue() == null && Objects.equals(inputSum.getText(), "") && Objects.equals(input1.getText(), "")
                    && Objects.equals(choiceBox.getSelectionModel().getSelectedItem(), "") && Objects.equals(input2.getText(), "")) throw new Exception();

            if(pickDate.getValue() != null){
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                text2 = pickDate.getValue().format(formatter);
            }
            if(!Objects.equals(inputSum.getText(), "")) text3 = inputSum.getText();
            if(!Objects.equals(input1.getText(), "")) text4 = input1.getText();
            if(!Objects.equals(choiceBox.getSelectionModel().getSelectedItem(), "")) text5 = (String) choiceBox.getSelectionModel().getSelectedItem();
            if(!Objects.equals(input2.getText(), "")) text6 = input2.getText();

            Integer.parseInt(inputSum.getText());
            Integer.parseInt(input2.getText());
            Integer.parseInt(input1.getText());

            Basic basic = new Basic(id, text1, text2, text3, text4, text5, text6);

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
        ArrayList transacts = new ArrayList<>();
        try {
            writerObj.writeObject(SHOW_QUERY);
            writerObj.writeObject(type);

            transacts = (ArrayList) readerObj.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        ObservableList list = FXCollections.observableArrayList(transacts);
        return list;
    }

    public void getConnected7(Socket clientSocket, ObjectOutputStream writerObj, ObjectInputStream readerObj, Stage stage) throws IOException, ClassNotFoundException {
        this.clientSocket = clientSocket;
        this.writerObj = writerObj;
        this.readerObj = readerObj;
        this.stage = stage;

        ArrayList<String> choices;

        writerObj.writeObject(SET_CHOICES);
        writerObj.writeObject(type);
        choices = (ArrayList) readerObj.readObject();




        choiceBox.setItems(FXCollections.observableArrayList(choices));

        pickDate.setDayCellFactory(picker -> new DateCell() {
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                LocalDate today = LocalDate.now();

                setDisable(empty || date.compareTo(today) < 0 );
            }
        });
    }

    public void clearInput(){
        inputID.clear();
        pickDate.setValue(null);
        inputSum.clear();
        input1.clear();
        choiceBox.setValue(null);
        input2.clear();
    }
}
