import java.sql.*;

public abstract class DBConn {
    protected Connection conn;
    public DBConn () {
    }
    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/DBProsjekt?autoReconnect=true&useSSL=false", "root", "root");
        } catch (Exception e)
        {
            throw new RuntimeException("Unable to connect", e);
        }
    }
}