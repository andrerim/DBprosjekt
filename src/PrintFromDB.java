import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

public class PrintFromDB extends DBConn{
    public PreparedStatement select_stmt;

    public void printResultFromQuery(String sqlstmt){
            try {
                select_stmt = conn.prepareStatement(sqlstmt);
                ResultSet rs = select_stmt.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();;
                int columnsNumber = rsmd.getColumnCount();
                while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                        if (i > 1) System.out.print(",  ");
                        String columnValue = rs.getString(i);
                        System.out.print(columnValue + " " + rsmd.getColumnName(i));
                    }
                    System.out.println(" ");
                }

            } catch (Exception e){
                System.out.println("DB error when printing result from query");
            }
    }

    public void printLatestInsert(String table){
        String sqlstmt = "SELECT * FROM " + table + " WHERE " +table + "." + table +"id = (SELECT LAST_INSERT_ID)";
        try {
            select_stmt = conn.prepareStatement(sqlstmt);
            ResultSet rs = select_stmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    if (i > 1) System.out.print(",  ");
                    String columnValue = rs.getString(i);
                    System.out.print(columnValue + " " + rsmd.getColumnName(i));
                }
                System.out.println(" ");
            }

        } catch (Exception e){
            System.out.println("DB error when printing last inserted record");
        }
    }
}
