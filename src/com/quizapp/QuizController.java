package com.quizapp;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.Button;
import javafx.scene.text.Font;
import java.sql.*;

public class QuizController {
    @FXML
    private Label questionLabel;
    @FXML
    private RadioButton option1, option2, option3, option4;
    @FXML
    private ToggleGroup optionGroup;
    @FXML
    private Button nextButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button mainMenuButton;
    @FXML
    private Button logoutButton;

    private String[] questions = {
            "বাংলাদেশের রাজধানী কোনটি?",
            "বাংলাদেশের জাতীয় ফল কী?",
            "বাংলাদেশের কত সালে স্বাধীনতা লাভ করে?",
            "বাংলাদেশের জাতীয় কবি কে?",
            "বাংলাদেশের বৃহত্তম নদী কোনটি?",
            "বাংলা নতুন বছর কোন দিন পালন করা হয়?",
            "বাংলাদেশের জাতীয় পাখি কোনটি?",
            "বাংলাদেশের প্রথম রাষ্ট্রপতি কে?",
            "বাংলাদেশের জাতীয় ফুল কী?",
            "বাংলাদেশের জাতীয় মাছ কী?"
    };
    private String[][] options = {
            {"ঢাকা", "চট্টগ্রাম", "খুলনা", "সিলেট"},
            {"আম", "কাঁঠাল", "জাম", "আনারস"},
            {"১৯৭০", "১৯৭১", "১৯৭২", "১৯৬৯"},
            {"কাজী নজরুল ইসলাম", "রবীন্দ্রনাথ ঠাকুর", "জসীমউদ্দীন", "সুকান্ত ভট্টাচার্য"},
            {"পদ্মা", "মেঘনা", "যমুনা", "তিস্তা"},
            {"১৪ এপ্রিল", "১৫ এপ্রিল", "১৩ এপ্রিল", "১৬ এপ্রিল"},
            {"দোয়েল", "ময়না", "শালিক", "কাক"},
            {"শেখ মুজিবুর রহমান", "আবদুল হামিদ খান ভাসানী", "জিয়াউর রহমান", "সৈয়দ নজরুল ইসলাম"},
            {"শাপলা", "রজনীগন্ধা", "গোলাপ", "সুন্দরী"},
            {"পাঙ্গাস", "ইলিশ", "রুই", "কাতল"}
    };
    private String[] answers = {
            "ঢাকা",
            "কাঁঠাল",
            "১৯৭১",
            "কাজী নজরুল ইসলাম",
            "পদ্মা",
            "১৪ এপ্রিল",
            "দোয়েল",
            "শেখ মুজিবুর রহমান",
            "শাপলা",
            "ইলিশ"
    };
    private int currentIndex = 0;
    private int score = 0;

    private static final String DB_URL = "jdbc:mysql://localhost:3306/quizdb";
    private static final String DB_USER = "root";
    private static final String DB_PASS = "sajeeb32";

    public void initialize() {
        if (optionGroup == null) {
            System.out.println("optionGroup is null in initialize, creating new ToggleGroup");
            optionGroup = new ToggleGroup();
            option1.setToggleGroup(optionGroup);
            option2.setToggleGroup(optionGroup);
            option3.setToggleGroup(optionGroup);
            option4.setToggleGroup(optionGroup);
        }
        loadQuestion(0);
    }

    @FXML
    private void handleNextAction() {
        if (optionGroup == null) {
            System.out.println("optionGroup is null in handleNextAction");
            return;
        }
        RadioButton selected = (RadioButton) optionGroup.getSelectedToggle();
        if (selected != null && selected.getText().equals(answers[currentIndex])) {
            score++;
        }
        currentIndex++;
        if (currentIndex < questions.length) {
            loadQuestion(currentIndex);
        } else {
            saveScore();
            questionLabel.setText("কুইজ শেষ ! আপনি পেয়েছেন : " + score + "/" + questions.length);
            questionLabel.setFont(new Font("Elephant", 14.0));
            option1.setVisible(false);
            option2.setVisible(false);
            option3.setVisible(false);
            option4.setVisible(false);
            nextButton.setVisible(false);
            exitButton.setVisible(true);
            mainMenuButton.setVisible(true);
            logoutButton.setVisible(true);
        }
    }

    private void saveScore() {
        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO scores (email, score) VALUES (?, ?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, Main.getCurrentEmail());
            ps.setInt(2, score);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadQuestion(int index) {
        questionLabel.setText(questions[index]);
        option1.setText(options[index][0]);
        option2.setText(options[index][1]);
        option3.setText(options[index][2]);
        option4.setText(options[index][3]);
        if (optionGroup != null) {
            optionGroup.selectToggle(null);
        } else {
            System.out.println("optionGroup is null in loadQuestion");
        }
    }

    @FXML
    private void handleExitAction() {
        System.exit(0);
    }

    @FXML
    private void handleMainMenuAction() {
        Main.showMainScene();
    }

    @FXML
    private void handleLogoutAction() {
        Main.showLoginScene();
    }
}