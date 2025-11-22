package com.msp.realestate;

import java.sql.*;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:msp_realestate.db";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }

    public static void initializeDatabase() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS users (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "username TEXT UNIQUE NOT NULL," +
                "password TEXT NOT NULL," +
                "email TEXT NOT NULL," +
                "phone TEXT," +
                "role TEXT NOT NULL," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

            stmt.execute("CREATE TABLE IF NOT EXISTS properties (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "title TEXT NOT NULL," +
                "type TEXT NOT NULL," +
                "location TEXT NOT NULL," +
                "area REAL NOT NULL," +
                "price REAL NOT NULL," +
                "status TEXT NOT NULL," +
                "description TEXT," +
                "owner_name TEXT," +
                "owner_contact TEXT," +
                "created_by INTEGER," +
                "created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP)");

            stmt.execute("CREATE TABLE IF NOT EXISTS documents (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "property_id INTEGER," +
                "doc_type TEXT NOT NULL," +
                "doc_name TEXT NOT NULL," +
                "doc_path TEXT NOT NULL," +
                "uploaded_by INTEGER," +
                "uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP," +
                "FOREIGN KEY(property_id) REFERENCES properties(id))");

            // Insert default admin if not exists
            String checkAdmin = "SELECT COUNT(*) FROM users WHERE username = 'admin'";
            try (ResultSet rs = stmt.executeQuery(checkAdmin)) {
                if (rs.next() && rs.getInt(1) == 0) {
                    stmt.execute("INSERT INTO users (username, password, email, phone, role) " +
                        "VALUES ('admin', 'admin123', 'mspandey0505@gmail.com', '7599099000', 'ADMIN')");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
