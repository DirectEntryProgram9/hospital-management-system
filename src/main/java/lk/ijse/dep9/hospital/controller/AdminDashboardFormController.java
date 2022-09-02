package lk.ijse.dep9.hospital.controller;

import lk.ijse.dep9.hospital.security.SecurityContextHolder;

public class AdminDashboardFormController {

    public void initialize() {
        System.out.println(SecurityContextHolder.getPrinciple());
    }
}
