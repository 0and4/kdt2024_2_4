package classLoader;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connect {
    private static final String url = "jdbc:mysql://localhost:3306/theater";
    private static Connection connection = null;

    public static Connection getConnection() {
        if (connection != null) {
            return connection;
        } else {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(
                        url,
                        "root",
                        "0000"
                );
                System.out.println("연결에 성곡하셨습니다...ㄴ");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return connection;
        }
    }
}
