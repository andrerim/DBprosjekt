import java.sql.*;
import java.util.Scanner;

public class OvelseHandler extends DBConn {

    private PreparedStatement register_stmt;
    int ovelseKey;
    public void registrerOvelse(){
        Scanner bruker_input = new Scanner(System.in);

        String sqlstmt = "INSERT INTO Øvelse (Navn) VALUES (?)";

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
            System.out.println("DB error during registration of Øvelse" + e);
        }

        System.out.println("Utføres øvelsen med apparat? (ja/nei)");
        String ovelse_type = bruker_input.nextLine();

        if (ovelse_type.equals("ja")){
            registrerOvelseMedApparat(ovelseKey);

        } else {
            registrerOvelseUtenApparat(ovelseKey);
        }
    }

    private void registrerOvelseMedApparat(int ovelseKey){
        String sqlstmt = "INSERT INTO ØvelseMedApparat (ØvelseID, ApparatID, AntallKilo, AntallSet) VALUES (?, ?, ?, ?)";

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
            register_stmt.executeUpdate();

            PrintFromDB p = new PrintFromDB();
            p.connect();
            sqlstmt = "SELECT * from ØvelseMedApparat natural join Øvelse WHERE ØvelseID = (select max(ØvelseID) from ØvelseMedAparat)";
            p.printResultFromQuery(sqlstmt);

        } catch (Exception e) {
            System.out.println("DB error during registration of ovelse" + e);
        }
    }

    public void registrerOvelseUtenApparat(int ovelseKey){
        String sqlstmt = "INSERT INTO ØvelseUtenApparat (ØvelseID, Beskrivelse) VALUES (?, ?)";

        Scanner bruker_input = new Scanner(System.in);

        try {
            register_stmt = conn.prepareStatement(sqlstmt);

            register_stmt.setInt(1, ovelseKey);

            System.out.println("Beskrivelse av øvelsen: ");

            String beskrivelse = bruker_input.nextLine();
            register_stmt.setString(2, beskrivelse);

            register_stmt.executeUpdate();

            PrintFromDB p = new PrintFromDB();
            p.connect();
            sqlstmt = "SELECT * from ØvelseUtenApparat natural join Øvelse WHERE ØvelseID = (select max(ØvelseID) from ØvelseUtenApparat);";
            p.printResultFromQuery(sqlstmt);
        } catch (Exception e){
            System.out.println("DB error during registrations of øvelseutenapparat" + e);
        }
    }

    public void resultatlogg(){
        String sqlstmt = "SELECT * FROM Økt natural join ØktHarØvelse natural join Øvelse WHERE (Dato >= ";
        Scanner bruker_input = new Scanner(System.in);
        System.out.println("Spesifiser intervallet du ønsker å se resultater fra: (format YYYY-MM-DD)");
        System.out.println("Start dato:");
        String fraDato = bruker_input.nextLine();
        System.out.println("Til dato: ");
        String tilDato = bruker_input.nextLine();
        sqlstmt = sqlstmt + " ' " + fraDato + " ' " + " AND Dato <= " + " ' " + tilDato + "'" + ")";
        try {
            PrintFromDB p = new PrintFromDB();
            p.connect();
            p.printResultFromQuery(sqlstmt);

        } catch (Exception e){
            System.out.println("DB error while getting results" + e);
        }
    }
}
