import java.sql.*;

public abstract class DBConn {
    protected Connection conn;
    public DBConn () {
    }
    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = DriverManager.getConnection(
                    "jdbc:mysql://mysql.stud.ntnu.no/fs_tdt4145_1_gruppe145?user=fs_tdt4145_1_gruppe145&password=datdat123"
                    , "fs_tdt4145_1_gruppe145", "datdat123");
        } catch (Exception e)
        {
            throw new RuntimeException("Unable to connect", e);
        }
    }
}