package net.comorevi.cphone.cphone.sql;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.comorevi.cphone.cphone.application.ApplicationPermission;
import net.comorevi.cphone.cphone.application.ApplicationManifest;
import net.comorevi.cphone.cphone.data.ApplicationData;
import net.comorevi.cphone.cphone.data.RuntimeData;
import net.comorevi.cphone.cphone.exception.PermissionException;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ApplicationSQLManager {

    private static Connection conn = null;
    private static String connname;

    private static boolean inited;

    public static boolean init() {
        try {
            if (!inited) {
                inited = true;

                Class.forName(RuntimeData.config.getString("SQLClass"));
                connname = "jdbc:" + RuntimeData.config.getString("SQLEngine") + "://" + RuntimeData.config.getString("ApplicationSQL");
                conn = DriverManager.getConnection(connname);

                int count;
                String sql =
                        "CREATE TABLE IF NOT EXISTS user (" +
                                "name TEXT NOT NULL, " +
                                "applications TEXT NOT NULL, " +
                                "permission TEXT NOT NULL, " +
                                "notices TEXT NOT NULL, " +
                                "read INTEGER" +
                                ")";

                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setQueryTimeout(10);
                count = stmt.executeUpdate();
                stmt.close();

                ApplicationData.applications
                        .values()
                        .stream()
                        .filter(manifest -> manifest.getPermission() == ApplicationPermission.ATTRIBUTE_DEFAULT)
                        .forEach(manifest -> getUserNames().forEach(name -> installApplication(name, manifest)));

                return (count > 0);

            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean addUser(String name, ApplicationPermission attribute) {
        try {
            int count;
            String sql =
                    "INSERT INTO " +
                    "user (" +
                        "name, applications, permission, notices, read" +
                    ") VALUES (" +
                        "?, ?, ?, ?, ?" +
                    ")";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setQueryTimeout(10);

            stmt.setString(1, name);
            stmt.setString(2, "");
            stmt.setString(3, attribute.getName());
            stmt.setString(4, "");
            stmt.setInt(5, fromBoolean(true));

            count = stmt.executeUpdate();
            stmt.close();

            ApplicationData.applications
                    .values()
                    .stream()
                    .filter(manifest -> manifest.getPermission() == ApplicationPermission.ATTRIBUTE_DEFAULT)
                    .forEach(manifest -> installApplication(name, manifest));

            return (count > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteUser(String name) {
        try {
            int count;
            String sql = "DELETE FROM user WHERE name = ?";

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

    public static boolean installApplication(String name, ApplicationManifest manifest) {
        try {
            int count;
            String sql = "UPDATE user SET applications = ? WHERE name = ?";

            List<String> applications = new ArrayList<>();
            applications.addAll(getApplications(name));

            if (applications.contains(manifest.getTitle())) return false;

            if (!getPermission(name).canAccept(manifest.getPermission())) {
                throw new PermissionException("Permission denied [" + name + "]: " + getPermission(name).getName() + ", Required: " + manifest.getPermission().getName());
            }

            applications.add(manifest.getTitle());

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setQueryTimeout(10);

            stmt.setString(1, new Gson().toJson(applications));
            stmt.setString(2, name);

            count = stmt.executeUpdate();
            stmt.close();

            return (count > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean unInstallApplication(String name, ApplicationManifest manifest) {
        try {
            int count;
            String sql = "UPDATE user SET applications = ? WHERE name = ?";

            List<String> applications = getApplications(name);
            if (!applications.contains(manifest.getTitle())) return false;

            applications.remove(manifest.getTitle());

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setQueryTimeout(10);

            stmt.setString(1, new Gson().toJson(applications));
            stmt.setString(2, name);

            count = stmt.executeUpdate();
            stmt.close();

            return (count > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean setPermission(String name, ApplicationPermission attribute) {
        try {
            int count;
            String sql = "UPDATE user SET permission = ? WHERE name = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setQueryTimeout(10);

            stmt.setString(1, attribute.getName());
            stmt.setString(2, name);

            count = stmt.executeUpdate();
            stmt.close();

            return (count > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static boolean setRead(String name, boolean read) {
        try {
            int count;
            String sql = "UPDATE user SET read = ? WHERE name = ?";

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setQueryTimeout(10);

            stmt.setInt(1, fromBoolean(read));
            stmt.setString(2, name);

            count = stmt.executeUpdate();
            stmt.close();

            return (count > 0);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static List<String> getApplications(String name) {
        try {
            String sql = "SELECT applications FROM user WHERE name = ?";
            String json = "";
            List<String> applications;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setQueryTimeout(10);
            stmt.setString(1, name);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) json = rs.getString("applications");
            stmt.close();

            applications = new Gson().fromJson(json, new TypeToken<List<String>>(){}.getType());

            if (applications == null) return Collections.emptyList();

            return applications;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static List<String> getUserNames() {
        try {
            String sql = "SELECT * FROM user";
            String json = "";
            List<String> names = new ArrayList<>();

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setQueryTimeout(10);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                names.add(rs.getString("name"));
            }
            stmt.close();

            return names;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static ApplicationPermission getPermission(String name) {
        try {
            String sql = "SELECT permission FROM user WHERE name = ?";
            ApplicationPermission result = null;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setQueryTimeout(10);
            stmt.setString(1, name);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) result = ApplicationPermission.fromName(rs.getString("permission"));
            stmt.close();

            return result;

        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean isRead(String name) {
        try {
            String sql = "SELECT read FROM user WHERE name = ?";
            int result = 0;

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setQueryTimeout(10);
            stmt.setString(1, name);

            ResultSet rs = stmt.executeQuery();
            if (rs.next()) result = rs.getInt("read");
            stmt.close();

            return toBoolean(result);

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 0: true
     * 1: false
     * @param bool
     * @return
     */
    private static int fromBoolean(boolean bool) {
        return bool ? 0 : 1;
    }

    private static boolean toBoolean(int i) {
        return (i == 0);
    }

}
