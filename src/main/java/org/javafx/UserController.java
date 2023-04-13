package org.javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.javafx.model.Tarea;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class UserController implements Initializable {

    @FXML
    private TableView<Tarea> tabla;
    @FXML
    private TableColumn colId, colName, colDesc, colEst;
    @FXML
    private Label nTareas, sessionDate, sessionUserName;
    @FXML
    private Button addTareaButton, exitButton, backToHomeButton;
    @FXML
    private Pane navbar;
    @FXML
    private TextField userNameField;

    private ObservableList<Tarea> tareas;
    private double xOffset = 0, yOffset = 0;
    private static String username = "usuario";


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navbar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        navbar.setOnMouseDragged(event -> {
            Stage stage = (Stage) navbar.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        reloadInfoSession();

        tareas = FXCollections.observableArrayList();
        this.colId.setCellValueFactory(new PropertyValueFactory("id"));
        this.colName.setCellValueFactory(new PropertyValueFactory("nombre"));
        this.colDesc.setCellValueFactory(new PropertyValueFactory("descripcion"));
        this.colEst.setCellValueFactory(new PropertyValueFactory("estado"));

        reloadNTarea();
    }

    public void newTarea(){
        Tarea newTarea = new Tarea( 1, "Tarea", "aasdas", "Incompleto");
        this.tareas.add(newTarea);
        this.tabla.setItems(tareas);
        reloadNTarea();
    }

    @FXML
    private void backToHome() throws IOException {
        App.setRoot("home");
    }

    @FXML
    private void closeWindow(ActionEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void reloadNTarea(){
        nTareas.setText(Integer.toString(tareas.size()));
    }

    @FXML
    public void reloadUserName(){
        username=userNameField.getText();
        reloadInfoSession();
    }

    @FXML
    public void reloadInfoSession(){
        userNameField.setText(username);
        sessionUserName.setText(username);
        sessionDate.setText(LocalDate.now().toString());
    }
}