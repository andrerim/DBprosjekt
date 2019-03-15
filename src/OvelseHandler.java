import java.sql.*;
import java.util.Scanner;

public class OvelseHandler extends DBConn {

    private PreparedStatement register_stmt;
    int ovelseKey;
    public void registrerOvelse(){
        Scanner bruker_input = new Scanner(System.in);

        String sqlstmt = "INSERT INTO øvelse (Navn) VALUES (?)";

        try {

            register_stmt = conn.prepareStatement(sqlstmt, Statement.RETURN_GENERATED_KEYS);


            System.out.println("Navn på øvelsen: ");
            String ovelseNavn = bruker_input.nextLine();
            register_stmt.setString(1, ovelseNavn);
            register_stmt.executeUpdate();
            ResultSet rs = register_stmt.getGeneratedKeys();
            if (rs.next()) {
                ovelseKey = rs.getInt(1);
            }

        }  catch (Exception e){
            System.out.println("DB error during registration of Øvelse");
        }

        System.out.println("Utføres øvelsen med apparat? (ja/nei)");
        String ovelse_type = bruker_input.nextLine();

        if (ovelse_type.equals("ja")){
            registrerOvelseMedApparat(ovelseKey);
        } else {
            ovelse_type = "øvelseutenapparat";
        }
    }

    public void registrerOvelseMedApparat(int ovelseKey){
        String sqlstmt = "INSERT INTO øvelsemedapparat (ØvelseID, ApparatID, AntallKilo, AntallSet) VALUES (?, ?, ?, ?)";

        Scanner bruker_input = new Scanner(System.in);

        try {
            register_stmt = conn.prepareStatement(sqlstmt);

            register_stmt.setInt(1, ovelseKey);

            System.out.println("Navn på apparat:  ");
            String apparatNavn = bruker_input.nextLine();

            ApparatHandler apparat = new ApparatHandler();
            apparat.connect();
            int apparatID = apparat.getApparatID(apparatNavn);

            register_stmt.setInt(2, apparatID);

            System.out.println("Hvor mange kilo? ");
            int antallKilo = Integer.parseInt(bruker_input.nextLine());
            register_stmt.setInt(3, antallKilo);

            System.out.println("Hvor mange set? ");
            int antallSet = Integer.parseInt(bruker_input.nextLine());
            register_stmt.setInt(4, antallSet);


        } catch (Exception e) {
            System.out.println("DB error during registration of ovelse");
        }
    }

}
