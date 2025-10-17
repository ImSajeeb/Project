package com.quizapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;
    private static String currentEmail ;


    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        showLoginScene();
        stage.setTitle("কুইজ এপ্লিকেশন !");
        Image icon = new Image("icon.png");
        primaryStage.getIcons().add(icon);
        stage.setX(500);
        stage.setY(100);
        stage.show();
    }

    public static void showSignupScene() {
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("/signup.fxml"));
            Scene scene = new Scene(root, 500, 400);
            primaryStage.setScene(scene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void showLoginScene(){
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("/login.fxml"));
           // Parent root = FXMLLoader.load(getClass)
            Scene scene = new Scene(root,500,400, Color.BLUE);
            primaryStage.setScene(scene);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void showMainScene(){
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("/main.fxml"));

            Scene scene = new Scene(root,500,400, Color.BLUE);
            primaryStage.setScene(scene);

        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


    public static void showQuizScene(){
        try {
            Parent root = FXMLLoader.load(Main.class.getResource("/quiz.fxml"));
            Scene scene = new Scene(root,500,400);
            primaryStage.setScene(scene);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void setCurrentEmail(String email){
        currentEmail = email;
    }
    public static String getCurrentEmail(){
        return currentEmail;
    }


    public static void main(String[] args) {
        launch(args);
    }
}

