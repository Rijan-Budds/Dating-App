package com.datingapp;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:h2:./datingdb";
    private static final String USER = "sa";
    private static final String PASS = "";

    public DatabaseManager() {
        initializeDatabase();
    }

    private void initializeDatabase() {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS)) {
            String sql = "CREATE TABLE IF NOT EXISTS profiles (" +
                        "id INT AUTO_INCREMENT PRIMARY KEY," +
                        "name VARCHAR(255)," +
                        "age INT," +
                        "bio TEXT," +
                        "image_path VARCHAR(255))";
            try (Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addProfile(Profile profile) {
        String sql = "INSERT INTO profiles (name, age, bio, image_path) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, profile.getName());
            pstmt.setInt(2, profile.getAge());
            pstmt.setString(3, profile.getBio());
            pstmt.setString(4, profile.getImagePath());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Profile> getAllProfiles() {
        List<Profile> profiles = new ArrayList<>();
        String sql = "SELECT * FROM profiles";
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Profile profile = new Profile(
                    rs.getString("name"),
                    rs.getInt("age"),
                    rs.getString("bio"),
                    rs.getString("image_path")
                );
                profiles.add(profile);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return profiles;
    }
}