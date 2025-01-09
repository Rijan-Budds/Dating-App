package com.datingapp;

import java.util.List;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private int currentProfileIndex = 0;
    private List<Profile> profiles;
    private ImageView profileImageView;
    private Label nameLabel;
    private Label bioLabel;
    private Label ageLabel;
    private DatabaseManager dbManager;

    @Override
    public void start(Stage primaryStage) {
        dbManager = new DatabaseManager();
        initializeSampleProfiles();
        profiles = dbManager.getAllProfiles();

        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        profileImageView = new ImageView();
        profileImageView.setFitHeight(400);
        profileImageView.setFitWidth(300);
        profileImageView.setPreserveRatio(true);

        nameLabel = new Label();
        nameLabel.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
        
        ageLabel = new Label();
        ageLabel.setStyle("-fx-font-size: 18px;");
        
        bioLabel = new Label();
        bioLabel.setStyle("-fx-font-size: 16px; -fx-wrap-text: true;");
        bioLabel.setMaxWidth(300);

        Button likeButton = new Button("Like ❤️");
        Button dislikeButton = new Button("Dislike ✗");
        
        likeButton.setStyle("-fx-font-size: 18px; -fx-background-color: #28a745; -fx-text-fill: white;");
        dislikeButton.setStyle("-fx-font-size: 18px; -fx-background-color: #dc3545; -fx-text-fill: white;");

        HBox buttonBox = new HBox(20);
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.getChildren().addAll(dislikeButton, likeButton);

        root.getChildren().addAll(profileImageView, nameLabel, ageLabel, bioLabel, buttonBox);

        likeButton.setOnAction(e -> handleLike());
        dislikeButton.setOnAction(e -> handleDislike());

        showCurrentProfile();

        Scene scene = new Scene(root, 400, 600);
        primaryStage.setTitle("Dating App");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void initializeSampleProfiles() {
        dbManager.addProfile(new Profile("Sarah Johnson", 25, "Love hiking and photography", "src/main/resources/images/profile1.jpg"));
        dbManager.addProfile(new Profile("Mike Smith", 28, "Coffee addict, tech enthusiast", "src/main/resources/images/profile2.jpg"));
        dbManager.addProfile(new Profile("Emma Davis", 24, "Foodie and traveler", "src/main/resources/images/profile3.jpg"));
    }

    private void showCurrentProfile() {
        if (currentProfileIndex < profiles.size()) {
            Profile currentProfile = profiles.get(currentProfileIndex);
            nameLabel.setText(currentProfile.getName());
            ageLabel.setText(currentProfile.getAge() + " years old");
            bioLabel.setText(currentProfile.getBio());
            
            try {
                Image image = new Image("file:" + currentProfile.getImagePath());
                profileImageView.setImage(image);
            } catch (Exception e) {
                System.out.println("Error loading image: " + e.getMessage());
            }
        } else {
            nameLabel.setText("No more profiles");
            ageLabel.setText("");
            bioLabel.setText("You've seen all profiles");
            profileImageView.setImage(null);
        }
    }

    private void handleLike() {
        System.out.println("Liked: " + profiles.get(currentProfileIndex).getName());
        currentProfileIndex++;
        showCurrentProfile();
    }

    private void handleDislike() {
        System.out.println("Disliked: " + profiles.get(currentProfileIndex).getName());
        currentProfileIndex++;
        showCurrentProfile();
    }

    public static void main(String[] args) {
        launch(args);
    }
}