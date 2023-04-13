package org.javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.javafx.model.Tarea;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class AdminController implements Initializable {
    @FXML
    private TableView<Tarea> tabla;
    @FXML
    private Button botonSalir, goHome, addTarea, modoOscuro;
    @FXML
    private TableColumn colId, colName, colDesc, colEst;
    @FXML
    private TextField textID, textNombre, textDescripcion, textEstado;
    @FXML
    private Label nTareas, sessionDate, sessionUserName, userNameField;
    @FXML
    private Pane navbar;

    private ObservableList<Tarea> tareas;
    private double xOffset = 0, yOffset = 0;
    private static String username="admin";

    public void close(ActionEvent actionEvent) {
        Stage stage = (Stage) botonSalir.getScene().getWindow();
        stage.close();
    }

    public void goHome(ActionEvent actionEvent) throws IOException {
        App.setRoot("home");
    }

    public void modoOscuro(ActionEvent actionEvent){
        Scene scene = ((Node) actionEvent.getSource()).getScene();
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
    }

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

    @FXML
    public void newTarea(){
        Tarea newTarea = new Tarea(Integer.parseInt(textID.getText()),textNombre.getText() , textDescripcion.getText(), textEstado.getText());
        this.tareas.add(newTarea);
        this.tabla.setItems(tareas);
        reloadNTarea();
    }

    @FXML
    public void reloadNTarea(){
        nTareas.setText(Integer.toString(tareas.size()));
    }

    @FXML
    public void reloadInfoSession(){
        userNameField.setText(username);
        sessionUserName.setText(username);
        sessionDate.setText(LocalDate.now().toString());
    }
}
