import java.sql.*;
import java.util.Scanner;


public class RegOekt extends DBConn {

    private PreparedStatement register_stmt;


    public void registrerOekt(){
        Scanner bruker_input = new Scanner(System.in);

        String sqlstmt = "INSERT INTO økt(Dato,Tidspunkt, Varighet, Form, Prestasjon, Notat) "
                + "VALUES(?,?,?,?,?,?)";
        long system_time = System.currentTimeMillis();
        Date current_date = new Date(system_time);
        Time current_time = new Time(system_time);
        try {
           // PreparedStatement stmt=conn.prepareStatement("select * from økt");
           // System.out.println(stmt);
           // ResultSet rs=stmt.executeQuery();
           // System.out.println(rs);
            register_stmt = conn.prepareStatement(sqlstmt);
            // Antagelse: Bruker bare nåværende dato, ikke mulig for bruker å skrive inn dato manuelt
            register_stmt.setDate(1, current_date);
            register_stmt.setTime(2, current_time);

            System.out.println("Hvor lenge varte økta? (oppgi antall minutt) ");
            int varighet_oekt = Integer.parseInt(bruker_input.nextLine());
            register_stmt.setInt(3, varighet_oekt);

            System.out.println("Hvordan var formen din? (1-10) ");
            int form_oekt = Integer.parseInt(bruker_input.nextLine());
            register_stmt.setInt(4, form_oekt);

            System.out.println("Hvordan presterte du? (1-10) ");
            int prestasjon_oekt = Integer.parseInt(bruker_input.nextLine());
            register_stmt.setInt(5, prestasjon_oekt);

            System.out.println("Notat om økta (maks 500karakterer) ");
            String notat_oekt = bruker_input.nextLine();
            register_stmt.setString(6, notat_oekt);


           register_stmt.executeUpdate();

        } catch (Exception e) {
            System.out.println("DB error during registration of Økt");
        }
    }

    public String getOekt(){
        return " ";
    }
}
