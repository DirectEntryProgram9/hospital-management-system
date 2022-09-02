package lk.ijse.dep9.hospital.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;

public class LoginFormController {
    public JFXTextField txtUsername;
    public JFXPasswordField txtPassword;
    public JFXButton btnLogin;
    public void initialize() {
        btnLogin.setDefaultButton(true);
    }

    public void btnLogin_OnAction(ActionEvent actionEvent) {
        String username = txtUsername.getText();
        String password = txtPassword.getText();
        if (username.isBlank()) {
            new Alert(Alert.AlertType.ERROR,"Username cannot be empty").show();
            txtUsername.requestFocus();
            txtUsername.selectAll();
            return;
        }
        else if (password.isBlank()) {
            new Alert(Alert.AlertType.ERROR,"Password cannot be empty").show();
            txtPassword.requestFocus();
            txtPassword.selectAll();
            return;
        }
        if (!username.matches("[0-9A-Za-z]+")) {
            new Alert(Alert.AlertType.ERROR,"Invalid login credentials").show();
            txtUsername.requestFocus();
            txtUsername.selectAll();
            return;
        }
    }
}
