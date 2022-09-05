package lk.ijse.dep9.hospital.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;
import lk.ijse.dep9.hospital.misc.CryptoUtil;
import lk.ijse.dep9.hospital.security.SecurityContextHolder;
import lk.ijse.dep9.hospital.security.User;
import lk.ijse.dep9.hospital.security.UserRole;
import java.io.IOException;
import java.sql.*;

public class LoginFormController {
    public JFXTextField txtUsername;
    public JFXPasswordField txtPassword;
    public JFXButton btnLogin;
    public void initialize() {
        btnLogin.setDefaultButton(true);
    }

    public void btnLogin_OnAction(ActionEvent actionEvent) throws ClassNotFoundException, IOException {
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
        Class.forName("com.mysql.cj.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/hospital_system", "root", "pubuduSQL@123")) {
            //System.out.println(connection);
            /**
             * REGULAR STATEMENT
             *             String sql = "SELECT role FROM User WHERE User.username='%s' AND User.password='%s'";
             *             sql = String.format(sql,username,password);
             *             Statement statement = connection.createStatement();
             *             ResultSet resultSet = statement.executeQuery(sql);
             */

            /* PREPARED STATEMENT */
            String sql = "SELECT role, password FROM User WHERE username=?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1,username);                    // parameter index starts with 1.
            //statement.setString(2,password);
            ResultSet resultSet = statement.executeQuery();

            //System.out.println(resultSet);
            if (resultSet.next()) {

                String cipherText = resultSet.getString("password");
                if (!CryptoUtil.getSha256Hex(password).equals(cipherText)) {
                    new Alert(Alert.AlertType.ERROR,"Invalid login credentials").show();
                    txtUsername.requestFocus();
                    txtUsername.selectAll();
                    return;
                }
                String role = resultSet.getString("role");
                //System.out.println(role);
                SecurityContextHolder.setPrinciple(new User(username, UserRole.valueOf(role)));
                Scene scene = null;
                switch (role) {
                    case "Admin":
                        scene = new Scene(FXMLLoader.load(this.getClass().getResource("/view/AdminDashboardForm.fxml")));
                        break;
                    case "Doctor":
                        scene = new Scene(FXMLLoader.load(this.getClass().getResource("/view/DoctorDashboardForm.fxml")));
                        break;
                    default:
                        scene = new Scene(FXMLLoader.load(this.getClass().getResource("/view/ReceptionistDashboardForm.fxml")));
                }
                Stage stage = new Stage();
                stage.setTitle("Hospital Management System");
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
                btnLogin.getScene().getWindow().hide();
            }
            else {
                new Alert(Alert.AlertType.ERROR,"Invalid login credentials").show();
                txtUsername.requestFocus();
                txtUsername.selectAll();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            new Alert(Alert.AlertType.ERROR,"Failed to connect with the database, try again").show();
        }

    }
}
