package com.quizapp;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import javafx.scene.control.Button;
import javafx.scene.control.Alert;
import java.sql.*;

public class SignupController {
    @FXML
    private TextField nameField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button signupButton;
    @FXML
    private Button loginButton; // To switch to login

    private static final String DB_URL = "jdbc:mysql://localhost:3306/quizdb";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "sajeeb32";

    @FXML
    private void handleSignupAction() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = passwordField.getText();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            return;
        }


        if (!email.endsWith("@mbstu.ac.bd") && !email.endsWith("@gmail.com")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ইমেইল সঠিক নয় 🙆‍♂️");
            alert.setHeaderText(null);
            alert.setContentText("সাইন আপ করতে সঠিকভাবে ইমেইল দিন !");
            alert.showAndWait();
            return;
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {

            String checkSql = "SELECT COUNT(*) FROM users WHERE email = ?";
            PreparedStatement checkPs = conn.prepareStatement(checkSql);
            checkPs.setString(1, email);
            ResultSet rs = checkPs.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("সাইন আপ ব্যর্থ হয়েছে");
                alert.setHeaderText(null);
                alert.setContentText("উক্ত ইমেইলে পূর্বেই একটি একাউন্ট যুক্ত আছে !");
                alert.showAndWait();
                return;
            }

            String sql = "INSERT INTO users (name, email, password) VALUES (?, ?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, email);
            ps.setString(3, password);
            int rows = ps.executeUpdate();
            if (rows > 0) {
                Main.setCurrentEmail(email);
                Main.showMainScene();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void switchToLogin() {
        Main.showLoginScene();
    }
}