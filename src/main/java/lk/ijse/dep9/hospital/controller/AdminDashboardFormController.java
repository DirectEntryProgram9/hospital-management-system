package lk.ijse.dep9.hospital.controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import lk.ijse.dep9.hospital.security.SecurityContextHolder;

public class AdminDashboardFormController {

    public JFXButton btnProfileManagement;
    public JFXButton btnViewRecords;
    public JFXButton btnSettings;

    public void initialize() {
        System.out.println(SecurityContextHolder.getPrinciple());
    }

    public void btnProfileManagement_OnAction(ActionEvent actionEvent) {
    }

    public void btnViewRecords_OnAction(ActionEvent actionEvent) {
    }

    public void btnSettingsOnAction(ActionEvent actionEvent) {
    }
}
