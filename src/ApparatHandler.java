import java.sql.*;
import java.util.Scanner;

public class ApparatHandler extends DBConn {
    private PreparedStatement register_stmt;

    public void registerApparat (){
        Scanner bruker_input = new Scanner(System.in);
        String sqlstmt = "INSERT INTO apparat (Navn, Beskrivelse) VALUES (?, ?)";
        try {
            register_stmt = conn.prepareStatement(sqlstmt);

            System.out.println("Hva heter apparatet?");
            String apparatnavn = bruker_input.nextLine();
            register_stmt.setString(1, apparatnavn);

            System.out.println("Beskrivelse av " + apparatnavn);
            String beskrivelseApparat = bruker_input.nextLine();
            register_stmt.setString(2, beskrivelseApparat);

            register_stmt.executeUpdate();
            System.out.println("Successfully inserted " + apparatnavn + " into apparat");
        } catch (Exception e){
            System.out.println("DB error during registration of Apparat" + e);
        }
    }

    public int getApparatID(String apparatNavn){
        String sqlstmt = "SELECT ApparatID FROM apparat WHERE navn = (?)";


        int apparatID = 0;
        try {
            PreparedStatement get_stmt = conn.prepareStatement(sqlstmt);
            get_stmt.setString(1, apparatNavn);
            ResultSet rs = get_stmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnsNumber = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnsNumber; i++) {
                    apparatID = Integer.parseInt(rs.getString(i));
                }
            }
        } catch (Exception e){
            System.out.println("DB error while getting apparatID" + e);
        }
        return apparatID;
    }
}
