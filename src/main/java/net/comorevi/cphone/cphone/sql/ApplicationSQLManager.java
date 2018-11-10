package net.comorevi.cphone.cphone.sql;

import net.comorevi.cphone.cphone.data.RuntimeData;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

class ApplicationSQLManager {

    private static Connection conn = null;
    private static String connname;

    static boolean init() {
        try {
            Class.forName("org.sqlite.JDBC");
            connname = "jdbc:sqlite://" + RuntimeData.currentDirectory + "Applications.db";
            conn = DriverManager.getConnection(connname);

            int count;
            String sql =
                    "CREATE TABLE IF NOT EXISTS application + (" +
                    "name TEXT NOT NULL, " +
                    "main TEXT NOT NULL, " +
                    "version TEXT NOT NULL, " +
                    "jar TEXT NOT NULL";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setQueryTimeout(10);
            count = stmt.executeUpdate();
            stmt.close();

            return (count > 0);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    static boolean addApplication(String name, String main, String version, String jar) {
        try {
            int count;
            String sql =
                    "INSERT INTO " +
                    "application (name, main, version, jar) " +
                    "VALUES (?, ?, ?, ?)";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setQueryTimeout(10);

            stmt.setString(1, name);
            stmt.setString(2, main);
            stmt.setString(3, version);
            stmt.setString(4, jar);

            count = stmt.executeUpdate();
            stmt.close();

            return (count > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    static boolean deleteApplication(String name) {
        try {
            int count;
            String sql = "DELETE FROM application WHERE name = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setQueryTimeout(10);
            stmt.setString(1, name);

            count = stmt.executeUpdate();
            stmt.close();

            return (count > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    static boolean updateApplication(String name, String main, String version, String jar) {
        try {
            int count;
            String sql = "UPDATE application SET (main = ?, version = ?, jar = ?) WHERE name = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setQueryTimeout(10);

            stmt.setString(1, main);
            stmt.setString(2, version);
            stmt.setString(3, jar);
            stmt.setString(4, name);

            count = stmt.executeUpdate();
            stmt.close();

            return (count > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
