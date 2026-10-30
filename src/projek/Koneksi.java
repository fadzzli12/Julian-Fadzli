package projek;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Koneksi {
    private static final String URL = "jdbc:mysql://localhost:3306/absensi_db";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() throws SQLException, ClassNotFoundException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(URL, USER, PASS);
    }

    public static void main(String[] args) {
        try {
            Connection conn = getConnection();
            System.out.println("Koneksi berhasil: " + conn);
            conn.close();
        } catch (Exception e) {
            System.out.println("Gagal: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
