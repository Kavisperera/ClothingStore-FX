package org.example.controller.clothingstoreFX;

import db.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import javafx.scene.control.cell.PropertyValueFactory;

public class adminDashboardController implements Initializable {

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button addProducts_addBtn;

    @FXML
    private TextField addProducts_brand;

    @FXML
    private Button addProducts_btn;

    @FXML
    private Button addProducts_clearBtn;

    @FXML
    private TableColumn<productData, String> addProducts_col_brand;

    @FXML
    private TableColumn<productData, BigDecimal> addProducts_col_price;

    @FXML
    private TableColumn<productData, String> addProducts_col_productID;

    @FXML
    private TableColumn<productData, String> addProducts_col_productName;

    @FXML
    private TableColumn<productData, String> addProducts_col_status;

    @FXML
    private Button addProducts_deleteBtn;

    @FXML
    private AnchorPane addProducts_form;

    @FXML
    private TextField addProducts_price;

    @FXML
    private TextField addProducts_productID;

    @FXML
    private TextField addProducts_productName;

    @FXML
    private TextField addProducts_search;

    @FXML
    private ComboBox<String> addProducts_status;

    @FXML
    private TableView<productData> addProducts_tableView;

    @FXML
    private Button addProducts_updateBtn;

    @FXML
    private AnchorPane dashboard_IncomeToday;


    @FXML
    private Label dashboard_activeEmployees;

    @FXML
    private Button dashboard_btn;

    @FXML
    private AreaChart<?, ?> dashboard_chart;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private Button available_btn;

    @FXML
    private Label dashboard_soldProducts;

    @FXML
    private Label dashboard_totalIncome;

    @FXML
    private Label available_basic_price;

    @FXML
    private Spinner<?> available_basic_quantity;

    @FXML
    private Button available_buyBtn;

    @FXML
    private Button available_clearBtn;

    @FXML
    private TableColumn<?, ?> available_col_brand;

    @FXML
    private TableColumn<?, ?> available_col_price;

    @FXML
    private TableColumn<?, ?> available_col_productID;

    @FXML
    private TableColumn<?, ?> available_col_productName;

    @FXML
    private TableColumn<?, ?> available_col_status;

    @FXML
    private AnchorPane available_form;

    @FXML
    private Label available_premium_price;

    @FXML
    private Spinner<?> available_premium_quantity;

    @FXML
    private Button available_receiptBtn;

    @FXML
    private TableView<?> available_tableView;

    @FXML
    private Label available_total;

    @FXML
    private Button employees_btn;

    @FXML
    private Button employees_clearBtn;

    @FXML
    private TableColumn<employeeData,String> employees_col_date;

    @FXML
    private TableColumn<employeeData,String> employees_col_employeeID;

    @FXML
    private TableColumn<employeeData,String> employees_col_firstName;

    @FXML
    private TableColumn<employeeData,String> employees_col_gender;

    @FXML
    private TableColumn<employeeData,String> employees_col_lastName;

    @FXML
    private TableColumn<employeeData,String> employees_col_password;

    @FXML
    private Button employees_deleteBtn;

    @FXML
    private TextField employees_employeeID;

    @FXML
    private TextField employees_firstName;

    @FXML
    private AnchorPane employees_form;

    @FXML
    private ComboBox<?> employees_gender;

    @FXML
    private TextField employees_lastName;

    @FXML
    private TextField employees_password;

    @FXML
    private Button employees_saveBtn;

    @FXML
    private TableView<employeeData> employees_tableView;

    @FXML
    private Button employees_updateBtn;

    @FXML
    private Button logout;

    @FXML
    private Label username;

    private double x = 0;
    private double y = 0;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    public void setAddProductsAdd() {
        String insertProduct = "INSERT INTO product (product_id, brand, product_name, status, price) "
                + "VALUES (?, ?, ?, ?, ?)";

        connect = DBConnection.getInstance().getConnection();
        try {
            Alert alert;

            if (addProducts_productID.getText().isEmpty()
                    || addProducts_brand.getText().isEmpty()
                    || addProducts_productName.getText().isEmpty()
                    || addProducts_status.getSelectionModel().getSelectedItem() == null
                    || addProducts_price.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                String check = "SELECT product_id FROM product WHERE product_id = '" + addProducts_productID.getText() + "'";
                statement = connect.createStatement();
                result = statement.executeQuery(check);

                if (result.next()) {
                    alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Product ID: " + addProducts_productID.getText() + " already exists!");
                    alert.showAndWait();
                } else {
                    prepare = connect.prepareStatement(insertProduct);
                    prepare.setString(1, addProducts_productID.getText());
                    prepare.setString(2, addProducts_brand.getText());
                    prepare.setString(3, addProducts_productName.getText());
                    prepare.setString(4, addProducts_status.getSelectionModel().getSelectedItem());
                    prepare.setBigDecimal(5, new BigDecimal(addProducts_price.getText()));

                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Product successfully added!");
                    alert.showAndWait();

                    addProductShowData();
                    setAddProductsStatusList();
                    AddProductsClear();
                }
            }
        } catch (Exception e) {e.printStackTrace();
        }
    }

    public void setAddProductsUpdate(){
        String updateProduct = "UPDATE product SET brand = '"
                + addProducts_brand.getText()+"', product_name = '"
                +addProducts_productName.getText()+"',status = '"
                +addProducts_status.getSelectionModel().getSelectedItem()+"',price ='"
                +addProducts_price.getText()+"' WHERE product_id ='"
                +addProducts_productID.getText()+"'";

        connect = DBConnection.getInstance().getConnection();

        try {
            Alert alert;

            if (addProducts_productID.getText().isEmpty()
                    || addProducts_brand.getText().isEmpty()
                    || addProducts_productName.getText().isEmpty()
                    || addProducts_status.getSelectionModel().getSelectedItem() == null
                    || addProducts_price.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to UPDATE product ID:"+ addProducts_productID.getText() + "?");
                alert.showAndWait();

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)){
                    statement = connect.createStatement();
                    statement.executeUpdate(updateProduct);

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Updated!");
                    alert.showAndWait();

                    addProductShowData();
                    setAddProductsStatusList();
                    AddProductsClear();

                }else {
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void setAddProductsDelete(){
        String deleteproduct = "DELETE FROM product WHERE product_id = '"
                +addProducts_productID.getText()+"'";

        connect = DBConnection.getInstance().getConnection();

        try {
            Alert alert;

            if (addProducts_productID.getText().isEmpty()
                    || addProducts_brand.getText().isEmpty()
                    || addProducts_productName.getText().isEmpty()
                    || addProducts_status.getSelectionModel().getSelectedItem() == null
                    || addProducts_price.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Please fill all blank fields");
                alert.showAndWait();
            } else {
                alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Message");
                alert.setHeaderText(null);
                alert.setContentText("Are you sure you want to DELETE product ID:"+ addProducts_productID.getText() + "?");
                alert.showAndWait();

                Optional<ButtonType> option = alert.showAndWait();

                if (option.get().equals(ButtonType.OK)){
                    prepare = connect.prepareStatement(deleteproduct);
                    prepare.executeUpdate();

                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Successfully Deleted!");
                    alert.showAndWait();

                    addProductShowData();
                    setAddProductsStatusList();
                    AddProductsClear();

                }else {
                }
            }
        }catch (Exception e){e.printStackTrace();}
    }

    public void AddProductsClear(){
        addProducts_productID.setText("");
        addProducts_brand.setText("");
        addProducts_productName.setText("");
        addProducts_status.getSelectionModel().clearSelection();
        addProducts_price.setText("");
    }
    private final String[] statusList = {"Available","Not Available","Out of Stock"};
    public void setAddProductsStatusList(){
        List<String>listS = new ArrayList<>();

        Collections.addAll(listS, statusList);
        ObservableList statusData = FXCollections.observableArrayList(listS);
        addProducts_status.setItems(statusData);
    }

    public void setAddProductsSearch(){

        FilteredList<productData> filter = new FilteredList<>(addProductList,e->true);
        addProducts_search.textProperty().addListener((Observable,oldValue,newValue) ->{
            filter.setPredicate(predicateproductData -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String searchKey = newValue.toLowerCase();
                if (predicateproductData.getProductId().toLowerCase().contains(searchKey)){
                    return true;
                }else if (predicateproductData.getBrand().toLowerCase().contains(searchKey)){
                    return true;
                }else if (predicateproductData.getStatus().toLowerCase().contains(searchKey)) {
                    return true;
                }else if (predicateproductData.getProductName().toLowerCase().contains(searchKey)) {
                    return true;
                }else return predicateproductData.getPrice().toString().contains(searchKey);
            });
        });
        SortedList<productData> sortList = new SortedList<>(filter);
        sortList.comparatorProperty().bind(addProducts_tableView.comparatorProperty());
        addProducts_tableView.setItems(sortList);
    }

    public ObservableList<productData> addProductsListData() {
        ObservableList<productData> prodList = FXCollections.observableArrayList();

        String sql = "SELECT * FROM product";

        connect = DBConnection.getInstance().getConnection();
        try {
            productData prod;

            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                prod = new productData(result.getString("product_id"),
                        result.getString("brand"),
                        result.getString("product_name"),
                        result.getString("status"),
                        result.getBigDecimal("price"));

                prodList.add(prod);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prodList;
    }
    private final ObservableList<productData> addProductList = addProductsListData();
    public void addProductShowData() {
        ObservableList<productData> addProductList = addProductsListData();
        addProducts_col_productID.setCellValueFactory(new PropertyValueFactory<>("productId"));
        addProducts_col_brand.setCellValueFactory(new PropertyValueFactory<>("brand"));
        addProducts_col_productName.setCellValueFactory(new PropertyValueFactory<>("productName"));
        addProducts_col_status.setCellValueFactory(new PropertyValueFactory<>("status"));
        addProducts_col_price.setCellValueFactory(new PropertyValueFactory<>("price"));

        addProducts_tableView.setItems(addProductList);

    }

    public void setAddProductsSelect() {
        productData prod = addProducts_tableView.getSelectionModel().getSelectedItem();
        if (prod == null) {
            return;
        }
        addProducts_productID.setText(prod.getProductId());
        addProducts_brand.setText(prod.getBrand());
        addProducts_productName.setText(prod.getProductName());
        addProducts_price.setText(String.valueOf(prod.getPrice()));

        addProducts_tableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            setAddProductsSelect();
        });
    }

    public void employeesSave(){
        java.sql.Date sqlDate = new java.sql.Date(System.currentTimeMillis());
        String insertEmployee = "INSERT INTO employee (employeeId, password, firstName, lastName, gender, date) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        
    }


    public ObservableList<employeeData> employeesListData() {
        ObservableList<employeeData> emData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM employee";

        connect = DBConnection.getInstance().getConnection();
        try {
            employeeData employeeD;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                employeeD = new employeeData(
                        result.getString("employeeId"),
                        result.getString("password"),
                        result.getString("firstName"),
                        result.getString("lastName"), // No leading space
                        result.getString("gender"), // No leading space
                        result.getDate("date") // No leading space
                );
                emData.add(employeeD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return emData;
    }

    private final ObservableList<employeeData> employeesList = employeesListData();

    public void employeesShowListData() {
        ObservableList<employeeData> employeesList = employeesListData();

        employees_col_employeeID.setCellValueFactory(new PropertyValueFactory<>("employeeId"));
        employees_col_password.setCellValueFactory(new PropertyValueFactory<>("password"));
        employees_col_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        employees_col_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        employees_col_gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
        employees_col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        employees_tableView.setItems(employeesList);
    }

    public void employeesSelect() {
        employeeData employeeD = employees_tableView.getSelectionModel().getSelectedItem();
        int num = employees_tableView.getSelectionModel().getSelectedIndex();

        if (num < 0) {
            return;
        }

        employees_employeeID.setText(employeeD.getEmployeeId());
        employees_password.setText(employeeD.getPassword());
        employees_firstName.setText(employeeD.getFirstName());
        employees_lastName.setText(employeeD.getLastName());
    }

    public void logout() {
        try {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to logout?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.isPresent() && option.get().equals(ButtonType.OK)) {
                logout.getScene().getWindow().hide();

                Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/org/example/controller/clothingstoreFX/Clothing-FXML.fxml")));

                Stage stage = new Stage();
                Scene scene = new Scene(root);

                root.setOnMousePressed((MouseEvent event) -> {
                    x = event.getSceneX();
                    y = event.getSceneY();
                });

                root.setOnMouseDragged((MouseEvent event) -> {
                    stage.setX(event.getScreenX() - x);
                    stage.setY(event.getScreenY() - y);
                    stage.setOpacity(0.8);
                });

                root.setOnMouseReleased((MouseEvent event) -> stage.setOpacity(1));

                stage.initStyle(StageStyle.TRANSPARENT);
                stage.setScene(scene);
                stage.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayUsername(){
        username.setText(getData.username);
    }

    public void switchForm(ActionEvent event) {
        if (event.getSource() == dashboard_btn) {
            dashboard_form.setVisible(true);
            addProducts_form.setVisible(false);
            available_form.setVisible(false);
            employees_form.setVisible(false);


            dashboard_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #0b4e68, #1f4c3f);");
            addProducts_btn.setStyle("-fx-background-color:transparent");
            available_btn.setStyle("-fx-background-color:transparent");
            employees_btn.setStyle("-fx-background-color:transparent");

        } else if (event.getSource() == addProducts_btn) {
            dashboard_form.setVisible(false);
            addProducts_form.setVisible(true);
            available_form.setVisible(false);
            employees_form.setVisible(false);

            addProducts_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #0b4e68, #1f4c3f);");
            dashboard_btn.setStyle("-fx-background-color:transparent");
            available_btn.setStyle("-fx-background-color:transparent");
            employees_btn.setStyle("-fx-background-color:transparent");

            addProductShowData();
            AddProductsStatusList();
            AddProductsSearch();

        } else if (event.getSource() == available_btn) {
            dashboard_form.setVisible(false);
            addProducts_form.setVisible(false);
            available_form.setVisible(true);
            employees_form.setVisible(false);

            available_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #0b4e68, #1f4c3f);");
            addProducts_btn.setStyle("-fx-background-color:transparent");
            employees_btn.setStyle("-fx-background-color:transparent");
            dashboard_btn.setStyle("-fx-background-color:transparent");

        }else if (event.getSource() == employees_btn) {
            dashboard_form.setVisible(false);
            addProducts_form.setVisible(false);
            employees_form.setVisible(true);
            available_form.setVisible(false);

            employees_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #0b4e68, #1f4c3f);");
            addProducts_btn.setStyle("-fx-background-color:transparent");
            dashboard_btn.setStyle("-fx-background-color:transparent");
            available_btn.setStyle("-fx-background-color:transparent");

            employeesShowListData();
        }

    }

    private void AddProductsSearch() {
    }

    private void AddProductsStatusList() {
    }

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayUsername();
        addProductShowData();
        setAddProductsStatusList();

        employeesShowListData();
    }

}