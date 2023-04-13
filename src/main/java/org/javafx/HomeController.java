package org.javafx;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class HomeController {

    @FXML
    private Button btn_home;

    @FXML
    private Button closeHome;

    @FXML
    private Label labelHome;

    @FXML
    private TextField userField;

    @FXML
    private TextField passField;

    @FXML
    private Label indicacion;

    @FXML
    private void btnHomeValidate() throws IOException {
        switch (userField.getText()){
            case "admin":
                if (passField.getText().equals("admin")){
                    indicacion.setVisible(false);
                    App.setRoot("admin");
                }else{
                    incorrectAccess(1);
                }
                break;
            case "usuario":
                if (passField.getText().equals("usuario")){
                    indicacion.setVisible(false);
                    App.setRoot("user");
                }else{
                    incorrectAccess(1);
                }
                break;
            default:
                incorrectAccess(0);
                break;

        }
    }

    /**
     * Muestra mensaje de error si falla el usuario o la contraseña
     * @param n 0-usuario incorrecto / 1-Contraseña incorrecta
     */
    @FXML
    private void incorrectAccess(int n){
        indicacion.setVisible(true);
        switch (n){
            case 0:
                indicacion.setText("Usuario incorrecto");
                break;
            case 1:
                animacion();
                passField.setText("");
                indicacion.setText("Contraseña incorrecta");
                break;
        }
    }

    /**
     * cierra la ventana
     * @param event
     */
    @FXML
    private void cerrarVentana(ActionEvent event) {

        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    private Timer timer; // Declarar la variable timer como una variable de instancia

    private void animacion() {
        final int[] n = {0};
        final int[] count = {0};
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                count[0]++;
                if (count[0]>5){
                    n[0] = 2;
                }
                switch (n[0]) {
                    case 0:
                        n[0] = 1;
                        passField.setTranslateX(9);
                        break;
                    case 1:
                        n[0] = 0;
                        passField.setTranslateX(-9);
                        break;
                    case 2:
                        passField.setTranslateX(0);
                        detenerAnimacion();
                        break;

                }
            }
        }, 0, 50);
    }

    // Método para detener la animación
    private void detenerAnimacion() {
        timer.cancel(); // Llamar al método cancel() en la instancia del Timer creado
    }

    public static void setTimeout(Runnable runnable, int delay){
        new Thread(() -> {
            try {
                Thread.sleep(delay);
                runnable.run();
            }
            catch (Exception e){
                System.err.println(e);
            }
        }).start();
    }


    //Esto es para mover la aplicación desde la navbar
    @FXML
    private Pane navbar; // Referencia al nodo raíz de la escena

    private double xOffset = 0;
    private double yOffset = 0;

    // Método de inicialización que se llama después de cargar la vista
    public void initialize() {
        // Agregar controlador de eventos onMousePressed al nodo raíz
        navbar.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        // Agregar controlador de eventos onMouseDragged al nodo raíz
        navbar.setOnMouseDragged(event -> {
            Stage stage = (Stage) navbar.getScene().getWindow();
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });
    }

}