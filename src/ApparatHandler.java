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
            System.out.println("DB error during registration of Apparat");
        }


    }
}
