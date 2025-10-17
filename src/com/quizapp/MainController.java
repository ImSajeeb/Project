package com.quizapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import java.sql.*;

public class MainController {
    @FXML
    private Label welcomeLabel;
    @FXML
    private Button playQuizButton;
    @FXML
    private Button scoreButton;
    @FXML
    private Button exitButton;
//    @FXML
//    private Button mainMenuButton;

    @FXML
    private Button logoutButton;



    private static final String DB_URL = "jdbc:mysql://localhost:3306/quizdb";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "sajeeb32";

    public void initialize() {
        welcomeLabel.setText("আপনি সফলভাবে লগইন করেছেন..!" );
        playQuizButton.setOnAction(e -> Main.showQuizScene());
        scoreButton.setOnAction(e -> showScore());
        exitButton.setOnAction(e -> handleExitAction());
        //mainMenuButton.setOnAction(e -> handleMainMenuAction());
        logoutButton.setOnAction(e->handleLogoutAction());
    }

    private void showScore() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT score FROM scores WHERE email = ? ORDER BY id DESC LIMIT 1";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, Main.getCurrentEmail());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                welcomeLabel.setText("আপনার সর্বশেষ অর্জিত পয়েন্ট " + rs.getInt("score"));
            } else {
                welcomeLabel.setText("কোনো পয়েন্ট নেই !");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            welcomeLabel.setText("পয়েন্ট লোড নিতে সমস্যা হচ্ছে ");
        }
    }

    @FXML
    private void handleExitAction() {
        System.exit(0);
    }

//    @FXML
//    private void handleMainMenuAction() {
//      Main.showMainScene();
//    }
    @FXML
    private void handleLogoutAction(){
        Main.showLoginScene();
    }
}