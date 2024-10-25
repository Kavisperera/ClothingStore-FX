package org.example.controller.clothingstoreFX;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javafx.scene.control.Label;
import javafx.stage.StageStyle;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;

public class employeeDashboardController  implements Initializable {

    @FXML
    private Button close;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button minimize;

    @FXML
    private Button purchase_addBtn;

    @FXML
    private TextField purchase_brand;

    @FXML
    private TableColumn<?, ?> purchase_col_brand;

    @FXML
    private TableColumn<?, ?> purchase_col_price;

    @FXML
    private TableColumn<?, ?> purchase_col_productName;

    @FXML
    private TableColumn<?, ?> purchase_col_quantity;

    @FXML
    private Label purchase_employeeId;

    @FXML
    private Button purchase_payBtn;

    @FXML
    private ComboBox<?> purchase_productName;

    @FXML
    private Spinner<?> purchase_quantity;

    @FXML
    private Button purchase_receiptBtn;

    @FXML
    private TableView<?> purchase_tableView;

    @FXML
    private Label purchase_total;

    private double x = 0;
    private double y = 0;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    private Statement statement;

    public void displayEmployeeId(){
        purchase_employeeId.setText(getData.employeeId);
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

    public void close() {
        System.exit(0);
    }

    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }
    
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayEmployeeId();
    }
}
