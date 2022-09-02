package lk.ijse.dep9.hospital.controller;

import lk.ijse.dep9.hospital.security.SecurityContextHolder;

public class DoctorDashboardFormController {
    public void initialize() {
        System.out.println(SecurityContextHolder.getPrinciple());
    }
}
